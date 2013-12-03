/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import com.j256.ormlite.dao.Dao;
import java.awt.Frame;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import model.Lagerbestand;
import model.Teilebestand;

/**
 *
 * @author simon
 */
public class Misc {
    public static boolean createErrorDialog(Frame frame, String errors){
       return Misc.createErrorDialog(frame, errors, false);
    }
    
    public static boolean createErrorDialog(Frame frame, String errors, boolean singleError){
         if(errors.length() > 0){
            if(!singleError){
                errors = "Es ist einer der folgenden Fehler aufgetreten: \n\n"+errors;
            }
            JOptionPane.showMessageDialog(frame, errors);
            return true;
        }
        else {
            return false;
        }
         
    }
  
}
