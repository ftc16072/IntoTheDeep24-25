package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestColorRangeSensor;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestServo;

import java.util.Arrays;
import java.util.List;

@Config
public class ScoringClaw extends QQMechanism {
    public static final int GRABBABLE_DISTANCE_CM = 3;
    public static double CLAW_CLOSE_POSITION = 0.3;
    public static double CLAW_OPEN_POSITION = 0;
    public static double WRIST_START_POSITION = 0;
    public static double WRIST_END_POSITION = 0.67;


    ElapsedTime openTimer = new ElapsedTime();
    Servo clawServo;
    Servo wristServo;
    ColorRangeSensor colorSensor;

    @Override
    public void init(HardwareMap hwMap) {
        clawServo = hwMap.get(Servo.class, "claw_servo");
        wristServo = hwMap.get(Servo.class, "wrist_servo");
        colorSensor = hwMap.get(ColorRangeSensor.class, "scoreclaw_color");
        open();
    }


    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestServo("claw_movement", CLAW_OPEN_POSITION, CLAW_CLOSE_POSITION, clawServo),
                new TestServo("wrist_movement", WRIST_START_POSITION, WRIST_END_POSITION, wristServo),
                new TestColorRangeSensor("scoreclaw_color", colorSensor)
        );

    }

    public boolean isClawOpen() {
        if ((clawServo.getPosition() == CLAW_OPEN_POSITION) &&
                (openTimer.seconds()>0.5))
            return true;

        return false;
    }

    public boolean isBlockGrabbable() {
        if (colorSensor.getDistance(DistanceUnit.CM) < GRABBABLE_DISTANCE_CM) {
            return true;
        }
        return false;
    }

    public void wristStart() {
        wristServo.setPosition(WRIST_START_POSITION);
    }

    public void wristEnd() {
        wristServo.setPosition(WRIST_END_POSITION);
    }

    public void open() {
        clawServo.setPosition(CLAW_OPEN_POSITION);
        openTimer.reset();

    }

    public void close() {
        clawServo.setPosition(CLAW_CLOSE_POSITION);
    }

    @Override
    public void update(Telemetry telemetry) {
        telemetry.addData("Distance", colorSensor.getDistance(DistanceUnit.CM));
        if (isClawOpen() && isBlockGrabbable()) {
            telemetry.addData("Auto Close", true);
            close();
        }else{
            telemetry.addData("Auto Close", false);

        }
    }
}