package org.firstinspires.ftc.team10940.util;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Utility class for button listener.
 * Handles all the button events.
 */

public class ControllerHandler {

    private ControllerListener listener; // ControllerListener that the handler will call

    private boolean buttons[];                  // Past button values
    private boolean buttonValues[];             // Temporary button values because Java doesn't support pointers
    private final short buttonsToCount = 15;    // Buttons to count to determine array sizes
    private final EventName eventTypeList[];    // List of eventTypes (in Enum form)
    private final String    eventTypeListStr[]; // List of eventTypes (in String form)

    // Will be used in v2
    private ArrayList<EventName> presentPressed;
    private ArrayList<EventName> presentReleased;
    private ArrayList<EventName> presentPressing;

    // Will be used in v2
    ArrayList<Object[]> listenerList;

    public ControllerHandler() {
        buttons = new boolean[buttonsToCount * 2];      //x buttons to count * 2 gamepads
        buttonValues = new boolean[buttonsToCount * 2]; //x buttons to count * 2 gamepads

        //Will be used in v2
        listenerList = new ArrayList<Object[]>();

        eventTypeList = EventName.values();               // Makes a list out of the EventName values
        eventTypeListStr = Arrays.toString(eventTypeList) // Converts enum list to string
                                 .replaceAll("^.|.$", "")
                                 .split(", ");
    }

    /**
     * Register the listener
     * @param cl ControllerListener to register
     */
    public void registerListener(ControllerListener cl) {
        listener = cl;
    }

    //Doesn't currently work yet
    public void on(String str, EventType et, ControllerListener cl) {
        if(Arrays.asList(eventTypeListStr).contains(str)) return; // Breaks off if string is not in button list

        Object[] tempArray = new Object[3];
        tempArray[0] = str;
        tempArray[1] = et;
        tempArray[2] = cl;
        listenerList.add(tempArray);
    }

    /**
     * Updates the handler values and calls the listener
     * @param g1 Gamepad 1 to harvest values
     * @param g2 Gamepad 2 to harvest values
     */
    public void update(Gamepad g1, Gamepad g2) {
        // This monstrosity is sadly needed because Java doesn't support pointers
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

        // Runs through all buttons to update values
        for(int i = 0; i < buttonsToCount * 2; i++) {
            if(buttonValues[i] && !buttons[i]) { // If the button is pressed and past value isn't
                // Call the listener with the current eventType (only even numbers since 2 gamepads), BUTTON_PRESSED event, and gamepad type
                listener.actionPerformed(eventTypeList[Math.round(i / 2)], EventType.BUTTON_PRESSED, returnGamepad(i));

                // Sets past value to pressed
                buttons[i] = true;
            } else if(!buttonValues[i] && buttons[i]) { // If the button isn't pressed and passed is pressed
                // Call the listener with the current eventType (only even numbers since 2 gamepads), BUTTON_RELEASED event, and gamepad type
                listener.actionPerformed(eventTypeList[Math.round(i / 2)], EventType.BUTTON_RELEASED, returnGamepad(i));

                // Sets past value to not pressed
                buttons[i] = false;
            }
        }
    }

    // Will work in v2
    public void update2(Gamepad g1, Gamepad g2) {
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
            if(buttonValues[i]) {
                presentPressing.add(eventTypeList[Math.round(i / 2)]);
            } else if(buttonValues[i] && !buttons[i]) {
                presentPressed.add(eventTypeList[Math.round(i / 2)]);

                buttons[i] = true;
            } else if(!buttonValues[i] && buttons[i]) {
                presentReleased.add(eventTypeList[Math.round(i / 2)]);

                buttons[i] = false;
            }
        }

        presentPressed.clear();
        presentReleased.clear();
        presentPressing.clear();
    }

    /**
     * Small function to return gamepad type
     * @param i Value
     * @return Gamepad1 if even. Gamepad2 if odd.
     */
    private GamepadType returnGamepad(int i) {
        if(i % 2 == 0)
            return GamepadType.GAMEPAD1;
        else
            return GamepadType.GAMEPAD2;
    }
}