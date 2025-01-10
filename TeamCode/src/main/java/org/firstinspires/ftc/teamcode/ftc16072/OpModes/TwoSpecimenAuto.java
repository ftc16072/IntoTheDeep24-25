package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.ftcteams.behaviortrees.DebugTree;
import com.ftcteams.behaviortrees.Node;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Trees.PreloadAuto;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Trees.TwoSpecimenAutoTree;

@Autonomous
public class TwoSpecimenAuto extends QQOpMode{
    boolean clawWasClosed;
    boolean test;
    boolean done;
    Node root = TwoSpecimenAutoTree.root();
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
        if (moveTimer.seconds() < INIT_MOVE_TIME_SECONDS){
            robot.mecanumDrive.move(INIT_MOVE_SPEED,0,0);
        }else {
            robot.mecanumDrive.stop();
            robot.controlHub.resetGyro();
        }
        robot.scoreArm.update(telemetry);

    }
    public void start(){
        robot.otos.setOtosPosition(7,-61.5,0);
    }

    public void loop() {
        super.loop();
        telemetry.addData("clawClosed",robot.scoringClaw.isClawClosed());
        telemetry.addData("clawWasClosed",clawWasClosed);
        telemetry.addData("test",test);
        if (robot.scoringClaw.isClawClosed() && !clawWasClosed) {
            robot.intakeClaw.open();
            robot.scoreArm.goToPlace();
            test = true;
        }
        if (robot.scoringClaw.isClawOpen()){
            test = false;
        }
        if (robot.scoringClaw.isScoreSwitchPressed()){
            robot.scoreArm.setNotScoring();
        }
        if(!done){
            Node.State state = root.tick(debugTree, this);
            //telemetry.addData("BT",debugTree);
            if(state == Node.State.SUCCESS){
                done = true;
            }
        }
        clawWasClosed = robot.scoringClaw.isClawClosed();
    }
}


