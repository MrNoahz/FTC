package org.firstinspires.ftc.team11342;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.Arrays;
import java.util.List;

public class ControllerHandler {

    private ControllerListener listener;

    private boolean buttons[];
    private boolean buttonValues[];
    private final short buttonsToCount = 15;
    private final List<EventName> eventTypeList;

    public ControllerHandler() {
        buttons = new boolean[buttonsToCount * 2];      //x buttons to count. 2 gamepads
        buttonValues = new boolean[buttonsToCount * 2]; //x buttons to count. 2 gamepads

        eventTypeList = Arrays.asList(EventName.values());
    }

    public void registerListener(ControllerListener cl) {
        listener = cl;
    }

    public void update(Gamepad g1, Gamepad g2) {
        // This is sadly needed because Java doesn't support pointers
        buttonValues[0] = g1.dpad_up;
        buttonValues[1] = g2.dpad_up;
        buttonValues[2] = g1.dpad_down;
        buttonValues[3] = g2.dpad_down;
        buttonValues[4] = g1.dpad_left;
        buttonValues[5] = g2.dpad_left;
        buttonValues[6] = g1.dpad_right;
        buttonValues[7] = g2.dpad_right;
        buttonValues[8] = g1.a;
        buttonValues[9] = g2.a;
        buttonValues[10] = g1.b;
        buttonValues[11] = g2.b;
        buttonValues[12] = g1.x;
        buttonValues[13] = g2.x;
        buttonValues[14] = g1.y;
        buttonValues[15] = g2.y;
        buttonValues[16] = g1.guide;
        buttonValues[17] = g2.guide;
        buttonValues[18] = g1.start;
        buttonValues[19] = g2.start;
        buttonValues[20] = g1.back;
        buttonValues[21] = g2.back;
        buttonValues[22] = g1.left_bumper;
        buttonValues[23] = g2.left_bumper;
        buttonValues[24] = g1.right_bumper;
        buttonValues[25] = g2.right_bumper;
        buttonValues[26] = g1.left_stick_button;
        buttonValues[27] = g2.left_stick_button;
        buttonValues[28] = g1.right_stick_button;
        buttonValues[29] = g2.right_stick_button;

        for(int i = 0; i < buttonsToCount * 2; i++) {
            if(buttonValues[i] && buttons[i]) {
                listener.actionPerformed(eventTypeList.get(i), EventType.BUTTON_PRESSED, (i + 2 & 1));

                buttons[i] = true;
            } else if(!buttonValues[i]) {
                listener.actionPerformed(eventTypeList.get(i), EventType.BUTTON_RELEASED, (i + 2 & 1));

                buttons[i] = false;
            }
        }
    }
}