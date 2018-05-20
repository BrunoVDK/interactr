package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.InvocationMessage;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.control.control.*;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

/**
 * A class of dialog windows associated with invocation messages.
 *
 * @author Team 25
 * @version 1.0
 */
public class DialogInvocationMessage extends DialogWindow {

    /**
     * Initialize this new dialog window for invocation messages with the given diagram
     *  and invocation message.
     *
     * @param diagram The diagram to associate this dialog window with.
     * @param message The invocation message to associate this dialog with.
     */
    DialogInvocationMessage(Diagram diagram, InvocationMessage message) {
        super(diagram);
        this.message = message;
        this.listBox = new ListBox(message.getArguments());
        this.methodName = new TextField(message.getMethodName() , "Method Name: ");
    }

    @Override
    protected void displayView(PaintBoard paintBoard) {
        methodName.display(paintBoard, getFrame().getX() + 10, getFrame().getY() + (getFrame().getHeight() * 1/12 ));
        listBox.display(paintBoard, getFrame().getX() + 10, getFrame().getY() + (getFrame().getHeight() * 2/12 ));
        buttonUp.display(paintBoard,getFrame().getX() + (getFrame().getWidth() * 12/15), getFrame().getY() + (getFrame().getHeight() * 4/12 ));
        buttonDown.display(paintBoard,getFrame().getX() + (getFrame().getWidth() * 12/15), getFrame().getY() + (getFrame().getHeight() * 8/12 ));

        buttonPlus.display(paintBoard,getFrame().getX() +  (getFrame().getWidth() * 1/3), getFrame().getY() + (getFrame().getHeight() * 10/12 ));
        buttonMinus.display(paintBoard,getFrame().getX() +  (getFrame().getWidth() * 2/3), getFrame().getY() + (getFrame().getHeight() * 10/12 ));
    }

    private TextField methodName;

    /**
     * The listbox of this dialog window
     */
    private ListBox listBox;

    /**
     * The down button of this dialog window
     */
    private ButtonDown buttonDown = new ButtonDown();
    /**
     * The up button of this dialog window
     */
    private ButtonUp buttonUp = new ButtonUp();
    /**
     * The minus button of this dialog window
     */
    private ButtonMinus buttonMinus = new ButtonMinus();
    /**
     * The plus button of this dialog window
     */
    private ButtonPlus buttonPlus = new ButtonPlus();

    /**
     * Registers the invocation message associated with this dialog window.
     */
    private InvocationMessage message;

    @Override
    protected Rectangle getDefaultFrame() {
        return new Rectangle(0, 0, 150, 300);
    }

    @Override
    public String getTitle() {
        return "Invocation Message Dialog - " + super.getTitle();
    }

}