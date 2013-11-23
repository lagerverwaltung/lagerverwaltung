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
import model.Lagerbestand;

/**
 *
 * @author simon
 */
public class LagerbestandCollection<Lagerbestand> extends ArrayList {
    
    private static LagerbestandCollection singleton;
    
    public static LagerbestandCollection getInstance()
    {
        if (LagerbestandCollection.singleton == null){
            singleton = new LagerbestandCollection();
        }
        return singleton;
    }
    public static LagerbestandCollection getInstance(boolean refresh)
    {
         singleton = LagerbestandCollection.getInstance();
         return singleton.loadCollection();
    }
    public LagerbestandCollection() {
        loadCollection();
    }
    
    // verändert->analog zu TeilebestandCollection
    public LagerbestandCollection<Lagerbestand> loadCollection()
    {
       try {
            Dao<model.Lagerbestand, Integer> lagerbestandDao = DatabaseManager.getInstance().getLagerbestandDao();
            List<Lagerbestand> list =  (List<Lagerbestand>) lagerbestandDao.queryForAll();
            clear();
            for (Lagerbestand lb1 : list) {
                add(lb1);
            }
            return this;
        } catch (SQLException ex) {
            Logger.getLogger(LagerbestandCollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return singleton;
    }
    //ab hier 2.Sprint
    public void addFilter(String attributeName, String attributeValue){
        
    }
    
    public void resetFilters()
    {
        
    }
}
