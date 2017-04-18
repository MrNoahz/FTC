package org.firstinspires.ftc.team10940;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.superfluous.oratio.Orator;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

/**
 * Insert Description Here
 */

@TeleOp(name="Checklist", group="apostrophe")
public class ApostropheTester extends LinearOpMode {

    /* Declare Resources */
    private ApostropheHardware robot   = new ApostropheHardware();  // Use Apostrophe's Hardware
    private ElapsedTime        runtime = new ElapsedTime();         // Used for tracking time
    private Orator orator;                                          // Used to make the robot talk (using Noah's new fancy library)

    private boolean leftMotorPass  = true;
    private boolean rightMotorPass = true;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        /* Initialize the hardware variables
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Initialize it with motors set to encoders
        initEncoders();

        /* Initializes the Orator class. (Allowing the robot to speak)
         * Passes the activity as a parameter
         */
        orator = new Orator((FtcRobotControllerActivity) hardwareMap.appContext);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        runtime.reset(); // Resets runtime counter on start

        telemetry.addData("Status", "Running time " + runtime.toString());
        telemetry.addData("Left Motor", "-");
        telemetry.addData("Right Motor", "-");
        telemetry.addData("Spinner", "-");
        telemetry.addData("Launcher", "-");
        telemetry.addData("Lifter", "-");
        telemetry.update();

        orator.orate("Testing motors");
        checkMotors();

        orator.orate("Testing spinner");
        checkSpinner();

        orator.orate("Testing launcher");
        checkLauncher();

        orator.orate("Testing lifter");
        checkLifter();

        orator.orate("Checklist finished");
    }

    private void checkMotors() {
        int leftStart  = robot.leftMotor.getCurrentPosition();
        int rightStart = robot.rightMotor.getCurrentPosition();

        robot.leftMotor.setPower(1.0);
        robot.rightMotor.setPower(-1.0);

        sleep(1000);

        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);

        if(robot.leftMotor.getCurrentPosition() < 500) // Arbitrary low number. Make sure that it's not 0 to account for friction
            leftMotorPass = false;

        if(robot.rightMotor.getCurrentPosition() > -500) // Same
            rightMotorPass = false;

        telemetry.addData("Status", "Running time " + runtime.toString());
        telemetry.addData("Left Motor", (leftMotorPass) ? "✓" : "×");
        telemetry.addData("Right Motor", (rightMotorPass) ? "✓" : "×");
        telemetry.addData("Spinner", "-");
        telemetry.addData("Launcher", "-");
        telemetry.addData("Lifter", "-");
        telemetry.update();
    }

    private void checkSpinner() {
        robot.spinny.setPower(0.5);

        sleep(1000);

        robot.spinny.setPower(0);

        telemetry.addData("Status", "Running time " + runtime.toString());
        telemetry.addData("Left Motor", (leftMotorPass) ? "✓" : "×");
        telemetry.addData("Right Motor", (rightMotorPass) ? "✓" : "×");
        telemetry.addData("Spinner", "✓");
        telemetry.addData("Launcher", "-");
        telemetry.addData("Lifter", "-");
        telemetry.update();
    }

    private void checkLauncher() {
        robot.launchy.setPower(0.3);

        sleep(500);

        robot.launchy.setPower(0);

        telemetry.addData("Status", "Running time " + runtime.toString());
        telemetry.addData("Left Motor", (leftMotorPass) ? "✓" : "×");
        telemetry.addData("Right Motor", (rightMotorPass) ? "✓" : "×");
        telemetry.addData("Spinner", "✓");
        telemetry.addData("Launcher", "✓");
        telemetry.addData("Lifter", "-");
        telemetry.update();
    }

    private void checkLifter() {
        robot.lifty.setPower(0.5);

        sleep(1000);

        robot.lifty.setPower(0);

        telemetry.addData("Status", "Running time " + runtime.toString());
        telemetry.addData("Left Motor", (leftMotorPass) ? "✓" : "×");
        telemetry.addData("Right Motor", (rightMotorPass) ? "✓" : "×");
        telemetry.addData("Spinner", "✓");
        telemetry.addData("Launcher", "✓");
        telemetry.addData("Lifter", "✓");
        telemetry.update();
    }

    private void initEncoders() {
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}