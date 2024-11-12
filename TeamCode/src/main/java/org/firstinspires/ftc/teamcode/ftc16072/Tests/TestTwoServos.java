package org.firstinspires.ftc.teamcode.ftc16072.Tests;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestTwoServos extends QQTest{
    double onPosition;
    double offPosition;
    Servo servo;
    Servo servo2;

    public TestTwoServos(String name, double onPosition, double offPosition, Servo servo, Servo servo2){
        super(name);
        this.onPosition = onPosition;
        this.offPosition = offPosition;
        this.servo = servo;
        this.servo2 = servo2;
    }
    @Override
    public void run(boolean on, Telemetry telemetry) {
        if(on){
            servo.setPosition(onPosition);
        }else{
            servo.setPosition(offPosition);
        }
        if(on){
            servo2.setPosition(onPosition);
        }else{
            servo2.setPosition(offPosition);
        }
        telemetry.addData("Position", servo.getPosition());
        telemetry.addData("Position", servo2.getPosition());
    }
}
