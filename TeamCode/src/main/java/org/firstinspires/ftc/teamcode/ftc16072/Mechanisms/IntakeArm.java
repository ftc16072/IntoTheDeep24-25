package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestTwoServos;

import java.util.Collections;
import java.util.List;

@Config
public class IntakeArm extends QQMechanism {
    private static final double MAX_SERVO_POS = 1;
    public static double MANUAL_CHANGE_AMOUNT = 0.02;
    public static double ARM_DROP_POSITION = 0.05;
    public static double ARM_AUTO_DROP_POSITION = 0.09;
    public static double ARM_INTAKE_POSITION = 0.58;
    public static double AUTONOMOUS_GRAB_POSITION = 0.7;
    Servo leftArmServo;
    Servo rightArmServo;

    @Override
    public void init(HardwareMap hwMap) {
        leftArmServo = hwMap.get(Servo.class, "left_arm_servo");
        rightArmServo = hwMap.get(Servo.class, "right_arm_servo");
        rightArmServo.setDirection(Servo.Direction.REVERSE);
    }
    protected void goToPos(double position){
        leftArmServo.setPosition(position);
        rightArmServo.setPosition(position);
    }
    public void goToDropPos(){
        goToPos(ARM_DROP_POSITION);
    }
    public void goToAutoDropPos(){
        goToPos(ARM_AUTO_DROP_POSITION);
    }
    public void goToIntake(){
        goToPos(ARM_INTAKE_POSITION);
    }
    public void goToAutoGrab(){
        goToPos(AUTONOMOUS_GRAB_POSITION);
    }


    public void rotateArmLeft(){
        rotateArm(MANUAL_CHANGE_AMOUNT);
    }
    public void  rotateArmRight(){
        rotateArm(-MANUAL_CHANGE_AMOUNT);
    }
    public void moveArmUp(){
        leftArmServo.setPosition(leftArmServo.getPosition()+MANUAL_CHANGE_AMOUNT);
        rightArmServo.setPosition(rightArmServo.getPosition()+MANUAL_CHANGE_AMOUNT);}
    public void moveArmDown(){
        leftArmServo.setPosition(leftArmServo.getPosition()-MANUAL_CHANGE_AMOUNT);
        rightArmServo.setPosition(rightArmServo.getPosition()-MANUAL_CHANGE_AMOUNT);}
    private void rotateArm(double manualChangeAmount){
        double leftPos = leftArmServo.getPosition();
        double rightPos = rightArmServo.getPosition();

        leftPos = leftPos - manualChangeAmount;
        rightPos = rightPos + manualChangeAmount;

        if(((leftPos <= MAX_SERVO_POS) && (leftPos >= 0)) &&
                ((rightPos <= MAX_SERVO_POS) && (rightPos >= 0))){
            leftArmServo.setPosition(leftPos);
            rightArmServo.setPosition(rightPos);
        }
    }
    public void rotateArmByDegrees(double theta){
        double CHANGE_AMOUNT_PER_DEGREE = 0.1; //TODO:make real
        rotateArm(theta*CHANGE_AMOUNT_PER_DEGREE);
    }



    @Override
    public List<QQTest> getTests() {
        return Collections.singletonList(
                new TestTwoServos("arm_pos", ARM_INTAKE_POSITION, ARM_DROP_POSITION, leftArmServo, rightArmServo));
    }
}