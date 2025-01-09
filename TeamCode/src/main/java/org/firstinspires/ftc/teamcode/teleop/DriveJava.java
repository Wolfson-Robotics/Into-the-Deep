package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotBase;

@TeleOp(name = "DriveJava")
public class DriveJava extends RobotBase {

//    private double powerFactor = 1;
    private double powerFactor = 0.7095;
    private final double manualArmSpeed = 0.01;

    private final double liftStationaryPower = 0.05;
    private final int liftRangeTolerance = 6;
    private final int minLift = -20;
    private final int maxLift = -4115;

//    private final double maxArm = 0.021;
//    private final double minArm = 0.065;
    private final double maxArm = 0.945;
    private final double minArm = 0.6055;


    boolean bumperPress = false;

    @Override
    public void runOpMode() {
        this.initMotors();
        telemetry.addLine("Waiting for start");
        telemetry.update();

        waitForStart();
        double currentArmPosition = 0.65;
        int cachedLiftPos = 0;

        boolean startedMoving = false;
        boolean alreadySwitchedMode = false;
        boolean minLiftAchieved = false;
        boolean maxLiftAchieved = false;
        boolean stasisAchieved = false;
        while (opModeIsActive()) {

            moveServo(arm, currentArmPosition, 20);
            if (gamepad2.right_stick_y != 0) {
                // greater than less than are reversed because of negative motor pos
//                if (lift.getCurrentPosition() >= this.minLift && gamepad2.right_stick_y > 0 && startedMoving) {
                if (lift.getCurrentPosition() >= this.minLift && gamepad2.right_stick_y > 0 && startedMoving) {

                    telemetry.addLine("controlling min lift");
                    alreadySwitchedMode = false;
                    cachedLiftPos = this.minLift;
                    // prevent jarring motor movement when user tries to go down when achieving lift
                   if (!minLiftAchieved) {
                        moveMotor(lift, this.minLift, this.liftStationaryPower, true);
                        minLiftAchieved = true;
                    }

//                } else if (lift.getCurrentPosition() <= this.maxLift && gamepad2.right_stick_y < 0 && startedMoving) {
                } else if (lift.getCurrentPosition() <= this.maxLift && gamepad2.right_stick_y < 0 && startedMoving) {

                    telemetry.addLine("controlling max lift");
                    alreadySwitchedMode = false;
                    cachedLiftPos = this.maxLift;
                    // prevent jarring motor movement when user tries to go up when achieving max
                    if (!maxLiftAchieved) {
                        moveMotor(lift, this.maxLift, this.liftStationaryPower, true);
                        maxLiftAchieved = true;
                    }

                } else {

                    boolean dont = false;
                    if (gamepad2.right_stick_y > 0 && startedMoving) {
                        if (Math.abs(lift.getCurrentPosition() - minLift) <= this.liftRangeTolerance && minLiftAchieved) {
                            dont = true;
                        }
                    } else if (gamepad2.right_stick_y < 0 && startedMoving) {
                        if (Math.abs(lift.getCurrentPosition() - maxLift) <= this.liftRangeTolerance && maxLiftAchieved) {
                            dont = true;
                        }
                    }
                    if (!dont) {
                        telemetry.addLine("user stick to lift");
                        startedMoving = true;
                        minLiftAchieved = false;
                        maxLiftAchieved = false;
                        stasisAchieved = false;
                        if (!alreadySwitchedMode) {
                            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                            alreadySwitchedMode = true;
                        }
                        lift.setPower(gamepad2.right_stick_y * 0.675);
                        cachedLiftPos = lift.getCurrentPosition();
                    }

                }
            } else {
                if (startedMoving) {
                    telemetry.addLine("Not receiving input right now maintaining lift position");
                    alreadySwitchedMode = false;
                    if (!stasisAchieved) {
                        moveMotor(lift, cachedLiftPos, this.liftStationaryPower, true);
                        stasisAchieved = true;
                    }
                }
            }


            if (gamepad2.left_stick_y < 0) {
                currentArmPosition += this.manualArmSpeed; // increase by a small step
//                if(currentArmPosition > 1) currentArmPosition = 1;
//                if (currentArmPosition >= 0.4288) currentArmPosition = 0.4288;
                if (currentArmPosition >= this.maxArm) currentArmPosition = this.maxArm;
            } else if (gamepad2.left_stick_y > 0) {
                currentArmPosition -= this.manualArmSpeed; // decrease by a small steps
//                if(currentArmPosition < -1) currentArmPosition = -1;
                if (currentArmPosition <= this.minArm) currentArmPosition = this.minArm;
            }
/*
            if (gamepad2.dpad_up) {
                // for hang even further back than this
//                currentArmPosition = 0.4288;
                currentArmPosition = 0.06;
            }
            if (gamepad2.dpad_down) {
                currentArmPosition = 0.38225;
            }
            if (gamepad2.dpad_right) {
                currentArmPosition = 0.241125;
            }*/
            if (gamepad2.dpad_down) {
                currentArmPosition = 0.8355;
            }
            if (gamepad2.dpad_left) {
                currentArmPosition = 0.655;
            }
            if (gamepad2.dpad_up) {
                currentArmPosition = 0.55;
            }
            // code lift preset(s) here later
            if (gamepad2.y) {

            }
            speedchange();
            telemetry.addData("arm sent pos: ", currentArmPosition);
            telemetry.addData("arm pos actual: ", arm.getPosition());
            telemetry.addData("claw pos: ", claw.getPosition());
            telemetry.addData("lift power sent: ", gamepad2.right_stick_y);
            telemetry.addData("lift pos sent: ", cachedLiftPos);
            telemetry.addData("lift pos actual: ", lift.getCurrentPosition());
            telemetry.addData("lift power actual: ", lift.getPower());
            telemetry.addData("lf_drive pos: ", lf_drive.getCurrentPosition());
            telemetry.addData("lf_drive power: ", lf_drive.getPower());
            telemetry.addData("rf_drive pos: ", rf_drive.getCurrentPosition());
            telemetry.addData("rf_drive power: ", rf_drive.getPower());
            telemetry.addData("gamepad2 right trigger: ", gamepad2.right_trigger);
            telemetry.addData("gamepad2 left trigger: ", gamepad2.left_trigger);
            telemetry.addData("gamepad2 right stick y: ", gamepad2.right_stick_y);
            telemetry.addData("gamepad1 left stick x: ", gamepad1.left_stick_x);
            telemetry.addData("gamepad1 left stick y: ", gamepad1.left_stick_y);
            telemetry.addData("gamepad1 right stick x: ", gamepad1.right_stick_x);
            telemetry.addData("gamepad1 right stick y: ", gamepad1.right_stick_y);
            telemetry.addData("lf_drive", lf_drive.getCurrentPosition());
            telemetry.addData("lf target",lf_drive.getCurrentPosition() + (int) ((20.00 * intCon)));
            telemetry.addData("power factor:", powerFactor);

            // grab claw
            if (gamepad2.left_trigger > 0.1) {
                //close
                claw.setPosition(this.closedClaw);
            }

            // drop claw
            if (gamepad2.right_trigger > 0.1) {
                //open
                claw.setPosition(this.openClaw);
            }


            moveBot(-gamepad1.left_stick_y, (gamepad1.right_stick_x), gamepad1.left_stick_x);
           // hang.setPower(gamepad1.left_trigger - gamepad1.right_trigger);
//            moveBot(-gamepad1.left_stick_y, (gamepad1.right_stick_x), gamepad1.left_stick_x);
            telemetry.update();
        }
    }

    private void speedchange()
    {
        if(!bumperPress) {
            boolean up = false;
            if (gamepad1.right_bumper || gamepad1.left_bumper) {
                up = gamepad1.right_bumper;
                bumperPress = true;

                if (up) powerFactor = Math.min(powerFactor + 0.1, 1);
                else powerFactor = Math.max(powerFactor - 0.1, 0.1);
                telemetry.addData("power factor:", powerFactor);

            }
        }
        if(bumperPress && !gamepad1.right_bumper && !gamepad1.left_bumper) bumperPress = false;
    }

    private void moveBot(float vertical, float pivot, float horizontal) {
//        pivot *= 0.6;
        pivot *= 0.855;
        rf_drive.setPower(powerFactor * (-pivot + (vertical - horizontal)));
        rb_drive.setPower(powerFactor * (-pivot + vertical + horizontal));
        lf_drive.setPower(powerFactor * (pivot + vertical + horizontal));
        lb_drive.setPower(powerFactor * (pivot + (vertical - horizontal)));
    }

}
