package test.interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.domain.ObjectParty;
import interactr.cs.kuleuven.be.ui.exceptions.InvalidLabelException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ObjectTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test(expected=InvalidLabelException.class)
    public void constructorTestValid() throws Exception {
        ObjectParty party = new ObjectParty("kzajrmazj");
    }

}
