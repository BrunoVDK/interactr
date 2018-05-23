package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.DiagramObserver;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

/**
 * A class of dialogs for parties.
 *
 * @author Team 25
 * @version 1.0
 */
public class DialogParty extends DialogWindow implements DiagramObserver {

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
    }

    @Override
    protected void generateModels() {
        super.generateModels();
        models.add(generateTextField(0, 0, 100, "test"));
        models.add(generateTextField(0, 0, 100, "test"));
    }

    @Override
    protected Rectangle getDefaultFrame() {
        return new Rectangle(0, 0, 270, 150);
    }

    /**
     * Registers the party for this dialog.
     */
    private Party party;

    @Override
    public void diagramDidDeleteParty(Diagram diagram, Party party) {
        if (this.party == party)
            close();
    }

    @Override
    public void diagramDidReplaceParty(Diagram diagram, Party party, Party newParty) {
        if (this.party == party) {
            this.party = newParty;

        }
    }

    @Override
    public String getTitle() {
        return "Party Dialog - " + super.getTitle();
    }

}