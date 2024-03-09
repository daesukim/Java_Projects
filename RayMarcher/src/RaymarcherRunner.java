public class RaymarcherRunner extends SwingApplication {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 640;
    private static final int TARGET_FPS = 100;

    private static final String TITLE = "Raymarcher";
    
    public RaymarcherRunner(int width, int height, int fps, String title) {
        super(width, height, fps, title);
        RaymarcherPanel raymarcherPanel = new RaymarcherPanel(this);
        this.addComponent(raymarcherPanel);
        this.packComponents();
        this.setVisible(true);
    }
    
    @Override
    public void run() {
    }
    
    public static void main(String[] args) {
        RaymarcherRunner runner = new RaymarcherRunner(WIDTH, HEIGHT, TARGET_FPS, TITLE);
        runner.run();
    }
}
