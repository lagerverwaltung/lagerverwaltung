/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author ssinger
 */
@DatabaseTable(tableName = "warenbewegung")
public class Warenbewegung {
    
    @DatabaseField(columnName = "warenbID", generatedId = true)
    private int warenBewegungsID;
      
    @ForeignCollectionField(columnName = "zielPositionID",eager = true)
    ForeignCollection<ZielPosition> arrZielPosition;
    
    @DatabaseField()
    private String verantwortlicher;
    
    @DatabaseField(columnName = "lagerbestandID", foreign = true)
    private Lagerbestand lagerbestand;
    
    @DatabaseField()
    private java.util.Date datum;
     
    @DatabaseField()
    private java.util.Date haltbarkeitsDatum;


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
	
	

}
