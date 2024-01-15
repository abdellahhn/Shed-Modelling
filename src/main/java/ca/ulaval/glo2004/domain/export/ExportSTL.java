package ca.ulaval.glo2004.domain.export;

import ca.ulaval.glo2004.domain.*;

import java.util.*;


public class ExportSTL {
    protected Shed chalet;

    public ExportSTL(){}
    public ExportSTL(Shed c) {
        this.chalet = c;
    }

    public void export(String path){
        // if its a wall we want to export, then we use exporterMur()
        exportMur(path);
        exportToit(path);
        exportRallongeToit(path);
        exportPignon(path);
        exportDessusToit(path);
    }

    protected void exportMur(String path){
        // overriden in every child class
    }
    private void exportToit(String path){
        // overriden in every child class
    }
    protected void exportRallongeToit(String path){
        // overriden in every child class
    }
    protected void exportPignon(String path){
        // overriden in every child class
    }
    protected void exportDessusToit(String path){
        // overriden in every child class
    }
    private void cpnvertToSTL(){
    }

    protected List<Triangle> writeRectangleToTriangle(Vertex[] vertices, Vertex[] vertices_back) {
        List<Triangle> allTriangles = new ArrayList<>();
        List<Triangle> allTriangles_back = new ArrayList<>();
        // Define triangles for the rectangle
        Triangle[] triangles = {
                new Triangle(vertices[0], vertices[1], vertices[3]),  // Triangle 1
                new Triangle(vertices[1], vertices[2], vertices[3])   // Triangle 2
        };

        Triangle[] backTriangles = {
                new Triangle(vertices_back[3], vertices_back[1], vertices_back[0]),  // Triangle 3 (Back)
                new Triangle(vertices_back[3], vertices_back[2], vertices_back[1])   // Triangle 4 (Back)

        };
        allTriangles.addAll(Arrays.asList(triangles));
        allTriangles.addAll(Arrays.asList(backTriangles));
        ArrayList<List<Triangle>> alll = new ArrayList<List<Triangle>>();
        alll.add(allTriangles);
        alll.add(allTriangles_back);
        return allTriangles;

    }

    protected Triangle[] createSideTriangles (Vertex[] vertices, Vertex[] vertices_back) {
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
        return sideTriangles;
    }

    protected Vertex[] generateVertices(ArrayList<Position> positions, Facade facade, double wallLongueur, double wallLargeur, double wallHauteur, double wallDepth){
        //ArrayList<Vertex[]> all_vertices= new ArrayList<Vertex[]>();
        Vertex[] vertices = new Vertex[positions.size()];
        int index = 0;
        for (Position pos : positions){
            double x = 0;
            double y = pos.getX().convertToDouble();
            double z = wallHauteur - pos.getY().convertToDouble();
            // plan x vers moi, y vers droite, z vers haut
            // Avant et Arriere sur le plan (y, z)
            // Gauche et Droite sur le plan (x, z)
            if(facade.equals(Facade.AVANT)){
                x = wallLargeur - wallDepth;
            }
            if (facade.equals(Facade.GAUCHE) || facade.equals(Facade.DROITE)){
                x = y;
                y = 0;
            }
            if (facade.equals(Facade.DROITE)){

                y = wallLongueur - wallDepth;

            }
            vertices[index] = new Vertex(x, y, z);
            index ++;
        }
        return vertices;
    }

    protected Vertex[] generateVerticesBack(ArrayList<Position> positions, Facade facade, double depth, double longueur, double largeur, double hauteur){

        Vertex[] vertices_back = new Vertex[positions.size()];
        int index = 0;
        for (Position pos : positions){

            double x = depth;
            double y = pos.getX().convertToDouble();
            double z = hauteur - pos.getY().convertToDouble();
            // plan x vers moi, y vers droite, z vers haut
            // Avant et Arriere sur le plan (y, z)
            // Gauche et Droite sur le plan (x, z)
            if(facade.equals(Facade.AVANT)){
                x = largeur;
            }
            if (facade.equals(Facade.GAUCHE) || facade.equals(Facade.DROITE)){
                x = y;
                y = depth;
            }
            if (facade.equals(Facade.DROITE)){

                y = longueur;
            }
            vertices_back[index] = new Vertex(x, y, z);
            index++;
        }
        return vertices_back;
    }


//    public void convertToSTL(){
//
//        Map<Facade, Wall> facadeWallMap = chalet.getWallList();
//
//        // Generate STL content
//        StringBuilder stlContent = new StringBuilder();
//        stlContent.append("solid Rectangle\n");
//
//        for (Map.Entry<Facade, Wall> entry : facadeWallMap.entrySet()){
//            Facade facade = entry.getKey();
//            Wall wall = entry.getValue();
//            System.out.println(facade);
//
//
//
//            Map<String, ArrayList<ArrayList<Position>>> viewPoints = wall.getViewPoints();
//
//            ArrayList<ArrayList<Position>> front = viewPoints.get("front");
//            ArrayList<Position> positions;
//            if (!front.isEmpty()) {
//                if (front.size() > 1)
//                { positions = front.get(1); }
//                else
//                { positions = front.get(0); }
//            }
//            else {
//                positions = new ArrayList<>(); // Handle the case when front is empty
//            }
//            double depth = chalet.getDepth().convertToDouble();
//
//
//            Vertex[] vertices = new Vertex[positions.size()];
//            int index = 0;
//            for (Position pos : positions){
//
//                double x = pos.getX().convertToDouble();
//                double y = pos.getY().convertToDouble();
//                vertices[index] = new Vertex(x, y, 0);
//                index ++;
//                System.out.println(x);
//                System.out.println(y);
//            }
//            for (Vertex vertex : vertices) {
//                System.out.println("Front: " +"X: " + vertex.getX() + ", Y: " + vertex.getY() + ", Z: " + vertex.getZ());
//            }
//
//            // Define triangles using the vertices
//            Triangle[] triangles = {
//                    //new Triangle(vertices[0], vertices[1], vertices[2]),  // Triangle 1
//                    //new Triangle(vertices[1], vertices[3], vertices[2])   // Triangle 2
//
//                    new Triangle(vertices[0], vertices[1], vertices[3]),  // Triangle 1
//                    new Triangle(vertices[1], vertices[2], vertices[3])   // Triangle 2
//            };
//
//    }
//
//            Vertex[] vertices_back = new Vertex[positions.size()];
//            int indexx = 0;
//            for (Position pos : positions){
//
//                double x = pos.getX().convertToDouble();
//                double y = pos.getY().convertToDouble();
//                vertices_back[indexx] = new Vertex(x, y, depth);
//                indexx ++;
//                //System.out.println(x);
//                //System.out.println(y);
//            }
//            for (Vertex vertex : vertices_back) {
//
//                System.out.println("Back: "+"X: " + vertex.getX() + ", Y: " + vertex.getY() + ", Z: " + vertex.getZ());
//            }
//
//            Triangle[] backTriangles = {
//                    //new Triangle(vertices[2], vertices[1], vertices[0]),  // Triangle 3 (Back)
//                    //new Triangle(vertices[2], vertices[3], vertices[1])   // Triangle 4 (Back)
//
//                    new Triangle(vertices_back[3], vertices_back[1], vertices_back[0]),  // Triangle 3 (Back)
//                    new Triangle(vertices_back[3], vertices_back[2], vertices_back[1])   // Triangle 4 (Back)
//            };
//
//
//            for (Triangle triangle : triangles) {
//                stlContent.append("  facet normal 0 0 1\n");
//                stlContent.append("    outer loop\n");
//                for (Vertex vertex : triangle.getVertices()) {
//                    stlContent.append("      vertex ").append(vertex.getX())
//                            .append(" ").append(vertex.getY())
//                            .append(" ").append(vertex.getZ()).append("\n");
//                }
//                stlContent.append("    endloop\n").append("  endfacet\n");
//            }
//
//            // Append triangles for the back side to the STL content
//            for (Triangle triangle : backTriangles) {
//                stlContent.append("  facet normal 0 0 -1\n");
//                stlContent.append("    outer loop\n");
//                for (Vertex vertex : triangle.getVertices()) {
//                    stlContent.append("      vertex ").append(vertex.getX())
//                            .append(" ").append(vertex.getY())
//                            .append(" ").append(vertex.getZ()).append("\n");
//                }
//                stlContent.append("    endloop\n").append("  endfacet\n");
//            }
//
//
//            // viewpoints.get("front").get(1);
//
//        }
//
//
//        stlContent.append("endsolid Rectangle\n");
//        String stlFileName = "walls.stl";
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(stlFileName))) {
//            writer.write(stlContent.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    protected List<Triangle> createCover(ArrayList<Position> positions, double largeur, double wallLongueur, double angle, double wallHauteur, double depth, ToitDirection directionToit) {
        List<Triangle> Triangles = new ArrayList<>();
        double angleRad = Math.toRadians(angle);
        double hauteurRal = Math.tan(angleRad) * largeur;

        if (directionToit.equals(ToitDirection.RIGHT_TO_LEFT)  || directionToit.equals(ToitDirection.LEFT_TO_RIGHT)){
            wallLongueur = largeur;
        }

        Vertex[] vertices= new Vertex[5];
        int index = 0;
        for (Position pos : positions) {
            double x = pos.getX().convertToDouble();
            double y = 0;
            double z = 0;
            if (pos.getY().convertToDouble() < 0) {
                z = wallHauteur - pos.getY().convertToDouble();
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

        Vertex[] verticesBack= new Vertex[5];
        int index2 = 0;
        for (Position pos : positions) {
            double x = pos.getX().convertToDouble();
            double y = wallLongueur;
            double z = 0;
            if (pos.getY().convertToDouble() < 0) {
                z = wallHauteur - pos.getY().convertToDouble();
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

        return Triangles;
    }
    protected List<Triangle> createRallonge(ArrayList<Position> positions, double largeur, double wallLongueur, double angle, double wallHauteur, double depth, ToitDirection directionToit){
        List<Triangle> Triangles = new ArrayList<>();
        double angleRad = Math.toRadians(angle);
        double hauteurRal= Math.tan(angleRad)*largeur;

        if (directionToit.equals(ToitDirection.RIGHT_TO_LEFT)  || directionToit.equals(ToitDirection.LEFT_TO_RIGHT)){
            wallLongueur = largeur;
        }

        Vertex[] vertices= new Vertex[4];
        int index = 0;
        for (Position pos : positions) {
            double x = pos.getX().convertToDouble();
            double y = 0;
            double z = 0;
            if (pos.getY().convertToDouble() < 0) {
                z = wallHauteur - pos.getY().convertToDouble();
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
            double x = pos.getX().convertToDouble();
            double y = wallLongueur;
            double z = 0;
            if (pos.getY().convertToDouble() < 0) {
                z = wallHauteur - pos.getY().convertToDouble();
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

        Vertex[] verticesGG = new Vertex[3];
        Vertex[] verticesGB = new Vertex[3];
        Vertex[] verticesDF = new Vertex[3];
        Vertex[] verticesDB = new Vertex[3];
        // Points
        // top left --> bottom left  -->  bottom right
        if (directionToit.equals(ToitDirection.RIGHT_TO_LEFT)  || directionToit.equals(ToitDirection.LEFT_TO_RIGHT)){
            wallLongueur = largeur;
        }
        // Pignon triangles production
        for (int i = 0; i < 4;i++) {
            Vertex[] verticesG = new Vertex[3];
            int index = 0;
            for (Position pos : positions) {
                double x = pos.getX().convertToDouble();
                double y = 0;
                double z = 0;

                if (i==1){  y = depth;  }
                if (i==2){  y =  wallLongueur - depth;}
                if (i==3){  y = wallLongueur;  }

                if (pos.getY().convertToDouble() < 0) {
                    z = wallHauteur - pos.getY().convertToDouble();
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
                verticesG[index] = new Vertex(x, y, z);
                index++;
            }
            Triangle Pignon = new Triangle(verticesG[0], verticesG[1], verticesG[2]);
            Triangles.add(Pignon);
            if (i==0){ verticesGG = verticesG; }
            if (i==1){ verticesGB = verticesG; }
            if (i==2){ verticesDF = verticesG; }
            if (i==3){ verticesDB = verticesG; }
        }
        for (int i = 0; i<2; i++){
            if (i==1){ verticesGG = verticesDF;
                       verticesGB = verticesDB; }

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



        return Triangles;

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




}

