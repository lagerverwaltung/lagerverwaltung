/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import helper.DatabaseManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ssinger
 */
@DatabaseTable(tableName = "warenbewegung")
public class Warenbewegung {
    
    @DatabaseField(columnName = "warenbID", generatedId = true)
    private int warenBewegungsID;
      
    @ForeignCollectionField(eager = true)
    ForeignCollection<ZielPosition> arrZielPosition;
    
    @DatabaseField()
    private String verantwortlicher;
    
    @DatabaseField()
    private int actionCode;
    
    @DatabaseField(columnName = "lagerbestandID", foreign = true, foreignAutoRefresh=true)
    private Lagerbestand lagerbestand;
    
    @DatabaseField()
    private java.util.Date datum;
     
    @DatabaseField()
    private java.util.Date haltbarkeitsDatum;

    @DatabaseField(columnName = "anschaffg")
    private String anschaffungsgrund;

    /**
     * @return the arrZielPosition
     */
    public ForeignCollection<ZielPosition> getArrZielPosition() {
        return arrZielPosition;
    }
    
    /**
     * @param arrZielPosition the arrZielPosition to set
     */
    public void setArrZielPosition(ForeignCollection<ZielPosition> arrZielPosition) {
        this.arrZielPosition = arrZielPosition;
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
     * @return the warenBewegungsID
     */
    public int getWarenBewegungsID() {
        return warenBewegungsID;
    }

    /**
     * @param warenBewegungsID the warenBewegungsID to set
     */
    public void setWarenBewegungsID(int warenBewegungsID) {
        this.warenBewegungsID = warenBewegungsID;
    }

    /**
     * @return the lagerbestand
     */
    public Lagerbestand getLagerbestand() {
        return lagerbestand;
    }

    /**
     * @param lagerbestand the lagerbestand to set
     */
    public void setLagerbestand(Lagerbestand lagerbestand) {
        this.lagerbestand = lagerbestand;
    }

    /**
     * @return the datum
     */
    public java.util.Date getDatum() {
        return datum;
    }

    /**
     * @param datum the datum to set
     */
    public void setDatum(java.util.Date datum) {
        this.datum = datum;
    }
    
    public int getActionCode() {
        return actionCode;
    }

    public void setActionCode(int actionCode) {
        this.actionCode = actionCode;
    }

    /**
     * @return the haltbarkeitsDatum
     */
    public java.util.Date getHaltbarkeitsDatum() {
        return haltbarkeitsDatum;
    }

    /**
     * @param haltbarkeitsDatum the haltbarkeitsDatum to set
     */
    public void setHaltbarkeitsDatum(java.util.Date haltbarkeitsDatum) {
        this.haltbarkeitsDatum = haltbarkeitsDatum;
    }
    
    public String getAnschaffungsgrund() {
        return anschaffungsgrund;
    }

    public void setAnschaffungsgrund(String anschaffungsgrund) {
        this.anschaffungsgrund = anschaffungsgrund;
    }
	
    public static Warenbewegung loadWarenbewegung(int id) throws SQLException
    {
        Dao<Warenbewegung,Integer> warenbewegungDao = DatabaseManager.getInstance().getWarenbewegungDao();
        try {
            List<Warenbewegung> lt = warenbewegungDao.queryForEq("warenbID", id);
            if(lt.size()>0){
                return lt.get(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Warenbewegung.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
	
   public void save() throws SQLException{
       Dao<Warenbewegung,Integer> warenbewegungDao = DatabaseManager.getInstance().getWarenbewegungDao();
       warenbewegungDao.createOrUpdate(this);
   }

    public void logWarenbewegung( Lagerbestand lagerbestand, int actionCode, String grund, Date datum, 
                                Date haltbarkeitsDatum, String verantwortlicher, 
                                ArrayList<ZielPosition> zp) throws SQLException {
        this.setActionCode(actionCode);
        this.setAnschaffungsgrund(grund);
        this.setLagerbestand(lagerbestand);
        this.setDatum(datum);
        this.setHaltbarkeitsDatum(haltbarkeitsDatum);
        this.setVerantwortlicher(verantwortlicher);
        this.save();
        for (ZielPosition z : zp) {
            z.setWarenbewegung(this);
            z.save();
        }
    }
}
