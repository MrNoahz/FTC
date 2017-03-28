package org.firstinspires.ftc.teamresearch;

/* Hardware class for testing purposes */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class TestHardware {
    public final double INCREMENT = 0.01;
    public final double MAX_POS   =  1.0;
    public final double MIN_POS   =  0.0;

    public DcMotor right;
    public DcMotor left;

    public Servo servo1;
    public Servo servo2;

    HardwareMap hwMap = null;

    private ElapsedTime period = new ElapsedTime();

    public TestHardware() {

    }

    public void init(HardwareMap hMap) {
        hwMap = hMap;

        left = hwMap.dcMotor.get("left");
        left.setDirection(DcMotor.Direction.REVERSE);

        right = hwMap.dcMotor.get("right");
        right.setDirection(DcMotor.Direction.FORWARD);

        left.setPower(0);
        right.setPower(0);

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //servo1 = hwMap.servo.get("right1");
        //servo1.setPosition((MAX_POS - MIN_POS) / 2);
    }

    public void waitForTick(long periodMs) throws InterruptedException {
        long  remaining = periodMs - (long)period.milliseconds();

        if (remaining > 0)
            Thread.sleep(remaining);

        period.reset();
    }
}
