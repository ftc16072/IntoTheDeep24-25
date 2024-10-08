package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions;

import com.ftcteams.behaviortrees.DebugTree;
import com.ftcteams.behaviortrees.Node;

import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.Util.Navigation;

abstract public class QQNode extends Node {
    @Override
    public State tick(DebugTree debug, Object obj) {
        return tick(debug, (QQOpMode)obj);
    }

    abstract public State tick(DebugTree debug, QQOpMode opMode);
}
