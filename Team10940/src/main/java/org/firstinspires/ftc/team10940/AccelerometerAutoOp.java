package org.firstinspires.ftc.team10940;

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

@Autonomous(name="Accelerometer Auto Test", group="no")
public class AccelerometerAutoOp extends LinearOpMode {

    ApostropheHardware robot   = new ApostropheHardware();
    ElapsedTime        runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP

    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     WHEEL_DIAMETER_CM       = 10.0;

    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     COUNTS_PER_CM           = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_CM * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    BNO055IMU imu;

    Orientation angles;
    Acceleration gravity;

    @Override
    public void runOpMode() throws InterruptedException {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "magic_compass");
        imu.initialize(parameters);

        composeTelemetry();

        // Driver Part
        robot.init(hardwareMap);

        telemetry.addData("Status", "Reseting Encoders");
        telemetry.update();

        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Path0",  "Starting at %7d :%7d",
                robot.leftMotor.getCurrentPosition(),
                robot.rightMotor.getCurrentPosition());
        telemetry.update();

        waitForStart();

        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);

//        while(opModeIsActive()) {
//
//        }
        driveCm(25, 1000);
        turn(90, 1000);
        turn(-90, 1000);
    }

    private void driveInch(double distance, int timeout) {
        driveEncoder(DRIVE_SPEED, distance * COUNTS_PER_INCH, distance * COUNTS_PER_INCH, timeout);
    }

    private void driveInch(double speed, double distance, int timeout) {
        driveEncoder(speed, distance * COUNTS_PER_INCH, distance * COUNTS_PER_INCH, timeout);
    }

    private void driveInch(double speed, double left, double right, int timeout) {
        driveEncoder(speed, left * COUNTS_PER_INCH, right * COUNTS_PER_INCH, timeout);
    }

    private void driveCm(double distance, int timeout) {
        driveEncoder(DRIVE_SPEED, distance * COUNTS_PER_CM, distance * COUNTS_PER_CM, timeout);
    }

    private void driveCm(double speed, double distance, int timeout) {
        driveEncoder(speed, distance * COUNTS_PER_CM, distance * COUNTS_PER_CM, timeout);
    }

    private void driveCm(double speed, double left, double right, int timeout) {
        driveEncoder(speed, left * COUNTS_PER_CM, right * COUNTS_PER_CM, timeout);
    }

    private void driveEncoder(double distance, int timeout) {
        driveEncoder(DRIVE_SPEED, distance, distance, timeout);
    }

    private void driveEncoder(double speed, double distance, int timeout) {
        driveEncoder(speed, distance, distance, timeout);
    }

    private void driveEncoder(double speed, double left, double right, int timeout) {
        int newLeftTarget;
        int newRightTarget;

        if(opModeIsActive()) {
            newLeftTarget  = robot.leftMotor.getCurrentPosition() + (int)(left);
            newRightTarget = robot.rightMotor.getCurrentPosition() + (int)(right);

            robot.leftMotor.setTargetPosition(newLeftTarget);
            robot.rightMotor.setTargetPosition(newRightTarget);

            robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();

            robot.leftMotor.setPower(Math.abs(speed));
            robot.rightMotor.setPower(Math.abs(speed));

            while(opModeIsActive() &&
                    (robot.leftMotor.isBusy() && robot.rightMotor.isBusy())) {

                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftMotor.getCurrentPosition(),
                        robot.rightMotor.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(timeout);
        }
    }

    private void turn(double deg, int timeout) {
        double currentAngle;
        double newAngle;

        // -1 for counterclockwise. 1 for clockwise
        int direction;

        if(opModeIsActive()) {

            angles   = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX);
            gravity  = imu.getGravity();

            currentAngle = doubleFormatAngle(angles.angleUnit, angles.firstAngle);
            newAngle = currentAngle + deg;

            telemetry.addData("No", currentAngle);
            telemetry.addData("No1", newAngle);
            telemetry.update();

            if(deg < 0)
                direction = -1;
            else
                direction = 1;

            telemetry.addData("No2", direction);
            telemetry.update();

            robot.rightMotor.setPower(TURN_SPEED * direction);
            robot.leftMotor.setPower(TURN_SPEED * -direction);

            if(direction == -1) {
                do {
                    angles   = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX);
                    gravity  = imu.getGravity();

                    telemetry.addData("No3", "DoingWhile1");
                    telemetry.update();

                    currentAngle = doubleFormatAngle(angles.angleUnit, angles.firstAngle);
                } while(currentAngle < newAngle);
            } else {
                do {
                    angles   = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX);
                    gravity  = imu.getGravity();

                    telemetry.addData("No3", "DoingWhile2");
                    telemetry.update();

                    currentAngle = doubleFormatAngle(angles.angleUnit, angles.firstAngle);
                } while(currentAngle > newAngle);
            }

            robot.rightMotor.setPower(0);
            robot.leftMotor.setPower(0);
        }
    }

    void composeTelemetry() {

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

    //----------------------------------------------------------------------------------------------
    // Formatting
    //----------------------------------------------------------------------------------------------

    double doubleFormatAngle(AngleUnit au, double a) {
        return doTheDos(AngleUnit.DEGREES.normalize(AngleUnit.DEGREES.fromUnit(au, a)));
    }

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    // Basically, formatAngle converts AngleUnit to degrees. Degrees gets converted into a string.
    // And then the string gets converted back to a double where it is made so that it's 0-360
    // Degrees instead of -180 - 180 degrees
    double doTheDos(double imSorry) {
        if (imSorry < 0)
            return 360 + imSorry;

        return imSorry;
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}
