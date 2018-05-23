package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.exceptions.InvalidActivateException;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.design.Box;
import interactr.cs.kuleuven.be.ui.design.Figure;
import interactr.cs.kuleuven.be.ui.design.Label;
import interactr.cs.kuleuven.be.ui.design.Model;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

import java.util.ArrayList;

/**
 * A class of dialog windows associated with invocation messages.
 *
 * @author Team 25
 * @version 1.0
 */
public class DialogInvocationMessage extends DialogWindow implements DiagramObserver {

    /**
     * Initialize this new dialog window for invocation messages with the given diagram
     *  and invocation message.
     *
     * @param diagram The diagram to associate this dialog window with.
     * @param message The invocation message to associate this dialog with.
     */
    DialogInvocationMessage(Diagram diagram, InvocationMessage message) {
        super(diagram);
        setMessage(message);
        generateModels();
        getDiagram().registerObserver(this);
    }

    /**
     * Returns the message associated with this dialog window.
     */
    private InvocationMessage getMessage() {
        return message;
    }

    /**
     * Sets the associated message for this dialog window to the given one.
     *
     * @param message The message to associate this dialog window with.
     */
    private void setMessage(InvocationMessage message) {
        this.message = message;
    }

    /**
     * Registers the invocation message associated with this dialog window.
     */
    private InvocationMessage message;

    @Override
    protected void generateModels() {
        super.generateModels();
        Box listBox = new Box(10,40,100,200);
        models.add(listBox);
        models.add(generateTextField(10,10, 100, message.getMethodName()));
        models.add(generateTextField(150, 120,100, ""));
        models.add(generateStringButton(190,150,"+"));
        models.add(generateStringButton(60,260,"-"));
        models.add(generateStringButton(120,110, "\u2191"));
        models.add(generateStringButton(120,130, "\u2193"));
    }

    /**
     * Returns a list box for the parameters of the invocation message at the given index.
     *
     * @return A list box that contains the arguments of the associated invocation message.
     */
    private Model generateListBox() {
        Figure listBox = new Box(10,40,100,200);
        String[] arguments = message.getArguments();
        int offset = 200 / PaintBoard.charHeight;
        int layer = 40;
        ArrayList<Label> argumentFields = new ArrayList<Label>();
        for(int i = 0; i < arguments.length; i++){
            Label nextArgument = this.generateTextField(10,layer,100,arguments[i]);
            argumentFields.add(nextArgument);
            layer += offset;
        }
        return listBox;
    }

    @Override
    public void activateFocus() throws InvalidActivateException {
        if (this.getFocusIndex() == 0) {
            // Add
        }
        else if (this.getFocusIndex() == 1) {
            // Delete
        }
        else if (this.getFocusIndex() == 2)
            goUp();
        else if (this.getFocusIndex() == 3)
            goDown();
    }

    @Override
    public void focus(int x, int y){
        super.focus(x,y);
    }

    @Override
    public void goUp() {
        selectedIndex = selectedIndex - 1;
        if (selectedIndex < 0)
            selectedIndex = message.getArguments().length-1;
    }

    @Override
    public void goDown() {
        selectedIndex = selectedIndex + 1;
        if (selectedIndex >= message.getArguments().length)
            selectedIndex = 0;
    }

    /**
     * Registers the argument currently entered in the argument textfield of this dialog.
     */
    private String argumentLabel = "";

    /**
     * Registers the index of the selection in the list box of this dialog.
     */
    private int selectedIndex = -1;

    @Override
    public void diagramDidDeleteMessage(Diagram diagram, Message message) {
        if (this.message == message)
            close();
    }

    @Override
    public Rectangle getFrame() {
        Rectangle frame = super.getFrame();
        return new Rectangle(frame.getX(), frame.getY(), 350, 40 + (message == null ? 0 : message.getArguments().length * 20));
    }

    @Override
    protected Rectangle getDefaultFrame() {
        return new Rectangle(0, 0, 350, 400);
    }

    @Override
    protected boolean canHaveAsFrame(Rectangle frame) {
        return true;
    }

    @Override
    public String getTitle() {
        return "Invocation Message Dialog - " + super.getTitle();
    }

    @Override
    public void close() {
        super.close();
        getDiagram().unregisterObserver(this);
    }

}