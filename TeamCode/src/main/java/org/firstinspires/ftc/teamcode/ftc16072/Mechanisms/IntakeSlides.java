package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;


import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestTwoMotors;
import org.firstinspires.ftc.teamcode.ftc16072.Util.PIDFController;

import java.util.Arrays;
import java.util.List;
@Config
public class IntakeSlides extends QQMechanism {
    //HAVE TO CHANGE ALL VALUES LATER
    public static final double TEST_SPEED = 0.55;
    public static double TEST_SPEED_OFF = 0.0;
    public static final int SLIDES_EXTENSION_BOUNDARY = 800;
    private static final int SlIDES_POSITION_SAFETY_BACK = -50;
    private static final int FULL_EXTENSION_POSITION = 2000;
    private static final int HALF_EXTENSION_POSITION = 1000;
    private static final int START_POSITION = 0;

    private DcMotorEx rightIntakeSlideMotor;
    private DcMotorEx leftIntakeSlideMotor;


    public int currentPos;
    public int desiredPos;
    public double motorPower;

    public static double kP = 0.01;
    public static double kI = 0.0;
    public static double kD = 0.0;
    public static double kF = 0.4;
    public static double max =  0.8;
    public static double min = 0.0;

    PIDFController pidfController = new PIDFController(kP,kI,kD,kF,max,min);


    @Override
    public void init(HardwareMap hwMap) {

        rightIntakeSlideMotor = hwMap.get(DcMotorEx.class, "right_intake_slide_motor");
        rightIntakeSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightIntakeSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightIntakeSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightIntakeSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftIntakeSlideMotor = hwMap.get(DcMotorEx.class, "left_intake_slide_motor");
        leftIntakeSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftIntakeSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftIntakeSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftIntakeSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void setPosition(int desiredPos){
        this.desiredPos = desiredPos;
    }
    public void manualPositionChange(int changeAmount){
        desiredPos += changeAmount;
    }
    @Override
    public void update(){
        currentPos = (leftIntakeSlideMotor.getCurrentPosition() + rightIntakeSlideMotor.getCurrentPosition())/2;//average left and right speeds
        double motorPower = pidfController.calculate(desiredPos,currentPos);
        this.motorPower = motorPower;
        leftIntakeSlideMotor.setPower(motorPower);
        rightIntakeSlideMotor.setPower(motorPower);

        if(currentPos <= SlIDES_POSITION_SAFETY_BACK){
            setPosition(START_POSITION);
        }

        if(currentPos > SLIDES_EXTENSION_BOUNDARY){
            setPosition(SLIDES_EXTENSION_BOUNDARY);
        }
    }



    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestTwoMotors("slides out", TEST_SPEED,leftIntakeSlideMotor,rightIntakeSlideMotor),
                new TestTwoMotors("slides stop", TEST_SPEED_OFF,leftIntakeSlideMotor,rightIntakeSlideMotor)
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


