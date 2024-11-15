package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AutoJavaBlue", group = "Auto")
public class AutoJavaBlue extends AutoJava {

    public AutoJavaBlue() {
        super(true);
    }

    @Override
    public void runOpMode() {
        this.commonAutoInit();
        moveBot(78.94792324566028,1, 0, 0);
        sleep(1000);
//        moveBot(324.385443698787,1, 0, 0);
        // move forward
        // lift up
        liftBot(2);
//        moveMotor(lift, -1300, 0.05);
        sleep(1000);
        moveBot(6.916667659722306,1, 0, 0);
        sleep(1000);
        // move forward slightly
        // lift down slightly
        liftBot(1);
        sleep(1000);
        claw.setPosition(0.055);
        sleep(1000);
        moveBot(11.916667659722306,-1, 0, 0);

        // let go claw
        // lift back up
        // move backward slightly
        // lift down
        // ....
    }
}
