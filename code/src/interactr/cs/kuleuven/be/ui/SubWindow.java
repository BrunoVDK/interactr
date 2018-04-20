package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.exceptions.*;
import interactr.cs.kuleuven.be.purecollections.PList;
import interactr.cs.kuleuven.be.purecollections.PMap;
import interactr.cs.kuleuven.be.ui.geometry.Colour;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

/**
 * A class of subwindows for displaying diagram views.
 *  The subwindows can be closed, resized and moved. Cycling between available diagram views
 *  is possible too.
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
    SubWindow(SubWindow subWindow) {
        if (subWindow == null || subWindow.getDiagram() == null) {
            setFrame(new Rectangle(0, 0, 400, 400));
            Diagram adoptedDiagram = new Diagram();
            views = views.plus(new SequenceView(adoptedDiagram));
            views = views.plus(new CommunicationView(adoptedDiagram));
        }
        else {
            setFrame(new Rectangle(0, 0, subWindow.getFrame().getWidth(), subWindow.getFrame().getHeight()));
            for (DiagramView view : subWindow.getViews())
                views = views.plus(view.clone());
            activeViewIndex = subWindow.getViews().indexOf(subWindow.getActiveView());
        }
        DiagramNotificationCenter.defaultCenter().registerSubWindow(getDiagram(), this);
    }

    /**
     * Returns the diagram associated with this subwindow.
     */
    public Diagram getDiagram() {
        return (views.size() == 0 ? null : views.get(0).getDiagram());
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
        return new Rectangle(
                frame.getX() - 5,
                frame.getY() - 5,
                frame.getWidth() + 10,
                frame.getHeight() + 10);
    }

    /**
     * Returns the frame in which views of this subwindow are drawn.
     *
     * @return The frame in which views of this subwindow are drawn.
     */
    public Rectangle getViewFrame() {
        return new Rectangle(
                frame.getX(),
                frame.getY() + TITLE_BAR_HEIGHT,
                frame.getWidth(),
                frame.getHeight() - TITLE_BAR_HEIGHT
        );
    }

    /**
     * Returns whether or not this subwindow can have the given frame as its frame.
     *
     * @param frame The frame to check with.
     * @return True if and only if the given frame's width and height are large enough.
     */
    private boolean canHaveAsFrame(Rectangle frame) {
        return (frame.getWidth() > CLOSE_BUTTON_SIZE + 10 && frame.getHeight() > TITLE_BAR_HEIGHT + 10);
    }

    /**
     * Set the size of this subwindow to the new size.
     *
     * @param   width
     *          The new width for this subwindow's frame.
     * @param   height
     *          The new height for this subwindow's frame.
     */
    public void setSize(int width, int height) {
        setFrame(new Rectangle(frame.getX(), frame.getY(), width, height));
    }

    /**
     * Set the frame of this subwindow to the given one.
     *
     * @param frame The new frame for this subwindow.
     * @throws IllegalWindowFrameException This subwindow cannot have the given frame as its own frame.
     */
    private void setFrame(Rectangle frame) throws IllegalWindowFrameException {
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

        // Get the borders at the start coordinates
        int border = getBordersAt(fromX, fromY);
        if (border == 0) // No resize can be done from the given start coordinates (no border)
            throw new InvalidResizeWindowException();

        // Calculate the new frame based on the end coordinates
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

        // Try to set the frame or throw an exception if the new frame is invalid
        try {
            setFrame(newFrame);
        }
        catch (IllegalWindowFrameException e) {
            throw new InvalidResizeWindowException();
        }

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
        getFrame().setX(getFrame().getX() + (toX - fromX));
        getFrame().setY(getFrame().getY() + (toY - fromY));
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
    public void addParty(int x, int y) throws InvalidAddPartyException {
        if (y < TITLE_BAR_HEIGHT) throw new InvalidAddPartyException();
        else y -= TITLE_BAR_HEIGHT;
        getActiveView().addParty(x,y);
        setSelectedComponent(getActiveView().getParty(x,y));
        setSelectedLabel("");
    }

    /**
     * Switch the type of the party at given coordinates.
     *
     * @param x The x coordinate of the party.
     * @param y The y coordinate of the party.
     */
    public void switchTypeOfParty(int x, int y) {
        getActiveView().switchTypeOfParty(x,y-TITLE_BAR_HEIGHT);
    }

    /**
     * Add a message from the given coordinates to the given ones.
     *
     * @param fromX The starting x coordinate for the message.
     * @param fromY The starting y coordinate for the message.
     * @param toX The ending x coordinate for the message.
     * @param toY The ending y coordinate for the message.
     * @throws InvalidAddMessageException The operation was not successful.
     */
    public void addMessage(int fromX, int fromY, int toX, int toY) throws InvalidAddMessageException {
        InvocationMessage message = getActiveView().getInvocationMessageForCoordinates(fromX, fromY, toX, toY);
        getActiveView().addMessage(message, fromX, fromY - TITLE_BAR_HEIGHT, toX, toY - TITLE_BAR_HEIGHT);
        setSelectedComponent(message);
        setSelectedLabel("");
    }

    /**
     * Select the component at the given coordinates in this subwindow.
     *
     * @param x The x coordinate of the component that is to be selected.
     * @param y The y coordinate of the component that is to be selected.
     */
    public void selectComponent(int x, int y) {
        DiagramComponent component = getActiveView().getSelectableComponent(x, y-TITLE_BAR_HEIGHT);
        if (component != null) {
            if (isSelected(component)) // Component already selected, activate it
                setSelectedLabel("");
            setSelectedComponent(component);
        }
    }

    /**
     * Returns whether or not the given diagram component is currently selected.
     *
     * @param component The component to check for.
     * @return True if and only if the given diagram component is currently selected.
     */
    private boolean isSelected(DiagramComponent component) {
        return getSelectedComponent() == component;
    }

    /**
     * Returns the diagram component that is currently active in this diagram.
     */
    public DiagramComponent getSelectedComponent() {
        return selectedComponent;
    }

    /**
     * Sets a diagramcomponent in this diagram as active component.
     *
     * @param component the diagram component that needs to be set as active component.
     */
    private void setSelectedComponent(DiagramComponent component) {
        this.selectedComponent = component;
    }

    /**
     * Deselects the currently selected component.
     *
     * @throws InvalidLabelException If the selected component is active and its label is invalid.
     */
    public void deselectAll() throws InvalidLabelException {
        if (selectionIsActive() && !getSelectedComponent().canHaveAsLabel(getSelectedLabel()))
            throw new InvalidLabelException();
        setSelectedLabel(null);
        setSelectedComponent(null);
    }

    /**
     * Returns whether or not the selection for this subwindow is active (that is, in edit mode).
     *
     * @return True if and only if the selected label is not null.
     */
    public boolean selectionIsActive() {
        return selectedLabel != null;
    }

    /**
     * Registers the active component in this diagram.
     */
    private DiagramComponent selectedComponent = null;

    /**
     * Sets the temporary label for the active component to the given one.
     *
     * @param selectedLabel The new temporary label for the active component.
     */
    public void setSelectedLabel(String selectedLabel){
        this.selectedLabel = selectedLabel;
        if (selectedComponent != null && selectedComponent.canHaveAsLabel(selectedLabel)) {
            selectedComponent.setLabel(selectedLabel);
            DiagramNotificationCenter.defaultCenter().diagramDidEditLabel(getDiagram(), getSelectedComponent());
        }
    }

    /**
     * Returns the temporary label of the currently active component in this diagram.
     */
    public String getSelectedLabel(){
        return selectedLabel;
    }

    /**
     * The temporary label for the active component of this diagram.
     */
    private String selectedLabel = null;

    /**
     * Removes the currently selected component from this subwindow's diagram.
     */
    public void deleteSelection(){
        if (selectedLabel == null && getSelectedComponent() != null) { // Only delete if not active
            getSelectedComponent().deleteFrom(getDiagram());
            DiagramNotificationCenter.defaultCenter().diagramDidDeleteComponent(getDiagram(), getSelectedComponent());
            deselectAll(); // Now deselect everything
        }
    }

    /**
     * Move the party at the given start coordinates to the given end coordinates.
     *
     * @param fromX The start x coordinate for the add.
     * @param fromY The start y coordinate for the add.
     * @param toX The end x coordinate for the add.
     * @param toY The end y coordinate for the add.
     * @throws NoSuchPartyException If there is no party at the given start coordinates.
     * @throws InvalidMovePartyException If the party could not be moved to the given end coordinates.
     */
    public void moveParty(int fromX, int fromY, int toX, int toY) throws NoSuchPartyException, InvalidMovePartyException {
        if (selectionIsActive())
            return;
        getActiveView().moveParty(fromX, fromY - TITLE_BAR_HEIGHT, toX, toY - TITLE_BAR_HEIGHT);
    }

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
        paintBoard.setClipRect(getFrame()); // Make sure no drawing is done outside the frame
        displayBackground(paintBoard);
        paintBoard.translateOrigin(getFrame().getX(), getFrame().getY() + TITLE_BAR_HEIGHT);
        getActiveView().display(paintBoard, getSelectedComponent(), getSelectedLabel(), getViewFrame()); // Draw view contents
        paintBoard.translateOrigin(-getFrame().getX(), -getFrame().getY() - TITLE_BAR_HEIGHT);
        displayTitleBar(paintBoard);
        displayCloseButton(paintBoard);
    }

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
    }

    /**
     * Displays the close button for this subwindow.
     *
     * @param paintBoard The paintboard on which should be drawn.
     */
    private void displayCloseButton(PaintBoard paintBoard) {
        Rectangle closeButtonFrame = getCloseButtonFrame();
        paintBoard.drawRectangle(closeButtonFrame.getX(), closeButtonFrame.getY(), closeButtonFrame.getWidth(), closeButtonFrame.getHeight());
        paintBoard.drawLine(closeButtonFrame.getX(), closeButtonFrame.getY(), closeButtonFrame.getX() + CLOSE_BUTTON_SIZE, closeButtonFrame.getY() + CLOSE_BUTTON_SIZE);
        paintBoard.drawLine(closeButtonFrame.getX(), closeButtonFrame.getY() + CLOSE_BUTTON_SIZE, closeButtonFrame.getX() + CLOSE_BUTTON_SIZE, closeButtonFrame.getY());
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
     * Returns the views in this subwindow.
     *
     * @return The diagram views held by this subwindow.
     */
    public PList<DiagramView> getViews() {
        return views;
    }

    /**
     * A list of diagram views held by this subwindow.
     */
    private PList<DiagramView> views = PList.<DiagramView>empty();

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
        int closeX = frame.getX() + frame.getWidth() - 4 - CLOSE_BUTTON_SIZE, closeY = frame.getY() + 3;
        return new Rectangle(closeX, closeY, CLOSE_BUTTON_SIZE, CLOSE_BUTTON_SIZE);
    }

    /**
     * The height of a subwindow's title bar.
     */
    private final static int TITLE_BAR_HEIGHT = 23;

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
     * Notify this subwindow that the given component in the given diagram changed.
     *
     * @param diagram The diagram to which the component belongs.
     * @param component The component whose label was edited.
     */
    public void diagramDidEditLabel(Diagram diagram, DiagramComponent component) {
        if (isSelected(component) && selectionIsActive())
            this.selectedLabel = component.getLabel();
    }

    /**
     * Close this subwindow.
     *  This unregisters it as an observer.
     */
    public void close() {
        DiagramNotificationCenter.defaultCenter().unregisterSubWindow(getDiagram(), this);
        for (DiagramView view : views)
            view.close(); // Close all views too, but only after unregistering this subwindow as observer
    }

}