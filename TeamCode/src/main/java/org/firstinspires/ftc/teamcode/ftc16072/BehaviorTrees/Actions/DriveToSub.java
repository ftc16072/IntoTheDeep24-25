package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions;

import com.ftcteams.behaviortrees.DebugTree;
import com.ftcteams.behaviortrees.Node;

import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class DriveToSub extends QQNode {
    @Override
    public State tick(DebugTree debug, QQOpMode opMode) {
        boolean done = opMode.nav.driveToPositionIN(0,48,0);
        if(done){
            return State.SUCCESS;
        }
        return State.RUNNING;
    }
}
