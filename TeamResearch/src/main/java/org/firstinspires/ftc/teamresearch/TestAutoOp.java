package org.firstinspires.ftc.teamresearch;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Auto 1", group="test")
//@Disabled

public class TestAutoOp extends LinearOpMode {
    private TestHardware robot   = new TestHardware();
    private ElapsedTime runtime = new ElapsedTime();

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
        robot.init(hardwareMap);

        waitForStart();
        runtime.reset();

        //driveTime(1.75, 0.3, 0);

//        while(opModeIsActive()) {
//            telemetry.addData("Status", "Run Time: " + runtime.toString());
//
//            telemetry.update();
//            idle();
//        }

        driveCm(66, 1, 1000);
    }

    private void driveCm(double length, double speed, double timeout) {
        driveEncode(length * COUNTS_PER_CM, length * COUNTS_PER_CM, 1.0, timeout);
    }

    private void driveInch(double length, double speed, double timeout) {
        driveEncode(length * COUNTS_PER_INCH, length * COUNTS_PER_INCH, 1.0, timeout);
    }

    private void driveEncode(double left, double right, double speed, double timeout) {
        int newLeft;
        int newRight;

        if(opModeIsActive()) {
            newLeft = robot.left.getCurrentPosition() + (int)(left);
            newRight = robot.right.getCurrentPosition() + (int)(right);

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

    private void driveTime(double sec, double pow, double timeout) {
        double newTime;

        if(opModeIsActive()) {
            newTime = runtime.time() + (sec * 1);

            robot.left.setPower(pow);
            robot.right.setPower(pow);

            while(opModeIsActive() &&
                    (runtime.time() < newTime)) {
                telemetry.addData("Elapsed Time", newTime - runtime.time());
            }

            robot.left.setPower(0);
            robot.right.setPower(0);
        }
    }
}