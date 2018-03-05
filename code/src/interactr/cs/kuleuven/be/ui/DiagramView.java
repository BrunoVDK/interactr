package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.purecollections.PMap;

import java.awt.*;
import java.util.Optional;

public abstract interface DiagramView {

    public void draw(Graphics g);

    public Optional<Party> getPartyAt(int x, int y);

    public boolean canAddPartyAt(int x, int y);

    public void addNewParty(Party party, int x ,int y);

    public void moveSelectedParty(Party party,int x, int y);








}