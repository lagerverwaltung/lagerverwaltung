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
public class TeileTableModel extends AbstractTableModel {
    ArrayList<Object[]> teileRows = new ArrayList();
    
    public void setData(ArrayList<Object[]> arr)
    {
        this.teileRows = arr;
    }
    
    public ArrayList<Object[]> dummyArrayList()
    {
        ArrayList<Object[]> arr = new ArrayList();
        String[] row = {"001212","Schraube M5","Betriebsstoffe","A231231f.cad","0,84 €", "Vorratsteile"};
        arr.add(row);
        return arr;
    }
    
    public int getColumnCount() {
        return 6;
    }
    
    public int getRowCount() {
        return teileRows.size();
    }

    public String getColumnName(int col) {
        String name = "";
        switch (col)
        {
            case 0:
                name = "Identnummer";
                break;
            case 1:
                name = "Bezeichnung";
                break;
            case 2:
                name = "Materialgruppe";
                break;
            case 3:
                name = "Zeichnungsnummer";
                break;
            case 4:
                name = "Preis";
                break;
            case 5:
                name = "Typ";
                break;
        }
        return name;
    }

    public Object getValueAt(int row, int col) {
        if(teileRows.size()>0){
            Object[] commisionColumns = teileRows.get(row);
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
