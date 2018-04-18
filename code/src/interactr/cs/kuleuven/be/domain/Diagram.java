package interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.exceptions.InvalidAddMessageException;
import interactr.cs.kuleuven.be.purecollections.PList;

import java.util.*;

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
            this.updatePrefix(message, index);
            this.updatePrefix(resultMessage);
        }
        else { // Insert
            messages = messages.plus(index, resultMessage);
            messages = messages.plus(index, message);
            associatedMessageIndices.add(index, index);
            associatedMessageIndices.add(index, index + 1);
            this.updatePrefix(message, index);
            this.updatePrefix(resultMessage);
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
     * Add a prefix to the given result message.
     *
     * @param message The message to add a prefix to.
     */
    private void updatePrefix(ResultMessage message){
        int index = this.getIndexOfAssociatedMessage(this.getIndexOfMessage(message));
        this.associatedPrefixes.add(index+1, null);
    }

    /**
     * Calculates a prefix for the given message in this diagram.
     *
     * @param message The message whose prefix should be determined.
     */
    private void updatePrefix(InvocationMessage message, int index) {
        Message prev = this.getPreviousInvocationMessage(message, index);

        if (prev == null){
            if(this.associatedPrefixes.isEmpty()) {
                this.associatedPrefixes.add(index, "1.");
            }
            else{
                this.associatedPrefixes.add(index, "1.");
            }
        }
        else if (message.getSender() == prev.getSender()) {
            String previousPrefix = this.associatedPrefixes.get(this.getIndexOfMessage(prev));
            int prefixLast = Integer.parseInt(previousPrefix.substring(previousPrefix.length()-2, previousPrefix.length()-1));
            int prefixNew = prefixLast + 1;
            String prefix = previousPrefix.substring(0 , previousPrefix.length()-2) + prefixNew + ".";
            this.associatedPrefixes.add(index, prefix);
        }
        else if (message.getSender() == prev.getReceiver()){
            String prefix = this.associatedPrefixes.get((this.getIndexOfMessage(prev))) + "1.";
            this.associatedPrefixes.add(index, prefix);
        }
    }

    private void shiftPrefix(InvocationMessage message, int index){

    }

    /**
     * Returns the previous invocation message for the given message.
     *
     * @param m The message to get the previous invocation for.
     * @param index The index of the message.
     * @return The previous invocation message for the given message.
     */
    private Message getPreviousInvocationMessage(Message m, int index){
        Message prev = null;
        for (int i=0 ; i<index ; i++)
            if (i < this.getIndexOfAssociatedMessage(i)
                    && (m.getSender() == this.getMessageAtIndex(i).getReceiver()
                        || m.getSender() == this.getMessageAtIndex(i).getSender()))
                prev = this.getMessageAtIndex(i);
        return prev;
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
    private ArrayList<Integer> associatedMessageIndices = new ArrayList<Integer>();

    /**
     * The prefixes associated with the messages.
     */
    private ArrayList<String> associatedPrefixes = new ArrayList<String>();

}