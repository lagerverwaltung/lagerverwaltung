/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "teilebestand")
public class Teilebestand {

    @DatabaseField(columnName = "teilID", generatedId = true)
    private int identnummer;
    
    @DatabaseField()
    private String bezeichnung;
    
    @DatabaseField()
    private String materialgruppe;
    
    @DatabaseField()
    private int zeichnungsnummer;
    
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
    public int getZeichnungsnummer() {
        return zeichnungsnummer;
    }

    /**
     * @param zeichnungsnummer the zeichnungsnummer to set
     */
    public void setZeichnungsnummer(int zeichnungsnummer) {
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
        
	public enum Typ {
	
		kaufteile (),
		werkzeuge (),
		unfertigeBaugruppen (),
		vorratsteile (),
		vorrichtungen (),
		;
		
	}
	
	

}
