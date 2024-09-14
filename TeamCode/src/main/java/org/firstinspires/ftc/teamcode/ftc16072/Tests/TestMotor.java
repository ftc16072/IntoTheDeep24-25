package org.firstinspires.ftc.teamcode.ftc16072.Tests;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestMotor extends QQTest{
    DcMotor motor;
    double speed;
    public TestMotor(String name, double speed, DcMotor motor){
        super(name);
        this.speed = speed;
        this.motor = motor;
    }
    @Override
    public void run(boolean on, Telemetry telemetry) {
        telemetry.addData("encoder position",motor.getCurrentPosition());
        if(on){
            motor.setPower(speed);
        }else{
            motor.setPower(0);
        }

    }

}
