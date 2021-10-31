package task_3.graphics.points;

import java.awt.*;


public class Pixel {
    private final static Color defaultColor = Color.BLACK;
    
    public final int x;
    public final int y;
    public final Color color;
    
    public Pixel(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    
    public Pixel(int x, int y) {
        this(x, y, defaultColor);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pixel)) {
            return false;
        }
        
        var pixel = (Pixel) o;
        if (x != pixel.x) {
            return false;
        }
        if (y != pixel.y) {
            return false;
        }
        return color.equals(pixel.color);
    }
    
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + color.hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        return "Pixel{" +
                   "x=" + x +
                   ", y=" + y +
                   '}';
    }
}
