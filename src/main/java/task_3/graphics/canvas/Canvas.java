package task_3.graphics.canvas;

import task_3.graphics.points.Pixel;
import task_3.graphics.points.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Canvas {
    public final BufferedImage bufferedImage;
    private final DrawParams drawParams = new DrawParams();
    
    public Canvas(int weight, int height) {
        bufferedImage = new BufferedImage(weight, height, BufferedImage.TYPE_INT_ARGB);
        Graphics gr = bufferedImage.createGraphics();
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, weight, height);
        gr.dispose();
    }
    
    public void setPixel(Pixel pixel) {
        try {
            bufferedImage.setRGB(pixel.x, pixel.y, pixel.color.getRGB());
        } catch (ArrayIndexOutOfBoundsException e) {
            //
        }
    }
    
    public DrawParams modifyDrawParams(DrawParams drawParams) {
        var oldDrawParams = this.drawParams.copy();
        this.drawParams.modify(drawParams);
        return oldDrawParams;
    }
    
    public Point convert(Pixel pixel) {
        return drawParams.convert(pixel);
    }
    
    public Pixel convert(Point point) {
        return drawParams.convert(point);
    }
    
    public void comeBack(DrawParams oldDrawParams) {
        this.drawParams.copyOf(oldDrawParams);
    }
}
