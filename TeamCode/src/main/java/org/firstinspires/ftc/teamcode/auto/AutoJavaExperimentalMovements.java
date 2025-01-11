package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.PersistentTelemetry;

@TeleOp(name = "AutoJavaExperimentalMovements")
public class AutoJavaExperimentalMovements extends AutoJava {

    private final double powerFactor = 0.4;
    private final PersistentTelemetry pTelem = new PersistentTelemetry(this.telemetry);

    private double startRf = 0;
    private double startLf = 0;

    public AutoJavaExperimentalMovements() {
        super(true);
    }

    // down by -3 and left by -3
    public void trial1() {
        beginTrial();
        print("trial 1");
        rb_drive.setPower(powerFactor * -1);
        rf_drive.setPower(powerFactor * -1);
        endTrial();
    }
    // down by -1 and left by -1
    public void trial2() {
        beginTrial();
        print("trial 2");
        rb_drive.setPower(powerFactor * -0.33333333333333333333333333333333333333333);
        lf_drive.setPower(powerFactor * -0.3333333333333333333333333333333333333333333);
        endTrial();
    }
    // up by 2 and right by 1
    public void trial3() {
        beginTrial();
        print("trial 3");
        rf_drive.setPower(powerFactor * 0.333333333333333333333333333333333333);
        rb_drive.setPower(powerFactor * 1);
        lf_drive.setPower(powerFactor * 1);
        lb_drive.setPower(powerFactor * 0.333333333333333333333333333333333333333);
        endTrial();
    }
    // up by 3 and right by 1
    public void trial4() {
        beginTrial();
        print("trial 4");
        rf_drive.setPower(powerFactor * 0.2);
        rb_drive.setPower(powerFactor * 0.8);
        lf_drive.setPower(powerFactor * 0.8);
        lb_drive.setPower(powerFactor * 0.2);
        endTrial();
    }
    // up by 1 and right by 2
    public void trial5() {
        beginTrial();
        print("trial 5");
        rf_drive.setPower(powerFactor * -0.33333333333333333333333);
        rb_drive.setPower(powerFactor * 1);
        lf_drive.setPower(powerFactor * 1);
        lb_drive.setPower(powerFactor * -0.333333333333333333333333);
        endTrial();
    }


    public void trial6() {
        beginTrial();
        print("trial 6");
        moveBotOld(15, 1, 0, 1);
        endTrial();
    }
    public void trial7() {
        beginTrial();
        print("trial 7");
        moveBotOld(15, 0, 1, 0);
        endTrial();
    }
    public void trial8() {
        beginTrial();
        print("trial 8");
        moveBotOld(15, 1, 1, 1);
        endTrial();
    }

    // lf experiment
    public void turnTrial1() {
        beginTrial();
        print("turn trial 1");
        print("changing powers attempting to turn (1)");
        double runCounter = 40;
        double lastPower1 = 1;
        double lastPower2 = 1;
        rf_drive.setPower(powerFactor * lastPower1);
        rb_drive.setPower(powerFactor * lastPower2);
        lf_drive.setPower(powerFactor * lastPower1);
        lb_drive.setPower(powerFactor * lastPower2);
        sleep(1000);
        while (runCounter > 0) {
            rf_drive.setPower(powerFactor * lastPower1);
            rb_drive.setPower(powerFactor * lastPower2);
            lf_drive.setPower(powerFactor * lastPower1);
            lb_drive.setPower(powerFactor * lastPower2);
            ElapsedTime runtime = new ElapsedTime();
            runtime.reset();
            if (runtime.milliseconds() > 100) {
                lastPower1 -= 0.1;
                lastPower2 += 0.05;
                runCounter--;
            }
        }
        endTrial();
    }

    public void turnTrial2() {
        beginTrial();
        print("turn trial 2");
        print("changing powers attempting to turn (2)");
        double runCounter = 40;
        double lastPower1 = -1;
        double lastPower2 = -1;
        rf_drive.setPower(powerFactor * lastPower1);
        rb_drive.setPower(powerFactor * lastPower2);
        lf_drive.setPower(powerFactor * lastPower1);
        lb_drive.setPower(powerFactor * lastPower2);
        sleep(1000);
        while (runCounter > 0) {
            rf_drive.setPower(powerFactor * lastPower1);
            rb_drive.setPower(powerFactor * lastPower2);
            lf_drive.setPower(powerFactor * lastPower1);
            lb_drive.setPower(powerFactor * lastPower2);
            ElapsedTime runtime = new ElapsedTime();
            runtime.reset();
            if (runtime.milliseconds() > 100) {
                lastPower1 -= 0.05;
                lastPower2 += 0.05;
                runCounter--;
            }
        }
        endTrial();
    }


    public void miscTrial1() {
        beginTrial();
        print("misc trial 1");
        print("smooth braking");
        double runCounter = 15;
        double lastPower1 = 1;
        double lastPower2 = 1;
        rf_drive.setPower(powerFactor * lastPower1);
        rb_drive.setPower(powerFactor * lastPower2);
        lf_drive.setPower(powerFactor * lastPower1);
        lb_drive.setPower(powerFactor * lastPower2);
        sleep(1000);
        while (runCounter > 0) {
            rf_drive.setPower(powerFactor * lastPower1);
            rb_drive.setPower(powerFactor * lastPower2);
            lf_drive.setPower(powerFactor * lastPower1);
            lb_drive.setPower(powerFactor * lastPower2);
            ElapsedTime runtime = new ElapsedTime();
            runtime.reset();
            if (runtime.milliseconds() > 100) {
                lastPower1 -= 0.065;
                lastPower2 -= 0.065;
                runCounter--;
            }
        }
    }
    public void miscTrial2() {
        runTasksAsync(
                () -> turnBot(180),
                () -> moveMotor(lift, -3000, 1)
        );
    }




    private void endTrial() {
        sleep(3000);
        removePower();
        print("change in pos rf: " + (rf_drive.getCurrentPosition() - startRf));
        print("change in pos lf: " + (lf_drive.getCurrentPosition() - startLf));
        sleep(1000);
    }
    private void beginTrial() {
        startRf = rf_drive.getCurrentPosition();
        startLf = lf_drive.getCurrentPosition();
    }


    @Override
    public void runOpMode() {
        this.commonAutoInit();

        boolean runningTrials = false;
        while (opModeIsActive()) {

            if (gamepad1.left_stick_x > 0.5 && gamepad1.right_stick_x < -0.5 && !runningTrials) {
                runningTrials = true;
                trial1();
                trial2();
                trial3();
                trial4();
                trial5();
            }
            if (gamepad1.left_stick_x < -0.5 && gamepad1.right_stick_x > 0.5 && !runningTrials) {
                runningTrials = true;
                turnTrial1();
            }
            if (gamepad1.left_stick_y > 0.5 && gamepad1.right_stick_y > 0.5 && !runningTrials) {
                runningTrials = true;
                miscTrial1();
            }
            if (gamepad1.left_stick_y < -0.5 && gamepad1.right_stick_y < -0.5 && !runningTrials) {
                print("testing moveebot in all cardinal directions");
                moveBotOld(10, 0, 0, -1);
                sleep(1000);
                moveBotOld(10, 0, 0, 1);
                sleep(1000);
                moveBotOld(10, -1, 0, 0);
                sleep(1000);
                moveBotOld(10, 1, 0, 0);
                sleep(1000);
                print("testing turnbot in all cardinal directions");
                turnBot(90);
                sleep(1000);
                turnBot(180);
                sleep(1000);
                turnBot(-90);
                sleep(1000);
            }
            runningTrials = false;

            if (gamepad1.dpad_up) {
                trial1();
            }
            if (gamepad1.dpad_right) {
                trial2();
            }
            if (gamepad1.dpad_down) {
                trial3();
            }
            if (gamepad1.dpad_left) {
                trial4();
            }
            if (gamepad1.y) {
                trial5();
            }
            if (gamepad1.b) {
                trial6();
            }
            if (gamepad1.a) {
                trial7();
            }
            if (gamepad1.x) {
                trial8();
            }
            /*
            if (gamepad1.a) {
                trial5();
            }*//*
            if (gamepad1.y) {
                miscTrial2();
            }*/

        }
    }

    private void print(String message) {
//        telemetry.addLine(message);
//        telemetry.update();
        pTelem.addLine(message);
        pTelem.update();
    }

    private void moveBot(float vertical, float pivot, float horizontal) {
        pivot *= 0.6;
        rf_drive.setPower(powerFactor * (-pivot + (vertical - horizontal)));
        rb_drive.setPower(powerFactor * (-pivot + vertical + horizontal));
        lf_drive.setPower(powerFactor * (pivot + vertical + horizontal));
        lb_drive.setPower(powerFactor * (pivot + (vertical - horizontal)));
    }
}
