package renderer;

import java.awt.*;
import java.util.List;

public class Polygon {

    List<Point> vertices;
    PolygonEnvironmentIntf pEIntf;

    public Polygon(List<Point> vertices, PolygonEnvironmentIntf pEIntf) {
        this.vertices = vertices;
        this.pEIntf = pEIntf;
    }

    public void draw(Graphics g) {
        Point temp = vertices.get(vertices.size() - 1);
        for(Point vertex : vertices){
            Point a = pEIntf.getEnvCoordinates(temp);
            Point b = pEIntf.getEnvCoordinates(vertex);
            g.drawLine(a.x, a.y, b.x, b.y);
            temp = vertex;
        }
    }

}
