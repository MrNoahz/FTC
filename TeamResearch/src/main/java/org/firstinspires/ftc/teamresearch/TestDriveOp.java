package org.firstinspires.ftc.teamresearch;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcontroller.internal.util.AudioList;

@TeleOp(name="Drive", group="test")
//@Disabled

public class TestDriveOp extends LinearOpMode {
    private TestHardware robot   = new TestHardware();
    private ElapsedTime  runtime = new ElapsedTime();

    private FtcRobotControllerActivity activity;

    private boolean[] buttons;
    private int currentFace;
    private int currentVoice;
    private int setFace;
    private int setVoice;
    private int faceLength;
    private int voiceLength;

    private String[] faceNames = { "Neutral", "Curious Left", "Curious Right", "Happy", "Mad", "Sad" };

    private int speedPress;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();
        runtime.reset();

        activity = (FtcRobotControllerActivity) hardwareMap.appContext;
        buttons = new boolean[9];

        currentFace  = 0;
        setFace      = 0;
        currentVoice = 0;

        faceLength = 6;
        voiceLength = AudioList.list.length;

        speedPress = 0;

        while(opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Current Face", Integer.toString(currentFace) + ": " + faceNames[currentFace]);
            telemetry.addData("Set Face To", Integer.toString(setFace) + ": " + faceNames[setFace]);
            telemetry.addData("Voice", Integer.toString(currentVoice) + ": " + AudioList.list[currentVoice][0]);

            robot.left.setPower(-gamepad1.left_stick_y);
            robot.right.setPower(-gamepad1.right_stick_y);

            if(gamepad1.left_bumper && !buttons[0]) {
                activity.runOnUiThread(new Runnable() {
                   @Override
                    public void run() {
                       activity.toggleFace();
                   }
                });

                buttons[0] = true;
            } else if(!gamepad1.left_bumper){
                buttons[0] = false;
            }

            if(gamepad1.dpad_left && !buttons[1]) {
                if(setFace == 0)
                    setFace = faceLength - 1;
                else
                   setFace--;

                buttons[1] = true;
            } else if(!gamepad1.dpad_left) {
                buttons[1] = false;
            }

            if(gamepad1.dpad_right && !buttons[2]) {
                if(setFace == faceLength - 1)
                    setFace = 0;
                else
                    setFace++;

                buttons[2] = true;
            } else if(!gamepad1.dpad_right) {
                buttons[2] = false;
            }

            if(gamepad1.y && !buttons[3]) {
                currentFace = setFace;

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.setFace(currentFace);
                    }
                });

                buttons[3] = true;
            } else if(!gamepad1.y){
                buttons[3] = false;
            }

            if(gamepad1.dpad_down && !buttons[4]) {
                if(currentVoice == 0)
                    currentVoice = voiceLength - 1;
                else
                    currentVoice--;

                buttons[4] = true;
            } else if(!gamepad1.dpad_down) {
                buttons[4] = false;
            }

            if(gamepad1.dpad_up && !buttons[5]) {
                if(currentVoice == voiceLength - 1)
                    currentVoice = 0;
                else
                    currentVoice++;

                buttons[5] = true;
            } else if(!gamepad1.dpad_up) {
                buttons[5] = false;
            }

            if(gamepad1.x && !buttons[6]) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.playSound((int) AudioList.list[currentVoice][1]);
                    }
                });

                buttons[6] = true;
            } else if(!gamepad1.x){
                buttons[6] = false;
            }

            if(gamepad1.right_bumper && !buttons[7]) {
                speedPress++;

                buttons[7] = true;
            } else if(!gamepad1.x){
                buttons[7] = false;
            }

            if(gamepad1.left_bumper && !buttons[8]) {
                speedPress++;

                buttons[8] = true;
            } else if(!gamepad1.x){
                buttons[8] = false;
            }

            speedPress = 0;

            telemetry.update();
            idle();
        }
    }
}