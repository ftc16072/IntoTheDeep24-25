package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestServo;

import java.util.Arrays;
import java.util.List;
@Config
public class Claw extends QQMechanism {
    public static double CLAW_CLOSE_POSITION = 0.3;
    public static double CLAW_OPEN_POSITION = 0;
    public static double WRIST_START_POSITION = 0;
    public static double WRIST_END_POSITION = 0.67;



    Servo clawServo;
    Servo wristServo;


    @Override
    public void init(HardwareMap hwMap) {
        clawServo = hwMap.get(Servo.class, "claw_servo");
        wristServo = hwMap.get(Servo.class, "wrist_servo");
    }


    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestServo("claw_movement", CLAW_OPEN_POSITION, CLAW_CLOSE_POSITION, clawServo),
                new TestServo("wrist_movement", WRIST_START_POSITION, WRIST_END_POSITION, wristServo)
        );

    }


    public void wristStart() {wristServo.setPosition(WRIST_START_POSITION);
    }
    public void wristEnd() {wristServo.setPosition(WRIST_END_POSITION);
    }

    public void open() {clawServo.setPosition(CLAW_OPEN_POSITION);
    }

    public void close() {clawServo.setPosition(CLAW_CLOSE_POSITION);}
    }