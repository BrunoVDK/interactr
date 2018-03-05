package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.shapes.ActorModel;
import interactr.cs.kuleuven.be.ui.shapes.ObjectModel;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SequenceView implements DiagramView {

    private final static int activationBarHeight = 100;


    public String toString() {
        return "Sequence View";
    }

    //TODO aanpassen dat het dynamisch wordt met lengte en breedte
    @Override
    public void draw(Graphics g) {
        g.drawLine(0, activationBarHeight, 600, activationBarHeight);
        for(Map.Entry<Party,HitBox> entry : parties.entrySet()) {

            if(entry.getKey().isActor()) ActorModel.draw(g,entry.getValue());
            else if(!entry.getKey().isActor()) ObjectModel.draw(g,entry.getValue());

            drawLifeline(g,entry.getValue());
        }
    }

    private void drawLifeline(Graphics g,HitBox hitBox){
        g.drawLine(hitBox.getxMin() + (HitBox.width / 2) ,
                (hitBox.getyMin() + HitBox.heigth),
                hitBox.getxMin() + (HitBox.width / 2), 600 );
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
    public boolean canAddPartyAt(int x, int y) {
        if (y < activationBarHeight) return true;
        else return false;
    }

    @Override
    public void addNewParty(Party party, int x, int y) {
        parties.put(party, new HitBox(x, activationBarHeight/4));
    }

    @Override
    public void moveSelectedParty(Party party, int x, int y) {
        parties.replace(party,new HitBox(x,activationBarHeight/4 ));
    }

    private HashMap<Party,HitBox> parties = new HashMap<>();





}






