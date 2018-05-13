package interactr.cs.kuleuven.be.ui.control;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.ui.command.CommandHandler;
import interactr.cs.kuleuven.be.ui.control.diagram.CommunicationView;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;
import interactr.cs.kuleuven.be.ui.control.diagram.SequenceView;

/**
 * A class of diagram windows with a series of diagram views.
 *
 * @author Team 25
 * @version 1.0
 */
public class DiagramWindow extends SubWindow {

    /**
     * Initialize this new diagram window without any diagram window.
     */
    public DiagramWindow() {
        this(null);
    }

    /**
     * Create a new subwindow with a default frame of size 400x400 as a duplicate of the given subwindow.
     *  If the given subwindow is null, a subwindow with a new (empty) diagram is created.
     *  Otherwise the diagram of the given subwindow is adopted by this new subwindow.
     *
     * @param diagramWindow The subwindow that is to be duplicated by this subwindow.
     */
     DiagramWindow(DiagramWindow diagramWindow) {
         super(diagramWindow);
         if (diagramWindow == null || diagramWindow.getDiagram() == null) {
            Diagram adoptedDiagram = new Diagram();
            views = views.plus(new SequenceView(adoptedDiagram));
            views = views.plus(new CommunicationView(adoptedDiagram));
            activateViewAtIndex(0);
        }
        else {
            for (DiagramView view : diagramWindow.getViews())
                views = views.plus(view.clone());
            activateViewAtIndex(diagramWindow.getViews().indexOf(diagramWindow.getActiveView()));
        }
    }

    /**
     * Returns the diagram associated with this subwindow.
     */
    public Diagram getDiagram() {
        return (views.size() == 0 ? null : views.get(0).getDiagram());
    }

    /**
     * Close this subwindow.
     *  This unregisters it as an observer.
     */
    public void close() {
        for (DiagramView view : views)
            view.close(); // Close all views too, but only after unregistering this subwindow as observer
    }

    @Override
    public CommandHandler nextHandler() {
        return getActiveView();
    }

}