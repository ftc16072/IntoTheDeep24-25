package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions;

import com.ftcteams.behaviortrees.DebugTree;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.QQTimeoutNode;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class Park extends QQTimeoutNode {
    public Park(double seconds) {
        super(seconds);
    }

    @Override
    public State tick(DebugTree debug, QQOpMode opMode) {
        boolean isDoneDriving = opMode.nav.driveToPositionIN(14,-109.5,0);
        opMode.telemetry.addData("location",opMode.robot.otos.getOtosPosition());
        if (isDoneDriving) {
            return State.SUCCESS;
        }
        if (hasTimedOut()) {
            return State.FAILURE;
        }
        return State.RUNNING;
    }
}
