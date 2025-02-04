package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestLimelight;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestOTOS;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestWebcam;

import java.util.Collections;
import java.util.List;

public class Limelight extends QQMechanism{
    private Limelight3A limelight;

    @Override
    public void init(HardwareMap hwMap) {
        limelight = hwMap.get(Limelight3A.class, "limelight");
    }
    public double getrobotPositionX() {
        LLResult result = limelight.getLatestResult();
        Pose3D botPose = result.getBotpose();
        return botPose.getPosition().x;
    }
    public double getrobotPositionY() {
        LLResult result = limelight.getLatestResult();
        Pose3D botPose = result.getBotpose();
        return botPose.getPosition().y;
    }
    public double getrobotPositionH() {
        LLResult result = limelight.getLatestResult();
        Pose3D botPose = result.getBotpose();
        return botPose.getOrientation().getYaw();
    }

    @Override
    public List<QQTest> getTests() {return Collections.singletonList(new TestLimelight("Limelight attached ", limelight));}
}

