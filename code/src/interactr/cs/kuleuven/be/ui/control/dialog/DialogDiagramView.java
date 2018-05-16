package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

/**
 * A class of dialog windows associated with diagram views.
 *
 * @author Team 25
 * @version 1.0
 */
public class DialogDiagramView extends DialogWindow {

    /**
     * Initialize this new dialog window for diagrams with the given diagram view.
     *
     * @param diagramView The diagram view to associate this dialog window with.
     */
    DialogDiagramView(DiagramView diagramView) {
        super(diagramView.getDiagram());
        this.diagramView = diagramView;
    }

    /**
     * Registers the diagram view associated with this dialog window.
     */
    private DiagramView diagramView;

    @Override
    protected void displayView(PaintBoard paintBoard) {

    }

    @Override
    protected Rectangle getDefaultFrame() {
        return null;
    }

    @Override
    public String getTitle() {
        return "Diagram Dialog - " + super.getTitle();
    }

}