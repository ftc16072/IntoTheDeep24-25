package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestMotor;

import java.util.Arrays;
import java.util.List;

public class MecanumDrive extends QQMechanism{
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    double speedMultiplier = 1.0;
    public enum Speed {
        SLOW,
        NORMAL,
        FAST,
        TURBO
    }


    @Override
    public void init(HardwareMap hwMap) {
        frontLeftMotor = hwMap.get(DcMotor.class, "front_left_motor");
        frontRightMotor = hwMap.get(DcMotor.class, "front_right_motor");
        backLeftMotor = hwMap.get(DcMotor.class, "back_left_motor");
        backRightMotor = hwMap.get(DcMotor.class,"back_right_motor");
        //device names are set to match the simulator

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        List<DcMotor> motors = Arrays.asList(frontLeftMotor, frontRightMotor,backLeftMotor,backRightMotor);
        for (DcMotor motor : motors){
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }
    public void setSpeed(Speed speed) {
        switch(speed){
            case NORMAL:
                speedMultiplier= 0.5;
                break;
            case SLOW :
                speedMultiplier= 0.25;
                break;
            case FAST:
                speedMultiplier= 0.75;
                break;
            case TURBO:
                speedMultiplier= 1;
                break;
        }
    }

    @Override
    public List<QQTest> getTests() {
        final double TEST_SPEED = 0.2;

        return Arrays.asList(
                new TestMotor("Front Left", TEST_SPEED, frontLeftMotor),
                new TestMotor("Front Right", TEST_SPEED, frontRightMotor),
                new TestMotor("Back Left", TEST_SPEED, backLeftMotor),
                new TestMotor("Back Right", TEST_SPEED, backRightMotor)
        );
    }
    public void move (double forwardSpeed, double strafeRightSpeed, double turnClockwiseSpeed){
        double frontLeftPower = forwardSpeed + strafeRightSpeed + turnClockwiseSpeed;
        double frontRightPower = forwardSpeed - strafeRightSpeed - turnClockwiseSpeed;
        double backRightPower = forwardSpeed + strafeRightSpeed - turnClockwiseSpeed;
        double backLeftPower = forwardSpeed - strafeRightSpeed + turnClockwiseSpeed;

        setPowers(frontLeftPower,frontRightPower,backRightPower,backLeftPower);

    }
    private void setPowers(double frontLeftPower, double frontRightPower,double backRightPower, double backLeftPower){
        double maxSpeed = 1.0;

        frontLeftPower *= speedMultiplier;
        frontRightPower *= speedMultiplier;
        backLeftPower *= speedMultiplier;
        backRightPower *= speedMultiplier;

        maxSpeed = Math.max(Math.abs(frontLeftPower), maxSpeed);
        maxSpeed = Math.max(Math.abs(frontRightPower), maxSpeed);
        maxSpeed = Math.max(Math.abs(backLeftPower), maxSpeed);
        maxSpeed = Math.max(Math.abs(backRightPower), maxSpeed);

        backLeftPower /= maxSpeed;
        backRightPower /= maxSpeed;
        frontLeftPower /= maxSpeed;
        frontRightPower /= maxSpeed;

        frontRightMotor.setPower(frontRightPower);
        frontLeftMotor.setPower(frontLeftPower);
        backRightMotor.setPower(backRightPower);
        backLeftMotor.setPower(backLeftPower);


    }
}

