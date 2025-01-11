package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions;

import com.ftcteams.behaviortrees.DebugTree;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.QQTimeoutNode;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class IntakeClawClose extends QQTimeoutNode {
    public IntakeClawClose(double seconds) {
        super(seconds);
    }
    State lastStatus = State.RUNNING;

    @Override
    public State tick(DebugTree debug, QQOpMode opMode) {
        if (lastStatus != State.RUNNING) {
            return lastStatus;
        }else {
            opMode.robot.intakeClaw.close();
            if (hasTimedOut()) {
                opMode.robot.scoreArm.setNotScoring();
                lastStatus = State.SUCCESS;
                return State.SUCCESS;
            }
            return State.RUNNING;
        }
    }
}
