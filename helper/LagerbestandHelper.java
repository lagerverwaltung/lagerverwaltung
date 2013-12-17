/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.text.ParseException;

/**
 *
 * @author simon
 */
public class LagerbestandHelper {
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
    
    
    private static LagerbestandHelper singleton;
    public static LagerbestandHelper getInstance()
    {
        if (LagerbestandHelper.singleton == null){
            singleton = new LagerbestandHelper();
        }
        return singleton;
    }
    
     /**
     * Validierungsfunktion für die Lagerdaten
     * Diese wandelt die Werte selbst in die notwendigen Typen um.
     * 
     * @param Alle Parameter werden als String übergeben (Aus der GUI)
     * @return HashMap<Integer, String> Eine Tabelle mit dem Fehlercode und der
     * Fehlernachricht
     */
    public HashMap<Integer,String> validateLagerbestandData(String menge,String datum, String grund)
    {
        HashMap<Integer,String> errors=new HashMap<Integer,String>();
        
        int mengeS=0;
        Date datumS = new Date();
        String grundS;
        
        try {
        mengeS=Integer.parseInt(menge);
        }
        catch(NumberFormatException e)
        {
        errors.put(MENGE_NOT_INTEGER, MENGE_NOT_INTEGER_TEXT);
        }
        
        if(mengeS<1)
            errors.put(MENGE_NOT_GREATER_ZERO,MENGE_NOT_GREATER_ZERO_TEXT);
        
        if(Character.isSpaceChar(grund.charAt(0)))
            errors.put(GRUND_SPACE, GRUND_SPACE_TEXT);
        
        try{
            DateFormat d= new SimpleDateFormat("dd.MM.yyyy");
            datumS=d.parse(datum);
        }
        catch(ParseException ex)
        {
            errors.put(DATE_NOT_VALID, DATE_NOT_VALID_TEXT);
        }
        Date today=new Date();
        
        if(datumS.before(today))
            errors.put(DATE_BEFORE_TODAY, DATE_BEFORE_TODAY_TEXT);
            
        return errors;
    }
}
