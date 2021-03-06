/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import model.collection.WarenbewegungCollection;
import model.table.WarenbewegungTableModel;
import view.DestinationLinesCellRenderer;

/**
 * Enthält Helperfunktionen für Tabellen
 * 
 * @author simon
 */
public class TableHelper {
    /* 
     * Erneuert die Warenbewegungstabelle
     * 
     * @param JTable Die Tabelle mit Warenbewegungen
     */
    public static void refreshWarenbewegungTableModel(JTable t){
        WarenbewegungCollection wc = WarenbewegungCollection.getInstance(true);
        WarenbewegungTableModel wm = new WarenbewegungTableModel();
        wm.setData(wc);
        t.setModel(wm);
        TableCellRenderer ren = new DestinationLinesCellRenderer();
        t.getColumnModel().getColumn(5).setCellRenderer(ren);

        int[] arrWidths = {20,120,65, 60, 17, 180, 120, 90, 80};
        TableColumn tc;
        t.setRowHeight(23);
        int i = 0;
        for (int width : arrWidths){
            tc = t.getColumnModel().getColumn(i++);
            tc.setPreferredWidth(width);
        }
    }
}
