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
     * Change the type of the given party in this diagram.
     *
     * @param party The party whose type is to be changed.
     */
    public void changePartyType(Party party) {
        party.setIsActor(!party.isActor());
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

}