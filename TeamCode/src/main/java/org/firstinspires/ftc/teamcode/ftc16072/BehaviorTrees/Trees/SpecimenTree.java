/* Tree TwoSpecimenTree for 16072 generated by http://behaviortrees.ftcteams.com */
        package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Trees;

import com.ftcteams.behaviortrees.Failover;
import com.ftcteams.behaviortrees.Node;
import com.ftcteams.behaviortrees.Parallel;
import com.ftcteams.behaviortrees.Sequence;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.ArmToIntake;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.DriveToFirstSample;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.DriveToIntakePosition;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.FirstScore;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.GetReadyToPushSamples;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.MoveForwardForTime;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.MoveRightForTime;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.Park;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.PushFirstSample;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.PushSamplesIn;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.PushSecondSample;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.PushThirdSample;
import org.firstinspires.ftc.teamcode.ftc16072.Tests.TestTwoMotors;


public class SpecimenTree {
    public static final int TIMEOUT_SECONDS = 8;

    public static Node root(){
        return new Failover(
                new Sequence(
                        new FirstScore(TIMEOUT_SECONDS),
                        new Parallel(2,
                            new ArmToIntake(TIMEOUT_SECONDS),
                            new GetReadyToPushSamples(TIMEOUT_SECONDS)
                        ),
                        new DriveToFirstSample(TIMEOUT_SECONDS),
                        new PushFirstSample(TIMEOUT_SECONDS),
                        new PushSamplesIn(TIMEOUT_SECONDS),
                        new PushFirstSample(TIMEOUT_SECONDS),
                        new PushSecondSample(TIMEOUT_SECONDS),
                        new MoveForwardForTime(0.5,1),
                       /* new PushSecondSample(TIMEOUT_SECONDS),
                        new MoveRightForTime(.2,1), //square on wall
                        new PushSamplesIn(TIMEOUT_SECONDS),*/
                        Cycle.root(),
                        Cycle.root(),
                        Cycle.root(),
                        //Cycle.root(),
                        new Parallel(2,
                                new Park(TIMEOUT_SECONDS),
                                new ArmToIntake(TIMEOUT_SECONDS))
                ),
                new Parallel(2,
                        new Park(TIMEOUT_SECONDS),
                        new ArmToIntake(TIMEOUT_SECONDS)));
    }
}

/* TREE
?
|  ->
|  |  [FisrtScore]
|  |  [DriveToIntakePosition]
|  |  [Intake]
|  |  [DrivetoScorePosition]
|  |  [StandardScore]
|  |  [Park]
|  [Park]
 */