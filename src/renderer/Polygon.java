package renderer;

import java.awt.*;
import java.util.List;

public class Polygon {

    List<Coordinate> vertices;
    PolygonEnvironmentIntf pEIntf;

    public Polygon(List<Coordinate> vertices, PolygonEnvironmentIntf pEIntf) {
        this.vertices = vertices;
        this.pEIntf = pEIntf;
    }

    public void draw(Graphics g) {
        Coordinate temp = vertices.get(vertices.size() - 1);
        for(Coordinate vertex : vertices){
            Point a = pEIntf.getEnvCoordinates(temp);
            Point b = pEIntf.getEnvCoordinates(vertex);
            g.drawLine(a.x, a.y, b.x, b.y);
            temp = vertex;
        }
    }

}
