package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions;

import com.ftcteams.behaviortrees.DebugTree;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.QQTimeoutNode;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class IntakeClawOpen extends QQTimeoutNode {
    public IntakeClawOpen(double seconds) {
        super(seconds);
    }
    State lastStatus = State.RUNNING;

    @Override
    public State tick(DebugTree debug, QQOpMode opMode) {
        if (lastStatus != State.RUNNING) {
            return lastStatus;
        }else {
            opMode.robot.intakeClaw.open();
            if (hasTimedOut()) {
                opMode.robot.scoreArm.setNotScoring();
                lastStatus = State.SUCCESS;
                return State.SUCCESS;
            }
            return State.RUNNING;
        }
    }
}
