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
import java.sql.SQLException;
import java.util.ArrayList;

@DatabaseTable(tableName = "zielposition")
public class ZielPosition {

    @DatabaseField(generatedId = true)
    private int zielPositionID;
    
    @DatabaseField()
    private int menge;
    
    @DatabaseField(columnName = "warenbID", foreign = true, foreignAutoRefresh=true)
    private Warenbewegung warenbewegung;
    
    @DatabaseField(columnName = "fachID", foreign = true,foreignAutoRefresh=true)
    private Lagerfach lagerfach;

    /**
     * @return the menge
     */
    public int getMenge() {
        return menge;
    }

    /**
     * @param menge the menge to set
     */
    public void setMenge(int menge) {
        this.menge = menge;
    }

    public int getZielPositionID() {
        return zielPositionID;
    }

    public void setZielPositionID(int zielPositionID) {
        this.zielPositionID = zielPositionID;
    }

    public Warenbewegung getWarenbewegung() {
        return warenbewegung;
    }

    public void setWarenbewegung(Warenbewegung warenbewegung) {
        this.warenbewegung = warenbewegung;
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

    public void save() throws SQLException{
       Dao<ZielPosition,Integer> zielPositionsDao = DatabaseManager.getInstance().getZielpositionDao();
       zielPositionsDao.createOrUpdate(this);
   }
    /**
     * Erzeugt eine Liste mit ZielPositionen aus rohen Positionsdaten-Matrix
     * @param String[] fachCode Enthält die Spalte mit der Lager Adresse 
     * @param int[] x Enthält die Spalte mit x Koordinaten
     * @param int[] y Enthält die Spalte mit y Koordinaten
     * @param int[] z Enthält die Spalte mit z Koordinaten
     * @param int[] qty  Enthält die Menge des Zieles
     * @return ArrayList<ZielPosition> ein Array mit ZielPosition Objekten
    * 
    */
    public static ArrayList<ZielPosition> createZielPositionen (String[] fachCode, 
                                                         int[] x,
                                                         int[] y,
                                                         int[] z,
                                                         int[] qty) throws SQLException{
        ArrayList<ZielPosition> ziele = new ArrayList();
        Lagerfach fach;
        Lager lager;
        ZielPosition ziel;
        for(int i = 0; i< fachCode.length;i++){
            lager = Lager.getLager(Lager.getLagerort(fachCode[i]));
            fach = Lagerfach.getFach(lager, x[i], y[i],z[i]);
            ziel = new ZielPosition();
            ziel.setLagerfach(fach);
            ziel.setMenge(qty[i]);
            ziele.add(ziel);
        }
        return ziele;
    }
}
