package test.interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.domain.ObjectParty;
import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ObjectTest {

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void constructorTestInvalid() {
        assertThrows(InvalidLabelException.class, () ->
        {new ObjectParty("kzajrmazj");});
    }

    @Test
    public void constructorTestValid() {
        try {
            new ObjectParty("valid:Label");
        } catch(InvalidLabelException e) {
            assert(false);
        }
    }

}
