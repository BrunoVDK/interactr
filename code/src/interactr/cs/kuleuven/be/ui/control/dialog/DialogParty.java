package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.control.control.Button;


import java.util.ArrayList;

public class DialogParty extends DialogWindow{



    /**
     * The party of this dialog
     */
    private Party party;

    /**
     * A single set of radio buttons of which is only one selected, ever.
     */
    private ArrayList<Button> Button;


    /**
     * Initialize this new dialog with given diagram.
     *
     * @param diagram The diagram to initialize this new dialog with.
     */
    public DialogParty(Diagram diagram) {
        super(diagram);
    }





}
