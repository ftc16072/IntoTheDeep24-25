package org.firstinspires.ftc.teamcode.ftc16072.Tests;


import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestSwitch extends QQTest{
    TouchSensor sensor;

    public TestSwitch(String name, TouchSensor sensor){
        super(name);
        this.sensor = sensor;
    }
    @Override
    public void run(boolean on, Telemetry telemetry) {
        telemetry.addData("is pressed",sensor.isPressed());
    }
}
