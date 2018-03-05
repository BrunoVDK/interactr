package interactr.cs.kuleuven.be.domain;

/**
 * A class of labels having a string value.
 */
public class Label {

    /**
     * Initialize this new label with an empty string.
     *
     * @post The value of this new label is an empty string.
     */
    public Label() {
        this("");
    }

    /**
     * Initialie this new label with given value.
     *
     * @param value The value for this new label.
     * @post The value of this new label equals the given one.
     */
    public Label(String value) {
        setValue(value);
    }

    /**
     * Returns the length of this label's value.
     *
     * @return The number of characters in this label's string value.
     */
    public int getLength() {
        return getValue().length();
    }

    /**
     * Returns the value of this label.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Sets the value of this label to the given one.
     *
     * @param value The new value for this label.
     * @post The value of this label equals the given one. If the given value
     *  is null, then this label's value remains unchanged.
     */
    public void setValue(String value) {
        if (value != null)
            this.value = value;
    }

    /**
     * Registers the value of this label.
     */
    private String value = "";

}