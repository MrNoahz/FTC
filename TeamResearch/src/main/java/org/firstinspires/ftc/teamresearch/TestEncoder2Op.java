package org.firstinspires.ftc.teamresearch;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

@TeleOp(name="BetterEncoder", group="test")
//@Disabled

public class TestEncoder2Op extends LinearOpMode {
    private TestHardware robot  = new TestHardware();
    private ElapsedTime  runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     WHEEL_DIAMETER_CM       = 10.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     COUNTS_PER_CM           = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_CM * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;



    @Override
    public void runOpMode() throws InterruptedException {
        boolean[][] buttons = new boolean[][] {
                // last state, current state
                { false, gamepad1.dpad_up },    // 0 dpad up
                { false, gamepad1.dpad_down },  // 1 dpad down
                { false, gamepad1.dpad_left },  // 2 dpad left
                { false, gamepad1.dpad_right }, // 3 dpad right
                { false, gamepad1.a },          // 4 A
                { false, gamepad1.b },          // 5 B
                { false, gamepad1.start }       // 6 start
        };

        ArrayList<Integer> directions = new ArrayList<Integer>();
        boolean testRunning = false;

        robot.init(hardwareMap);

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        runtime.reset();

        while(opModeIsActive()) {
            for(int j = 0; j < buttons.length; j++) {
                switch(j) {
                    case 0:
                        buttons[j][1] = gamepad1.dpad_up;
                        break;
                    case 1:
                        buttons[j][1] = gamepad1.dpad_down;
                        break;
                    case 2:
                        buttons[j][1] = gamepad1.dpad_left;
                        break;
                    case 3:
                        buttons[j][1] = gamepad1.dpad_right;
                        break;
                    case 4:
                        buttons[j][1] = gamepad1.a;
                        break;
                    case 5:
                        buttons[j][1] = gamepad1.b;
                        break;
                    case 6:
                        buttons[j][1] = gamepad1.start;
                        break;
                }
            }

            for(int i = 0; i < buttons.length; i++) {
                if(buttons[i][0] && !buttons[i][1] && !testRunning) {
                    if(i == 6)
                        testRunning = true;

                    directions.add(i);
                }

                //String directionString = TextUtils.join(", ", directions);
                //telemetry.addData("Directions", directionString);
                telemetry.addData("Buttons", "Number: " + String.valueOf(i) + ", Last state: " + String.valueOf(buttons[i][0]) + ", Current State: " + String.valueOf(buttons[i][1]));
                //telemetry.addData("Loop", "Button " + String.valueOf(i) + " " + buttons[i][0]);

                buttons[i][0] = buttons[i][1];
            }

            if(testRunning) {
                for(Object dir : directions) {
                    switch((Integer) dir) {
                        case 0:
                            drive(DRIVE_SPEED, 50, 50, 3.0);
                            break;
                        case 1:
                            drive(DRIVE_SPEED, -50, -50, 3.0);
                            break;
                        case 2:
                            drive(DRIVE_SPEED, -1, 1, 3.0);
                            break;
                    }
                }

                testRunning = false;
            }

            telemetry.addData("Running Thing", testRunning);
            telemetry.update();

            idle();
        }

        //drive(DRIVE_SPEED, 50, 50, 3.0);
    }

    public void drive(double speed, double left, double right, double timeout) {
        int newLeft;
        int newRight;

        if(opModeIsActive()) {
            newLeft = robot.left.getCurrentPosition() + (int)(left * COUNTS_PER_CM);
            newRight = robot.right.getCurrentPosition() + (int)(right * COUNTS_PER_CM);

            robot.left.setTargetPosition(newLeft);
            robot.right.setTargetPosition(newRight);

            robot.left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();

            robot.left.setPower(Math.abs(speed));
            robot.right.setPower(Math.abs(speed));

            while(opModeIsActive() &&
                    (runtime.seconds() < timeout) &&
                    (robot.left.isBusy() && robot.right.isBusy())) {
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeft,  newRight);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.left.getCurrentPosition(),
                        robot.right.getCurrentPosition());
                telemetry.update();
            }

            robot.left.setPower(0);
            robot.right.setPower(0);

            robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}