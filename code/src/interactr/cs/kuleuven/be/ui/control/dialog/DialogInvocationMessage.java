package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.InvocationMessage;
import interactr.cs.kuleuven.be.exceptions.InvalidActivateException;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.control.control.*;
import interactr.cs.kuleuven.be.ui.design.Box;
import interactr.cs.kuleuven.be.ui.design.Figure;
import interactr.cs.kuleuven.be.ui.design.Label;
import interactr.cs.kuleuven.be.ui.design.Model;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
        models.add(this.getAddButton());
        models.add(this.getDeleteButton());
        models.add(this.getUpButton());
        models.add(this.getDownButton());
        models.add(this.getMethodNameField());
        models.add(this.getArgumentTextField());
        models.add(this.getListBox());
        ArrayList<Label> argumentFields = this.getArgumentFields();
        for(Model model : argumentFields){
            models.add(model);
        }
    }

    /**
     * Returns a list box for the parameters of the invocation message at the given index.
     *
     * @return A list box that contains the parameters at the given index
     */
    private Model getListBox(){
        Figure listBox = new Box(10,40,100,200);
        return listBox;
    }

    private ArrayList<Label> getArgumentFields(){
        String[] arguments = message.getArguments();
        int offset = 200 / PaintBoard.charHeight;
        int layer = 40;
        ArrayList<Label> argumentFields = new ArrayList<Label>();
        for(int i = 0; i < arguments.length; i++){
            Label nextArgument = this.generateTextField(10,layer,100,arguments[i]);
            argumentFields.add(nextArgument);
            layer += offset;
        }
        return argumentFields;
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
        Box addButton = this.generateStringButton(190,150,"+");
        return addButton;
    }

    private Model getDeleteButton(){
        Box deleteButton = this.generateStringButton(60,260,"-");
        return deleteButton;
    }

    private Model getUpButton(){
        Box upButton = this.generateStringButton(120,110, "\u2191");
        return upButton;
    }

    private Model getDownButton(){
        Box downButton = this.generateStringButton(120,130, "\u2193");
        return downButton;
    }

    protected void displayView(PaintBoard paintBoard){
        this.generateModels();
        for (Model model : models)
            model.draw(paintBoard);
    }

    @Override
    public void activateFocus() throws InvalidActivateException {
        if(this.getFocusIndex() == 0){
            //addButton
        }
        else if(this.getFocusIndex() == 1){
            //deleteButton
        }
        else if(this.getFocusIndex() == 2){
            //upButton

        }
        else if(this.getFocusIndex() == 3){
            //downButton
        }
    }

    @Override
    public void focus(int x, int y){

    }


    @Override
    public void display(PaintBoard paintBoard) {
        super.display(paintBoard);
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