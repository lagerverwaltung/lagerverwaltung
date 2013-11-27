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
        rowO = (Warenbewegung) warenRows.get(row);
        Lagerbestand lb = rowO.getLagerbestand();
        Lagerfach lf = lb.getLagerfach();
        Teilebestand tl = lb.getTeil();
        String s = new String();
        s = "vergessen";
      //  ZielPosition zp = rowO.getZielPosition();
        
        if(warenRows.size()>0){
            
        //    DatabaseManager dbm = new DatabaseManager();
          //   List l = null;
            /*    try {
                    l = dbm.getWarenbewegungDao().queryForEq("warenbID", rowO.getWarenBewegungsID());
                } catch (SQLException ex) {
                    Logger.getLogger(WarenbewegungTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                        Warenbewegung wbq = (Warenbewegung) l.get(0);
                        ForeignCollection<ZielPosition> alq;
                        alq = wbq.getArrZielPosition();
                        Warenbewegung wbz = (Warenbewegung) l.get(0);
                        ForeignCollection<ZielPosition> alz;
                        alz = wbz.getArrZielPosition();*/
            
            if(rowO != null){
                switch(col){
                    case 0:
                        //WarenbewegungsID
                        return rowO.getWarenBewegungsID();
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
                        if(lb.getMenge() != 0){
                            return lb.getMenge();
                        }
                    case 4:
                        //name = "Zielfach";
                   //   if( zp.getLagerfach()!= null){
                    //       return zp.getLagerfach(); }
                    //    return s;
                       
                    case 5:
                        //name = "Menge";
                     //   if( zp.getMenge()!= 0){
                        //    return zp.getMenge();}
                            return s;
                        
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
