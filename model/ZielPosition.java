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

/**
 * Model für eine ZielPosition
 * @author simon
 */
@DatabaseTable(tableName = "zielposition")
public class ZielPosition {

    /* ID der Zielposition  */
    @DatabaseField(generatedId = true)
    private int zielPositionID;
    
    /* Menge die an das Ziel übermittelt wird  */
    @DatabaseField()
    private int menge;
    
    /* die zugehörige Warenbewegung  */
    @DatabaseField(columnName = "warenbID", foreign = true, foreignAutoRefresh=true)
    private Warenbewegung warenbewegung;
    
    /* Das zugehörige Fach */
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

    /**
     *
     * @return
     */
    public int getZielPositionID() {
        return zielPositionID;
    }

    /**
     *
     * @param zielPositionID
     */
    public void setZielPositionID(int zielPositionID) {
        this.zielPositionID = zielPositionID;
    }

    /**
     *
     * @return
     */
    public Warenbewegung getWarenbewegung() {
        return warenbewegung;
    }

    /**
     *
     * @param warenbewegung
     */
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

    /**
     * Speichert eine ZielPosition
     * @throws SQLException
     */
    public void save() throws SQLException{
       Dao<ZielPosition,Integer> zielPositionsDao = DatabaseManager.getInstance().getZielpositionDao();
       zielPositionsDao.createOrUpdate(this);
   }
}
