package interactr.cs.kuleuven.be.ui;

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
     * @param diagramWindow The diagram window to associate this paint board with.
     * @param diagramController The diagram controller to associate this paint board with.
     * @throws IllegalArgumentException The given window is nil.
     */
    public PaintBoard(DiagramWindow diagramWindow, DiagramController diagramController) throws IllegalArgumentException {
        if (diagramWindow == null)
            throw new IllegalArgumentException("Null window.");
        this.diagramWindow = diagramWindow;
        setDiagramController(diagramController);
    }

    /**
     * Refresh this paint board by redrawing its contents.
     */
    public void refresh() {
        this.getDiagramWindow().repaint();
    }

    /**
     * Make this paint board paint its contents in the given graphics context.
     *
     * @param context The graphics context to draw in.
     */
    public void paint(Graphics context) {
        currentContext = context;
        context.setFont(defaultFont);
        // Alternatively, ask for figures (basic geometric shapes) to draw and
        //  draw them with private methods
        getDiagramController().displayView();
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
     * Get the width of the given string when drawn in this paint board.
     *
     * @param string The string whose display width is desired.
     * @return The width of the given string when drawn in this paint board.
     */
    public int getWidthForString(String string) {
        if (currentContext != null)
            return currentContext.getFontMetrics().stringWidth(string);
        return 0;
    }

    /**
     * Get the height of chars when drawn in this paint board.
     *
     * @return The height of chars when displayed in this paint board.
     */
    public int getHeightOfChars() {
        if (currentContext != null)
            return currentContext.getFont().getSize();
        return 0;
    }

    /**
     * Returns the width for this board.
     *
     * @return The width of this paint board.
     */
    public int getWidth() {
        return getDiagramWindow().getWidth();
    }

    /**
     * Returns the height for this board.
     *
     * @return The height of this paint board.
     */
    public int getHeight() {
        return getDiagramWindow().getHeight();
    }

    /**
     * Variable registering the current graphics context of this paint board.
     */
    private Graphics currentContext;

    /**
     * Returns the diagram window associated with this paint board.
     */
    public DiagramWindow getDiagramWindow() {
        return diagramWindow;
    }

    /**
     * Variable registering the diagram window associated with this paint board.
     */
    private DiagramWindow diagramWindow;

    /**
     * Associate the given diagram controller with this paint board.
     *
     * @param diagramController The diagram controller associated with this paint board.
     */
    public void setDiagramController(DiagramController diagramController) {
        this.diagramController = diagramController;
    }

    /**
     * Returns the diagram controller associated with this paint board.
     */
    public DiagramController getDiagramController() {
        return this.diagramController;
    }

    /**
     * Variable registering the diagram controller associated with this paint board.
     */
    private DiagramController diagramController;

    /**
     * The default font used by this paint board.
     */
    private static Font defaultFont = new Font("Monospaced", Font.PLAIN, 12);

}