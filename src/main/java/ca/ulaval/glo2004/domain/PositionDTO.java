package ca.ulaval.glo2004.domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;
public class PositionDTO {
    public UUID Uuid;
    //private ValeurImperialeDTO m_x;
    private ValeurImperiale m_x;
    private ValeurImperiale m_y;
    public PositionDTO(Position po)
    {
        m_x = po.getX();
        m_y = po.getY();
        Uuid = po.getUUID();
    }
    public ValeurImperiale getX()
    {
        return m_x;
    }
    public ValeurImperiale getY()
    {
        return m_y;
    }
}
