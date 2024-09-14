package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestOTOS;

import java.util.Arrays;
import java.util.List;

public class OpticalTrackingOdometrySensor extends QQMechanism{
    SparkFunOTOS otos;
    @Override
    public void init(HardwareMap hwMap) {
        otos = hwMap.get(SparkFunOTOS.class,"sensor_otos"); //called this for virtual robot
        otos.setAngularUnit(AngleUnit.DEGREES);
        otos.setLinearUnit(DistanceUnit.INCH);
        otos.calibrateImu();
        SparkFunOTOS.Pose2D offset = new SparkFunOTOS.Pose2D(4,0,0);
        otos.setOffset(offset);
        otos.resetTracking();
    }
    public void setOtosPosition(double x, double y, double h){
        SparkFunOTOS.Pose2D position = new SparkFunOTOS.Pose2D(x,y,h);
        otos.setPosition(position);
    }
    public SparkFunOTOS.Pose2D getOtosPosition(){
        return otos.getPosition();
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestOTOS("otos position", otos)
        );
    }
}
