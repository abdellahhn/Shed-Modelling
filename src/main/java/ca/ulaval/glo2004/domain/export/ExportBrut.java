package ca.ulaval.glo2004.domain.export;
import ca.ulaval.glo2004.domain.*;

import java.util.ArrayList;
import java.util.*;

import ca.ulaval.glo2004.domain.export.ExportSTL;

//import sun.security.provider.SHA;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ExportBrut extends ExportSTL{
    public ExportBrut(Shed chalet){
        super(chalet);
    }
    private static List<Triangle> allTriangles = new ArrayList<>();
    private static List<Triangle> allTriangles_back = new ArrayList<>();
    protected void exporterMur(){

    }
    protected void exporterRallongeToit(){

    }
    protected void exportPignon(){

    }
    protected void exportDessusToit(){

    }

    protected void exportMur(String path){
//        for (Map.Entry<Facade, Wall> wall : chalet.getWallList().entrySet()) {
//            String facade = "";
//            if (wall.getValue().facade.equals(Facade.AVANT)) {
//                facade = "F";
//            } else if (wall.getValue().facade.equals(Facade.ARRIERE)){
//                facade = "A";
//            } else if (wall.getValue().facade.equals(Facade.GAUCHE)) {
//                facade = "G";
//            } else {
//                facade = "D";
//            }
//            // add the triangles
//            List<Triangle> triangles = convertWallsToSTL(wall);
//            System.out.println(wall.getValue().facade.toString());
//
////            Projet_Brut_X.stl (où Projet est remplacé par le nom du projet et X est remplacé par la lettre suivante selon qu’il s’agit
////                    de la Façade (F),
////                    de l’Arrière (A),
////                    du mur Gauche (G),
////                    Droit (D),
////                    du dessus de toit (T),
////                    de la rallonge verticale du mur supportant le toit (R),
////                    du pignon gauche (PG)
////                    ou du pignon droit (PD).
//
//            try {
//                String fileName = path + facade + ".stl";
//                STLWriter.writeSTLFile(fileName, triangles);
//                System.out.println("STL file created successfully at : " + path);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        for (Map.Entry<Facade, Wall> wallEntry : chalet.getWallList().entrySet()) {
            List<Triangle> triangles = convertWallsToSTL(wallEntry);
            String facade = getFacadeLetter(wallEntry.getKey());

            try {
                String fileName = path + facade + ".stl";
                STLWriter.writeSTLFile(fileName, triangles);
                System.out.println("STL file created successfully at : " + path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<Triangle> TrianglesToit = new ArrayList<>();
            //test for pignon
            // facade droite
            //Facade facadeDroite = Facade.DROITE;
            Facade facadeDroite = Facade.ARRIERE;
            if(chalet.getDirection().equals(ToitDirection.BACK_TO_FRONT) || chalet.getDirection().equals(ToitDirection.FRONT_TO_BACK)){
                facadeDroite = Facade.DROITE;
            }
            Map<String, List<Triangle>> mapOfListsTriangles = exporterToit(facadeDroite, chalet);

            for (Map.Entry<String, List<Triangle>> entry : mapOfListsTriangles.entrySet()) {
                String key = entry.getKey();
                List<Triangle> roofTr = entry.getValue();
                try {
                    String fileName = path + key + ".stl";
                    STLWriter.writeSTLFile(fileName, roofTr);
                    System.out.println("STL file created successfully at : " + path);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private List<Triangle> convertWallsToSTL(Map.Entry<Facade, Wall> wallEntry) {
        Facade facade = wallEntry.getKey();
        Wall wall = wallEntry.getValue();

        double depth = chalet.getDepth().convertToDouble();
        double longueur = chalet.getLongueur().convertToDouble();
        double largeur = chalet.getLargeur().convertToDouble();
        double hauteur = chalet.getHauteur().convertToDouble();

        System.out.println("Longueur: "+ longueur);
        System.out.println("Largeur: "+ largeur);
        System.out.println("Hauteur: "+ hauteur);


        ArrayList<ArrayList<Position>> front = wall.getViewPoints().get("front");
        ArrayList<Position> positions;
        if (front.size() > 1)
        { positions = front.get(1); }
        else
        { positions = front.get(0); }


        Vertex[] vertices = generateVertices(positions, facade, longueur, largeur, hauteur, depth);
        Vertex[] vertices_back = generateVerticesBack(positions, facade, depth, longueur, largeur, hauteur);
        List<Triangle> allTriangles = writeRectangleToTriangle(vertices, vertices_back);
        Triangle[] sideTriangles =createSideTriangles(vertices, vertices_back);
        allTriangles.addAll(Arrays.asList(sideTriangles));
        return allTriangles;
    }
    private String getFacadeLetter(Facade facade) {
        switch (facade) {
            case AVANT:
                return "F";
            case ARRIERE:
                return "A";
            case GAUCHE:
                return "G";
            case DROITE:
                return "D";
            default:
                return "";
        }
    }
}

