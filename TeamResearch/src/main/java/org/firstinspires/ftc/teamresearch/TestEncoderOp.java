package org.firstinspires.ftc.teamresearch;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/* OpMode for testing purposes */

@Autonomous(name="EncoderOp", group="test")
//@Disabled
public class TestEncoderOp extends LinearOpMode {

    private TestHardware robot   = new TestHardware();
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
        //((FtcRobotControllerActivity) hardwareMap.appContext).getAccelerometer();

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Path", "Starting at %7d :%7d",
                robot.left.getCurrentPosition(),
                robot.right.getCurrentPosition());
        telemetry.update();

        waitForStart();
        
        //driveCm(DRIVE_SPEED, 50, 50, 3.0);
        //driveCm(DRIVE_SPEED, -0.015, 0.015, 3.0);
        driveEncode(DRIVE_SPEED, 7200, 7200, 3.0);
        driveEncode(DRIVE_SPEED, -720, 720, 3.0);
    }

    public void driveCm(double speed, double left, double right, double timeout) {
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

    public void driveEncode(double speed, double left, double right, double timeout) {
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
}
