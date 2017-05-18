package org.firstinspires.ftc.team10940;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="EncoderTest", group="Apostrophe")
//@Disabled

public class EncodeAuto extends EncoderAutoBase {
    /* Declare OpMode members. */
    private ApostropheHardware robot   = new ApostropheHardware();
    private ElapsedTime     runtime = new ElapsedTime();

    @Override

    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();
        runtime.reset();

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();


        telemetry.addData("Step1", "step1");
        telemetry.update();

        /*
        Test the different methods and possible outcomes
         */
        drive(1, 100, 5); //test drive method 100 centimeters
        sleep(5);
        turn(90, 1, 5); //test turn left
        sleep(5);
        turn(180, 1, 5); //test turn around
        sleep(5);
        turn(-90, 1, 5); //test turn right
        sleep(5);
        turn(45, 1, 5); //test turn



//         /*
//        Blue Mid Corner Flat Delay (not angled, delayed)
//         */
//        sleep(17);
//        drive(1, 32, 0.5);
//        turn(45, 1, 0.5);
//        drive(1, 50, 0.5);
//        launchSystem();
//        drive(1, 110, 0.5);
//
//        /*
//        Blue Goal (can not be delayed)
//         */
//         drive(1, 25, 0.5);
//        launchSystem();
//        turn(-90, 1, 0.5);
//        drive(1, 120, 0.5);
//
//        /*
//        Blue Goal Corner (does not need to be angled, delayed for penalty)
//         */
//        sleep(10);
//        drive(1, 32, 0.5);
//        turn(45, 1, 0.5);
//        drive(1, 50, 0.5);
//        launchSystem();
//        turn(-120, 1, 0.5);
//        drive(1, 180, 0.5);
//
//        /*
//        Blue Goal Corner Delay (does not need to be angled, delayed)
//         */
//        sleep(17);
//        drive(1, 32, 0.5);
//        turn(45, 1, 0.5);
//        drive(1, 50, 0.5);
//        launchSystem();
//        turn(-120, 1, 0.5);
//        drive(1, 180, 0.5);

    }
}
