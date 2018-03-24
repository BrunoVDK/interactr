package interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.exceptions.InvalidAddMessageException;
import interactr.cs.kuleuven.be.purecollections.PList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * A class of diagrams having parties and messages sent between these parties.
 *
 * @author Team 25
 * @version 1.0
 */
public class Diagram {

    /**
     * Initialize this new diagram without any messages or parties.
     */
    public Diagram() {
        this.activeComponent = null;
    }

    /**
     * Add the given party to this diagram.
     */
    public void addParty(Party party) {
        parties = parties.plus(party);
    }

    /**
     * Replace a party with the given party.
     *
     * @param oldParty The party that is to be replaced.
     * @param newParty The party to replace it with.
     */
    public void replaceParty(Party oldParty, Party newParty) {
        parties = parties.minus(oldParty);
        parties = parties.plus(newParty);
        for (Message message : messages)
            if (message.getSender() == oldParty)
                message.setSender(newParty);
            else if (message.getReceiver() == oldParty)
                message.setReceiver(newParty);
    }

    /**
     * Deletes the given party from this diagram, as well as all its dependencies.
     *
     * @param party The party that is to be removed.
     */
    public void deleteParty(Party party) {
        boolean keepOnGoing = true;
        while (keepOnGoing) {
            keepOnGoing = false;
            for (int i=0 ; i<messages.size() ; i++) {
                Message message = messages.get(i);
                if (messages.get(i).getSender() == party || message.getReceiver() == party) {
                    deleteMessage(message);
                    keepOnGoing = true;
                    break;
                }
            }
        }
        parties = parties.minus(party);
    }

    /**
     * Returns the parties associatd with this diagram.
     *  Parties are ordered from first added to last added.
     */
    public PList<Party> getParties() {
        return parties;
    }

    /**
     * A list of parties associated with this diagram.
     */
    private PList<Party> parties = PList.<Party>empty();

    /**
     * Returns a list of all messages in this diagram.
     *
     * @return A list with all the messages in this diagram.
     */
    public PList<Message> getMessages() {
        return messages;
    }

    /**
     * Returns the number of messages in this diagram.
     *
     * @return The number of messages in this diagram.
     */
    public int getNbMessages() {
        return messages.size();
    }

    /**
     * Returns the message at the given row in this diagram.
     *
     * @param index The row where the message is located.
     * @return The message in this diagram at the given row, or null if there is none.
     */
    public Message getMessageAtIndex(int index) {
        if (index < 0 || index >= getNbMessages())
            return null;
        return messages.get(index);
    }

    /**
     * Returns the index of the message associated with the message at the given index.
     *
     * @param index The index of the message whose associated message's index is desired.
     * @return The index of the message associated with the message at the given index, or
     *  -1 if there is none.
     */
    public int getIndexOfAssociatedMessage(int index) {
        if (index < associatedMessageIndices.size())
            return associatedMessageIndices.get(index);
        return -1;
    }

    /**
     * Returns the result message for the given invocation message.
     *
     * @param message The invocation message whose result message is desired.
     * @return The result message matching the given invocation message, or null if there is none.
     */
    public ResultMessage getResultMessageForInvocationMessage(InvocationMessage message) {
        int index = getIndexOfMessage(message);
        if (index < 0)
            return null;
        return (ResultMessage)messages.get(associatedMessageIndices.get(index));
    }

    /**
     * Insert the given invocation message at the given index in this diagram.
     *
     * @param message The invocation message that is to be added.
     * @param index The index to insert this message at.
     * @throws InvalidAddMessageException The given message could not be added at the given index in this diagram.
     */
    public void insertInvocationMessageAtIndex(InvocationMessage message, int index) {

        // Only insert if it is a valid message to insert
        if (!canAddMessageAtIndex(message, index))
            throw new InvalidAddMessageException();

        // Always insert a corresponding result message
        ResultMessage resultMessage = new ResultMessage(message);

        // First shift all the indices
        for (int i=0 ; i<messages.size() ; i++) {
            Integer formerIndex = associatedMessageIndices.get(i);
            if (formerIndex >= index) {
                associatedMessageIndices.remove(i);
                associatedMessageIndices.add(i, formerIndex + 2); // Shift two downwards because of insert
            }
        }

        // Insert the message
        if (messages.size() == 0 || index >= messages.size()) { // Append to end
            index = messages.size();
            messages = messages.plus(message);
            messages = messages.plus(resultMessage);
            associatedMessageIndices.add(index + 1);
            associatedMessageIndices.add(index);
        }
        else { // Insert
            messages = messages.plus(index, resultMessage);
            messages = messages.plus(index, message);
            associatedMessageIndices.add(index, index);
            associatedMessageIndices.add(index, index + 1);
        }
    }

    /**
     * Checks whether or not the given message can be added at the given index in this diagram .
     *
     * @param message The message that is to be added.
     * @param index The index at which the message should be added.
     * @return True if and only if the sender of the given message lie at the top of the call stack
     *  at the given index.
     */
    private boolean canAddMessageAtIndex(Message message, int index) {
        if (messages.size() == 0)
            return true;
        if (index >= messages.size())
            return messages.get(messages.size() - 1).getReceiver() == message.getSender();
        if (index == 0)
            return (message.getSender() == messages.getFirst().getSender());
        return (messages.get(index).getSender() == message.getSender());
    }

    /**
     * Returns the index of the given message for this diagram.
     *
     * @param message The message whose index is desired.
     * @return The index of the message.
     */
    public int getIndexOfMessage(Message message) {
        return messages.indexOf(message);
    }

    /**
     * Removes the given message from this diagram, as well as its dependencies.
     *
     * @param message The message that is to be removed.
     */
    public void deleteMessage(Message message) {

        // Pre-processing
        int messageIndex = getIndexOfMessage(message);
        if (messageIndex < 0)
            return; // Message not in stack
        int associatedMessageIndex = associatedMessageIndices.get(messageIndex);
        int min = Math.min(messageIndex, associatedMessageIndex), max = Math.max(messageIndex, associatedMessageIndex);
        int count = max - min + 1; // Number of messages to remove
        int i = 0; // Current index of message to be removed

        // First update associated message indices
        for (int j=0 ; j<messages.size() ; j++) {
            Integer formerIndex = associatedMessageIndices.get(j);
            if (formerIndex > max) {
                associatedMessageIndices.remove(j);
                associatedMessageIndices.add(j, formerIndex - count); // Shift count upwards because of removal
            }
        }

        // Now remove the messages
        while(i < count) {
            messages = messages.minus(min);
            associatedMessageIndices.remove(min);
            i++;
        }

    }

    /**
     * Returns the initiator for this diagram.
     *
     * @return The first sending party in this diagram's call stack.
     */
    public Party getInitiator() {
        if (getNbMessages() > 0)
            return messages.getFirst().getSender();
        return null;
    }

    /**
     * Calculates a prefix for the given message in this diagram.
     *
     * @param message The message whose prefix should be determined.
     * @return A prefix for the given message.
     */
    public String getPrefix(Message message) {
        int index = getIndexOfMessage(message);
        if (index == -1 || getIndexOfAssociatedMessage(index) < index)
            return "";
        ArrayList<Integer> sequence = new ArrayList<Integer>();
        Party initiator = getInitiator();
        
        return "X.X";
    }

    /**
     * The messages held by this diagram.
     */
    private PList<Message> messages = PList.<Message>empty();

    /**
     * The indices of associated messages for the messages held by this diagram.
     *
     * @note Each entry in this array gives the associated message of the message at the same index
     *  in the messages list.
     */
    private ArrayList<Integer> associatedMessageIndices = new ArrayList<Integer>();

    /**
     * List registering selected diagramcomponents.
     */
    private PList<DiagramComponent> selection = PList.<DiagramComponent>empty();

    /**
     * Returns the list of selected components.
     */
    public PList<DiagramComponent> getSelectedComponents(){
        return this.selection;
    }

    /**
     * Adds the given component to the selected components in this diagram.
     *  If the given component is already selected, it is activated.
     *
     * @param component The component that is to be selected.
     */
    public void addToSelection(DiagramComponent component) {
        if (isSelected(component)) {
            setActiveComponent(component);
            setTemporaryLabel("");
        }
        else if (component != null)
            selection = selection.plus(component);
    }

    /**
     * Returns whether or not the given diagram component is currently selected.
     *
     * @param component The component to check for.
     * @return True if and only if the given diagram component is currently selected.
     */
    public boolean isSelected(DiagramComponent component) {
        return selection.contains(component);
    }

    /**
     * Registers the active component in this diagram.
     */
    private DiagramComponent activeComponent;

    /**
     * Returns the diagramcomponent that is currently active in this diagram.
     */
    public DiagramComponent getActiveComponent() {
        return activeComponent;
    }

    /**
     * Sets a diagramcomponent in this diagram as active component.
     * @param component the diagramcomponent that needs to be set as active component.
     */
    public void setActiveComponent(DiagramComponent component) {
        unselectAll();
        setTemporaryLabel("");
        this.activeComponent = component;
    }

    /**
     * Unselects the currently active component(s).
     */
    public void unselectAll(){
        this.activeComponent = null;
    }

    /**
     * Sets the temporary label for the active component to the given one.
     *
     * @param label The new temporary label for the active component.
     * @throws IllegalArgumentException If the given label is null.
     */
    public void setTemporaryLabel(String label){
        if(label == null){
            throw new IllegalArgumentException("Invalid temporary label (null).");
        }
        this.temporaryLabel = label;
    }

    /**
     * Returns the temporary label of the currently active component in this diagram.
     */
    public String getTemporaryLabel(){
        return temporaryLabel;
    }

    /**
     * The temporary label for the active component of this diagram.
     */
    private String temporaryLabel = "";
}