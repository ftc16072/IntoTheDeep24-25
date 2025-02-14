package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

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

import java.util.Collections;
import java.util.List;

public class Limelight extends QQMechanism{
    private Limelight3A limelight;
    LLResult llResult;
    private Pose3D lastValidPos = new Pose3D(
            //arbitrary starting pose
            new Position(DistanceUnit.INCH, 0,0,0,0),
            new YawPitchRollAngles(AngleUnit.DEGREES, 0, 0, 0, 0));
    private Pose3D getBotPose() {
        llResult = limelight.getLatestResult();
        if((llResult != null) && llResult.isValid()){
            //lastValidPos = llResult.getBotpose_MT2();
            lastValidPos = llResult.getBotpose();
        }
        return lastValidPos;
    }
    @Override
    public void init(HardwareMap hwMap) {
        limelight = hwMap.get(Limelight3A.class, "limelight");
    }
    public boolean isAprilTagSeen(){
        return ((llResult != null) && llResult.isValid());
    }

    @Override
    public void update(Telemetry telemetry){
        super.update(telemetry);
        getBotPose();
        telemetry.addData("april tag seen", isAprilTagSeen());
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

