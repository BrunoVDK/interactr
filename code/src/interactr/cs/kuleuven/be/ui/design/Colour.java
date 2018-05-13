package interactr.cs.kuleuven.be.ui.design;

/**
 * A class of colours represented by a HSB values.
 *
 * @author Team 25
 * @version 1.0
 */
public class Colour {

    // Standard colours
    public static Colour BLACK = new Colour(0.0f, 0.0f, 0.0f);
    public static Colour BLUE = new Colour(0.67f, 1.0f, 1.0f);
    public static Colour GRAY = new Colour(0.0f, 0.0f, 0.2f);
    public static Colour LIGHT_GRAY = new Colour(0.0f, 0.0f, 0.8f);
    public static Colour DARK_GRAY = new Colour(216/360f, 35/360f, 0.66f);
    public static Colour RED = new Colour(0.0f, 1.0f, 1.0f);
    public static Colour WHITE = new Colour(0.0f, 0.0f, 1.0f);

    /**
     * Initiliaze this new colour with given hue, saturation and brightness value.
     *
     * @param hue The hue for this new colour.
     * @param saturation The saturation for this new colour.
     * @param brightness The brightness for this new colour.
     */
    public Colour(float hue, float saturation, float brightness) {
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
    }

    /**
     * Returns the hue of this colour.
     */
    public float getHue() {
        return this.hue;
    }

    /**
     * The hue for this colour.
     */
    private float hue;

    /**
     * Returns the saturation of this colour.
     */
    public float getSaturation() {
        return this.saturation;
    }

    /**
     * The saturation for this colour.
     */
    private float saturation;

    /**
     * Returns the brightness of this colour.
     */
    public float getBrightness() {
        return this.brightness;
    }

    /**
     * The brightness for this colour.
     */
    private float brightness;

}