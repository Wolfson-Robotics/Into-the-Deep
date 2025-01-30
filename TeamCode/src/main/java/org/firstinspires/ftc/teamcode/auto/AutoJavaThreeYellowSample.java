package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AutoJavaThreeYellowSample", group = "Auto")
public class AutoJavaThreeYellowSample extends AutoJava {

    public AutoJavaThreeYellowSample() {
        super(true);
    }

    @Override
    public void runOpMode() {
        this.commonAutoInit();
/*
        // preloaded sample init
        sleep(150);
        runTasksAsync(
                () -> topBasketLift(),
                () -> {
                    moveBot(3.55, 0, 0, 0.65);
                    sleep(150);
                    moveBot(15.819595645412130745, 0.65, 0, 0);
                }
                );
        sleep(150);
        // preloaded sample basket
        moveBot(4.15, 0.5, 0, 0);
        sleep(150);
        sampleInBasket();
        sleep(150);
        arm.setPosition(0.6);
        restArm();
        sleep(350);
        moveBot(5.15, -0.5, 0, 0);
        sleep(250);
        // right sample init basket -> ready to start right sample
        runTasksAsync(
                () -> restLift(),
                // setPosition claw 0.3
                () -> moveServo(claw, 0.3),
                () -> {
                    // turnBot 94.913043478260876
                    turnBot(90);
                    sleep(250);
                    moveBot(1.7712286158631414, 0, 0, 1);
                    sleep(50);
                    // moveBot 19.267 1 0 0
                    // moveBot 17.267 1 0 0
                    moveBot(18.367, 1, 0, 0);
                }
                );
        // right sample grab sample
        sleep(150);
        grabSample();
        sleep(350);
        moveBot(2, 1, 0, 0);
        sleep(250);
        // turnBot -2
        // sleep 350
        // right sample turn back to go to basket
        runTasksAsync(
                () -> restArm(),
                () -> topBasketLift(),
                () -> {
                    // turnBot -135.086956521739131
                    // turnBot -125.086956521739131
                    // turnBot -119.086956521739131
                    turnBot(-126.5);
                    sleep(200);
                    // moveBot 18.964541213063764 0.65 0 0
                    // moveBot 18.964541213063764 0.65 0 0
                    // moveBot 16.964541213063764 0.65 0 0
                    moveBot(15, 0.65, 0, 0);
                    // moveBot 15.964541213063764 0.8 0 0
                }
                );
        sleep(150);
        // right sample basket operation
        // moveBot 2 0.65 0 0
        moveBot(4, 0.65, 0, 0);
        sleep(150);
        sampleInBasket();
        sleep(350);
        restArm();
        sleep(350);
        // moveBot 2.9937791601866253 -0.6 0 0
        // moveBot 5 -0.6 0 0
        moveBot(6, -0.6, 0, 0);
        sleep(250);
        // middle sample init go to sample
        runTasksAsync(
                () -> restLift(),
                () -> moveServo(claw, 0.3),
                // setPosition claw 0.3
                () -> {
                    // turnBot 147.07521739130437
                    turnBot(141.07521739130437);
                    sleep(150);
                    // moveBot 9.995 1 0 0
                    // moveBot 9.665 1 0 0
                    // moveBot 8.885 1 0 0
                    moveBot(7.125, 1, 0, 0);
                }
                );
        // middle sample grab sample
        sleep(150);
        grabSample();
        sleep(300);
        // middle sample go to basket
        runTasksAsync(
                () -> restArm(),
                () -> topBasketLift(),
                () -> {
                    turnBot(165.11739130434784);
                    sleep(150);
                    // moveBot 8.947900466562984 0.65 0 0
                    // moveBot 8.617900466562984 0.65 0 0
                    //moveBot 7.4 0.65 0 0
                    // moveBot 6.62 0.65 0 0
                    moveBot(8, 0.65, 0, 0);
                    // moveBot 6.182099533437016 0.65 0 0
                    turnBot(65.926086956521742);
                }
                );
        // middle sample basket operation
        sleep(150);
        // moveBot 7.455 0.65 0 0
        moveBot(10.5, 0.65, 0, 0);
        sleep(150);
        sampleInBasket();
        sleep(350);
        restArm();
        sleep(350);
        // moveBot 5.91601866251944 -0.6 0 0
        // moveBot 7.91601866251944 -0.6 0 0
        moveBot(11, -0.6, 0, 0);
        sleep(350);
        // left sample init go to sample
        runTasksAsync(
                () -> restLift(),
                // setPosition claw 0.3
                () -> moveServo(claw, 0.3),
                () -> {
                    // turnBot 104.86956521739131
                    // turnBot 102.86956521739131
                    // turnBot 95.86956521739131
                    // turnBot 100.9695652173913
                    turnBot(80);
                    sleep(150);
                }
        );*/



        // preloaded sample init
        sleep(150);
        runTasksAsync(
                () -> topBasketLift(),
                () -> {
                    moveBot(3.55, 0, 0, 0.65);
                    sleep(150);
                    moveBot(15.819595645412130745, 0.65, 0, 0);
                }
        );
        sleep(150);
        // preloaded sample basket
        moveBot(4.15, 0.5, 0, 0);
        sleep(150);
        sampleInBasket();
        sleep(150);
        arm.setPosition(0.6);
        restArm();
        sleep(350);
        moveBot(5.15, -0.5, 0, 0);
        sleep(250);
        // right sample init basket -> ready to start right sample
        runTasksAsync(
                () -> restLift(),
                // setPosition claw 0.3
                () -> moveServo(claw, 0.3),
                () -> {
                    // turnBot 94.913043478260876
                    turnBot(90);
                    sleep(250);
                    moveBot(1.7712286158631414, 0, 0, 0.75);
                    sleep(50);
                    // moveBot 19.267 0.75 0 0
                    // moveBot 17.267 0.75 0 0
                    moveBot(18.367, 0.75, 0, 0);
                }
        );
        // right sample grab sample
        sleep(150);
        grabSample();
        sleep(350);
        // moveBot 2 0.75 0 0
        // sleep 250
        // turnBot -2
        // sleep 350
        // right sample turn back to go to basket
        runTasksAsync(
                () -> restArm(),
                () -> topBasketLift(),
                () -> {
                    // turnBot -135.086956521739131
                    // turnBot -125.086956521739131
                    // turnBot -119.086956521739131
                    turnBot(-126.5);
                    sleep(200);
                    // moveBot 18.964541213063764 0.65 0 0
                    // moveBot 18.964541213063764 0.65 0 0
                    // moveBot 16.964541213063764 0.65 0 0
                    moveBot(15, 0.65, 0, 0);
                    // moveBot 15.964541213063764 0.8 0 0
                }
        );
        sleep(150);
        // right sample basket operation
        // moveBot 2 0.65 0 0
        moveBot(4, 0.65, 0, 0);
        sleep(150);
        sampleInBasket();
        sleep(350);
        restArm();
        sleep(350);
        // moveBot 2.9937791601866253 -0.6 0 0
        // moveBot 5 -0.6 0 0
        moveBot(6, -0.6, 0, 0);
        sleep(250);
        // middle sample init go to sample
        runTasksAsync(
                () -> restLift(),
                () -> moveServo(claw, 0.3),
                // setPosition claw 0.3
                () -> {
                    // turnBot 147.07521739130437
                    // turnBot 141.07521739130437
                    turnBot(136);
                    sleep(150);
                    // moveBot 9.995 0.75 0 0
                    // moveBot 9.665 0.75 0 0
                    // moveBot 8.885 0.75 0 0
                    moveBot(7.125, 0.75, 0, 0);
                }
        );
        // middle sample grab sample
        sleep(150);
        grabSample();
        sleep(300);
        // middle sample go to basket
        runTasksAsync(
                () -> restArm(),
                () -> topBasketLift(),
                () -> {
                    turnBot(165.11739130434784);
                    sleep(150);
                    // moveBot 8.947900466562984 0.65 0 0
                    // moveBot 8.617900466562984 0.65 0 0
                    //moveBot 7.4 0.65 0 0
                    // moveBot 6.62 0.65 0 0
                    moveBot(8, 0.65, 0, 0);
                    // moveBot 6.182099533437016 0.65 0 0
                    turnBot(65.926086956521742);
                }
        );
        // middle sample basket operation
        sleep(150);
        // moveBot 7.455 0.65 0 0
        // moveBot 10.5 0.65 0 0
        moveBot(9.85, 0.65, 0, 0);
        sleep(150);
        sampleInBasket();
        sleep(350);
        restArm();
        sleep(350);
        // moveBot 11 -0.6 0 0
        moveBot(10.35, -0.6, 0, 0);
        sleep(350);
        moveMotor(lift, 0, 1.5);
    }
}
