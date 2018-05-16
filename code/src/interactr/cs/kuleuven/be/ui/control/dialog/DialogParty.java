package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.control.control.RadioButton;
import interactr.cs.kuleuven.be.ui.control.control.TextField;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

/**
 * A class of dialogs for parties.
 *
 * @author Team 25
 * @version 1.0
 */
public class DialogParty extends DialogWindow {

    /**
     * Initialize this new party dialog with given party, diagram and party type.
     *
     * @param party The party to associate this dialog window with.
     * @param diagram The diagram to associate this dialog window with.
     * @param isActor True if this dialog window refers to an actor.
     */
    DialogParty(Party party, Diagram diagram, boolean isActor){
        super(diagram);
        this.party = party;
        this.isActor = isActor;
    }

    @Override
    protected Rectangle getDefaultFrame() {
        return new Rectangle(0, 0, 250, 200);
    }

    /**
     * Registers whether or not this dialog window refers to an actor.
     */
    private boolean isActor;

    @Override
    protected void displayView(PaintBoard paintBoard) {
        // TODO Set state of control inside control? Eg. state of radio button == Enum.ON | Enum.OFF?
        displayControl(paintBoard, actorButton, getFrame().getWidth() * 9/12, getFrame().getHeight()/2);
        displayControl(paintBoard, objectButton,getFrame().getWidth() * 11/12, getFrame().getHeight()/2);
    }

    /**
     * Registers the radio buttons for this party dialog.
     */
    private RadioButton actorButton = new RadioButton(), objectButton = new RadioButton();

    /**
     * Registers the text fields for this party dialog
     */
    private TextField instanceName = new TextField(), className = new TextField();

    /**
     * The party of this dialog
     */
    private Party party;

    /**
     * Initialize this new dialog with given diagram.
     *
     * @param diagram The diagram to initialize this new dialog with.
     */
    public DialogParty(Diagram diagram) {
        super(diagram);
    }

    @Override
    public String getTitle() {
        return "Party Dialog - " + super.getTitle();
    }

}