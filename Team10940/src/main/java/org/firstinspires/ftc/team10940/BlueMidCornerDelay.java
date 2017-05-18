package org.firstinspires.ftc.team10940;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="BlueMidCornerDelay", group="Apostrophe")
//@Disabled

public class BlueMidCornerDelay extends EncoderAutoBase {
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
        Blue Mid Corner Delay(already angled, delayed)
         */
        sleep(17000);
        drive(1, 45, 0.5);
        launchSystem();
        drive(1, 120, 0.5);


    }
}
