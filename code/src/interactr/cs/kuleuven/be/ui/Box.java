package interactr.cs.kuleuven.be.ui;

public class Box {

    /**
     * A constructor that creats a boc of two coordinates
     * @param xLeftUp
     * @param yLeftUp
     * @param xMax
     * @param yMax
     */
    public Box(int xLeftUp, int yLeftUp, int xMax, int yMax) {
        this.xMin = xLeftUp;
        this.yMin = yLeftUp;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    /**
     * The coordinates
     */
    private int xMin, yMin, xMax, yMax;



    /**
     * A method that checks if the given coordinate is enclosed by the box
     * @param x
     * @param y
     * @return
     */
    public boolean isEnclosed(int x, int y){
        if( x > xMin && x < xMax && y > yMin && y < yMax) return true;
        else return false;
    }


}
