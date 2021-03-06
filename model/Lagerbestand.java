/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;
import helper.DatabaseManager;
import helper.Misc;
import java.sql.SQLException;
import java.util.List;

/**
 * Model für den Lagerbestand 
 * @author simon
 */
@DatabaseTable(tableName = "lagerbestand")

public class Lagerbestand {
    
    /* ID des Lagerbestands  */
    @DatabaseField(columnName = "lagerbestandID", generatedId = true)
    private int lagerbestandsnummer;
    
    /* verknüpftes Teile Model */
    @DatabaseField(columnName = "teilID", foreign = true, foreignAutoRefresh=true)
    private Teilebestand teil;
    
    /* verknüpftes Lagerfach Model */
    @DatabaseField(columnName = "fachID", foreign = true, foreignAutoRefresh=true)
    private Lagerfach lagerfach;
    
    /* Anzahl der eingelagerten Teile */
    @DatabaseField()
    private int anzahl;
    
    /* Text mit dem Anschaffungsgrund */
    @DatabaseField(columnName = "anschaffg")
    private String anschaffungsgrund;

    /**
     * @return the lagerbestandsnummer
     */
    public int getLagerbestandsnummer() {
        return lagerbestandsnummer;
    }

    /**
     * @param lagerbestandsnummer the lagerbestandsnummer to set
     */
    public void setLagerbestandsnummer(int lagerbestandsnummer) {
        this.lagerbestandsnummer = lagerbestandsnummer;
    }

    /**
     * @return the teil
     */
    public Teilebestand getTeil() {
        return teil;
    }

    /**
     * @param teil the teil to set
     */
    public void setTeil(Teilebestand teil) {
        this.teil = teil;
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
     * @return the menge
     */
    public int getMenge() {
        return anzahl;
    }

    /**
     * @param menge the menge to set
     */
    public void setMenge(int menge) {
        this.anzahl = menge;
    }

    /**
     * @return the anschaffungsgrund
     */
    public String getAnschaffungsgrund() {
        return anschaffungsgrund;
    }

    /**
     * @param anschaffungsgrund the anschaffungsgrund to set
     */
    public void setAnschaffungsgrund(String anschaffungsgrund) {
        this.anschaffungsgrund = anschaffungsgrund;
    }
    
    /**
     * Gibt den zur id gehörenden Lagerbestand zurück
     * @param id
     * @return
     * @throws SQLException 
     */    
    public static Lagerbestand getLagerbestand(int id) throws SQLException {
        Dao<Lagerbestand, Integer> LagerbestandDao = DatabaseManager.getInstance().getLagerbestandDao();
        List<Lagerbestand> lb = LagerbestandDao.queryForEq("lagerbestandID", id);
        if (lb.size() > 0) {
            return lb.get(0);
        }
        return null;
    }
    
    /**
     * Gibt den Lagerbestand zurück, der mit Teil- und FachId identifiziert werden
     * kann
     * @param teilid
     * @param fachid
     * @return
     * @throws SQLException 
     */
    public static Lagerbestand getLagerbestand(int teilid,int fachid) throws SQLException{
       int quellLbID = Lagerbestand.getLagerbestandID(teilid, fachid);
       return Lagerbestand.getLagerbestand(quellLbID);
    }
    
    /**
     *
     * @param teilid
     * @param fachid
     * @return
     * @throws SQLException
     */
    public static int getLagerbestandID(int teilid, int fachid) throws SQLException {

        Dao<Lagerbestand, Integer> lagerbestandDao = DatabaseManager.getInstance().getLagerbestandDao();
        QueryBuilder<Lagerbestand, Integer> queryBuilder = lagerbestandDao.queryBuilder();
        queryBuilder.where()
                .eq("teilID", teilid)
                .and()
                .eq("fachID", fachid);

        PreparedQuery<Lagerbestand> preparedQuery = queryBuilder.prepare();
        List<Lagerbestand> l = lagerbestandDao.query(preparedQuery);

        if (l.size() > 0) {
            return l.get(0).getLagerbestandsnummer();
        }
        return 0;

    }

   /**
    * @param Lagerbestand
    * @param MEnge
    * true, wenn im Lagerbestand nurnoch 1 Teil vorhanden ist
     *
     * @param teil
     * @return
     * @throws SQLException
     */
    public static boolean isLastTeil(Teilebestand teil) throws SQLException {
        int count = Teilebestand.countLagerbestand(teil);
        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * true, wenn der Lagerbestand <= Menge ist
     * @author ssinger
     * @param menge die geprüft werden soll
     * @return 
     */
    public boolean enoughLagerbestandCapacity(int menge) {
        if (this.getMenge() <= menge) {
            return true;
        }
        return false;
    }
    
    /**
     *
     * @return
     * @throws SQLException
     */
    public Warenbewegung getWarenbewegung() throws SQLException{
        Dao<model.Warenbewegung, Integer> warenbewegungDao = DatabaseManager.getInstance().getWarenbewegungDao();
        List<model.Warenbewegung> wbList = warenbewegungDao.queryForEq("lagerbestandID", this.getLagerbestandsnummer());

        if(wbList.size() > 0){
            return wbList.get(0);
        }
        return null;
    }

    /**
     * Speichert einen Lagerbestand
     * @throws SQLException
     */
    public void save() throws SQLException {
       Dao<Lagerbestand,Integer> LagerbestandDao = DatabaseManager.getInstance().getLagerbestandDao();
       LagerbestandDao.createOrUpdate(this);
    }
   
  

}