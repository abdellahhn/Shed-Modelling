package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.domain.*;
import ca.ulaval.glo2004.drawer.GridDrawer;
import ca.ulaval.glo2004.drawer.ItemDrawer;

import javax.swing.JPanel;
import java.awt.geom.Point2D;

import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.io.Serializable;


/**
 * @author abdellahhanane
 */
public class DrawingPanel
        extends JPanel
        implements MouseListener,
        MouseWheelListener,
        MouseMotionListener,
        KeyListener, Serializable{

    public MainWindow mainWindow;
    public Dimension initialDimension;
    public ItemDrawer itemDrawer;
    private AffineTransform tx = new AffineTransform();
    public double zoomFactor = 1.0;

    private boolean zoomed = false;
    private AffineTransform cumulativeTransform = new AffineTransform();
    private AffineTransform zoomTransform = new AffineTransform();
    // public ZoomHandler zoomHandler;
    public double offsetX = 0;
    public double offsetY = 0;


    public DrawingPanel() {
        initDrawingPanel(null,
                null);
    }


    public DrawingPanel(MainWindow mainWindow,
                        Controller controller) {
        initDrawingPanel(mainWindow,
                controller);
    }

    private void initDrawingPanel(final MainWindow mainWindow,
                                  Controller controller) {

        this.mainWindow = mainWindow;
        this.itemDrawer = new ItemDrawer(controller);

        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double oldZoomFactor = zoomFactor;
                double rotation = e.getPreciseWheelRotation();
                zoomFactor *= (rotation < 0) ? 1.05 : 0.95;

                double factor = zoomFactor / oldZoomFactor;
                offsetX += (e.getX() - offsetX) * (1 - factor);
                offsetY += (e.getY() - offsetY) * (1 - factor);

                mainWindow.offsetX = offsetX;
                mainWindow.offsetY = offsetY;
                mainWindow.echelle = zoomFactor;
                repaint();
            }
        });


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int windowX = e.getX();
                int windowY = e.getY();
                
                mainWindow.offsetX = offsetX;
                mainWindow.offsetY = offsetY;
                mainWindow.echelle = zoomFactor;

                ValeurImperiale upX = rescalee(windowX, getFacteur(), getOffsetX());
                ValeurImperiale upY = rescalee(windowY, getFacteur(), getOffsetY());

                Position windowPos = new Position(upX, upY);
                repaint();
                
            }
        });

        if (mainWindow != null) {

            int width = (int) (java.awt.Toolkit.getDefaultToolkit().
                    getScreenSize().width);
            setPreferredSize(new Dimension(width,
                    1));
            setVisible(true);
            int height = (int) (width * 0.5);
            initialDimension = new Dimension(width,
                    height);

        }

    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gg = (Graphics2D) g;

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Apply zoom and offset transform
        AffineTransform transform = new AffineTransform();
        transform.translate(offsetX, offsetY);
        transform.scale(zoomFactor, zoomFactor);
        g2.transform(transform);
        if (this.mainWindow != null) {
            super.paintComponent(g);
            if (this.mainWindow.isGrille){
                GridDrawer grid = new GridDrawer();
                grid.drawGrid(g, mainWindow.spaceGrill);
            }

            int vue = 8;

            String objectToPaint = this.mainWindow.getObjectToPaint();
            if (objectToPaint.startsWith("Mur")) {
                switch (objectToPaint) {
                    case "MurFace":
                        vue = 1;
                        itemDrawer.dessinerPoly(g, this.mainWindow.controller.infoPourAfficherLaVue(vue));
                        break;
                    case "MurArriere":
                        vue = 2;
                        itemDrawer.dessinerPoly(g, this.mainWindow.controller.infoPourAfficherLaVue(vue));
                        break;
                    case "MurGauche":
                        vue = 3;
                        itemDrawer.dessinerPoly(g, this.mainWindow.controller.infoPourAfficherLaVue(vue));
                        break;
                    case "MurDroite":
                        vue = 4;
                        itemDrawer.dessinerPoly(g, this.mainWindow.controller.infoPourAfficherLaVue(vue));
                        break;
                }
            } else if (objectToPaint.startsWith("Vue")) {
                vue = 0;
                itemDrawer.dessinerPoly(g, this.mainWindow.controller.infoPourAfficherLaVue(vue));
            }
        }
//        gg.transform(zoomHandler.getTransform());
//        gg.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void resetZoom() {
        zoomFactor = 1.0;
        offsetX = 0;
        offsetY = 0;
        this.repaint();
    }

    public boolean isZoom() {
        return zoomed;
    }

    public double getFacteur() {
        return zoomFactor;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public ValeurImperiale rescalee(double position, double newScale, double offset) {
        // Calculate the updated position based on the new scale
        ValeurImperiale impNewScale = ValeurImperiale.convertDoubleToValeurImperiale(newScale);
//        System.out.println("nv scale rescalee met" + impNewScale);
//                System.out.println(impNewScale);
        ValeurImperiale off = new ValeurImperiale(offset);
//                System.out.println("offset valImper rescale" + off);

        double adjusted = (int)((position - offset) / newScale);

        ValeurImperiale viAdjusted = ValeurImperiale.convertDoubleToValeurImperiale(adjusted);
//                System.out.println(impNewScale);
//        ValeurImperiale adjustedPosX = position.substract(off).divide(impNewScale);
//        System.out.println("position ajuste" + viAdjusted);

        // Return the updated position
        return viAdjusted;
    }


}

