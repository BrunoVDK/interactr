package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.exceptions.InvalidActivateException;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.design.Circle;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

/**
 * A class of dialog windows associated with diagram views.
 *
 * @author Team 25
 * @version 1.0
 */
public class DialogDiagram extends DialogWindow {

    /**
     * Initialize this new dialog window for diagrams with the given diagram view.
     *
     * @param diagramWindow The diagram window to associate this dialog window with.
     */
    DialogDiagram(DiagramWindow diagramWindow) {
        super(diagramWindow.getDiagram());
        this.diagramWindow = diagramWindow;
        setFrame(getDefaultFrame());
        generateModels();
    }

    @Override
    protected void generateModels() {
        super.generateModels();
        for (int i=0 ; i<diagramWindow.getNbViews() ; i++) {
            String label = diagramWindow.getViewAt(i).toString();
            Circle radioButton = generateRadioButton(30, 15 + 20*i, label);
            if (diagramWindow.getActiveViewIndex() == i)
                radioButton.setFilled(true);
            models.add(radioButton);
        }
    }

    @Override
    public void activateFocus() throws InvalidActivateException {
        diagramWindow.activateViewAtIndex(getFocusIndex());
    }

    /**
     * Registers the diagram view associated with this dialog window.
     */
    private DiagramWindow diagramWindow;

    @Override
    public void display(PaintBoard paintBoard) {
        if (diagramWindow.isClosed())
            close();
        else
            super.display(paintBoard);
    }

    @Override
    protected Rectangle getDefaultFrame() {
        return new Rectangle(0, 0, 250, getDefaultHeight());
    }

    /**
     * Returns the default height for this diagram dialog.
     *
     * @return The height of the title bar plus 30 times the number of views in the associated diagram window.
     */
    private int getDefaultHeight() {
        if (diagramWindow == null)
            return TITLE_BAR_HEIGHT;
        return TITLE_BAR_HEIGHT + 30 * diagramWindow.getNbViews();
    }

    @Override
    protected boolean canHaveAsFrame(Rectangle frame) {
        return (super.canHaveAsFrame(frame) && (diagramWindow == null || frame.getHeight() == getDefaultHeight()));
    }

    @Override
    public String getTitle() {
        return "Diagram Dialog - " + super.getTitle();
    }

}