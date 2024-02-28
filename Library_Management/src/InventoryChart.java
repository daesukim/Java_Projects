import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class InventoryChart extends JComponent {
    private int[] stats;
    private String title;

    public InventoryChart(int[] stats, String title){
        this.stats = stats;
        this.title = title;
    }

    public int[] getStats() {
        return stats;
    }

    public String getTitle() {
        return title;
    }

    public void setStats(int[] stats) {
        this.stats = stats;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void paintComponent(Graphics g){
        int totalNum = stats[0] + stats[1];
        double fictionHeight = (double) stats[0] / totalNum * 440;
        double nonfictionHeight = (double) stats[1] / totalNum * 440;
        int height = 0;
        g.setColor(Color.ORANGE);
        g.fillRect(20,20,360, (int) fictionHeight);
        height = (int) fictionHeight + 20;
        g.setColor(Color.green);
        g.fillRect(20, height, 360, (int) nonfictionHeight);
        g.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 20);
        g.setFont(font);
        g.drawString("Fiction Book: " + stats[0], 110, height/2 + 20);
        g.drawString("Nonfiction Book: " + stats[1], 95, height/2 + 40 + (int)nonfictionHeight);
    }
}
