package unit.domain;

import interactr.cs.kuleuven.be.domain.Message;
import interactr.cs.kuleuven.be.domain.Party;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageTest {

    @Test
    void invalidPartyTest() {
        assertThrows(IllegalArgumentException.class,
                ()->{Message message = new Message("", null, Party.createParty());});
        assertThrows(IllegalArgumentException.class,
                ()->{Message message = new Message("", Party.createParty(), null);});
    }

}