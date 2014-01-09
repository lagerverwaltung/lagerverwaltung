/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import model.Lager;
import model.Teilebestand;
import model.filter.LagerbestandFilterModel;
import model.filter.TeilebestandFilterModel;
import model.filter.WarenbewegungFilterModel;
   
/**
 * Klasse mit Untersützungsfunktionen rund um Filtering
 * @author ssinger
 */
public class FilterUiHelper {

    int MENGE_NOT_INTEGER_ID = 1;
    String MENGE_NOT_INTEGER_TEXT = "Die Menge muss ganzzahlig sein";
    
    int MENGE_LT_ZERO_ID = 2;
    String MENGE_LT_ZERO_TEXT = "Mengen müssen größer als Null sein";
    
    int PREIS_LT_ZERO_ID = 3;
    String PREIS_LT_ZERO_TEXT = "Der Preis darf nicht negativ sein";
    
    int PREIS_NOT_FLOAT_ID = 4;
    String PREIS_NOT_FLOAT_TEXT = "Der Preis muss eine reelle Zahl sein.";
    
    int DATE_NOT_VALID_ID = 5;
    String DATE_NOT_VALID_TEXT = "Bitte valides Datumsformat tt.mm.yyyy eingeben!";
    
    int VON_GT_BIS_ID = 6;
    String VON_GT_BIS_TEXT = "von > bis";


    private static FilterUiHelper singleton;
    public static FilterUiHelper getInstance(){
        if (FilterUiHelper.singleton == null){
            singleton = new FilterUiHelper();
        }
        return singleton;
    }
    
    /**
     * Validierungsfunktion für den TeileFilter
     *
     * @author ssinger
     * @param Alle Parameter werden als String aus der GUI übergeben
     * @return HashMap<Integer, String> Eine Tabelle mit dem Fehlercode und der
     * Fehlernachricht
     */
    public HashMap<Integer, String> validateTeileFilter(String preisVon, String preisBis, String veVon, String veBis){
        HashMap<Integer, String> errors = new HashMap();
        float preisVonS = 0, preisBisS = Float.MAX_VALUE;
        int vonVeS = 0, bisVeS = Integer.MAX_VALUE;

        if (preisVon.length() > 0) {
            try {
                preisVonS = Float.parseFloat(preisVon);
            } catch (NumberFormatException e) {
                errors.put(PREIS_NOT_FLOAT_ID, PREIS_NOT_FLOAT_TEXT);
            }
            if (preisVonS < 0) {
                errors.put(PREIS_LT_ZERO_ID, PREIS_LT_ZERO_TEXT);
            }
        }

        if (preisBis.length() > 0) {
            try {
                preisBisS = Float.parseFloat(preisBis);
            } catch (NumberFormatException e) {
                errors.put(PREIS_NOT_FLOAT_ID, PREIS_NOT_FLOAT_TEXT);
            }
            if (preisBisS < 0) {
                errors.put(PREIS_LT_ZERO_ID, PREIS_LT_ZERO_TEXT);
            }
        }
        
        if(preisVonS > preisBisS){
            errors.put(VON_GT_BIS_ID, VON_GT_BIS_TEXT);
        }

        if (veVon.length() > 0) {
            try {
                vonVeS = Integer.parseInt(veVon);
            } catch (NumberFormatException e) {
                errors.put(MENGE_NOT_INTEGER_ID, MENGE_NOT_INTEGER_TEXT);
            }
            if (vonVeS < 0) {
                errors.put(MENGE_LT_ZERO_ID, MENGE_LT_ZERO_TEXT);
            }
        }

        if (veBis.length() > 0) {
            try {
                bisVeS = Integer.parseInt(veBis);
            } catch (NumberFormatException e) {
                errors.put(MENGE_NOT_INTEGER_ID, MENGE_NOT_INTEGER_TEXT);
            }
            if (bisVeS < 0) {
                errors.put(MENGE_LT_ZERO_ID, MENGE_LT_ZERO_TEXT);
            }
        }
        
        if(vonVeS > bisVeS){
            errors.put(VON_GT_BIS_ID, VON_GT_BIS_TEXT);
        }
        return errors;
    }
    
    /**
     * Validierungsfunktion für den Lagerfilter
     * @author ssinger
     * @param Alle Parameter werden als String aus der GUI übergeben
     * @return HashMap<Integer, String> Eine Tabelle mit dem Fehlercode und der
     * Fehlernachricht
     */
    public HashMap<Integer, String> validateLagerFilter(String mengeVon, String mengeBis) {
        int mVon = 0, mBis = Integer.MAX_VALUE;
        
        HashMap<Integer, String> errors = new HashMap();
        if (mengeVon.length() > 0) {
            try {
                mVon = Integer.parseInt(mengeVon);
            } catch (NumberFormatException e) {
                errors.put(MENGE_NOT_INTEGER_ID, MENGE_NOT_INTEGER_TEXT);
            }
            if(mVon < 0){
                errors.put(MENGE_LT_ZERO_ID, MENGE_LT_ZERO_TEXT);
            }
        }
        if (mengeBis.length() > 0) {
            try {
                mBis = Integer.parseInt(mengeBis);
            } catch (NumberFormatException e) {
                errors.put(MENGE_NOT_INTEGER_ID, MENGE_NOT_INTEGER_TEXT);
            }
            if(mBis < 0){
                errors.put(MENGE_LT_ZERO_ID, MENGE_LT_ZERO_TEXT);
            }
        }
        
        if(mVon > mBis){
            errors.put(VON_GT_BIS_ID, VON_GT_BIS_TEXT);
        }

        return errors;
    }
    
    /**
     * Validierungsfunktion für den WarenbewegungFilter
     *
     * @author ssinger
     * @param Alle Parameter werden als String aus der GUI übergeben
     * @return HashMap<Integer, String> Eine Tabelle mit dem Fehlercode und der
     * Fehlernachricht
     */
    public HashMap<Integer, String> validateWarenbewegungFilter(String datumVon, String datumBis, String haltbarVon, String haltbarBis){
        DateFormat d = new SimpleDateFormat("dd.MM.yyyy");
        d.setTimeZone(TimeZone.getTimeZone("CET"));
        d.setLenient(false);
        
        Date datumVonS = new Date();
        Date datumBisS = new Date();
        Date haltbarBisS = new Date();
        Date haltbarVonS = new Date();
                
        try{
             datumVonS = d.parse("01.01.1969");
             datumBisS = d.parse("01.01.2542");
             haltbarBisS = d.parse("01.01.2542");
             haltbarVonS = d.parse("01.01.1969");
        }catch(ParseException e){
            System.out.println("nothing to catch");
        }

        HashMap<Integer, String> errors = new HashMap();
        
        if(datumVon.length() > 0){
            try {
                datumVonS = d.parse(datumVon);
            } catch (ParseException ex) {
                errors.put(DATE_NOT_VALID_ID, DATE_NOT_VALID_TEXT);
            } 
        }
      
        if(datumBis.length() > 0){
            try {
                datumBisS = d.parse(datumBis);
            } catch (ParseException ex) {
                errors.put(DATE_NOT_VALID_ID, DATE_NOT_VALID_TEXT);
            }  
        }

        if(datumVonS.after(datumBisS)){
            errors.put(VON_GT_BIS_ID, VON_GT_BIS_TEXT);
        }
        
        if(haltbarVon.length() > 0 ){
            try {
                haltbarVonS = d.parse(haltbarVon);
            } catch (ParseException ex) {
                errors.put(DATE_NOT_VALID_ID, DATE_NOT_VALID_TEXT);
            }

        }

        if(haltbarBis.length() > 0){
            try {
                haltbarBisS = d.parse(haltbarBis);
            } catch (ParseException ex) {
                errors.put(DATE_NOT_VALID_ID, DATE_NOT_VALID_TEXT);
            }
        }
     
        if(haltbarVonS.after(haltbarBisS)){
            errors.put(VON_GT_BIS_ID, VON_GT_BIS_TEXT);
        }
        return errors;
    }
    
    /**
     * Gibt für übergebenen Lagercode den Lagerort-Enum Wert zurück
     * @author ssinger
     * @param s Lagercode
     * @return 
     */
    public static Lager.Lagerort getComboLager(String s) {
        switch (s) {
            case "HL":
                return Lager.Lagerort.hochregal;
            case "FL":
                return Lager.Lagerort.freilager;
            default:
                return null;
        }
    }
    
    /**
     * Erzeugt ein gültiges LagerbestandFilterobjekt mit maximalen Grenzen
     * @author ssinger
     * @param lagertyp
     * @param Alle Parameter der Gui werden als String übergeben
     * @return fertiges LagerbestandFilterModel-Objekt
     */
    public static LagerbestandFilterModel createLFM(String lagertyp, String x, String y, String z, String mengeVon, String mengeBis, String grund, String bezeichnung){
         LagerbestandFilterModel lfm = new LagerbestandFilterModel();
        if (lagertyp.length() > 1) {
            lfm.setLagerTyp(FilterUiHelper.getComboLager(lagertyp));
        }
        if (!x.equals("x")) {
            lfm.setX(Integer.parseInt(x));
        }
        if (!y.equals("y")) {
            lfm.setY(Integer.parseInt(y));
        }
        if (!z.equals("z")) {
            lfm.setZ(Integer.parseInt(z));
        }
        if (mengeVon.length() > 0) {
            lfm.setVonMenge(Integer.parseInt(mengeVon));
        }else{
            lfm.setVonMenge(0);
        }
        if (mengeBis.length() > 0) {
            lfm.setBisMenge(Integer.parseInt(mengeBis));
        }else{
            lfm.setBisMenge(Integer.MAX_VALUE);
        }
        if (grund.length() > 0) {
            lfm.setGrund(grund);
        }
        if(bezeichnung.length() > 0){
            lfm.setBezeichnung(bezeichnung);
        }
         
         return lfm;
    }
    
    /**
     * Erzeugt ein gültiges TeileFilterObjekt mit maximalen Grenzen
     * @author ssinger
     * @param Alle Parameter der Gui werden als String übergeben
     * @return fertiges TeilebestandFilterModel-Objekt
     */
    public static TeilebestandFilterModel createTFM(String typ, String bezeichnung, String matGruppe, String zeichNr, String preisVon, String preisBis, String veVon, String veBis){
        TeilebestandFilterModel tfm = new TeilebestandFilterModel();
        
        if(bezeichnung.length() > 0){
            tfm.setBezeichnung(bezeichnung);
        }
        if(typ.length() > 0){
            tfm.setTyp(getComboTyp(typ));
        }
        if (matGruppe.length() > 0) {
            tfm.setMaterialgruppe(matGruppe);
        }
        if (zeichNr.length() > 0) {
            tfm.setZeichnungsnummer(zeichNr);
        }
        if (preisVon.length() > 0) {
            tfm.setVonPreis(helper.Misc.parseToCurrency(preisVon));
        }else{
            tfm.setVonPreis(0);
        }
        if (preisBis.length() > 0) {
            tfm.setBisPreis(helper.Misc.parseToCurrency(preisBis));
        }else{
            tfm.setBisPreis(Float.MAX_VALUE);
        }
        if (veVon.length() > 0) {
            tfm.setVonVe(Integer.parseInt(veVon));
        }else{
            tfm.setVonVe(0);
        }
        if (veBis.length() > 0) {
            tfm.setBisVe(Integer.parseInt(veBis));
        }else{
            tfm.setBisVe(Integer.MAX_VALUE);
        }
        
        return tfm;
    }
    
    /**
     * Erzeugt ein gültiges WarenbewegungFilterModel-Objekt mit maximalen Grenzen
     * @author ssinger
     * @param Alle Parameter der Gui werden als String übergeben
     * @return fertiges TeilebestandFilterModel-Objekt
     */
    public static WarenbewegungFilterModel createWFM(String bezeichnung, String haltbarVon, String haltbarBis, String qLagertyp, String Bewegungstyp, String qx, String qy, String qz, String teiltyp, String zLagertyp, String zx, String zy, String zz, String datumVon, String datumBis, String bewegungsTyp) {
        WarenbewegungFilterModel wfm = new WarenbewegungFilterModel();
        DateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        f.setTimeZone(TimeZone.getTimeZone("CET"));
        f.setLenient(false);
        int expireID = 0;
        if(bezeichnung.length() > 0){
            wfm.setBezeichnung(bezeichnung);
        }
        if(qLagertyp.length() > 1){
            wfm.setqLagerort(FilterUiHelper.getComboLager(qLagertyp));
        }
        if (!qx.equals("x")) {
            wfm.setqX(Integer.parseInt(qx));
        }
        if (!qy.equals("y")) {
            wfm.setqY(Integer.parseInt(qy));
        }
        if (!qz.equals("z")) {
            wfm.setqZ(Integer.parseInt(qz));
        }
        if (teiltyp.length() > 0) {
            wfm.setTyp(getComboTyp(teiltyp));
        }
        if (zLagertyp.length() > 1) {
            wfm.setzLagerort(FilterUiHelper.getComboLager(zLagertyp));
        }
        if (!zx.equals("x")) {
            wfm.setzX(Integer.parseInt(zx));
        }
        if (!"y".equals(zy)) {
            wfm.setzY(Integer.parseInt(zy));
        }
        if (!"z".equals(zz)) {
            wfm.setzZ(Integer.parseInt(zz));
        }
        try {
            if (datumVon.length() > 0) {
                wfm.setDatumVon(f.parse(datumVon));
            }
            if (datumBis.length() > 0) {
                wfm.setDatumBis(f.parse(datumBis));
            }
            if (haltbarVon.length() > 0) {
                wfm.setHaltbarVon(f.parse(haltbarVon));
                expireID++;
            }
            if(haltbarBis.length() > 0){
                wfm.setHaltbarBis(f.parse(haltbarBis));
                expireID++;
            }
            
            if(bewegungsTyp.length() > 1){
                switch(bewegungsTyp){
                    case "einlagern" : wfm.setBewegungsTyp(1);
                        break;
                    case "auslagern" : wfm.setBewegungsTyp(3);
                        break;
                    case "umlagern" : wfm.setBewegungsTyp(4);
                        break;
                    case "splitten" : wfm.setBewegungsTyp(5);
                }
            }
            wfm.setExpireID(expireID);
        } catch (ParseException e) {
            System.out.println("nothing to catch");
        }
        return wfm;
    }

    
    /**
     * gibt für den Übergebenen String den passenden Teilebestand-
     * Enumerationstyp zurück
     * @param s String
     * @return Teilebestand.typ
     */
    public static Teilebestand.Typ getComboTyp(String s) {
        switch (s) {
            case "Kaufteile":
                return Teilebestand.Typ.kaufteile;
            case "Werkzeuge":
                return Teilebestand.Typ.werkzeuge;
            case "Vorratsteile":
                return Teilebestand.Typ.vorratsteile;
            case "Vorrichtungen":
                return Teilebestand.Typ.vorrichtungen;
            case "unfertige Baugruppe":
                return Teilebestand.Typ.unfertigeBaugruppen;
        }
        return null;
    }
}

