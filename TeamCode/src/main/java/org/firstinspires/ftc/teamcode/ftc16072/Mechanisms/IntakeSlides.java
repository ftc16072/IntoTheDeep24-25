package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;


import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestSwitch;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestTwoMotors;
import org.firstinspires.ftc.teamcode.ftc16072.Util.PIDFController;

import java.util.Arrays;
import java.util.List;


@Config
public class IntakeSlides extends QQMechanism {
    //HAVE TO CHANGE ALL VALUES LATER
    public static final double TEST_SPEED = 0.55;
    private static final int SlIDES_POSITION_SAFETY_BACK = -50;
    private static final int FULL_EXTENSION_POSITION = 1480;
    public static final int SLIDES_EXTENSION_BOUNDARY = FULL_EXTENSION_POSITION+10;
    private static final int HALF_EXTENSION_POSITION = 740;
    private static final int START_POSITION = 0;
    public static double MANUAL_CHANGE_AMOUNT = 30;

    private DcMotor rightIntakeSlideMotor;
    private DcMotor leftIntakeSlideMotor;
    private TouchSensor limitSwitch;

    boolean isWithinTolerence;


    public int currentPos;
    public int desiredPos;
    public double motorPower;

    public static double kP = 0.01;
    public static double kI = 0.0;
    public static double kD = 0.0;
    public static double kF = 0.0;
    public static double max =  0.8;
    public static double min = -max;

    PIDFController pidfController = new PIDFController(kP,kI,kD,kF,max,min);


    @Override
    public void init(HardwareMap hwMap) {

        rightIntakeSlideMotor = hwMap.get(DcMotor.class, "right_intake_slide_motor");
        rightIntakeSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightIntakeSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightIntakeSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightIntakeSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftIntakeSlideMotor = hwMap.get(DcMotor.class, "left_intake_slide_motor");
        leftIntakeSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftIntakeSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftIntakeSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        limitSwitch = hwMap.get(TouchSensor.class, "slides_switch");
    }
    public void setPosition(int desiredPos){
        this.desiredPos = desiredPos;
    }
    public void extend(double speed){
        manualPositionChange(MANUAL_CHANGE_AMOUNT * speed);
    }
    public void retract(double speed){
        manualPositionChange(-MANUAL_CHANGE_AMOUNT * speed);
    }
    private void manualPositionChange(double changeAmount){
        desiredPos += changeAmount;
    }
    @Override
    public void update(Telemetry telemetry){
        if(limitSwitch.isPressed()) {
            leftIntakeSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftIntakeSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightIntakeSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightIntakeSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            if (desiredPos < 0) {
                desiredPos = 0;
            }
        }
        if (Math.abs(desiredPos - currentPos) <= 30){
            isWithinTolerence = true;
        }
        currentPos = (leftIntakeSlideMotor.getCurrentPosition() + rightIntakeSlideMotor.getCurrentPosition())/2;//average left and right speeds
        double motorPower = pidfController.calculate(desiredPos,currentPos);
        if (desiredPos == 0 && !limitSwitch.isPressed()){
            motorPower = -0.8;
        }
        this.motorPower = motorPower;
        leftIntakeSlideMotor.setPower(motorPower);
        rightIntakeSlideMotor.setPower(motorPower);
        //telemetry.addData("Current", currentPos);
        //telemetry.addData("Desired", desiredPos);
        //telemetry.addData("Motor Power", motorPower);

        if(currentPos <= SlIDES_POSITION_SAFETY_BACK){
            setPosition(SlIDES_POSITION_SAFETY_BACK);
        }

        if(currentPos > SLIDES_EXTENSION_BOUNDARY){
            setPosition(SLIDES_EXTENSION_BOUNDARY);
        }
    }

    public boolean isSwitchPressed(){
        return limitSwitch.isPressed();
    }
    public boolean isSafeToRotate() { return currentPos > HALF_EXTENSION_POSITION;}

    public boolean getIsWithinTolerence(){
        return isWithinTolerence;
    }




    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestTwoMotors("slides out", TEST_SPEED,leftIntakeSlideMotor,rightIntakeSlideMotor),
                new TestTwoMotors("slides in", -TEST_SPEED,leftIntakeSlideMotor,rightIntakeSlideMotor),
                new TestSwitch("slides limit switch", limitSwitch)
        );
    }
    public void fullExtension(){
        setPosition(FULL_EXTENSION_POSITION);
    }
    public void halfExtension(){
        setPosition(HALF_EXTENSION_POSITION);
    }
    public void startPosition(){
        setPosition(START_POSITION);
    }


}


