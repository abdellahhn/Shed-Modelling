package ca.ulaval.glo2004.domain.export;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class Rectangle extends java.awt.Rectangle {
    public Vertex vertex1;
    public Vertex vertex2;
    public Vertex vertex3;

    public Vertex vertex4;

    public double height;

    public double width;

    public double x;
    public double y;
    public Color color; // Color for filling the rectangle
    public String normal;

    public Rectangle(double x, double y, double width, double height, Color fillColor) {
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        this.color = fillColor;
        this.vertex1 = new Vertex(x, y, 0);
        this.vertex2 = new Vertex(x + width, y, 0);
        this.vertex3 = new Vertex(x + width, y + height, 0);
        this.vertex4 = new Vertex(x, y + height, 0);
    }

    public Rectangle(Vertex v1, Vertex v2, Vertex v3, Vertex v4) {
        this.vertex1 = v1;
        this.vertex2 = v2;
        this.vertex3 = v3;
        this.vertex4 = v4;

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
    public void draw(Graphics2D g2d) {
        Vertex[] vertices = getVertices();
        int[] xPoints = new int[vertices.length];
        int[] yPoints = new int[vertices.length];

        for (int i = 0; i < vertices.length; i++) {
            xPoints[i] = (int) vertices[i].getX();
            yPoints[i] = (int) vertices[i].getY();
        }

        g2d.setColor(getColor());
        g2d.drawPolygon(xPoints, yPoints, vertices.length);
    }

    public Rectangle(double x, double y, double width, double height) {
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        this.vertex1 = new Vertex(x, y, 0);                         // top left
        this.vertex2 = new Vertex(x+width, y, 0);               // top right
        this.vertex3 = new Vertex(x+width, y+height, 0);    // bottom right
        this.vertex4 = new Vertex(x, y+height, 0);            // bottom left


    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public double getWidth(){
        return this.width;
    }
    public double getHeight(){
        return this.height;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color =  color;
    }


    public Vertex[] getVertices() {
        Vertex[] vertices = {vertex1, vertex2, vertex3, vertex4};
        return vertices;


    }
}