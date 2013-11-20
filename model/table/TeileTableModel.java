/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.table;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Teilebestand;
import model.collection.TeilebestandCollection;

/**
 *
 * @author simon
 */
public class TeileTableModel extends AbstractTableModel {
    TeilebestandCollection<Teilebestand> teileRows = new TeilebestandCollection();
    
    public void setData(TeilebestandCollection<Teilebestand> arr)
    {
        this.teileRows = arr;
    }
    
    public int getColumnCount() {
        return 7;
    }
    
    public int getRowCount() {
        return teileRows.size();
    }

    public String getColumnName(int col) {
        String name = "";
        switch (col)
        {
            case 0:
                name = "Teil ID";
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
            case 6:
                name = "Größe in VE";
                break;
        }
        return name;
    }

    public Object getValueAt(int row, int col) {
        Teilebestand rowO;
        if(teileRows.size()>0){
            rowO = (Teilebestand) teileRows.get(row);
            if(rowO != null){
                switch(col){
                    case 0:
                         return rowO.getIdentnummer();
                    case 1:
                        if(rowO.getBezeichnung() != null){
                        return rowO.getBezeichnung();
                        }
                    case 2:
                        if(rowO.getMaterialgruppe() != null){
                            return rowO.getMaterialgruppe();
                        }
                    case 3:
                         if(rowO.getZeichnungsnummer() != null){
                            return rowO.getZeichnungsnummer();
                         }
                    case 4:
                        System.out.println("4");
                        if(rowO.getPreis() != 0){
                            return new Float(rowO.getPreis());
                        }
                    case 5:
                        if(rowO.getTyp() != null){
                        return rowO.getTyp();
                        }
                    case 6:
                        if(rowO.getVe() > 0){
                        return rowO.getVe();
                        }
                    default:     
                    return "empty";
                }
            }
        }
        return new String("not");
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
