package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions;

import com.ftcteams.behaviortrees.DebugTree;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.QQTimeoutNode;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class FirstScore extends QQTimeoutNode {
    public static final double FORWARD_SPEED = 0.4;
    State lastStatus = State.RUNNING;

    public FirstScore(double seconds) {
        super(seconds);
    }

    @Override
    public State tick(DebugTree debug, QQOpMode opMode) {
        if (lastStatus != State.RUNNING){
            return lastStatus;
        }else{
            if(hasTimedOut()){
                opMode.robot.mecanumDrive.stop();
                lastStatus = State.FAILURE;
                return State.FAILURE;
            }
            opMode.robot.mecanumDrive.move(FORWARD_SPEED,0,0);
            if (opMode.robot.scoringClaw.isClawOpen()){
                opMode.robot.mecanumDrive.stop();
                lastStatus = State.SUCCESS;
                return State.SUCCESS;

            }
            return State.RUNNING;
        }
    }
}

