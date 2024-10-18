package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AutoJavaBlue", group = "Auto")
public class AutoJavaBlue extends AutoJava {

    public AutoJavaBlue() {
        super(true);
    }

    @Override
    public void runOpMode() {
        this.commonAutoInit();
        // move forward
        // lift up
        // move forward slightly
        // lift down slightly
        // let go claw
        // lift back up
        // move backward slightly
        // lift down
        // ....
    }
}
