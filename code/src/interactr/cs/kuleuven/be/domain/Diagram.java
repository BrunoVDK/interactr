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
            ; // Replace all
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
     * Returns the message at the given row in this diagram.
     *
     * @param index The row where the message is located.
     * @return The message in this diagram at the given row, or null if there is none.
     */
    public Message getMessageAtIndex(int index) {
        return messages.get(index);
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
        if (!canAddMessageAtIndex(message, index))
            throw new InvalidAddMessageException();
        ResultMessage resultMessage = new ResultMessage(message);
        if (messages.size() == 0 || index >= messages.size()) {
            index = messages.size();
            messages = messages.plus(message);
            messages = messages.plus(resultMessage);
            associatedMessageIndices.add(index + 1);
            associatedMessageIndices.add(index);
        }
        else {
            messages = messages.plus(index, resultMessage);
            messages = messages.plus(index, message);
            associatedMessageIndices.add(index, index);
            associatedMessageIndices.add(index, index-1);
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
            return (message.getSender() == messages.getFirst().getReceiver());
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
        int messageIndex = getIndexOfMessage(message);
        int associatedMessageIndex = associatedMessageIndices.get(messageIndex);
        int min = Math.min(messageIndex, associatedMessageIndex), max = Math.max(messageIndex, associatedMessageIndex);
        int count = max - min + 1; // Number of messages to remove
        int i = 0; // Current number of message removed
        while(i < count) {
            messages = messages.minus(min);
            i++;
        }
        for (i=min ; i<messages.size() ; i++) {
            Integer formerIndex = associatedMessageIndices.remove(i);
            associatedMessageIndices.add(i,  formerIndex - count);
        }
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

}