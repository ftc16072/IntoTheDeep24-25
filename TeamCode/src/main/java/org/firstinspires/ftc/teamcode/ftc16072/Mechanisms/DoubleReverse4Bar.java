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
    public static final double TEST_SPEED = 0.2;
    DcMotor leftMotor;
    DcMotor rightMotor;
    public static double kP = 0.001;
    public static double kI = 0.0;
    public static double kD = 0.0;
    public static double kF = 0.0;
    public static double max =  0.8;
    public static double min = 0.0;

    int currentPos;
    int desiredPos;


    PIDFController pidfController = new PIDFController(kP,kI,kD,kF,max,min);

    public int INTAKE_POS = 0;//TODO: make real

    public int PLACE_POS = 600; //TODO: make real :)

    @Override
    public void init(HardwareMap hwMap) {
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftMotor = hwMap.get(DcMotor.class, "left_4bar_motor");
        rightMotor = hwMap.get(DcMotor.class, "right_4bar_motor");
    }

    public setPosition(int desiredPos){
        this.desiredPos = desiredPos;
    }
    public manualPositionChange(int changeAmount){
        desiredPos += changeAmount;
    }
    @Override
    public void update(){
        currentPos = (leftMotor.getCurrentPosition() + rightMotor.getCurrentPosition())/2;//average left and right speeds
        double motorPower = pidfController.calculate(desiredPos,currentPos);
        leftMotor.setPower(motorPower);
        rightMotor.setPower(motorPower);
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(new TestTwoMotors("fourbar", TEST_SPEED,leftMotor,rightMotor));
    }
}
