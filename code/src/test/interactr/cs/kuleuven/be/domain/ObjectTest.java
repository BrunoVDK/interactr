package test.interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.domain.ObjectParty;
import interactr.cs.kuleuven.be.ui.exceptions.InvalidLabelException;
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
    public void constructorTestValid() throws Exception {
        assertThrows(InvalidLabelException.class, () ->
        {ObjectParty party = new ObjectParty("kzajrmazj");});
    }

}
