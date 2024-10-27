package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestMotor;
import org.firstinspires.ftc.teamcode.ftc16072.Util.PIDFController;

import java.util.Arrays;
import java.util.List;
@Config
public class Arm extends QQMechanism{
    public static final double TEST_SPEED = 0.35;
    DcMotor armMotor;
    public static double kP = 0.01;
    public static double kI = 0.0;
    public static double kD = 0.0;
    public static double kF = 0;
    public static double max =  0.4;
    public static double min = -max;

    public int currentPos;
    public int desiredPos;
    public double motorPower;
    public static int PLACEMENT_POSITION = 1030;
    public static int INTAKE_POSITION = 315;
    public static int PLACING_POSITION = 1300;
    public static int AUTO_DRIVE_POSITION = 120;
    public static int GROUND_POSITION = -10;
    static final int WRIST_THRESHOLD = 500;

    public Telemetry telemetry;


    PIDFController pidfController = new PIDFController(kP,kI,kD,kF,max,min);

    @Override
    public void init(HardwareMap hwMap) {
        armMotor = hwMap.get(DcMotor.class, "arm_motor");
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
    public void place(){desiredPos = PLACING_POSITION;}
    public void goToDrive(){desiredPos = AUTO_DRIVE_POSITION;}
    public void goToGround(){desiredPos = GROUND_POSITION;}

    public boolean isAboveWristThreshold(){
        return armMotor.getCurrentPosition() > WRIST_THRESHOLD;
    }

    @Override
    public void update(){
        currentPos = (armMotor.getCurrentPosition());
        double motorPower = pidfController.calculate(desiredPos,currentPos);
        telemetry.addData("Current", currentPos);
        telemetry.addData("Desired", desiredPos);
        telemetry.addData("Motor Power", motorPower);
        this.motorPower = motorPower;
        armMotor.setPower(motorPower);
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestMotor("arm up", TEST_SPEED, armMotor),
                new TestMotor("arm down", -TEST_SPEED, armMotor)
        );
    }
}
