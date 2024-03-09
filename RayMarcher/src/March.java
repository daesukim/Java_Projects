import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class March {
    private Point2D start;
    private Point2D end;

    public March(Point2D start, Point2D end) {
        this.start = start;
        this.end = end;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // Draw the line
        Line2D line = new Line2D.Double(start.getX(), start.getY(), end.getX(), end.getY());
        g2d.draw(line);

        // Calculate the radius of the circle
        double radius = start.distance(end);

        // Calculate the top-left point of the circle
        double x = start.getX() - radius;
        double y = start.getY() - radius;

        // draw the circle
        Ellipse2D circle = new Ellipse2D.Double(x, y, 2 * radius, 2 * radius);
        g2d.draw(circle);

        g.drawOval((int)x,(int)y,2 * (int)radius, 2* (int)radius);
    }
}

