package org.firstinspires.ftc.teamcode.ftc16072.Tests;

import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestLimelight extends QQTest{
Limelight3A limelight;

    public  TestLimelight(String name, Limelight3A limelight){
        super(name);
        this.limelight = limelight;
    }
    @Override
    public void run(boolean on, Telemetry telemetry) {
        telemetry.addData("Attached", limelight.isConnected());
    }
}
