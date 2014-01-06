/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import model.Lager;
import model.Lager.Lagerort;
import model.Lagerbestand;
import model.Lagerfach;
import model.Teilebestand;
import model.ZielPosition;

/**
 *
 * @author smodlich
 */
public class BestandsGUIHelper {
    
    int quellFachID;
    int menge;
    Date hbDatum;

    String grund;
    Lagerfach[] faecher;
    ArrayList<HashMap> destinations;
    
    int teilID;
    
    int MENGE_NOT_INTEGER=1;
    String MENGE_NOT_INTEGER_TEXT="Menge muss eine Zahl sein.";
    
    int MENGE_NOT_GREATER_ZERO=2;
    String MENGE_NOT_GREATER_ZERO_TEXT="Menge muss größer als 0 sein!";
    
    int GRUND_SPACE=3;
    String GRUND_SPACE_TEXT="Grund darf nicht mit einem Leerzeichen beginnen!";
   
    int DATE_NOT_VALID=4;
    String DATE_NOT_VALID_TEXT="Bitte valides Datumsformat tt.mm.yyyy eingeben!";
    
    int DATE_BEFORE_TODAY=5;
    String DATE_BEFORE_TODAY_TEXT="Das eingegebene Datum liegt vor dem heutigen Datum";
    
    int NO_DATE=6;
    String NO_DATE_TEXT="Es wurde kein Datum eingegeben!";
    
    int NO_GRUND=7;
    String NO_GRUND_TEXT="Es wurde kein Grund eingegeben!";
    
    int QUELLMENGE_TOOLOW_ID = 9;
    String QUELLMENGE_TOOLOW_TEXT ="Die Zielmenge überschreitet die Menge der im Quell-Fach vorhandenen Teile!";
    
    int ZIELMENGE_TOOHIGH_ID = 10;
    String ZIELMENGE_TOOHIGH_TEXT ="Die Zielmenge überschreitet den im Fach vorhandenen Platz! Fach: ";
    
    int AUSLAGERN_TOHIGH_ID = 11;
    String AUSLAGERN_TOHIGH_TEXT ="Es existieren nicht ausreichend Teile dieses Typs in diesem Fach, um diese Menge auszulagern!";
    
    int SOURCE_EQUAL_DESTINATION_ID = 12;
    String SOURCE_EQUAL_DESTINATION_TEXT = "Das Quellfach darf nicht als Zielfach angegeben werden!";
    
    public int getMenge()
    {
        return menge;
    }
    
    public void setTeilID(int teilID)
    {
        this.teilID=teilID;
    }
    
    public int getTeilID()
    {
        return teilID;
    }
    
    public void setQuellFachID(int quellFachId)
    {
      this.quellFachID=quellFachId;
    }
    
    public int getQuellFachID()
    {
      return quellFachID;
    }
    
    public Lagerfach getEinLagerfach()
    {
        return faecher[0];
    }
    
    public int getEineMenge()
    {
        return menge;
    }
    
    public Lagerfach[] getFaecher()
    {
        return faecher;
    }
    
    public String getGrund()
    {
        return grund;
    }
    
    public Lagerfach generateFach(int x,int y,int z, String lo) throws SQLException 
    {
        Lagerort l;
        if (lo.equals("FL")) {

            l=Lager.Lagerort.freilager;
        } else {
            l=Lager.Lagerort.hochregal;
        }
        return Lagerfach.getFach(Lager.getLager(l),x ,y,z);
    }
    
    public void setFaecher (Lagerfach[] l)
    {
        faecher=l;
    }
    
    public void validateDestinationData (String[] cbLager, String[]cbX, String[] cbY, String[]cbZ, String[] qty)
    {
        
    }
    
    public void setEinFach (Lagerfach l)
    {
        faecher[0]=l;
    }
    
     public Date getHbDatum() {
        return hbDatum;
    }

    public void setHbDatum(Date hbDatum) {
        this.hbDatum = hbDatum;
    }
    
    public ArrayList<HashMap> getDestinations() {
        return destinations;
    }

    public void setDestinations(ArrayList<HashMap> destinations) {
        this.destinations = destinations;
    }
    
     
    /**
     * @author smodlich
     * generischer Kapazitätstest für alle Bestandsaenderungen
     * @param faecher Faecher Array ausgelesen aus BestandsGUIHelper Objekt
     * @param teil Teilebestand
     * @param mengen
     * @param mengeOld
     * @return
     * @throws SQLException 
     */
    public HashMap<Integer,String> kapazitaetsTest(Lagerfach fach,Teilebestand teil,int menge,int mengeOld) throws SQLException
    {
        HashMap<Integer,String> errors = new HashMap<Integer,String>();
        int groesse= teil.getVe();
        
        int errorIndex=0;
        
        int freeVE=fach.getFreeVe();

        if((menge*groesse)>freeVE)
        {
            errorIndex++;
            errors.put(errorIndex,"Die Kapazitaet im Fach X:"+ fach.getX()+" Y:"+fach.getY() +" Z:"+ 
            fach.getZ()+ " ist nicht ausreichend. Es sind noch " + freeVE + " VE frei.Aber es werden " + 
            menge*groesse+ " VE benötigt.");               
        }
        if(menge > 0 && (menge+mengeOld)<0)
        {
            errorIndex++;
            errors.put(errorIndex, "Keine ausreichende Menge im Quellfach vorhanden!");
        }
    
        return errors;
    }
    
    public HashMap<Integer,String> validateLagerbestandData(int code,String mengenE,String datumE, String grundE, ArrayList<HashMap> destinations) throws SQLException
    {
        HashMap<Integer,String> errors=new HashMap<Integer,String>();
        setDestinations(destinations);
        if(code != BestandsaenderungFrame.SPLITTEN){
            try {
                menge =Integer.parseInt(mengenE);
            }
            catch(NumberFormatException e)
            {
                errors.put(MENGE_NOT_INTEGER, MENGE_NOT_INTEGER_TEXT);
            }

            if(menge<1){
                errors.put(MENGE_NOT_GREATER_ZERO,MENGE_NOT_GREATER_ZERO_TEXT);
            }
        }
        
        if(grundE!=null)
        {grund=grundE;}
        else
        {
           errors.put(NO_GRUND, NO_GRUND_TEXT);
        }
        if (code==BestandsaenderungFrame.EINLAGERN_LAGERBESTAND || code==BestandsaenderungFrame.EINLAGERN_TEILEBESTAND)
        {
        try{
            DateFormat d= new SimpleDateFormat("dd.MM.yyyy");
            hbDatum=d.parse(datumE);
        }
        catch(ParseException ex)
        {
            errors.put(DATE_NOT_VALID, DATE_NOT_VALID_TEXT);
        }
        catch(NullPointerException e)
        {
            errors.put(NO_DATE, NO_DATE_TEXT);
        }
        Date today=new Date();
        
        
            if(hbDatum!=null)
            {
                if(hbDatum.before(today))
                    errors.put(DATE_BEFORE_TODAY, DATE_BEFORE_TODAY_TEXT);
           
            }
            else
            {
                errors.put(NO_DATE, NO_DATE_TEXT);
            }
        }
        Lagerbestand quellLb=null;
        Teilebestand teil;
        int oldMenge;
        if(Lagerbestand.getLagerbestand(getTeilID(), getQuellFachID())!=null){
            quellLb= Lagerbestand.getLagerbestand(getTeilID(), getQuellFachID());
            teil = quellLb.getTeil();
            oldMenge = quellLb.getMenge();
        }
        else {
            teil = Teilebestand.loadTeil(getTeilID());
            oldMenge = 0;
        }
        
        int qtySum = 0;
        for(int i = 0; i<destinations.size(); i++){
            HashMap hm = destinations.get(i);
            try{
                int qty = Integer.parseInt((String)hm.get("qty"));  
                qtySum += qty;
                Lagerfach fach = Lagerfach.getFach(
                        Lager.getLager(Lager.getLagerort((String)hm.get("fachCode"))), 
                        (int)hm.get("x"), 
                        (int)hm.get("y"), 
                        (int)hm.get("z")
                );
                HashMap<Integer, String> kapError = kapazitaetsTest(fach, teil, qty, oldMenge);
                for (int errorCode : kapError.keySet()){
                    errors.put(errorCode, kapError.get(errorCode));
                }
                if(quellLb != null && quellLb.getLagerfach().equals(fach)){
                    errors.put(SOURCE_EQUAL_DESTINATION_ID, SOURCE_EQUAL_DESTINATION_TEXT);
                }
                if(qty < 1){
                    errors.put(MENGE_NOT_GREATER_ZERO, MENGE_NOT_GREATER_ZERO_TEXT);
                }
            }
            catch(NumberFormatException e)
            {
                errors.put(MENGE_NOT_INTEGER, MENGE_NOT_INTEGER_TEXT);
            }
            
        }
        
        if(code == BestandsaenderungFrame.AUSLAGERN && quellLb.getMenge() < menge){
            errors.put(AUSLAGERN_TOHIGH_ID, AUSLAGERN_TOHIGH_TEXT);
        }
        
        if((code == BestandsaenderungFrame.SPLITTEN || code == BestandsaenderungFrame.UMLAGERN) 
                && quellLb.getMenge()< qtySum
        ){
                errors.put(QUELLMENGE_TOOLOW_ID, QUELLMENGE_TOOLOW_TEXT);
        }
        
        return errors;
    }
    
            
}
