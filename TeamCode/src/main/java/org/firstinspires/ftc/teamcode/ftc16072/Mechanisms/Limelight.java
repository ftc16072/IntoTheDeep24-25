package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestLimelight;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestOTOS;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestWebcam;

import java.util.Collections;
import java.util.List;

public class Limelight extends QQMechanism{
    private Limelight3A limelight;
    LLResult result;
    private Pose3D lastValidPos = new Pose3D(
            //arbitrary starting pose
            new Position(DistanceUnit.INCH, 0,0,0,0),
            new YawPitchRollAngles(AngleUnit.DEGREES, 0, 0, 0, 0));
    private Pose3D getBotPose() {
        result = limelight.getLatestResult();
        if((result != null) && result.isValid()){
            lastValidPos = result.getBotpose_MT2();
        }
        return lastValidPos;
    }
                                        @Override
    public void init(HardwareMap hwMap) {
        limelight = hwMap.get(Limelight3A.class, "limelight");
    }
    public boolean isAprilTagSeen(){
        return ((result != null) && result.isValid());
    }

    public void update(){
        getBotPose();
    }
    public void start(){
        limelight.start();
    }
    public void stop(){
        limelight.stop();
    }
    public Pose3D getRobotPosition(){
        return getBotPose();
    }
    public double getRobotPositionX(DistanceUnit distanceUnit) {
        return distanceUnit.fromMeters(getBotPose().getPosition().x);
    }
    public double getRobotPositionY(DistanceUnit distanceUnit) {
        return distanceUnit.fromMeters(getBotPose().getPosition().y);
    }
    public void setYaw(AngleUnit angleUnit, double yaw){
        limelight.updateRobotOrientation(angleUnit.toDegrees(yaw));
    }



    @Override
    public List<QQTest> getTests() {return Collections.singletonList(new TestLimelight("Limelight attached ", limelight));}
}

