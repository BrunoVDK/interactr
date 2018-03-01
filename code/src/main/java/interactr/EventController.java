package main.java.interactr;

import java.awt.event.MouseEvent;

public class EventController {

    private Diagram diagram;

    public EventController(){
        this.diagram = new Diagram();
    }


    /**
     * A method that interprets the mouse event en tells whats need to be done
     * @param id (id == MouseEvent.MOUSE_PRESSED), releases (id == MouseEvent.MOUSE_RELEASED), or drags (id == MouseEvent.MOUSE_DRAGGED
     * @param x
     * @param y
     * @param clickCount
     */
    public void interpretMouseEvent(int id, int x, int y, int clickCount){
        switch (id){
            case MouseEvent.MOUSE_CLICKED:
                    if(clickCount == 2) diagram.addParty(x,y);
            case 2: id = 1;
            case 3: id = 2;
        }


    }


}
