package interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.ui.geometry.Figure;
import interactr.cs.kuleuven.be.ui.geometry.LabeledFigure;
import interactr.cs.kuleuven.be.ui.geometry.Link;

/**
 * A  class of messages. Each message has a receiving - and a sender party.
 *
 * @author Team 25
 * @version 1.0
 */
public class Message extends DiagramComponent {

    /**
     * Checks whether or not the given label is a valid label for this message.
     *
     * @param label The label whose validity is to be checked.
     * @return True if and only if the given label has a length of at most 30 characters.
     */
    public boolean canHaveAsLabel(Label label) {
        return label.getLength() <= 30;
    }

    @Override
    public LabeledFigure getProposedFigure() {
        return new Link();
    }

}
