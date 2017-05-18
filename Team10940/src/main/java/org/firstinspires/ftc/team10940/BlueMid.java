package org.firstinspires.ftc.team10940;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="BlueMid", group="Apostrophe")
//@Disabled

public class BlueMid extends EncoderAutoBase {
    /* Declare OpMode members. */

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        runtime.reset();

        /*
        Blue Mid (can not be delayed)
         */
        drive(1, 20, 0.5);//drive to launch position
        launchSystem();//launch
        drive(1, 12, 0.5);//drive remainder of 1 ft
        turn(-90, 1, 0.5);//turn right
        drive(1, 32, 0.5);//drive another ft
        turn(-90, 1, 0.5);//turn right
        drive(1, 50, 0.5);//drive onto middle

    }
}

