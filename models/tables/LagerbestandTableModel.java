/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models.tables;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author simon
 */
public class LagerbestandTableModel extends AbstractTableModel {

    ArrayList<Object[]> lagerbestandRows = new ArrayList();
    
    public void setData(ArrayList<Object[]> arr)
    {
        this.lagerbestandRows = arr;
    }
    
    public ArrayList<Object[]> dummyArrayList()
    {
        ArrayList<Object[]> arr = new ArrayList();
        String[] row = {"9999","Hochregallager","11","12","1","Schrauben M5","001212", "1000","Bedarf Periode 1"};
        arr.add(row);
        return arr;
    }
    
    public int getColumnCount() {
        return 9;
    }
    
    public int getRowCount() {
        return lagerbestandRows.size();
    }

    public String getColumnName(int col) {
        String name = "";
        switch (col)
        {
            case 0:
                name = "FachID";
                break;
            case 1:
                name = "Lager";
                break;
            case 2:
                name = "x";
                break;
            case 3:
                name = "y";
                break;
            case 4:
                name = "z";
                break;
            case 5:
                name = "Teilbezeichnung";
                break;
            case 6:
                name = "Identnummer";
                break;
            case 7:
                name = "Menge";
                break;
            case 8:
                name = "Ansch. Grund";
                break;
        }
        return name;
    }

    public Object getValueAt(int row, int col) {
        if(lagerbestandRows.size()>0){
            Object[] commisionColumns = lagerbestandRows.get(row);
            return commisionColumns[col];
        }
        return "notfound";
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Verhindert das Editieren der Zellen
     */
    public boolean isCellEditable(int row, int col) {
       return false;
    }
}
