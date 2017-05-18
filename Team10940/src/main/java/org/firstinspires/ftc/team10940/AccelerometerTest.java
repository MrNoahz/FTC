package org.firstinspires.ftc.team10940;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.hardware.adafruit.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.superfluous.oratio.Orator;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.team10940.util.ControllerHandler;

import java.util.Locale;

/**
 * Created by noah on 4/20/17.
 */

@TeleOp(name = "Accelerometer Test", group="No")
public class AccelerometerTest extends ApostropheDriveOp {

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

        orator = new Orator((FtcRobotControllerActivity) hardwareMap.appContext);

        // Not Driver Part

        waitForStart();

        // Driver Part
        runtime.reset();
        looptime.reset();

        handler = new ControllerHandler(); // Initialize handler for button listener
        handler.registerListener(this);    // Register the listener using the actionPerformed function

        spinnerOn  = 0;    // Spinner defaults as off
        spinnerDir = true; // Spinner direction defaults as forward

        lifterDir = true;  // Lifter direction defaults as up

        // Not driver part

        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);

        while(opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());  // Display running time to driver
            looptime.reset();

            // Run wheels in tank mode (note: The joystick goes negative when pushed forward, so negate it)
            robot.leftMotor.setPower(-gamepad1.left_stick_y);    // Multiplies speed by motor speed percentage
            robot.rightMotor.setPower(-gamepad1.right_stick_y);  // Same ^

            // Sets spinner to the spinnerOn toggle
            robot.spinny.setPower(spinnerOn);

            // If right bumper is pressed, set to 0.75 power. Else, 0
            // robot.lifty.setPower((gamepad2.right_bumper) ? 0.75 : 0);
            robot.lifty.setPower((gamepad2.left_stick_y) * 0.5);

            /* Trigger point is 0.8
             * If right or left trigger exceeds trigger point, change the direction
             * of the launcher and turn it on (Forward and reverse respectively)
             * Else set the launcher power to 0
             */
            double triggerPoint = 0.8;

            if(gamepad2.right_trigger > triggerPoint) {
                robot.launchy.setDirection(DcMotorSimple.Direction.FORWARD);
                robot.launchy.setPower(0.8);
            } else if(gamepad2.left_trigger > triggerPoint) {
                robot.launchy.setDirection(DcMotorSimple.Direction.REVERSE);
                robot.launchy.setPower(0.8);
            } else {
                robot.launchy.setPower(0);
            }

            handler.update(gamepad1, gamepad2); // Update the handler
            telemetry.addData("Cycle time", looptime.seconds());

            telemetry.update();

            idle();
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
                .addData("heading2", new Func<String>() {
                    @Override public String value() {
                        return Double.toString( doubleFormatAngle(angles.angleUnit, angles.firstAngle));
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
