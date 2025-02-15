package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.ftcteams.behaviortrees.DebugTree;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class Inspection extends QQOpMode{
    boolean clawWasClosed;
    boolean test;
    boolean done;
    DebugTree debugTree = new DebugTree();
    ElapsedTime moveTimer = new ElapsedTime();
    double INIT_MOVE_TIME_SECONDS = 1;
    double INIT_MOVE_SPEED = -0.2;
    @Override
    public void init(){
        super.init();
        moveTimer.reset();
        robot.scoreArm.goToInit();
        robot.scoringClaw.close();
    }

    @Override
    public void init_loop() {
        robot.scoreArm.update(telemetry);

    }
    public void start(){
        super.start();
        robot.otos.setOtosPosition(7,-61.5,0);
    }

    public void loop() {
        super.loop();
        }
}


