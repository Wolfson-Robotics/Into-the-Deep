package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotBase;

@TeleOp(name = "DriveJava")
public class DriveJava extends RobotBase {

    private double powerFactor = 0.7095;
    private boolean bumperPress = false;

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

        int cachedSlidePos = 0;
        boolean startedMovingSlide = false;
        boolean alreadySwitchedModeSlide = false;
        boolean minSlideAchieved = false;
        boolean maxSlideAchieved = false;
        boolean stasisAchievedSlide = false;
        while (opModeIsActive()) {

            moveServo(arm, currentArmPosition, 20);
            if (gamepad2.right_stick_y != 0) {
                // greater than less than are reversed because of negative motor pos
                if (lift.getCurrentPosition() >= this.minLift && gamepad2.right_stick_y > 0 && startedMoving) {

                    telemetry.addLine("controlling min lift");
                    alreadySwitchedMode = false;
                    cachedLiftPos = this.minLift;
                    // prevent jarring motor movement when user tries to go down when achieving lift
                   if (!minLiftAchieved) {
                        driveMotor(lift, this.minLift, this.liftStationaryPower);
                        minLiftAchieved = true;
                    }

                } else if (lift.getCurrentPosition() <= this.maxLift && gamepad2.right_stick_y < 0 && startedMoving) {

                    telemetry.addLine("controlling max lift");
                    alreadySwitchedMode = false;
                    cachedLiftPos = this.maxLift;
                    // prevent jarring motor movement when user tries to go up when achieving max
                    if (!maxLiftAchieved) {
                        driveMotor(lift, this.maxLift, this.liftStationaryPower);
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
                        lift.setPower(gamepad2.right_stick_y * liftDriveLim);
                        cachedLiftPos = lift.getCurrentPosition();
                    }

                }
            } else {
                if (startedMoving) {
                    telemetry.addLine("Not receiving input right now maintaining lift position");
                    alreadySwitchedMode = false;
                    if (!stasisAchieved) {
                        driveMotor(lift, cachedLiftPos, this.liftStationaryPower);
                        stasisAchieved = true;
                    }
                }
            }






            if (gamepad1.dpad_up || gamepad1.dpad_down) {
                if (slide.getCurrentPosition() <= this.maxSlide && gamepad2.dpad_up && startedMovingSlide) {

                    telemetry.addLine("controlling max slide");
                    alreadySwitchedModeSlide = false;
                    cachedSlidePos = this.maxSlide;
                    // prevent jarring motor movement when user tries to go down when achieving lift
                    if (!maxSlideAchieved) {
                        driveMotor(slide, this.maxSlide, 0.22);
                        maxSlideAchieved = true;
                    }

                } else if (slide.getCurrentPosition() >= this.minSlide && gamepad2.dpad_down && startedMovingSlide) {

                    telemetry.addLine("controlling min slide");
                    alreadySwitchedModeSlide = false;
                    cachedSlidePos = this.minSlide;
                    // prevent jarring motor movement when user tries to go up when achieving max
                    if (!minSlideAchieved) {
                        driveMotor(slide, this.minSlide, 1);
                        minSlideAchieved = true;
                    }

                } else {

                    boolean dont = false;
                    if (gamepad2.dpad_up && startedMovingSlide) {
                        if (Math.abs(slide.getCurrentPosition() - maxSlide) <= this.slideRangeTolerance && maxSlideAchieved) {
                            dont = true;
                        }
                    } else if (gamepad2.dpad_down && startedMovingSlide) {
                        if (Math.abs(slide.getCurrentPosition() - minSlide) <= this.slideRangeTolerance && minSlideAchieved) {
                            dont = true;
                        }
                    }
                    if (!dont) {
                        telemetry.addLine("user stick to slide");
                        startedMovingSlide = true;
                        minSlideAchieved = false;
                        maxSlideAchieved = false;
                        stasisAchievedSlide = false;
                        if (!alreadySwitchedModeSlide) {
                            slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                            alreadySwitchedModeSlide = true;
                        }
                        int currSlidePos = slide.getCurrentPosition();
                        double slidePowerCoef = 0.7;
                        if (currSlidePos < this.minSlide && currSlidePos > -7) {
                            slidePowerCoef = 1;
                        } else if (currSlidePos < -7 && currSlidePos > -15) {
                            slidePowerCoef = 0.7;
                        } else if (currSlidePos < -15 && currSlidePos > -29) {
                            slidePowerCoef = 0.26;
                        } else if (currSlidePos < -29 && currSlidePos > -38) {
                            slidePowerCoef = 0.13;
                        } else if (currSlidePos < -38 && currSlidePos > this.maxSlide) {
                            slidePowerCoef = 0.05;
                        }

                        if (gamepad1.dpad_up) {
                            slide.setPower(-1 * slidePowerCoef);
                        } else if (gamepad1.dpad_down) {
                            slide.setPower(slidePowerCoef);
                        } else if (Math.abs(gamepad1.left_stick_x) > 0
                                || Math.abs(gamepad1.left_stick_y) > 0
                                || Math.abs(gamepad1.left_stick_x) > 0) {
//                            slide.setPower(1);
                            driveMotor(slide, minSlide, 1);
                        } else {
                            slide.setPower(0);
                        }
                        cachedSlidePos = slide.getCurrentPosition();
                    }

                }
            } else {
                if (startedMovingSlide) {
                    telemetry.addLine("Not receiving input right now maintaining slide position");
                    alreadySwitchedModeSlide = false;
                    if (!stasisAchievedSlide) {
                        if (Math.abs(gamepad1.left_stick_x) > 0
                                || Math.abs(gamepad1.left_stick_y) > 0
                                || Math.abs(gamepad1.left_stick_x) > 0) {
                            driveMotor(slide, minSlide, 1);
//                            slide.setPower(1);
                        } else {
                            driveMotor(slide, cachedSlidePos, this.liftStationaryPower);
                            stasisAchievedSlide = true;
                        }
                    }
                }
            }
            if (Math.abs(gamepad1.left_stick_x) > 0
                    || Math.abs(gamepad1.left_stick_y) > 0
                    || Math.abs(gamepad1.left_stick_x) > 0) {
                driveMotor(slide, minSlide, 1);
//                            slide.setPower(1);
            }

//            if (gamepad2.a) {
//                currentArmPosition = 0.69;
//            }
            if (gamepad2.left_stick_y < 0 && !gamepad2.a) {
                currentArmPosition += this.manualArmSpeed; // increase by a small step
                if (currentArmPosition >= this.maxArm) currentArmPosition = this.maxArm;
            } else if (gamepad2.left_stick_y > 0 && !gamepad2.a) {
                currentArmPosition -= this.manualArmSpeed; // decrease by a small step
                if (currentArmPosition <= this.minArm) currentArmPosition = this.minArm;
            }

//            if (gamepad2.dpad_down) {
            if (gamepad2.a) {
                currentArmPosition = this.maxArm;
            }
//            if (gamepad2.dpad_right) {
            if (gamepad2.b) {
//                currentArmPosition = 0.78;
                currentArmPosition = 0.18;
            }
//            if (gamepad2.dpad_up) {
            if (gamepad2.y) {
                currentArmPosition = this.minArm;
            }

            // note: for arm movements, automatically fix the slide arm
            // bring slide arm back
            if (gamepad2.dpad_up) {
                slideArm.setPosition(0.7);
            }
            // put slide arm down
            if (gamepad2.dpad_down) {
                slideArm.setPosition(0.3);
            }
            // intake color sample
            if (gamepad2.right_bumper) {
                leftRoller.setPower(-1);
                rightRoller.setPower(1);
//                leftWheel.setPosition(0.3);
//                rightWheel.setPosition(0.3);
            } else
            // spit out color sample
            if (gamepad2.left_bumper) {
                leftRoller.setPower(1);
                rightRoller.setPower(-1);
//                leftWheel.setPosition(0.6);
//                rightWheel.setPosition(0.6);
            } else {
                leftRoller.setPower(0);
                rightRoller.setPower(0);
            }





            // grab claw
            if (gamepad2.left_trigger > 0.1) {
                claw.setPosition(this.closedClaw);
            }
            // open claw
            if (gamepad2.right_trigger > 0.1) {
                claw.setPosition(this.openClaw);
            }

            speedchange();
            moveBot(-gamepad1.left_stick_y, (gamepad1.right_stick_x), gamepad1.left_stick_x);
           // hang.setPower(gamepad1.left_trigger - gamepad1.right_trigger);
            telemetry.addData("lift power sent: ", gamepad2.right_stick_y);
            telemetry.addData("lift pos sent: ", cachedLiftPos);
            telemetry.addData("lift pos actual: ", lift.getCurrentPosition());
            telemetry.addData("lift power actual: ", lift.getPower());
            telemetry.addData("slide pos sent: ", cachedSlidePos);
            telemetry.addData("slide pos actual: ", slide.getCurrentPosition());
            telemetry.addData("slide power: ", slide.getPower());
            telemetry.addData("arm sent pos: ", currentArmPosition);
            telemetry.addData("arm pos actual: ", arm.getPosition());
            telemetry.addData("claw pos: ", claw.getPosition());
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
            telemetry.addData("power factor:", powerFactor);
            telemetry.addLine(" ");
            telemetry.addLine(" ");
            telemetry.addData("gamepad2 right bumper: ", gamepad2.right_bumper);
            telemetry.addData("gamepad2 left bumper: ", gamepad2.left_bumper);
            telemetry.addData("gamepad2 dpad up: ", gamepad2.dpad_up);
            telemetry.addData("gamepad2 dpad down: ", gamepad2.dpad_down);
            telemetry.addData("gamepad1 dpad up: ", gamepad1.dpad_down);
            telemetry.addData("gamepad1 dpad down: ", gamepad1.dpad_down);
            telemetry.addData("gamepad2 a: ", gamepad2.a);
            telemetry.addData("gamepad2 b: ", gamepad2.b);
            telemetry.addData("gamepad2 y: ", gamepad2.y);
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
        pivot *= 0.855f;
        rf_drive.setPower(powerFactor * (-pivot + (vertical - horizontal)));
        rb_drive.setPower(powerFactor * (-pivot + vertical + horizontal));
        lf_drive.setPower(powerFactor * (pivot + vertical + horizontal));
        lb_drive.setPower(powerFactor * (pivot + (vertical - horizontal)));
    }

}
