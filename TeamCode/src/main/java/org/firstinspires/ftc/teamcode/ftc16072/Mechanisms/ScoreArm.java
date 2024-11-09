package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestServo;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestSwitch;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestTwoMotors;
import org.firstinspires.ftc.teamcode.ftc16072.Util.PIDFController;

import java.util.Arrays;
import java.util.List;

public class ScoreArm extends QQMechanism{
    public static final double TEST_SPEED = 0.55;
    public static double TEST_SPEED_OFF = 0.4;
    DcMotor leftMotor;
    DcMotor rightMotor;
    TouchSensor limitSwitch;
    public static double kP = 0.01;
    public static double kI = 0.0;
    public static double kD = 0.0;
    public static double kF = 0;
    public static double max =  0.8;
    public static double min = -0.8;

    public int currentPos;
    public int desiredPos;
    public double motorPower;

    static int INTAKE_POSITION = 0;
    static int SCORING_POSITION = 940;
    static int PLACING_POSITION = 1300;

    public Telemetry telemetry;


    PIDFController pidfController = new PIDFController(kP,kI,kD,kF,max,min);

    @Override
    public void init(HardwareMap hwMap) {
        leftMotor = hwMap.get(DcMotor.class, "left_score_arm_motor");
        rightMotor = hwMap.get(DcMotor.class, "right_score_arm_motor");
        limitSwitch = hwMap.get(TouchSensor.class, "score_arm_switch");
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
    public void goToPlace(){
        desiredPos = PLACING_POSITION;
    }
    public void manualPositionChange(double changeAmount){
        desiredPos += changeAmount;
    }




    @Override
    public void update(){
        if(limitSwitch.isPressed()) {
            leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            if (desiredPos < 0){
                desiredPos = 0;
            }
        }

        currentPos = (leftMotor.getCurrentPosition() + rightMotor.getCurrentPosition())/2;//average left and right speeds
        double motorPower = pidfController.calculate(desiredPos,currentPos);
        this.motorPower = motorPower;
        leftMotor.setPower(motorPower);
        rightMotor.setPower(motorPower);

    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestTwoMotors("score arm up", TEST_SPEED,leftMotor,rightMotor),
                new TestTwoMotors("score arm down", -TEST_SPEED, leftMotor,rightMotor),
                new TestSwitch("Arm limit switch", limitSwitch)
        );
    }
}
