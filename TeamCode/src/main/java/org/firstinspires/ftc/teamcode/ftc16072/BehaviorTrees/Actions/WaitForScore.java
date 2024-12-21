package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions;

import com.ftcteams.behaviortrees.DebugTree;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.QQTimeoutNode;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class WaitForScore extends QQTimeoutNode {
    public WaitForScore(double seconds) {
        super(seconds);
    }

    @Override
    public State tick(DebugTree debug, QQOpMode opMode) {
        if (hasTimedOut()){
            opMode.robot.scoreArm.setNotScoring();
            return State.FAILURE;
        }else if (opMode.robot.scoringClaw.isScoreSwitchPressed()){
            return State.SUCCESS;
        }
        return State.RUNNING;
    }
}
