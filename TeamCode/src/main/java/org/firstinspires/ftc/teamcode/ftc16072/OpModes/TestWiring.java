package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ftc16072.Mechanisms.QQMechanism;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.QQTest;

import java.util.List;

@TeleOp
public class TestWiring extends QQOpMode {

    int currentMechanism = 0;
    int currentTest = 0;
    List<QQMechanism> mechanismList;
    List<QQTest> testList;

    private boolean wasUp;
    private boolean wasDown;
    private boolean wasLeft;
    private boolean wasRight;


    @Override
    public void init() {
        super.init();
        mechanismList = robot.getMechanisms();
        telemetry.addLine("Press A to run");
    }

    @Override
    public void loop() {
        if(gamepad1.dpad_up && !wasUp){
            currentTest = 0;
            currentMechanism += 1;
            if(currentMechanism >= mechanismList.size()){
                currentMechanism = 0;
            }
        }

        if(gamepad1.dpad_down && !wasDown){
            currentTest = 0;
            currentMechanism -= 1;
            if(currentMechanism < 0 ){
                currentMechanism = mechanismList.size()-1;
            }
        }
        testList = mechanismList.get(currentMechanism).getTests();
        if(gamepad1.dpad_left && !wasLeft){
            currentTest -= 1;
            if(currentTest < 0){
                currentTest = testList.size()-1;
            }
        }
        if(gamepad1.dpad_right && !wasRight){
            currentTest += 1;
            if(currentTest>=testList.size()){
                currentTest = 0;
            }
        }
        wasDown = gamepad1.dpad_down;
        wasLeft = gamepad1.dpad_left;
        wasRight = gamepad1.dpad_right;
        wasUp = gamepad1.dpad_up;
        telemetry.addData("Mechanism", mechanismList.get(currentMechanism).getName());
        telemetry.addData("Test", testList.get(currentTest).getName());
        testList.get(currentTest).run(gamepad1.a, telemetry);
    }
}
