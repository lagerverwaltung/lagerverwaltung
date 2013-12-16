/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.util.HashMap;
import javax.swing.JTable;
import model.Teilebestand;
import model.collection.TeilebestandCollection;
import model.table.TeileTableModel;

/**
 *
 * @author simon
 */
public class TeilebestandHelper {
    private static TeilebestandHelper singleton;
    
    /**
     * Singleton Getter
     * 
     * @return TeilebestandHelper - Liefert die TeilebestandHelper Instanz
     */
    public static TeilebestandHelper getInstance()
    {
        if (TeilebestandHelper.singleton == null){
            singleton = new TeilebestandHelper();
        }
        return singleton;
    }
    
    int EMPTY_TEILBEZEICHNUNG_ID = 1;
    String EMPTY_TEILBEZEICHNUNG_TEXT = "Es muss eine Teilbezeichnung eingegeben werden.";

    int EMPTY_MATERIAL_ID = 2;
    String EMPTY_MATERIAL_TEXT = "Es muss eine Material eingegeben werden.";

    int EMPTY_ZEICHNUNG_ID = 3;
    String EMPTY_ZEICHNUNG_TEXT = "Es muss eine Zeichnungsnummer eingegeben werden.";

    int EMPTY_SIZE_ID = 4;
    String EMPTY_SIZE_TEXT = "Es muss eine Teilgröße eingegeben werden.";

    int PRICE_NOT_FLOAT_ID = 5;
    String PRICE_NOT_FLOAT_TEXT = "Der Preis muss eine Fließkommazahl sein.";
    
    int PRICE_EQUAL_ZERO_ID = 6;
    String PRICE_EQUAL_ZERO_TEXT = "Der Preis muss größer als 0 sein.";
    
    int GROESSE_NOT_INTEGER_ID = 6;
    String GROESSE_NOT_INTEGER_TEXT = "Die Fachgröße muss in Ganzzahlwerten eingegeben werden.";
    
    int GROESSE_EQUAL_ZERO_ID = 6;
    String GROESSE_EQUAL_ZERO_TEXT = "Die Fachgröße muss größer als 0 sein.";
    
    /**
    /**
     * Validierungsfunktion für die Teiledaten
     * Diese wandelt die Werte selbst in die notwendigen Typen um.
     * 
     * @param Alle Parameter werden als String übergeben (Aus der GUI)
     * @return HashMap<Integer, String> Eine Tabelle mit dem Fehlercode und der
     * Fehlernachricht
     */
    public HashMap<Integer, String> validateTeilData(String bez, String mat, String zeich, String groes, String pri){
        HashMap<Integer, String> errors = new HashMap();
        
        if (bez.trim().length() == 0) {
            errors.put(EMPTY_TEILBEZEICHNUNG_ID, EMPTY_TEILBEZEICHNUNG_TEXT);
        }
        if (mat.trim().length() == 0) {
           errors.put(EMPTY_MATERIAL_ID, EMPTY_MATERIAL_TEXT);
        }
        if (zeich.trim().length() == 0) {
            errors.put(EMPTY_ZEICHNUNG_ID, EMPTY_ZEICHNUNG_TEXT);
        }
        if (groes.trim().length() == 0) {
            errors.put(EMPTY_SIZE_ID, EMPTY_SIZE_TEXT);
        }
        float euro = 0;

        try {
            String euroStr = pri;
            if (euroStr.contains(",")) {
                euroStr = euroStr.replace(',', '.');
                euro = Float.parseFloat(euroStr);
            } else {
                euro = Float.parseFloat(pri);
            }
        } catch (NumberFormatException e) {
            errors.put(PRICE_NOT_FLOAT_ID, PRICE_NOT_FLOAT_TEXT);
        }
        if(euro <= 0){
            errors.put(PRICE_EQUAL_ZERO_ID, PRICE_EQUAL_ZERO_TEXT);
        }
        
        String groesse = groes;
        int groesseInt = 0;
        try {
            if (groesse.length() > 0) {
                
                groesseInt = Integer.parseInt(groesse);
            }
        } catch (NumberFormatException e) {
            errors.put(GROESSE_NOT_INTEGER_ID, GROESSE_NOT_INTEGER_TEXT);
        }
        
        if(groesse.length() > 0 && groesseInt <= 0){
             errors.put(GROESSE_EQUAL_ZERO_ID, GROESSE_EQUAL_ZERO_TEXT);
             
        }
        return errors;
    }
    
    /**
     * Erneuert die Teilebestandstabelle
     * 
     * @param JTable die Teilebestandstabelle
     */
     public static void refreshTeileTableModel(JTable teileBestandTable){
        TeilebestandCollection tc = TeilebestandCollection.getInstance(true);
        TeileTableModel tm = new TeileTableModel();
        tm.setData(tc);
        teileBestandTable.setModel(tm);
    }
}
