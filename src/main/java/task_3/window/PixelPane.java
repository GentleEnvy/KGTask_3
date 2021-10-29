package task_3.window;

import javafx.scene.layout.Pane;
import task_3.graphics.canvas.Canvas;
import task_3.graphics.canvas.DrawParams;
import task_3.graphics.drawers.BaseDrawer;
import task_3.graphics.points.Pixel;
import task_3.graphics.points.Point;

import java.util.ArrayList;
import java.util.List;


public class PixelPane
    extends Pane
{
    private static final int SCREEN_WIGHT = 1920;
    private static final int SCREEN_HEIGHT = 1080;
    
    private static final double SPEED_DRAG = 1;
    private static final double SPEED_SCALE = 0.03;
    
    private final List<BaseDrawer> drawers = new ArrayList<>();
    private final DrawParams drawParams = new DrawParams();
    
    private Pixel prevDrag = null;
    
    {
        drawParams.offsetX = 760;
        drawParams.offsetY = -370;
        
        setOnMousePressed(
            event -> prevDrag = new Pixel(
                (int) event.getX(),
                (int) event.getY()
            )
        );
        
        setOnMouseDragged(
            event -> {
                if (prevDrag != null) {
                    var current = new Pixel((int) event.getX(), (int) event.getY());
                    var delta = new Pixel(
                        (int) ((current.x - prevDrag.x) * SPEED_DRAG),
                        (int) ((current.y - prevDrag.y) * SPEED_DRAG)
                    );
                    
                    var deltaReal = drawParams.convert(delta);
                    var zeroReal = drawParams.convert(new Pixel(0, 0));
                    var vector = new Point(
                        deltaReal.x - zeroReal.x,
                        deltaReal.y - zeroReal.y
                    );
                    
                    drawParams.offsetX += vector.x;
                    drawParams.offsetY += vector.y;
                    
                    prevDrag = current;
                }
                render();
            }
        );
        
        setOnMouseReleased(event -> prevDrag = null);
        
        setOnScroll(event -> {
            int ticks = (int) -event.getDeltaY() / 32;
            double k = ticks <= 0 ? 1 - SPEED_SCALE : 1 + SPEED_SCALE;
            
            var scaleParams = new DrawParams();
            var scale = k * Math.pow(k, Math.abs(ticks));
            scaleParams.scaleX = scale;
            scaleParams.scaleY = scale;
            scaleParams.offsetX = (scale - 1) * drawParams.scaleX * event.getX();
            scaleParams.offsetY = (1 - scale) * drawParams.scaleY * event.getY();
            drawParams.modify(scaleParams);
            
            render();
        });
    }
    
    public void addDrawer(BaseDrawer drawer) {
        drawers.add(drawer);
        render();
    }
    
    public void removeDrawer(BaseDrawer drawer) {
        drawers.remove(drawer);
        render();
    }
    
    public void render() {
        getChildren().clear();
        
        var canvas = new Canvas(SCREEN_WIGHT, SCREEN_HEIGHT);
        canvas.modifyDrawParams(drawParams);
        
        for (var drawer : drawers) {
            drawer.draw(canvas);
        }
        
        getChildren().add(canvas);
    }
}
