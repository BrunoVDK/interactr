package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Party;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Optional;


/**
 * A class of diagram handlers for intercepting and interpreting mouse and key events.
 */
public class DiagramController {

    /**
     * Initialize a new diagram handler.
     *
     */
    public DiagramController() {
        this.diagram = new Diagram(this);
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


    //TODO de logica doorverwijzen naar een andere klassen want deze moet hier niet staan
    void handleMouseEvent(int id, int x, int y, int clickCount) {
        switch(id){
            case MouseEvent.MOUSE_CLICKED:
                if(clickCount == 2 && !paintController.getPartyAt(x,y).isPresent() && paintController.canAddParty(x,y)){
                    getDiagram().addParty(x,y);
                    getWindow().repaint();
                }
                if (clickCount == 2 && paintController.getPartyAt(x,y).isPresent()){
                    getDiagram().changePartyType(paintController.getPartyAt(x,y).get());
                    getWindow().repaint();
                }
            case MouseEvent.MOUSE_PRESSED:
                if(paintController.getPartyAt(x,y).isPresent()) paintController.setSelectedParty(paintController.getPartyAt(x,y).get());

            case MouseEvent.MOUSE_DRAGGED:
                if(paintController.getSelectedParty() != null) {
                    paintController.moveSelectedParty(x, y);
                    getWindow().repaint();
                }

            case MouseEvent.MOUSE_RELEASED:
                if(paintController.getSelectedParty() != null) paintController.setSelectedParty(null);
        }

    }

    void handleKeyEvent(int id, int keyCode, char keyChar) {
        if (keyChar == KeyEvent.VK_TAB && id == KeyEvent.KEY_TYPED) {
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

    /**
     *
     * @param p
     */
    public void addPartyToView(Party p,int x, int y){
        paintController.addNewPartyToViews(p,x,y);
    }



}