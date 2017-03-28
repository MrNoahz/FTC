package org.firstinspires.ftc.teamresearch;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.hardware.adafruit.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.util.Locale;
import static android.R.attr.angle;

@Autonomous(name="Gyro", group="test")
//@Disabled
public class TestGyroOp extends LinearOpMode {

    private TestHardware robot   = new TestHardware();
    private ElapsedTime  runtime = new ElapsedTime();

    //TODO: Move this to robot class
    private BNO055IMU imu;

    Orientation angles;
    Acceleration gravity;

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

        BNO055IMU.Parameters gyroParams = new BNO055IMU.Parameters();
        gyroParams.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        gyroParams.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        gyroParams.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
        gyroParams.loggingEnabled      = true;
        gyroParams.loggingTag          = "IMU";
        gyroParams.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(gyroParams);

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

        composeTelemetry();

        waitForStart();

        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);

//        while(opModeIsActive())
//            telemetry.update();

        driveCm(10, 0.5, 1000);
        turn(90, 0.5, 0);
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

    private void turn(double direction, double speed, double timeout) {
//        int newLeft;
//        int newRight;

        double newAngle;

        if(opModeIsActive()) {
            //newLeft = robot.left.getCurrentPosition() + (int)(left);
            //newRight = robot.right.getCurrentPosition() + (int)(right);

            newAngle = AngleUnit.DEGREES.normalize(AngleUnit.DEGREES.fromUnit(angles.angleUnit, angle)) + direction;

            telemetry.addData("Angles", "Current Angle: " + AngleUnit.DEGREES.normalize(AngleUnit.DEGREES.fromUnit(angles.angleUnit, angle)));
            telemetry.addData("Angles", "New Angle: " + newAngle);

//            robot.left.setTargetPosition(newLeft);
//            robot.right.setTargetPosition(newRight);

//            robot.left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();

            robot.left.setPower(Math.abs(speed));
            robot.right.setPower(Math.abs(speed));

//            while(opModeIsActive() &&
//                    (runtime.seconds() < timeout) &&
//                    (robot.left.isBusy() && robot.right.isBusy())) {
//                telemetry.addData("Path1",  "Running to %7d :%7d", newLeft,  newRight);
//                telemetry.addData("Path2",  "Running at %7d :%7d",
//                        robot.left.getCurrentPosition(),
//                        robot.right.getCurrentPosition());
//                telemetry.update();
//            }

            while(opModeIsActive() &&
                    (runtime.seconds() < timeout) &&
                    (robot.left.isBusy() && robot.right.isBusy()) &&
                    AngleUnit.DEGREES.normalize(AngleUnit.DEGREES.fromUnit(angles.angleUnit, angle)) < newAngle) {

                telemetry.update();
            }

            robot.left.setPower(0);
            robot.right.setPower(0);

            robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    private void composeTelemetry() {

        // At the beginning of each telemetry update, grab a bunch of data
        // from the IMU that we will then display in separate lines.
        telemetry.addAction(new Runnable() { @Override public void run()
        {
            // Acquiring the angles is relatively expensive; we don't want
            // to do that in each of the three items that need that info, as that's
            // three times the necessary expense.
            angles   = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX);
            gravity  = imu.getGravity();
        }
        });

        telemetry.addLine()
                .addData("status", new Func<String>() {
                    @Override public String value() {
                        return imu.getSystemStatus().toShortString();
                    }
                })
                .addData("calib", new Func<String>() {
                    @Override public String value() {
                        return imu.getCalibrationStatus().toString();
                    }
                });

        telemetry.addLine()
                .addData("heading", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.firstAngle);
                    }
                })
                .addData("roll", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.secondAngle);
                    }
                })
                .addData("pitch", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.thirdAngle);
                    }
                });

        telemetry.addLine()
                .addData("grvty", new Func<String>() {
                    @Override public String value() {
                        return gravity.toString();
                    }
                })
                .addData("mag", new Func<String>() {
                    @Override public String value() {
                        return String.format(Locale.getDefault(), "%.3f",
                                Math.sqrt(gravity.xAccel*gravity.xAccel
                                        + gravity.yAccel*gravity.yAccel
                                        + gravity.zAccel*gravity.zAccel));
                    }
                });
    }

    private double angleTransformation(double x) {
        return x;
    }

    private String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    private String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}
