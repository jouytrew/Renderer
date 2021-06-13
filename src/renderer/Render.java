package renderer;

import environment.Environment;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class Render extends Environment implements PolygonEnvironmentIntf{

    //<editor-fold desc="Fields">
    Coordinate reference = new Coordinate(0.0, 0.0);
    double theta = 0;

    double zoom = 1;
    double panDelta = 5;
    final int DEGREES_IN_CIRCLE = 360;

    Dimension screenSize;
    Point screenCenter;

    List<Polygon> polygons;
    //</editor-fold>

    //<editor-fold desc="Init Env">
    @Override
    public void initializeEnvironment() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenCenter = new Point(screenSize.width / 2, screenSize.height / 2);

        polygons = new ArrayList<>();

        List<Coordinate> Q1 = new ArrayList<>();
        Q1.add(new Coordinate(100,100));
        Q1.add(new Coordinate(200,100));
        Q1.add(new Coordinate(100,200));
        polygons.add(new Polygon(Q1, this));

//        List<Point> Q2 = new ArrayList<>();
//        Q2.add(new Point(-1,1));
//        Q2.add(new Point(-2,1));
//        Q2.add(new Point(-1,2));
//        polygons.add(new Polygon(Q2, this));
//
//        List<Point> Q4 = new ArrayList<>();
//        Q4.add(new Point(1,-1));
//        Q4.add(new Point(2,-1));
//        Q4.add(new Point(1,-2));
//        polygons.add(new Polygon(Q4, this));
//
//        List<Point> Q3 = new ArrayList<>();
//        Q3.add(new Point(-1,-1));
//        Q3.add(new Point(-2,-1));
//        Q3.add(new Point(-1,-2));
//        polygons.add(new Polygon(Q3, this));
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
                    reference.y += (panDelta / zoom * Math.cos(degreeToRadian(theta)));
                    reference.x += (panDelta / zoom * Math.sin(degreeToRadian(theta)));
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    reference.y -= panDelta / zoom * Math.cos(degreeToRadian(theta));
                    reference.x -= panDelta / zoom * Math.sin(degreeToRadian(theta));
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    reference.x -= panDelta / zoom * Math.cos(degreeToRadian(theta)) ;
                    reference.y += panDelta / zoom * Math.sin(degreeToRadian(theta));
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    reference.x += panDelta / zoom * Math.cos(degreeToRadian(theta));
                    reference.y -= panDelta / zoom * Math.sin(degreeToRadian(theta));
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

                case KeyEvent.VK_R:
                    reference.x = 0;
                    reference.y = 0;
                    theta = 0;
                    break;

                case KeyEvent.VK_Z:
                    zoom *= 2.0;
                    break;
                case KeyEvent.VK_X:
                    zoom /= 2.0;
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

        Point origin = getEnvCoordinates(new Coordinate(0, 0));

        g.setFont(new Font("Courier New", Font.PLAIN, 12));
        g.drawString("Theta: " + theta + " / " + degreeToRadian(theta) + " radians", 5, 12);
        g.drawString("Reference: (" + reference.x + ", " + reference.y + ")", 5, 24);

        double slope = Math.tan(degreeToRadian(theta));

        // draw the origin
        int circleRadius = 5; // temp value
        g.drawOval(origin.x - circleRadius, origin.y - circleRadius, 2 * circleRadius, 2 * circleRadius);

        // TODO: Build functions to draw lines and vertical lines
        // draw the x axis
        int a = origin.y + (int) (slope * origin.x);
        int b = origin.y + (int) (slope * (origin.x - screenSize.width));

        if (theta == 90 || theta == 270) g.drawLine((int)(origin.x - reference.x) , 0,  (int)(origin.x - reference.x), screenSize.height);
        else g.drawLine(0, a, screenSize.width, b);

        // draw the y axis
        a = origin.x - (int) (slope * origin.y);
        b = origin.x - (int) (slope * (origin.y - screenSize.height));

        if(theta == 90 || theta == 270) g.drawLine(0, (int)(origin.y - reference.y), screenSize.width, (int)(origin.y - reference.y));
        else g.drawLine(a, 0, b, screenSize.height);

        if (polygons != null) {
            for (Polygon polygon : polygons) {
                polygon.draw(g);
            }
        }
    }
    //</editor-fold>

    /**
     * converts degrees to radians
     * @param n a value in degrees
     * @return the converted value in radians
     */
    private double degreeToRadian(double n) {
        return n * Math.PI / 180;
    }

    //<editor-fold desc="Polygon Environment Interface">
    /**
     * Returns the screen draw coordinates for a given point within the cartesian coordinate system
     * @param coordinate cartesian coordinates which need to be translated
     * @return the screen coordinates of the input
     */
    @Override
    public Point getEnvCoordinates(Coordinate coordinate) {
        Coordinate relativeCoordinates = new Coordinate(coordinate.x - reference.x, coordinate.y - reference.y);
        if ((relativeCoordinates.x == 0.0) && (relativeCoordinates.y == 0.0)) {
            return screenCenter;
        } else {
            double h = Math.sqrt(Math.pow(relativeCoordinates.x, 2) + Math.pow(relativeCoordinates.y, 2)); // Pythagoras X^2 + Y^2 = H^2
            double phi = Math.atan(relativeCoordinates.y / relativeCoordinates.x) + degreeToRadian(relativeCoordinates.x >= 0 ? 0 : 180);
            double combinedAngle = phi + degreeToRadian(theta);
            Point drawCoordinates = new Point(screenCenter.x + (int) (zoom * h * Math.cos(combinedAngle)),
                    screenCenter.y - (int) (zoom * h * Math.sin(combinedAngle)));
            return drawCoordinates;
        }
    }
    //</editor-fold>
}
