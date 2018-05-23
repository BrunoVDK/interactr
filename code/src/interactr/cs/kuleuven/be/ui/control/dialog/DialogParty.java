package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.DiagramComponent;
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
        setParty(party);
        this.isActor = isActor;
        getDiagram().registerObserver(this);
        generateModels();
    }

    @Override
    protected void generateModels() {
        super.generateModels();
        models.add(generateTextField(30, 10, getFrame().getWidth() - 60, instanceName + (getFocusIndex() == 0 ? "|" : "")));
        models.add(generateTextField(30, 40, getFrame().getWidth() - 60, className+ (getFocusIndex() == 1 ? "|" : "")));
        Circle actor = generateRadioButton(35,70,"Actor");
        Circle object = generateRadioButton(35,100,"Object");
        actor.setFilled(isActor);
        object.setFilled(!isActor);
        models.add(actor);
        models.add(object);
    }

    /**
     * Registers whether or not this dialog is associated with a party or actor.
     */
    private boolean isActor;

    /**
     * The two names of the party
     */
    private String instanceName, className;

    @Override
    protected Rectangle getDefaultFrame() {
        return new Rectangle(0, 0, 270, 150);
    }

    @Override
    protected boolean canHaveAsFrame(Rectangle frame) {
        return (party == null || (frame.getWidth() >= 200 && frame.getHeight() == 150));
    }

    /**
     * Sets the party associated with this dialog to the given one.
     *
     * @param party The new party associated with this dialog.
     */
    public void setParty(Party party) {
        this.party = party;
        this.instanceName = party.getInstanceName();
        this.className = party.getClassName();
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
            setParty(newParty);
            isActor = !isActor;
        }
    }

    @Override
    public void diagramDidEditLabel(Diagram diagram, DiagramComponent component) {
        if (this.party == component)
            setParty(this.party);
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
        } catch (InvalidLabelException ignored) {}
    }

    @Override
    public void removeLastChar() {
        try {
            if (getFocusIndex() == 0)
                instanceName = instanceName.substring(0,instanceName.length() - 1);
            else if (getFocusIndex() == 1)
                className = className.substring(0,className.length() - 1);
            getDiagram().setLabelOfComponent(party,instanceName + ":" + className);
        } catch (InvalidLabelException ignored) {}
    }

    @Override
    public void activateFocus() throws InvalidActivateException {
        if (getFocusIndex() == (isActor ? 3 : 2))
            getDiagram().replaceParty(party, party.switchType());
    }

    @Override
    public void close() {
        super.close();
        getDiagram().unregisterObserver(this);
    }

}