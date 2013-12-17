/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.filter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Lager;
import model.Teilebestand;

/**
 *
 * @author ssinger
 */
public class WarenbewegungFilterModel {
    private Date vonHaltbarkeit;
    private Date bisHaltbarkeit;
    private String verantwortlicher;
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
    
    public WarenbewegungFilterModel(){
        try {
            DateFormat f = new SimpleDateFormat("dd.MM.YYYY");
            vonHaltbarkeit = f.parse("01.01.1969");
            bisHaltbarkeit = f.parse("01.01.2542");
        } catch (ParseException ex) {
            System.out.println("Nothing to Catch");
            
        }
        verantwortlicher = "Lagerverwalter";
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
        return vonHaltbarkeit;
    }

    /**
     * @param vonHaltbarkeit the vonHaltbarkeit to set
     */
    public void setVonHaltbarkeit(Date vonHaltbarkeit) {
        this.vonHaltbarkeit = vonHaltbarkeit;
    }

    /**
     * @return the bisHaltbarkeit
     */
    public Date getBisHaltbarkeit() {
        return bisHaltbarkeit;
    }

    /**
     * @param bisHaltbarkeit the bisHaltbarkeit to set
     */
    public void setBisHaltbarkeit(Date bisHaltbarkeit) {
        this.bisHaltbarkeit = bisHaltbarkeit;
    }

    /**
     * @return the verantwortlicher
     */
    public String getVerantwortlicher() {
        return verantwortlicher;
    }

    /**
     * @param verantwortlicher the verantwortlicher to set
     */
    public void setVerantwortlicher(String verantwortlicher) {
        this.verantwortlicher = verantwortlicher;
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
}
