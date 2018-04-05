package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Diagram;
import java.util.HashMap;

/**
 * An interface for diagram observers. Each diagram observer is notified of changes to the diagram
 *  that it observes.
 *  Each change is of a certain type, each type of update has a specific set of parameters.
 *
 * @author Team 25
 * @version 1.0
 */
public interface DiagramObserver {

    /**
     * Called when a diagram did update in any which way.
     *
     * @param diagram The diagram that was updated.
     * @param updateType The type of update.
     * @param parameters The parameters for the update type.
     */
    public void diagramDidUpdate(Diagram diagram, String updateType, HashMap<String, Object> parameters);

}