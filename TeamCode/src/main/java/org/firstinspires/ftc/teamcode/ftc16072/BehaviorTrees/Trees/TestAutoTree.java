package org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Trees;

/* Tree PreloadAuto for 16072 generated by http://behaviortrees.ftcteams.com */

import com.ftcteams.behaviortrees.Node;
import com.ftcteams.behaviortrees.Sequence;

import org.firstinspires.ftc.teamcode.ftc16072.BehaviorTrees.Actions.Park;


public class TestAutoTree {

    public static final int TIMEOUT_SECONDS = 10;

    public static Node root(){
        return new Sequence(
                new Park(TIMEOUT_SECONDS));
}}


