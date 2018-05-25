package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.ResultMessage;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
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
        models.add(generateTextField(20,5, getFrame().getWidth() - 40, message.getLabel() + "|"));
    }

    @Override
    public void display(PaintBoard paintBoard) {
        if (!getDiagram().hasComponent(this.message))
            close();
        else
            super.display(paintBoard);
    }

    /**
     * Registers the invocation message associated with this dialog window.
     */
    private ResultMessage message;

    @Override
    protected boolean canHaveAsFrame(Rectangle frame) {
        return (message == null || (frame.getWidth() >= 350 && frame.getHeight() == 50));
    }

    @Override
    protected Rectangle getDefaultFrame() {
        return new Rectangle(0, 0, 350, 50);
    }

    @Override
    public String getTitle() {
        return "Result Message Dialog - " + super.getTitle();
    }

    @Override
    public void appendChar(char c){
        getDiagram().setLabelOfComponent(message, message.getLabel() + c);
    }

    @Override
    public void removeLastChar() {
        getDiagram().setLabelOfComponent(message, message.getLabel().
                substring(0, message.getLabel().length() -1) );
    }

}