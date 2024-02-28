import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

/**
 * Displays and updates the logic for the top-down raymarcher.
 */
public class RaymarcherPanel extends JPanel { 
    
    /**
     * We need to keep a reference to the parent swing app for sizing and 
     * other bookkeeping.
     */
    private final RaymarcherRunner raymarcherRunner;
    private final ArrayList<CollisionObject> objects;
    private final Camera cam;
    
    public RaymarcherPanel(RaymarcherRunner raymarcherRunner) {
        this.raymarcherRunner = raymarcherRunner;
        this.setPreferredSize(new Dimension(this.raymarcherRunner.getFrame().getWidth(),
                this.raymarcherRunner.getFrame().getHeight()));

        Random random = new Random();
        int numberOfObjects = random.nextInt(15) + 1;
        this.objects = new ArrayList<>(numberOfObjects);

        this.cam = new Camera(0,0);
        addMouseMotionListener(cam);
        addMouseListener(cam);

        for (int i = 0; i < numberOfObjects; i++){
            int randomShape = random.nextInt(2);
            if (randomShape == 1){
                double randomWidth = random.nextDouble() * 300;
                double randomHeight = random.nextDouble() * 300;
                double randomX = random.nextDouble() * (getPreferredSize().width - randomWidth);
                double randomY = random.nextDouble() * (getPreferredSize().height - randomHeight);
                CollisionObject object = new RectangleObject(randomX, randomY, randomWidth, randomHeight);
                objects.add(object);
            }
            else{
                double randomRadius = random.nextDouble() * 150;
                double randomX = random.nextDouble() * (getPreferredSize().width - randomRadius);
                double randomY = random.nextDouble() * (getPreferredSize().height - randomRadius);
                CollisionObject object = new CircleObject(randomX, randomY, randomRadius);
                objects.add(object);
            }
        }
    }

    public ArrayList<March> march(){

        ArrayList<March> marches = new ArrayList<>();

        double distance_initiator = 1000;
        double currentX = cam.getX();
        double currentY = cam.getY();
        double endX = 0;
        double endY = cam.getY();


       do{
           distance_initiator = 1000;

            for (int i = 0; i < objects.size(); i++) {
                double distance = objects.get(i).computeDistance(currentX, currentY);
                if (distance < distance_initiator) {
                    distance_initiator = distance;
                    //endX = currentX + distance_initiator;
                    endX = currentX + distance_initiator * Math.cos(cam.getAngle());
                    endY = currentY + distance_initiator * Math.sin(cam.getAngle());
                }

            }

            if (distance_initiator > 0.01) {
                marches.add(new March(new Point2D.Double(currentX, currentY), new Point2D.Double(endX, endY)));
                currentX = endX;
                currentY = endY;
            }
            else{
                break;
            }

        } while (distance_initiator > 0.01 && currentX < this.getWidth() && currentY < this.getHeight());


        return marches;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);

        double distance_initiator = 500;

        for (int i = 0; i < objects.size(); i++){
            CollisionObject shape = objects.get(i);
            shape.drawObject(g2d);
            double distance = shape.computeDistance(cam.getX(), cam.getY());
            if (distance < distance_initiator){
                distance_initiator = distance;
            }
        }

        cam.drawObject(g2d);
        //Ellipse2D CameraBound = new Ellipse2D.Double(cam.getX() - distance_initiator, cam.getY() - distance_initiator, distance_initiator * 2, distance_initiator * 2);
        //g2d.draw(CameraBound);

        ArrayList<March> marches = march();
        for (int i = 0; i < marches.size(); i++){
            marches.get(i).draw(g2d);
        }
    }
}
