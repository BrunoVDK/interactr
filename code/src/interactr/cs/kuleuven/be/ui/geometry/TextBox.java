package interactr.cs.kuleuven.be.ui.geometry;

import interactr.cs.kuleuven.be.ui.PaintBoard;

/**
 * A class of text boxes.
 *
 * @author Team 25
 * @version 1.0
 */
public class TextBox extends Figure {

    /**
     * Initialize this new text box with given string value.
     *
     * @param string The string value for this new textbox.
     */
    public TextBox(String string) {
        super(0,0,100, 20);
        setString(string);
    }

    /**
     * Returns the string value of this text box.
     */
    public String getString() {
        return string;
    }

    /**
     * Set the string value for this text box to match the given one.
     *
     * @param string The new string value for this text box.
     */
    public void setString(String string) {
        this.string = string;
    }

    /**
     * The string value for this text box.
     */
    protected String string;

    @Override
    public void draw(PaintBoard paintBoard) {
        paintBoard.drawString(getString(), getX(), getY());
    }

}
