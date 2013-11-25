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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@DatabaseTable(tableName = "lagerbestand")
public class Lagerbestand {

    @DatabaseField(columnName = "lagerbestandID", generatedId = true)
    private int lagerbestandsnummer;
    
    @DatabaseField(columnName = "teilID", foreign = true, foreignAutoRefresh=true)
    private Teilebestand teil;
    
    @DatabaseField(columnName = "fachID", foreign = true, foreignAutoRefresh=true)
    private Lagerfach lagerfach;
    
    @DatabaseField()
    private int anzahl;
    
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
	//ab hier Artjoms Code, angepasst an Lagerfach-Model,Ausgabe Lagebestands
    
        public static Lagerbestand loadLagerObjekt(int id){
            Dao<Lagerbestand,Integer> lagerbestandDao = DatabaseManager.getInstance().getLagerbestandDao();
        try {
            List<Lagerbestand> lb = lagerbestandDao.queryForEq("lagerbestandID", id);
            if(lb.size()>0){
                return lb.get(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Lagerbestand.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
        }
        
        
        
	public static Lagerbestand getLagerbestand(int id) throws SQLException{
        Dao<Lagerbestand,Integer> LagerbestandDao = DatabaseManager.getInstance().getLagerbestandDao();
        List<Lagerbestand> lb = LagerbestandDao.queryForEq("lagerbestandID", id);
        if(lb.size()>0){
            return lb.get(0);
        }
        return null;
    }
    //Speichern Lagerbestand
   public void save() throws SQLException{
       Dao<Lagerbestand,Integer> LagerbestandDao = DatabaseManager.getInstance().getLagerbestandDao();
       LagerbestandDao.createOrUpdate(this);

}

}