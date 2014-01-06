/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import helper.DatabaseManager;
import helper.Misc;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public static Lager getLager (Lagerort ort) throws SQLException  {
        Dao<Lager,Integer> lagerDao = DatabaseManager.getInstance().getLagerDao();
        List<Lager> list;
        try {
            list = lagerDao.queryForEq("lagerort", ort);
            for (Lager f : list) {
                return f;
            }
        } 
        catch (SQLException ex) {
            Misc.printSQLException(null, ex);
        }
        return null;
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
     * Gibt den Lagerort zu einem LagerCode zurück
     * @return lagerort
     */
    public static Lager.Lagerort getLagerort(String lagerCode) {
        if(lagerCode.equals("FL")){
            return Lager.Lagerort.freilager;
        }
        else {
            return Lager.Lagerort.hochregal;
        }
    }
    
    /*
     * @author unknown
     * Gibt den Code des Lagerortes zurück
     * HL = Hochregallager
     * FL = Freilager
     */
    public String getLagerortCode() {
        String code = "";
        if(lagerort == Lager.Lagerort.freilager){
            code = "FL";
        }
        else {
            code = "HL";
        }
        return code;
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
    
    public void createFaecher(int breite, int tiefe, int hoehe) throws SQLException
    {
        for(int i = 1; i <= breite; i++){
            for(int j = 1; j <= tiefe;j++){
                for(int k = 1; k <= hoehe; k++){
                    Lagerfach fach = new Lagerfach();
                    fach.setLager(this);
                    fach.setX(i);
                    fach.setY(j);
                    fach.setZ(k);
                    fach.setGroesse(Lagerfach.Groesse.mittel);
                    fach.save();
                }
            }
        }
    }
    /**
     * Speichert das initialisierte Lager und initialisiert den Fächerbestand
     * 
     */
    public void save() throws SQLException
    {
        Dao<Lager, Integer> lagerDao;
        lagerDao = DatabaseManager.getInstance().getLagerDao();
        lagerDao.createOrUpdate(this);
        createFaecher(breite, tiefe, hoehe);
    }
    
    public List<Lagerfach> getFaecher() throws SQLException
    {
        Dao<Lagerfach,Integer> lagerfachDao = DatabaseManager.getInstance().getLagerfachDao();
        return lagerfachDao.queryForEq("lagerID", this);
    }
}