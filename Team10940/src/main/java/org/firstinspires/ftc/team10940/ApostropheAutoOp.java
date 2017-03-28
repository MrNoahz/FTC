package org.firstinspires.ftc.team10940;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutoOp", group="Apostrophe")
//@Disabled
public class ApostropheAutoOp extends LinearOpMode {
    private ApostropheHardware robot   = new ApostropheHardware();
    private ElapsedTime  runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();

        robot.rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        //drive forward for 0.5 seconds on 0.5 power (set up for launch)
        driveTime(0.5, 0.5);

        robot.launchy.setPower(1); //run launcher
        Wait(1);
        robot.launchy.setPower(0);

        robot.lifty.setPower(0.75); //run spinner
        Wait(2);
        robot.lifty.setPower(0);

        robot.launchy.setPower(1); //run launcher
        Wait(1);
        robot.launchy.setPower(0);

        robot.rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        driveTime(2, 0.5);//turn around


        robot.rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        robot.leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        driveTime(2, 0.5);//drive forward for 2 seconds on 0.5 power (onto middle)
    }


    private void driveTime(double sec, double pow) {
        double newTime;

        if(opModeIsActive()) {
            newTime = runtime.time() + (sec * 1);

            robot.leftMotor.setPower(pow);
            robot.rightMotor.setPower(pow);

            while(opModeIsActive() &&
                    (runtime.time() < newTime)) {
                telemetry.addData("Elapsed Time", newTime - runtime.time());
            }

            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);
        }
    }

    private void Wait(double sec) { //Replace with thread.sleep and see what happens
        double newTime;

        if(opModeIsActive()){
            newTime = runtime.time() + (sec * 1);

            while(opModeIsActive() &&
                    (runtime.time() < newTime)) {
                telemetry.addData("Elapsed Time", newTime - runtime.time());
            }

        }
    }
}