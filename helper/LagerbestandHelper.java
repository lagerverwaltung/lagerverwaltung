/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import com.j256.ormlite.dao.ForeignCollection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.text.ParseException;
import model.Lagerbestand;
import model.Lagerfach;
import model.Warenbewegung;
import model.ZielPosition;

/**
 *
 * @author simon
 */
public class LagerbestandHelper {
    int MENGE_NOT_INTEGER = 1;
    String MENGE_NOT_INTEGER_TEXT = "Menge muss eine Zahl sein.";

    int MENGE_NOT_GREATER_ZERO = 2;
    String MENGE_NOT_GREATER_ZERO_TEXT = "Menge muss größer als 0 sein!";

    int GRUND_SPACE = 3;
    String GRUND_SPACE_TEXT = "Grund darf nicht mit einem Leerzeichen beginnen!";

    int DATE_NOT_VALID = 4;
    String DATE_NOT_VALID_TEXT = "Bitte valides Datumsformat tt.mm.yyyy eingeben!";

    int DATE_BEFORE_TODAY = 5;
    String DATE_BEFORE_TODAY_TEXT = "Das eingegebene Datum liegt vor dem heutigen Datum";

    
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

    /**
     * validiert die Eingabedatuen für Umlagern
     * @author ssinger
     * @param menge
     * @return 
     */
    public HashMap<Integer, String> validateUmlagern(String menge) {
        HashMap<Integer, String> errors = new HashMap();
        int mengeS = 0;
        
        try{
            mengeS = Integer.parseInt(menge);
        } catch (NumberFormatException e){
            errors.put(MENGE_NOT_INTEGER, MENGE_NOT_INTEGER_TEXT);
        }
        
        if(mengeS < 1){
            errors.put(MENGE_NOT_GREATER_ZERO, MENGE_NOT_GREATER_ZERO_TEXT);
        }
        return errors;
    }
    
    /**
     * speichert den Vorgang "Umlagern"
     * @author ssinger
     * @param quellLagerbestand 
     * @param zielfach 
     * @param menge umzulagernde Menge
     * @throws SQLException 
     */
    public void saveUmlagern(Lagerbestand quellLagerbestand, Lagerfach zielfach, int menge) throws SQLException{
  
        int subMenge = quellLagerbestand.getMenge() - menge;
        if(subMenge <= 0){
            
            Lagerbestand lb = new Lagerbestand();
            quellLagerbestand.setMenge(subMenge);
            quellLagerbestand.save();
            
            lb.setAnschaffungsgrund(quellLagerbestand.getAnschaffungsgrund());
            lb.setTeil(quellLagerbestand.getTeil());
            lb.setLagerfach(zielfach);
            lb.setMenge(menge);
            lb.save();
            
            Warenbewegung wb = new Warenbewegung();
            wb.setAnschaffungsgrund(quellLagerbestand.getAnschaffungsgrund());
            wb.setDatum(new Date());
            wb.setHaltbarkeitsDatum(quellLagerbestand.getWarenbewegung().getHaltbarkeitsDatum());
            wb.setLagerbestand(lb);
            wb.setVerantwortlicher("Lagerverwalter");
            wb.save();
            
            ZielPosition zp = new ZielPosition();
            zp.setLagerfach(zielfach);
            zp.setWarenbewegung(wb);
            zp.save();
        }
        
    }
}
