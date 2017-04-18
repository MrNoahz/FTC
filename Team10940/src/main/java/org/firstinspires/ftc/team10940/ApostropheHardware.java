package org.firstinspires.ftc.team10940;

/**
 * This class is used to define all the specific hardware for team Apostrophe's (10940) robot.
 *
 * Used primarily by the ApostropheDriveOp and ApostropheAutoOps
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note: All names are lowercase and are typed as-is
 *
 * Motor channel:  Left  Drive Motor:  "left"
 * Motor channel:  Right Drive Motor:  "right"
 * Motor channel:  Spinner  Motor:     "spinner"
 * Motor channel:  Launcher Motor:     "launchy"
 * Motor channel:  Lifter   Motor:     "lifty"
 *
 * Note: The Left and Right drive motor are located on the back with the front wheels being omniwheels.
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

class ApostropheHardware {

    /* Public OpMode members */
    DcMotor rightMotor;
    DcMotor leftMotor;

    DcMotor spinny;
    DcMotor launchy;
    DcMotor lifty;

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hwMap) {
        // Define and Initialize Motors. Set all the motor directions.
        rightMotor = hwMap.dcMotor.get("right");
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        leftMotor = hwMap.dcMotor.get("left");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);

        spinny = hwMap.dcMotor.get("spinner");
        spinny.setDirection(DcMotorSimple.Direction.FORWARD);

        launchy = hwMap.dcMotor.get("launchy");
        //launchy.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT); // Basically ensures that if the launcher stops on the catcher then there's no resistance (and less chance of breaking the spring steel)
        launchy.setDirection(DcMotorSimple.Direction.FORWARD);

        lifty = hwMap.dcMotor.get("lifty");
        lifty.setDirection(DcMotorSimple.Direction.FORWARD);

        // Set all motors to zero power
        rightMotor.setPower(0);
        leftMotor.setPower(0);

        spinny.setPower(0);

        launchy.setPower(0);
        lifty.setPower(0);

        // Set all motors to run without encoders
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        spinny.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        launchy.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lifty.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
