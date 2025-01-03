package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Trees;

/* Tree PreloadAuto for 16072 generated by http://behaviortrees.ftcteams.com */

import com.ftcteams.behaviortrees.Node;
import com.ftcteams.behaviortrees.Failover;
import com.ftcteams.behaviortrees.Sequence;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.DriveToChamber;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.WaitForClawOpen;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.WaitForScore;
import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.Park;


public class PreloadAuto {

    public static final int TIMEOUT_SECONDS = 10;

    public static Node root(){
        return new Failover(
                new Sequence(
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
