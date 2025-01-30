package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Trees;

/* Tree PreloadAuto for 16072 generated by http://behaviortrees.ftcteams.com */

import com.ftcteams.behaviortrees.Failover;
import com.ftcteams.behaviortrees.Node;
import com.ftcteams.behaviortrees.Parallel;
import com.ftcteams.behaviortrees.Sequence;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.ArmToIntake;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.ArmToScore;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.ArmToScored;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.BehindChamber;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.BehindChamberForSecondScore;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.BehindChamberForThirdScore;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.DriveToChamber;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.IntakeArmOut;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.Park;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.ReadyToIntakeOne;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.ReadyToIntakeTwo;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.SlidesOutToMiddle;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.WaitForClawOpen;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.Delay;


public class SpecimenCycleAutoTree {

    public static final int TIMEOUT_SECONDS = 10;

    public static Node root(){
        return new Failover(
                new Sequence(
                        new DriveToChamber(TIMEOUT_SECONDS),
                        new WaitForClawOpen(TIMEOUT_SECONDS),
                        new Parallel(1,
                                new IntakeArmOut(1),
                                new BehindChamber(TIMEOUT_SECONDS)
                                ),
                        new Parallel(2,
                                new Sequence(
                                        new Delay(0.5),
                                        new SlidesOutToMiddle(TIMEOUT_SECONDS)
                                        ),
                                new ReadyToIntakeOne(TIMEOUT_SECONDS)),
                        DoubleIntake.root(),
                        new Parallel(2,
                                new ReadyToIntakeOne(TIMEOUT_SECONDS),
                                new ArmToScore(TIMEOUT_SECONDS)),
                        new BehindChamberForSecondScore(TIMEOUT_SECONDS),
                        new DriveToChamber(TIMEOUT_SECONDS),
                        new WaitForClawOpen(TIMEOUT_SECONDS),
                        new BehindChamber(TIMEOUT_SECONDS),
                        new Parallel(2,
                                new ReadyToIntakeTwo(TIMEOUT_SECONDS),
                                new ArmToIntake(TIMEOUT_SECONDS)),
                        Intake.root(),
                        new Parallel(2,
                                new ReadyToIntakeTwo(TIMEOUT_SECONDS),
                                new ArmToScore(TIMEOUT_SECONDS)),
                        new BehindChamberForThirdScore(TIMEOUT_SECONDS),
                        new DriveToChamber(TIMEOUT_SECONDS),
                        new WaitForClawOpen(TIMEOUT_SECONDS),
                        new Park(TIMEOUT_SECONDS)),

                new Park(TIMEOUT_SECONDS));
                    }
}

/* TREE
?
|  ->
|  |  [DriveToChamber]
|  |  [Score]
|  |  [Park]
|  [Park]
 */
