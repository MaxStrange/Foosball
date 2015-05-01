package fussball;

/**
 *A class to represent a mathematical or physical 2D vector quantity.
 * @author Max Strange
 */
public class Vector {
    private double x;
    private double y;
    
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getMagnitude() { return Math.sqrt((this.x * this.x) + (this.y * this.y)); }
    public double getXComponent() { return this.x; }
    public double getYComponent() { return this.y; }
}
