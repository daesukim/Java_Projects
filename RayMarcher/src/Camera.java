import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Random;

public class Camera implements Drawable, MouseMotionListener, MouseListener {
    private int x;
    private int y;
    private double angle;

    public double getAngle() {
        return angle;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public Camera(int x, int y){
        this.x = x;
        this.y = y;
        this.angle = 0;
    }

    @Override
    public void drawObject(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillOval(this.x, this.y, 10, 10);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int button = e.getButton();
        if (button == MouseEvent.BUTTON1){
            this.angle++;
        }
        else if (button == MouseEvent.BUTTON3){
            this.angle--;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
