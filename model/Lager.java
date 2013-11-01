/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "lager")
public class Lager {
    
    @DatabaseField(generatedId = true)
    private int lagerID;
    
    @DatabaseField(columnName = "hoehe", canBeNull = false)
    private int hoehe;
    
    @DatabaseField(columnName = "breite", canBeNull = false)
    private int breite;
    
    @DatabaseField(columnName = "tiefe", canBeNull = false)
    private int tiefe;
    
    @DatabaseField(columnName = "lagerort", canBeNull = false)
    private Lager.Lagerort lagerort;
    
    @DatabaseField(columnName = "kleinVE", canBeNull = false)
    private int kleinVE;
    
    @DatabaseField(columnName = "mittelVE", canBeNull = false)
    private int mittelVE;
    
    @DatabaseField(columnName = "grossVE", canBeNull = false)
    private int grossVE;

    public Lager () {
    
    }
    /**
     * @return the hoehe
     */
    public int getHoehe() {
        return hoehe;
    }

    /**
     * @param hoehe the hoehe to set
     */
    public void setHoehe(int hoehe) {
        this.hoehe = hoehe;
    }

    /**
     * @return the breite
     */
    public int getBreite() {
        return breite;
    }

    /**
     * @param breite the breite to set
     */
    public void setBreite(int breite) {
        this.breite = breite;
    }

    /**
     * @return the tiefe
     */
    public int getTiefe() {
        return tiefe;
    }

    /**
     * @param tiefe the tiefe to set
     */
    public void setTiefe(int tiefe) {
        this.tiefe = tiefe;
    }

    /**
     * @return the lagerort
     */
    public Lager.Lagerort getLagerort() {
        return lagerort;
    }

    /**
     * @param lagerort the lagerort to set
     */
    public void setLagerort(Lager.Lagerort lagerort) {
        this.lagerort = lagerort;
    }

    /**
     * @return the lagerID
     */
    public int getLagerID() {
        return lagerID;
    }

    /**
     * @param lagerID the lagerID to set
     */
    public void setLagerID(int lagerID) {
        this.lagerID = lagerID;
    }

    /**
     * @return the kleinVE
     */
    public int getKleinVE() {
        return kleinVE;
    }

    /**
     * @param kleinVE the kleinVE to set
     */
    public void setKleinVE(int kleinVE) {
        this.kleinVE = kleinVE;
    }

    /**
     * @return the mittelVE
     */
    public int getMittelVE() {
        return mittelVE;
    }

    /**
     * @param mittelVE the mittelVE to set
     */
    public void setMittelVE(int mittelVE) {
        this.mittelVE = mittelVE;
    }

    /**
     * @return the grossVE
     */
    public int getGrossVE() {
        return grossVE;
    }

    /**
     * @param grossVE the grossVE to set
     */
    public void setGrossVE(int grossVE) {
        this.grossVE = grossVE;
    }
	
    public enum Lagerort {
            freilager (),
            hochregal (),
            ;	
    }
	

}