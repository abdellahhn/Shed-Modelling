/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.domain;


import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author abdellahhanane
 */
public class ValeurImperiale implements Comparable<ValeurImperiale>, Serializable{

    public double value;
    private int m_valeurEntiere;
    private int m_fractionTop;
    private int m_fractionBot;
    private UUID uuid;
   
    public ValeurImperiale()
    {
        int ppi = 90;
        m_valeurEntiere = 0;
        m_fractionTop = 0;
        m_fractionBot = 0;
        this.uuid = UUID.randomUUID();
    }

    public ValeurImperiale(ValeurImperiale other) {
        this.value = other.value;
        this.m_valeurEntiere = other.m_valeurEntiere;
        this.m_fractionTop = other.m_fractionTop;
        this.m_fractionBot = other.m_fractionBot;
        this.uuid = other.uuid;
    }



    
    public ValeurImperiale(int p_pixel)
    {
        int ppi = 90;
        m_valeurEntiere = (p_pixel / ppi );
        m_fractionTop = (p_pixel % ppi);
        m_fractionBot = ppi;
        this.uuid = UUID.randomUUID();
    }

    public ValeurImperiale(double value) {
        int intValue = (int) value;
        int fractionTop = (int) ((value - intValue) * 1);
        m_valeurEntiere = intValue;
        m_fractionTop = fractionTop;
        m_fractionBot = 1;
        this.uuid = UUID.randomUUID();
    }



    
    public ValeurImperiale(int p_valeurEntiere, int p_fractionTop, int p_fractionBot)
    {
        if (p_fractionTop == 0 || p_fractionBot == 0)
        {
            m_valeurEntiere = p_valeurEntiere;
            m_fractionTop = 0;
            m_fractionBot = 1;
        }
        
        else 
        
        {
            m_valeurEntiere = p_valeurEntiere;
            m_fractionTop = p_fractionTop;
            m_fractionBot = p_fractionBot;
        }
        
     }
    
        public double toDouble(){
        
        double valeurEntiere = getValeurEntiere();
        double fractionTop = getFractionTop();
        double fractionBot = getFractionBot();
        
        return valeurEntiere + (fractionTop/fractionBot);
    } 
        
    public static ValeurImperiale convertDoubleToValeurImperiale(double value) {
        // Convert the double value to the desired format (entier, fractionTop, fractionBot)
        int intValue = (int) value;  // Extract the integer part
        int fractionTop = (int) ((value - intValue) * 100);  // Extract two decimal places
        int fractionBot = 100;  // Set the denominator to 100

        // Return a new ValeurImperiale object
        return new ValeurImperiale(intValue, fractionTop, fractionBot);
    }


    public int getValeurEntiere()
    {
        return m_valeurEntiere;
    }
    

    public void setValeurEntiere(int p_valeurEntiere)
    {
        this.m_valeurEntiere = p_valeurEntiere;
    }
  

    
    public int getFractionTop()
    {      
        return m_fractionTop;
    }
    

    public void setFractionTop(int p_fractionTop)
    {
        this.m_fractionTop = p_fractionTop;
    }
  

    
    public int getFractionBot()
    { 
        return m_fractionBot;     
    }
    

    public void setFractionBot(int p_fractionBot)
    {
        this.m_fractionBot = p_fractionBot;
    }

    public ValeurImperiale substract(ValeurImperiale p_otherValeurImperiale) {

        int valeurEntiere = this.getValeurEntiere() - p_otherValeurImperiale.getValeurEntiere();
        int fractionTop = (this.getFractionTop() * p_otherValeurImperiale.getFractionBot()) - (p_otherValeurImperiale.getFractionTop()* this.getFractionBot());
        int fractionBot = this.getFractionBot() * p_otherValeurImperiale.getFractionBot();

        return new ValeurImperiale(valeurEntiere, fractionTop, fractionBot);

    }

    public static ValeurImperiale fromPouceFraction(int pouce, int fractionTop, int fractionBot) {
        if (fractionBot == 0) {
            throw new IllegalArgumentException("Le dénominateur ne peut pas être zéro.");
        }
        return new ValeurImperiale(pouce, fractionTop, fractionBot);
    }

    public ValeurImperiale add(ValeurImperiale p_otherValeurImperiale){

        int valeurEntiere = this.getValeurEntiere() + p_otherValeurImperiale.getValeurEntiere();
        int fractionTop = (this.getFractionTop() * p_otherValeurImperiale.getFractionBot()) + (p_otherValeurImperiale.m_fractionTop * this.getFractionBot());
        int fractionBot = (this.getFractionBot() * p_otherValeurImperiale.getFractionBot());

        return new ValeurImperiale (valeurEntiere, fractionTop, fractionBot);
    }

    public ValeurImperiale divideByInt(int divisor) {
        int numerator = this.getValeurEntiere() * this.getFractionBot() + this.getFractionTop();
        int denominator = this.getFractionBot();

        int newNumerator = numerator;
        int newDenominator = denominator * divisor;
        int valeurEntiere = (int) newNumerator / newDenominator;
        newNumerator = newNumerator % newDenominator;


        return new ValeurImperiale(valeurEntiere, newNumerator, newDenominator);
    }

    public ValeurImperiale divide(ValeurImperiale p_otherValeurImperiale) {

        if (p_otherValeurImperiale.getValeurEntiere() == 0 &&
        p_otherValeurImperiale.getFractionTop() == 0 &&
        p_otherValeurImperiale.getFractionBot() == 0) {
        throw new IllegalArgumentException("Cannot divide by zero.");
    }

    // Calculate the division
    if (p_otherValeurImperiale.getValeurEntiere() == 0){
        int valeurEntiere = 0;
         int fractionTop = this.getFractionTop() / p_otherValeurImperiale.getFractionTop();
    int fractionBot = this.getFractionBot() / p_otherValeurImperiale.getFractionBot();
        return new ValeurImperiale(valeurEntiere, fractionTop, fractionBot);

    }else{
        int valeurEntiere = this.getValeurEntiere() / p_otherValeurImperiale.getValeurEntiere();
    int fractionTop = this.getFractionTop() / p_otherValeurImperiale.getFractionTop();
    int fractionBot = this.getFractionBot() / p_otherValeurImperiale.getFractionBot();
    return new ValeurImperiale(valeurEntiere, fractionTop, fractionBot);
        }
    }

     public int imperialeToPixel(){
          
        float topBot = ((float) this.getFractionTop() / (float) this.getFractionBot());
        float valeurEntiere = ((float) this.getValeurEntiere());
        float calcul = (topBot + valeurEntiere) * 90;
      
        return Math.round(calcul);
    }

    @Override
    public String toString() {
        if (m_fractionTop == 0 || m_fractionBot == 1) {
            // If there is no fraction or the fraction is 1, return just the integer part
            return String.valueOf(m_valeurEntiere);
        } else {
            // If there is a fraction, return it in the form "integer.fraction"
            return m_valeurEntiere + "." + m_fractionTop + "/" + m_fractionBot;
        }
    }

    @Override
    public int compareTo(ValeurImperiale other) {
        // Compare the integer and fractional parts
        double thisValue = this.convertToDouble();
        double otherValue = other.convertToDouble();

        return Double.compare(thisValue, otherValue);
    }

    public double convertToDouble() {
        return (double) m_valeurEntiere + (double) m_fractionTop / (double) m_fractionBot;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ValeurImperiale other = (ValeurImperiale) obj;
        return this.compareTo(other) == 0;
    }

    @Override
    public int hashCode() {
        int result = getValeurEntiere();
        result = 31 * result + getFractionTop();
        result = 31 * result + getFractionBot();
        return result;
    }
    public UUID getUUID() {return uuid;}
}
