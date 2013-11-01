/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "lagerbestand")
public class Lagerbestand {

    @DatabaseField(columnName = "lagerbestandID", generatedId = true)
    private int lagerbestandsnummer;
    
    @DatabaseField(columnName = "teilID", foreign = true)
    private Teilebestand teil;
    
    @DatabaseField(columnName = "fachID", foreign = true)
    private Lagerfach lagerfach;
    
    @DatabaseField()
    private int anzahl;
    
    @DatabaseField(columnName = "anschaffg")
    private String anschaffungsgrund;

    /**
     * @return the lagerbestandsnummer
     */
    public int getLagerbestandsnummer() {
        return lagerbestandsnummer;
    }

    /**
     * @param lagerbestandsnummer the lagerbestandsnummer to set
     */
    public void setLagerbestandsnummer(int lagerbestandsnummer) {
        this.lagerbestandsnummer = lagerbestandsnummer;
    }

    /**
     * @return the teil
     */
    public Teilebestand getTeil() {
        return teil;
    }

    /**
     * @param teil the teil to set
     */
    public void setTeil(Teilebestand teil) {
        this.teil = teil;
    }

    /**
     * @return the lagerfach
     */
    public Lagerfach getLagerfach() {
        return lagerfach;
    }

    /**
     * @param lagerfach the lagerfach to set
     */
    public void setLagerfach(Lagerfach lagerfach) {
        this.lagerfach = lagerfach;
    }

    /**
     * @return the menge
     */
    public int getMenge() {
        return anzahl;
    }

    /**
     * @param menge the menge to set
     */
    public void setMenge(int menge) {
        this.anzahl = menge;
    }

    /**
     * @return the anschaffungsgrund
     */
    public String getAnschaffungsgrund() {
        return anschaffungsgrund;
    }

    /**
     * @param anschaffungsgrund the anschaffungsgrund to set
     */
    public void setAnschaffungsgrund(String anschaffungsgrund) {
        this.anschaffungsgrund = anschaffungsgrund;
    }
	
	

}

