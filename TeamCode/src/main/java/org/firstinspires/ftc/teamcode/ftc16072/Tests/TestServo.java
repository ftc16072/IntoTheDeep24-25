package org.firstinspires.ftc.teamcode.ftc16072.Tests;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestServo extends QQTest{
    double onPosition;
    double offPosition;
    Servo servo;

    public TestServo(String name, double onPosition, double offPosition, Servo servo){
        super(name);
        this.onPosition = onPosition;
        this.offPosition = offPosition;
        this.servo = servo;
    }
    @Override
    public void run(boolean on, Telemetry telemetry) {
        if(on){
            servo.setPosition(onPosition);
        }else{
            servo.setPosition(offPosition);
        }
        telemetry.addData("Position", servo.getPosition());

    }
}
