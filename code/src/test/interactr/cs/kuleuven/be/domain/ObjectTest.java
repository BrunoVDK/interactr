package test.interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.domain.ObjectParty;
import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ObjectTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void canHaveAsLabelTest() {
        assert(new ObjectParty().canHaveAsLabel("a:A"));
        assert(!new ObjectParty().canHaveAsLabel("A:A"));
    }

    @Test
    void constructorTestInvalid() {
        assertThrows(InvalidLabelException.class, () ->
        {ObjectParty party = new ObjectParty("a:");});
    }

    @Test
    void constructorTestValid() {
        try {
            new ObjectParty("a:A");
            new ObjectParty(":A");
        } catch(InvalidLabelException e) {
            assert(false);
        }
    }

}
