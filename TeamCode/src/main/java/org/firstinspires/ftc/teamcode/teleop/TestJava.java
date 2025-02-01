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
            telemetry.addLine("Dpad up is roller test");
            telemetry.addLine("Dpad down is wheel");
            telemetry.addLine("Dpad left is claw");
            telemetry.addLine("Dpad right is claw quick");
            telemetry.addLine("a is arm test");
            telemetry.addLine("b is is slide test");
            telemetry.addLine("x is experimental slide test");
            telemetry.update();
            if (gamepad1.a) {
                armTest();
            }
            if (gamepad1.x) {
                slideTestExperimental();
            }
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
                rollerTest();
            }
            if(gamepad1.b)
            {
                slideTest();
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
    void clawTestQuick() {
        for (double i = 1; i >= -1; i -= 0.01) {
            claw.setPosition(i);
            telemetry.addData("pos: ", i);
            telemetry.update();
        }
    }
    void armTest() {
        for (double i = 0.3; i >= -1; i -= 0.05) {
            arm.setPosition(i);
            telemetry.addData("pos: ", i);
            telemetry.update();
            sleep(1000);
        }
    }
    void rollerTest() {
        telemetry.addLine("testing leftwheel pos power");
        telemetry.addData("leftwheel dir", leftRoller.getDirection().name());
        telemetry.update();
        leftRoller.setPower(1);
        sleep(2500);
        leftRoller.setPower(0);
        telemetry.addLine("testing leftwheel neg power");
        telemetry.addData("leftwheel dir", leftRoller.getDirection().name());
        telemetry.update();
        leftRoller.setPower(-1);
        sleep(2500);
        leftRoller.setPower(0);
        telemetry.addLine("testing rightwheel pos power");
        telemetry.addData("leftwheel dir", rightRoller.getDirection().name());
        telemetry.update();
        rightRoller.setPower(1);
        sleep(2500);
        rightRoller.setPower(0);
        telemetry.addLine("testing rightwheel neg power");
        telemetry.addData("leftwheel dir", rightRoller.getDirection().name());
        telemetry.update();
        rightRoller.setPower(-1);
        sleep(2500);
        rightRoller.setPower(0);
    }

    void slideTestExperimental() {
        telemetry.addLine("neg power");
        telemetry.update();
        slide.setPower(-1);
        sleep(1500);
        telemetry.addLine("pos power");
        telemetry.update();
        slide.setPower(1);
        sleep(1500);
    }
    void slideTest()
    {
        boolean bPressed = false;
        int outPos = 0;
        int inPos = 0;
        slide.setPower(-1);
        while(!bPressed && opModeIsActive())
        {
            telemetry.addLine("click y when at desired out pos");
            telemetry.addData("slide power: ",slide.getPower());
            telemetry.update();
            if(gamepad1.y) {
                bPressed = true;
            }

        }
        outPos = slide.getCurrentPosition();
        slide.setPower(0);
        bPressed = false;
        sleep(1000);
        slide.setPower(1);
        while(!bPressed && opModeIsActive())
        {
            telemetry.addLine("click y when at desired in pos");
            telemetry.addData("slide power: ",slide.getPower());
            telemetry.update();
            if(gamepad1.y) {
                bPressed = true;
            }

        }
        inPos = slide.getCurrentPosition();
        slide.setPower(0);
        bPressed = false;
        sleep(1000);
        while(!bPressed && opModeIsActive())
        {
            telemetry.addData("Slide out pos: ", outPos);
            telemetry.addData("Slide in pos: ", inPos);
            telemetry.addLine("click y to move on");
            telemetry.update();
            if(gamepad1.y) {
                bPressed = true;
            }

        }
    }

}
