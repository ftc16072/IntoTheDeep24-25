package org.firstinspires.ftc.teamcode.ftc16072;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.ControlHub;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.MecanumDrive;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.OpticalTrackingOdometrySensor;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.QQMechanism;

import java.util.Arrays;
import java.util.List;


public class Robot {
    public ControlHub controlHub;
    public MecanumDrive mecanumDrive;
    public OpticalTrackingOdometrySensor otos;
    List<QQMechanism> mechanisms;

    public Robot() {
        mecanumDrive = new MecanumDrive();
        controlHub = new ControlHub();
        otos = new OpticalTrackingOdometrySensor();

        mechanisms = Arrays.asList(
                controlHub,
                mecanumDrive,
                otos);
    }
    public void init(HardwareMap hwMap) {
        for (QQMechanism mechanism : mechanisms) {
            mechanism.init(hwMap);
        }
    }
    public void update(){
        for(QQMechanism mechanism: mechanisms){
            mechanism.update();
        }
    }

    public List<QQMechanism> getMechanisms() {
        return mechanisms;
    }
}
