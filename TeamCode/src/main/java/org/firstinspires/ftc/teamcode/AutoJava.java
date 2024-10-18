package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.old.PixelDetection;

import java.util.ArrayList;

public abstract class AutoJava extends RobotBase {


    protected final double WHEEL_DIAMETER_INCHES = 3.0;
    protected final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER_INCHES;
    protected final double TICKS_PER_ROTATION = 288;
    protected final double intCon = 8.727272;


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
    }


    protected void moveBot(double distIN, float vertical, float pivot, float horizontal) {

        // 23 motor tics = 1 IN
        int motorTics;
        int posNeg = (vertical >= 0) ? 1 : -1;

        rf_drive.setPower(powerFactor * (-pivot + (vertical - horizontal)));
        rb_drive.setPower(powerFactor * (-pivot + vertical + horizontal));
        lf_drive.setPower(powerFactor * (pivot + vertical + horizontal));
        lb_drive.setPower(powerFactor * (pivot + (vertical - horizontal)));

        if (horizontal >= 0) {
            motorTics = lf_drive.getCurrentPosition() + (int) ((distIN * intCon) * posNeg);
            if (posNeg == 1) {
                while ((lf_drive.getCurrentPosition() > motorTics) && opModeIsActive()) {
                    telemetry.addData("pos: ", lf_drive.getCurrentPosition());
                    telemetry.update();
                    idle();
                }
            } else {
                while ((lf_drive.getCurrentPosition() < motorTics) && opModeIsActive()) {
                    telemetry.addData("pos: ", lf_drive.getCurrentPosition());
                    telemetry.update();
                    idle();
                }
            }
        } else {
            motorTics = rf_drive.getCurrentPosition() + (int) ((distIN * intCon) * posNeg);
            while ((rf_drive.getCurrentPosition() < motorTics) && opModeIsActive()) {
                idle();
            }
        }
        telemetry.addLine("done");
        telemetry.update();
        removePower();

    }


    protected void moveBotTics(double motorTics, float vertical, float pivot, float horizontal) {

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

    protected void turnBot(int degrees) {
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

    public void liftBot(int level) {
        int liftPos = 0;
        switch (level)
        {
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
            }
        }

        if (level == -1)
            lift.setDirection(DcMotorSimple.Direction.REVERSE);
        else if (level == 1) // positions change -1,2,1 when 2,1 happens direction should change
            lift.setDirection(DcMotorSimple.Direction.FORWARD);

        moveMotor(lift, liftPos);
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


    public abstract void runOpMode();


}
