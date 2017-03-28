package org.firstinspires.ftc.team10940;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutoOpEncoder", group="Apostrophe")
@Disabled
public class ApostropheAutoOp2 extends LinearOpMode {
    private ApostropheHardware robot   = new ApostropheHardware();
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
        robot.init(hardwareMap);

        waitForStart();

        driveCm(58, 0.5, 1000);
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
            newLeft = robot.leftMotor.getCurrentPosition() + (int)(left);
            newRight = robot.rightMotor.getCurrentPosition() + (int)(right);

            robot.leftMotor.setTargetPosition(newLeft);
            robot.rightMotor.setTargetPosition(newRight);

            robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();

            robot.leftMotor.setPower(Math.abs(speed));
            robot.rightMotor.setPower(Math.abs(speed));

            while(opModeIsActive() &&
                    (runtime.seconds() < timeout) &&
                    (robot.leftMotor.isBusy() && robot.rightMotor.isBusy())) {
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeft,  newRight);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftMotor.getCurrentPosition(),
                        robot.rightMotor.getCurrentPosition());
                telemetry.update();
            }

            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);

            robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    private void driveTime(double time, double speed, double timeout) {
        robot.leftMotor.setPower(DRIVE_SPEED);
        robot.rightMotor.setPower(DRIVE_SPEED);
        runtime.reset();

        while(opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Path", "1: %2.5f S Elapsed", runtime.seconds());
        }

        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
    }
}