package ca.ulaval.glo2004.domain.export;
import ca.ulaval.glo2004.domain.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class ExportFini extends ExportSTL {
    public ExportFini(Shed chalet) {
        super(chalet);
    }

    public void export(String path){
        exportMur(path);
    }

    public void exportMur(String path) {
        double longeur_chal = chalet.getLongueur().convertToDouble();
        double largeur_chal = chalet.getLargeur().convertToDouble();
        for (Map.Entry<Facade, Wall> wall : chalet.getWallList().entrySet()) {
            String facade;

            if (wall.getValue().facade.equals(Facade.AVANT)) {
                facade = "F";
            } else if (wall.getValue().facade.equals(Facade.ARRIERE)) {
                facade = "A";
            } else if (wall.getValue().facade.equals(Facade.GAUCHE)) {
                facade = "G";
            } else {
                facade = "D";
            }
            Facade facade2 = wall.getKey();
            Wall wall3 = chalet.getWallList().get(facade2);




//            // create small rectangles
            List<Rectangle> small_rectangles_list = writeWallFiniToRectangles(wall.getKey(), chalet);
            // Faire copie de small rectangles list et enlever les small rectangle correspondant a rainure

            //List<Rectangle> small_rectangles_list_copie = enlever_reinnure_stl(small_rectangles_list);
            //small_rectangles_list.addAll(small_rectangles_list_copie);


// here you wanted to remove all small rectangles that represent the rainures
//but ended up removing those in the front too, it a trou live
/*
            List<Rectangle> rectanglesToRemoveRainure = new ArrayList<>();
            for (Rectangle small_rectangle: small_rectangles_list){
                if (small_rectangle.vertex1.x<chalet.getDepth().convertToDouble()){
                    rectanglesToRemoveRainure.add(small_rectangle);
                }
            }
            for (Rectangle rect2 : rectanglesToRemoveRainure) {
                double x2 = rect2.getX();
                double y2 = rect2.getY();

                // Iterate through the first list and remove rectangles with the same x and y as rect2
                for (int i = 0; i < small_rectangles_list.size(); i++) {
                    Rectangle rect1 = small_rectangles_list.get(i);
                    if (rect1.getX() == x2 && rect1.getY() == y2) {
                        small_rectangles_list.remove(i);
                        i--; // Decrement i to handle shifting of elements after removal
                    }
                }

            }*/



            List<Triangle> All_triangles_list = writeRectangleToTriangle(small_rectangles_list, chalet.getDepth().convertToDouble(), wall.getKey(), longeur_chal, largeur_chal);


            // Create Wall Sides
            // creating the side walls will be different too, here we have to add a functionality so that the left and right sides should cover les rainures
            // To cover les rainures, we gotta have 3 rectangles on each side these 3  __/--
            // Top and Bottom sides should be changes too, so basically we will have two rectangles for each, one the same as usual with 1/2 width and one with smaller longueur and 1/2 width too
            // Solution is writing a new createSideTriangles function to stl fini, this one will implement the same features as the one before but
            ArrayList<ArrayList<Position>> front = wall3.getViewPoints().get("front");
            ArrayList<Position> positions;
            if (front.size() > 1)
            { positions = front.get(1); }
            else
            { positions = front.get(0); }
            Vertex[] vertices = generateVertices(positions, facade2, longeur_chal, largeur_chal, chalet.getHauteur().convertToDouble(), chalet.getDepth().convertToDouble());
            Vertex[] vertices_back = generateVerticesBack(positions, facade2, chalet.getDepth().convertToDouble(),longeur_chal, largeur_chal, chalet.getHauteur().convertToDouble());
            Triangle[] Wall_sideTriangles =createSideTriangles(vertices, vertices_back);

            for (Triangle triangle : Wall_sideTriangles) {
                All_triangles_list.add(triangle);
            }

            // Create accessories sides
            List<Triangle> AccessoriesSides = generateAccessoriesSideTriangles(wall3.getAccessories(),chalet.getDepth().convertToDouble(), wall.getKey(), longeur_chal, largeur_chal, chalet.getHauteur().convertToDouble());
            All_triangles_list.addAll(AccessoriesSides);
            // Create
            double hauteur_chal = chalet.getHauteur().convertToDouble();
            double depth_chal = chalet.getDepth().convertToDouble();


            //List<Triangle> triangles = convertWallsToSTL(wall);
//            Projet_Brut_X.stl (où Projet est remplacé par le nom du projet et X est remplacé par la lettre suivante selon qu’il s’agit
//                    de la Façade (F),
//                    de l’Arrière (A),
//                    du mur Gauche (G),
//                    Droit (D),
//                    du dessus de toit (T),
//                    de la rallonge verticale du mur supportant le toit (R),
//                    du pignon gauche (PG)
//                    ou du pignon droit (PD).

            try {
                String fileName = path + facade + ".stl";
                STLWriter.writeSTLFile(fileName, All_triangles_list);
                System.out.println("STL file created successfully at : " + path);

            } catch (IOException e) {
                e.printStackTrace();
            }



        }


        List<Triangle> TrianglesToit = new ArrayList<>();
        //test for pignon
        // facade droite
        Facade facadeDroite = Facade.ARRIERE;
        if(chalet.getDirection().equals(ToitDirection.BACK_TO_FRONT) || chalet.getDirection().equals(ToitDirection.FRONT_TO_BACK)){
            facadeDroite = Facade.DROITE;
        }
        //Facade facadeDroit = Facade.DROITE;
        Map<String, List<Triangle>> mapOfListsTriangles = exporterToit(facadeDroite, chalet);

        for (Map.Entry<String, List<Triangle>> entry : mapOfListsTriangles.entrySet()) {
            String key = entry.getKey();
            List<Triangle> triangles = entry.getValue();
            try {
                String fileName = path + key + ".stl";
                STLWriter.writeSTLFile(fileName, triangles);
                System.out.println("STL file created successfully at : " + path);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        }

        protected void exportRallongeToit(String path) {
        List<Triangle> triangles = convertRallongeToSTL();
        try {
            String fileName = path + "R" + ".stl";
            STLWriter.writeSTLFile(fileName, triangles);
            System.out.println("STL file created successfully at : " + path);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected Map<String, List<Triangle>> exporterToit(Facade facade, Shed chalet) {
        List<Triangle> Triangles = new ArrayList<>();
/*
        if (facade.equals(Facade.ARRIERE)||facade.equals(Facade.AVANT)||facade.equals(Facade.GAUCHE)){
            //return Triangles;
        }*/

        //Map<Facade, Wall> facadeWallMap = chalet.getWallList();
        //Wall wall = facadeWallMap.get(facade);

        Roof leftRoof = chalet.getRoofList().get(facade);
        ArrayList<ArrayList<Position>> leftRoofPoints = leftRoof.getViewPoints().get("roof");

        ToitDirection orientation = chalet.getDirection();
        for (ArrayList<Position> roofView : leftRoofPoints) {
            System.out.println("list of points: ");
            System.out.println(facade);
            System.out.println(orientation);
            for (Position pos : roofView) {
                double x = pos.getX().convertToDouble();
                double y = pos.getY().convertToDouble();
                System.out.println("("+ x +" ; "+y+")");
            }
        }

        List<Triangle> pinocchio = createPignonT(leftRoofPoints.get(0), chalet.getLargeur().convertToDouble(), chalet.getLongueur().convertToDouble(), chalet.getAngle(), chalet.getHauteur().convertToDouble(), chalet.getDepth().convertToDouble(), orientation);
        List<Triangle> pinocchio2 = createRallonge(leftRoofPoints.get(1), chalet.getLargeur().convertToDouble(), chalet.getLongueur().convertToDouble(), chalet.getAngle(), chalet.getHauteur().convertToDouble(), chalet.getDepth().convertToDouble(), orientation);
        List<Triangle> pinocchio3 = createCover(leftRoofPoints.get(2), chalet.getLargeur().convertToDouble(), chalet.getLongueur().convertToDouble(), chalet.getAngle(), chalet.getHauteur().convertToDouble(), chalet.getDepth().convertToDouble(), orientation);


        /*
        ArrayList<ArrayList<Position>> front = wall.getViewPoints().get("front");
        ArrayList<Position> positions;
        if (front.size() > 1)
        { positions = front.get(1); }
        else
        { positions = front.get(0); }

        for (Position pos : positions) {
            System.out.println(facade);
            System.out.println("Point");
            double x = pos.getX().convertToDouble();
            double y = pos.getY().convertToDouble();
            System.out.println("("+ x +" ; "+y+")");
        }
        */
        Map<String, List<Triangle>> mapOfLists = new HashMap<>();

        mapOfLists.put("pignon", pinocchio);
        mapOfLists.put("rallonge", pinocchio2);
        mapOfLists.put("cover", pinocchio3);


        //Triangles.addAll(pinocchio);
        //Triangles.addAll(pinocchio2);
        //Triangles.addAll(pinocchio3);

        return mapOfLists;
    }

    protected List<Triangle> createCover(ArrayList<Position> positions, double largeur, double wallLongueur, double angle, double wallHauteur, double depth, ToitDirection directionToit) {
        List<Triangle> Triangles = new ArrayList<>();
        double angleRad = Math.toRadians(angle);
        double hauteurRal = Math.tan(angleRad) * largeur;

        double a = 0;
        double h = 0;
        for (int i =0; i<2; i++) {

            if (i == 0) {
                a = 0;
                h = 0;
            }
            if (i == 1) {
                a = depth / 2;
                h = hauteurRal - depth/2;
            }
            if (directionToit.equals(ToitDirection.RIGHT_TO_LEFT)  || directionToit.equals(ToitDirection.LEFT_TO_RIGHT)){
                wallLongueur = largeur;
            }
            double topX = 0;
            Vertex[] vertices = new Vertex[5];
            int index = 0;
            for (Position pos : positions) {

                double x = pos.getX().convertToDouble()+a;
                if (x>topX){ topX = x;}
                if (i==1){
                    if (x>2){x = pos.getX().convertToDouble()-a; }
                }

                double y = a;
                double z = 0;
                if (pos.getY().convertToDouble() < 0) {
                    z = wallHauteur - pos.getY().convertToDouble()-a*3/4;///2;
                } else {
                    z = wallHauteur - pos.getY().convertToDouble();
                }
                if (directionToit.equals(ToitDirection.LEFT_TO_RIGHT)){
                    double temp2 = x;
                    x = y;
                    y = chalet.getLongueur().convertToDouble()-temp2;
                }
                if (directionToit.equals(ToitDirection.RIGHT_TO_LEFT)){
                    double temp = x;
                    x = y;
                    y = chalet.getLongueur().convertToDouble()-temp;
                }
                vertices[index] = new Vertex(x, y, z);
                index++;
            }
            Triangle Tr1 = new Triangle(vertices[1], vertices[2], vertices[0]);
            Triangle Tr2 = new Triangle(vertices[4], vertices[2], vertices[0]);
            Triangle Tr3 = new Triangle(vertices[4], vertices[2], vertices[3]);
            Triangles.add(Tr1);
            Triangles.add(Tr2);
            Triangles.add(Tr3);

            Vertex[] verticesBack = new Vertex[5];
            int index2 = 0;
            for (Position pos : positions) {
                //double x = pos.getX().convertToDouble();
                double x = pos.getX().convertToDouble()+a;
                if (x>topX){ topX = x;}
                if (i==1){
                    // here 2 represent the minimum value for x in general so that it is considered from the other side and we have to remove the depth not add it
                    if (x>2){x = pos.getX().convertToDouble()-a;
                    System.out.println("top x value is"+x);}
                }
                double y = wallLongueur-a;
                double z = 0;
                if (pos.getY().convertToDouble() < 0) {
                    z = wallHauteur - pos.getY().convertToDouble()-a*3/4;///2;
                } else {
                    z = wallHauteur - pos.getY().convertToDouble();
                }
                if (directionToit.equals(ToitDirection.RIGHT_TO_LEFT)){
                    double temp = x;
                    // here y goes from O to depth since the x is the longueur - depth
                    x = y;
                    y = chalet.getLongueur().convertToDouble()-temp;
                }
                if (directionToit.equals(ToitDirection.LEFT_TO_RIGHT)){
                    double temp2 = x;
                    x = y;
                    y = chalet.getLongueur().convertToDouble()-temp2;
                }

                verticesBack[index2] = new Vertex(x, y, z);
                index2++;
            }
            Triangle Tr1B = new Triangle(verticesBack[1], verticesBack[2], verticesBack[0]);
            Triangle Tr2B = new Triangle(verticesBack[4], verticesBack[2], verticesBack[0]);
            Triangle Tr3B = new Triangle(verticesBack[4], verticesBack[2], verticesBack[3]);
            Triangles.add(Tr1B);
            Triangles.add(Tr2B);
            Triangles.add(Tr3B);

            // Creating the Top view of the Cover
            Triangle Tr1BTop = new Triangle(vertices[1], verticesBack[1], verticesBack[2]);
            Triangle Tr2BTop = new Triangle(vertices[1], vertices[2], verticesBack[2]);
            Triangles.add(Tr1BTop);
            Triangles.add(Tr2BTop);

            // Creating the Bot view of the Cover
            Triangle Tr1Bot = new Triangle(vertices[0], verticesBack[0], verticesBack[4]);
            Triangle Tr2Bot = new Triangle(vertices[0], vertices[4], verticesBack[4]);
            Triangles.add(Tr1Bot);
            Triangles.add(Tr2Bot);

            // Creating the Rear view of the Cover
            Triangle Tr1BR = new Triangle(vertices[0], verticesBack[0], verticesBack[1]);
            Triangle Tr2BR = new Triangle(vertices[0], vertices[1], verticesBack[1]);
            Triangles.add(Tr1BR);
            Triangles.add(Tr2BR);

            // Creating the Front view of the Cover
            Triangle Tr1BF = new Triangle(vertices[2], verticesBack[2], verticesBack[3]);
            Triangle Tr2BF = new Triangle(vertices[2], vertices[3], verticesBack[3]);
            Triangles.add(Tr1BF);
            Triangles.add(Tr2BF);

            // Creating the bot view of the Cover
            Triangle Tr1BBB = new Triangle(vertices[3], verticesBack[3], verticesBack[4]);
            Triangle Tr2BBB = new Triangle(vertices[3], vertices[4], verticesBack[4]);
            Triangles.add(Tr1BBB);
            Triangles.add(Tr2BBB);
        }

        return Triangles;
    }
    protected List<Triangle> createRallonge(ArrayList<Position> positions, double largeur, double wallLongueur, double angle, double wallHauteur, double depth, ToitDirection directionToit){
        List<Triangle> Triangles = new ArrayList<>();
        double angleRad = Math.toRadians(angle);
        double hauteurRal= Math.tan(angleRad)*largeur;
        double a = 0;
        double b =0;
        for (int i =0; i<2; i++){

            if (i==0){ a =0;}
            if (i==1){ a =depth/2;}
            if (i==0){ b =0;}
            if (i==1){ b =depth/2;}
            if (directionToit.equals(ToitDirection.FRONT_TO_BACK)  || directionToit.equals(ToitDirection.LEFT_TO_RIGHT)){
                a = -a;
            }

            if (directionToit.equals(ToitDirection.RIGHT_TO_LEFT)  || directionToit.equals(ToitDirection.LEFT_TO_RIGHT)){
                wallLongueur = largeur;
            }


            Vertex[] vertices= new Vertex[4];
            int index = 0;
            for (Position pos : positions) {
                double x = pos.getX().convertToDouble()-a;
                double y = b;
                double z = 0;

                if (pos.getY().convertToDouble() < 0) {
                    z = wallHauteur - pos.getY().convertToDouble()-b*3/4;//-b;///2;
                } else {
                    z = wallHauteur - pos.getY().convertToDouble();
                }
                if (directionToit.equals(ToitDirection.LEFT_TO_RIGHT)){
                    double temp2 = x;
                    x = y;
                    y = chalet.getLongueur().convertToDouble()-temp2;
                }
                if (directionToit.equals(ToitDirection.RIGHT_TO_LEFT)){
                    double temp = x;
                    x = y;
                    y = chalet.getLongueur().convertToDouble()-temp;
                }
                vertices[index] = new Vertex(x, y, z);
                index++;
            }
            Triangle Tr1 = new Triangle(vertices[0], vertices[1], vertices[3]);
            Triangle Tr2 = new Triangle(vertices[2], vertices[1], vertices[3]);
            Triangles.add(Tr1);
            Triangles.add(Tr2);

            Vertex[] verticesBack= new Vertex[4];
            int index2 = 0;
            for (Position pos : positions) {
                double x = pos.getX().convertToDouble()-a;
                double y = wallLongueur-b;
                double z = 0;
                if (pos.getY().convertToDouble() < 0) {
                    z = wallHauteur - pos.getY().convertToDouble()-b*3/4;///2;
                } else {
                    z = wallHauteur - pos.getY().convertToDouble();
                }

                if (directionToit.equals(ToitDirection.RIGHT_TO_LEFT)){
                    double temp = x;
                    // here y goes from O to depth since the x is the longueur - depth
                    x = y;
                    y = chalet.getLongueur().convertToDouble()-temp;
                }
                if (directionToit.equals(ToitDirection.LEFT_TO_RIGHT)){
                    double temp2 = x;
                    x = y;
                    y = chalet.getLongueur().convertToDouble()-temp2;
                }
                verticesBack[index2] = new Vertex(x, y, z);
                index2++;
            }
            Triangle Tr1B = new Triangle(verticesBack[0], verticesBack[1], verticesBack[3]);
            Triangle Tr2B = new Triangle(verticesBack[2], verticesBack[1], verticesBack[3]);
            Triangles.add(Tr1B);
            Triangles.add(Tr2B);

            Triangle[] SideTriangles =  createSideTriangles (vertices, verticesBack);
            for (Triangle trg: SideTriangles){
                Triangles.add(trg);
            }


        }


/*
        // Creating the Back view Rectangle of the rallonge
        Triangle Tr1BB = new Triangle(vertices[1], verticesBack[1], verticesBack[2]);
        Triangle Tr2BB = new Triangle(vertices[1], vertices[2], verticesBack[2]);
        //Triangles.add(Tr1BB);
        //Triangles.add(Tr2BB);

        // Creating the Front view Rectangle of the rallonge
        Triangle Tr1BF = new Triangle(vertices[0], verticesBack[0], verticesBack[3]);
        Triangle Tr2BF = new Triangle(vertices[0], vertices[3], verticesBack[3]);
        //Triangles.add(Tr1BF);
        //Triangles.add(Tr2BF);
*/
        return Triangles;
    }
    protected List<Triangle> createPignonT(ArrayList<Position> positions, double largeur, double wallLongueur, double angle, double wallHauteur, double depth, ToitDirection directionToit){
        List<Triangle> Triangles = new ArrayList<>();
        double angleRad = Math.toRadians(angle);
        double hauteurRal= Math.tan(angleRad)*largeur;

       // if (directionToit.equals(ToitDirection.RIGHT_TO_LEFT)  || directionToit.equals(ToitDirection.LEFT_TO_RIGHT)){
        //    wallLongueur = largeur;}

        Vertex[] verticesGG = new Vertex[3];
        Vertex[] verticesGB = new Vertex[3];
        Vertex[] verticesDF = new Vertex[3];
        Vertex[] verticesDB = new Vertex[3];
        // Points
        // top left --> bottom left  -->  bottom right
        double a = 0;
        double b =0;
        // Pignon triangles production
        double topX = 0;
        for (int j = 0; j<2; j++) {
            if (j==0){ a =0;}
            if (j==1){ a =depth/2;}
            if (directionToit.equals(ToitDirection.RIGHT_TO_LEFT)  || directionToit.equals(ToitDirection.LEFT_TO_RIGHT)){
                wallLongueur = largeur;
            }
            //double topX = 0;
            for (int i = 0; i < 4; i++) {
                Vertex[] verticesG = new Vertex[3];
                int index = 0;
                for (Position pos : positions) {

                    double x = pos.getX().convertToDouble()+a;
                    if (x>topX){ topX = x;}
                    if (j==1){
                        if (x==topX){x = pos.getX().convertToDouble()-a; }
                    }

                    double y = 0+a;
                    double z = 0;

                    if (i == 1) {
                        y = depth / 2 +a;
                    }
                    if (i == 2) {
                        y = wallLongueur - depth / 2-a;
                    }
                    if (i == 3) {
                        y = wallLongueur-a;
                    }

                    if (pos.getY().convertToDouble() < 0) {
                        z = wallHauteur - pos.getY().convertToDouble()-a*3/4;///2;
                    } else {
                        z = wallHauteur - pos.getY().convertToDouble();
                    }
                    /*
                    if (facadeToit.equals(ToitDirection.LEFT_TO_RIGHT ) || facadeToit.equals(ToitDirection.LEFT_TO_RIGHT )){
                        double temp= x;
                        x = y;
                        y=temp;
                    }*/

                    if (directionToit.equals(ToitDirection.LEFT_TO_RIGHT)){
                        double temp2 = x;
                        x = y;
                        y = chalet.getLongueur().convertToDouble()-temp2;
                    }
                    if (directionToit.equals(ToitDirection.RIGHT_TO_LEFT)){
                        double temp = x;
                        x = y;
                        y = chalet.getLongueur().convertToDouble()-temp;
                    }
                    verticesG[index] = new Vertex(x, y, z);
                    index++;
                }
                Triangle Pignon = new Triangle(verticesG[0], verticesG[1], verticesG[2]);
                Triangles.add(Pignon);
                if (i == 0) {
                    verticesGG = verticesG;
                }
                if (i == 1) {
                    verticesGB = verticesG;
                }
                if (i == 2) {
                    verticesDF = verticesG;
                }
                if (i == 3) {
                    verticesDB = verticesG;
                }
            }
            for (int i = 0; i < 2; i++) {
                if (i == 1) {
                    verticesGG = verticesDF;
                    verticesGB = verticesDB;
                }

                // Creating the Back view Rectangle of the
                Triangle Tr1BB = new Triangle(verticesGG[0], verticesGG[1], verticesGB[0]);
                Triangle Tr2BB = new Triangle(verticesGG[1], verticesGB[0], verticesGB[1]);
                Triangles.add(Tr1BB);
                Triangles.add(Tr2BB);

                // Creating the Bot view Rectangle of the
                Triangle Tr1Bot = new Triangle(verticesGG[1], verticesGG[2], verticesGB[1]);
                Triangle Tr2Bot = new Triangle(verticesGG[2], verticesGB[1], verticesGB[2]);
                Triangles.add(Tr1Bot);
                Triangles.add(Tr2Bot);

                // Creating the Bot view Rectangle of the
                Triangle Tr1Top = new Triangle(verticesGG[0], verticesGG[2], verticesGB[0]);
                Triangle Tr2Top = new Triangle(verticesGG[2], verticesGB[0], verticesGB[2]);
                Triangles.add(Tr1Top);
                Triangles.add(Tr2Top);
            }

        }

        return Triangles;

    }

    protected void exportPignon(String path) {
//        String pignonPath;
//        for (Pignon pignon : chalet.getPignonList()) {
//            List<Triangle> triangles = convertPignonToSTL(pignon);
//            if (pignon.facade.equals(Facade.DROITE)) {
//                pignonPath = "PD";
//            } else {
//                pignonPath = "PG";
//            }
//            try {
//                String fileName = path + pignonPath + ".stl";
//                STLWriter.writeSTLFile(fileName, triangles);
//                System.out.println("STL file created successfully at : " + path);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    protected void exportDessusToit(String path) {
        List<Triangle> triangles = convertToitToSTL();
        try {
            String fileName = path + "R" + ".stl";
            STLWriter.writeSTLFile(fileName, triangles);
            System.out.println("STL file created successfully at : " + path);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Triangle> convertWallsToSTL(Map.Entry<Facade, Wall> wallEntry) {
        Wall wall = wallEntry.getValue();
        Facade facade = wallEntry.getKey();


        List<Triangle> allTriangles = new ArrayList<>();


        List<Rectangle> smallRectangles = writeWallFiniToRectangles(facade, chalet);


        for (Rectangle rect : smallRectangles) {
            allTriangles.addAll(convertRectangleToTwoTriangles(rect));
        }

        return allTriangles;
    }

    private List<Triangle> convertRectangleToTwoTriangles(Rectangle rect) {
        // Assuming each rectangle can be represented by two triangles
        Vertex[] vertices = rect.getVertices();
        List<Triangle> triangles = new ArrayList<>();
        triangles.add(new Triangle(vertices[0], vertices[1], vertices[2]));
        triangles.add(new Triangle(vertices[2], vertices[3], vertices[0]));

        return triangles;
    }


    private boolean isTriangleOverlappingWithAccessories(Triangle triangle, List<Accessory> accessories) {
        // Implement logic to check if the triangle overlaps with any accessory
        // This will depend on the structure of your Triangle and Accessory classes
        return false;
    }

    private List<Triangle> convertRallongeToSTL() {
        return new ArrayList<>();
    }

    private List<Triangle> convertToitToSTL() {
        return new ArrayList<>();
    }

//    private List<Triangle> convertPignonToSTL(Pignon pignon) {
//        return new ArrayList<>();
//    }

    public List<Rectangle> enlever_reinnure_stl(List<Rectangle> smallRectangles){

        double hauteurChal = chalet.getHauteur().convertToDouble();
        int nbrCol = 100;
        int nbrRows = 100;

        double smallHeight = hauteurChal / nbrRows;
        double smallWidth = chalet.getLargeur().convertToDouble() / nbrCol;

        double depth = chalet.getDepth().convertToDouble();

        int totalSmallRectInWidth = (int) Math.floor(depth / 2 / smallWidth);
        int totalSmallRectInHeight = (int) Math.floor(hauteurChal / smallHeight);

        List<Rectangle> rectanglesToRemove = new ArrayList<>();

        for (int h = 0; h < totalSmallRectInWidth; h++) {
            for (int k = 0; k < totalSmallRectInHeight; k++) {
                Rectangle toRemove = new Rectangle(h * smallWidth, k * smallHeight, smallWidth, smallHeight);
                rectanglesToRemove.add(toRemove);
            }
        }

        List<Rectangle> resultRectangles = new ArrayList<>(smallRectangles);

        Iterator<Rectangle> iterator = resultRectangles.iterator();
        while (iterator.hasNext()) {
            Rectangle rect1 = iterator.next();
            for (Rectangle rect2 : rectanglesToRemove) {
                if (rect1.getX() == rect2.getX() && rect1.getY() == rect2.getY()) {
                    iterator.remove();
                    break;
                }
            }
        }

        return resultRectangles;

}

    public List<Rectangle> writeWallFiniToRectangles(Facade facade, Shed chalet) {

        Map<Facade, Wall> facadeWallMap = chalet.getWallList();
        Wall wall = facadeWallMap.get(facade);
        //Facade facade = wallEntry.getKey();
        //Wall wall = wallEntry.getValue();

        List<Triangle> allTriangles = new ArrayList<>();
        List<Triangle> allTriangles_back = new ArrayList<>();
        List<Rectangle> allrectangles = new ArrayList<Rectangle>();
        List<Rectangle> rectangles_ = new ArrayList<Rectangle>();

        double depth_chalet = chalet.getDepth().convertToDouble();
        double longueur_chalet = chalet.getLongueur().convertToDouble();
        double largeur_chalet = chalet.getLargeur().convertToDouble();
        double hauteur_chalet = chalet.getHauteur().convertToDouble();

        ArrayList<ArrayList<Position>> front = wall.getViewPoints().get("front");
        ArrayList<Position> positions;
        if (front.size() > 1) {
            positions = front.get(1);
        } else {
            positions = front.get(0);
        }

        // Create a rectangle to represent the wall
        Position origin = positions.get(0);
        double x = origin.getX().convertToDouble();
        double y = origin.getY().convertToDouble();
        double width = wall.getWidth().convertToDouble();
        double height = wall.getHeight().convertToDouble();
        Rectangle wall_rectangle = new Rectangle(x, y, width, height);
        int nbr_col = 100;
        int nbr_rows = 100;

        double small_height = height / nbr_rows;
        double small_width = width / nbr_col;

        List<Rectangle> small_rectangles = new ArrayList<Rectangle>();
        for (int i = 0; i < nbr_rows; i++) {
            for (int j = 0; j < nbr_col; j++) {
                Rectangle recto = new Rectangle(x + j * small_width, y + i * small_height, small_width, small_height);
                small_rectangles.add(recto);
            }
        }
        List<Accessory> accessoriesList = wall.getAccessories();
        for (Accessory acc : accessoriesList) {
            Position org = acc.getPosition();
            double accX = org.getX().convertToDouble();
            //double accY = wall.getHeight().convertToDouble() - org.getY().convertToDouble();
            double accHeigth = acc.getHeight().convertToDouble();
            double accWidth = acc.getWidth().convertToDouble();
            double accY = height - org.getY().convertToDouble()-accHeigth;
            /*
            int intValue = (int) accX;
            int floorX =(int)Math.floor(accX);
            int ceilValue = (int) Math.ceil(accX);
            */
            int nbr_rectangles_in_width = (int) Math.floor(accX / small_width);
            int nbr_rectangles_in_height = (int) Math.floor(accY / small_height);

            double smallX = x + nbr_rectangles_in_width * small_width;
            double smallY = y + nbr_rectangles_in_height * small_height;

            int total_small_rect_in_width = (int) Math.floor(accWidth / small_width);
            int total_small_rect_in_height = (int) Math.floor(accHeigth / small_height);

            List<Rectangle> rectanglesToRemove = new ArrayList<>();

            for (int h = nbr_rectangles_in_width; h < total_small_rect_in_width + nbr_rectangles_in_width; h++) {

                for (int k = nbr_rectangles_in_height; k < total_small_rect_in_height + nbr_rectangles_in_height; k++) {
                    Rectangle toRemove = new Rectangle(x + h * small_width, y + k * small_height, small_width, small_height);
                    rectanglesToRemove.add(toRemove);
                    //small_rectangles = removeRectangle(small_rectangles, x +h*small_width, y + k*small_height, small_width, small_height);
                    // small_rectangles.remove(toRemove);
                }
            }
            // Iterating through the second list and removing rectangles with same x and y from the first list
            for (Rectangle rect2 : rectanglesToRemove) {
                double x2 = rect2.getX();
                double y2 = rect2.getY();

                // Iterate through the first list and remove rectangles with the same x and y as rect2
                for (int i = 0; i < small_rectangles.size(); i++) {
                    Rectangle rect1 = small_rectangles.get(i);
                    if (rect1.getX() == x2 && rect1.getY() == y2) {
                        small_rectangles.remove(i);
                        i--; // Decrement i to handle shifting of elements after removal
                    }
                }
            }
            //small_rectangles.removeAll(rectanglesToRemove);
        }

        //ArrayList<List<Triangle>> alll = new ArrayList<List<Triangle>>();
        //alll.add(allTriangles);
        //alll.add(allTriangles_back);
        //for (Rectangle rect : small_rectangles) {
        //  System.out.println("Rectangle: " + rect);
        //}

        //DrawRectangles drawRectangles = new DrawRectangles(small_rectangles);
        //drawRectangles.setVisible(true);
//

        return small_rectangles;

    }

    protected List<Rectangle> removeRainureRect(List<Rectangle> small_rectangles){
        List<Rectangle> small_rectangles_list = small_rectangles;

        double hauteurChal = chalet.getHauteur().convertToDouble();
        int nbr_col = 100;
        int nbr_rows = 100;

        double small_height = hauteurChal / nbr_rows;
        double small_width = chalet.getLargeur().convertToDouble() / nbr_col;

        double depth = chalet.getDepth().convertToDouble();

        int total_small_rect_in_width = (int) Math.floor(depth/2 / small_width);
        int total_small_rect_in_height = (int) Math.floor(hauteurChal / small_height);

        List<Rectangle> rectanglesToRemove = new ArrayList<>();

        for (int h = 0 ; h < total_small_rect_in_width ; h++) {

            for (int k = 0; k < total_small_rect_in_height; k++) {
                Rectangle toRemove = new Rectangle( h * small_width, k * small_height, small_width, small_height);
                rectanglesToRemove.add(toRemove);
                //small_rectangles = removeRectangle(small_rectangles, x +h*small_width, y + k*small_height, small_width, small_height);
                // small_rectangles.remove(toRemove);
            }
        }
        for (Rectangle rect2 : rectanglesToRemove) {
            double x2 = rect2.getX();
            double y2 = rect2.getY();

            // Iterate through the first list and remove rectangles with the same x and y as rect2
            for (int i = 0; i < small_rectangles_list.size(); i++) {
                Rectangle rect1 = small_rectangles_list.get(i);
                if (rect1.getX() == x2 && rect1.getY() == y2) {
                    small_rectangles_list.remove(i);
                    i--; // Decrement i to handle shifting of elements after removal
                }
            }
        }

        return small_rectangles_list;
    }

    protected List<Triangle> writeRectangleToTriangle(List<Rectangle> small_rectangles, double depth, Facade facade, double longeur_Chal, double largeur_Chal) {
        List<Triangle> allTriangles = new ArrayList<>();
        List<Triangle> allTriangles_back = new ArrayList<>();
        // Define triangles for the rectangle
        for (Rectangle small_rectangle : small_rectangles) {
            Vertex[] vertices_basic = small_rectangle.getVertices();

            Vertex[] vertices = generatefrontVertices(vertices_basic, depth, facade, longeur_Chal, largeur_Chal);

            Triangle[] triangles = {
                    new Triangle(vertices[0], vertices[1], vertices[3]),  // Triangle 1
                    new Triangle(vertices[1], vertices[2], vertices[3])   // Triangle 2
            };

            // Here we have to modify the generateBackVertices function so that the back_vertices are always the interior vertices
            // Then we have to make sure the width of the back vertices is smaller by 1/2 depth in both sides so that it leaves space for rainures
            // il faut traiter selon la facade
            // si c la facade gauche ou droit il faut que x < 3/2 depth car ce mur est 1/2 loin de Origine de base  pour cote 0
/*
            if (small_rectangle.vertex1.x<depth/2 ){
                //allTriangles.addAll(Arrays.asList(triangles));
                System.out.println("un rectangle cote 0 enleve");
            } else if (small_rectangle.vertex1.x > longeur_Chal-depth) {
                //allTriangles.addAll(Arrays.asList(triangles));
                System.out.println("un rectangle cote 0 enleve");
            }

            else {
*/
            Vertex[] vertices_back = generateBackVertices(vertices_basic, depth, facade, longeur_Chal, largeur_Chal);

            Triangle[] backTriangles = {
                    new Triangle(vertices_back[3], vertices_back[1], vertices_back[0]),  // Triangle 3 (Back)
                    new Triangle(vertices_back[3], vertices_back[2], vertices_back[1])   // Triangle 4 (Back)

            };
            allTriangles.addAll(Arrays.asList(triangles));
            allTriangles.addAll(Arrays.asList(backTriangles));
            //ArrayList<List<Triangle>> alll = new ArrayList<List<Triangle>>();
            //alll.add(allTriangles);
            //alll.add(allTriangles_back);
            }


        //}

        return allTriangles;

    }

    protected List<Rectangle> removeRectangle(List<Rectangle> rectangles, double x, double y, double width, double height) {
        List<Rectangle> rectanglesToRemove = new ArrayList<>();

        // Find rectangles that match the specified dimensions and position
        for (Rectangle rect : rectangles) {
            if (rect.getX() == x && rect.getY() == y && rect.getWidth() == width && rect.getHeight() == height) {
                rectanglesToRemove.add(rect);
            }
        }

        // Remove identified rectangles from the list
        rectangles.removeAll(rectanglesToRemove);

        return rectangles;
    }

    protected Vertex[] generatefrontVertices(Vertex[] vertices_basic, double depth, Facade facade, double longeur_Chal, double largeur_Chal) {
        Vertex[] vertices = new Vertex[4];
        for (int i = 0; i < 4; i++) {
            Vertex vert = vertices_basic[i];
            double x = 0;
            double y = vert.x;  // le x ici designe le width du chalet
            double z = vert.y;  // le y ici designe la hauteur du chalet
            // pour le roof la hauteur sera une variable : hauteur y = sin(angle) * [y:(la coordonnees y du point: l'hypothénus dans notre triangle rectangle)]
            if (facade.name().equals("AVANT")) {
                x = largeur_Chal;
            }
            if (facade.name().equals("GAUCHE") || facade.name().equals("DROITE")) {
                x = y;
                y = 0;
            }
            if (facade.name().equals("DROITE")) {

                y = longeur_Chal;

            }
            vertices[i] = new Vertex(x, y, z);
            //vert.setZ(chalet.getDepth().convertToDouble());
        }
        return vertices;
    }

    protected Vertex[] generateBackVertices(Vertex[] vertices_basic, double depth, Facade facade, double longeur_Chal, double largeur_Chal) {
        Vertex[] vertices_back = new Vertex[4];
        for (int i = 0; i < 4; i++) {
            Vertex vert = vertices_basic[i];
            double x = depth;
            double y = vert.x;
            double z = vert.y;

            if (facade.name().equals("AVANT")) {
                x = largeur_Chal - depth;

                double max = vertices_basic[1].x;
                double min = vertices_basic[0].x;
                if (y==max){
                    y = y ;//+ depth/2;
                    //System.out.println("max is out at : " + y);
                }

                if (y==min){
                    y = y;//y - depth/2;
                    //System.out.println("min is out at : " + y);
                }

            }
            if (facade.name().equals("GAUCHE") || facade.name().equals("DROITE")) {
                x = y;
                y = depth;
            }
            if (facade.name().equals("DROITE")) {

                y = longeur_Chal - depth;

            }
            vertices_back[i] = new Vertex(x, y, z);

            //vert.setZ(chalet.getDepth().convertToDouble());
        }
        return vertices_back;
    }

    protected List<Triangle> generateAccessoriesSideTriangles(List<Accessory> accessoriesList, double depth, Facade facade, double longeur_Chal, double largeur_Chal, double hauteur) {
        List<Triangle> allTriangles = new ArrayList<>();
        // Create a rectangle to represent the wall

        double x = 0;
        double y = 0;

        Rectangle wall_rectangle = new Rectangle(x, y, largeur_Chal, hauteur);
        int nbr_col = 100;
        int nbr_rows = 100;

        double small_height = hauteur / nbr_rows;
        double small_width = largeur_Chal / nbr_col;


        for (Accessory acc : accessoriesList) {
            Position org = acc.getPosition();

            double accX = org.getX().convertToDouble()+depth*3/5;
            //double accY = org.getY().convertToDouble();
            double accHeigth = acc.getHeight().convertToDouble();
            double accWidth = acc.getWidth().convertToDouble();
            double accY = hauteur - org.getY().convertToDouble()-accHeigth;
            // you have to create a rectangle following the small rectangles that create you accessory
            /*
            int intValue = (int) accX;
            int floorX =(int)Math.floor(accX);
            int ceilValue = (int) Math.ceil(accX);
            */
            int nbr_rectangles_in_width = (int) Math.floor(accX / small_width);
            int nbr_rectangles_in_height = (int) Math.floor(accY / small_height);

            double smallX = x + nbr_rectangles_in_width * small_width;
            double smallY = y + nbr_rectangles_in_height * small_height;

            int total_small_rect_in_width = (int) Math.floor(accWidth / small_width);
            int total_small_rect_in_height = (int) Math.floor(accHeigth / small_height);



            Rectangle AccRect = new Rectangle(smallX, smallY, total_small_rect_in_width*small_width, total_small_rect_in_height*small_height);

            //ArrayList<Position> positions= acc.getPositionList();
            //Vertex[] vertices = generateVertices(positions, facade, longeur_Chal, largeur_Chal, hauteur, depth);
            //Vertex[] vertices_back = generateVerticesBack(positions, facade, depth, longeur_Chal, largeur_Chal, hauteur);

            Vertex[] vertices_basic = AccRect.getVertices();
            Vertex[] vertices = generatefrontVertices(vertices_basic, depth, facade, longeur_Chal, largeur_Chal);
            Vertex[] vertices_back = generateBackVertices(vertices_basic, depth, facade, longeur_Chal, largeur_Chal);

            Triangle[] sideTriangles = {
                    // Side 1 (Left side)
                    new Triangle(vertices[0], vertices[3], vertices_back[0]),
                    new Triangle(vertices[3], vertices_back[3], vertices_back[0]),

                    // Side 2 (Right side)
                    new Triangle(vertices[2], vertices_back[2], vertices[1]),
                    new Triangle(vertices[1], vertices_back[1], vertices_back[2]),

                    // Side 3 (Upper Side)
                    new Triangle(vertices[0], vertices[1], vertices_back[0]),
                    new Triangle(vertices[1], vertices_back[1], vertices_back[0]),

                    // Side 4 (Buttom Side)
                    new Triangle(vertices[3], vertices[2], vertices_back[3]),
                    new Triangle(vertices[2], vertices_back[2], vertices_back[3])
            };


            allTriangles.addAll(Arrays.asList(sideTriangles));

        }
        return allTriangles;
    }


    // Method to return the letter corresponding to the facade
    private String getFacadeLetter(Facade facade) {
        switch (facade) {
            case AVANT: return "F";
            case ARRIERE: return "A";
            case GAUCHE: return "G";
            case DROITE: return "D";
            default: return "";
        }
    }
}