package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;
import helper.DatabaseManager;
import java.sql.SQLException;
import java.util.List;


/**
 * Model für ein Lagerfach
 * @author simon
 */
@DatabaseTable(tableName = "lagerfach")
public class Lagerfach {
    
     /* Fach ID  */
    @DatabaseField(columnName = "fachID", generatedId = true)
    private int fachnummer;

     /* Bemerkung zum Fach */
    @DatabaseField()
    private String bemerkung;
    
     /* x Koordinate des Faches  */
    @DatabaseField(columnName = "x")
    private int x;
    
    /* y Koordinate des Faches  */
    @DatabaseField(columnName = "y")
    private int y;
    
    /* z Koordinate des Faches  */
    @DatabaseField(columnName = "z")
    private int z;
    
    /* Groesse des Faches  */
    @DatabaseField()
    private Groesse groesse;
    
    /* verknuepftes Lager  */
    @DatabaseField(columnName = "lagerID", canBeNull = false, foreign = true, foreignAutoRefresh=true)
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

    /**
     * Gibt den belegten Platzbedarf des Fachs in VE zurück
     * @author ssinger
     * 
     * @return Anzahl der verbrauchten VE
     * @throws SQLException
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
    
   /**
     * @author ssinger
     * 
     * Gibt die Kapazität des Fachs in VE 
     *
     * @return Maximale Fachgröße
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
    
     /**
     * @author ssinger
     * gibt die Anzahl der nicht belegten VE's zurück
     *
     * @return
     * @throws SQLException
     */
    public int getFreeVe() throws SQLException {
        int maxVe = Lagerfach.getLagerfach(fachnummer).getMaxVe();
        int usedVe = Lagerfach.getLagerfach(fachnummer).getUsedVe();

        return maxVe - usedVe;
    }
    
    /**
     * Gibt das Lagerfach mit angegebener id zurück
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public static Lagerfach getLagerfach(int id) throws SQLException{
        Dao<Lagerfach,Integer> lagerfachDao = DatabaseManager.getInstance().getLagerfachDao();
        List<Lagerfach> l = lagerfachDao.queryForEq("fachID", id);
        if(l.size()>0){
            return l.get(0);
        }
        return null;
    }
    
    /**
     * Gibt Lagerfach zur lagerfachadresse aus
     * @author ssinger
     * @param String ort, x int, y int, z int
     * @return Lagerfach
     *
     * @param lager
     * @param x
     * @param y
     * @param z
     * @return
     * @throws SQLException
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
    /**
     * speichert das Lagerfach
     *
     * @throws SQLException
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
    
    /**
     * Enum Klasse für die Fachgröße
     */
    public enum Groesse {
        klein(),
        mittel(),
        gross(),;
    }

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @return
     */
    public int getZ() {
        return z;
    }

    /**
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @param z
     */
    public void setZ(int z) {
        this.z = z;
    }
    
    /**
     *
     * @param lf
     * @return
     */
    public boolean equals(Lagerfach lf){
       if(lf.getFachnummer() == this.getFachnummer()){
           return true;
       }
       return false;
   }

}

