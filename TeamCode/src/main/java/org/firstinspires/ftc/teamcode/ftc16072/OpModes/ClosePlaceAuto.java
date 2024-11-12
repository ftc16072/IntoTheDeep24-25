package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

public class ClosePlaceAuto extends QQOpMode {
    private double step = 0;

    public void init() {
        super.init();
        robot.otos.setOtosPosition(31.48, -63.02, 0);
        robot.claw.close();
    }

    public void loop() {
        if (step == 0) {
            robot.scoreArm.goToPlace();
            boolean donedriving = nav.driveToPositionIN(0, -33.48, 0);

            if (donedriving) {
                step = 1;
            }
        }
        else if (step == 1) {
            robot.scoreArm.goToScoring();
            boolean donedriving = nav.driveToPositionIN(0, -38, 0);
            robot.claw.open();

            if (donedriving) {
                step = 2;
            }
        }
        else if (step == 2) {
            robot.scoreArm.goToPlace();
            boolean donedriving = nav.driveToPositionIN(0,-33.48,0);

            if (donedriving) {
                step = 3;
            }
        }
        else if (step == 3) {
            boolean donedriving = nav.driveToPositionIN(24, -24, 0);

            if (donedriving) {
                step = 4;
            }
        }
        else if(step == 4){
            boolean donedriving = nav.driveToPositionIN(49,-55.48,0);
            robot.scoreArm.goToIntake();

            if (donedriving) {
                step = 5;
            }
        }

        else if(step==5) {
            //Fully extend intake arm and pick up sample then bring it
            robot.scoreArm.goToIntake();
            step = 6;
        }

        else if(step == 6) {

        }

    }

}
