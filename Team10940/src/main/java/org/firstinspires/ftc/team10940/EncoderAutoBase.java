package org.firstinspires.ftc.team10940;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

abstract class EncoderAutoBase extends LinearOpMode {
    /* Declare OpMode members. */
    ApostropheHardware robot   = new ApostropheHardware();
    ElapsedTime runtime = new ElapsedTime();

    private static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    private static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    private static final double     WHEEL_DIAMETER_CM       = 10.0;
    private static final double     COUNTS_PER_CM           = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_CM * Math.PI);

    void drive(double speed, double cm, double sleep) {
        double d = COUNTS_PER_CM * cm;

        driveEncode(d, d, speed, sleep);
    }

    void drive(double cm_left, double cm_right, double speed, double sleep) {
        double d_left = COUNTS_PER_CM * cm_left;
        double d_right = COUNTS_PER_CM * cm_right;

        driveEncode(d_left, d_right, speed, sleep);
    }

    void turn(double degrees, double speed, double sleep) {
        driveEncode(-(19 * degrees), (19 * degrees), speed, sleep);
    }

    void launchSystem() {
        robot.launchy.setPower(1);
        sleep(1000);
        robot.launchy.setPower(0);

        robot.lifty.setPower(-0.5);
        sleep(3000);
        robot.lifty.setPower(0);

        robot.launchy.setPower(1);
        sleep(1000);
        robot.launchy.setPower(0);
    }

     void driveEncode(double left, double right, double speed, double timeout) {
        int newLeft;
        int newRight;

        telemetry.addData("OPMode", opModeIsActive());

        if(opModeIsActive()) {
            telemetry.addData("LEft", robot.leftMotor.getCurrentPosition());
            telemetry.addData("RIght", robot.rightMotor.getCurrentPosition());

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

            sleep((long) timeout);
        }
    }
}