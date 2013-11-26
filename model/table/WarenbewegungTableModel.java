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

    @Override
    public Object getValueAt(int row, int col) {
        Warenbewegung rowO;
        if(warenRows.size()>0){
            rowO = (Warenbewegung) warenRows.get(row);
            DatabaseManager dbm = new DatabaseManager();
             List l = null;
                try {
                    l = dbm.getWarenbewegungDao().queryForEq("warenbID", rowO.getWarenBewegungsID());
                } catch (SQLException ex) {
                    Logger.getLogger(WarenbewegungTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                        Warenbewegung wbq = (Warenbewegung) l.get(0);
                        ForeignCollection<ZielPosition> alq;
                        alq = wbq.getArrZielPosition();
                        Warenbewegung wbz = (Warenbewegung) l.get(0);
                        ForeignCollection<ZielPosition> alz;
                        alz = wbz.getArrZielPosition();
            
            if(rowO != null){
                switch(col){
                    case 0:
                        //WarenbewegungsID
                        return rowO.getWarenBewegungsID();
                    case 1:
                        //Bezeichnung
                        if(rowO.getLagerbestand().getTeil().getBezeichnung() != null){
                            return rowO.getLagerbestand().getTeil().getBezeichnung();
                        }
                    case 2:
                        //name = "Quellfach";
                        for (ZielPosition z : alq) {
                            return z.getLagerfach().getFachnummer();
                        }
                    case 3:
                        //name = "Menge";
                        for (ZielPosition z : alq) {
                            return z.getMenge();
                        }
                    case 4:
                        //name = "Zielfach";
                        for (ZielPosition z : alz) {
                            return z.getLagerfach().getFachnummer();
                        }
                    case 5:
                        //name = "Menge";
                        for (ZielPosition z : alz) {
                            return z.getMenge();
                        }
                    case 6:
                        //name = "Verantwortlicher";
                        if(rowO.getVerantwortlicher() != null){
                            return rowO.getVerantwortlicher();
                        }
                    case 7:
                        //name = "Datum";
                        if(rowO.getDatum() != null){
                            return rowO.getDatum();
                        }
                    case 8:
                        //name = "Haltbar bis";
                        if(rowO.getHaltbarkeitsDatum() != null){
                            return rowO.getHaltbarkeitsDatum();
                        }
                    case 9:
                        //name = "Typ";
                        String typ = rowO.getLagerbestand().getTeil().getTyp().toString();
                        if(rowO != null){
                            return typ;
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
