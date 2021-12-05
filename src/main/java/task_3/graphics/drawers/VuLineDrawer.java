package task_3.graphics.drawers;

import task_3.figures.Line;
import task_3.graphics.canvas.Canvas;
import task_3.graphics.points.Pixel;

import java.awt.*;


public class VuLineDrawer
    extends BaseLineDrawer
{
    public VuLineDrawer(Line line) {
        super(line);
    }
    
    @Override
    protected void _drawLine(
        Canvas canvas,
        int startX,
        int endX,
        int startY,
        int endY
    ) {
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
                color
            )
        );
        canvas.setPixel(
            new Pixel(
                isSteep ? endY : endX,
                isSteep ? endX : endY,
                color
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
            color.getRed(),
            color.getGreen(),
            color.getBlue(),
            (int) ((opacity > 1 ? 1 : (opacity < 0 ? 0 : opacity)) * 255)
        );
    }
}
