package org.firstinspires.ftc.team10940;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.superfluous.oratio.Orator;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.team10940.util.BetterMotorFailureTest;
import org.firstinspires.ftc.team10940.util.ControllerHandler;
import org.firstinspires.ftc.team10940.util.ControllerListener;
import org.firstinspires.ftc.team10940.util.EventName;
import org.firstinspires.ftc.team10940.util.EventType;
import org.firstinspires.ftc.team10940.util.GamepadType;
import org.firstinspires.ftc.team10940.util.MotorFailure;

/**
 * This OpMode uses the ApostropheHardware class to define the devices on the robot.
 * All device access is managed through the ApostropheHardware class. (Refer to this class for device names)
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode is the main driver OpMode for the Apostrophe team (as of Velocity Vortex 2016-2017)
 * It utilizes a basic Tank Drive Teleop
 *
 * Gamepad1 acts as the driver and controls the Left and Right motor in a tank drive manner.
 *
 * Gamepad2 acts as the co-pilot and controls the robot's point scoring functionality including the spinner, lifter, and launcher.
 * A will toggle the spinner
 * B will toggle the spinner direction
 * Left Bumper will toggle the lifter direction
 * Right Bumper controls the lifter
 * Left and Right Trigger control the launcher
 *
 * X and Y for both controllers will say "Shalom" and "Wake me up inside" using Noah's fancy new Oratio library
 */

@TeleOp(name="Apostrophe", group="apostrophe")
//@Disabled
public class ApostropheDriveOp extends LinearOpMode implements ControllerListener, MotorFailure {

    /* Declare Resources */
    ApostropheHardware robot    = new ApostropheHardware();  // Use Apostrophe's Hardware
    ElapsedTime        runtime  = new ElapsedTime();         // Used for tracking time
    ElapsedTime        looptime = new ElapsedTime();         // Used to debug how long each cycle is
    ControllerHandler  handler  = new ControllerHandler();   // Used for Noah's fancy new button listener
    Orator orator;                                           // Used to make the robot talk (using Noah's new fancy library)

    // Currently TESTING
    BetterMotorFailureTest failureTester;

    int spinnerOn;      // Toggle for spinner power
    boolean spinnerDir; // Toggle for spinner direction. True = forward. False = backward.
    boolean lifterDir;  // Toggle for lifter direction

    int lastTime;

    @Override
    public void runOpMode() throws InterruptedException {

        /* Initialize the hardware variables
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        /* Initializes the Orator class. (Allowing the robot to speak)
         * Passes the activity as a parameter
         */
        orator = new Orator((FtcRobotControllerActivity) hardwareMap.appContext);

        // Currently TESTING
        failureTester = new BetterMotorFailureTest(this);

        // Currently TESTING
        initEncoders();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        runtime.reset(); // Resets runtime counter on start
        looptime.reset();

        // Must be called after waitForStart()
        handler = new ControllerHandler(); // Initialize handler for button listener
        handler.registerListener(this);    // Register the listener using the actionPerformed function

        spinnerOn  = 0;    // Spinner defaults as off
        spinnerDir = true; // Spinner direction defaults as forward

        lifterDir = true;  // Lifter direction defaults as up

        // Run util the end of the match (driver presses STOP)
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

            // Currently TESTING
            failureTester.update(-gamepad1.left_stick_y, -gamepad1.right_stick_y, robot.leftMotor.getCurrentPosition(), robot.rightMotor.getCurrentPosition());

            handler.update(gamepad1, gamepad2); // Update the handler
            telemetry.addData("Cycle time", looptime.seconds());
            telemetry.update();                 // Update telemetry

            idle(); // Yields thread to give other threads a chance to run
        }
    }

    /* New and improved button listener */
    @Override
    public void actionPerformed(EventName en, EventType et, GamepadType gt) {
        // Filter out all events that aren't BUTTON_RELEASED
        if(et == EventType.BUTTON_RELEASED) {
            switch(en) {
                case X:
                    // If x is pressed (and released) then the robot will say "shalom"
                    orator.orate("Hello");
                    break;
                case Y:
                    // If y is pressed (and released) then the robot will say "Wake me up inside"
                    orator.orate("Wake me up, wake me up inside I can't wake up. Wake me up inside, save me, Call my name and save me from the dark, wake me up. Bid my blood to run, I can't wake up. Before I Come undone, save me. Save me from the nothing I've become.");
                    break;
            }
        }

        // Filter out all events that aren't from Gamepad1 and BUTTON_RELEASED
        if(gt == GamepadType.GAMEPAD1 && et == EventType.BUTTON_RELEASED) {
            switch(en) {

            }
        }

        // Filter out all events that aren't from Gamepad2 and BUTTON_RELEASED
        if(gt == GamepadType.GAMEPAD2 && et == EventType.BUTTON_RELEASED) {
            switch(en) {
                case A:
                    // If A is pressed, it will toggle the direction
                    spinnerOn = 1 - spinnerOn;
                    break;
                case B:
                    // If B is pressed, it will toggle  the spinner direction
                    spinnerDir = !spinnerDir;

                    if(spinnerDir) robot.spinny.setDirection(DcMotorSimple.Direction.REVERSE);
                    else           robot.spinny.setDirection(DcMotorSimple.Direction.FORWARD);
                    break;
                case LEFT_BUMPER:
                    // If LEFT_BUMPER is pressed, it will toggle the lifter direction
                    lifterDir = !lifterDir;

                    if(lifterDir) robot.lifty.setDirection(DcMotorSimple.Direction.REVERSE);
                    else          robot.lifty.setDirection(DcMotorSimple.Direction.FORWARD);
                    break;
            }
        }
    }

    // Currently TESTING
    @Override
    public void onFail(int i) {
        telemetry.addData("Motor", "Motor failure" + Integer.toString(i));
    }

    // Currently TESTING
    private void initEncoders() {
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        robot.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
