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
        this.setFrame(new Rectangle(0,0, width, height));
    }

    /**
     * Registers the default width and height of this dialog window.
     */
    private static final int width = 250, height = 200;

    /**
     * Registers whether or not this dialog window refers to an actor.
     */
    private boolean isActor;

    @Override
    protected void displayView(PaintBoard paintBoard) {
        /*
        if (isActor) {
            actorButton.displayControl(paintBoard, width * 9/12, height/2, true);
            objectButton.displayControl(paintBoard, width * 11/12, height/2, false);
        }
        else {
            actorButton.displayControl(paintBoard, width * 9/12, height/2, false);
            objectButton.displayControl(paintBoard, width * 11/12, height/2, true);
        }
        */
    }

    private RadioButton actorButton, objectButton;

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