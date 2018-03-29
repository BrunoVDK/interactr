package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

import java.util.ArrayList;

/**
 * A class of subwindows for displaying diagrams.
 *  The subwindows can be closed, resized and moved.
 *
 * @author Team 25
 * @version 1.0
 */
public class SubWindow {

    /**
     * Create a new subwindow with given frame. The new subwindow has a sequence view and a diagram view.
     *
     * @param frame The frame for the new subwindow.
     */
    public SubWindow(Rectangle frame) {
        this(frame, null);
    }

    /**
     * Create a new subwindow with given frame as a duplicate of the given subwindow.
     *  If the given subwindow is null, a subwindow with a new (empty) diagram is created.
     *  Otherwise the diagram of the given subwindow is adopted by this new subwindow.
     *
     * @param frame The frame for the new subwindow.
     * @param subWindow The subwindow that is to be duplicated by this subwindow.
     */
    public SubWindow(Rectangle frame, SubWindow subWindow) {
        setFrame(frame);
        Diagram adoptedDiagram = null;
        if (subWindow == null) {
            adoptedDiagram = 
        }
    }

    /**
     * Returns the frame of this subwindow.
     */
    public Rectangle getFrame() {
        return frame;
    }

    /**
     * Set the frame of this subwindow to the given one.
     *
     * @param   frame
     *          The new frame for this subwindow.
     */
    public void setFrame(Rectangle frame) {
        this.frame = frame;
    }

    /**
     * Set the size of this subwindow to the new size.
     *
     * @param   width
     *          The new width for this subwindow's frame.
     * @param   height
     *          The new height for this subwindow's frame.
     * @throws  IllegalArgumentException
     *          The given width is smaller than one.
     * @throws  IllegalArgumentException
     *          The given height is smaller than one.
     */
    public void setSize(int width, int height) {
        if (width < 1)
            throw new IllegalArgumentException("The width cannot be smaller than 1.");
        if (height < 1)
            throw new IllegalArgumentException("The height cannot be smaller than 1.");
        this.frame.setWidth(width);
        this.frame.setHeight(height);
    }

    /**
     * The frame of this subwindow.
     */
    private Rectangle frame;

    /**
     * The index of the currently active view in this subwindow.
     */
    private int activeViewIndex = 0;

    /**
     * A list of diagram views held by this subwindow.
     */
    private ArrayList<DiagramView> views;

}