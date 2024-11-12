package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestTwoServos;

import java.util.Arrays;
import java.util.List;

@Config
public class IntakeArm extends QQMechanism {
    //change values later
    public static double ARM_DROP_POSITION = 0.7;
    public static double ARM_INTAKE_POSITION = 0.9;
    public static double SEARCHING_POSITION = 0.85;
    public static double TRANSFER_POSITION = 0.8;
    Servo leftArmServo;
    Servo rightArmServo;

    @Override
    public void init(HardwareMap hwMap) {
        leftArmServo = hwMap.get(Servo.class, "left_arm_servo");
        rightArmServo = hwMap.get(Servo.class, "right_arm_servo");
        rightArmServo.setDirection(Servo.Direction.REVERSE);
    }
    public void goToPos(double position){
        leftArmServo.setPosition(position);
        rightArmServo.setPosition(position);

    }
    public void goToDropPos(){
        leftArmServo.setPosition(ARM_DROP_POSITION);
        rightArmServo.setPosition(ARM_DROP_POSITION);
    }
    public void goToIntake(){
        leftArmServo.setPosition(ARM_INTAKE_POSITION);
        rightArmServo.setPosition(ARM_INTAKE_POSITION);
    }
    public void searching(){
        leftArmServo.setPosition(SEARCHING_POSITION);
        rightArmServo.setPosition(SEARCHING_POSITION);
    }
    public void transfer(){
        leftArmServo.setPosition(TRANSFER_POSITION);
        rightArmServo.setPosition(TRANSFER_POSITION);
    }



    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestTwoServos("arm_pos", ARM_DROP_POSITION, ARM_INTAKE_POSITION, leftArmServo, rightArmServo));

    }
}

