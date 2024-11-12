package org.firstinspires.ftc.teamcode.ftc16072.Tests;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class TestWebcam extends QQTest{
WebcamName webcamName;
    public TestWebcam(String name, WebcamName webcamName){
        super(name);
        this.webcamName = webcamName;
    }
    @Override
    public void run(boolean on, Telemetry telemetry) {
        telemetry.addData("Attached", webcamName.isAttached());
    }
}
