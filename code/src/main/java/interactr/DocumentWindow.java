import canvaswindow.CanvasWindow;

import javax.swing.text.Document;
import java.awt.*;

/**
 * A class that extends from canvaswindow, ...
 */
public class DocumentWindow extends CanvasWindow {

    int width = 600;
    int height = 600;
    String title;
    private String recordingPath;

    /**
     * The main method
     *
     * @param args
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new DocumentWindow("My Canvas Window").show();
        });
    }

    /**
     * Initializes a CanvasWindow object.
     *
     * @param title Window title
     */

    protected DocumentWindow(String title) {
        super(title);
    }



    /**
     * Called to allow you to paint on the canvas.
     *
     * You should not use the Graphics object after you return from this method.
     *
     * @param g This object offers the methods that allow you to paint on the canvas.
     */
    @Override
    protected void paint(Graphics g) {


    }

    /**
     * Called when the user presses (id == MouseEvent.MOUSE_PRESSED), releases (id == MouseEvent.MOUSE_RELEASED), or drags (id == MouseEvent.MOUSE_DRAGGED) the mouse.
     *
     * @param  id the type of mouseEvent (PRESSSED, RELEASED, DRAGGED)
     * @param x the x value where the mouseEvent happened
     * @param y the y value where the mouseEvent happened
     * @param clickCount how many clicks there were
     */
    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {

    }

    /**
     * Called when the user presses a key (id == KeyEvent.KEY_PRESSED) or enters a character (id == KeyEvent.KEY_TYPED).
     *
     * @param id the type of mouseEvent (PRESSSED, TYPED)
     * @param keyCode
     * @param keyChar
     */
    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {

    }



}
