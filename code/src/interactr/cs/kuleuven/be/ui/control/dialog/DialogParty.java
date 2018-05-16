package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.control.control.RadioButton;
import interactr.cs.kuleuven.be.ui.control.control.TextField;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;


public class DialogParty extends DialogWindow {

    private static  final int width = 300, height = 50;

    public DialogParty(Party party, Diagram diagram, boolean isActor){
        super(diagram);
        this.party = party;
        this.isActor = isActor;
        this.setFrame(new Rectangle(0,0,width,height));
    }

    private boolean isActor;

    @Override
    protected void displayView(PaintBoard paintBoard) {
        if(isActor) {
            actor.displayControl(paintBoard, width * 9/12, height/2, true);
            object.displayControl(paintBoard, width * 11/12, height/2, false);
        }else{
            actor.displayControl(paintBoard, width * 9/12, height/2, false);
            object.displayControl(paintBoard, width * 11/12, height/2, true);
        }


    }

    private RadioButton actor,object;

    private TextField instanceName, className;

    /**
     * The party of this dialog
     */
    private Party party;


    /**
     * Initialize this new dialog with given diagram.
     *
     * @param diagram The diagram to initialize this new dialog with.
     */
    public DialogParty(Diagram diagram) {
        super(diagram);
    }





}
