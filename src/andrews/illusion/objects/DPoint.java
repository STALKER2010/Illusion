package andrews.illusion.objects;

import java.awt.geom.Point2D;

/**
 * @author STALKER_2010
 */
public class DPoint extends Point2D {
    public double x = 0d, y = 0d;

    public DPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Determines whether or not two points are equal. Two instances of
     * <code>Point2D</code> are equal if the values of their
     * <code>x</code> and <code>y</code> member fields, representing
     * their position in the coordinate space, are the same.
     *
     * @param obj an object to be compared with this <code>Point2D</code>
     * @return <code>true</code> if the object to be compared is
     * an instance of <code>Point2D</code> and has
     * the same values; <code>false</code> otherwise.
     */
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj instanceof DPoint) {
            DPoint pt = (DPoint) obj;
            return (x == pt.x) && (y == pt.y);
        }
        return super.equals(obj);
    }

    /**
     * Returns a string representation of this point and its location
     * in the {@code (x, y)} coordinate space. This method is
     * intended to be used only for debugging purposes, and the content
     * and format of the returned string may vary between implementations.
     * The returned string may be empty but may not be <code>null</code>.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }
}
