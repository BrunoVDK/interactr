package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.exceptions.InvalidActivateException;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.command.CommandNotProcessedException;
import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.design.Circle;
import interactr.cs.kuleuven.be.ui.design.Colour;
import interactr.cs.kuleuven.be.ui.design.Label;
import interactr.cs.kuleuven.be.ui.design.Model;
import interactr.cs.kuleuven.be.ui.geometry.Point;
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
        models = models.minusAll(models);
        for (int i=0 ; i<diagramWindow.getNbViews() ; i++)
            models = models.plus(getRadioButton(i));
        setFocus(models.get(diagramWindow.getActiveViewIndex()));
    }

    /**
     * Returns a radio button for the diagram view at the given index.
     *
     * @param viewIndex The index for the diagram view.
     * @return A radio button for selecting that diagram view.
     */
    private Model getRadioButton(int viewIndex) {
        Circle radioButton = new Circle(30, 30*viewIndex, PaintBoard.charHeight);
        String label = diagramWindow.getViewAt(viewIndex).toString();
        radioButton.add(new Label((getFrame().getWidth() - 50 - PaintBoard.charWidth * label.length())/2, 0, label));
        radioButton.setCoordinates(new Point(30, 15 + 20*viewIndex));
        if (diagramWindow.getActiveViewIndex() == viewIndex)
            radioButton.setFilled(true);
        return radioButton;
    }

    @Override
    public void activateFocus() throws InvalidActivateException {
        if (getFocus() != null)
            diagramWindow.activateViewAtIndex(models.indexOf(getFocus()));
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
    protected void displayView(PaintBoard paintBoard) {
        paintBoard.setColour(Colour.BLACK);
        generateModels();
        for (Model model : models)
            model.draw(paintBoard);
    }

    @Override
    public void focusNext() {
        setFocus(models.get((models.indexOf(getFocus()) + 1) % models.size()));
    }

    @Override
    public void goUp() {
        throw new CommandNotProcessedException();
    }

    @Override
    public void goDown() {
        throw new CommandNotProcessedException();
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