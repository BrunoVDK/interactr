package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.DiagramObserver;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.exceptions.InvalidActivateException;
import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.design.Circle;
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
        instanceName = party.getInstanceName();
        className = party.getClassName();
        this.isActor = isActor;

    }

    @Override
    protected void generateModels() {
        super.generateModels();
        models.add(generateTextField(0, 0, 100, instanceName));
        models.add(generateTextField(0, 0, 100, className));
        Circle actor = generateRadioButton(0,0,"Actor");
        Circle object = generateRadioButton(0,0,"Object");
        models.add(actor);
        models.add(object);
        actor.setFilled(isActor);
        object.setFilled(!isActor);
    }

    private boolean isActor;

    /**
     * The two names of the party
     */
    private String instanceName, className;

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

    @Override
    public void appendChar(char c) {
        try {
            if (getFocusIndex() == 0)
                instanceName += c;
            else if (getFocusIndex() == 1)
                className += c;

            getDiagram().setLabelOfComponent(party,instanceName + ":" + className);
        }catch (InvalidLabelException ignored){
            //nothing
        }
    }

    @Override
    public void removeLastChar() {
        try {
            if (getFocusIndex() == 0)
                instanceName = instanceName.substring(0,instanceName.length() -1);

            else if (getFocusIndex() == 1)
                className = className.substring(0,className.length() -1);

            getDiagram().setLabelOfComponent(party,instanceName + ":" + className);
        }catch (InvalidLabelException ignored){
            //nothing
        }
    }

    @Override
    public void activateFocus() throws InvalidActivateException {


    }
}