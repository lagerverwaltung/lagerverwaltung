/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.filter;

import helper.Misc;
import java.sql.SQLException;
import model.Lager;
import model.Teilebestand;

/**
 * StandardContainer für TeilebestandFilter-Werte
 * @author ssinger
 */
public class TeilebestandFilterModel {
    
    private Teilebestand.Typ typ;
    private String materialgruppe;
    private int vonVe;
    private int bisVe;
    private float vonPreis;
    private float bisPreis;
    private String zeichnungsnummer;
    private String bezeichnung;
    
    /**
     * Erstellt ein standardFilterModel Objekt mit maximalen Schranken
     * @author ssinger
     */
    public TeilebestandFilterModel(){
        typ = null;
        materialgruppe = "%%";
        vonVe = 0;
        bisVe = Integer.MAX_VALUE;
        vonPreis = 0;
        bisPreis = Float.MAX_VALUE;
        zeichnungsnummer = "%%";
        bezeichnung = "%%";
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
        this.materialgruppe = "%"+matGr+"%";
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
        this.zeichnungsnummer = "%"+zeichNr+"%";
    }

    /**
     * @return the typ
     */
    public Teilebestand.Typ getTyp() {
        return typ;
    }

    /**
     * @param typ the typ to set
     */
    public void setTyp(Teilebestand.Typ typ) {
        this.typ = typ;
    }

    /**
     * @return the bezeichnung
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * @param bezeichnung the bezeichnung to set
     */
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = "%"+bezeichnung+"%";
    }
}
