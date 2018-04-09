package interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ActorTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void canHaveAsLabelTest() {
        assert(new ActorParty().canHaveAsLabel("a:A"));
        assert(!(new ActorParty().canHaveAsLabel("A:A")));
    }

    @Test
    void constructorTestInvalid() {
        assertThrows(InvalidLabelException.class, () ->
        {ActorParty party = new ActorParty("a:");});
    }

    @Test
    void constructorTestValid() {
        try {
            new ActorParty("a:A");
            new ActorParty(":A");
        } catch(InvalidLabelException e) {
            assert(false);
        }
    }

}
