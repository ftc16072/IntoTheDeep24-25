package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestServo;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestTwoServos;

import java.util.Arrays;
import java.util.List;

@Config
public class IntakeClaw extends QQMechanism {
    public static final double MAX_SERVO_POS = 1;
    public static double CLAW_CLOSE_POSITION = 1;
    public static double CLAW_OPEN_POSITION = 0.8;
    public static double WRIST_START_POSITION = 0.0;
    public static double WRIST_TRANSFER_POSITION = 0.65;

    ElapsedTime openTimer = new ElapsedTime();
    ElapsedTime closedTimer = new ElapsedTime();
    Servo clawServo;
    Servo leftWristServo;
    Servo rightWristServo;
    double CLOSED_TIME = 0.5;
    double OPEN_TIME = 0.5;
    double leftWristServoPos;
    double rightWristServoPos;


    @Override
    public void init(HardwareMap hwMap) {
        clawServo = hwMap.get(Servo.class, "intake_claw_servo");
        leftWristServo = hwMap.get(Servo.class, "left_intake_wrist");
        rightWristServo = hwMap.get(Servo.class,"right_intake_wrist");
        leftWristServo.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestServo("claw_movement", CLAW_OPEN_POSITION, CLAW_CLOSE_POSITION, clawServo),
                new TestTwoServos("wrist_movement", WRIST_START_POSITION, WRIST_TRANSFER_POSITION, leftWristServo,rightWristServo)
        );
    }
    public void wristTransfer(){
        leftWristServoPos = WRIST_START_POSITION;
        rightWristServoPos = WRIST_START_POSITION;
    }
    public void wristIntake(){
        leftWristServoPos = WRIST_TRANSFER_POSITION;
        rightWristServoPos = WRIST_TRANSFER_POSITION;
    }
    public void open() {
        openTimer.reset();
        clawServo.setPosition(CLAW_OPEN_POSITION);
    }

    public void close() {
        closedTimer.reset();
        clawServo.setPosition(CLAW_CLOSE_POSITION);
    }

    public void adjustWrist(double manualChangeAmount){
        if (leftWristServoPos <= MAX_SERVO_POS && rightWristServoPos <= MAX_SERVO_POS){
            leftWristServoPos += manualChangeAmount;
            rightWristServoPos -= manualChangeAmount;
        }
    }    public boolean isClawOpen() {
        if ((clawServo.getPosition() == CLAW_OPEN_POSITION) &&
                (openTimer.seconds()> OPEN_TIME))
            return true;

        return false;
    }
    public boolean isClawClosed() {
        if ((clawServo.getPosition() == CLAW_CLOSE_POSITION) &&
                (closedTimer.seconds()> CLOSED_TIME))
            return true;

        return false;
    }


    @Override
    public void update(Telemetry telemetry){
        rightWristServo.setPosition(rightWristServoPos);
        leftWristServo.setPosition(leftWristServoPos);
    }

}

