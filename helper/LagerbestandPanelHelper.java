/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Lagerbestand;
import view.BestandsaenderungFrame;
import view.LagerbestandPanel;

/**
 *
 * @author ssinger
 */
public class LagerbestandPanelHelper {
    /*
    
    public view.BestandsaenderungFrame teilAuslagernAction(int fachId, int teilId) {
        int lagerbestandId = 0;
        Lagerbestand lb = new Lagerbestand();
        try {
            lagerbestandId = Lagerbestand.getLagerbestandID(teilId, fachId);
            lb = Lagerbestand.getLagerbestand(lagerbestandId);
        } catch (SQLException ex) {
            Logger.getLogger(LagerbestandPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        BestandsaenderungFrame bestandsaenderungFrame = new BestandsaenderungFrame(true, lb);
        return bestandsaenderungFrame;

    }
    */
}
