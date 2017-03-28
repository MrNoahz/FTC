package org.firstinspires.ftc.team10940;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Apostrophe", group="apostrophe")
//@Disabled

public class ApostropheDriveOp extends LinearOpMode {
    private ApostropheHardware robot   = new ApostropheHardware();
    private ElapsedTime        runtime = new ElapsedTime();

    private boolean buttons[];

    private int spinnerOn;
    private boolean spinnerDir; //True forward. False backward.
    private boolean lifterDir;

    private int motorPercent;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();
        runtime.reset();

        buttons = new boolean[5];

        spinnerOn = 0;
        spinnerDir = true;

        lifterDir = true;

        motorPercent = 100;

        while(opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());

            robot.leftMotor.setPower(-gamepad1.left_stick_y * (motorPercent / 100));
            robot.rightMotor.setPower(-gamepad1.right_stick_y * (motorPercent / 100));

            robot.spinny.setPower(spinnerOn);

            //TODO make button that shifts speed of robot

            if(gamepad2.a && !buttons[0]) {
                spinnerOn = 1 - spinnerOn;
                robot.spinny.setPower(spinnerOn);

                buttons[0] = true;
            } else if(!gamepad2.a) {
                buttons[0] = false;
            }

            if(gamepad2.b && !buttons[1]) {
                spinnerDir = !spinnerDir;

                if(spinnerDir)
                    robot.spinny.setDirection(DcMotorSimple.Direction.REVERSE);
                else
                    robot.spinny.setDirection(DcMotorSimple.Direction.FORWARD);

                buttons[1] = true;
            } else if(!gamepad2.b) {
                buttons[1] = false;
            }

            robot.lifty.setPower((gamepad2.right_bumper) ? 0.75 : 0);

            if(gamepad2.left_bumper && !buttons[2]) {
                lifterDir = !lifterDir;

                if(lifterDir)
                    robot.lifty.setDirection(DcMotorSimple.Direction.REVERSE);
                else
                    robot.lifty.setDirection(DcMotorSimple.Direction.FORWARD);

                buttons[2] = true;
            } else if(!gamepad2.left_bumper) {
                buttons[2] = false;
            }

            if(gamepad2.dpad_up && !buttons[3]) {
                if(motorPercent < 100)
                    motorPercent += 5;

                buttons[3] = true;
            } else if(!gamepad2.dpad_up) {
                buttons[3] = false;
            }

            if(gamepad2.dpad_down && !buttons[4]) {
                if(motorPercent > 0)
                    motorPercent -= 5;

                buttons[4] = true;
            } else if(!gamepad2.dpad_down) {
                buttons[4] = false;
            }

            //robot.launchy.setPower((gamepad2.right_trigger > 0.80) ? 1 : 0);

            if(gamepad2.right_trigger > 0.8) {
                robot.launchy.setDirection(DcMotorSimple.Direction.FORWARD);
                robot.launchy.setPower(0.8);
            } else if(gamepad2.left_trigger > 0.8) {
                robot.launchy.setDirection(DcMotorSimple.Direction.REVERSE);
                robot.launchy.setPower(0.8);
            } else {
                robot.launchy.setPower(0);
            }

            telemetry.addData("Speed", motorPercent);

            telemetry.update();

            idle();
        }
    }
}
