package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.ui.design.Colour;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

import java.awt.*;

/**
 * A class of paint boards for drawing basic geometric shapes.
 *  Paint boards allow for encapsulation of how it is, exactly, that the drawing is done.
 *
 * @author Team 25
 * @version 1.0
 */
public class PaintBoard {

    /**
     * Initialize this new paint board with given diagram window and diagram controller.
     *
     * @param window The diagram window to associate this paint board with.
     * @param controller The diagram controller to associate this paint board with.
     * @throws IllegalArgumentException The given window is nil.
     */
    public PaintBoard(Window window, Controller controller) throws IllegalArgumentException {
        if (window == null)
            throw new IllegalArgumentException("Null window.");
        this.window = window;
        setController(controller);
    }

    /**
     * Refresh this paint board by redrawing its contents.
     */
    public void refresh() {
        this.getWindow().repaint();
    }

    /**
     * Make this paint board paint its contents in the given graphics context.
     *
     * @param context The graphics context to draw in.
     */
    public void paint(Graphics context) {
        currentContext = context;
        context.setFont(defaultFont);
        getController().displayAllSubWindows();
    }

    /**
     * Draw a line at given start and end coordinates on this paint board.
     *
     * @param x1 The start x coordinate for the line.
     * @param y1 The start y coordinate for the line.
     * @param x2 The end x coordinate for the line.
     * @param y2 The end y coordinate for the line.
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
        if (currentContext != null)
            currentContext.drawLine(x1, y1, x2, y2);
    }

    /**
     * Draw a rectangle of given width and height at the given coordinates on this paint board.
     *
     * @param x The start x coordinate for the line.
     * @param y The start y coordinate for the line.
     * @param width The end x coordinate for the line.
     * @param height The end y coordinate for the line.
     */
    public void drawRectangle(int x, int y, int width, int height) {
        if (currentContext != null)
            currentContext.drawRect(x, y, width, height);
    }

    /**
     * Draw an oval at given coordinate of given width and height in this paint board.
     *
     * @param x The x coordinate of the center of the oval that is to be drawn.
     * @param y The y coordinate of the center of the oval that is to be drawn.
     * @param width The width of the oval that is to be drawn.
     * @param height The height of the oval that is to be drawn.
     */
    public void drawOval(int x, int y, int width, int height) {
        if (currentContext != null)
            currentContext.drawOval(x, y, width, height);
    }

    /**
     * Draw the given string at the given coordinate.
     *
     * @param string The string that is to be drawn.
     * @param x The x coordinate to draw at.
     * @param y The y coordinate to draw at.
     */
    public void drawString(String string, int x, int y) {
        if (currentContext != null)
            currentContext.drawString(string, x, y);
    }

    /**
     * Fill a rectangle of given width and height at the given coordinates on this paint board.
     *
     * @param x The start x coordinate for the line.
     * @param y The start y coordinate for the line.
     * @param width The end x coordinate for the line.
     * @param height The end y coordinate for the line.
     */
    public void fillRectangle(int x, int y, int width, int height) {
        if (currentContext != null)
            currentContext.fillRect(x, y, width, height);
    }

    /**
     * Translate the origin of this paintboard to the given coordinate.
     *  All drawing in subsequent calls will be relative to this new origin.
     *
     * @param x The new x coordinate for the origin.
     * @param y The new y coordinate for the origin.
     */
    public void translateOrigin(int x, int y) {
        if (currentContext != null)
            currentContext.translate(x, y);
    }

    /**
     * Get the current colour used when drawing.
     *
     * @return The colour currently used for drawing in this paintboard.
     */
    public Colour getColour() {
        if (currentContext != null) {
            float[] components = currentContext.getColor().getColorComponents(null);
            return new Colour(components[0], components[1], components[2]);
        }
        return Colour.BLACK;
    }

    /**
     * Sets the colour for this paint board.
     *
     * @param colour The colour to use for this paint board.
     */
    public void setColour(Colour colour) {
        if (currentContext != null)
            currentContext.setColor(Color.getHSBColor(colour.getHue(), colour.getSaturation(), colour.getBrightness()));
    }

    /**
     * Returns the width for this board.
     *
     * @return The width of this paint board.
     */
    public int getWidth() {
        return getWindow().getWidth();
    }

    /**
     * Returns the height for this board.
     *
     * @return The height of this paint board.
     */
    public int getHeight() {
        return getWindow().getHeight();
    }

    /**
     * The new clipping rectangle for this paintboard.
     *
     * @param clipRect The new clipping rectangle for this paintboard.
     */
    public void setClipRect(Rectangle clipRect) {
        this.currentContext.setClip(new java.awt.Rectangle(clipRect.getX(), clipRect.getY(), clipRect.getWidth(), clipRect.getHeight()));
    }

    /**
     * Variable registering the current graphics context of this paint board.
     */
    private Graphics currentContext;

    /**
     * Returns the diagram window associated with this paint board.
     */
    public Window getWindow() {
        return window;
    }

    /**
     * Variable registering the diagram window associated with this paint board.
     */
    private Window window;

    /**
     * Associate the given diagram controller with this paint board.
     *
     * @param controller The diagram controller associated with this paint board.
     */
    private void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Returns the diagram controller associated with this paint board.
     */
    private Controller getController() {
        return this.controller;
    }

    /**
     * Variable registering the diagram controller associated with this paint board.
     */
    private Controller controller;

    /**
     * The default font used by this paint board.
     */
    private static Font defaultFont = new Font("Monospaced", Font.PLAIN, 12);

    /**
     * Returns the char height used for labels.
     */
    public static int charHeight = 12;

    /**
     * Returns the char width used for labels.
     */
    public static int charWidth = 6;

    static {
        PaintBoard.charHeight = defaultFont.getSize();
        PaintBoard.charWidth = java.awt.Toolkit.getDefaultToolkit().getFontMetrics(defaultFont).charWidth('a');
    }

}