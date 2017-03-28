package org.firstinspires.ftc.team11342;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.superfluous.oratio.Orator;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

@TeleOp(name="Punctuation Holonomic Drive", group="punctuationomni")
public class PunctuationHolonomicDriveOp extends LinearOpMode {

    private PunctuationHolonomicHardware robot   = new PunctuationHolonomicHardware();
    private ElapsedTime runtime = new ElapsedTime();

    private boolean buttons[];

    private int spinnerOn;

    private Orator orator;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        orator = new Orator((FtcRobotControllerActivity) hardwareMap.appContext);

        waitForStart();
        runtime.reset();

        buttons = new boolean[3];

        spinnerOn = 0;

        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());

            //TODO Implement DPad support
            if(gamepad1.dpad_right) {
                robot.motor0.setPower(-1 + gamepad1.right_stick_x);
                robot.motor1.setPower(-1 + gamepad1.right_stick_x);
                robot.motor2.setPower( 1 + gamepad1.right_stick_x);
                robot.motor3.setPower( 1 + gamepad1.right_stick_x);
            } else if(gamepad1.dpad_up) {
                robot.motor0.setPower(-1 + gamepad1.right_stick_x);
                robot.motor1.setPower( 1 + gamepad1.right_stick_x);
                robot.motor2.setPower( 1 + gamepad1.right_stick_x);
                robot.motor3.setPower(-1 + gamepad1.right_stick_x);
            } else if(gamepad1.dpad_left) {
                robot.motor0.setPower( 1 + gamepad1.right_stick_x);
                robot.motor1.setPower( 1 + gamepad1.right_stick_x);
                robot.motor2.setPower(-1 + gamepad1.right_stick_x);
                robot.motor3.setPower(-1 + gamepad1.right_stick_x);
            } else if(gamepad1.dpad_down) {
                robot.motor0.setPower( 1 + gamepad1.right_stick_x);
                robot.motor1.setPower(-1 + gamepad1.right_stick_x);
                robot.motor2.setPower(-1 + gamepad1.right_stick_x);
                robot.motor3.setPower( 1 + gamepad1.right_stick_x);
            } else if(gamepad1.right_bumper) {
                //TODO Stick Rounding Testing

                robot.motor0.setPower(round( gamepad1.left_stick_y) - round(gamepad1.left_stick_x) - gamepad1.right_stick_x);
                robot.motor1.setPower(round(-gamepad1.left_stick_y) - round(gamepad1.left_stick_x) - gamepad1.right_stick_x);
                robot.motor2.setPower(round(-gamepad1.left_stick_y) + round(gamepad1.left_stick_x) - gamepad1.right_stick_x);
                robot.motor3.setPower(round( gamepad1.left_stick_y) + round(gamepad1.left_stick_x) - gamepad1.right_stick_x);

//                if(gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0) {
//                    double angle = convertToFullCircle(Math.atan2(gamepad1.left_stick_x, gamepad1.left_stick_y)
//                                    * (180 / Math.PI));
//
//                    double joystickDistance = Math.sqrt(Math.abs(Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2)));
//
//                    telemetry.addData("JoystickAngle", angle);
//                    telemetry.addData("JoystickDistance", joystickDistance);
//
//                    if(angle <= 22.5 && angle >= 337.5) {
//                        robot.motor0.setPower( joystickDistance + gamepad1.right_stick_x);
//                        robot.motor1.setPower( joystickDistance + gamepad1.right_stick_x);
//                        robot.motor2.setPower(-joystickDistance + gamepad1.right_stick_x);
//                        robot.motor3.setPower(-joystickDistance + gamepad1.right_stick_x);
//                    } else if(angle < 67.5) {
//                        robot.motor0.setPower( 0 + gamepad1.right_stick_x);
//                        robot.motor1.setPower(-1 + gamepad1.right_stick_x);
//                        robot.motor2.setPower( 0 + gamepad1.right_stick_x);
//                        robot.motor3.setPower( 1 + gamepad1.right_stick_x);
//                    } else if(angle < 112.5) {
//                        robot.motor0.setPower(-joystickDistance + gamepad1.right_stick_x);
//                        robot.motor1.setPower(-joystickDistance + gamepad1.right_stick_x);
//                        robot.motor2.setPower( joystickDistance + gamepad1.right_stick_x);
//                        robot.motor3.setPower( joystickDistance + gamepad1.right_stick_x);
//                    } else if(angle < 157.5) {
//                        robot.motor0.setPower(-1 + gamepad1.right_stick_x);
//                        robot.motor1.setPower( 0 + gamepad1.right_stick_x);
//                        robot.motor2.setPower( 1 + gamepad1.right_stick_x);
//                        robot.motor3.setPower( 0 + gamepad1.right_stick_x);
//                    } else if(angle < 202.5) {
//                        robot.motor0.setPower(-joystickDistance + gamepad1.right_stick_x);
//                        robot.motor1.setPower( joystickDistance + gamepad1.right_stick_x);
//                        robot.motor2.setPower( joystickDistance + gamepad1.right_stick_x);
//                        robot.motor3.setPower(-joystickDistance + gamepad1.right_stick_x);
//                    } else if(angle < 247.5) {
//                        robot.motor0.setPower( 0 + gamepad1.right_stick_x);
//                        robot.motor1.setPower( 1 + gamepad1.right_stick_x);
//                        robot.motor2.setPower( 0 + gamepad1.right_stick_x);
//                        robot.motor3.setPower(-1 + gamepad1.right_stick_x);
//                    } else if(angle < 292.5) {
//                        robot.motor0.setPower( joystickDistance + gamepad1.right_stick_x);
//                        robot.motor1.setPower( joystickDistance + gamepad1.right_stick_x);
//                        robot.motor2.setPower(-joystickDistance + gamepad1.right_stick_x);
//                        robot.motor3.setPower(-joystickDistance + gamepad1.right_stick_x);
//                    } else if(angle < 337.5) {
//                        robot.motor0.setPower( 1 + gamepad1.right_stick_x);
//                        robot.motor1.setPower( 0 + gamepad1.right_stick_x);
//                        robot.motor2.setPower(-1 + gamepad1.right_stick_x);
//                        robot.motor3.setPower( 0 + gamepad1.right_stick_x);
//                    }

//                    if(angle <= 22.5 && angle >= 337.5) {
//                        robot.motor0.setPower(-joystickDistance + gamepad1.right_stick_x);
//                        robot.motor1.setPower(-joystickDistance + gamepad1.right_stick_x);
//                        robot.motor2.setPower( joystickDistance + gamepad1.right_stick_x);
//                        robot.motor3.setPower( joystickDistance + gamepad1.right_stick_x);
//                    } else if(angle < 67.5) {
//                        robot.motor0.setPower(-joystickDistance + gamepad1.right_stick_x);
//                        robot.motor1.setPower( 0 + gamepad1.right_stick_x);
//                        robot.motor2.setPower( joystickDistance + gamepad1.right_stick_x);
//                        robot.motor3.setPower( 0 + gamepad1.right_stick_x);
//                    } else if(angle < 112.5) {
//                        robot.motor0.setPower(-joystickDistance + gamepad1.right_stick_x);
//                        robot.motor1.setPower( joystickDistance + gamepad1.right_stick_x);
//                        robot.motor2.setPower( joystickDistance + gamepad1.right_stick_x);
//                        robot.motor3.setPower(-joystickDistance + gamepad1.right_stick_x);
//                    } else if(angle < 157.5) {
//                        robot.motor0.setPower( 0 + gamepad1.right_stick_x);
//                        robot.motor1.setPower( joystickDistance + gamepad1.right_stick_x);
//                        robot.motor2.setPower( 0 + gamepad1.right_stick_x);
//                        robot.motor3.setPower(-joystickDistance + gamepad1.right_stick_x);
//                    } else if(angle < 202.5) {
//                        robot.motor0.setPower( joystickDistance + gamepad1.right_stick_x);
//                        robot.motor1.setPower( joystickDistance + gamepad1.right_stick_x);
//                        robot.motor2.setPower(-joystickDistance + gamepad1.right_stick_x);
//                        robot.motor3.setPower(-joystickDistance + gamepad1.right_stick_x);
//                    } else if(angle < 247.5) {
//                        robot.motor0.setPower( joystickDistance + gamepad1.right_stick_x);
//                        robot.motor1.setPower( 0 + gamepad1.right_stick_x);
//                        robot.motor2.setPower(-joystickDistance + gamepad1.right_stick_x);
//                        robot.motor3.setPower( 0 + gamepad1.right_stick_x);
//                    } else if(angle < 292.5) {
//                        robot.motor0.setPower( joystickDistance + gamepad1.right_stick_x);
//                        robot.motor1.setPower(-joystickDistance + gamepad1.right_stick_x);
//                        robot.motor2.setPower(-joystickDistance + gamepad1.right_stick_x);
//                        robot.motor3.setPower( joystickDistance + gamepad1.right_stick_x);
//                    } else if(angle < 337.5) {
//                        robot.motor0.setPower( 0 + gamepad1.right_stick_x);
//                        robot.motor1.setPower(-joystickDistance + gamepad1.right_stick_x);
//                        robot.motor2.setPower( 0 + gamepad1.right_stick_x);
//                        robot.motor3.setPower( joystickDistance + gamepad1.right_stick_x);
//                    }
//                }
            } else {
                robot.setDirection(5); //Neutral direction
                robot.motor0.setPower( gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x);
                robot.motor1.setPower(-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x);
                robot.motor2.setPower(-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x);
                robot.motor3.setPower( gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x);
            }

            if(gamepad1.a && !buttons[0]) {
                orator.orate("dobreeden comrades");

                buttons[0] = true;
            } else if(!gamepad1.a) {
                buttons[0] = false;
            }

            if(gamepad1.b && !buttons[1]) {
                orator.orate("Shalom");

                buttons[1] = true;
            } else if(!gamepad1.b) {
                buttons[1] = false;
            }

            if(gamepad1.x && !buttons[2]) {
                orator.orate("woof woof. I'm a dog");

                buttons[2] = true;
            } else if(!gamepad1.x) {
                buttons[2] = false;
            }

            telemetry.update();

            idle();
        }
    }

    /*
        Using an arc tangent functions (atan2) will return
        negative numbers for over 180 degrees. So we'll
        convert it so that we get a full positive 360 degree circle
    */
    private double convertToFullCircle(double i) { return (i < 0) ? (360 + i) : i; }

    private double round(double i) {
        if(i < -0.49)
            return -1;
        else if(i < 0.49)
            return 1;
        else
            return 0;
    }
}
