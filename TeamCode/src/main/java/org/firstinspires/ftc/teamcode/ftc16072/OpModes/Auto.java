package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import android.util.Log;

import com.ftcteams.behaviortrees.DebugTree;
import com.ftcteams.behaviortrees.Node;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Trees.SpecimenTree;

@Autonomous
public class Auto extends QQOpMode{
    boolean clawWasClosed;
    boolean test;
    boolean done;
    Node root = SpecimenTree.root();
    DebugTree debugTree = new DebugTree();
    ElapsedTime moveTimer = new ElapsedTime();
    double INIT_MOVE_TIME_SECONDS = 1;
    double INIT_MOVE_SPEED = -0.2;
    boolean switchWasPressed;
    @Override
    public void init(){
        robot.scoringClaw.resetHasColor();
        super.init();
        moveTimer.reset();
        robot.scoreArm.goToInit();
        robot.scoringClaw.close();
        robot.intakeArm.goToDropPos();
    }

    @Override
    public void init_loop() {
        if (moveTimer.seconds() < INIT_MOVE_TIME_SECONDS){
            robot.mecanumDrive.move(INIT_MOVE_SPEED,0,0);
        }else {
            robot.mecanumDrive.stop();
            robot.controlHub.resetGyro();
        }
        robot.scoreArm.update(telemetry);

    }
    public void start(){
        super.start();
        robot.otos.setOtosPosition(10,32,0);
    }

    public void loop() {
        super.loop();
        double degreesYaw = robot.controlHub.getYaw(AngleUnit.DEGREES);
        if (isAllianceRed){
            degreesYaw = AngleUnit.normalizeDegrees(degreesYaw + 180);
        }
        robot.limelight.setYaw(AngleUnit.DEGREES, degreesYaw);
        if (robot.scoringClaw.isClawClosed() && !clawWasClosed) {
            robot.intakeClaw.open();
            robot.scoreArm.goToPlace();
            test = true;
        }
        if(robot.scoreArm.isStalling()){
            robot.scoringClaw.open();
        }
        if (robot.scoringClaw.isClawOpen()){
            test = false;
        }
        if (robot.scoringClaw.isScoreSwitchPressed()){
            robot.scoreArm.setNotScoring();
        }
        if (robot.scoreArm.isLimitSwitchPressed() && !switchWasPressed){
            robot.scoringClaw.open();
        }
        if(!done){
 //           debugTree.reset();
            Node.State state = root.tick(debugTree, this);
            if(state == Node.State.SUCCESS){
                done = true;
            }
//            Log.d("QQ", "DT:" + debugTree.toString());
        }
        clawWasClosed = robot.scoringClaw.isClawClosed();
        switchWasPressed = robot.scoreArm.isLimitSwitchPressed();
    }
}


