package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.ui.exceptions.InvalidAddException;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * A class of event handlers for interpreting incoming mouse/key events
 *  and forwarding them to the associated diagram controller.
 *
 * @author Team 25
 * @version 1.0
 */
public class EventHandler {

    /**
     * Initialize this new event handler with given diagram controller.
     *
     * @param diagramController The diagram controller to associate this event handler with.
     */
    public EventHandler(DiagramController diagramController) {
        setDiagramController(diagramController);
    }

    /**
     * Handle the mouse event at given location, of given type and with given click count.
     *
     * @param id The type of the mouse event.
     * @param x The x coordinate for the mouse event.
     * @param y The y coordinate for the mouse event.
     * @param clickCount The amount of clicks for this mouse events.
     */
    void handleMouseEvent(int id, int x, int y, int clickCount) {
        if (getDiagramController() != null) {
            if (id == MouseEvent.MOUSE_CLICKED) {
                switch (clickCount) {
                    case 1: // Single click
                        // 4 use cases dealt with here
                        break;
                    case 2: // Double click
                        try {
                            getDiagramController().addPartyAt(x,y);
                        }
                        catch (InvalidAddException exception) {
                            if (exception.getParty() != null)
                                getDiagramController().switchPartyType(exception.getParty());
                        }
                        break;
                }
            }
        }
    }

    /**
     * Handle the key event of given type, having the given key code and key char.
     *
     * @param id The type of key event.
     * @param keyCode The key code for the key event.
     * @param keyChar The key char for the key event.
     */
    void handleKeyEvent(int id, int keyCode, char keyChar) {
        if (getDiagramController() != null) {
            switch (id) {
                case KeyEvent.KEY_TYPED:
                    if (keyChar == KeyEvent.VK_TAB)
                        getDiagramController().nextView();
                    else if (keyChar == KeyEvent.VK_DELETE) {

                    }
                    break;
            }
        }
    }

    /**
     * Returns the diagram controller associated with this event handler.
     */
    public DiagramController getDiagramController() {
        return diagramController;
    }

    /**
     * Set the diagram controller for this event handler to the given one.
     *
     * @param diagramController The new diagram controller for this event handler.
     * @post The diagram controller associated with this event handler matches the given one.
     */
    public void setDiagramController(DiagramController diagramController) {
        this.diagramController = diagramController;
    }

    /**
     * Variable registering the diagram controller of this event handler.
     */
    private DiagramController diagramController;

}