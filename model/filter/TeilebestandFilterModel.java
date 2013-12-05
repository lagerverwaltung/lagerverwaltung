/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.filter;

import model.Lager;

/**
 *
 * @author ssinger
 */
public class TeilebestandFilterModel {
    
    private String materialgruppe;
    private int vonVe;
    private int bisVe;
    private float vonPreis;
    private float bisPreis;
    private String zeichnungsnummer;
    
    /*
     * Initialisiert den Filter in Grundeinstellungen
     */
    public TeilebestandFilterModel() {
        materialgruppe = "%%";
        vonVe = 0;
        if (Lager.getLager(Lager.Lagerort.hochregal).getGrossVE() > Lager.getLager(Lager.Lagerort.freilager).getGrossVE()) {
            bisVe = Lager.getLager(Lager.Lagerort.hochregal).getGrossVE();
        } else {
            bisVe = Lager.getLager(Lager.Lagerort.freilager).getGrossVE();
        }
        vonPreis = 0;
        bisPreis = Float.MAX_VALUE;
        zeichnungsnummer = "%%";
    }

    /**
     * @return the matGr
     */
    public String getMaterialgruppr() {
        return materialgruppe;
    }

    /**
     * @param matGr the matGr to set
     */
    public void setMaterialgruppe(String matGr) {
        this.materialgruppe = matGr;
    }

    /**
     * @return the vonVe
     */
    public int getVonVe() {
        return vonVe;
    }

    /**
     * @param vonVe the vonVe to set
     */
    public void setVonVe(int vonVe) {
        this.vonVe = vonVe;
    }

    /**
     * @return the bisVe
     */
    public int getBisVe() {
        return bisVe;
    }

    /**
     * @param bisVe the bisVe to set
     */
    public void setBisVe(int bisVe) {
        this.bisVe = bisVe;
    }

    /**
     * @return the vonPreis
     */
    public float getVonPreis() {
        return vonPreis;
    }

    /**
     * @param vonPreis the vonPreis to set
     */
    public void setVonPreis(float vonPreis) {
        this.vonPreis = vonPreis;
    }

    /**
     * @return the bisPreis
     */
    public float getBisPreis() {
        return bisPreis;
    }

    /**
     * @param bisPreis the bisPreis to set
     */
    public void setBisPreis(float bisPreis) {
        this.bisPreis = bisPreis;
    }

    /**
     * @return the zeichNr
     */
    public String getZeichnungsnummer() {
        return zeichnungsnummer;
    }

    /**
     * @param zeichNr the zeichNr to set
     */
    public void setZeichnungsnummer(String zeichNr) {
        this.zeichnungsnummer = zeichNr;
    }
}
