/* Tree TwoSpecimenTree for 16072 generated by http://behaviortrees.ftcteams.com */
        package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Trees;

import com.ftcteams.behaviortrees.Node;
import com.ftcteams.behaviortrees.Failover;
import com.ftcteams.behaviortrees.Parallel;
import com.ftcteams.behaviortrees.Sequence;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.ArmToIntake;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.FirstScore;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.DriveToIntakePosition;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.Park;



public class FourSpecimenTree {
    public static final int TIMEOUT_SECONDS = 10;

    public static Node root(){
        return new Failover(
                new Sequence(
                        new FirstScore(TIMEOUT_SECONDS),
                        new Parallel(2,
                                new DriveToIntakePosition(TIMEOUT_SECONDS),
                                new ArmToIntake(TIMEOUT_SECONDS)),
                        Cycle.root(10),
                        Cycle.root(10),
                        Cycle.root(10),
                        Cycle.root(10),
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