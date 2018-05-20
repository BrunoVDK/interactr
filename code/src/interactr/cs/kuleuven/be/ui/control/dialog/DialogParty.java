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
     * Intialize this new party dialog with given party, diagram and actor flag.
     *
     * @param party The party to associate this new dialog with.
     * @param diagram The diagram to associate this new dialog with.
     * @param isActor A flag denoting whether or not the given party is an actor.
     */
    DialogParty(Party party, Diagram diagram, boolean isActor){
        super(diagram);
        this.party = party;
        if(isActor)
            actorButton.setActive(true);
        else
            objectButton.setActive(true);
        this.instanceName = new TextField(party.getInstanceName(), "Instance name: ");
        this.className = new TextField(party.getClassName(), "Class name: ");
    }

    @Override
    protected Rectangle getDefaultFrame() {
        return new Rectangle(0, 0, 270, 200);
    }

    @Override
    protected void displayView(PaintBoard paintBoard) {
        actorButton.display(paintBoard, 10, getFrame().getHeight() * 1/5);
        objectButton.display(paintBoard, 10 , getFrame().getHeight() * 2/5);
        instanceName.display(paintBoard,10   , getFrame().getHeight() * 3/5 );
        className.display(paintBoard, 10, getFrame().getHeight() * 4/5);

    }

    /**
     * Registers the radio buttons for this party dialog.
     */
    private RadioButton actorButton = new RadioButton("Actor: "), objectButton = new RadioButton("Object: ");

    /**
     * Registers the text fields for this party dialog
     */
    private TextField instanceName, className;

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