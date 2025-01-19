package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions;

import com.ftcteams.behaviortrees.DebugTree;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.QQTimeoutNode;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class WaitForPlace extends QQTimeoutNode {
    public WaitForPlace(double seconds) {super(seconds);}
    State lastStatus = State.RUNNING;
    @Override
    public State tick(DebugTree debug, QQOpMode opMode) {
        if (lastStatus != State.RUNNING) {
            return lastStatus;
        }else {
            if (hasTimedOut()) {
                opMode.robot.scoreArm.setNotScoring();
                lastStatus = State.FAILURE;
                return State.FAILURE;
            } else if (opMode.robot.scoringClaw.isScoreSwitchPressed() || (opMode.robot.scoreArm.isStalling())){
                opMode.robot.scoringClaw.open();
                lastStatus = State.SUCCESS;
                return State.SUCCESS;
            }
            }
        return State.RUNNING;
        }
    }

