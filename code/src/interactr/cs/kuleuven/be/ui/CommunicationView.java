package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Party;
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
            else if(!entry.getKey().isActor()) ObjectModel.draw(g,entry.getValue());
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

    @Override
    public void addNewParty(Party party, int x, int y) {
        parties.put(party, new HitBox(x,y));
    }

    @Override
    public void moveSelectedParty(Party party, int x, int y) {
        parties.replace(party,new HitBox(x,y));
    }

    private HashMap<Party,HitBox> parties = new HashMap<>();

}