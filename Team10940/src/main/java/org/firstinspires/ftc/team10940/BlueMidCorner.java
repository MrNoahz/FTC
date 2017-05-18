package org.firstinspires.ftc.team10940;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="BlueMidCorner", group="Apostrophe")
//@Disabled

public class BlueMidCorner extends EncoderAutoBase {
    /* Declare OpMode members. */
    private ApostropheHardware robot   = new ApostropheHardware();
    private ElapsedTime     runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();
        runtime.reset();

        /*
        Blue Mid Corner (already angled, not delayed)
         */
        drive(1, 45, 0.5);
        launchSystem();
        drive(1, 120, 0.5);
    }
}
