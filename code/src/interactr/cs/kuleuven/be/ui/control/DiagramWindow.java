package interactr.cs.kuleuven.be.ui.control;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.exceptions.IllegalWindowFrameException;
import interactr.cs.kuleuven.be.purecollections.PList;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.command.CommandHandler;
import interactr.cs.kuleuven.be.ui.control.diagram.CommunicationView;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;
import interactr.cs.kuleuven.be.ui.control.diagram.SequenceView;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

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
     private DiagramWindow(DiagramWindow diagramWindow) {
         super(diagramWindow);
         if (diagramWindow == null || diagramWindow.getDiagram() == null) {
            Diagram adoptedDiagram = new Diagram();
            views = views.plus(new SequenceView(adoptedDiagram, getViewFrame()));
            views = views.plus(new CommunicationView(adoptedDiagram, getViewFrame()));
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

    @Override
    void setFrame(Rectangle frame) throws IllegalWindowFrameException {
        super.setFrame(frame);
        for (DiagramView view : getViews())
            view.setFrame(getViewFrame());
    }

    /**
     * Switch to the next view.
     */
    public void nextView() {
        activeViewIndex = (activeViewIndex + 1) % views.size();
    }

    /**
     * Returns the title of this subwindow.
     *
     * @return The title of this subwindow.
     */
    public String getTitle() {
        return getActiveView().toString();
    }

    /**
     * Returns the currently active view in this subwindow.
     *
     * @return The active view for this subwindow.
     */
    private DiagramView getActiveView() {
        return views.get(activeViewIndex);
    }

    /**
     * Activate the view at the given index.
     *
     * @param index The index of the view that is to be activated.
     */
    private void activateViewAtIndex(int index) {
        if (index >= 0 && index < views.size())
            activeViewIndex = index;
    }

    /**
     * The index of the currently active view in this subwindow.
     */
    private int activeViewIndex = 0;

    /**
     * Returns the views in this subwindow.
     *
     * @return The diagram views held by this subwindow.
     */
    PList<DiagramView> getViews() {
        return views;
    }

    /**
     * A list of diagram views held by this subwindow.
     */
    PList<DiagramView> views = PList.<DiagramView>empty();

    @Override
    public void displayView(PaintBoard paintBoard) {
        getActiveView().display(paintBoard);
    }

    /**
     * Close this subwindow.
     *  This unregisters it as an observer.
     */
    public void close() {
        super.close();
        for (DiagramView view : views)
            view.close(); // Close all views too, but only after unregistering this subwindow as observer
    }

    @Override
    public CommandHandler nextHandler() {
        return getActiveView();
    }

    public void executeDiagramWindow(Command command){

    }
}