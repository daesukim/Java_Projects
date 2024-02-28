import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class CircleObject extends CollisionObject{
    private double diameter;

    public CircleObject(double x, double y, double diameter) {
        super(x, y);
        this.diameter = diameter;
    }

    public void setRadius(double radius) {
        this.diameter = radius;
    }

    public double getRadius() {
        return diameter;
    }

    @Override
    public void drawObject(Graphics2D g2d) {
        Ellipse2D object = new Ellipse2D.Double(this.getX(), this.getY(), diameter, diameter);
        g2d.setColor(this.getColor());
        g2d.fill(object);
    }

    @Override
    public double computeDistance(double cameraX, double cameraY) {
        double x = cameraX - (this.getX() + diameter / 2);
        double y = cameraY - (this.getY() + diameter / 2) ;
        return Math.sqrt((Math.pow(x,2) + Math.pow(y,2))) - diameter/2;
    }
}
