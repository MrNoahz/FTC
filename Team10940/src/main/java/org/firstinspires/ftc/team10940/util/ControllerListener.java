package org.firstinspires.ftc.team10940.util;

/**
 * Interface used by ControllerHandler.
 * actionPerformed is called when ControllerHandler updates
 */
public interface ControllerListener {
    /**
     * Called by ControllerHandler when updates
     * @param en EventName
     * @param et EventType
     * @param gt GamepadType
     */
    void actionPerformed(EventName en, EventType et, GamepadType gt);
}