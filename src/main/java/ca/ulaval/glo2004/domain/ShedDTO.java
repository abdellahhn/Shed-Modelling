package ca.ulaval.glo2004.domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShedDTO {
    public ArrayList<Position> positionList;
    private ValeurImperiale longueur;
    private ValeurImperiale largeur;
    private ValeurImperiale hauteur;
    private ValeurImperiale depth;
   private Map<Facade, Wall> wallList;
   public UUID Uuid;

    public Color couleurMateriel = new Color(153, 153, 153);


    public ShedDTO(Shed sh) {
        this.longueur = sh.getLongueur();
        this.largeur = sh.getLargeur();
        this.hauteur = sh.getHauteur();
        this.depth = sh.getDepth();
       this.wallList= sh.getWallList();
        Uuid = sh.getUUID();
    }
    public ValeurImperiale getLongueur() {return longueur;}
    public ValeurImperiale getLargeur() {return largeur;}
    public ValeurImperiale getHauteur() {return hauteur;}
    public ValeurImperiale getDepth() {return depth;}
  public Map<Facade, Wall> getWallList() {return wallList;}
}
