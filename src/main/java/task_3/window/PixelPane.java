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
    private static final double SPEED_DRAG = 1;
    private static final double SPEED_SCALE = 0.03;
    
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
    
    private boolean checkEdit(InputEvent event) {
        var wasEdited = false;
        for (var drawer : drawers) {
            if (drawer.edit(drawParams, event)) {
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
    public void keyTyped(final KeyEvent e) {
        checkEdit(e);
    }
    
    @Override
    public void keyPressed(final KeyEvent e) {
        checkEdit(e);
    }
    
    @Override
    public void keyReleased(final KeyEvent e) {
        checkEdit(e);
    }
    
    @Override
    public void mouseClicked(final MouseEvent e) {
        checkEdit(e);
    }
    
    @Override
    public void mousePressed(final MouseEvent event) {
        if (!checkEdit(event)) {
            prevDrag = new Pixel(event.getX(), event.getY());
        }
    }
    
    @Override
    public void mouseReleased(final MouseEvent e) {
        if (!checkEdit(e)) {
            prevDrag = null;
        }
    }
    
    @Override
    public void mouseEntered(final MouseEvent e) {
        checkEdit(e);
    }
    
    @Override
    public void mouseExited(final MouseEvent e) {
        checkEdit(e);
    }
    
    @Override
    public void mouseDragged(final MouseEvent event) {
        if (!checkEdit(event) && prevDrag != null) {
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
    public void mouseMoved(final MouseEvent e) {
        checkEdit(e);
    }
    
    @Override
    public void mouseWheelMoved(final MouseWheelEvent event) {
        if (!checkEdit(event)) {
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
