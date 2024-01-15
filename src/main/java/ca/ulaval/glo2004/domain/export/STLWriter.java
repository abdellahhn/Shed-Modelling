package ca.ulaval.glo2004.domain.export;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class STLWriter {
    public static void writeSTLFile(String filePath, List<Triangle> triangles) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(filePath)))) {
            // Write STL header
            writer.println("solid Shed");

            // Write each triangle
            String facet = "  facet normal 0 0 0";
            for (Triangle triangle : triangles) {

                writer.println(facet);
                writer.println("    outer loop");
                writeVertex(writer, triangle.vertex1);
                writeVertex(writer, triangle.vertex2);
                writeVertex(writer, triangle.vertex3);
                writer.println("    endloop");
                writer.println("  endfacet");
            }

            // Write STL footer
            writer.println("endsolid Shed");
        }
    }
    private static void writeTriangleToSTL(Triangle triangle, StringBuilder stlContent){
        stlContent.append("  facet normal 0 0 1\n");
        stlContent.append("    outer loop\n");
        for (Vertex vertex : triangle.getVertices()) {
            stlContent.append("      vertex ").append(vertex.getX())
                    .append(" ").append(vertex.getY())
                    .append(" ").append(vertex.getZ()).append("\n");
        }
        stlContent.append("    endloop\n").append("  endfacet\n");

        //writer.write("      vertex " + vertex.getX() + " " + vertex.getY() + " " + vertex.getZ() + "\n");

    }

    private static void writeVertex(PrintWriter writer, Vertex vertex) {
        writer.printf("      vertex %f %f %f%n", vertex.getX(), vertex.getY(), vertex.getZ());
    }


}
