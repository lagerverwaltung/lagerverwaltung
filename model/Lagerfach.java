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
    
    @DatabaseField(columnName = "lagerID", canBeNull = false, foreign = true)
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

    public static Lagerfach getLagerfach(int id) throws SQLException{
        Dao<Lagerfach,Integer> lagerfachDao = DatabaseManager.getInstance().getLagerfachDao();
        List<Lagerfach> l = lagerfachDao.queryForEq("fachID", id);
        if(l.size()>0){
            return l.get(0);
        }
        return null;
    }
    //gibt Lagerfach zur lagerfachadresse aus
    
    public static Lagerfach getFach(String ort, int x, int y, int z) throws SQLException{
        
        int lo = 1;
        if(ort.equals(Lager.Lagerort.freilager.toString())){
            lo = 2;
        }
        
	 Dao<Lagerfach,Integer> lagerfachDao = DatabaseManager.getInstance().getLagerfachDao();
	QueryBuilder<Lagerfach, Integer> queryBuilder = lagerfachDao.queryBuilder();
	queryBuilder	.where()
                	.eq("LagerID", lo )
			.and()
			.eq("x", x)
			.and()
                	.eq("y", y)
			.and()
			.eq("z", z);	
	PreparedQuery<Lagerfach> preparedQuery = queryBuilder.prepare();
        List<Lagerfach> l = lagerfachDao.query(preparedQuery);
        
        if(l.size()>0){
            return l.get(0);
        }
        return null;
}
    
   public void save() throws SQLException{
       Dao<Lagerfach,Integer> lagerfachDao = DatabaseManager.getInstance().getLagerfachDao();
       lagerfachDao.createOrUpdate(this);
   }
    /**
     * @param lager the lager to set
     */
    public void setLager(Lager lager) {
        this.lager = lager;
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

