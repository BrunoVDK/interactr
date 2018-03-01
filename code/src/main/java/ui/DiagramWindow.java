package main.java.ui;

import java.awt.*;

/**
 * A class of windows for displaying and interacting with interaction diagrams.
 *
 * @author Team 25
 * @version 1.0
 */
public class DiagramWindow extends CanvasWindow {

    /**
     * Initializes this new window with given title.
     *
     * @param title The title for the new window.
     * @post The title of this window equals the given one.
     */

    protected DiagramWindow(String title) {
        super(title);
    }

    /**
     * Paints the contents of this window in the given graphics context.
     *
     * @param context The graphics context in which to draw.
     */
    @Override
    protected void paint(Graphics context) {
        // Delegate to controller
    }

    /**
     * Called when the user presses (id == MouseEvent.MOUSE_PRESSED),
     *  releases (id == MouseEvent.MOUSE_RELEASED),
     *  or drags (id == MouseEvent.MOUSE_DRAGGED) the mouse.
     *
     * @param id The type of mouseEvent (PRESSSED, RELEASED, DRAGGED)
     * @param x The x value where the mouseEvent happened
     * @param y The y value where the mouseEvent happened
     * @param clickCount How many clicks there were
     */
    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        // Delegate to controller
    }

    /**
     * Called when the user presses a key (id == KeyEvent.KEY_PRESSED)
     *  or enters a character (id == KeyEvent.KEY_TYPED).
     *
     * @param id the type of mouseEvent (PRESSSED, TYPED)
     * @param keyCode The key code for the event.
     * @param keyChar The key char for the event.
     */
    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        // Delegate to controller
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new DiagramWindow("My Canvas Window").show();
        });
    }

}