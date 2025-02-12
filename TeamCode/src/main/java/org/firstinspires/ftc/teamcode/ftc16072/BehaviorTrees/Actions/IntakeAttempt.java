package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions;

import com.ftcteams.behaviortrees.DebugTree;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.QQTimeoutNode;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class IntakeAttempt extends QQTimeoutNode {
    public static final double INTAKE_SPEED = -0.3;
    State lastStatus = State.RUNNING;

    public IntakeAttempt(double seconds) {
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
            opMode.robot.mecanumDrive.move(INTAKE_SPEED,0,0);
            if (opMode.robot.scoringClaw.isClawClosed()){
                opMode.robot.mecanumDrive.stop();
                opMode.robot.intakeClaw.open();;
                lastStatus = State.SUCCESS;
                return State.SUCCESS;

            }
            return State.RUNNING;
        }
    }
}

