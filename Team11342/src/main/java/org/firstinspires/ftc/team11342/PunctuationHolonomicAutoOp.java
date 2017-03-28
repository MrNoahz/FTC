package org.firstinspires.ftc.team11342;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Punctuation Holonomic Auto", group="punctuationholonomic")
public class PunctuationHolonomicAutoOp extends LinearOpMode {
    private PunctuationHolonomicHardware robot   = new PunctuationHolonomicHardware();
    private ElapsedTime runtime = new ElapsedTime();

    //private Oratio orator = new Oratio();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();
        runtime.reset();

//        robot.setDirection(0);
//        robot.frontLeftMotor.setPower(1);
//        robot.frontRightMotor.setPower(1);
//        robot.backLeftMotor.setPower(1);
//        robot.backRightMotor.setPower(1);
//
//        sleep(800);
//        robot.setDirection(1);
//
//        sleep(800);
//        robot.setDirection(2);
//
//        sleep(800);
//        robot.setDirection(3);
//
//        sleep(800);
//        robot.setDirection(4);
//
//        sleep(800);
//        robot.setDirection(5);
//
//        sleep(800);
//        robot.frontLeftMotor.setPower(0);
//        robot.frontRightMotor.setPower(0);
//        robot.backLeftMotor.setPower(0);
//        robot.backRightMotor.setPower(0);



        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motor 0", robot.motor0.getDirection());
            telemetry.addData("Motor 1", robot.motor1.getDirection());
            telemetry.addData("Motor 2", robot.motor2.getDirection());
            telemetry.addData("Motor 3", robot.motor3.getDirection());

            sleep(800);

            robot.setDirection(3);
            robot.motor0.setPower(1);
            robot.motor1.setPower(1);
            robot.motor2.setPower(1);
            robot.motor3.setPower(1);

            telemetry.update();

            sleep(800);
            robot.setDirection(1);
            robot.motor3.setDirection(DcMotorSimple.Direction.FORWARD);

            telemetry.update();

            sleep(800);
            telemetry.update();

            sleep(800);
            robot.motor0.setPower(0);
            robot.motor1.setPower(0);
            robot.motor2.setPower(0);
            robot.motor3.setPower(0);

//            robot.rightMotor.setPower(-gamepad1.right_stick_y);
//            robot.leftMotor.setPower(-gamepad1.left_stick_y);
//
//            robot.spinner.setPower(spinnerOn);
//



            telemetry.update();

            idle();
        }
    }
}
