package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Party;

/**
 * A class of communication views. Communication views display diagrams
 *  as a composite of parties with messages sent between them. Parties
 *  can be located at any coordinate.
 *
 * @author Team 25
 * @version 1.0
 */
public class CommunicationView extends DiagramView {
    
    @Override
    public String viewName() {
        return "Communication View";
    }

    public boolean canAddMessage(Party sender, Party receiver, int y){return false;}


}