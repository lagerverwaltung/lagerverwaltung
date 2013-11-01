/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author ssinger
 */
public class Lagerfach {

	private int fachnummer;

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
     * @return the positionVector
     */
    public PositionVector getPositionVector() {
        return positionVector;
    }

    /**
     * @param positionVector the positionVector to set
     */
    public void setPositionVector(PositionVector positionVector) {
        this.positionVector = positionVector;
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
	
	public enum Lagerort {
	
		freilager (),
		hochregal (),
		;	
	
	}
	
	private String bemerkung;
	private PositionVector positionVector;
	private Groesse groesse;
	private Lager lager;
	
}

