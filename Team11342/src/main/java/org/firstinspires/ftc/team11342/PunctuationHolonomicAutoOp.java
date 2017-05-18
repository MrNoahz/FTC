package org.firstinspires.ftc.team11342;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

        sleep(10000);

        //robot.setDirection(0);
        robot.frontLeftMotor.setPower(-1);
        robot.frontRightMotor.setPower(1);
        robot.backLeftMotor.setPower(-1);
        robot.backRightMotor.setPower(1);

        sleep(2000);

        robot.frontLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backLeftMotor.setPower(0);
        robot.backRightMotor.setPower(0);
    }
}
