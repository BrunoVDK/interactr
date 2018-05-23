package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.ResultMessage;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.design.Colour;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

/**
 * A class of dialog windows associated with result messages.
 *
 * @author Team 25
 * @version 1.0
 */
public class DialogResultMessage extends DialogWindow {

    /**
     * Initialize this new dialog window for result messages with the given diagram
     *  and result message.
     *
     * @param diagram The diagram to associate this dialog window with.
     * @param message The result message to associate this dialog with.
     */
    DialogResultMessage(Diagram diagram, ResultMessage message) {
        super(diagram);
        this.message = message;
    }

    @Override
    protected void generateModels() {
        super.generateModels();
        models.add(generateTextField(0,0, getFrame().getWidth() - 30, message.getLabel()));
    }

    @Override
    public void display(PaintBoard paintBoard) {
        if (!getDiagram().hasComponent(this.message))
            close();
        paintBoard.setColour(Colour.BLUE);
    }

    /**
     * Registers the invocation message associated with this dialog window.
     */
    private ResultMessage message;

    @Override
    protected Rectangle getDefaultFrame() {
        return new Rectangle(0, 0, 270, 150);
    }

    @Override
    public String getTitle() {
        return "Result Message Dialog - " + super.getTitle();
    }

}