package interactr.cs.kuleuven.be.ui;

import java.awt.*;

/**
 * A class of windows for displaying and interacting with interaction diagrams.
 *
 * @author Team 25
 * @version 1.0
 */
public class DiagramWindow extends CanvasWindow {

    /**
     * Initializes this new window with an empty title.
     */
    public DiagramWindow() {
        this("");
    }

    /**
     * Initializes this new window with given title and diagram handler.
     *
     * @param title The title for the new window.
     */
    DiagramWindow(String title) {
        super(title);
    }

    /**
     * Paints the contents of this window in the given graphics context.
     *
     * @param context The graphics context in which to draw.
     */
    @Override
    protected void paint(Graphics context) {
        if (getPaintBoard() != null)
            getPaintBoard().paint(context);
    }

    /**
     * Called when the user presses (id == MouseEvent.MOUSE_PRESSED),
     *  releases (id == MouseEvent.MOUSE_RELEASED),
     *  or drags (id == MouseEvent.MOUSE_DRAGGED) the mouse.
     *
     * @param id The type of mouseEvent (PRESSSED, RELEASED, DRAGGED).
     * @param x The x value where the mouseEvent happened.
     * @param y The y value where the mouseEvent happened.
     * @param clickCount How many clicks there were.
     */
    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        if (getEventHandler() != null)
            getEventHandler().handleMouseEvent(id, x, y, clickCount);
    }

    /**
     * Called when the user presses a key (id == KeyEvent.KEY_PRESSED)
     *  or enters a character (id == KeyEvent.KEY_TYPED).
     *
     * @param id the type of mouseEvent (PRESSSED, TYPED).
     * @param keyCode The key code for the event.
     * @param keyChar The key char for the event.
     * @param keyModifiers The key modifiers.
     */
    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar, int keyModifiers) {
        if (getEventHandler() != null)
            getEventHandler().handleKeyEvent(id, keyCode, keyChar, keyModifiers);
    }

    /**
     * Returns the paint board associated with this diagram window.
     */
    public PaintBoard getPaintBoard() {
        return paintBoard;
    }

    /**
     * Set the paint board of this diagram window to match the given one.
     *
     * @param paintBoard The new paint board for this window.
     * @throws IllegalArgumentException The given paint board's associated window does not
     *  equal this diagram window.
     */
    public void setPaintBoard(PaintBoard paintBoard) throws IllegalArgumentException {
        if (paintBoard.getDiagramWindow() != this)
            throw new IllegalArgumentException("Paintboard's window invalid.");
        this.paintBoard = paintBoard;
    }

    /**
     * Registers the paint board associated with this window.
     */
    private PaintBoard paintBoard;

    /**
     * Returns the event handler associated with this diagram window.
     */
    private EventHandler getEventHandler() {
        return eventHandler;
    }

    /**
     * Set the event handler of this diagram window to match the given one.
     *
     * @param eventHandler The new event handler for this window.
     */
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * Registers the event handler associated with this window.
     */
    private EventHandler eventHandler;

    /**
     * Returns the width of this window.
     */
    public int getWidth() {
        return this.panel.getWidth();
    }

    /**
     * Returns the height of this window.
     */
    public int getHeight() {
        return this.panel.getHeight();
    }

}