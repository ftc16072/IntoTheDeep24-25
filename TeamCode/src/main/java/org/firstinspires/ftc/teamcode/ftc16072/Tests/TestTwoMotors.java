package org.firstinspires.ftc.teamcode.ftc16072.Tests;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestTwoMotors extends QQTest{
    DcMotor leftMotor;
    DcMotor rightMotor;
    double speed;
    public TestTwoMotors(String name, double speed, DcMotor leftMotor, DcMotor rightMotor){
        super(name);
        this.speed = speed;
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }
    @Override
    public void run(boolean on, Telemetry telemetry) {
        telemetry.addData("encoder position",leftMotor.getCurrentPosition());
        telemetry.addData("encoder position",rightMotor.getCurrentPosition());
        if(on){
            leftMotor.setPower(speed);
            rightMotor.setPower(speed);

        }else{
            leftMotor.setPower(0);
            rightMotor.setPower(0);

        }

    }

}
