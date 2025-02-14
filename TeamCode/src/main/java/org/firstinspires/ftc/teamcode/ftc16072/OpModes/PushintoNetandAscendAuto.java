package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

//@Autonomous
public class PushintoNetandAscendAuto extends QQOpMode{
    private double step = 0;

    public void init(){
        super.init();
        robot.otos.setOtosPosition(-61.5,0,-90);
    }
    public void loop(){
        super.loop();
        if(step == 0){
            boolean donedriving = nav.driveToPositionIN(-58,24,0);
              if(donedriving){
                  step = 1;
            }
        }else if(step == 1){
            boolean doneDriving = nav.driveToPositionIN(-28,24,0);
            if(doneDriving){
                step = 2;
            }
        }else if(step == 2){
           boolean doneDriving = nav.driveToPositionIN(-24,10,0);
           if(doneDriving){
               step = 3;
            }
    } else if (step == 3) {
            boolean doneDriving = nav.driveToPositionIN(-24,10,-90);
            if (doneDriving){
                step = 4;
                robot.scoreArm.goToPlace();
            }
        }

    }
}
