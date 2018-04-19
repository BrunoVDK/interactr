package test.unit.domain;

import interactr.cs.kuleuven.be.domain.ActorParty;
import interactr.cs.kuleuven.be.domain.Message;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageTest {

    @Test
    void invalidPartyTest() {
        assertThrows(IllegalArgumentException.class,
                ()->{Message message = new Message(null, Party.createParty());});
        assertThrows(IllegalArgumentException.class,
                ()->{Message message = new Message(Party.createParty(), null);});
    }

    @Test
    void toStringTest() {
        Message message = new Message(Party.createParty(), Party.createParty());
        message.setLabel("function()");
        assertNotNull(message.toString());
    }

    @Test
    void proposedLinkTest() {
        Message message = new Message(Party.createParty(), Party.createParty());
        assertNotNull(message.proposedLink());
    }

}