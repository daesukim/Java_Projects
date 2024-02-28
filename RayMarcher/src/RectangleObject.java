import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class RectangleObject extends CollisionObject{
    private double width;
    private double height;

    public RectangleObject(double x, double y, double width, double height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public void drawObject(Graphics2D g2d) {
        Rectangle2D object = new Rectangle2D.Double(getX(), getY(), width, height);
        g2d.setColor(this.getColor());
        g2d.fill(object);
    }

    @Override
    public double computeDistance(double cameraX, double cameraY) {
        double l4 = Line2D.ptSegDist(this.getX(),this.getY(),this.getX() + width, this.getY(), cameraX, cameraY);
        double l1 = Line2D.ptSegDist(this.getX(),this.getY(),this.getX(), this.getY() + height, cameraX, cameraY);
        double l2 = Line2D.ptSegDist(this.getX(), this.getY() + height,this.getX() + width, this.getY() + height, cameraX, cameraY);
        double l3 = Line2D.ptSegDist(this.getX() + width, this.getY(),this.getX() + width, this.getY() + height, cameraX, cameraY);

        return Math.min(Math.min(l1, l2), Math.min(l3, l4));
    }
}
