/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.filter;

import model.Lager;


/**
 *
 * @author ssinger
 */
public class LagerbestandFilterModel {
    private Lager.Lagerort lagerTyp;
    private int x;
    private int y;
    private int z;
    private int vonMenge;
    private int bisMenge;
    private String grund;
    
    
    public LagerbestandFilterModel(){
        x = 0;
        y = 0;
        z = 0;
        vonMenge = 0;
        bisMenge = Integer.MAX_VALUE;
        grund = "*";
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
            this.y = y;
    }

    /**
     * @return the z
     */
    public int getZ() {
        return z;
    }

    /**
     * @param z the z to set
     */
    public void setZ(int z) {
            this.z = z;
    }

    /**
     * @return the vonMenge
     */
    public int getVonMenge() {
        return vonMenge;
    }

    /**
     * @param vonMenge the vonMenge to set
     */
    public void setVonMenge(int vonMenge) {
        this.vonMenge = vonMenge;
    }

    /**
     * @return the bisMenge
     */
    public int getBisMenge() {
        return bisMenge;
    }

    /**
     * @param bisMenge the bisMenge to set
     */
    public void setBisMenge(int bisMenge) {
        this.bisMenge = bisMenge;
    }

    /**
     * @return the grund
     */
    public String getGrund() {
        return grund;
    }

    /**
     * @param grund the grund to set
     */
    public void setGrund(String grund) {
        if(!grund.equals("")){
            this.grund = grund;
        }
    }

    /**
     * @return the lagerTyp
     */
    public Lager.Lagerort getLagerTyp() {
        return lagerTyp;
    }

    /**
     * @param lagerTyp the lagerTyp to set
     */
    public void setLagerTyp(Lager.Lagerort lagerTyp) {
        this.lagerTyp = lagerTyp;
    }
}
