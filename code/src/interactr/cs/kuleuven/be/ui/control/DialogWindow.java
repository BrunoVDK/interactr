package interactr.cs.kuleuven.be.ui.control;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.ui.PaintBoard;

/**
 * A class of dialog windows.
 *
 * @author Team 25
 * @version 1.0
 */
public class DialogWindow extends SubWindow {

    /**
     * Initialize this new dialog with given diagram.
     *
     * @param diagram The diagram to initialize this new dialog with.
     */
    public DialogWindow(Diagram diagram) {
        if (diagram == null)
            throw new IllegalArgumentException("Diagram cannot be null.");
        this.diagram = diagram;
    }

    @Override
    protected void displayView(PaintBoard paintBoard) {
        // TODO
    }

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
    public String getTitle() {
        return ""; // TODO
    }

}