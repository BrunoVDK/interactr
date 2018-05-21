package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.control.control.RadioButton;
import interactr.cs.kuleuven.be.ui.design.Colour;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

import java.util.ArrayList;

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
        for (int i=0 ; i<diagramWindow.getNbViews() ; i++)
            buttons.add(new RadioButton(diagramWindow.getViewAt(i).toString()));
        setFrame(getDefaultFrame());
        if (buttons.size() > 0)
            setFocusedControl(buttons.get(0));
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
        int i = 0;
        for (RadioButton button : buttons) {
            button.setActive(diagramWindow.getActiveView() == diagramWindow.getViewAt(i));
            display(paintBoard, button, 10,TITLE_BAR_HEIGHT - 3 + 30 * i++);
        }
    }

    /**
     * Registers the radio buttons for this party dialog.
     */
    private ArrayList<RadioButton> buttons = new ArrayList<>();

    @Override
    public void focusNext() {
        setFocusedControl(buttons.get((buttons.indexOf(getFocusedControl()) + 1) % buttons.size()));
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