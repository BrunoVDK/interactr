package interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.exceptions.InvalidAddMessageException;
import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;
import interactr.cs.kuleuven.be.purecollections.PList;
import interactr.cs.kuleuven.be.ui.DiagramObserver;

import java.util.*;

/**
 * A class of diagrams having parties and messages sent between these parties.
 *
 * @author Team 25
 * @version 1.0
 */
public class Diagram {

    /**
     * The number of diagrams that have been generated so far.
     */
    private static int nbOfDiagrams = 0;

    /**
     * Initialize this new diagram without any messages or parties.
     */
    public Diagram() {
        nbOfDiagrams++;
        sequenceNumber = nbOfDiagrams;
    }

    /**
     * Registers the sequence number for this diagram.
     */
    private static int sequenceNumber;

    /**
     * Returns the sequence number for this diagram.
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Add the given party to this diagram.
     *
     * @param party The party that is to be added to this diagram.
     */
    public void addParty(Party party) {
        parties = parties.plus(party);
        for (DiagramObserver observer : observers)
            observer.diagramDidAddParty(this, party);
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
        for (Message message : messages) {
            if (message.getSender() == oldParty)
                message.setSender(newParty);
            else if (message.getReceiver() == oldParty)
                message.setReceiver(newParty);
        }
        for (DiagramObserver observer : observers)
            observer.diagramDidReplaceParty(this, oldParty, newParty);
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
            for (Message message : messages) {
                if (message.getSender() == party || message.getReceiver() == party) {
                    deleteMessage(message);
                    keepOnGoing = true;
                    break;
                }
            }
        }
        parties = parties.minus(party);
        for (DiagramObserver observer : observers)
            observer.diagramDidDeleteComponent(this, party);
    }

    /**
     * Removes the given message from this diagram, as well as its dependencies.
     *
     * @param message The message that is to be removed.
     */
    public void deleteMessage(Message message) {
        int count = getNbMessagesToRemove(message); // Refactor temp variable to getter?
        if (count > 0) {
            for (int j=0 ; j<messages.size() ; j++) {
                Integer formerIndex = associatedMessageIndices.get(j);
                if (formerIndex > getMaximumIndex(message)) {
                    associatedMessageIndices.remove(j);
                    associatedMessageIndices.add(j, formerIndex - count); // Shift count upwards because of removal
                }
            }
            for (int i=0 ; i<count ; i++)
                deleteMessageAtIndex(getMinimumIndex(message));
            calculateAllPrefixes();
        }
    }

    /**
     * Sets the label of the given component to the given one.
     *
     * @param component The component whose label is to be changed.
     * @param label The new label for the component.
     * @throws InvalidLabelException If the given label is not a valid one for the given component.
     */
    public void setLabelOfComponent(DiagramComponent component, String label) throws InvalidLabelException {
        component.setLabel(label);
        for (DiagramObserver observer : observers)
            observer.diagramDidEditLabel(this, component);
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
    private PList<Party> parties = PList.empty();

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
        if (index >= 0 && index < associatedMessageIndices.size())
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
     *  This method also adds a corresponding result message.
     *
     * @param message The invocation message that is to be added.
     * @param index The index to insert this message at.
     * @throws InvalidAddMessageException The given message could not be added at the given index in this diagram.
     */
    public void insertInvocationMessageAtIndex(InvocationMessage message, int index) {
        if (!canInsertMessageAtIndex(message, index))
            throw new InvalidAddMessageException();
        for (int i=0 ; i<messages.size() ; i++) { // Shift indices
            Integer formerIndex = associatedMessageIndices.get(i);
            if (formerIndex >= index) {
                associatedMessageIndices.remove(i);
                associatedMessageIndices.add(i, formerIndex + 2); // Shift two downwards because of insert
            }
        }
        if (messages.size() == 0 || index >= messages.size())
            appendMessagesToEnd(message, new ResultMessage(message));
        else
            insertMessagesAtIndex(message, new ResultMessage(message), index);
        calculateAllPrefixes();
    }

    /**
     * Insert the given messages at the given index in the callstack.
     *  This method does not recalculate the associated message indices.
     *
     * @param invocation The invocation message that is to be inserted.
     * @param result The result message that is to be inserted.
     */
    private void insertMessagesAtIndex(InvocationMessage invocation, ResultMessage result, int index) {
        messages = messages.plus(index, result);
        messages = messages.plus(index, invocation);
        associatedMessageIndices.add(index, index);
        associatedMessageIndices.add(index, index + 1);
        associatedPrefixes.add(index, null);
        associatedPrefixes.add(index, calculatePrefix(invocation, index));
        for (DiagramObserver observer : observers)
            observer.diagramDidAddMessages(this, invocation, result, index);
    }

    /**
     * Append the given messages to the end of the callstack.
     *  This method does not recalculate the associated message indices.
     *
     * @param invocation The invocation message that is to be added.
     * @param result The result message that is to be added.
     */
    private void appendMessagesToEnd(InvocationMessage invocation, ResultMessage result) {
        int index = messages.size();
        messages = messages.plus(invocation);
        messages = messages.plus(result);
        associatedMessageIndices.add(index + 1);
        associatedMessageIndices.add(index);
        associatedPrefixes.add(null);
        associatedPrefixes.add(calculatePrefix(invocation, index));
        for (DiagramObserver observer : observers)
            observer.diagramDidAddMessages(this, invocation, result, index);
    }

    /**
     * Checks whether or not the given message can be added at the given index in this diagram .
     *  This method does not recalculate the associated message indices.
     *
     * @param message The message that is to be added.
     * @param index The index at which the message should be added.
     * @return True if and only if the sender of the given message lie at the top of the call stack
     *  at the given index.
     */
    private boolean canInsertMessageAtIndex(Message message, int index) {
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
     * @return The index of the message or -1 if no such message was found.
     */
    public int getIndexOfMessage(Message message) {
        return messages.indexOf(message);
    }

    /**
     * Returns the maximum index for the given message and its associated index.
     *
     * @param message The message whose max index is desired.
     * @return The maximum index for the given message and its associated index, or -1 if the message is not in the
     *  callstack.
     */
    private int getMaximumIndex(Message message) {
        int messageIndex = getIndexOfMessage(message);
        int associatedMessageIndex = associatedMessageIndices.get(messageIndex);
        int max = Math.max(messageIndex, associatedMessageIndex);
        return (messageIndex >= 0 ? max : -1);
    }

    /**
     * Returns the minimum index for the given message and its associated index.
     *
     * @param message The message whose max index is desired.
     * @return The maximum index for the given message and its associated index, or -1 if the message is not in the
     *  callstack.
     */
    private int getMinimumIndex(Message message) {
        int messageIndex = getIndexOfMessage(message);
        int associatedMessageIndex = associatedMessageIndices.get(messageIndex);
        int min = Math.min(messageIndex, associatedMessageIndex);
        return (messageIndex >= 0 ? min : -1);
    }

    /**
     * Returns the number of messages to remove from this diagram when removing the given one.
     *
     * @param message The message to remove.
     * @return The number of messages that have to be removed upon removal of the given one.
     */
    private int getNbMessagesToRemove(Message message) {
        if (getIndexOfMessage(message) >= 0)
            return getMaximumIndex(message) - getMaximumIndex(message) + 1;
        return 0;
    }

    /**
     * Delete the message at the given index.
     *  This method does not ensure the validity of the associated message indices, prefixes, ...
     *   after processing.
     *
     * @param index The index of the message that is to be removed.
     */
    private void deleteMessageAtIndex(int index) {
        Message message = messages.get(index);
        messages = messages.minus(index);
        associatedMessageIndices.remove(index);
        associatedPrefixes.remove(index);
        for (DiagramObserver observer : observers)
            observer.diagramDidDeleteComponent(this, message);
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
     * Returns the prefix for the given message.
     *
     * @param message The message to get the prefix for.
     * @return The prefix for the given message.
     */
    public String getPrefix(Message message) {
        int index = getIndexOfMessage(message);
        return associatedPrefixes.get(index);
    }

    /**
     * Calculates a prefix for the given message in this diagram.
     *
     * @param message The message whose prefix should be determined.
     */
    private String calculatePrefix(Message message, int index) {
        Message previousInvocation = this.getPreviousInvocation(index);
        if (previousInvocation == null)
            return "1.";
        else if (message.getSender() == previousInvocation.getSender()) {
            String previousPrefix = this.associatedPrefixes.get(this.getIndexOfMessage(previousInvocation));
            int prefixLast = Integer.parseInt(previousPrefix.substring(previousPrefix.length()-2, previousPrefix.length()-1));
            int prefixNew = prefixLast + 1;
            return previousPrefix.substring(0 , previousPrefix.length()-2) + prefixNew + ".";
        }
        else if (message.getSender() == previousInvocation.getReceiver()){
            return this.associatedPrefixes.get((this.getIndexOfMessage(previousInvocation))) + "1.";
        }
        return null;
    }

    /**
     * Calculates the prefixes of all messages in this diagram.
     */
    private void calculateAllPrefixes() {
        for (int i=0 ; i<messages.size() ; i++) {
            String prefix = calculatePrefix(messages.get(i), i);
            associatedPrefixes.remove(i);
            associatedPrefixes.add(i, prefix);
        }
    }

    /**
     * Returns the previous invocation message's index for the message at given index.
     *
     * @param index The index of the message.
     * @return The index of the previous invocation message for the given message.
     */
    private Message getPreviousInvocation(int index) {
        if (index == 0) return null;
        int associatedIndex = getIndexOfAssociatedMessage(index-1);
        return getMessageAtIndex(associatedIndex < (index-1) ? associatedIndex : (index-1));
    }

    /**
     * The messages held by this diagram.
     */
    private PList<Message> messages = PList.<Message>empty();

    /**
     * The indices of associated messages for the messages held by this diagram.
     *  Each entry in this array gives the associated message of the message at the same index
     *  in the messages list.
     */
    private ArrayList<Integer> associatedMessageIndices = new ArrayList<>();

    /**
     * The prefixes associated with the messages.
     */
    private ArrayList<String> associatedPrefixes = new ArrayList<>();

    /**
     * Registers the given diagram observer.
     *
     * @param observer The new observer that is to be registered.
     */
    public void registerObserver(DiagramObserver observer) {
        if (!observers.contains(observer))
            observers = observers.plus(observer);
    }

    /**
     * Unregisters the given diagram observer.
     *
     * @param observer The observer that is to be unregistered.
     */
    public void unregisterObserver(DiagramObserver observer) {
        observers = observers.minus(observer);
    }

    /**
     * The list of observers of this diagram.
     */
    private PList<DiagramObserver> observers = PList.empty();

}