package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.InvocationMessage;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.control.control.*;
import interactr.cs.kuleuven.be.ui.design.Box;
import interactr.cs.kuleuven.be.ui.design.Figure;
import interactr.cs.kuleuven.be.ui.design.Label;
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
        this.setInvocationMessage(message);
        this.setFrame(getDefaultFrame());
        generateModels();
    }

    /**
     * Registers the invocation message associated with this dialog window.
     */
    private InvocationMessage message;

    private void setInvocationMessage(InvocationMessage message){
        this.message = message;
    }

    public InvocationMessage getInvocationMessage(){
        return message;
    }

    protected void generateModels() {
        models.clear();
        models.add(this.getListBox());
        models.add(this.getMethodNameField());
        models.add(this.getArgumentTextField());
        models.add(this.getAddButton());
        models.add(this.getDeleteButton());
    }

    /**
     * Returns a list box for the parameters of the invocation message at the given index.
     *
     * @return A list box that contains the parameters at the given index
     */
    private Model getListBox(){
        Figure listBox = new Box(10,40,100,200);
        String[] arguments = message.getArguments();
        return listBox;
    }

    /**
     * Returns a textfield that contains the method name of the invocationMessage
     *
     * @return  textfield that contains the method name of the invocationMessage
     */
    private Model getMethodNameField(){
        Label method = this.generateTextField(10,10, 100, this.getInvocationMessage().getMethodName());
        return method;
    }

    /**
     * Returns an empty textfield that can be used to add arguments to the invocationMessage
     *
     * @return an empty textfield
     */
    private Model getArgumentTextField(){
        Label argument = this .generateTextField(150, 120,100, "");
        return argument;
    }

    /**
     * Returns an add button that can be used to add the argument typed in the argument textfield to the end of the list box
     *
     * @return an add button
     */
    private Model getAddButton(){
        Box addButton = this.generateStringButton(200,150,"+");
        return addButton;
    }

    private Model getDeleteButton(){
        Box deleteButton = this.generateStringButton(60,260,"-");
        return deleteButton;
    }

    protected void displayView(PaintBoard paintBoard){
        this.generateModels();
        for (Model model : models)
            model.draw(paintBoard);
    }

    @Override
    public void display(PaintBoard paintBoard) {
        super.display(paintBoard);
    }

    /*@Override
    protected void displayView(PaintBoard paintBoard) {
        methodName.display(paintBoard, 10, (getFrame().getHeight() * 1/12 ));
        listBox.display(paintBoard, 10, (getFrame().getHeight() * 2/12 ));
        buttonUp.display(paintBoard,(getFrame().getWidth() * 12/15), (getFrame().getHeight() * 4/12 ));
        buttonDown.display(paintBoard,(getFrame().getWidth() * 12/15), (getFrame().getHeight() * 8/12 ));

        buttonPlus.display(paintBoard,(getFrame().getWidth() * 1/3), (getFrame().getHeight() * 10/12 ));
        buttonMinus.display(paintBoard,(getFrame().getWidth() * 2/3), (getFrame().getHeight() * 10/12 ));
    }*/

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