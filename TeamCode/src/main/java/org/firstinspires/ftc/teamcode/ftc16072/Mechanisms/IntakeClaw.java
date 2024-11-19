package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestServo;

import java.util.Arrays;
import java.util.List;

@Config
public class IntakeClaw extends QQMechanism {
    public static double CLAW_CLOSE_POSITION = 1;
    public static double CLAW_OPEN_POSITION = 0.8;
    public static double WRIST_FLAT_POSITION = 0.5;
    public static double WRIST_START_POSITION = 0.15;
    public static double WRIST_END_POSITION = 0.85;

    Servo clawServo;
    Servo wristServo;
    double wristServoPos;

    @Override
    public void init(HardwareMap hwMap) {
        clawServo = hwMap.get(Servo.class, "intake_claw_servo");
        wristServo = hwMap.get(Servo.class, "intake_wrist_servo");
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestServo("claw_movement", CLAW_OPEN_POSITION, CLAW_CLOSE_POSITION, clawServo),
                new TestServo("wrist_movement", WRIST_START_POSITION, WRIST_END_POSITION, wristServo)
        );
    }


    public void open() {
        clawServo.setPosition(CLAW_OPEN_POSITION);
    }

    public void close() {
        clawServo.setPosition(CLAW_CLOSE_POSITION);
    }

    public void wristFlat(){
        wristServoPos = WRIST_FLAT_POSITION;
    }
    public void adjustWrist(double manualChangeAmount){
        wristServoPos += manualChangeAmount;
        wristServoPos = Math.min(WRIST_END_POSITION,wristServoPos);
        wristServoPos = Math.max(WRIST_START_POSITION,wristServoPos);
    }

    @Override
    public void update(Telemetry telemetry){
        wristServo.setPosition(wristServoPos);
    }

}

