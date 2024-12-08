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
       // sleep(7000);
        moveBot(154.11459617621637,1, 0, 0);
        sleep(500);
        arm.setPosition(0.6400000000000001);
        sleep(500);
        turnBot(17);
        sleep(1000);
        moveBot(18.5104183758682,1, 0, 0);
        sleep(500);
        moveMotor(lift, -1854, 0.25);
        lift.setPower(0.05);
        sleep(500);
        moveBot(20,1, 0, 0);
        sleep(500);
        moveMotor(lift, -800, 0.25);
        lift.setPower(0.05);
        claw.setPosition(0.36);
        sleep(500);

        moveBot(160.2083446006954,-1, 0, 0);

        sleep(500);

        moveBot(325,0,0,1);
        sleep(500);
        moveBot(20,-1, 0, 0);
       // moveMotor(lift, -992, 0.25);
        sleep(500);
       // moveBot(80,-1, 0, 0);

       // sleep(500);

       // moveBot(340,0,0,1);
        moveMotor(lift, -10, 0.25);


        /*
        claw.setPosition(0.9);
        rf_drive.setPower(powerFactor * (-0 + (0 - 1)));
        rb_drive.setPower(powerFactor * (-0 + 0 + 1));
        lf_drive.setPower(powerFactor * (0 + 0 + 1));
        lb_drive.setPower(powerFactor * (0 + (0 - 1)));
        sleep(1200);
        rf_drive.setPower(0);
        rb_drive.setPower(0);
        lf_drive.setPower(0);
        lb_drive.setPower(0);
        /*
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
        // ....*/
    }
}
