package task_3.graphics.drawers;

import javafx.scene.paint.Color;
import task_3.figures.Line;
import task_3.graphics.canvas.Canvas;
import task_3.graphics.points.Pixel;


public class LineDrawer
    extends BaseDrawer
{
    private static final Color defaultColor = Color.BLACK;
    
    private final Line line;
    
    public LineDrawer(final Line line) {
        this.line = line;
    }
    
    @Override
    protected void _draw(Canvas canvas) {
        var startPixel = canvas.convert(line.start);
        var endPixel = canvas.convert(line.end);
        
        if (startPixel.equals(endPixel)) {
            canvas.setPixel(startPixel);
            return;
        }
        
        int startX = startPixel.x;
        int startY = startPixel.y;
        int endX = endPixel.x;
        int endY = endPixel.y;
        
        boolean isSteep = Math.abs(startX - endX) < Math.abs(startY - endY);
        if (isSteep) {
            int startX_ = startX;
            // noinspection SuspiciousNameCombination
            startX = startY;
            // noinspection SuspiciousNameCombination
            startY = startX_;
            
            int endX_ = endX;
            // noinspection SuspiciousNameCombination
            endX = endY;
            // noinspection SuspiciousNameCombination
            endY = endX_;
        }
        
        if (startX > endX) {
            int startX_ = startX;
            startX = endX;
            endX = startX_;
            int startY_ = startY;
            startY = endY;
            endY = startY_;
        }
        
        int dx = endX - startX;
        double dy = endY - startY;
        
        canvas.setPixel(
            new Pixel(
                isSteep ? startY : startX,
                isSteep ? startX : startY,
                defaultColor
            )
        );
        canvas.setPixel(
            new Pixel(
                isSteep ? endY : endX,
                isSteep ? endX : endY,
                defaultColor
            )
        );
        
        double k = dy / dx;
        double y = startY + k;
        for (int x = startX + 1; x < endX; ++x) {
            int intY = (int) y;
            canvas.setPixel(
                new Pixel(
                    isSteep ? intY : x,
                    isSteep ? x : intY,
                    createColor(1 - (y - intY))
                )
            );
            canvas.setPixel(
                new Pixel(
                    isSteep ? intY + 1 : x,
                    isSteep ? x : intY + 1,
                    createColor(y - intY)
                )
            );
            y += k;
        }
    }
    
    private Color createColor(double opacity) {
        return new Color(
            defaultColor.getRed(),
            defaultColor.getGreen(),
            defaultColor.getBlue(),
            opacity > 1 ? 1 : (opacity < 0 ? 0 : opacity)
        );
    }
}
