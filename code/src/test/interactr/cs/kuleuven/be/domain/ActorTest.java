package test.interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.domain.ActorParty;
import interactr.cs.kuleuven.be.ui.exceptions.InvalidLabelException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ActorTest {

    @Before
    protected void setUp() {
    }

    @After
    protected void tearDown() {
    }

    @Test(expected=InvalidLabelException.class)
    void constructorTestValid() throws Exception {
        ActorParty party = new ActorParty();
    }

}
