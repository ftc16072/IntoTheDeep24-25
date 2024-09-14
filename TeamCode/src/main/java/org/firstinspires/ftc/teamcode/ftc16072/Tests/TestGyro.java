package org.firstinspires.ftc.teamcode.ftc16072.Tests;

import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestGyro extends QQTest{

    IMU gyro;
    public TestGyro(String name, IMU gyro){
        super(name);
        this.gyro=gyro;
    }

    @Override
    public void run(boolean on, Telemetry telemetry) {
        telemetry.addData(getName(), gyro.getRobotYawPitchRollAngles());
        if(on){
            gyro.resetYaw();
        }

    }
}
