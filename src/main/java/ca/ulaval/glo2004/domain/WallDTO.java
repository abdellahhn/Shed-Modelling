package ca.ulaval.glo2004.domain;
import java.util.*;
import java.util.List;
public class WallDTO {
    private List<AcessoryDTO> accessoryList;
    private ValeurImperiale width;
    private ValeurImperiale depth;
    private ValeurImperiale height;
    public Facade facade;
    public Map<String, ArrayList<ArrayList<Position>>> viewPoints = new HashMap<>();

    public WallDTO(Wall wa) {
        this.facade = wa.getFacade();
        this.width = wa.getWidth();
       // this.accessoryList =wa.getAccessories();
        this.height = wa.getHeight();
        this.depth = wa.getDepth();

//        Position topLeft = null;
//        Position botLeft = null;
//        Position topRight = null;
//        Position botRight = null;
//        ArrayList<Position> frontView1 = new ArrayList<>();
//        ArrayList<Position> frontView2 = new ArrayList<>();
//        ArrayList<Position> frontView3 = new ArrayList<>();
//        ArrayList<ArrayList<Position>> view = new ArrayList<>();

//        if (facade == Facade.AVANT || facade == Facade.ARRIERE) {
//            this.width = chalet.getLongueur();
//            topLeft = new Position(new ValeurImperiale(10), new ValeurImperiale(10));
//            botLeft = new Position(new ValeurImperiale(10), this.height.add(new ValeurImperiale(10)));
//            topRight = new Position(this.width.add(new ValeurImperiale(10)), new ValeurImperiale(10));
//            botRight = new Position(this.width.add(new ValeurImperiale(10)), this.height.add(new ValeurImperiale(10)));
//
//            frontView1.add(topLeft);
//            frontView1.add(topRight);
//            frontView1.add(botRight);
//            frontView1.add(botLeft);
//            view.add(frontView1);
//
//        } else {
//            this.width = chalet.getLargeur().substract(chalet.getDepth());
//            Position p1_rec1 = new Position(new ValeurImperiale(10), new ValeurImperiale(10));
//            Position p2_rec1 = new Position((new ValeurImperiale(10)).add((this.depth).divideByInt(2)), new ValeurImperiale(10));
//            Position p3_rec1 = new Position((new ValeurImperiale(10)).add((this.depth).divideByInt(2)), (new ValeurImperiale(10).add(chalet.getHauteur())));
//            Position p4_rec1 = new Position((new ValeurImperiale(10)), new ValeurImperiale(10).add(chalet.getHauteur()));
//
//            frontView1.add(p1_rec1);
//            frontView1.add(p2_rec1);
//            frontView1.add(p3_rec1);
//            frontView1.add(p4_rec1);
//            view.add(frontView1);
//
//            Position p1_rec2 = new Position(new ValeurImperiale(10).add((this.depth).divideByInt(2)), new ValeurImperiale(10));
//            Position p2_rec2 = new Position(new ValeurImperiale(10).add((this.depth).divideByInt(2)).add(this.width), new ValeurImperiale(10));
//            Position p3_rec2 = new Position(new ValeurImperiale(10).add((this.depth).divideByInt(2)).add(this.width), new ValeurImperiale(10).add(chalet.getHauteur()));
//            Position p4_rec2 = new Position(new ValeurImperiale(10).add((this.depth).divideByInt(2)), new ValeurImperiale(10).add(chalet.getHauteur()));
//
//
//            frontView2.add(p1_rec2);
//            frontView2.add(p2_rec2);
//            frontView2.add(p3_rec2);
//            frontView2.add(p4_rec2);
//            view.add(frontView2);
//
//            Position p1_rec3 = new Position(new ValeurImperiale(10).add(this.depth).divideByInt(2).add(this.width), new ValeurImperiale(10));
//            Position p2_rec3 = new Position(new ValeurImperiale(10).add(chalet.getLargeur()), new ValeurImperiale(10));
//            Position p3_rec3 = new Position(new ValeurImperiale(10).add(chalet.getLargeur()), new ValeurImperiale(10).add(chalet.getHauteur()));
//            Position p4_rec3 = new Position(new ValeurImperiale(10).add(this.depth).divideByInt(2).add(this.width), new ValeurImperiale(10).add(chalet.getHauteur()));
//
//
//            frontView3.add(p1_rec3);
//            frontView3.add(p2_rec3);
//            frontView3.add(p3_rec3);
//            frontView3.add(p4_rec3);
//            view.add(frontView3);
//
//        }


       // wa.removeGroove();
    }
    public AcessoryDTO findAccessory(PositionDTO positionDTO){

        for (AcessoryDTO accessory : accessoryList) {
            if (accessory.selected(positionDTO)) {
                return accessory;
            }
        }
        return null;
    }
}