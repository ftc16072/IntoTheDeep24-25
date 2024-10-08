package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestTwoMotors;
import org.firstinspires.ftc.teamcode.ftc16072.Util.PIDFController;

import java.util.Arrays;
import java.util.List;

public class DoubleReverse4Bar extends QQMechanism{
    public static final double TEST_SPEED = 0.55;
    public static double TEST_SPEED_OFF = 0.4;
    DcMotor leftMotor;
    DcMotor rightMotor;
    public static double kP = 0.01;
    public static double kI = 0.0;
    public static double kD = 0.0;
    public static double kF = 0.4;
    public static double max =  0.8;
    public static double min = 0.0;

    public int currentPos;
    public int desiredPos;
    public double motorPower;


    PIDFController pidfController = new PIDFController(kP,kI,kD,kF,max,min);

    @Override
    public void init(HardwareMap hwMap) {
        leftMotor = hwMap.get(DcMotor.class, "left_4bar_motor");
        rightMotor = hwMap.get(DcMotor.class, "right_4bar_motor");
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setPosition(int desiredPos){
        this.desiredPos = desiredPos;
    }
    public void manualPositionChange(int changeAmount){
        desiredPos += changeAmount;
    }
    @Override
    public void update(){
        currentPos = (leftMotor.getCurrentPosition() + rightMotor.getCurrentPosition())/2;//average left and right speeds
        double motorPower = pidfController.calculate(desiredPos,currentPos);
        this.motorPower = motorPower;
        leftMotor.setPower(motorPower);
        rightMotor.setPower(motorPower);
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestTwoMotors("fourbar up", TEST_SPEED,leftMotor,rightMotor),
                new TestTwoMotors("four bar stop", TEST_SPEED_OFF,leftMotor,rightMotor)
        );
    }
}
