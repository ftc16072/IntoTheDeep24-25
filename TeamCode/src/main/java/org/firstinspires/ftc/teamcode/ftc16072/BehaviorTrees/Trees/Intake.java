
package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Trees;

import com.ftcteams.behaviortrees.Failover;
import com.ftcteams.behaviortrees.Node;
import com.ftcteams.behaviortrees.Sequence;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.IntakeAttempt;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.MoveForwardForTime;


public class Intake {
    public static Node root(){
        final double INTAKE_TIMEOUT_SECONDS = 1.5;
        final double MOVEMENT_TIMEOUT_SECONDS = 5;
        return new Failover(
                new IntakeAttempt(INTAKE_TIMEOUT_SECONDS),
                new Sequence(
                        new MoveForwardForTime(0.25,-.5),
                        new IntakeAttempt(INTAKE_TIMEOUT_SECONDS)),
                new Sequence(
                        new MoveForwardForTime(0.25,-.5),
                        new IntakeAttempt(INTAKE_TIMEOUT_SECONDS)),
                new Sequence(
                        new MoveForwardForTime(0.25,-.5),
                        new IntakeAttempt(INTAKE_TIMEOUT_SECONDS)),
                new Sequence(
                        new MoveForwardForTime(0.25,-.5),
                        new IntakeAttempt(INTAKE_TIMEOUT_SECONDS)));
    }
}

/* TREE
?
|  [IntakeAttempt]
|  ->
|  |  [ReadyForIntake]
|  |  [IntakeAttempt]
 */