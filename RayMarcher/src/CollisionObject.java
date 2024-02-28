import java.awt.*;
import java.util.Random;

public abstract class CollisionObject implements Drawable{
    private double x;
    private double y;
    private Color color;


    public CollisionObject(double x, double y){
        this.x = x;
        this.y = y;
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        this.color = new Color(r, g, b);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract double computeDistance(double cameraX, double cameraY);
}
