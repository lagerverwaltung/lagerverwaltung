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
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;
import helper.DatabaseManager;
import helper.Misc;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import view.BestandsaenderungFrame;

/**
 * Modeklasse für eine Warenbewegung
 * 
 * @author Simon
 */
@DatabaseTable(tableName = "warenbewegung")
public class Warenbewegung {
    /* Feld zur Identifizierung der Warenbewegung */
    @DatabaseField(columnName = "warenbID", generatedId = true)
    private int warenBewegungsID;
      
    /* Liste mit den zugehörigen 1 bis n Zielen  */
    @ForeignCollectionField(eager = true)
    ForeignCollection<ZielPosition> arrZielPosition;
    
    /* Der Name des Verantwortlichen für die Warenbewegung */
    @DatabaseField()
    private String verantwortlicher;
    
    /* Die ID für die Art der Warenbewegung */
    @DatabaseField()
    private int actionCode;
    
    /* Das zugehörige Lagerbestand Objekt */
    @DatabaseField(columnName = "lagerbestandID", foreign = true, foreignAutoRefresh=true, maxForeignAutoRefreshLevel=4)
    private Lagerbestand lagerbestand;
    
    /* Das Datum an dem die Warenbewegung durchgeführt wurde */
    @DatabaseField()
    private java.util.Date datum;
    
    /* Das Haltbarkeitsdatum der bewegten Teile */
    @DatabaseField()
    private java.util.Date haltbarkeitsDatum;

    /* Der Grund für die Warenbewegung */
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
    
    /**
     *
     * @return
     */
    public int getActionCode() {
        return actionCode;
    }
    
    /* 
     * Die ID für die Art der Warenbewegung
     * 
     * @return String Name der Aktion
     */
    /**
     *
     * @return
     */
    public String getActionName() {
        switch(getActionCode()){
            case BestandsaenderungFrame.EINLAGERN_TEILEBESTAND:
                return "Einlagern";
            case BestandsaenderungFrame.EINLAGERN_LAGERBESTAND:
                return "Einlagern";
            case BestandsaenderungFrame.AUSLAGERN:
                return "Auslagern";
            case BestandsaenderungFrame.UMLAGERN:
                return "Umlagern";
            case BestandsaenderungFrame.SPLITTEN:
                return "Splitten";
        }
        return "n/a";
    }

    /**
     *
     * @param actionCode
     */
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
    
    /**
     *
     * @return
     */
    public String getAnschaffungsgrund() {
        return anschaffungsgrund;
    }

    /**
     *
     * @param anschaffungsgrund
     */
    public void setAnschaffungsgrund(String anschaffungsgrund) {
        this.anschaffungsgrund = anschaffungsgrund;
    }
	
    /**
     * Läd eine Warenbewegung
     * @param id ID der Warenbewegung
     * @return Liefert eine Warenbewegung
     * @throws SQLException
     */
    public static Warenbewegung loadWarenbewegung(int id) throws SQLException
    {
        Dao<Warenbewegung,Integer> warenbewegungDao = DatabaseManager.getInstance().getWarenbewegungDao();
        try {
            List<Warenbewegung> lt = warenbewegungDao.queryForEq("warenbID", id);
            if(lt.size()>0){
                return lt.get(0);
            }
        } catch (SQLException ex) {
            Misc.printSQLException(null, ex);
        }
        return null;
    }
	
    /**
     * Speichert eine Warenbewegung 
     * @throws SQLException
     */
    public void save() throws SQLException{
       Dao<Warenbewegung,Integer> warenbewegungDao = DatabaseManager.getInstance().getWarenbewegungDao();
       warenbewegungDao.createOrUpdate(this);
   }

    /**
     * Erstellt aus Bewegungsdaten eine Warenbewegung und fügt die Ziele an
     * @param lagerbestand Der Lagerbestand
     * @param actionCode Art der Aktion
     * @param grund Bewegungsgrund
     * @param datum Bewegungsdatum
     * @param haltbarkeitsDatum Haltbarkeitsdatum des bewegten Bulks
     * @param verantwortlicher Verantwortlicher
     * @param zp Liste mit ZielPositionen
     * @throws SQLException
     */
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
    
    /**
     * Liefert das zuletzt angegebene Haltbarkeitsdatum des Lagerbestands
     * @param lb Lagerbestand
     * @return Date HaltbarkeitsDatum
     * @throws SQLException
     */
    public static Date getLastHaltbarkeitsdatum(Lagerbestand lb) throws SQLException{
        List<Lagerbestand> result = new ArrayList();
        Date today = new Date();
        Date hbDate = null;
        Dao<Warenbewegung,Integer> warenbewegungDao = DatabaseManager.getInstance().getWarenbewegungDao();
        QueryBuilder<Warenbewegung, Integer> warenbQb = warenbewegungDao.queryBuilder();
        
         Dao<Lagerbestand, Integer> lagerbestandDao = DatabaseManager.getInstance().getLagerbestandDao();
        QueryBuilder<Lagerbestand, Integer> lagerbestandQb = lagerbestandDao.queryBuilder();
        
        lagerbestandQb.where().eq("teilID", lb.getTeil())
                .and().eq("fachID", lb.getLagerfach());
         
        warenbQb.join(lagerbestandQb);
        warenbQb.orderBy("warenbID", false);
        //warenbQb.orderBy("lagerbestandID", false);
                
        PreparedQuery<Warenbewegung> preparedQuery = warenbQb.prepare();
        List<Warenbewegung> wbResult = warenbewegungDao.query(preparedQuery);
        
        if (wbResult.size() > 0) {
            for (int i = 0; i < wbResult.size(); i++) {
                if(wbResult.get(i).getHaltbarkeitsDatum() != null){
                    hbDate = wbResult.get(i).getHaltbarkeitsDatum();
                    break;
                }
            }
        }
        return hbDate;
    }
    
}
