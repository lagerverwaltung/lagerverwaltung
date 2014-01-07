/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import java.awt.Frame;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import model.Lager;
import model.Lagerbestand;
import model.Warenbewegung;

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
    
    /**
     * gibt eine List mit allen abgelaufenen Teilen (lagerbestand) zurück
     * @return
     * @throws SQLException 
     */
    public static List<Lagerbestand> checkDateByExpire() throws SQLException{
        List<Lagerbestand> result = new ArrayList();
        Date today = new Date();
        Dao<model.Warenbewegung, Integer> warenbewegungDao = DatabaseManager.getInstance().getWarenbewegungDao();
        QueryBuilder<Warenbewegung, Integer> warenbQb = warenbewegungDao.queryBuilder();
        
        warenbQb.where().lt("haltbarkeitsDatum", today);
        PreparedQuery<Warenbewegung> preparedQuery = warenbQb.prepare();
        List<Warenbewegung> wbResult = warenbewegungDao.query(preparedQuery);
        
        if (wbResult.size() > 0) {
            for (int i = 0; i < wbResult.size(); i++) {
                result.add(wbResult.get(i).getLagerbestand());
            }
        }
                
        return result;
    }
    
    /**
     * Erstellt ein Dialog, der alle Abgelaufenen Teile und deren Lagerplatz
     * anzeigt.
     * @param f
     * @throws SQLException 
     */
    public static void printExpiredLagerbestand(Frame f) throws SQLException{
        List<Lagerbestand> expiredLb = checkDateByExpire();
        String s = "Folgende Teile sind abgelaufen: \n";
        if(expiredLb != null){
            for(int i = 0; i < expiredLb.size(); i++){
                s +=    "\""+expiredLb.get(i).getTeil().getBezeichnung()+"\""
                        + "; ID "
                        + expiredLb.get(i).getTeil().getIdentnummer()
                        + " im Fach "
                        + expiredLb.get(i).getLagerfach().toString() 
                        + "\n"; 
            }
            Misc.createErrorDialog(f, s);
        }
    }
}
