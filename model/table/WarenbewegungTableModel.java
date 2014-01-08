/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.table;

import com.j256.ormlite.dao.ForeignCollection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;
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
        this.warenRows = arr;
    }
    
    @Override
    public int getColumnCount() {
        return 9;
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
                name = "WID";
                break;
            case 1:
                name = "Teil";
                break;
            case 2:
                name = "Aktion";
                break;
            case 3:
                name = "Quellfach";
                break;
            case 4:
                name = "Mng";
                break;
            case 5:
                name = "Zielfächer";
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
        }
        return name;
    }

    @Override
     public Object getValueAt(int row, int col) {
        Warenbewegung wb;
        if (getRowCount() != 0){
            wb = (Warenbewegung) warenRows.get(row);
            Lagerbestand lb = wb.getLagerbestand();
            Lagerfach lf = null;
            Teilebestand tl = null;
            if(lb!=null){
                lf = lb.getLagerfach();
                tl = lb.getTeil();
            }
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            df.setTimeZone(TimeZone.getTimeZone("CET"));
            df.setLenient(false);

            int qGes = 0;
            String strZiel = "";
            ForeignCollection<ZielPosition> al;
            al = wb.getArrZielPosition();
            int i = 1;
            for (ZielPosition z : al) {
                strZiel += "Ziel "+i+": Menge "+z.getMenge()+" an "+z.getLagerfach()+"\n";
                qGes += z.getMenge();
                i++;
            }
            strZiel += "";

            if(warenRows.size()>0){
                if(wb != null){
                    switch(col){
                        case 0:
                            return wb.getWarenBewegungsID();
                        case 1:
                            if(tl != null && tl.getBezeichnung() != null){
                                return tl.getBezeichnung();
                            }
                        case 2:
                            return wb.getActionName();
                        case 3:
                            if(lf != null && lf.getFachnummer()!= 0){
                                return lf;
                            }
                        case 4:
                            if(qGes != 0){
                                return qGes;
                            }
                        case 5:
                            return strZiel;
                        case 6:
                        if(wb.getVerantwortlicher() != null){
                            return wb.getVerantwortlicher();
                        }
                        case 7:
                            if(wb.getDatum() != null){
                                DateFormat formatter = new SimpleDateFormat("dd.MM.yy H:mm");
                                formatter.setTimeZone(TimeZone.getTimeZone("CET"));
                                formatter.setLenient(false);
                                return formatter.format( wb.getDatum() );
                            }else{
                                return "";
                            }
                        case 8:
                            if(wb.getHaltbarkeitsDatum() != null){
                                return df.format(wb.getHaltbarkeitsDatum());
                            }else{
                                return "";
                            }
                        default:
                            return "empty";
                    }
                }
            }
        }
        return "notfoudnd";
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
