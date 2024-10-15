package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestServo;

import java.util.Collections;
import java.util.List;
@Config
public class Claw extends QQMechanism {
    public static double CLAW_OPEN_POSITION = 0.3;
    public static double CLAW_CLOSE_POSITION = 0;


    Servo clawServo;

    @Override
    public void init(HardwareMap hwMap) {
        clawServo = hwMap.get(Servo.class, "claw_servo");
    }

    @Override
    public List<QQTest> getTests() {
        return Collections.singletonList(
                new TestServo("claw_movement", CLAW_OPEN_POSITION, CLAW_CLOSE_POSITION, clawServo)
        );
    }

    public void open() {
        clawServo.setPosition(CLAW_OPEN_POSITION);
    }

    public void close() {
        clawServo.setPosition(CLAW_CLOSE_POSITION);}}


