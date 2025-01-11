package org.firstinspires.ftc.teamcode.auto;

public class AutoJavaRedSample extends AutoJava {

    public AutoJavaRedSample() {
        super(true);
    }

    @Override
    public void runOpMode() {
        this.commonAutoInit();

        runTasksAsync(
                () -> topBasketLift(),
                () -> {
                    moveBot(3.55, 0, 0, 0.75);
                    sleep(150);
                    moveBot(15.819595645412130745, 0.75, 0, 0);
                }
        );
        sleep(150);

        // preloaded sample basket
        moveBot(4.15, 0.5, 0, 0);
        sleep(150);
        sampleInBasket();
        sleep(150);
        moveBot(5.15, -0.5, 0, 0);
        sleep(150);

        // right sample init basket -> ready to start right sample
        runTasksAsync(
                () -> restLift(),
                () -> claw.setPosition(0.3),
                () -> {
                    turnBot(94.913043478260876);
                    sleep(250);
                    moveBot(1.6712286158631414, 0, 0, 1);
                    moveBot(19.267, 1, 0, 0);
                }
        );


        // right sample grab sample
        sleep(150);
        grabSample();
        // right sample turn back to go to basket
        sleep(250);
        runTasksAsync(
                () -> restArm(),
                () -> topBasketLift(),
                () -> {
                    turnBot(-125.086956521739131);
                    sleep(200);
                    moveBot(16.964541213063764, 0.75, 0, 0);
                }
        );
        sleep(150);


        // right sample basket operation
        moveBot(2, 0.65, 0, 0);
        sleep(150);
        sampleInBasket();
        sleep(150);
        moveBot(2.9937791601866253, -0.6, 0, 0);
        sleep(50);


        // middle sample init
        runTasksAsync(
                () -> restLift(),
                () -> claw.setPosition(0.3),
                () -> {
                    turnBot(147.07521739130437);
                    sleep(150);
                    moveBot(9.5, 1, 0, 0);
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
                    moveBot(7.947900466562984, 0.75, 0, 0);
                    turnBot(65.926086956521742);
                }
        );

        // middle sample basket operation
        sleep(150);
        moveBot(7.455, 0.65, 0, 0);
        sleep(150);
        sampleInBasket();
        sleep(150);
        moveBot(5.91601866251944, -0.6, 0, 0);
        sleep(150);

        // left sample init go to sample
        runTasksAsync(
                () -> restLift(),
                () -> claw.setPosition(0.3),
                () -> {
                    turnBot(100.9695652173913);
                    sleep(150);
                    moveBot(12.56096423017107214, 1, 0, 0);
                    sleep(150);
                    moveBot(1.45, 0, 0, -1);
                }
        );

        // left sample grab sample
        sleep(150);
        /*
        arm.setPosition(0.9000000000000001);
        sleep(300);
        claw.setPosition(0.46);
         */
        grabSample();
        sleep(300);

        // left sample go to basket
        runTasksAsync(
                () -> restArm(),
                () -> topBasketLift(),
                () -> {
                    turnBot(-167.08695652173914);
                    sleep(150);
                    moveBot(13.975116640746501, 0.75, 0, 0);
                    sleep(150);
                    turnBot(68.30434782608696);
                }
        );

        // left sample basket operation
        moveBot(4, 0.7, 0, 0);
        sleep(150);
        moveBot(2.7433903576982894, 1, 0, 0);
        sleep(150);
        sampleInBasket();
        sleep(150);
        moveBot(3.9937791601866253, -0.6, 0, 0);
        sleep(50);
        restLift();
    }
}
