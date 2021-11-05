package task_3.window;

import task_3.graphics.canvas.Canvas;
import task_3.graphics.canvas.DrawParams;
import task_3.graphics.drawers.BaseDrawer;
import task_3.graphics.points.Pixel;
import task_3.graphics.points.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class PixelPane
    extends JPanel
    implements MouseListener,
               MouseMotionListener,
               MouseWheelListener,
               KeyListener
{
    public static final double SPEED_DRAG = 1;
    public static final double SPEED_SCALE = 0.03;
    
    private final List<BaseDrawer> drawers = new ArrayList<>();
    private final DrawParams drawParams = new DrawParams();
    
    private Pixel prevDrag = null;
    
    {
        drawParams.offsetX = 760;
        drawParams.offsetY = -370;
    }
    
    public PixelPane() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addKeyListener(this);
        requestFocusInWindow();
    }
    
    public void addDrawer(BaseDrawer drawer) {
        drawers.add(drawer);
    }
    
    public void removeDrawer(BaseDrawer drawer) {
        drawers.remove(drawer);
    }
    
    private boolean handle(TypeEvent typeEvent, InputEvent event) {
        var wasEdited = false;
        for (var drawer : drawers) {
            if (drawer.handleEvent(typeEvent, event, drawParams)) {
                wasEdited = true;
            }
        }
        repaint();
        return wasEdited;
    }
    
    @Override
    public void paint(Graphics g) {
        Canvas canvas = new Canvas(getWidth(), getHeight());
        canvas.modifyDrawParams(drawParams);
        for (BaseDrawer drawer : drawers) {
            drawer.draw(canvas);
        }
        g.drawImage(canvas.bufferedImage, 0, 0, null);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        handle(TypeEvent.KEY_TYPED, e);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        handle(TypeEvent.KEY_PRESSED, e);
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        handle(TypeEvent.KEY_RELEASED, e);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        handle(TypeEvent.MOUSE_CLICKED, e);
    }
    
    @Override
    public void mousePressed(MouseEvent event) {
        if (!handle(TypeEvent.MOUSE_PRESSED, event)) {
            prevDrag = new Pixel(event.getX(), event.getY());
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if (!handle(TypeEvent.MOUSE_RELEASED, e)) {
            prevDrag = null;
        }
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        handle(TypeEvent.MOUSE_ENTERED, e);
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        handle(TypeEvent.MOUSE_EXITED, e);
    }
    
    @Override
    public void mouseDragged(MouseEvent event) {
        if (!handle(TypeEvent.MOUSE_DRAGGED, event) && prevDrag != null) {
            var current = new Pixel(event.getX(), event.getY());
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
            repaint();
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        handle(TypeEvent.MOUSE_MOVED, e);
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        if (!handle(TypeEvent.MOUSE_WHEEL_MOVED, event)) {
            double scale = 1 + Math.signum(event.getWheelRotation()) * SPEED_SCALE;
            
            var scaleParams = new DrawParams();
            scaleParams.scaleX = scale;
            scaleParams.scaleY = scale;
            scaleParams.offsetX = (scale - 1) * drawParams.scaleX * event.getX();
            scaleParams.offsetY = (1 - scale) * drawParams.scaleY * event.getY();
            drawParams.modify(scaleParams);
            
            repaint();
        }
    }
}
