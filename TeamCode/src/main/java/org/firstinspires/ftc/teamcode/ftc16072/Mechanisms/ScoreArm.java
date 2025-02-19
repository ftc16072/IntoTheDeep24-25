package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestSwitch;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestTwoMotors;
import org.firstinspires.ftc.teamcode.ftc16072.Util.PIDFController;

import java.util.Arrays;
import java.util.List;

@Config
public class ScoreArm extends QQMechanism{
    public static final double TEST_SPEED = 0.55;
    public static final int CLAW_RELEASE_OFFSET = 250;
    public static final double SCORE_POWER = -0.8;
    public static final double STALL_CURRENT = 8;
    public static final int TOLERANCE_THRESHOLD = 100;
    DcMotorEx leftMotor;
    DcMotorEx rightMotor;
    TouchSensor limitSwitch;
    TouchSensor rightChamberContact;
    TouchSensor leftChamberContact;
    public static double kP = 0.0017;
    public static double kI = 0.0;
    public static double kD = 0;
    public static double kF = 0;
    public static double max =  1.0;
    public static double min = -1.0;
    boolean wasChamberContacted;
    protected int currentPos;
    public int desiredPos;
    boolean isScoring;
    double lastEncoderPos;

    boolean encoderStalled;

    public static int INTAKE_POSITION = 0;
    public static int SCORING_POSITION = 450;
    public static int PLACING_POSITION = 830;
    public static int MOVING_POSITION = 450;
    public static int INIT_POSITION = 240;

    public boolean isWithinTolerance;

    PIDFController pidfController = new PIDFController(kP,kI,kD,kF,max,min);

    @Override
    public void init(HardwareMap hwMap) {
        leftMotor = hwMap.get(DcMotorEx.class, "left_score_arm_motor");
        rightMotor = hwMap.get(DcMotorEx.class, "right_score_arm_motor");
        limitSwitch = hwMap.get(TouchSensor.class, "score_arm_switch");
        rightChamberContact = hwMap.get(TouchSensor.class, "right_chamber_contact_switch");
        leftChamberContact = hwMap.get(TouchSensor.class,"left_chamber_contact_switch");
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void goToIntake(){
        desiredPos = INTAKE_POSITION;
    }
    public void goToScoring(){
        desiredPos = SCORING_POSITION;
    }
    public void goToPlace(){desiredPos = PLACING_POSITION;}
    public void goToMove(){desiredPos = MOVING_POSITION;}
    public void goToInit(){desiredPos = INIT_POSITION;}

    public void manualPositionChange(int changeAmount){
        desiredPos += changeAmount;
    }




    @Override
    public void update(Telemetry telemetry){
        boolean newChamberContacted = rightChamberContact.isPressed() || leftChamberContact.isPressed();
        if(limitSwitch.isPressed()) {
            leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            isScoring = false;
            if (desiredPos < 0){
                desiredPos = 0;
            }
        }else if (newChamberContacted && !wasChamberContacted){
            isScoring = true;
            goToScoring();
        }
        wasChamberContacted = newChamberContacted;
        currentPos = (leftMotor.getCurrentPosition() + rightMotor.getCurrentPosition())/2;//average left and right speeds

        double motorPower = pidfController.calculate(desiredPos,currentPos);
        if (isScoring){
            if (lastEncoderPos == currentPos){
                encoderStalled = true;
            }
            lastEncoderPos = currentPos;
            motorPower = SCORE_POWER;
        }else {
            lastEncoderPos = 0;
            encoderStalled = false;
        }
        leftMotor.setPower(motorPower);
        rightMotor.setPower(motorPower);

        if (Math.abs(desiredPos - currentPos) <= TOLERANCE_THRESHOLD){
            isWithinTolerance = true;
        }else {isWithinTolerance = false;}

        //pidfController.updateConstants(kP,kI,kD,kF,max,min);
        telemetry.addData("curerent pos",currentPos);
        telemetry.addData("desired pos",desiredPos);
        telemetry.addData("motor power",motorPower);
        telemetry.addData("left motor current", leftMotor.getCurrent(CurrentUnit.AMPS));
        telemetry.addData("right motor current", rightMotor.getCurrent(CurrentUnit.AMPS));
        telemetry.addData("Is stalled", isStalling());
    }
    public void setNotScoring(){
        isScoring = false;
    }
    public boolean isChamberContacted(){
        return wasChamberContacted;
    }

    public int getCurrentPos() {
        return currentPos;
    }
    public boolean isTimeToReleaseClaw(int lostContactPosition){
        return (currentPos < lostContactPosition - CLAW_RELEASE_OFFSET);
    }
    public boolean isLimitSwitchPressed(){
        return limitSwitch.isPressed();
    }

    public boolean getIsWithinTolerence(){return isWithinTolerance;}


    public boolean isStalling(){
        return (((leftMotor.getCurrent(CurrentUnit.AMPS) > STALL_CURRENT) || (rightMotor.getCurrent(CurrentUnit.AMPS) > STALL_CURRENT))||encoderStalled);
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestTwoMotors("score arm up", TEST_SPEED,leftMotor,rightMotor),
                new TestTwoMotors("score arm down", -TEST_SPEED, leftMotor,rightMotor),
                new TestSwitch("Arm limit switch", limitSwitch),
                new TestSwitch("right chamber contact", rightChamberContact),
                new TestSwitch("left chamber contact", leftChamberContact)
        );
    }
}
