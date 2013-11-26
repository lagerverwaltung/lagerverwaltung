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

@DatabaseTable(tableName = "teilebestand")
public class Teilebestand {

    @DatabaseField(columnName = "teilID", generatedId = true)
    private int identnummer;
    
    @DatabaseField()
    private String bezeichnung;
    
    @DatabaseField()
    private String materialgruppe;
    
    @DatabaseField()
    private String zeichnungsnummer;
    
    @DatabaseField()
    private float preis;
    
    @DatabaseField()
    private Typ typ;
    
    @DatabaseField()
    private int ve;

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
    

    // ab hier relevant für uns,zum Auslesen von Teilebestand
    public static Teilebestand loadTeil(int id)
    {
        Dao<Teilebestand,Integer> teilebestandDao = DatabaseManager.getInstance().getTeilebestandDao();
        try {
            List<Teilebestand> lt = teilebestandDao.queryForEq("teilID", id);
            if(lt.size()>0){
                return lt.get(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Teilebestand.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    //Speichern
    public void save() throws SQLException{
       Dao<Teilebestand,Integer> teilebestandDao = DatabaseManager.getInstance().getTeilebestandDao();
       teilebestandDao.createOrUpdate(this);
    }
        
	public enum Typ {
        kaufteile ("Kaufteile"),
		werkzeuge ("Werkzeuge"),
		unfertigeBaugruppen ("unfertige Baugruppen"),
		vorratsteile ("Vorratsteile"),
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
	
	

}
