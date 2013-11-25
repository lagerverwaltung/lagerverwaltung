/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.collection;

import com.j256.ormlite.dao.Dao;
import helper.DatabaseManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Teilebestand;

/**
 *
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
    
    public void addFilter(String attributeName, String attributeValue){
        
    }
    
    public void resetFilters()
    {
        
    }
}
