package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.exceptions.InvalidActivateException;
import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.design.Box;
import interactr.cs.kuleuven.be.ui.design.Colour;
import interactr.cs.kuleuven.be.ui.design.Label;
import interactr.cs.kuleuven.be.ui.design.Model;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

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
        setFocusIndex(1);
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
        if (message.getArguments().length <= selectedIndex)
            selectedIndex = -1;
        this.message = message;
        this.methodNameLabel = message.getMethodName();
    }

    /**
     * Registers the invocation message associated with this dialog window.
     */
    private InvocationMessage message;

    @Override
    protected void generateModels() {
        super.generateModels();
        Box listBox = generateListBox();
        models.add(listBox);
        models.add(generateTextField(11,6, 270, methodNameLabel + (getFocusIndex() == 1 ? "|" : "")));
        models.add(generateStringButton(290,5,"+"));
        models.add(generateStringButton(320,5,"-"));
        models.add(generateTextField(11, 36,270, argumentLabel + (getFocusIndex() == 4 ? "|" : "")));
        models.add(generateStringButton(290,35, "\u2193"));
        models.add(generateStringButton(320,35, "\u2191"));
    }

    /**
     * Returns a list box for the parameters of the invocation message at the given index.
     *
     * @return A list box that contains the arguments of the associated invocation message.
     */
    private Box generateListBox() {
        Box listBox = new Box(10,63,getFrame().getWidth() - 20,getFrame().getHeight() - 100);
        String[] arguments = message.getArguments();
        for (int i = 0; i < arguments.length; i++) {
            Label argumentLabel = new Label(5, 3 + LIST_BOX_ROW_HEIGHT * i, arguments[i]);
            if (i == selectedIndex)
                argumentLabel.setColour(Colour.BLUE);
            listBox.add(argumentLabel);
        }
        return listBox;
    }

    @Override
    protected boolean canFocus(int index) {
        return (index != 0);
    }

    @Override
    public void activateFocus() throws InvalidActivateException {
        if (this.getFocusIndex() == 2)
            addArgument();
        else if (this.getFocusIndex() == 3)
            deleteSelectedArgument();
        else if (this.getFocusIndex() == 5)
            goDown();
        else if (this.getFocusIndex() == 6)
            goUp();
    }

    @Override
    public void focus(int x, int y){
        int i=0;
        for (Model child : generateListBox().getChildren()) {
            if (child.isHit(x - getFrame().getX(), y - getFrame().getY() - TITLE_BAR_HEIGHT)) {
                selectedIndex = i;
                break;
            }
            i++;
        }
        super.focus(x,y);
    }

    /**
     * Adds the argument entered in the argument field to the parameter list.
     */
    private void addArgument() {
        if (!getMessage().canHaveAsArgument(argumentLabel))
            return;
        String[] arguments = getMessage().getArguments();
        String[] newArguments = new String[arguments.length + 1];
        for (int i=0 ; i<arguments.length ; i++)
            newArguments[i] = arguments[i];
        newArguments[arguments.length] = argumentLabel;
        getDiagram().setLabelOfComponent(getMessage(), getActiveLabel(newArguments));
        selectedIndex = arguments.length;
    }

    /**
     * Deletes the selected argument from the associated message's argument list.
     */
    private void deleteSelectedArgument() {
        if (!getMessage().canHaveAsArgument(argumentLabel))
            return;
        String[] arguments = getMessage().getArguments();
        String[] newArguments = new String[arguments.length - 1];
        for (int i=0 ; i<arguments.length-1 ; i++)
            newArguments[i] = arguments[i + (i >= selectedIndex ? 1 : 0)];
        getDiagram().setLabelOfComponent(getMessage(), getActiveLabel(newArguments));
        selectedIndex = -1;
    }

    @Override
    public void goUp() {
        if (selectedIndex > 0) {
            String[] arguments = getMessage().getArguments();
            String temp = arguments[selectedIndex-1];
            arguments[selectedIndex-1] = arguments[selectedIndex];
            arguments[selectedIndex] = temp;
            selectedIndex--;
            getDiagram().setLabelOfComponent(getMessage(), getActiveLabel(arguments));
        }
    }

    @Override
    public void goDown() {
        String[] arguments = getMessage().getArguments();
        if (selectedIndex < arguments.length-1) {
            String temp = arguments[selectedIndex+1];
            arguments[selectedIndex+1] = arguments[selectedIndex];
            arguments[selectedIndex] = temp;
            getDiagram().setLabelOfComponent(getMessage(), getActiveLabel(arguments));
            selectedIndex++;
        }
    }

    @Override
    public void appendChar(char c) {
        try {
            if (getFocusIndex() == 1)
                methodNameLabel += c;
            else if (getFocusIndex() == 4)
                argumentLabel += c;
            getDiagram().setLabelOfComponent(getMessage(),getActiveLabel());
        } catch (InvalidLabelException ignored) {}
    }

    @Override
    public void removeLastChar() {
        try {
            if (getFocusIndex() == 1)
                methodNameLabel = methodNameLabel.substring(0, methodNameLabel.length() - 1);
            else if (getFocusIndex() == 4)
                argumentLabel = argumentLabel.substring(0, argumentLabel.length() - 1);
            getDiagram().setLabelOfComponent(getMessage(),getActiveLabel());
        } catch (InvalidLabelException ignored) {}
    }

    /**
     * Returns the active label for this dialog.
     *
     * @return The label destined for the associated message.
     */
    private String getActiveLabel() {
        return getActiveLabel(getMessage().getArguments());
    }

    /**
     * Returns the active label for this dialog, assuming the given argument list.
     *
     * @param arguments The argument list for the active label.
     * @return The active label, having given argument list.
     */
    private String getActiveLabel(String[] arguments) {
        String label = methodNameLabel + "(";
        for (int i=0 ; i<arguments.length ; i++)
            label += arguments[i] + ",";
        if (arguments.length > 0)
            label = label.substring(0, label.length() - 1);
        return label + ")";
    }

    /**
     * Registers the argument currently entered in the argument textfield of this dialog.
     */
    private String argumentLabel = "", methodNameLabel = "";

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
    public void diagramDidEditLabel(Diagram diagram, DiagramComponent component) {
        if (this.message == component)
            setMessage(message);
    }

    @Override
    public Rectangle getFrame() {
        Rectangle frame = super.getFrame();
        return new Rectangle(frame.getX(), frame.getY(), 350, 100 + (message == null ? 0 : message.getArguments().length * LIST_BOX_ROW_HEIGHT));
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

    /**
     * Registers the row height for the list box.
     */
    private static int LIST_BOX_ROW_HEIGHT = 20;

}