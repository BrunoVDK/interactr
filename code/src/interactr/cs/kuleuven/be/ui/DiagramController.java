package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Party;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


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
                handleMouseClicked(x,y,clickCount);
                break;

            case MouseEvent.MOUSE_PRESSED:
                handleMousePressed(x,y,clickCount);
                break;

            case MouseEvent.MOUSE_DRAGGED:
                handleMouseDragged(x,y,clickCount);
                break;

            case MouseEvent.MOUSE_RELEASED:
                handleMouseReleased();
                break;
        }

    }

    private boolean notYetReleased;

    void handleMouseClicked(int x, int y, int clickCount){
        if(clickCount == 2
                && !paintController.getPartyAt(x,y).isPresent() && paintController.canAddParty(x,y)
                && ! paintController.isEditMode()){
            getDiagram().addParty(x,y);
            getWindow().repaint();
        }
        if (clickCount == 2 && paintController.getPartyAt(x,y).isPresent() && ! paintController.isEditMode()){
            getDiagram().changePartyType(paintController.getPartyAt(x,y).get());
            getWindow().repaint();
        }
    }

    void handleMousePressed(int x, int y, int clickcount){
        if(clickcount == 1
                && paintController.getSelectedParty() == null
                && paintController.getPartyAt(x,y).isPresent()
                && ! paintController.isEditMode()) {

            paintController.setSelectedParty(paintController.getPartyAt(x,y).get());
            paintController.setPreviousSelectedPosition(new int[]{x,y});
            this.notYetReleased = true;
        }

    }
    void handleMouseDragged(int x, int y, int clickcount){
        if (! paintController.isEditMode()) {
            paintController.moveSelectedParty(x, y);
            getWindow().repaint();
        }
    }

    void handleMouseReleased(){
        if(! paintController.isEditMode()) {
            paintController.setSelectedParty(null);
            paintController.setPreviousSelectedPosition(null);
            this.notYetReleased = false;
        }
    }

    void handleKeyEvent(int id, int keyCode, char keyChar) {
        if (keyChar == KeyEvent.VK_TAB && id == KeyEvent.KEY_TYPED) {
            this.paintController.switchView();
            this.getWindow().repaint();
        }

        else if (keyChar == KeyEvent.VK_DELETE) {

        }
        else if(paintController.isEditMode() && keyChar == KeyEvent.VK_BACK_SPACE && id == KeyEvent.KEY_TYPED){
            getDiagram().deleteCharOfLabel(paintController.getSelectedParty());
            getWindow().repaint();
        }
        else if(paintController.isEditMode() && keyChar == KeyEvent.VK_ENTER && id == KeyEvent.KEY_TYPED){
            if(paintController.getSelectedParty().checkCorrectnessLabel()){
                paintController.setEditMode(false);
                paintController.setSelectedParty(null);
            }
        }

        else if(paintController.isEditMode() && keyChar != KeyEvent.VK_ENTER && id == KeyEvent.KEY_TYPED){
            getDiagram().addCharToLabel(paintController.getSelectedParty(),keyChar);
            getWindow().repaint();
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
        paintController.setEditMode(true);
        paintController.setSelectedParty(p);
        paintController.addNewPartyToViews(p,x,y);
    }



}