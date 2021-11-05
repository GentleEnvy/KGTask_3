package task_3.window;

import task_3.figures.Polygon;
import task_3.graphics.drawers.PolygonDrawer;
import task_3.graphics.points.Point;

import javax.swing.*;


public class Window
    extends JFrame
{
    private final PixelPane pixelPane;
    
    public Window() {
        pixelPane = new PixelPane();
        init();
        pixelPane.setFocusable(true);
        this.add(pixelPane);
    }
    
    public void init() {
        pixelPane.addDrawer(
            new PolygonDrawer(new Polygon(
                200.0, 5, Math.PI / 2, new Point(0, 0)
            ))
        );
        pixelPane.addDrawer(
            new PolygonDrawer(new Polygon(
                300.0, 10, 0, new Point(500, 0)
            ))
        );
    }
}
