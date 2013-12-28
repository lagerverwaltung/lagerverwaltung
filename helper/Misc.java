/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.awt.Frame;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
     * Globale Hilfsfunktionen, die keinem Model direkt angehoerig sind
     * 
     * 
     * @author Simon Pickert
     */
public class Misc {
    
    /**
     * Zeigt einen Fehlerdialog an
     * 
     * @param Frame frame - Das Hauptfenster indem der Fehler angezeigt werden soll
     * @param String errors - Ein String mit der Fehlermeldung
     * @return ob Fehler aufgetreten sind
     */
    public static boolean createErrorDialog(Frame frame, String error){
       JOptionPane.showMessageDialog(frame, error);
       return false;
    }
    
    /**
     * Zeigt einen Fehlerdialog an
     * 
     * @param HashMap<Integer,String> errors - Eine Listen bestehend aus Fehlerindizes 
     * @param Frame frame - Das Hauptfenster indem der Fehler angezeigt werden soll
     * @return ob Fehler aufgetreten sind
     */
    public static boolean createErrorDialog(Frame frame, HashMap<Integer,String> errors){
        String strErrors = "";
        if(errors.size() > 0){
            if (errors.size() > 1){
                strErrors = "Es ist einer der folgenden Fehler aufgetreten: \n\n";
            }
            for(Map.Entry e : errors.entrySet()){
                strErrors += e.getValue()+"\n";
              }
            JOptionPane.showMessageDialog(frame, strErrors);
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Behandelt eine SQLException und gibt zusätzliche Informationen für die 
     * Diagnose aus
     * 
     * @param HashMap<Integer,String> errors - Eine Listen bestehend aus Fehlerindizes 
     * @param Frame frame - Das Hauptfenster indem der Fehler angezeigt werden soll
     * @return ob Fehler aufgetreten sind
     */
    public static void printSQLException (Frame f, SQLException ex){
         String strErrors = "Es ist ein Datenbankfehler aufgetreten. Errorcode:"+ex.getErrorCode();
         System.out.println("Fehlerhaftes Statement:"+ex.getSQLState());
         Misc.createErrorDialog(f, strErrors);
    }
  
    /**
     * Parsed String zu einer Währungseinheit mit 2 Nachkommastellen, tauscht
     * gegebenenfalls "," mit ".".
     * @author ssinger
     * @param string
     * @return float
     */
    public static Float parseToCurrency(String string) {
        float currency = 0;
        if (string.contains(",")) {
            currency = Float.parseFloat(string.replace(',', '.'));

        } else {
            currency = Float.parseFloat(string);
        }
        
        double d = Math.round(currency * 100) /100.0;
        return (float)d;
    }
}
