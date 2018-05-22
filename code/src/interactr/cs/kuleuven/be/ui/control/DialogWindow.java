package interactr.cs.kuleuven.be.ui.control;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.command.Command;
import interactr.cs.kuleuven.be.ui.command.CommandNotProcessedException;
import interactr.cs.kuleuven.be.ui.control.control.Control;
import interactr.cs.kuleuven.be.ui.design.Colour;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

/**
 * A class of dialog windows.
 *
 * @author Team 25
 * @version 1.0
 */
public abstract class DialogWindow extends SubWindow {

    /**
     * Initialize this new dialog with given diagram.
     *
     * @param diagram The diagram to initialize this new dialog with.
     */
    public DialogWindow(Diagram diagram) {
        if (diagram == null)
            throw new IllegalArgumentException("Diagram cannot be null.");
        this.diagram = diagram;
        this.frame = getDefaultFrame();
    }

    @Override
    protected boolean canHaveAsFrame(Rectangle frame) {
        return  super.canHaveAsFrame(frame)
                && (frame.getHeight() >= getDefaultFrame().getHeight() && frame.getWidth() >= getDefaultFrame().getWidth());
    }

    /**
     * Displays the given control in the given paintboard.
     *
     * @param paintBoard The paintboard to draw on.
     * @param control The control that is to be drawn.
     * @param x The x coordinate to draw the control at.
     * @param y The y coordinate to draw the control at.
     */
    protected final void display(PaintBoard paintBoard, Control control, int x, int y) {
        if (focusedControl == control)
            paintBoard.setColour(Colour.BLUE);
        else
            paintBoard.setColour(Colour.BLACK);
        control.display(paintBoard, x, y);
    }

    /**
     * Returns the focused control for this dialog window.
     */
    protected Control getFocusedControl() {
        return focusedControl;
    }

    /**
     * Sets the focused control to the given one.
     *
     * @param control The control to focus on.
     */
    protected void setFocusedControl(Control control) {
        focusedControl = control;
    }

    /**
     * Focus on the next control.
     */
    public void focusNext() {}

    /**
     * Registers the control focused on by this dialog window.
     */
    private Control focusedControl = null;

    /**
     * A method that is used by the Dialog Diagram
     */
    public abstract void goUp();

    /**
     * A method that is used by the Dialog Diagram
     */
    public abstract void goDown();


    /**
     * Returns the diagram associated with this dialog.
     */
    public Diagram getDiagram() {
        return diagram;
    }

    /**
     * Registers the diagram associated with this dialog.
     */
    private Diagram diagram;

    /**
     * Returns the default frame for this dialog window.
     *
     * @return The default frame for this dialog window.
     */
    protected abstract Rectangle getDefaultFrame();

    @Override
    public void executeCommand(Command command) throws CommandNotProcessedException {
        command.executeDialogWindow(this);
    }

    @Override
    public String getTitle() {
        return "Diagram " + getDiagram().getSequenceNumber();
    }

}