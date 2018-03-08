package interactr.cs.kuleuven.be.domain;

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
        // TODO
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
     * Returns the index of the given message for this diagram.
     *
     * @param message The message whose index is desired.
     * @return The index of the message.
     */
    public int getIndexOfMessage(Message message) {
        return messages.indexOf(message);
    }

    /**
     * Returns the index of the return message for this diagram.
     *
     * @param message The message for which the index of its return message is desired.
     * @return The index of the return message associated with the given message.
     */
    public int getIndexOfReturnMessage(Message message) {
        int idx = getIndexOfMessage(message);
        return 0;
    }

    /**
     * Removes the given message from this diagram, as well as its dependencies.
     *
     * @param message The message that is to be removed.
     */
    public void deleteMessage(Message message) {
        // TODO
    }

    /**
     * The messages held by this diagram.
     */
    private PList<Message> messages = PList.<Message>empty();

    /**
     * Registers the indices
     */
    private ArrayList<Integer> associatedMessageIndices = new ArrayList<Integer>();

}