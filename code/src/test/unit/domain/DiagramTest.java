package test.unit.domain;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.InvocationMessage;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.exceptions.InvalidAddMessageException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiagramTest {

    @Test
    void replacePartyTest() {
        Diagram diagram = new Diagram();
        Party party = Party.createParty();
        Party party2 = Party.createParty();
        diagram.addParty(party);
        assertEquals(diagram.getParties().size(), 1);
        diagram.addParty(party2);
        assertEquals(diagram.getParties().size(), 2);
        diagram.insertInvocationMessageAtIndex(new InvocationMessage(party, party2), 0);
        Party newParty = Party.createParty();
        diagram.replaceParty(party, newParty);
        diagram.deleteParty(party2);
        assertEquals(diagram.getParties().size(), 1);
        assertEquals(diagram.getParties().get(0), newParty);

    }

    @Test
    void deletePartyTest() {
        Diagram diagram = new Diagram();
        Party party = Party.createParty();
        Party party2 = Party.createParty();
        diagram.addParty(party);
        assertEquals(diagram.getParties().size(), 1);
        diagram.addParty(party2);
        assertEquals(diagram.getParties().size(), 2);
        diagram.insertInvocationMessageAtIndex(new InvocationMessage(party, party2), 0);
        diagram.deleteParty(party);
        assertEquals(diagram.getParties().size(), 1);
        diagram.deleteParty(party2);
        assertEquals(diagram.getParties().size(), 0);
    }

    @Test
    void invalidGetMessageAtIndexTest() {
        Diagram diagram = new Diagram();
        assertNull(diagram.getMessageAtIndex(-1));
    }

    @Test
    void invalidGetIndexOfAssociatedMessageTest() {
        Diagram diagram = new Diagram();
        assertEquals(diagram.getIndexOfAssociatedMessage(-1), -1);
    }

    @Test
    void invalidGetResultMessageTest() {
        Diagram diagram = new Diagram();
        assertNull(diagram.getResultMessageForInvocationMessage(null));
    }

    @Test
    void addMessageTest() {
        Diagram diagram = new Diagram();
        InvocationMessage message = new InvocationMessage(Party.createParty(), Party.createParty());
        diagram.insertInvocationMessageAtIndex(message,0);
        assertTrue(diagram.getMessages().size() == 2);
        InvocationMessage message2 = new InvocationMessage(Party.createParty(), Party.createParty());
        assertThrows(InvalidAddMessageException.class,
                ()->{diagram.insertInvocationMessageAtIndex(message2,0);});
        assertThrows(InvalidAddMessageException.class,
                ()->{diagram.insertInvocationMessageAtIndex(message2,4);});
        assertTrue(diagram.getMessages().size() == 2);
        InvocationMessage message3 = new InvocationMessage(message.getSender(), message.getReceiver());
        diagram.insertInvocationMessageAtIndex(message3, 4);
        assertTrue(diagram.getMessages().size() == 4);
    }

    @Test
    void deleteMessageTest() {
        Diagram diagram = new Diagram();
        InvocationMessage message = new InvocationMessage(Party.createParty(), Party.createParty());
        InvocationMessage fake = new InvocationMessage(Party.createParty(), Party.createParty());
        diagram.insertInvocationMessageAtIndex(message,0);
        assertTrue(diagram.getMessages().size() == 2);
        diagram.deleteMessage(fake);
        assertTrue(diagram.getMessages().size() == 2);
        InvocationMessage message2 = new InvocationMessage(message.getSender(), message.getReceiver());
        diagram.insertInvocationMessageAtIndex(message2, 4);
        diagram.deleteMessage(message);
        assertTrue(diagram.getMessages().size() == 2);
        diagram.deleteMessage(message2);
        assertTrue(diagram.getMessages().size() == 0);
    }

    @Test
    void getInitiatorTest() {
        Diagram diagram = new Diagram();
        assertNull(diagram.getInitiator());
        InvocationMessage message = new InvocationMessage(Party.createParty(), Party.createParty());
        diagram.insertInvocationMessageAtIndex(message,0);
        assertEquals(diagram.getInitiator(), message.getSender());
    }

    @Test
    void getPrefixTest() {
        Diagram diagram = new Diagram();
        InvocationMessage message = new InvocationMessage(Party.createParty(), Party.createParty());
        diagram.insertInvocationMessageAtIndex(message,0);
        assertEquals(diagram.getPrefix(message), "1.");
    }

}