package ca.ulaval.glo2004.drawer;
import ca.ulaval.glo2004.domain.ValeurImperiale;

import java.awt.*;
import java.io.Serializable;


public class GridDrawer implements Serializable{

    public GridDrawer(){
    }

    public void drawGrid(Graphics g, ValeurImperiale spaceValeurImperiale) {
        g.setColor(Color.LIGHT_GRAY);

        // Convert ValeurImperiale to pixels for grid spacing
        int size_c = spaceValeurImperiale.imperialeToPixel();

        // Assuming the grid should be drawn within a certain range
        for (int i = -1000; i < 1000; i++) {
            int pos = i * size_c;
            g.drawLine(-12000, pos, 12000, pos);
            g.drawLine(pos, -12000, pos, 12000);
        }
    }
}
