package org.firstinspires.ftc.team11342;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Punctuation", group="punctuation")  // @Autonomous(...) is the other common choice
public class PunctuationDriveOp extends LinearOpMode {

    private PunctuationHardware robot   = new PunctuationHardware();
    private ElapsedTime         runtime = new ElapsedTime();

    private boolean buttons[];

    private int spinnerOn;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();
        runtime.reset();

        buttons = new boolean[1];

        spinnerOn = 0;

        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());

            robot.rightMotor.setPower(-gamepad1.right_stick_y);
            robot.leftMotor.setPower(-gamepad1.left_stick_y);

            robot.spinner.setPower(spinnerOn);

            if(gamepad1.a && !buttons[0]) {
                spinnerOn = 1 - spinnerOn;
                robot.spinner.setPower(spinnerOn);

                buttons[0] = true;
            } else if(!gamepad1.a) {
                buttons[0] = false;
            }

            telemetry.update();

            idle();
        }
    }
}
