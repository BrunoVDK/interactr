package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.exceptions.InvalidAddPartyException;
import interactr.cs.kuleuven.be.exceptions.NoSuchPartyException;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

import java.util.ArrayList;

/**
 * A class of subwindows for displaying diagrams.
 *  The subwindows can be closed, resized and moved.
 *
 * @author Team 25
 * @version 1.0
 */
public class SubWindow {

    /**
     * Create a new subwindow without duplicating an other one.
     */
    public SubWindow() {
        this(null);
    }

    /**
     * Create a new subwindow with a default frame of size 400x400 as a duplicate of the given subwindow.
     *  If the given subwindow is null, a subwindow with a new (empty) diagram is created.
     *  Otherwise the diagram of the given subwindow is adopted by this new subwindow.
     *
     * @param subWindow The subwindow that is to be duplicated by this subwindow.
     */
    public SubWindow(SubWindow subWindow) {
        setFrame(new Rectangle(0, 0, 400, 400));
        Diagram adoptedDiagram = null;
        if (subWindow == null)
            adoptedDiagram = new Diagram();
        else
            adoptedDiagram = subWindow.getDiagram();
        views.add(new SequenceView(adoptedDiagram));
        views.add(new CommunicationView(adoptedDiagram));
    }

    /**
     * Returns the diagram associated with this subwindow.
     */
    public Diagram getDiagram() {
        return null;
    }

    /**
     * Returns the frame of this subwindow.
     */
    public Rectangle getFrame() {
        return frame;
    }

    /**
     * Set the frame of this subwindow to the given one.
     *
     * @param   frame
     *          The new frame for this subwindow.
     */
    public void setFrame(Rectangle frame) {
        this.frame = frame;
    }

    /**
     * Set the size of this subwindow to the new size.
     *
     * @param   width
     *          The new width for this subwindow's frame.
     * @param   height
     *          The new height for this subwindow's frame.
     * @throws  IllegalArgumentException
     *          The given width is smaller than one.
     * @throws  IllegalArgumentException
     *          The given height is smaller than one.
     */
    public void setSize(int width, int height) {
        if (width < 1)
            throw new IllegalArgumentException("The width cannot be smaller than 1.");
        if (height < 1)
            throw new IllegalArgumentException("The height cannot be smaller than 1.");
        this.frame.setWidth(width);
        this.frame.setHeight(height);
    }

    /**
     * The frame of this subwindow.
     */
    private Rectangle frame;

    /**
     * Add a party at the given coordinates in the currently active diagram view.
     *
     * @param x The x coordinate to add the party at.
     * @param y The y coordinate to add the party at.
     * @throws InvalidAddPartyException If a party cannot be added at the given coordinates.
     */
    public void addPartyAt(int x, int y) throws InvalidAddPartyException {
        // TODO
    }

    /**
     * Add a message from the given coordinates to the given ones.
     *
     * @param fromX The starting x coordinate for the message.
     * @param fromY The starting y coordinate for the message.
     * @param toX The ending x coordinate for the message.
     * @param toY The ending y coordinate for the message.
     */
    public void addMessage(int fromX, int fromY, int toX, int toY) {
        // TODO
    }

    /**
     * Switch the type of the party at given coordinates.
     *
     * @param x The x coordinate of the party.
     * @param y The y coordinate of the party.
     */
    public void switchTypeofPartyAt(int x, int y) {
        // Get the party
    }

    /**
     * Select the component at the given coordinates in this subwindow.
     *
     * @param x The x coordinate of the component that is to be selected.
     * @param y The y coordinate of the component that is to be selected.
     */
    public void selectComponentAt(int x, int y) {
        // TODO
    }

    /**
     * Removes the currently selected component from this subwindow's diagram.
     */
    public void deleteSelection(){
        // TODO
    }

    /**
     * Start moving the party at the given coordinates.
     *  This simply starts a move session for the party at the given coordinates.
     *  If there is no party at those coordinates, an exception is thrown.
     *
     * @param x The x coordinate of the party that is to be moved.
     * @param y The y coordinate of the party that is to be moved.
     * @throws NoSuchPartyException If there is no party at the given coordinates.
     */
    public void movePartyAt(int x, int y) throws NoSuchPartyException {
        movedParty = getActiveView().getPartyAt(x, y);
        if (movedParty == null)
            throw new NoSuchPartyException(x, y);
    }

    /**
     * Move the party that is currently being moved to the given of coordinates.
     *
     * @param x The new x coordinate for the party.
     * @param y The new y coordinate for the party.
     */
    public void movePartyTo(int x, int y) {

    }

    /**
     * The party for the currently active moving session.
     */
    private Party movedParty = null;

    /**
     * Switch to the next view.
     */
    public void nextView() {
        activeViewIndex = (activeViewIndex + 1) % views.size();
    }

    /**
     * Display the currently active view in the given paintboard.
     *
     * @param paintBoard The paintboard on which should be drawn.
     */
    public void displayView(PaintBoard paintBoard) {

        Rectangle frame = getFrame();
        paintBoard.setClipRect(frame); // Make sure no drawing is done outside the frame
        getActiveView().display(paintBoard); // Draw view contents

        // Draw the sub window's frame
        paintBoard.setColour(Colour.GRAY);
        paintBoard.fillRectangle(frame.getX(), frame.getY(), frame.getWidth(), frame.getHeight());
        paintBoard.setColour(Colour.BLACK);
        paintBoard.drawRectangle(frame.getX(), frame.getY(), frame.getWidth(), frame.getHeight());

    }

    /**
     * Returns the currently active view in this subwindow.
     *
     * @return The active view for this subwindow.
     */
    public DiagramView getActiveView() {
        return views.get(activeViewIndex);
    }

    /**
     * The index of the currently active view in this subwindow.
     */
    private int activeViewIndex = 0;

    /**
     * A list of diagram views held by this subwindow.
     */
    private ArrayList<DiagramView> views = new ArrayList<DiagramView>();

}