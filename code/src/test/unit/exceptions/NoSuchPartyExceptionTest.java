package test.unit.exceptions;

import interactr.cs.kuleuven.be.exceptions.NoSuchPartyException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NoSuchPartyExceptionTest {

    private NoSuchPartyException exception = new NoSuchPartyException(0,0);

    @Test
    void getCoordinates() {
        assertEquals(exception.getX(), 0);
        assertEquals(exception.getY(), 0);
    }

}
