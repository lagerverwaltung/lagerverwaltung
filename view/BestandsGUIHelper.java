/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import model.Lager;
import model.Lager.Lagerort;
import model.Lagerfach;

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
    
    
    public int getMenge()
    {
        return menge;
    }
    
    public void setTeilID(String teilID)
    {
        this.teilID=Integer.parseInt(teilID);
    }
    
    public void setquellFachID(int quellFachId)
    {
      this.quellFachID=quellFachId;
    }
    
    public int getquellFachID()
    {
      return quellFachID;
    }
    
    public Lagerfach getEinLagerfach()
    {
        return faecher[0];
    }
    
    public String getGrund()
    {
        return grund;
    }
    
    public Lagerfach generateFach(int x,int y,int z, String lo) throws Exception
    {
        return (Lagerfach.getFach(Lager.getLager(Lagerort.valueOf(lo)),x ,y,z));
        
    }
    
    
    public void setFaecher (Lagerfach[] l)
    {
        faecher=l;
    }
    
    public void setEinFach (Lagerfach l)
    {
        faecher[0]=l;
    }
    
    
    public HashMap<Integer,String> validateLagerbestandData(String mengeE,String datumE, String grundE)
    {
        HashMap<Integer,String> errors=new HashMap<Integer,String>();
        
        
        
        try {
        menge=Integer.parseInt(mengeE);
        }
        catch(NumberFormatException e)
        {
        errors.put(MENGE_NOT_INTEGER, MENGE_NOT_INTEGER_TEXT);
        }
        
        if(menge<1)
            errors.put(MENGE_NOT_GREATER_ZERO,MENGE_NOT_GREATER_ZERO_TEXT);
        
        if(Character.isSpaceChar(grundE.charAt(0)))
            errors.put(GRUND_SPACE, GRUND_SPACE_TEXT);
        grund=grundE;
        
        try{
            DateFormat d= new SimpleDateFormat("dd.MM.yyyy");
            hbDatum=d.parse(datumE);
        }
        catch(ParseException ex)
        {
            errors.put(DATE_NOT_VALID, DATE_NOT_VALID_TEXT);
        }
        Date today=new Date();
        
        if(hbDatum.before(today))
            errors.put(DATE_BEFORE_TODAY, DATE_BEFORE_TODAY_TEXT);
            
        
        
        return errors;
    }
    
            
}