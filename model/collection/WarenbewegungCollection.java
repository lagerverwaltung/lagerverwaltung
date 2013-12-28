/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.collection;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.stmt.QueryBuilder;
import helper.DatabaseManager;
import helper.Misc;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ZielPosition;
import model.filter.WarenbewegungFilterModel;


/**
 * @param <Warenbewegung>
 * @author simon
 */
public class WarenbewegungCollection<Warenbewegung> extends ArrayList {
    
    private static WarenbewegungCollection singleton;
    
    public static WarenbewegungCollection getInstance()
    {
        if (WarenbewegungCollection.singleton == null){
            singleton = new WarenbewegungCollection();
        }
        return singleton;
    }
    
    public static WarenbewegungCollection getInstance(boolean refresh) {
        singleton = WarenbewegungCollection.getInstance();
        return singleton.loadCollection();
    }
   
    public WarenbewegungCollection() {
        loadCollection();
    }
    
    public WarenbewegungCollection<Warenbewegung> loadCollection()
    {
        try {
            Dao<model.Warenbewegung, Integer> warenbewegungDao = DatabaseManager.getInstance().getWarenbewegungDao();
            List<Warenbewegung> list = (List<Warenbewegung>) warenbewegungDao.queryForAll();
            
            clear();
            for(Warenbewegung wb : list){
                add(wb);
            }
            return this;
        } catch(SQLException ex){
            Misc.printSQLException(null, ex);
        }
        return singleton;
    }
    
    public WarenbewegungCollection<Warenbewegung> applyFilter(WarenbewegungFilterModel wfm) throws SQLException{
        
        Dao<model.Warenbewegung, Integer> warenbewegungDao = DatabaseManager.getInstance().getWarenbewegungDao();
        List<model.Warenbewegung> resultA = warenbewegungDao.queryForAll();
        List<model.Warenbewegung> resultB = new ArrayList();
        ForeignCollection<ZielPosition> fc;
        
        if(resultA.size() > 0){
            for(int i = 0; i < resultA.size(); i++){
                if(resultA.get(i).getHaltbarkeitsDatum().after(wfm.getHaltbarVon())
                        || resultA.get(i).getHaltbarkeitsDatum().before(wfm.getBisHaltbarkeit())){
                    resultB.add(resultB.get(i));
                }
            }
            resultA.clear();
            
            for(int i = 0; i < resultB.size(); i++){
                if(resultB.get(i).getDatum().after(wfm.getDatumVon())
                        || resultB.get(i).getDatum().before(wfm.getDatumBis())){
                    resultA.add(resultB.get(i));
                }
            }
            resultB.clear();
            
            for (int i = 0; i < resultA.size(); i++) {
                if (resultA.get(i).getQuellFach().getLager().getLagerort().equals(wfm.getqLagerort())) {
                    resultB.add(resultB.get(i));
                }
            }
            resultA.clear();

            for (int i = 0; i < resultB.size(); i++) {
                if (resultB.get(i).getQuellFach().getX() == wfm.getqX()) {
                    resultA.add(resultB.get(i));
                }
            }
            resultB.clear();
            
            for (int i = 0; i < resultA.size(); i++) {
                if (resultA.get(i).getQuellFach().getY() == wfm.getqY()) {
                    resultB.add(resultB.get(i));
                }
            }
            resultA.clear();

            for (int i = 0; i < resultB.size(); i++) {
                if (resultB.get(i).getQuellFach().getZ() == wfm.getqZ()) {
                    resultA.add(resultB.get(i));
                }
            }
            resultB.clear();
            
            for (int i = 0; i < resultA.size(); i++) {
                fc = resultA.get(i).getArrZielPosition();
                for (ZielPosition zp : fc) {
                    if (zp.getLagerfach().getLager().getLagerort().equals(wfm.getzLagerort())) {
                        resultB.add(resultA.get(i));
                    }
                }
            }
            resultA.clear();

            for (int i = 0; i < resultB.size(); i++) {
                fc = resultB.get(i).getArrZielPosition();
                for (ZielPosition zp : fc) {
                    if (zp.getLagerfach().getX() == wfm.getzX()) {
                        resultA.add(resultB.get(i));
                    }
                }
            }
            resultB.clear();

            for (int i = 0; i < resultA.size(); i++) {
                fc = resultA.get(i).getArrZielPosition();
                for (ZielPosition zp : fc) {
                    if (zp.getLagerfach().getY() == wfm.getzY()) {
                        resultB.add(resultA.get(i));
                    }
                }
            }
            resultA.clear();

            for (int i = 0; i < resultB.size(); i++) {
                fc = resultB.get(i).getArrZielPosition();
                for (ZielPosition zp : fc) {
                    if (zp.getLagerfach().getZ() == wfm.getzZ()) {
                        resultA.add(resultB.get(i));
                    }
                }
            }
            resultB.clear();
            
            for (int i = 0; i < resultA.size(); i++) {
                if (resultA.get(i).getLagerbestand().getTeil().getBezeichnung().contains(wfm.getBezeichnung())) {
                    resultB.add(resultB.get(i));
                }
            }
            resultA.clear();

            for (int i = 0; i < resultB.size(); i++) {
                if (resultB.get(i).getLagerbestand().getTeil().getTyp().equals(wfm.getTyp())) {
                    resultA.add(resultB.get(i));
                }
            }
            resultB.clear();
        }
        
        
        this.clear();
        for(model.Warenbewegung wb1 : resultA){
            add(wb1);
        }
        return this;
    }
    
}
