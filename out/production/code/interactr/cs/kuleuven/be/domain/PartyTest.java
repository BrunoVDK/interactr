package interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PartyTest {

    @Before
    void setUp() {
    }

    @Test
    void canHaveAsLabelTest() {
        assert(new Party().canHaveAsLabel("a:A"));
        assert(!new Party().canHaveAsLabel("A:A"));
    }

    @Test
    void constructorTestInvalid() {
        assertThrows(InvalidLabelException.class, () ->
        {Party party = new Party("a:");});
    }

    @Test
    void constructorTestValid() {
        try {
            new Party("a:A");
            new Party(":A");
        } catch(InvalidLabelException e) {
            assert(false);
        }
    }

}
