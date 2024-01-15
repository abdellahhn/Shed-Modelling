package ca.ulaval.glo2004.domain.export;

import java.util.ArrayList;

public class Triangle {
    public Vertex vertex1;
    public Vertex vertex2;
    public Vertex vertex3;

    public String normal;

    public Triangle(Vertex v1, Vertex v2, Vertex v3) {
        this.vertex1 = v1;
        this.vertex2 = v2;
        this.vertex3 = v3;
    }

    public Vertex[] getVertices(){
        Vertex[] vertices = {vertex1, vertex2, vertex3};
        return vertices;


    }
}

