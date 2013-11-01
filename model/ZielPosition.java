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
public class ZielPosition {

	private int menge;
	private int bewegungsPositionID;
	private Lagerfach lagerfach;

    /**
     * @return the menge
     */
    public int getMenge() {
        return menge;
    }

    /**
     * @param menge the menge to set
     */
    public void setMenge(int menge) {
        this.menge = menge;
    }

    /**
     * @return the bewegungsPositionID
     */
    public int getBewegungsPositionID() {
        return bewegungsPositionID;
    }

    /**
     * @param bewegungsPositionID the bewegungsPositionID to set
     */
    public void setBewegungsPositionID(int bewegungsPositionID) {
        this.bewegungsPositionID = bewegungsPositionID;
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

}
