package interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.purecollections.PList;

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
     * Returns the message at the given row in this diagram.
     *
     * @param row The row where the message is located.
     * @return The message in this diagram at the given row, or null if there is none.
     */
    public Message getMessageAtRow(int row) {
        if (row > messages.size())
            return null;
        else
            return messages.get(row);
    }

    /**
     * The messages held by this callstack.
     */
    private PList<Message> messages = PList.<Message>empty();

}