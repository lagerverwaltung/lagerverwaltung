/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.table;

import com.j256.ormlite.dao.ForeignCollection;
import helper.DatabaseManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import model.Lagerbestand;
import model.Lagerfach;
import model.Teilebestand;
import model.Warenbewegung;
import model.ZielPosition;
import model.collection.WarenbewegungCollection;

/**
 *
 * @author simon
 */
public class WarenbewegungTableModel extends AbstractTableModel{

    WarenbewegungCollection<Warenbewegung> warenRows = new WarenbewegungCollection();
    ArrayList<Object[]> warenRowsArr = new ArrayList();
    
    public void setData(WarenbewegungCollection<Warenbewegung> arr)
    {
        this.warenRowsArr = arr;
    }
    
    @Override
    public int getColumnCount() {
        return 10;
    }
    
    @Override
    public int getRowCount() {
        return warenRows.size();
    }

    @Override
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
                name = "Zielfach/fächer";
                break;
            case 5:
                name = "Verantwortlicher";
                break;
            case 6:
                name = "Datum";
                break;
            case 7:
                name = "Haltbar bis";
                break;
            case 8:
                name = "Typ";
                break;
        }
        return name;
    }

    @Override
     public Object getValueAt(int row, int col) {
        Warenbewegung wb;
        wb = (Warenbewegung) warenRows.get(row);
        Lagerbestand lb = wb.getLagerbestand();
        Lagerfach lf = lb.getLagerfach();
        Teilebestand tl = lb.getTeil();
        String s = new String();
        s = "vergessen";
        
        int qGes = 0;
        String strZiel = "<html>";
        ForeignCollection<ZielPosition> al;
        al = wb.getArrZielPosition();
        int i = 1;
        for (ZielPosition z : al) {
            strZiel += "Ziel "+i+" Menge "+z.getMenge()+" an "+z.getLagerfach();
            qGes += z.getMenge();
        }
        strZiel += "</html>";
        
        if(warenRows.size()>0){
            if(wb != null){
                switch(col){
                    case 0:
                        //WarenbewegungsID
                        return wb.getWarenBewegungsID();
                    case 1:
                        //Bezeichnung
                        if(tl.getBezeichnung() != null){
                            return tl.getBezeichnung();
                        }
                    case 2:
                        if(lf.getFachnummer()!= 0){
                            return lf.getFachnummer();
                        }
                    case 3:
                        //name = "Menge" aus dem Lagerbestand;
                        if(qGes != 0){
                            return qGes;
                        }
                    case 4:
                        return strZiel;
                    case 5:
                    //name = "Verantwortlicher";
                    if(wb.getVerantwortlicher() != null){
                        return wb.getVerantwortlicher();
                    }
                    case 6:
                        //name = "Datum";
                        if(wb.getDatum() != null){
                            return wb.getDatum();
                        }
                    case 7:
                        //name = "Haltbar bis";
                        if(wb.getHaltbarkeitsDatum() != null){
                            return wb.getHaltbarkeitsDatum();
                        }
                    case 8:
                        //name = "Typ";
                         if(tl.getTyp()!= null){
                            return tl.getTyp();
                        }
                    default:
                        return "empty";
                }
                
            }
        }
        return "notfoudnd";
        /*if(warenbRows.size()>0){
            Object[] commisionColumns = warenbRows.get(row);
            return commisionColumns[col];
        }
        return "notfound";
        */
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Verhindert das Editieren der Zellen
     */
    @Override
    public boolean isCellEditable(int row, int col) {
       return false;
    }
}
