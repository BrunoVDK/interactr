package interactr.cs.kuleuven.be.ui.control;

import interactr.cs.kuleuven.be.exceptions.*;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.command.CommandHandler;
import interactr.cs.kuleuven.be.ui.design.Colour;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

/**
 * A class of subwindows for displaying diagram views.
 *  The subwindows can be closed, resized and moved. Cycling between available diagram views
 *  is possible too.
 *
 * @author Team 25
 * @version 1.0
 */
public abstract class SubWindow implements CommandHandler {

    /**
     * Create a new subwindow with a default frame of size 400x400.
     */
    SubWindow() {
        this(null, null);
    }

    /**
     * Create a new subwindow as a duplicate of the given subwindow.
     *
     * @param subWindow The subwindow that is to be duplicated by this subwindow.
     * @param frame The frame to initialize this new subwindow with. If null, a default frame size of 400x400 is used.
     */
    SubWindow(SubWindow subWindow, Rectangle frame) {
        if (subWindow == null)
            setFrame(frame == null ? new Rectangle(0, 0, 400, 400) : frame);
        else
            setFrame(new Rectangle(0, 0, subWindow.getFrame().getWidth(), subWindow.getFrame().getHeight()));
    }

    /**
     * Returns the frame of this subwindow.
     */
    public Rectangle getFrame() {
        return frame;
    }

    /**
     * Returns the frame of this subwindow, including its resize borders.
     *
     * @return The frame of this subwindow with a margin of 5 for each resize border.
     */
    public Rectangle getBorderedFrame() {
        return new Rectangle(frame.getX() - 5, frame.getY() - 5, frame.getWidth() + 10, frame.getHeight() + 10);
    }

    /**
     * Returns the frame in which views of this subwindow are drawn.
     *
     * @return The frame in which views of this subwindow are drawn.
     */
    Rectangle getViewFrame() {
        return new Rectangle(frame.getX(), frame.getY() + TITLE_BAR_HEIGHT, frame.getWidth(), frame.getHeight() - TITLE_BAR_HEIGHT);
    }

    /**
     * Returns whether or not this subwindow can have the given frame as its frame.
     *
     * @param frame The frame to check with.
     * @return True if and only if the given frame's width and height are large enough.
     */
    protected boolean canHaveAsFrame(Rectangle frame) {
        return (frame.getWidth() > CLOSE_BUTTON_SIZE + 10 && frame.getHeight() > TITLE_BAR_HEIGHT + 50);
    }

    /**
     * Set the frame of this subwindow to the given one.
     *
     * @param frame The new frame for this subwindow.
     * @throws IllegalWindowFrameException This subwindow cannot have the given frame as its own frame.
     */
    protected void setFrame(Rectangle frame) throws IllegalWindowFrameException {
        if (!canHaveAsFrame(frame))
            throw new IllegalWindowFrameException();
        this.frame = frame;
    }

    /**
     * Resize this subwindow from the given start coordinate to the given end coordinate.
     *
     * @param fromX The start x coordinate for the resize.
     * @param fromY The start y coordinate for the resize.
     * @param toX The end x coordinate for the resize.
     * @param toY The end y coordinate for the resize.
     * @throws InvalidResizeWindowException The resize was not successful.
     */
    public void resize(int fromX, int fromY, int toX, int toY) throws InvalidResizeWindowException {
        int border = getBordersAt(fromX, fromY);
        if (border == 0)
            throw new InvalidResizeWindowException();
        try {
            Rectangle newFrame = new Rectangle(this.getFrame());
            if ((border & SubWindowBorder.NORTH.code) != 0) {
                newFrame.setY(newFrame.getY() + (toY - fromY));
                newFrame.setHeight(newFrame.getHeight() - (toY - fromY));
            }
            if ((border & SubWindowBorder.EAST.code) != 0)
                newFrame.setWidth(newFrame.getWidth() + (toX - fromX));
            if ((border & SubWindowBorder.SOUTH.code) != 0)
                newFrame.setHeight(newFrame.getHeight() + (toY - fromY));
            if ((border & SubWindowBorder.WEST.code) != 0) {
                newFrame.setX(newFrame.getX() + (toX - fromX));
                newFrame.setWidth(newFrame.getWidth() - (toX - fromX));
            }
            setFrame(newFrame);
        }
        catch (IllegalWindowFrameException e) {throw new InvalidResizeWindowException();}
    }

    /**
     * Moves the this subwindow horizontally and/or vertically.
     *
     * @param fromX The start x coordinate for the move.
     * @param fromY The start y coordinate for the move.
     * @param toX The end x coordinate for the move.
     * @param toY The end y coordinate for the move.
     */
    public void move(int fromX, int fromY, int toX, int toY) throws InvalidMoveWindowException {
        if (!titleBarEncloses(fromX, fromY))
            throw new InvalidMoveWindowException();
        setFrame(new Rectangle(getFrame().getX() + (toX - fromX), getFrame().getY() + (toY - fromY), getFrame().getWidth(), getFrame().getHeight()));
    }

    /**
     * The frame of this subwindow.
     */
    protected Rectangle frame;

    /**
     * Display the currently active view in the given paintboard.
     *
     * @param paintBoard The paintboard on which should be drawn.
     */
    public void display(PaintBoard paintBoard) {
        paintBoard.setClipRect(getFrame()); // Make sure no drawing is done outside the frame
        displayBackground(paintBoard);
        paintBoard.translateOrigin(getFrame().getX(), getFrame().getY() + TITLE_BAR_HEIGHT);
        displayView(paintBoard);
        paintBoard.translateOrigin(-getFrame().getX(), -getFrame().getY() - TITLE_BAR_HEIGHT);
        displayTitleBar(paintBoard);
        displayCloseButton(paintBoard);
    }

    /**
     * Display the view of this subwindow.
     *  This method should be overriden by subclasses.
     *
     * @param paintBoard The paintboard to draw on.
     */
    protected void displayView(PaintBoard paintBoard) {}

    /**
     * Displays the background for this subwindow.
     *
     * @param paintBoard The paintboard on which should be drawn.
     */
    private void displayBackground(PaintBoard paintBoard) {
        paintBoard.setColour(Colour.WHITE);
        paintBoard.fillRectangle(getFrame().getX(), getFrame().getY(), getFrame().getWidth(), getFrame().getHeight());
    }

    /**
     * Displays the title bar for this subwindow.
     *
     * @param paintBoard The paintboard on which should be drawn.
     */
    private void displayTitleBar(PaintBoard paintBoard) {
        paintBoard.setColour(Colour.LIGHT_GRAY);
        paintBoard.fillRectangle(getFrame().getX(), getFrame().getY(), getFrame().getWidth()-1, TITLE_BAR_HEIGHT);
        paintBoard.setColour(Colour.GRAY);
        paintBoard.drawRectangle(getFrame().getX(), getFrame().getY(), getFrame().getWidth()-1, getFrame().getHeight()-1);
        displayTitle(paintBoard);
    }

    /**
     * Display the title of this subwindow on the given paintboard.
     *
     * @param paintBoard The paintboard to draw on.
     */
    private void displayTitle(PaintBoard paintBoard) {
        int titleWidth = paintBoard.getWidthForString(getTitle()); // Fowler prefers no temporary variables :'(
        int titleHeight = paintBoard.getHeightForString(getTitle());
        paintBoard.setColour(Colour.BLACK);
        if (titleWidth < getFrame().getWidth() - CLOSE_BUTTON_SIZE * 3)
            paintBoard.drawString(getTitle(),
                    getFrame().getX() + (getFrame().getWidth() - titleWidth) / 2,
                    getFrame().getY() + TITLE_BAR_HEIGHT - (TITLE_BAR_HEIGHT - titleHeight) / 2);
    }

    /**
     * Displays the close button for this subwindow.
     *
     * @param paintBoard The paintboard on which should be drawn.
     */
    private void displayCloseButton(PaintBoard paintBoard) {
        Rectangle closeButtonFrame = getCloseButtonFrame();
        paintBoard.drawRectangle(closeButtonFrame.getX(), closeButtonFrame.getY(), closeButtonFrame.getWidth(), closeButtonFrame.getHeight());
        paintBoard.drawLine(closeButtonFrame.getX() + 4, closeButtonFrame.getY() + 4, closeButtonFrame.getX() + CLOSE_BUTTON_SIZE - 4, closeButtonFrame.getY() + CLOSE_BUTTON_SIZE - 4);
        paintBoard.drawLine(closeButtonFrame.getX() + 4, closeButtonFrame.getY() + CLOSE_BUTTON_SIZE - 4, closeButtonFrame.getX() + CLOSE_BUTTON_SIZE - 4, closeButtonFrame.getY() + 4);
    }

    /**
     * Returns whether or not the given coordinates lie within this subwindow's title bar.
     *
     * @param x The x coordinate to check with.
     * @param y The y coordinate to check with.
     * @return True if and only if the given coordinates lie within this subwindow's title bar.
     */
    private boolean titleBarEncloses(int x, int y) {
        return (this.getFrame().encloses(x,y) && y < getFrame().getY() + TITLE_BAR_HEIGHT);
    }

    /**
     * Returns whether or not the given coordinates lie within this subwindow's close button.
     *
     * @param x The x coordinate to check with.
     * @param y The y coordinate to check with.
     * @return True if and only if the given coordinates lie within this subwindow's close button.
     */
    public boolean closeButtonEncloses(int x, int y) {
        return getCloseButtonFrame().encloses(x,y);
    }

    /**
     * Returns the frame of the close button of this subwindow.
     *
     * @return A rectangle - the frame of the close button of this subwindow.
     */
    private Rectangle getCloseButtonFrame() {
        int closeX = getFrame().getX() + getFrame().getWidth() - 4 - CLOSE_BUTTON_SIZE, closeY = getFrame().getY() + 3;
        return new Rectangle(closeX, closeY, CLOSE_BUTTON_SIZE, CLOSE_BUTTON_SIZE);
    }

    /**
     * The height of a subwindow's title bar.
     */
    protected final static int TITLE_BAR_HEIGHT = 23;

    /**
     * The size of a subwindow's close button. Should be smaller than the title bar height.
     */
    private final static int CLOSE_BUTTON_SIZE = 17;

    /**
     * Returns a code for the borders that lies at the given coordinate.
     *
     * @param x The x coordinate to check with.
     * @param y The y coordinate to check with.
     * @return An integer representing the borders lying at the given coordinates.
     */
    private int getBordersAt(int x, int y) {
        int borderCode = 0;
        if (Math.abs(getFrame().getX() - x) <= 10)
            borderCode |= SubWindowBorder.WEST.code;
        if (Math.abs(getFrame().getY() - y) <= 4) // Less margin for northern direction
            borderCode |= SubWindowBorder.NORTH.code;
        if (Math.abs( ( getFrame().getY() + getFrame().getHeight() ) - y ) <= 10)
            borderCode |= SubWindowBorder.SOUTH.code;
        if (Math.abs( ( getFrame().getX() + getFrame().getWidth() ) - x ) <= 10)
            borderCode |= SubWindowBorder.EAST.code;
        return borderCode;
    }

    /**
     * Returns the title of this subwindow.
     *
     * @return The title of this subwindow.
     */
    public abstract String getTitle();

    /**
     * Close this subwindow.
     */
    public void close() {isClosed = true;}

    /**
     * Returns whether or not this subwindow is closed.
     */
    public boolean isClosed() {
        return isClosed;
    }

    /**
     * Registers whether or not this subwindow is closed.
     */
    private boolean isClosed = false;

}