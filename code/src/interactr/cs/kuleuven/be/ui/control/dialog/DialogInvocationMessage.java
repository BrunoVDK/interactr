package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.InvocationMessage;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.control.control.*;
import interactr.cs.kuleuven.be.ui.design.Box;
import interactr.cs.kuleuven.be.ui.design.Figure;
import interactr.cs.kuleuven.be.ui.design.Model;
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

    protected void generateModels() {

    }

    /**
     * Returns a list box for the parameters of the invocation message at the given index.
     *
     * @param index The index for the list box
     * @return A list box that contains the parameters at the given index
     */
    private Model getListBox(int index){
        return null;
    }

    @Override
    protected void displayView(PaintBoard paintBoard) {
        methodName.display(paintBoard, 10, (getFrame().getHeight() * 1/12 ));
        listBox.display(paintBoard, 10, (getFrame().getHeight() * 2/12 ));
        buttonUp.display(paintBoard,(getFrame().getWidth() * 12/15), (getFrame().getHeight() * 4/12 ));
        buttonDown.display(paintBoard,(getFrame().getWidth() * 12/15), (getFrame().getHeight() * 8/12 ));

        buttonPlus.display(paintBoard,(getFrame().getWidth() * 1/3), (getFrame().getHeight() * 10/12 ));
        buttonMinus.display(paintBoard,(getFrame().getWidth() * 2/3), (getFrame().getHeight() * 10/12 ));
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
    public void goUp() {
        listBox.goUp();
    }

    @Override
    public void goDown() {
        listBox.goDown();
    }

    @Override
    protected Rectangle getDefaultFrame() {
        return new Rectangle(0, 0, 270, 300);
    }

    @Override
    public String getTitle() {
        return "Invocation Message Dialog - " + super.getTitle();
    }

}