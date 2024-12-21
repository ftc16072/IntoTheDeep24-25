package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.ftcteams.behaviortrees.DebugTree;
import com.ftcteams.behaviortrees.Node;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.QQTimeoutNode;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Trees.PreloadAuto;

@Autonomous
public class PreloadAutoBehaviorTree extends QQOpMode{
    boolean clawWasClosed;
    boolean done;
    Node root = PreloadAuto.root();
    DebugTree debugTree = new DebugTree();
    @Override
    public void init(){
        super.init();
        robot.scoreArm.goToScoring();
        robot.scoringClaw.close();
        robot.otos.setOtosPosition(7,-61.5,0);
    }

    @Override
    public void init_loop() {
        robot.scoreArm.update(telemetry);
    }

    public void loop() {
        super.loop();
        if (robot.scoringClaw.isClawClosed() && !clawWasClosed) {
            robot.intakeClaw.open();
            robot.scoreArm.goToPlace();

        }
        if (robot.scoringClaw.isScoreSwitchPressed()){
            robot.scoreArm.setNotScoring();
        }
        if(!done){
            Node.State state = root.tick(debugTree, this);
            telemetry.addData("BT",debugTree);
            if(state == Node.State.SUCCESS){
                done = true;
            }
        }
        clawWasClosed = robot.scoringClaw.isClawClosed();
    }
}

