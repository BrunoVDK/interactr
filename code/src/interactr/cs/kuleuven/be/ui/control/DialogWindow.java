package interactr.cs.kuleuven.be.ui.control;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.ui.command.Command;
import interactr.cs.kuleuven.be.ui.command.CommandNotProcessedException;
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
        this.setFrame(getDefaultFrame());
    }

    @Override
    protected boolean canHaveAsFrame(Rectangle frame) {
        if (!super.canHaveAsFrame(frame))
            return false;
        return (frame.getHeight() >= getDefaultFrame().getHeight() && frame.getWidth() >= getDefaultFrame().getWidth());
    }

    /**
     * Returns the default frame for this dialog window.
     *
     * @return The default frame for this dialog window.
     */
    protected abstract Rectangle getDefaultFrame();

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

    @Override
    public void executeCommand(Command command) throws CommandNotProcessedException {
        command.executeDialogWindow(this);
    }

    @Override
    public String getTitle() {
        return "Diagram " + getDiagram().getSequenceNumber();
    }

}