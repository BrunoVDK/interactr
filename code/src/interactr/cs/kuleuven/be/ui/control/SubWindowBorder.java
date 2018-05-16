package interactr.cs.kuleuven.be.ui.control;

/**
 * An enumeration for borders of a subwindow.
 *
 * @author Team 25
 * @version 1.0
 */
public enum SubWindowBorder {

    NORTH(0b0001), // Upper/North border
    EAST(0b0010), // Right/East border
    SOUTH(0b0100), // Lower/South border
    WEST(0b1000); // Left/West border

    /**
     * Registers the code for a border.
     */
    public final int code;

    SubWindowBorder(int code) {
        this.code = code;
    }

}