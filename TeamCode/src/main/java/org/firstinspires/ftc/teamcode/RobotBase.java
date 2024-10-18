package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public abstract class RobotBase extends LinearOpMode {

    protected DcMotorEx rf_drive;
    protected DcMotorEx rb_drive;
    protected DcMotorEx lf_drive;
    protected DcMotorEx lb_drive;

    protected DcMotor lift;

    protected Servo arm;
    protected Servo claw;

    protected OpenCvCamera camera;
    protected String webcamName = "Webcam 1";


    protected double powerFactor = 1;

    protected void initMotors() {

        rf_drive = hardwareMap.get(DcMotorEx.class, "right_drive1");
        rb_drive = hardwareMap.get(DcMotorEx.class, "right_drive2");
        lf_drive = hardwareMap.get(DcMotorEx.class, "left_drive1");
        lb_drive = hardwareMap.get(DcMotorEx.class, "left_drive2");

        rf_drive.setDirection(DcMotorSimple.Direction.REVERSE);
        rb_drive.setDirection(DcMotorSimple.Direction.REVERSE);

        lift = hardwareMap.get(DcMotorEx.class, "lift");
        arm = hardwareMap.get(Servo.class, "arm");
        claw = hardwareMap.get(Servo.class, "claw");

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setDirection(DcMotorSimple.Direction.REVERSE);

        arm.setPosition(0.35);

    }

    public void setBrakeMotors() {
        rf_drive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb_drive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf_drive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb_drive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }


    protected void initCamera() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        this.camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
//        this.pixelDetection = new PixelDetection(this.blue);
//        camera.setPipeline(pixelDetection);


        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(432,240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera error code:", errorCode);
                telemetry.update();
            }
        });

    }


    protected void moveServo(Servo servo, double targetPosition, long speed) {
        if (Math.abs(servo.getPosition() - targetPosition) > 0.01) {
            // Move the servo towards the target position slowly
            if (servo.getPosition() < targetPosition) {
                servo.setPosition(servo.getPosition() + .01);
            } else {
                servo.setPosition(servo.getPosition() - .01);
            }

            // Sleep for a short duration (adjust as needed)
            sleep(speed); // Sleep for 100 milliseconds (adjust for desired speed)
        }

    }

    protected void moveMotor(DcMotor motor, int targetPosition, double speed) {
        motor.setTargetPosition(targetPosition);
        motor.setPower(speed);
        while (lift.isBusy() && lift.getCurrentPosition() < -1 * targetPosition) {
            idle();
        }
        motor.setPower(0);
    }
    protected void moveMotor(DcMotor motor, int targetPosition) {
        moveMotor(motor, targetPosition, 0.05);
    }


    protected void removePower() {
        lf_drive.setPower(0);
        lb_drive.setPower(0);
        rf_drive.setPower(0);
        rb_drive.setPower(0);
    }


}
