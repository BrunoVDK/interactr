package interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.ui.geometry.Figure;

/**
 * A class of diagram components.
 *
 * @author Team 25
 * @version 1.0
 */
public abstract class DiagramComponent {

    /**
     * Returns a proposal for how this component should be drawn, by returning a mock-up figure.
     *
     * @return A mock-up for how this component should be drawn.
     */
    public abstract Figure getProposedFigure();

}