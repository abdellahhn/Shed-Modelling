package ca.ulaval.glo2004.domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

public class PolygoneDTO {
    //   private ArrayList<PositionDTO> m_listePositionImpériaux;
    private ArrayList<Position> m_listePositionImpériaux;
    private Color m_couleur;
    private UUID uuid;


    public PolygoneDTO(Polygone po)
    {
        m_listePositionImpériaux = po.getListePointsImperiaux();
        m_couleur = po.getColor();
        this.uuid = po.getUUID();
    }
}
