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
    int mengen[];
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
    
    int NO_DATE=6;
    String NO_DATE_TEXT="Es wurde kein Datum eingegeben!";
    
    int NO_GRUND=7;
    String NO_GRUND_TEXT="Es wurde kein Grund eingegeben!";
    
    
    public int[] getMengen()
    {
        return mengen;
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
    
    public int getquellFachID()
    {
      return quellFachID;
    }
    
    public Lagerfach getEinLagerfach()
    {
        return faecher[0];
    }
    
    public int getEineMenge()
    {
        return mengen[0];
    }
    
    public Lagerfach[] getFaecher()
    {
        return faecher;
    }
    
    public String getGrund()
    {
        return grund;
    }
    
    public Lagerfach generateFach(int x,int y,int z, String lo) throws Exception
    {
        Lagerort l;
        if (lo.equals("FL")) {

            l=Lager.Lagerort.freilager;
        } else {
            l=Lager.Lagerort.hochregal;
        }
        return (Lagerfach.getFach(Lager.getLager(l),x ,y,z));
    }
    
    
    public void setFaecher (Lagerfach[] l)
    {
        faecher=l;
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
    
    public HashMap<Integer,String> validateLagerbestandData(int code,String mengenE[],String datumE, String grundE)
    {
        HashMap<Integer,String> errors=new HashMap<Integer,String>();
        
        mengen=new int[mengenE.length];
        
        try {
            for (int i=0;i<mengenE.length;i++)
            {
                mengen[i]=Integer.parseInt(mengenE[i]);
            }
        
        }
        catch(NumberFormatException e)
        {
        errors.put(MENGE_NOT_INTEGER, MENGE_NOT_INTEGER_TEXT);
        }
        
        for(int i=0;i<mengen.length;i++)
        {
        if(mengen[i]<1)
            errors.put(MENGE_NOT_GREATER_ZERO,MENGE_NOT_GREATER_ZERO_TEXT);
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
        return errors;
    }
    
            
}
