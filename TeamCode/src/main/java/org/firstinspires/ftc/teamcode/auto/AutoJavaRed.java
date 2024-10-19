package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AutoJavaRed", group = "Auto")
public class AutoJavaRed extends AutoJava {

    public AutoJavaRed() {
        super(false);
    }

    @Override
    public void runOpMode() {
        this.commonAutoInit();
        /*
        moveBot(35, 1, 0, 0);
        telemetry.addData("lf_drive pos: ", lf_drive.getCurrentPosition());
        telemetry.addData("rf_drive pos: ", rf_drive.getCurrentPosition());
        telemetry.update();
        sleep(2000);
        moveBot(35, -1, 0, 0);
        telemetry.addData("lf_drive pos: ", lf_drive.getCurrentPosition());
        telemetry.addData("rf_drive pos: ", rf_drive.getCurrentPosition());
        telemetry.update();
        sleep(2000);
        moveBot(35, 0, 0, 1);
        telemetry.addData("lf_drive pos: ", lf_drive.getCurrentPosition());
        telemetry.addData("rf_drive pos: ", rf_drive.getCurrentPosition());
        telemetry.update();
        sleep(2000);
        moveBot(35, 0, 0, -1);
        telemetry.addData("lf_drive pos: ", lf_drive.getCurrentPosition());
        telemetry.addData("rf_drive pos: ", rf_drive.getCurrentPosition());
        telemetry.update();
        sleep(10000);*/
//        moveMotor(lift, -1300, 0.05);
        liftBot(2);
        /*
        sleep(1000);
//        moveMotor(lift, -800);
//        while (opModeIsActive()) {
            lf_drive.setPower(0.155);
            lb_drive.setPower(0.155);
            rf_drive.setPower(0.155);
            rb_drive.setPower(0.0155);
            sleep(550);
            removePower();
            telemetry.addLine(String.valueOf(lf_drive.getCurrentPosition()));
            telemetry.update();
            sleep(10000);*/
//        }
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
