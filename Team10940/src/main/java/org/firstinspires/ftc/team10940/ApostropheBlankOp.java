package org.firstinspires.ftc.team10940;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.superfluous.oratio.Orator;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.team10940.util.ControllerHandler;
import org.firstinspires.ftc.team10940.util.ControllerListener;
import org.firstinspires.ftc.team10940.util.EventName;
import org.firstinspires.ftc.team10940.util.EventType;
import org.firstinspires.ftc.team10940.util.GamepadType;

@TeleOp(name="Blank", group="apostropheresearch")
//@Disabled

public class ApostropheBlankOp extends LinearOpMode implements ControllerListener {
    private ElapsedTime        runtime = new ElapsedTime();
    private ControllerHandler handler = new ControllerHandler();
    private Orator orator;

    @Override
    public void runOpMode() throws InterruptedException {
        orator = new Orator((FtcRobotControllerActivity) hardwareMap.appContext);

        waitForStart();
        runtime.reset();

        handler = new ControllerHandler();
        handler.registerListener(this);

        while(opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            long startTime = System.nanoTime();

            handler.update(gamepad1, gamepad2);

            telemetry.addData("Looptime:", (System.nanoTime() - startTime) / 1000000);
            telemetry.update();

            idle();
        }
    }

    @Override
    public void actionPerformed(EventName en, EventType et, GamepadType gt) {
        telemetry.addData("Gamepad", gt);

        if(et == EventType.BUTTON_RELEASED && gt == GamepadType.GAMEPAD1) {
            switch(en) {
                case X:
                    orator.orate("Shalom");
                    break;
                case Y:
                    orator.orate("Wake me up inside. Can't wake up. Call my name and save me from the dark");
                    break;
            }
        }
    }
}
