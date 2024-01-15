package ca.ulaval.glo2004.domain;
import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;
public class ValeurImperialeDTO {
    public double value;
    private int m_valeurEntiere;
    private int m_fractionTop;
    private int m_fractionBot;
    private UUID uuid;
public ValeurImperialeDTO(ValeurImperiale va){
    this.m_valeurEntiere= va.getValeurEntiere();
    this.m_fractionTop = va.getFractionTop();
    this.m_fractionBot = va.getFractionBot();
    this.uuid = va.getUUID();
}
}
