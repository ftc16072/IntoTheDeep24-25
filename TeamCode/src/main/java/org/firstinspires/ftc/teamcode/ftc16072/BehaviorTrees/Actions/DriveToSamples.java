package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions;

import com.ftcteams.behaviortrees.DebugTree;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.QQTimeoutNode;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class DriveToSamples extends QQTimeoutNode {
    public DriveToSamples(double seconds) {
        super(seconds);
    }
    State lastStatus = State.RUNNING;

    @Override
    public State tick(DebugTree debug, QQOpMode opMode) {

        opMode.telemetry.addData("location",opMode.robot.otos.getOtosPosition());
        if (lastStatus != State.RUNNING){
            return lastStatus;
        }else{
            boolean isDoneDriving = opMode.nav.driveToPositionIN(55,-91,0);
            if (isDoneDriving) {
                lastStatus = State.SUCCESS;
                return State.SUCCESS;
            }
            if (hasTimedOut()) {
                lastStatus = State.FAILURE;
                return State.FAILURE;
            }
            return State.RUNNING;
        }
    }
}

