package org.firstinspires.ftc.team11342;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Punctuation T Drive", group="punctuationomni")
public class PunctuationTDriveOp extends LinearOpMode {

    private PunctuationHolonomicHardware robot   = new PunctuationHolonomicHardware();
    private ElapsedTime runtime = new ElapsedTime();

    private boolean buttons[];

    private int spinnerOn;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();
        runtime.reset();

        buttons = new boolean[4];

        spinnerOn = 0;

        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motor 0", robot.motor0.getDirection() + ", " + robot.motor0.getPower());
            telemetry.addData("Motor 1", robot.motor1.getDirection() + ", " + robot.motor1.getPower());
            telemetry.addData("Motor 2", robot.motor2.getDirection() + ", " + robot.motor2.getPower());
            telemetry.addData("Motor 3", robot.motor3.getDirection() + ", " + robot.motor3.getPower());

            robot.setDirection(5); //Neutral
            robot.motor0.setPower( gamepad1.left_stick_x + gamepad1.right_stick_x);
            robot.motor1.setPower(-gamepad1.left_stick_y + gamepad1.right_stick_x);
            robot.motor2.setPower(-gamepad1.left_stick_x + gamepad1.right_stick_x);
            robot.motor3.setPower( gamepad1.left_stick_y + gamepad1.right_stick_x);

//            if(gamepad1.left_bumper) {
//                robot.motor0.setPower(1);
//                robot.motor1.setPower(1);
//                robot.motor2.setPower(1);
//                robot.motor3.setPower(1);
//            } else {
//                robot.motor0.setPower(0);
//                robot.motor1.setPower(0);
//                robot.motor2.setPower(0);
//                robot.motor3.setPower(0);
//            }
//
//            if(gamepad1.y && !buttons[0]) {
//                robot.setDirection(0);
//
//                buttons[0] = true;
//            } else if(!gamepad1.y) {
//                buttons[0] = false;
//            }
//
//            if(gamepad1.b && !buttons[1]) {
//                robot.setDirection(1);
//
//                buttons[1] = true;
//            } else if(!gamepad1.b) {
//                buttons[1] = false;
//            }
//
//            if(gamepad1.a && !buttons[2]) {
//                robot.setDirection(2);
//
//                buttons[2] = true;
//            } else if(!gamepad1.a) {
//                buttons[2] = false;
//            }
//
//            if(gamepad1.x && !buttons[3]) {
//                robot.setDirection(3);
//
//                buttons[3] = true;
//            } else if(!gamepad1.x) {
//                buttons[3] = false;
//            }



//            if (gamepad1.left_stick_y != 0) {
//                robot.setDirection(0);
//
//                robot.frontLeftMotor.setPower(-gamepad1.left_stick_y);
//                robot.frontRightMotor.setPower(-gamepad1.left_stick_y);
//                robot.backLeftMotor.setPower(-gamepad1.left_stick_y);
//                robot.backRightMotor.setPower(-gamepad1.left_stick_y);
//            } else if (gamepad1.left_stick_x != 0) {
//                robot.setDirection(1);
//
//                robot.frontLeftMotor.setPower(-gamepad1.left_stick_y);
//                robot.frontRightMotor.setPower(-gamepad1.left_stick_y);
//                robot.backLeftMotor.setPower(-gamepad1.left_stick_y);
//                robot.backRightMotor.setPower(-gamepad1.left_stick_y);
//            }

            telemetry.update();

            idle();
        }
    }
}
