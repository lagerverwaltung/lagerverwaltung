/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.collection;
import com.j256.ormlite.dao.Dao;
import helper.DatabaseManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @param <Teilebestand>
 * @author simon
 */
public class TeilebestandCollection<Teilebestand> extends ArrayList {
    private static TeilebestandCollection singleton;
    
    public static TeilebestandCollection getInstance()
    {
        if (TeilebestandCollection.singleton == null){
            singleton = new TeilebestandCollection();
        }
        return singleton;
    }
    
    public TeilebestandCollection() {
        loadCollection();
    }
    
    public static TeilebestandCollection getInstance(boolean refresh)
    {
         singleton = TeilebestandCollection.getInstance();
         return singleton.loadCollection();
    }
   
    public TeilebestandCollection<Teilebestand> loadCollection()
    { 
        try {
            Dao<model.Teilebestand, Integer> teilebestandDao = DatabaseManager.getInstance().getTeilebestandDao();
            List<Teilebestand> list =  (List<Teilebestand>) teilebestandDao.queryForAll();
            clear();
            for (Teilebestand tb1 : list) {
                add(tb1);
            }
            return this;
        } catch (SQLException ex) {
            Logger.getLogger(TeilebestandCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return singleton;
    }
    
    public void addFilter(String attributeName, String attributeValue){
        
    }
    
    public void resetFilters()
    {
        
    }

    
}
