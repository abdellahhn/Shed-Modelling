package ca.ulaval.glo2004.drawer;
import ca.ulaval.glo2004.domain.Polygone;
import ca.ulaval.glo2004.domain.Position;


import java.awt.Polygon;

import java.awt.Graphics;
import java.awt.*;
import java.util.ArrayList;


import java.awt.Graphics2D;

import ca.ulaval.glo2004.domain.Controller;
import java.io.Serializable;


/**
 *
 * @author abdellahhanane
 */
public class ItemDrawer implements Serializable{

    private Controller controller;
    private Dimension initialdimension;
    String objtToPaint;

    public ItemDrawer(Controller controller){
        this.controller = controller;
    }

    public void startDraw(Graphics2D g){
        g.setStroke(new BasicStroke(5));
        g.setColor(Color.BLACK);
//        dessinerPoly(g,controller.infoPourAfficherLaVue(vueSelectionne));
    }

    public void dessinerPoly(Graphics g, ArrayList<Polygone> p_liste) {
        for (Polygone p : p_liste) {
            if (p != null) {
                Polygon s = new Polygon();

                for (Position position : p.getListePointsImperiaux()) {
                    // Convert the Position to pixels using the imperialeToPixel method
                    int x = position.getX().imperialeToPixel();
                    int y = position.getY().imperialeToPixel();

                    s.addPoint(x, y);
                }

                g.setColor(Color.BLACK); // Set the border color to black
                g.drawPolygon(s); // Draw the border of the polygon

                g.setColor(p.getColor()); // Set the fill color to the polygon's color

                // Close the shape by adding the first point again to the end
                s.addPoint(s.xpoints[0], s.ypoints[0]);

                g.fillPolygon(s); // Fill the polygon
            }
        }
    }
}
