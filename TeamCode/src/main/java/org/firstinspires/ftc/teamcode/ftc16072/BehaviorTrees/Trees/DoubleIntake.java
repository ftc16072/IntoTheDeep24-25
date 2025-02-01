
package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Trees;

import com.ftcteams.behaviortrees.Failover;
import com.ftcteams.behaviortrees.Node;
import com.ftcteams.behaviortrees.Parallel;
import com.ftcteams.behaviortrees.Sequence;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.ArmToIntake;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.Delay;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.IntakeArmGrab;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.IntakeArmIn;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.IntakeAttempt;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.IntakeClawClose;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.ReadyToIntakeOne;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.SlidesIn;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.StrafeToSample;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.WristToIntake;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.WristToTransfer;


public class DoubleIntake {
    public static Node root(){
        final double INTAKE_TIMEOUT_SECONDS = 1.5;
        final double MOVEMENT_TIMEOUT_SECONDS = 5;
        return new Failover(
            new Sequence(
                    new StrafeToSample(5),
                    new Delay(0.2),
                    new IntakeArmGrab(0.5),
                    new IntakeClawClose(0.5),
                    new Parallel(3,
                            new WristToTransfer(0.5),
                            new IntakeArmIn(1),
                            new SlidesIn(MOVEMENT_TIMEOUT_SECONDS)),
                    new ArmToIntake(1),
                    new Failover(
                        new IntakeAttempt(INTAKE_TIMEOUT_SECONDS),
                        new Sequence(
                                new ReadyToIntakeOne(MOVEMENT_TIMEOUT_SECONDS),
                                new IntakeAttempt(INTAKE_TIMEOUT_SECONDS)))),
                new Parallel(3,
                        new WristToTransfer(0.5),
                        new IntakeArmIn(1),
                        new SlidesIn(MOVEMENT_TIMEOUT_SECONDS)),
                new ArmToIntake(1),
                new Failover(
                        new IntakeAttempt(INTAKE_TIMEOUT_SECONDS),
                        new Sequence(
                                new ReadyToIntakeOne(MOVEMENT_TIMEOUT_SECONDS),
                                new IntakeAttempt(INTAKE_TIMEOUT_SECONDS))));
    }
}

/* TREE
?
|  [IntakeAttempt]
|  ->
|  |  [ReadyForIntake]
|  |  [IntakeAttempt]
 */