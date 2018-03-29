package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Party;
<<<<<<< HEAD
import interactr.cs.kuleuven.be.ui.shapes.ActorModel;
import interactr.cs.kuleuven.be.ui.shapes.ObjectModel;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommunicationView implements DiagramView {

    @Override
    public void draw(Graphics g) {
        for(Map.Entry<Party,HitBox> entry : parties.entrySet()) {
            if(entry.getKey().isActor()) ActorModel.draw(g,entry.getValue());
            else if(!entry.getKey().isActor()) ObjectModel.draw(g,entry.getValue(),entry.getKey());
        }
    }

    @Override
    public Optional<Party> getPartyAt(int x, int y) {
        return parties.entrySet()
                .stream()
                .filter(entry ->  entry.getValue().encloses(x,y))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    @Override
    public boolean canAddPartyAt(int x, int y) {return true;}
=======
>>>>>>> brunoBranch

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