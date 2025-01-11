package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.RobotBase;
import org.firstinspires.ftc.teamcode.old.PixelDetection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        powerFactor = 0.6;
        powerFactor = 0.725;
        arm.setPosition(0.6);
        claw.setPosition(this.closedClaw);
    }


    // pos vertical is forward, neg vertical is negative
    // pos pivot is clockwise, neg pivot is counterclockwise
    // pos horizontal is right, neg horizontal is left
    // notes:
    // rf_drive, when going forward, goes into the negative direction positionally
    protected void moveBotOld(double distIN, double vertical, double pivot, double horizontal) {

        // 23 motor tics = 1 IN
        int motorTics;
        int posNeg = (vertical >= 0) ? 1 : -1;

        rf_drive.setPower(powerFactor * (-pivot + (vertical - horizontal)));
        rb_drive.setPower(powerFactor * (-pivot + vertical + horizontal));
        lf_drive.setPower(powerFactor * (pivot + vertical + horizontal));
        lb_drive.setPower(powerFactor * (pivot + (vertical - horizontal)));

        if (horizontal != 0) {
            posNeg = (horizontal > 0) ? 1 : -1;
            motorTics = lf_drive.getCurrentPosition() + (int) ((distIN * intCon) * (posNeg));
            if (posNeg == 1) {
                // right goes negative
                while ((lf_drive.getCurrentPosition() < motorTics) && opModeIsActive()) {
                    idle();
                }
            } else {
                // left goes positive
                while ((lf_drive.getCurrentPosition() > motorTics) && opModeIsActive()) {
                    idle();
                }
            }
        } else {
            posNeg = vertical >= 0 ? -1 : 1;
            motorTics = rf_drive.getCurrentPosition() + (int) ((distIN * intCon) * posNeg);
            if (posNeg == -1) {
                while (rf_drive.getCurrentPosition() > motorTics && opModeIsActive()) {
                    idle();
                }
            } else {
                while ((rf_drive.getCurrentPosition() < motorTics) && opModeIsActive()) {
                    idle();
                }
            }

        }
        removePower();

    }


    // 12 inches (or a foot) is 73.6770894730908 robot inches
    protected void moveBot(double in, double vertical, double pivot, double horizontal) {
//        moveBotOld((in/12d) * 73.6770894730908, vertical, pivot, horizontal);
        moveBotOld(in*ticsPerInch, vertical, pivot, horizontal);
    }

    protected void moveBotDiag(double horizIn, double vertIn, double vertical, double horizontal) {
        double rfPower = vertical - horizontal, rbPower = vertical + horizontal,
                lfPower = vertical + horizontal, lbPower = vertical - horizontal;
//        double rfPower = (vertical * vertIn) + (-horizontal * horizIn);
//        double rbPower = (vertical * vertIn) + (horizontal * horizIn);

        int vertNeg = (vertical >= 0) ? -1 : 1, horizNeg = (horizontal > 0) ? 1 : -1;
        int vertTics = rf_drive.getCurrentPosition() + (int) ((vertIn * intCon) * vertNeg);
        int horizTics = lf_drive.getCurrentPosition() + (int) ((horizIn * intCon) * (horizNeg));
        Supplier<Boolean> vertCond = vertNeg == -1 ? () -> (rf_drive.getCurrentPosition() > vertTics && opModeIsActive()) : () -> (rf_drive.getCurrentPosition() < vertTics) && opModeIsActive();
        Supplier<Boolean> horizCond = horizNeg == 1 ? () -> (lf_drive.getCurrentPosition() < horizTics) && opModeIsActive() : () -> (lf_drive.getCurrentPosition() > horizTics) && opModeIsActive();
/*
        boolean goVert = true, goHoriz = true;
        while (true) {
            if (!vertCond.get()) {
                rfPower = (-horizontal * horizIn);
                rbPower = (horizontal * horizIn);
                goVert = false;
            }
            if (!horizCond.get()) {
                rfPower = (vertical * vertIn);
                rbPower = (vertical * vertIn);
                goHoriz = false;
            }
            if (!goVert && !goHoriz) {
                break;
            }
            rf_drive.setPower(powerFactor * rfPower);
            rb_drive.setPower(powerFactor * rbPower);
            lf_drive.setPower(powerFactor * rbPower);
            lb_drive.setPower(powerFactor * rfPower);
        }*/
//        /*
        while (vertCond.get() || horizCond.get()) {
            if (!vertCond.get()) {
//                rfPower = (-horizontal * horizIn);
//                rbPower = (horizontal * horizIn);
                rfPower = -horizontal;
                rbPower = horizontal;
            }
            if (!horizCond.get()) {
//                rfPower = (vertical * vertIn);
//                rbPower = (vertical * vertIn);
                rfPower = vertical;
                rbPower = vertical;
            }
            rf_drive.setPower(powerFactor * rfPower);
            rb_drive.setPower(powerFactor * rbPower);
            lf_drive.setPower(powerFactor * rbPower);
            lb_drive.setPower(powerFactor * rfPower);
//            idle();
        }
//        */
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
        double distIN = (Math.abs((distUnit * ((degrees*1.75))) / 90))*degConv;
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


    protected void runTasksAsync(List<Runnable> fns) {
        ExecutorService executorService = Executors.newFixedThreadPool(fns.size()); // Thread pool
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        fns.forEach(fn -> futures.add(CompletableFuture.runAsync(fn, executorService)));

        CompletableFuture<Void> allThreads = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allThreads.join();
        executorService.shutdown();
    }
    protected void runTasksAsync(Runnable... fns) {
        runTasksAsync(Arrays.stream(fns).collect(Collectors.toList()));
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


    protected void grabSample() {
        arm.setPosition(0.9300000000000002);
        sleep(300);
        claw.setPosition(0.46);
    }
    protected void sampleInBasket() {
        arm.setPosition(0.76);
        sleep(150);
        claw.setPosition(0.3);
        sleep(150);
        arm.setPosition(0.609);
    }
    protected void restLift() {
        moveMotor(lift, 0, 1.5);
    }
    protected void topBasketLift() {
        moveMotor(lift, -4220, 1.5);
    }
    protected void restArm() {
        arm.setPosition(0.6094444444444445);
    }
    // stub methods for later
    protected void placeSpecimen() {

    }


    public abstract void runOpMode();


}
