package interactr.cs.kuleuven.be.ui.control;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.exceptions.InvalidActivateException;
import interactr.cs.kuleuven.be.purecollections.PList;
import interactr.cs.kuleuven.be.ui.command.Command;
import interactr.cs.kuleuven.be.ui.command.CommandNotProcessedException;
import interactr.cs.kuleuven.be.ui.design.Model;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

import java.util.NoSuchElementException;

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
     * Returns the index of the element for this dialog window.
     */
    protected int getFocusIndex() {
        return focusIndex;
    }

    /**
     * Activate the focused element.
     *
     * @throws InvalidActivateException If the focused element can't be activated.
     */
    public void activateFocus() throws InvalidActivateException {
        throw new InvalidActivateException();
    }

    /**
     * Focus on the element at the given coordinates.
     *
     * @param x The x coordinate where the element lies.
     * @param y The y coordinate where the element lies.
     */
    public void focus(int x, int y) {
        x -= getFrame().getX();
        y -= getFrame().getY() + TITLE_BAR_HEIGHT;
        for (int i=0 ; i<models.size() ; i++) {
            if (models.get(i).isHit(x, y)) {
                setFocusIndex(i);
                activateFocus();
                return;
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * Sets the focused element to the given one.
     *
     * @param focusIndex The index of the element to focus on.
     */
    private void setFocusIndex(int focusIndex) {
        this.focusIndex = focusIndex;
    }

    /**
     * Focus on the next control.
     */
    public void focusNext() {setFocusIndex((getFocusIndex() + 1) % models.size());}

    /**
     * Registers the index of the element focused on by this dialog window.
     */
    private int focusIndex = 0;

    /**
     * Generate the models held by this dialog window.
     */
    protected abstract void generateModels();

    /**
     * The list of models held by this dialog.
     */
    protected PList<Model> models = PList.empty();

    /**
     * A method that is used by the Dialog Diagram
     */
    public void goUp() {throw new CommandNotProcessedException();};

    /**
     * A method that is used by the Dialog Diagram
     */
    public void goDown() {throw new CommandNotProcessedException();};

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