package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.CustomTelemetryLogger;
import org.firstinspires.ftc.teamcode.RobotBase;
import org.firstinspires.ftc.teamcode.old.ServoSettings;
import org.firstinspires.ftc.teamcode.auto.instruct.AutoInstructionCodeSerializer;
import org.firstinspires.ftc.teamcode.auto.instruct.AutoInstructionConstants;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import android.os.Environment;

/**
 * Gamepad 1 drive trains
 * Gamepad2 Arm
 * author: WolfsonRobotics
 */
@TeleOp(name = "debugjava")
public class DebugJava extends RobotBase {
    private ServoSettings servoSettings = new ServoSettings();
    private Map<String, ServoSettings> servoPositions = new HashMap<>();
    private CustomTelemetryLogger logger;
    private CustomTelemetryLogger textOutputLogger;

    private double liftStationaryPower = 0.05;
    private int minLift = -16;
    private int maxLift = -1585;

    private double maxArm = 0.3995;
    private double minArm = 0.055;
    private double manualArmSpeed = 0.01;

    private boolean buttonPressed = false;



    @Override
    public void runOpMode() {
        initMotors();

        telemetry.addLine("Waiting for start");
        telemetry.update();
        double currentArmPosition = 0.55; // start position for armServo
        double currentElbowPosition = .7;
        boolean buttonPressed = false;
        //variables for debug
        String moves = "";
        double startposright = rf_drive.getCurrentPosition();
        double startposleft = lf_drive.getCurrentPosition();
        double armPastPos = 0;
        double elbowPastPos = 0;
        int numberlog = 0;
        int clawChanged = 0;
        int tapePlace = 0;
        boolean debug = false;
        boolean depadPressed = false;
        boolean turn = false;
        int allowOtherMovement = 0;
        int preLogLiftPos = 0;
        int lastLogLiftPos = 0;
        double preLogArmPos = 0;
        double lastLogArmPos = 0;
        boolean loggingLift = false;
        debug = true;
        telemetry.addLine("debug on");
        telemetry.update();


        boolean startedMoving = false;
        boolean alreadySwitchedMode = false;
        boolean minLiftAchieved = false;
        boolean maxLiftAchieved = false;
        int cachedLiftPos = 0;

        /*
         * Wait for the user to press start on the Driver Station
         */
        try {
            waitForStart();
            if (debug) {
                String logFilePath = String.format("/sdcard/Logs/" + new Date().getHours() + "_" + new Date().getMinutes() + "_" + new Date().getSeconds() + ".txt", Environment.getExternalStorageDirectory().getAbsolutePath());
                logger = new CustomTelemetryLogger(logFilePath);
                textOutputLogger = new CustomTelemetryLogger(AutoInstructionConstants.autoInstructPath);
                telemetry.addData("name of file: ", logFilePath);
                telemetry.addData("name of auto instruct file: ", AutoInstructionConstants.autoInstructPath);
                telemetry.update();

            }
            while (opModeIsActive()) {
                if (debug) {

                    // Dpad up starts the logging pipeline
                    if (gamepad1.dpad_up) {
                        startposright = rf_drive.getCurrentPosition();
                        startposleft = lf_drive.getCurrentPosition();
                        preLogLiftPos = lift.getCurrentPosition();
                        preLogArmPos = arm.getPosition();
                        telemetry.addLine("log start");
                        telemetry.update();
                    }
                    if ((!gamepad1.dpad_down && !gamepad1.dpad_right && !gamepad1.dpad_left) && depadPressed)
                        depadPressed = false;
                    // Dpad down starts logging the movement
                    if (gamepad1.dpad_down && !depadPressed) {
                        depadPressed = true;
                        numberlog++;
                        double rightDif = (rf_drive.getCurrentPosition() - startposright);
                        double leftDif = (lf_drive.getCurrentPosition() - startposleft);
                        logger.logData("log num: " + numberlog + "\n");
                        logger.logData("right movement:" + rightDif + "\n");
                        logger.logData("left movement:" + leftDif + "\n");
                        telemetry.addData("log end", numberlog);
                        telemetry.update();
                        boolean vertical = ((rightDif >= 0));
                        switch (allowOtherMovement) {
                            case 1:

                                telemetry.addData("vert", (leftDif < 0));
                                telemetry.update();
                                moves += "moveBot(" + ((Math.abs(leftDif)) / intCon) + "," + ((leftDif < 0) ? 1 : -1) + ", 0, 0);\nsleep(500);\n";
                                break;
                            case 2:
                                telemetry.addData("horz", (rightDif > 0));
                                telemetry.update();
                                moves += "moveBot(" + ((Math.abs(rightDif)) / intCon) + ",0,0," + ((rightDif > 0) ? -1 : 1) + ");\nsleep(500);\n";
                                break;
                            case 3:
                                telemetry.addLine("turn");
                                telemetry.update();
                                moves += ("turnBot(" + (ticsToDegrees((int) (Math.round(leftDif))) + ");\nsleep(1000);\n"));
                                break;
                        }
                        allowOtherMovement = 0;

                        switch (clawChanged)
                        {
                            case 1:
                                moves += "claw.setPosition(" + this.closedClawPos + ");\nsleep(500);\n";
                                clawChanged = 0;
                                break;
                            case 2:
                                moves += "claw.setPosition(" + this.openClawPos + ");\nsleep(500);\n";
                                clawChanged = 0;
                                break;

                        }

                        lastLogLiftPos = lift.getCurrentPosition();
                        lastLogArmPos = arm.getPosition();
                        if (preLogLiftPos != lastLogLiftPos) {
                            moves += "moveMotor(lift, " + lastLogLiftPos + ", 0.25);\nsleep(500);\n";
                        }
                        if (preLogArmPos != lastLogArmPos) {
                            moves += "arm.setPosition(" + arm.getPosition() + ");\nsleep(500);\n";
                        }

                        startposright = rf_drive.getCurrentPosition();
                        startposleft = lf_drive.getCurrentPosition();
                        preLogLiftPos = lift.getCurrentPosition();
                        preLogArmPos = arm.getPosition();
                        turn = false;

                    }
                    // Dpad up actually sends the data to the FileWriter
                    if (gamepad1.dpad_right && !depadPressed) {
                        depadPressed = true;
                        logger.logData(moves);
                        textOutputLogger.logData("\n\n" + AutoInstructionCodeSerializer.serialize(moves) + "\n\n");
                        telemetry.addData("logged moves: ", moves);
                        telemetry.update();

                    }


                    if (gamepad2.right_stick_y != 0 || gamepad2.left_stick_y != 0)
                        buttonPressed = false;
                    }

                    if (gamepad2.left_trigger > 0) {claw.setPosition(this.closedClawPos);  clawChanged = 1;} //grab claw
                    if (gamepad2.right_trigger > 0) {claw.setPosition(this.openClawPos); clawChanged = 2;}//drop

/* drivejava driving mechanisms here */

                moveServo(arm, currentArmPosition, 20);
                if (gamepad2.right_stick_y != 0) {
//                if (lift.getCurrentPosition() <= -3900) {
//                    moveMotor(lift, -3900, 0.05);
                    if (lift.getCurrentPosition() >= this.minLift && gamepad2.right_stick_y > 0 && startedMoving) {
                        telemetry.addLine("controlling min lift");
                        alreadySwitchedMode = false;
                        cachedLiftPos = this.minLift;
                        if (!minLiftAchieved) {
                            moveMotor(lift, this.minLift, this.liftStationaryPower, true);
                            minLiftAchieved = true;
                        }
                    } else if (lift.getCurrentPosition() <= this.maxLift && gamepad2.right_stick_y < 0 && startedMoving) {
                        telemetry.addLine("controlling max lift");
                        alreadySwitchedMode = false;
                        cachedLiftPos = this.maxLift;
                        if (!maxLiftAchieved) {
                            moveMotor(lift, this.maxLift, this.liftStationaryPower, true);
                            maxLiftAchieved = true;
                        }
                    } else {
//                    moveMotor(lift, -1565, 0.05);
                        telemetry.addLine("user stick to lift");
                        startedMoving = true;
                        minLiftAchieved = false;
                        maxLiftAchieved = false;
                        if (!alreadySwitchedMode) {
                            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                            alreadySwitchedMode = true;
                        }
                        lift.setPower(gamepad2.right_stick_y * 0.5);
                        cachedLiftPos = lift.getCurrentPosition();
                    }

                } else {
                    if (startedMoving) {
                        telemetry.addLine("Not receiving input right now maintaining lift position");
                        alreadySwitchedMode = false;
                        moveMotor(lift, cachedLiftPos, this.liftStationaryPower, true);
                    }
                }


                if (gamepad2.left_stick_y > 0) {
                    currentArmPosition += this.manualArmSpeed; // increase by a small step
//                if(currentArmPosition > 1) currentArmPosition = 1;
//                if (currentArmPosition >= 0.4288) currentArmPosition = 0.4288;
                    if (currentArmPosition >= this.maxArm) currentArmPosition = this.maxArm;
                } else if (gamepad2.left_stick_y < 0) {
                    currentArmPosition -= this.manualArmSpeed; // decrease by a small steps
//                if(currentArmPosition < -1) currentArmPosition = -1;
                    if (currentArmPosition <= this.minArm) currentArmPosition = this.minArm;
                }

                    switch (allowOtherMovement) {
                        case 1:
                            moveBot(-gamepad1.left_stick_y, 0, 0);
                            break;
                        case 2:
                            moveBot(0, 0, gamepad1.left_stick_x);
                            break;
                        case 3:
                            moveBot(0, (gamepad1.right_stick_x), 0);
                            break;
                    }
                    if (gamepad1.left_stick_y != 0 && allowOtherMovement == 0) {
                        allowOtherMovement = 1;
                        telemetry.addLine("1");
                        telemetry.update();
                    }
                    if (gamepad1.left_stick_x != 0 && allowOtherMovement == 0) {
                        telemetry.addLine("2");
                        telemetry.update();
                        allowOtherMovement = 2;
                    }
                    if (gamepad1.right_stick_x != 0 && allowOtherMovement == 0) {
                        telemetry.addLine("3");
                        telemetry.update();
                        allowOtherMovement = 3;
                    }
                }


        }catch (IOException e) {
            telemetry.addData("Error", "IOException: " + e.getMessage());
            telemetry.update();
        } finally {
            if (logger != null) {
                logger.close();
            }
            if (textOutputLogger != null) {
                textOutputLogger.close();
            }
        }
        if (debug && logger != null && textOutputLogger != null) {
            textOutputLogger.close();
            logger.close();
            telemetry.addLine("logger close");
            telemetry.update();
        }
    }


    /*
    public void lowerArm() {
        armServo.setPosition(0.55);
        elbowServo.setPosition(0.32485);
    }

    public void tapePlace() {
        restArm();
        sleep(1000);
        armServo.setPosition(0.55);
        elbowServo.setPosition(0.27);
        sleep(1000);
        claw1.setPosition(0.0);
        sleep(750);
        armServo.setPosition(0.55);
        elbowServo.setPosition(0.3075);
        sleep(750);
        claw1.setPosition(0.12);
        sleep(1000);
        restArm();
    }

    public void restArm() {
        armServo.setPosition(0.4927);
        elbowServo.setPosition(0.50);
    }
    protected void backdropPlace() {
        claw1.setPosition(0);
        sleep(1000);
        armServo.setPosition(0.4927);
        elbowServo.setPosition(0.5483);
    }
    protected void backdropPlaceHigh() {
        armServo.setPosition(0.005);
        elbowServo.setPosition(0.0622);
        sleep(750);
        claw1.setPosition(0);
        sleep(750);
        armServo.setPosition(0.4927);
        elbowServo.setPosition(0.5483);
    }*/
    private void moveBot(float vertical, float pivot, float horizontal) {
        pivot *= 0.5;
        rf_drive.setPower(powerFactor * (-pivot + (vertical - horizontal)));
        rb_drive.setPower(powerFactor * (-pivot + vertical + horizontal));
        lf_drive.setPower(powerFactor * (pivot + vertical + horizontal));
        lb_drive.setPower(powerFactor * (pivot + (vertical - horizontal)));

    }

}






