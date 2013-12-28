/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.filter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Lager;
import model.Teilebestand;

/**
 * StandardContainer für WarenbewegungFilter-Werte
 * @author ssinger
 */
public class WarenbewegungFilterModel {
    private Date haltbarVon;
    private Date haltbarBis;
    private Date datumVon;
    private Date datumBis;
    private Lager.Lagerort qLagerort;
    private int qZ;
    private int qX;
    private int qY;
    private Lager.Lagerort zLagerort;
    private int zZ;
    private int zX;
    private int zY;
    private String bezeichnung;
    private Teilebestand.Typ typ;
    
    /**
     * erzeugt ein standard Filter Objekt mit maximalen Schranken
     */
    public WarenbewegungFilterModel(){
        try {
            DateFormat f = new SimpleDateFormat("dd.MM.YYYY");
            haltbarVon = f.parse("01.01.1969");
            haltbarBis = f.parse("01.01.2542");
            datumVon = haltbarVon;
            datumBis = haltbarBis;
        } catch (ParseException ex) {
            System.out.println("Nothing to Catch");
        }
        qZ = 0;
        qX = 0;
        qY = 0;
        zZ = 0;
        zX = 0;
        zY = 0;
        bezeichnung = "";
        
    }

    /**
     * @return the vonHaltbarkeit
     */
    public Date getVonHaltbarkeit() {
        return getHaltbarVon();
    }

    /**
     * @param vonHaltbarkeit the vonHaltbarkeit to set
     */
    public void setVonHaltbarkeit(Date vonHaltbarkeit) {
        this.setHaltbarVon(vonHaltbarkeit);
    }

    /**
     * @return the bisHaltbarkeit
     */
    public Date getBisHaltbarkeit() {
        return getHaltbarBis();
    }

    /**
     * @param bisHaltbarkeit the bisHaltbarkeit to set
     */
    public void setBisHaltbarkeit(Date bisHaltbarkeit) {
        this.setHaltbarBis(bisHaltbarkeit);
    }

    /**
     * @return the qLagerort
     */
    public Lager.Lagerort getqLagerort() {
        return qLagerort;
    }

    /**
     * @param qLagerort the qLagerort to set
     */
    public void setqLagerort(Lager.Lagerort qLagerort) {
        this.qLagerort = qLagerort;
    }

    /**
     * @return the qZ
     */
    public int getqZ() {
        return qZ;
    }

    /**
     * @param qZ the qZ to set
     */
    public void setqZ(int qZ) {
        this.qZ = qZ;
    }

    /**
     * @return the qX
     */
    public int getqX() {
        return qX;
    }

    /**
     * @param qX the qX to set
     */
    public void setqX(int qX) {
        this.qX = qX;
    }

    /**
     * @return the qY
     */
    public int getqY() {
        return qY;
    }

    /**
     * @param qY the qY to set
     */
    public void setqY(int qY) {
        this.qY = qY;
    }

    /**
     * @return the zLagerort
     */
    public Lager.Lagerort getzLagerort() {
        return zLagerort;
    }

    /**
     * @param zLagerort the zLagerort to set
     */
    public void setzLagerort(Lager.Lagerort zLagerort) {
        this.zLagerort = zLagerort;
    }

    /**
     * @return the zZ
     */
    public int getzZ() {
        return zZ;
    }

    /**
     * @param zZ the zZ to set
     */
    public void setzZ(int zZ) {
        this.zZ = zZ;
    }

    /**
     * @return the zX
     */
    public int getzX() {
        return zX;
    }

    /**
     * @param zX the zX to set
     */
    public void setzX(int zX) {
        this.zX = zX;
    }

    /**
     * @return the zY
     */
    public int getzY() {
        return zY;
    }

    /**
     * @param zY the zY to set
     */
    public void setzY(int zY) {
        this.zY = zY;
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
        this.bezeichnung = bezeichnung;
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
     * @return the haltbarVon
     */
    public Date getHaltbarVon() {
        return haltbarVon;
    }

    /**
     * @param haltbarVon the haltbarVon to set
     */
    public void setHaltbarVon(Date haltbarVon) {
        this.haltbarVon = haltbarVon;
    }

    /**
     * @return the haltbarBis
     */
    public Date getHaltbarBis() {
        return haltbarBis;
    }

    /**
     * @param haltbarBis the haltbarBis to set
     */
    public void setHaltbarBis(Date haltbarBis) {
        this.haltbarBis = haltbarBis;
    }

    /**
     * @return the datumVon
     */
    public Date getDatumVon() {
        return datumVon;
    }

    /**
     * @param datumVon the datumVon to set
     */
    public void setDatumVon(Date datumVon) {
        this.datumVon = datumVon;
    }

    /**
     * @return the datumBis
     */
    public Date getDatumBis() {
        return datumBis;
    }

    /**
     * @param datumBis the datumBis to set
     */
    public void setDatumBis(Date datumBis) {
        this.datumBis = datumBis;
    }
}
