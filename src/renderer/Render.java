package renderer;

import environment.Environment;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Render extends Environment {

    //<editor-fold desc="Fields">
    Point reference = new Point(0, 0);
    double theta = 0;

    final int interval = 50; // TODO: add zoom later
    final int DEGREES_IN_CIRCLE = 360;

    Dimension screenSize;
    Point screenCenter;
    //</editor-fold>

    //<editor-fold desc="Init Env">
    @Override
    public void initializeEnvironment() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenCenter = new Point(screenSize.width / 2, screenSize.height / 2);
    }
    //</editor-fold>

    //<editor-fold desc="Handlers">
    @Override
    public void timerTaskHandler() {

    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if(reference != null) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    reference.y--;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    reference.y++;
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    reference.x--;
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    reference.x++;
                    break;

                case KeyEvent.VK_Q:
                    theta++;
                    if (theta >= DEGREES_IN_CIRCLE) {
                        theta -= DEGREES_IN_CIRCLE;
                    }
                    break;
                case KeyEvent.VK_E:
                    theta--;
                    if (theta < 0) {
                        theta += DEGREES_IN_CIRCLE;
                    }
                    break;
            }
        }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {

    }
    //</editor-fold>

    //<editor-fold desc="Environment Mouse Events">
    @Override
    protected void environmentMouseExited(MouseEvent e) {

    }

    @Override
    protected void environmentMouseEntered(MouseEvent e) {

    }

    @Override
    protected void environmentMouseReleased(MouseEvent e) {

    }

    @Override
    protected void environmentMousePressed(MouseEvent e) {

    }

    @Override
    public void environmentMouseDragged(MouseEvent e) {

    }

    @Override
    public void environmentMouseMoved(MouseEvent e) {

    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {

    }
    //</editor-fold>

    //<editor-fold desc="Paint">
    @Override
    public void paintEnvironment(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //<editor-fold defaultstate="collapsed" desc="Antialias">
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//</editor-fold>
        double slope = Math.tan(degreeToRadian(theta));
        Point origin = new Point(screenCenter.x - (interval * reference.x), screenCenter.y - (interval * reference.y));

        g.setFont(new Font("Courier New", Font.PLAIN, 12));
        g.drawString("Angle: " + theta, 5, 12);

        // draw the x axis
        int a = origin.y + (int) (slope * origin.x);
        int b = origin.y + (int) (slope * (origin.x - screenSize.width));
        if(theta == 90 || theta == 270) g.drawLine(screenCenter.x, 0,  screenCenter.x, screenSize.height);
        else g.drawLine(0, a, screenSize.width, b);

        // draw the y axis
        a = origin.x - (int) (slope * origin.y);
        b = origin.x - (int) (slope * (origin.y - screenSize.height));
        if(theta == 90 || theta == 270) g.drawLine(0, screenCenter.y, screenSize.width, screenCenter.y);
        else g.drawLine(a, 0, b, screenSize.height);
    }
    //</editor-fold>

    private double degreeToRadian(double n) {
        return n * Math.PI / 180;
    }
}
