package interactr.cs.kuleuven.be.ui.control.diagram;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.ui.design.Box;
import interactr.cs.kuleuven.be.ui.design.Figure;
import interactr.cs.kuleuven.be.ui.design.StickFigure;

/**
 * A class of visitors for creating figures for parties.
 *
 * @author Team 25
 * @version 1.0
 */
public class PartyModeller implements DiagramVisitor {

    private PartyModeller() {
        // Exists only to defeat instantiation.
    }

    /**
     * Get the default modeller for parties.
     *  This is a singleton.
     */
    public static PartyModeller defaultCenter() {
        return defaultModeller;
    }

    /**
     * The singleton instance.
     */
    private final static PartyModeller defaultModeller = new PartyModeller();

    /**
     * Create a figure for the given party.
     *  This figure is not unique, i.e. it is meant to be used as a flyweight.
     *
     * @param party The party to create a figure for.
     * @return A figure representing the given party, or null if none could be created.
     */
    Figure generateFigure(Party party) {
        figure = null;
        party.acceptVisitor(this);
        return figure;
    }

    /**
     * Registers the created figure.
     */
    private Figure figure;

    /**
     * Registers the flyweights.
     */
    private static Figure objectFigure = new Box(), actorFigure = new StickFigure();

    @Override
    public void visit(ObjectParty party) {
        figure = objectFigure;
    }

    @Override
    public void visit(ActorParty party) {
        figure = actorFigure;
    }

}