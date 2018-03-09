package interactr.cs.kuleuven.be.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class MessageTest {

    private InvocationMessage message;
    private Party sender;
    private Party receiver;


    @BeforeEach
    void setUp() {
            sender = mock(Party.class);
            receiver = mock(Party.class);
            message = new InvocationMessage(sender,receiver);
    }

    @Test
    void invalidConstructorTest() {
        assertThrows(IllegalArgumentException.class, () ->
                new Message(null, null));
    }

    @Test
    void getterTest() {
        assert(message.getReceiver() == receiver);
        assert(message.getSender() == sender);
    }

    @Test
    void getMessageTest() {
        Diagram diagram = new Diagram();
        diagram.insertInvocationMessageAtIndex(message, 0);
        assert(diagram.getMessages().contains(message));
        assert(diagram.getMessageAtIndex(0) == message);
    }

    @Test
    void deleteTest() {
        Diagram diagram = new Diagram();
        diagram.insertInvocationMessageAtIndex(message,0);
        message.delete(diagram);
        assert(!diagram.getMessages().contains(message));
    }

}
