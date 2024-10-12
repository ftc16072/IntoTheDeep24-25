package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestMotor;
import org.firstinspires.ftc.teamcode.ftc16072.Util.PIDFController;

import java.util.Arrays;
import java.util.List;

public class Arm extends QQMechanism{
    public static final double TEST_SPEED = 0.55;
    public static double TEST_SPEED_OFF = 0.4;
    DcMotor armMotor;
    public static double kP = 0.01;
    public static double kI = 0.0;
    public static double kD = 0.0;
    public static double kF = 0.4;
    public static double max =  0.8;
    public static double min = 0.0;

    public int currentPos;
    public int desiredPos;
    public double motorPower;
    public int PLACEMENT_POSITION = 1000; //TODO make real
    public int INTAKE_POSITION = 0; //TODO


    PIDFController pidfController = new PIDFController(kP,kI,kD,kF,max,min);

    @Override
    public void init(HardwareMap hwMap) {
        armMotor = hwMap.get(DcMotor.class, "arm_motor");
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setPosition(int desiredPos){
        this.desiredPos = desiredPos;
    }
    public void manualPositionChange(int changeAmount){
        desiredPos += changeAmount;
    }

    public void goToIntake(){
        desiredPos = INTAKE_POSITION;
    }
    public void goToPlacement(){
        desiredPos = PLACEMENT_POSITION;
    }

    @Override
    public void update(){
        currentPos = (armMotor.getCurrentPosition());
        double motorPower = pidfController.calculate(desiredPos,currentPos);
        this.motorPower = motorPower;
        armMotor.setPower(motorPower);
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestMotor("arm move", TEST_SPEED, armMotor)
        );
    }
}
