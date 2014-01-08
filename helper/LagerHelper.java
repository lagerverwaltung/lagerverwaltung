/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.util.HashMap;

/**
     * Enthält Hilfsfunktionen für die Speicherung und Verwaltung der
     * Lagerdaten
     * 
     * @author Simon Pickert
     */
public class LagerHelper {
    int BREITE_NOT_INTEGER_ID = 1;
    String BREITE_NOT_INTEGER_TEXT = "Die Breite muss numerisch sein.";
    
    int HOEHE_NOT_INTEGER_ID = 2;
    String HOEHE_NOT_INTEGER_TEXT = "Die Höhe muss numerisch sein.";
    
    int TIEFE_NOT_INTEGER_ID = 3;
    String TIEFE_NOT_INTEGER_TEXT = "Die Tiefe muss numerisch sein.";
    
    int FACHKLEIN_NOT_INTEGER_ID = 4;
    String FACHKLEIN_NOT_INTEGER_TEXT = "Die Fachgröße 'klein' muss numerisch sein.";
    
    int FACHMITTEL_NOT_INTEGER_ID = 5;
    String FACHMITTEL_NOT_INTEGER_TEXT = "Die Fachgröße 'mittel' muss numerisch sein.";
    
    int FACHGROSS_NOT_INTEGER_ID = 6;
    String FACHGROSS_NOT_INTEGER_TEXT = "Die Fachgröße 'groß' muss numerisch sein.";
    
    int BREITE_MAX_VALUE_ID = 7;
    String BREITE_MAX_VALUE_TEXT = "Die Breite darf maximal 1000 sein.";
    
    int HOEHE_MAX_VALUE_ID = 8;
    String HOEHE_MAX_VALUE_TEXT = "Die Höhe darf maximal 1000 sein.";
    
    int TIEFE_MAX_VALUE_ID = 9;
    String TIEFE_MAX_VALUE_TEXT = "Die Tiefe darf maximal 1000 sein.";
    
    int ALL_NOT_NEGATIVE_ID = 10;
    String ALL_NOT_NEGATIVE_TEXT = "Es dürfen keine negativen Werte angegeben werden."; 
    
    int MAX_MATRIX_ID = 11;
    String MAX_MATRIX_TEXT = "Das Produckt aus Breite, Höhe und Tiefe darf 5000 nicht überschreiten (maximale Fachanzahl)."; 
    
    int ALL_ZERO_FORBIDDEN_ID = 12;
    String ALL_ZERO_FORBIDDEN_TEXT = "Der Wert 0 ist nicht zugelassen."; 
    
    int WRONG_SIZE_ASSIGNMENT_ID = 13;
    String WRONG_SIZE_ASSIGNMENT_TEXT = "Unzulässige Fachgrößenzuordnung. Es muss folgende Bedingung gelten: Fach klein <= Fach mittel <= Fach groß !";
    
    
    /**
     * Singleton Getter
     * 
     * @return LagerHelper Eine LagerHelper Instanz
     */
    private static LagerHelper singleton;
    public static LagerHelper getInstance()
    {
        if (LagerHelper.singleton == null){
            singleton = new LagerHelper();
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
    public HashMap<Integer, String> validateLagerData(String breite, String hoehe, String tiefe, String fachKlein, String fachMittel, String fachGross){
        HashMap<Integer, String> errors = new HashMap();
        int breiteS, hoeheS, tiefeS, fachKleinS, fachMittelS, fachGrossS;
        breiteS = hoeheS = tiefeS = fachKleinS = fachMittelS = fachGrossS = 0;
        try {
            breiteS = Integer.parseInt(breite);
        }
        catch(NumberFormatException e) {
            errors.put(BREITE_NOT_INTEGER_ID, BREITE_NOT_INTEGER_TEXT);
        }
        
        try {
            hoeheS = Integer.parseInt(hoehe);
        }
        catch(NumberFormatException e) {
            errors.put(HOEHE_NOT_INTEGER_ID, HOEHE_NOT_INTEGER_TEXT);
        }
        
        try {
            tiefeS = Integer.parseInt(tiefe);
        }
        catch(NumberFormatException e) {
            errors.put(TIEFE_NOT_INTEGER_ID, TIEFE_NOT_INTEGER_TEXT);
        }
        
        try {
            fachKleinS = Integer.parseInt(fachKlein);
        }
        catch(NumberFormatException e) {
            errors.put(FACHKLEIN_NOT_INTEGER_ID, FACHKLEIN_NOT_INTEGER_TEXT);
        }
        
        try {
            fachMittelS = Integer.parseInt(fachMittel);
        }
        catch(NumberFormatException e) {
              errors.put(FACHMITTEL_NOT_INTEGER_ID, FACHMITTEL_NOT_INTEGER_TEXT);
        }
        
        try {
            fachGrossS = Integer.parseInt(fachGross);
        }
        catch(NumberFormatException e) {
              errors.put(FACHGROSS_NOT_INTEGER_ID, FACHGROSS_NOT_INTEGER_TEXT);
        }
        
        if(breiteS > 1000 && breiteS != 0){
             errors.put(BREITE_MAX_VALUE_ID, BREITE_MAX_VALUE_TEXT);
        }
        
        if(hoeheS > 1000 && hoeheS != 0){
             errors.put(HOEHE_MAX_VALUE_ID, HOEHE_MAX_VALUE_TEXT);
        }
        if(tiefeS > 1000 && tiefeS != 0){
             errors.put(TIEFE_MAX_VALUE_ID, TIEFE_MAX_VALUE_TEXT);
        }
        
        if(breiteS < 0 || tiefeS < 0 || hoeheS < 0 || fachKleinS < 0 || fachMittelS < 0 || fachGrossS < 0){
            errors.put(ALL_NOT_NEGATIVE_ID, ALL_NOT_NEGATIVE_TEXT);
        }
        if (errors.size() == 0){
            if(breiteS * hoeheS * tiefeS > 5000){
                 errors.put(MAX_MATRIX_ID, MAX_MATRIX_TEXT);
            }
            if(breiteS == 0 || hoeheS == 0 || tiefeS == 0 || fachKleinS == 0 || fachMittelS == 0 || fachGrossS == 0){
                 errors.put(ALL_ZERO_FORBIDDEN_ID, ALL_ZERO_FORBIDDEN_TEXT);
            }
        }
        if (!(fachKleinS <= fachMittelS && fachMittelS <= fachGrossS))
        {
            errors.put(WRONG_SIZE_ASSIGNMENT_ID, WRONG_SIZE_ASSIGNMENT_TEXT);
        }
        return errors;
    }

}
