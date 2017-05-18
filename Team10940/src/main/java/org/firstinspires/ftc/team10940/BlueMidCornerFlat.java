package org.firstinspires.ftc.team10940;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="BlueMidCornerFlat", group="Apostrophe")
//@Disabled

public class BlueMidCornerFlat extends EncoderAutoBase {
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
        Blue Mid Corner Flat (not angled, delayed for penalty)
         */
        sleep(10 * 1000);
        drive(1, 32, 0.5);
        turn(45, 1, 0.5);
        drive(1, 50, 0.5);
        launchSystem();
        drive(1, 110, 0.5);

    }
}
