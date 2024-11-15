package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.RobotBase;
import org.firstinspires.ftc.teamcode.old.PixelDetection;

import java.util.ArrayList;

public abstract class AutoJava extends RobotBase {


    protected ArrayList<String> movements = new ArrayList<>();

    protected PixelDetection pixelDetection;
    protected boolean blue = false;


    protected AutoJava(boolean blue) {
        this.blue = blue;
    }

    @Override
    public void initMotors() {
        super.initMotors();
        this.setBrakeMotors();
        arm.setPosition(0.6);
        claw.setPosition(this.closedClawPos);
    }


    protected void moveBot(double distIN, double vertical, double pivot, double horizontal) {

        // 23 motor tics = 1 IN
        int motorTics;
        int posNeg = (vertical >= 0) ? 1 : -1;

        rf_drive.setPower(powerFactor * (-pivot + (vertical - horizontal)));
        rb_drive.setPower(powerFactor * (-pivot + vertical + horizontal));
        lf_drive.setPower(powerFactor * (pivot + vertical + horizontal));
        lb_drive.setPower(powerFactor * (pivot + (vertical - horizontal)));

        if (horizontal != 0) {
            posNeg = (horizontal > 0) ? 1 : -1;
            motorTics = lf_drive.getCurrentPosition() + (int) ((distIN * intCon) * (-1*posNeg));
            if (posNeg == 1) {
                // right goes negative
                while ((lf_drive.getCurrentPosition() > motorTics) && opModeIsActive()) {
                    idle();
                }
            } else {
                // left goes positive
                while ((lf_drive.getCurrentPosition() < motorTics) && opModeIsActive()) {
                    idle();
                }
            }
        } else {
            motorTics = rf_drive.getCurrentPosition() + (int) ((distIN * intCon) * posNeg);
            if (posNeg == 1) {
                while (rf_drive.getCurrentPosition() < motorTics && opModeIsActive()) {
                    idle();
                }
            } else {
                while ((rf_drive.getCurrentPosition() > motorTics) && opModeIsActive()) {
                    idle();
                }
            }

        }
        removePower();

    }


    protected void moveBotTics(double motorTics, double vertical, double pivot, double horizontal) {

        int posNeg = (vertical >= 0) ? 1 : -1;

        rf_drive.setPower(powerFactor * (-pivot + (vertical - horizontal)));
        rb_drive.setPower(powerFactor * (-pivot + vertical + horizontal));
        lf_drive.setPower(powerFactor * (pivot + vertical + horizontal));
        lb_drive.setPower(powerFactor * (pivot + (vertical - horizontal)));

        if (horizontal >= 0) {
            motorTics = lf_drive.getCurrentPosition() + (int) ((motorTics) * posNeg);
            if (posNeg == -1) {
                while ((lf_drive.getCurrentPosition() > motorTics) && opModeIsActive()) {
                    idle();
                }
            } else {
                while ((lf_drive.getCurrentPosition() < motorTics) && opModeIsActive()) {
                    idle();
                }
            }
        } else {
            motorTics = rf_drive.getCurrentPosition() + (int) ((motorTics) * posNeg);
            while ((rf_drive.getCurrentPosition() < motorTics) && opModeIsActive()) {
                idle();
            }
        }
        removePower();

    }

    protected void turnBot(double degrees) {
        // 13.62 inches is default robot length
        double robotLength = 13.62;
        double distUnit = (robotLength) / (Math.cos(45));
        double distIN = Math.abs((distUnit * ((degrees*1.75))) / 90);
        int motorTics;
        int pivot = (degrees >= 0) ? 1 : -1;
        rf_drive.setPower(powerFactor * (-pivot));
        rb_drive.setPower(powerFactor * (-pivot));
        lf_drive.setPower(powerFactor * (pivot));
        lb_drive.setPower(powerFactor * (pivot));
        motorTics = lf_drive.getCurrentPosition() + (int) Math.round((distIN * intCon)* pivot);
        if (pivot == 1) {
            while ((lf_drive.getCurrentPosition() < motorTics) && opModeIsActive()) {
                idle();
            }
        }
        if (pivot == -1) {
            while ((lf_drive.getCurrentPosition() > motorTics) && opModeIsActive()) {
                idle();
            }
        }
        removePower();

    }


    public void liftBot(int level, double speed) {
        int liftPos = 0;
        switch (level)
        {
            case -1: {
                liftPos = -100;
                break;
            }
            case 0: {
                liftPos = -940;
                break;
            }
            case 1: {
//                liftPos = -940;
                liftPos = -1045;
                break;
            }
            case 2: {
                liftPos = -1350;
                break;
            }
            /*
            case -1: {
                liftPos = -100;
                break;
            }
            case 0: {
                liftPos = -800;
                break;
            }
            case 1: {
                liftPos = -1500;
                break;
            }
            case 2: {
                liftPos = -2650;
                break;
            }
            case 3: {
                liftPos = -4150;
                break;
            }*/
        }

        if (level == -1)
            lift.setDirection(DcMotorSimple.Direction.REVERSE);
        else if (level == 1) // positions change -1,2,1 when 2,1 happens direction should change
            lift.setDirection(DcMotorSimple.Direction.FORWARD);

        moveMotor(lift, liftPos, speed, true);
    }

    protected void liftBot(int level) {
        liftBot(level, 0.2975);
    }



    protected void commonAutoInit() {
        this.initMotors();
//        this.initCamera();

        while (!isStarted()) {

        }

        telemetry.addLine("Waiting for start");
        telemetry.update();
        waitForStart();

//        camera.closeCameraDevice();
    }

    // stub methods for later
    protected void placeSpecimen() {

    }


    public abstract void runOpMode();


}
