package org.firstinspires.ftc.teamcode.ftc16072.Mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestServo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Config
public class Slides extends QQMechanism {
    public static double SLIDE_BACK_POSITION = 0.125;
    public static double SLIDE_FRONT_POSITION = 1.0;
    public static double SLIDE_MIDDLE_POSITION = 0.5;

    Servo slideServo;

    @Override
    public void init(HardwareMap hwMap) {
        slideServo = hwMap.get(Servo.class, "slide_movement");
    }

    public void goToPos(double position){
        slideServo.setPosition(position);
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
            new TestServo("slide_back", SLIDE_BACK_POSITION, SLIDE_MIDDLE_POSITION, slideServo),
            new TestServo("slide_front", SLIDE_FRONT_POSITION, SLIDE_MIDDLE_POSITION, slideServo));
    }
}
