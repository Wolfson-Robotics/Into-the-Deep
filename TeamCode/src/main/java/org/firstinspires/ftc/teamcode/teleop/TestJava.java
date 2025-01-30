package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotBase;

@TeleOp(name = "TestJava")
public class TestJava extends RobotBase {

    @Override
    public void runOpMode() {
        initMotors();
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addLine("Manual:");
            telemetry.addLine("Dpad down is wheel");
            telemetry.addLine("Dpad left is claw");
            telemetry.addLine("Dpad right is claw quick");
            telemetry.addLine("Dpad up is slide servo");
            telemetry.update();
            if (gamepad1.dpad_down) {
                wheelTest();
            }
            if (gamepad1.dpad_left) {
                clawTest();
            }
            if (gamepad1.dpad_right) {
                clawTestQuick();
            }
            if (gamepad1.dpad_up) {
                slideServoTest();
            }
        }
    }

    void wheelTest() {
        telemetry.addLine("testing left drive 1 forward");
        telemetry.update();
        lf_drive.setPower(0.25);
        sleep(2000);
        lf_drive.setPower(0);
        telemetry.addLine("testing left drive 1 reverse");
        telemetry.update();
        lf_drive.setPower(-0.25);
        sleep(2000);
        lf_drive.setPower(0);
        telemetry.addLine("testing left drive 2 forward");
        telemetry.update();
        lb_drive.setPower(0.25);
        sleep(2000);
        lb_drive.setPower(0);
        telemetry.addLine("testing left drive 2 reverse");
        telemetry.update();
        lb_drive.setPower(-0.25);
        sleep(2000);
        lb_drive.setPower(0);
        telemetry.addLine("testing right drive 1 forward");
        telemetry.update();
        rf_drive.setPower(0.25);
        sleep(2000);
        rf_drive.setPower(0);
        telemetry.addLine("testing right drive 1 reverse");
        telemetry.update();
        rf_drive.setPower(-0.25);
        sleep(2000);
        rf_drive.setPower(0);
        telemetry.addLine("testing right drive 2 forward");
        telemetry.update();
        rb_drive.setPower(0.25);
        sleep(2000);
        rb_drive.setPower(0);
        telemetry.addLine("testing right drive 2 reverse");
        telemetry.update();
        rb_drive.setPower(-0.25);
        sleep(2000);
        rb_drive.setPower(0);
    }
    void clawTest() {
        for (double i = 1; i >-1; i -= 0.01) {
            claw.setPosition(i);
            telemetry.addData("pos: ", i);
            telemetry.update();
            sleep(1500);
        }
    }
    void slideServoTest() {
        for (double i = 1; i >-1; i -= 0.01) {
            slideServo.setPosition(i);
            telemetry.addData("pos: ", i);
            telemetry.update();
            sleep(1500);
        }
    }
    void clawTestQuick() {
        for (double i = 1; i >= -1; i -= 0.01) {
            claw.setPosition(i);
            telemetry.addData("pos: ", i);
            telemetry.update();
        }
    }



}
