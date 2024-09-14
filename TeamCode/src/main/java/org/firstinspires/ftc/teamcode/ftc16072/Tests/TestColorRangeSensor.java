package org.firstinspires.ftc.teamcode.ftc16072.Tests;

import com.qualcomm.robotcore.hardware.ColorRangeSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


public class TestColorRangeSensor extends QQTest{
    ColorRangeSensor sensor;

    public TestColorRangeSensor(String name, ColorRangeSensor sensor){
        super(name);
        this.sensor = sensor;
    }
    @Override
    public void run(boolean on, Telemetry telemetry) {
        telemetry.addData("distance", sensor.getDistance(DistanceUnit.MM));
        telemetry.addData("red",sensor.red());
        telemetry.addData("green",sensor.green());
        telemetry.addData("blue",sensor.blue());
    }
}
