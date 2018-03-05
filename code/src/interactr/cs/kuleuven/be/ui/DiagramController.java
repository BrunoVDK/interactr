package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Party;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * A class of diagram handlers for intercepting and interpreting mouse and key events.
 */
public class DiagramController {

    /**
     * Initialize a new diagram handler.
     *
     */
    public DiagramController() {
        this.diagram = new Diagram();
        this.paintController = new PaintController(this);
        java.awt.EventQueue.invokeLater(() -> {
            this.window = new DiagramWindow("test/interactr/cs/kuleuven/be", this);
            this.window.show();
        });
    }

    /**
     * Variable registering the paint controller for this diagram controller.
     */
    private PaintController paintController;

    void handleMouseEvent(int id, int x, int y, int clickCount) {

    }

    void handleKeyEvent(int id, int keyCode, char keyChar) {
        if (keyChar == KeyEvent.VK_TAB) {
            this.paintController.switchView();
            this.getWindow().repaint();
        }
        else if (keyChar == KeyEvent.VK_DELETE) {

        }
    }

    public void paint(Graphics context) {
        this.paintController.paint(context);
    }
    


    /**
     * Returns the canvaswindow of this class
     * @return
     */
    public DiagramWindow getWindow() {
        return window;
    }

    /**
     * The window associated with this diagram controller.
     */
    private DiagramWindow window;

    public Diagram getDiagram() {
        return this.diagram;
    }

    /**
     * The diagram associated with this diagram handler.
     */
    private Diagram diagram;


    public static void main(String[] args) {
        new DiagramController();
    }

}