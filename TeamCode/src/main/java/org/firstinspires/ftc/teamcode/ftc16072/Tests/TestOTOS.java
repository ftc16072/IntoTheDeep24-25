package org.firstinspires.ftc.teamcode.ftc16072.Tests;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestOTOS extends QQTest{
    SparkFunOTOS otos;

    public TestOTOS(String name, SparkFunOTOS otos){
        super(name);
        this.otos = otos;
    }
    @Override
    public void run(boolean on, Telemetry telemetry) {
        telemetry.addData("x_position",otos.getPosition().x);
        telemetry.addData("y_position",otos.getPosition().y);
        telemetry.addData("theta",otos.getPosition().h);
        if (on){
            otos.resetTracking();
        }
    }
}
