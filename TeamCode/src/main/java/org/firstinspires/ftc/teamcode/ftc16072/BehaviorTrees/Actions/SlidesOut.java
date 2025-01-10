package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions;

import com.ftcteams.behaviortrees.DebugTree;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.QQTimeoutNode;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class SlidesOut extends QQTimeoutNode {
    public SlidesOut(double seconds) {
        super(seconds);
    }
    State lastStatus = State.RUNNING;

    @Override
    public State tick(DebugTree debug, QQOpMode opMode) {
        if (lastStatus != State.RUNNING) {
            return lastStatus;
        }else {
            opMode.robot.intakeSlides.halfExtension();
            opMode.robot.intakeArm.goToIntake();
            if (hasTimedOut()) {
                lastStatus = State.FAILURE;
                return State.FAILURE;
            } else if (opMode.robot.intakeSlides.getIsWithinTolerence()) {
                lastStatus = State.SUCCESS;
                return State.SUCCESS;
            }
            return State.RUNNING;
        }
    }
}
