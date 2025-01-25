package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;


import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestGyro;

import java.util.Collections;
import java.util.List;

public class ControlHub extends QQMechanism{
    IMU gyro;
    LynxModule controlHub;
    LynxModule expansionHub;
    @Override
    public void init(HardwareMap hwMap) {
        controlHub = (LynxModule) hwMap.get(LynxModule.class, "Control Hub");
        gyro = hwMap.get(IMU.class,"imu");
        expansionHub = (LynxModule) hwMap.get(LynxModule.class, "Expansion Hub 2");

        RevHubOrientationOnRobot orientationOnRobot =
                new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP);

        gyro.initialize(new IMU.Parameters(orientationOnRobot));
    }
    public double getControlHubCurrent(CurrentUnit currentUnit){
        return controlHub.getCurrent(currentUnit);
    }
    public double getExpansionHubCurrent(CurrentUnit currentUnit){
        return expansionHub.getCurrent(currentUnit);
    }

    public double getYaw(AngleUnit angleUnit){
        return gyro.getRobotYawPitchRollAngles().getYaw(angleUnit);
    }

    public void resetGyro(){
        gyro.resetYaw();
    }

    @Override
    public List<QQTest> getTests() {
        return Collections.singletonList(new TestGyro("Gyro", gyro));
    }

}