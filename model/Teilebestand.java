
package model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import helper.DatabaseManager;
import helper.Misc;
import java.sql.SQLException;
import java.util.List;

/**
 * Model für den Teilebestand
 * @author simon
 */
@DatabaseTable(tableName = "teilebestand")
public class Teilebestand {

     /* Teile ID  */
    @DatabaseField(columnName = "teilID", generatedId = true)
    private int identnummer;
    
    /* Bezeichnung des Teils  */
    @DatabaseField()
    private String bezeichnung;
    
    /* Materialgruppe des Teils */
    @DatabaseField()
    private String materialgruppe;
    
    /* Zeichnungsnummer des Teils */
    @DatabaseField()
    private String zeichnungsnummer;
    
    /* Preis des Teils */
    @DatabaseField()
    private float preis;
    
    /* Teiltyp */
    @DatabaseField()
    private Typ typ;
    
    /* VE die das Teile einnimmt  */
    @DatabaseField()
    private int ve;
    
    /* Löschstatus des Teils  */
    @DatabaseField()
    private boolean deleted;

    /**
     * 
     * @return
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     *
     * @param isDeleted
     * @throws SQLException
     */
    public void setDeleted(boolean isDeleted) throws SQLException {
        this.deleted = isDeleted;
        save();
    }

    /**
     * @return the identnummer
     */
    public int getIdentnummer() {
        return identnummer;
    }

    /**
     * @param identnummer the identnummer to set
     */
    public void setIdentnummer(int identnummer) {
        this.identnummer = identnummer;
    }

    /**
     * @return the bezeichnung
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * @param bezeichnung the bezeichnung to set
     */
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    /**
     * @return the materialgruppe
     */
    public String getMaterialgruppe() {
        return materialgruppe;
    }

    /**
     * @param materialgruppe the materialgruppe to set
     */
    public void setMaterialgruppe(String materialgruppe) {
        this.materialgruppe = materialgruppe;
    }

    /**
     * @return the zeichnungsnummer
     */
    public String getZeichnungsnummer() {
        return zeichnungsnummer;
    }

    /**
     * @param zeichnungsnummer the zeichnungsnummer to set
     */
    public void setZeichnungsnummer(String zeichnungsnummer) {
        this.zeichnungsnummer = zeichnungsnummer;
    }

    /**
     * @return the preis
     */
    public float getPreis() {
        return preis;
    }

    /**
     * @param preis the preis to set
     */
    public void setPreis(float preis) {
        this.preis = preis;
    }

    /**
     * @return the typ
     */
    public Typ getTyp() {
        return typ;
    }

    /**
     * @param typ the typ to set
     */
    public void setTyp(Typ typ) {
        this.typ = typ;
    }

    /**
     * @return the ve
     */
    public int getVe() {
        return ve;
    }

    /**
     * @param ve the ve to set
     */
    public void setVe(int ve) {
        this.ve = ve;
    }
    

    /**
     * Läd ein Teil anhand seiner ID
     * @param id
     * @return Teilebestand - gibt ein Teil zurück
     * @throws SQLException
     */
    public static Teilebestand loadTeil(int id) throws SQLException
    {
        Dao<Teilebestand,Integer> teilebestandDao = DatabaseManager.getInstance().getTeilebestandDao();
        try {
            List<Teilebestand> lt = teilebestandDao.queryForEq("teilID", id);
            if(lt.size()>0){
                return lt.get(0);
            }
        } catch (SQLException ex) {
            Misc.printSQLException(null, ex);
        }
        return null;
    }
    /**
     * Speichert das Teil
     * 
     * @throws SQLException
     */
    public void save() throws SQLException{
       Dao<Teilebestand,Integer> teilebestandDao = DatabaseManager.getInstance().getTeilebestandDao();
       teilebestandDao.createOrUpdate(this);
    }
        
	/**
     * Enumeration für den Teiltyp
     */
    public enum Typ {
        /**
         *
         */
        kaufteile ("Kaufteile"),
		/**
         *
         */
        werkzeuge ("Werkzeuge"),
		/**
         *
         */
        unfertigeBaugruppen ("unfertige Baugruppen"),
		/**
         *
         */
        vorratsteile ("Vorratsteile"),
		/**
         *
         */
        vorrichtungen ("Vorrichtungen"),
		;
        private final String text;
        private Typ(final String text)
        {
            this.text = text;
        }
        
        @Override
        public String toString() {
            return text;
        }
	}
    
   /**
    * Ermittelt ob es eingelagerte Teile von diesem Typ gibt
    * 
    * @param Teilebestand teil Das Teil das geprueft wird
    * @return int wieviele Lagerbestaende es gibt
     *
     * @param teil
     * @return
     * @throws SQLException
     */
    public static int countLagerbestand(Teilebestand teil) throws SQLException {
        Dao<Lagerbestand, Integer> lagerbestandDao = DatabaseManager.getInstance().getLagerbestandDao();
        List<Lagerbestand> lbList = lagerbestandDao.queryForAll();
        int teilId = teil.getIdentnummer();
        int count = 0;

        if (lbList.size() > 0) {
            for (int i = 0; i < lbList.size(); i++) {
                if (lbList.get(i).getTeil().getIdentnummer() == (teilId) && lbList.get(i).getMenge() > 0) {
                    count++;
                }
            }
        }
        return count;
    }
}
