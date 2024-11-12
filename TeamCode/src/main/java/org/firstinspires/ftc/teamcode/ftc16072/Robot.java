package org.firstinspires.ftc.teamcode.ftc16072;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.Claw;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.ControlHub;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.IntakeArm;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.IntakeClaw;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.IntakeSlides;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.MecanumDrive;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.OpticalTrackingOdometrySensor;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.QQMechanism;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.ScoreArm;

import java.util.Arrays;
import java.util.List;


public class Robot {
    public ControlHub controlHub;
    public MecanumDrive mecanumDrive;
    public OpticalTrackingOdometrySensor otos;
    public Claw claw;
    public IntakeArm intakeArm;
    //public DoubleReverse4Bar doubleReverse4Bar;
   // public Slides slides;
    public IntakeClaw intakeClaw;


  
    public IntakeSlides intakeSlides;

    public ScoreArm scoreArm;


    List<QQMechanism> mechanisms;

    public Robot() {
        mecanumDrive = new MecanumDrive();
        controlHub = new ControlHub();
        otos = new OpticalTrackingOdometrySensor();
        claw = new Claw();
        intakeSlides = new IntakeSlides();
        intakeArm = new IntakeArm();
        //doubleReverse4Bar = new DoubleReverse4Bar();
        //slides = new Slides();

        scoreArm = new ScoreArm();

        mechanisms = Arrays.asList(
                controlHub,
                mecanumDrive,
                // otos,
                claw,
                intakeArm,
               // slides,
                //doubleReverse4Bar,
                intakeSlides,
                scoreArm);

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
