package org.firstinspires.ftc.teamcode.auto;

import static org.firstinspires.ftc.teamcode.auto.instruct.AutoInstructionConstants.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.instruct.AutoInstructionConstants;
import org.firstinspires.ftc.teamcode.auto.instruct.AutoInstructionReader;
import org.firstinspires.ftc.teamcode.auto.instruct.AutoOperation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Autonomous(name = "AutoInstructionReaderJava", group = "Auto")
public class AutoInstructionReaderJava extends AutoJava {

    private final ArrayList<?> dummyArr = new ArrayList<>();

    public AutoInstructionReaderJava() { super(true); }

    @Override
    public void runOpMode() {

        initMotors();
        telemetry.addLine("Waiting for start");
        telemetry.update();
        waitForStart();


        telemetry.addLine("Parsing instructions...");
        telemetry.update();
        try {

            AutoInstructionReader reader = new AutoInstructionReader(AutoInstructionConstants.autoInstructPath);

            AutoOperation autoOperation;
            boolean skipOperations = false;
            while ((autoOperation = reader.readLine()) != null) {


                final String operationName = autoOperation.getOperationName();
                final ArrayList<String> operationArgs = autoOperation.getOperationArgs();

                boolean skipCurrOperation = false;
                switch(operationName) {
                    case singleCommentMarker:
                        skipCurrOperation = true;
                        break;
                    case multiCommentBegin:
                        skipOperations = true;
                        break;
                    case multiCommentEnd:
                        skipOperations = false;
                        skipCurrOperation = true;
                        break;
                    case stopMarker:
                        if (skipCurrOperation || skipOperations) {
                            break;
                        }
                        return;
                }
                if (skipCurrOperation || skipOperations) {
                    continue;
                }



                ArrayList<?> finalOperationArgs = new ArrayList<>();
                switch (operationName) {

                    case "moveBot":
                        ArrayList<Double> moveBotArgs = operationArgs.stream()
                                .map(String::valueOf)
                                .map(Double::parseDouble)
                                .collect(Collectors.toCollection(ArrayList::new));
                        moveBot(moveBotArgs.get(0), moveBotArgs.get(1), moveBotArgs.get(2), moveBotArgs.get(3));
                        break;
                    case "moveBotOld":
                        ArrayList<Double> moveBotOldArgs = operationArgs.stream()
                                .map(String::valueOf)
                                .map(Double::parseDouble)
                                .collect(Collectors.toCollection(ArrayList::new));
                        moveBotOld(moveBotOldArgs.get(0), moveBotOldArgs.get(1), moveBotOldArgs.get(2), moveBotOldArgs.get(3));
                        break;
                    case "moveBotDiag":
                        ArrayList<Double> moveBotDiagArgs = operationArgs.stream()
                                .map(String::valueOf)
                                .map(Double::parseDouble)
                                .collect(Collectors.toCollection(ArrayList::new));
                        moveBotDiag(moveBotDiagArgs.get(0), moveBotDiagArgs.get(1), moveBotDiagArgs.get(2), moveBotDiagArgs.get(3));
                        break;
                    case "turnBot":
                        turnBot(Double.parseDouble(operationArgs.get(0)));
                        break;
                    case "liftBot":
                        if (operationArgs.size() == 2) {
                            liftBot(Integer.parseInt(operationArgs.get(0)), Double.parseDouble(operationArgs.get(1)));
                        } else {
                            liftBot(Integer.parseInt(operationArgs.get(0)));
                        }
                        break;

                    case "sleep":
                        sleep(Long.parseLong(operationArgs.get(0)));
                        break;

                    case "setPosition":
                        double servoPos = Double.parseDouble(operationArgs.get(1));
                        switch(operationArgs.get(0)) {
                            case "arm":
                                arm.setPosition(servoPos);
                                break;
                            case "claw":
                                claw.setPosition(servoPos);
                                break;
                        }
                        break;

                    case "setPower":
                        double powerFac = Double.parseDouble(operationArgs.get(1));
                        switch(operationArgs.get(0)) {
                            case "rf_drive":
                                rf_drive.setPower(powerFac);
                                break;
                            case "rb_drive":
                                rb_drive.setPower(powerFac);
                                break;
                            case "lf_drive":
                                lf_drive.setPower(powerFac);
                                break;
                            case "lb_drive":
                                lb_drive.setPower(powerFac);
                                break;
                            case "lift":
                                lift.setPower(powerFac);
                                break;
                        }
                        break;

                    case "moveServo":
                        double servoPosParam = Double.parseDouble(operationArgs.get(1));
                        long servoSpeed = Long.parseLong(operationArgs.get(2));
                        switch(operationArgs.get(0)) {
                            case "arm":
                                moveServo(arm, servoPosParam, servoSpeed);
                                break;
                            case "claw":
                                moveServo(claw, servoPosParam, servoSpeed);
                                break;
                        }
                        break;

                    case "moveMotor":
                        int motorPos = Integer.parseInt(operationArgs.get(1));
                        double motorSpeed = Double.parseDouble(operationArgs.get(2));
                        switch(operationArgs.get(0)) {
                            case "rf_drive":
                                moveMotor(rf_drive, motorPos, motorSpeed);
                                break;
                            case "rb_drive":
                                moveMotor(rb_drive, motorPos, motorSpeed);
                                break;
                            case "lf_drive":
                                moveMotor(lf_drive, motorPos, motorSpeed);
                                break;
                            case "lb_drive":
                                moveMotor(lb_drive, motorPos, motorSpeed);
                                break;
                            case "lift":
                                moveMotor(lift, motorPos, motorSpeed);
                                break;
                        }
                        break;

                    case "powerFactor":
                        telemetry.addLine("CHANGING POWER FACTOR TO " + operationArgs.get(0));
                        telemetry.update();
                        this.powerFactor = Double.parseDouble(operationArgs.get(0));
                        telemetry.addLine(String.valueOf(this.powerFactor));
                        telemetry.update();
                        break;


                    case "placeSpecimen":
                        placeSpecimen();
                        break;

                    default:
                        finalOperationArgs = operationArgs;
                        break;

                }



            }
            reader.close();


        } catch (IOException e) {
            telemetry.addLine("An error occurred:");
            telemetry.addLine(e.getMessage());
            telemetry.addLine(Stream.of(e.getStackTrace())
                            .map(String::valueOf)
                            .collect(Collectors.joining("\n")));
            telemetry.update();
            sleep(60000);
        }


    }






}
