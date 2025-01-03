package org.firstinspires.ftc.teamcode.ftc16072;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.ScoringClaw;
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
    public ScoringClaw scoringClaw;
    public IntakeArm intakeArm;
    //public DoubleReverse4Bar doubleReverse4Bar;
   // public Slides slides;
    public IntakeClaw intakeClaw;


  
    public IntakeSlides intakeSlides;

    public ScoreArm scoreArm;

    boolean driveOnly;


    List<QQMechanism> mechanisms;

    public Robot() {
        mecanumDrive = new MecanumDrive();
        controlHub = new ControlHub();
        otos = new OpticalTrackingOdometrySensor();
        scoringClaw = new ScoringClaw();
        intakeSlides = new IntakeSlides();
        intakeArm = new IntakeArm();
        //doubleReverse4Bar = new DoubleReverse4Bar();
        //slides = new Slides();

        scoreArm = new ScoreArm();
        intakeClaw = new IntakeClaw();

        mechanisms = Arrays.asList(
                controlHub,
                mecanumDrive,
                otos,
                scoringClaw,
                intakeArm,
                intakeSlides,
                scoreArm,
                intakeClaw);

    }
    public void init(HardwareMap hwMap) {
        for (QQMechanism mechanism : mechanisms) {
            mechanism.init(hwMap);
        }
    }
    public void update(Telemetry telemetry){
        for(QQMechanism mechanism: mechanisms){
            mechanism.update(telemetry);
        }
    }

    public void makeDriveOnly(){
        mechanisms = Arrays.asList(
                controlHub,
                mecanumDrive);
    }

    public List<QQMechanism> getMechanisms() {
        return mechanisms;
    }
}
