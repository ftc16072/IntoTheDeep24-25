package org.firstinspires.ftc.teamcode.ftc16072;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.Arm;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.ControlHub;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.DoubleReverse4Bar;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.MecanumDrive;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.OpticalTrackingOdometrySensor;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.QQMechanism;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.Slides;

import java.util.Arrays;
import java.util.List;


public class Robot {
    public ControlHub controlHub;
    public MecanumDrive mecanumDrive;
    public OpticalTrackingOdometrySensor otos;
    public Claw claw;
    public DoubleReverse4Bar doubleReverse4Bar;
    public Slides slides;
    public Arm arm;
    List<QQMechanism> mechanisms;

    public Robot() {
        mecanumDrive = new MecanumDrive();
        controlHub = new ControlHub();
        otos = new OpticalTrackingOdometrySensor();
        claw = new Claw();
        doubleReverse4Bar = new DoubleReverse4Bar();
        slides = new Slides();
        arm = new Arm();

        mechanisms = Arrays.asList(
                controlHub,
                mecanumDrive,
                otos,
                claw,
                slides,
                claw,
                doubleReverse4Bar,
                arm);
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
