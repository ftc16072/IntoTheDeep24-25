package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions;

import com.ftcteams.behaviortrees.DebugTree;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.QQTimeoutNode;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class Delay extends QQTimeoutNode {
    public Delay(double seconds) {
        super(seconds);
    }

    @Override
    public State tick(DebugTree debug, QQOpMode opMode) {
        if (hasTimedOut()){
            return State.SUCCESS;
        }
        return State.RUNNING;
    }
}
