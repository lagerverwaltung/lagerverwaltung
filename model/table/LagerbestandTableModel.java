/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.table;

import javax.swing.table.AbstractTableModel;
import model.Lager;
import model.Lagerbestand;
import model.Lagerfach;
import model.Teilebestand;
import model.collection.LagerbestandCollection;


/**
 * Table Model für die Lagerbestand Tabelle
 * @author artjom
 * @athor Simon
 */
public class LagerbestandTableModel extends AbstractTableModel {
    LagerbestandCollection<Lagerbestand> lagerbestandRows = new LagerbestandCollection();
    
    /**
     * Setzt die Daten für die Lagerbestandtabelle
     * @param arr
     */
    public void setData(LagerbestandCollection<Lagerbestand> arr)
    {
        this.lagerbestandRows = arr;
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
                name = "Lagerort";
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
                name = "Bezeichnung";
                break;
            case 6:
                name = "Teil ID";
                break;
            case 7:
                name = "Menge";
                break;
            case 8:
                name = " Grund";
                break;
        }
        return name;
    }
    
        public Object getValueAt(int row, int col) {
            if (getRowCount() != 0){
                Lagerbestand rowO = null;
                rowO = (Lagerbestand) lagerbestandRows.get(row);
                Lagerfach lf = rowO.getLagerfach();
                if(lf != null){
                    Lager l = lf.getLager();
                }
                Teilebestand tl = rowO.getTeil();



                if(lagerbestandRows.size()>0){
                 rowO = (Lagerbestand) lagerbestandRows.get(row);
                    if(rowO != null){
                        switch(col){
                            case 0:
                                 return lf.getFachnummer();

                            case 1:

                                if(lf.getLager() == null){
                                } else {
                                 return lf.getLager().getLagerort();
                                }
                            case 2:
                                if(lf.getX() != 0){
                                return lf.getX();
                                }
                            case 3:
                                    if(lf.getY() != 0){
                                    return lf.getY();
                                    }
                            case 4:
                                    if(lf.getZ() != 0){
                                    return lf.getZ();
                                    }
                            case 5:
                                    if(tl.getBezeichnung() != null){
                                    return tl.getBezeichnung();
                                    }
                            case 6:
                                     if(tl.getIdentnummer() != 0){
                                    return tl.getIdentnummer();
                                    }
                            case 7:
                                     if(rowO.getMenge()!= 0){
                                    return rowO.getMenge();
                                 }
                            case 8:

                                    if(rowO.getAnschaffungsgrund() != null){
                                    return rowO.getAnschaffungsgrund();
                                }
                            default:     
                            return "empty";
                        }
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
