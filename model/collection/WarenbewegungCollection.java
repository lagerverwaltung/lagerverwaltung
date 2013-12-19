/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.collection;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import helper.DatabaseManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            Logger.getLogger(WarenbewegungCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return singleton;
    }
    
    public WarenbewegungCollection<Warenbewegung> addFilter(WarenbewegungFilterModel wfm) throws SQLException{
        Dao<model.Warenbewegung, Integer> warenbewegungDao = DatabaseManager.getInstance().getWarenbewegungDao();
        Dao<model.ZielPosition, Integer> zielPositionDao = DatabaseManager.getInstance().getZielpositionDao();
        
        QueryBuilder<model.Warenbewegung, Integer> warenQb = warenbewegungDao.queryBuilder();
        QueryBuilder<model.ZielPosition, Integer> zielQb = zielPositionDao.queryBuilder();

        //warenQb.where().eq
        //List<model.Warenbewegung> wbList = warenbewegungDao.queryForAll();
        
        
        return this;
    }
    
    public void resetFilters()
    {
        
    }
}
