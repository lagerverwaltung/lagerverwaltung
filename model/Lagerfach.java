/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;
import helper.DatabaseManager;
import java.sql.SQLException;
import java.util.List;


@DatabaseTable(tableName = "lagerfach")
public class Lagerfach {
    @DatabaseField(columnName = "fachID", generatedId = true)
    private int fachnummer;

    @DatabaseField()
    private String bemerkung;
    
    @DatabaseField(columnName = "x")
    private int x;
    
    @DatabaseField(columnName = "y")
    private int y;
    
    @DatabaseField(columnName = "z")
    private int z;
    
    @DatabaseField()
    private Groesse groesse;
    
    @DatabaseField(columnName = "lagerID", canBeNull = false, foreign = true,foreignAutoRefresh=true)
    private Lager lager;
    /**
     * @return the fachnummer
     */
    public int getFachnummer() {
        return fachnummer;
    }

    /**
     * @param fachnummer the fachnummer to set
     */
    public void setFachnummer(int fachnummer) {
        this.fachnummer = fachnummer;
    }

    /**
     * @return the bemerkung
     */
    public String getBemerkung() {
        return bemerkung;
    }

    /**
     * @param bemerkung the bemerkung to set
     */
    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

     /**
     * @return the groesse
     */
    public Groesse getGroesse() {
        return groesse;
    }

    /**
     * @param groesse the groesse to set
     */
    public void setGroesse(Groesse groesse) {
        this.groesse = groesse;
    }

    /**
     * @return the lager
     */
    public Lager getLager() {
        return lager;
    }

    /*
    * @author ssinger
    * gibt den belegten Platzbedarf des Fachs in VE zurück
    */
    public int getUsedVe() throws SQLException{
        int usedVe = 0;
        Dao<Teilebestand, Integer> teilebestandDao = DatabaseManager.getInstance().getTeilebestandDao();
        Dao<Lagerbestand, Integer> lagerbestandDao = DatabaseManager.getInstance().getLagerbestandDao();
        List<Lagerbestand> lbList = lagerbestandDao.queryForEq("fachID", fachnummer);

        if (lbList.size() > 0) {
            for (int i = 0; i < lbList.size(); i++) {
                if(lbList.get(i) != null){
                    List<Teilebestand> tbList = teilebestandDao.queryForEq("teilID", lbList.get(i).getTeil().getIdentnummer());
                    if (tbList.size() > 0) {
                        for (int j = 0; j < tbList.size(); j++) {
                            usedVe = usedVe + lbList.get(i).getMenge() * tbList.get(j).getVe();
                        }

                    }
                }
            }

        }
        
        return usedVe;
    }
    
    /*
     * @author ssinger
     * Gibt die Kapazität des Fachs in VE 
     */
    public int getMaxVe(){
        if(groesse.equals(Groesse.klein)){
            return this.getLager().getGrossVE();
        }
        if(groesse.equals(Groesse.mittel)){
            return this.getLager().getMittelVE();
        }
        if(groesse.equals(Groesse.gross)){
            return this.getLager().getKleinVE();
        }
        return 0;
    }
    
    /*
    * @author ssinger
    * gibt die Anzahl der nicht belegten VE's zurück
    */
    public int getFreeVe() throws SQLException {
        int maxVe = Lagerfach.getLagerfach(fachnummer).getMaxVe();
        int usedVe = Lagerfach.getLagerfach(fachnummer).getUsedVe();

        return maxVe - usedVe;
    }
    
    /*
    * Gibt das Lagerfach mit angegebener id zurück
    */
    public static Lagerfach getLagerfach(int id) throws SQLException{
        Dao<Lagerfach,Integer> lagerfachDao = DatabaseManager.getInstance().getLagerfachDao();
        List<Lagerfach> l = lagerfachDao.queryForEq("fachID", id);
        if(l.size()>0){
            return l.get(0);
        }
        return null;
    }
    
    /*
     *  gibt Lagerfach zur lagerfachadresse aus
     * @author ssinger
    * @param String ort, x int, y int, z int
    * @return Lagerfach
    */
    public static Lagerfach getFach(Lager lager, int x, int y, int z) throws SQLException{

        Dao<Lagerfach, Integer> lagerfachDao = DatabaseManager.getInstance().getLagerfachDao();
        Dao<Lager, Integer> lagerDao = DatabaseManager.getInstance().getLagerDao();
        
        QueryBuilder<Lager, Integer> lagerQb = lagerDao.queryBuilder();
        QueryBuilder<Lagerfach, Integer> lagerfachQb = lagerfachDao.queryBuilder();
        
        lagerQb.where().eq("lagerort", lager.getLagerort());
        lagerfachQb.where().eq("x", x).and().eq("y", y).and().eq("z", z);
        
        List<Lagerfach> results = lagerfachQb.join(lagerQb).query();

        if (results.size() > 0) {
            return results.get(0);
        }
        return null;
}
    /*
    * speichert das Lagerfach
    */
    public void save() throws SQLException {
        Dao<Lagerfach, Integer> lagerfachDao = DatabaseManager.getInstance().getLagerfachDao();
        lagerfachDao.createOrUpdate(this);
    }
    /**
     * @param lager the lager to set
     */
    public void setLager(Lager lager) {
        this.lager = lager;
    }
    
    public String toString()
    {
        return getLager().getLagerortCode()+" "+getX()+" "+getY()+" "+getZ();
    }
	public enum Groesse {
	
		klein (),
		mittel (),
		gross (),
		;	
	
	}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }
        
    
	
	
	
	
	
}

