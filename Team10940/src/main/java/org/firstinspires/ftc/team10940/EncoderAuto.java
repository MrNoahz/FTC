package org.firstinspires.ftc.team10940;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by noah on 4/11/17.
 */

@Autonomous(name = "Encoder", group = "Apostrophe")
//@Disabled

public class EncoderAuto extends LinearOpMode{
    private ApostropheHardware robot   = new ApostropheHardware();
    private ElapsedTime runtime = new ElapsedTime();

    private static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    private static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    private static final double     WHEEL_DIAMETER_INCHES   = 3.94 ;     // For figuring circumference
    private static final double     WHEEL_DIAMETER_CM       = 10.0 ;     // For figuring circumference
    private static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);
    private static final double     COUNTS_PER_CM           = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_CM * Math.PI);
    private static final double     DRIVE_SPEED             = 0.6;
    private static final double     TURN_SPEED              = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();
        runtime.reset();
        telemetry.addData("Status","Resetting Encoders");
        telemetry.update();

        drive(1, 100, 1);
        turn(90, 1, 5);
        turn(180, 1, 5);
        turn(-90, 1, 5);
        turn(45, 1, 5);
    }

private void drive(double speed, double cm, double sleep) {
    double d = COUNTS_PER_CM * cm;
    driveEncode(d, d, speed, sleep);
}

private void turn(double degrees, double speed, double sleep){
    driveEncode(-(19 * degrees), (19 * degrees), speed, sleep);
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

}


