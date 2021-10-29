package task_3.graphics.canvas;

import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import task_3.graphics.points.Pixel;
import task_3.graphics.points.Point;


public class Canvas
    extends ImageView
{
    private final WritableImage writableImage;
    private final DrawParams drawParams = new DrawParams();
    
    public Canvas(int weight, int height) {
        super();
        writableImage = new WritableImage(weight, height);
        setImage(writableImage);
    }
    
    public void setPixel(Pixel pixel) {
        try {
            writableImage.getPixelWriter().setColor(pixel.x, pixel.y, pixel.color);
        } catch (IndexOutOfBoundsException e) {
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
