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
public class WarenbewegungTableModel extends AbstractTableModel {


    ArrayList<Object[]> warenbRows = new ArrayList();
    
    public void setData(ArrayList<Object[]> arr)
    {
        this.warenbRows = arr;
    }
    
    public ArrayList<Object[]> dummyArrayList()
    {
        ArrayList<Object[]> arr = new ArrayList();
        String[] row = {"1","Stollen","-","0","HR010101","3","Dr. Modlich","12.11.2013","24.12.2013","Ersteinlagerung"};
        arr.add(row);
        String[] row1 = {"2","Stollen","HR010101","3","HR010102","1","Dr. Modlich","13.11.2013","24.12.2013","Split"};
        arr.add(row1);
        String[] row2 = {"2","Stollen","HR010101","3","HR010103","1","Dr. Modlich","13.11.2013","24.12.2013","Split"};
        arr.add(row2);
        String[] row3 = {"2","Stollen","HR010101","3","HR010201","1","Dr. Modlich","13.11.2013","24.12.2013","Split"};
        arr.add(row3);
        return arr;
    }
    
    public int getColumnCount() {
        return 10;
    }
    
    public int getRowCount() {
        return warenbRows.size();
    }

    public String getColumnName(int col) {
        String name = "";
        switch (col)
        {
            case 0:
                name = "WbID";
                break;
            case 1:
                name = "Teil";
                break;
            case 2:
                name = "Quellfach";
                break;
            case 3:
                name = "Menge";
                break;
            case 4:
                name = "Zielfach";
                break;
            case 5:
                name = "Menge";
                break;
            case 6:
                name = "Verantwortlicher";
                break;
            case 7:
                name = "Datum";
                break;
            case 8:
                name = "Haltbar bis";
                break;
            case 9:
                name = "Typ";
                break;
        }
        return name;
    }

    public Object getValueAt(int row, int col) {
        if(warenbRows.size()>0){
            Object[] commisionColumns = warenbRows.get(row);
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