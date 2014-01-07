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
}
