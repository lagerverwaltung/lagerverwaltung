/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.collection;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import helper.DatabaseManager;
import java.sql.SQLException;
import java.util.ArrayList; 
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Lager;
import model.Lagerbestand;
import model.Lagerfach;
import model.filter.LagerbestandFilterModel;

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
            
            QueryBuilder<model.Lagerbestand, Integer> queryBuilder = lagerbestandDao.queryBuilder();
            queryBuilder.where().gt("anzahl", 0);
                   
            PreparedQuery<model.Lagerbestand> preparedQuery = queryBuilder.prepare();
            List<Lagerbestand> list = (List<Lagerbestand>) lagerbestandDao.query(preparedQuery);
        
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
    public LagerbestandCollection<Lagerbestand> addFilter(LagerbestandFilterModel lfm) throws SQLException{
       
        List<model.Lagerbestand> result = new ArrayList();
        Dao<model.Lagerbestand, Integer> lagerbestandDao = DatabaseManager.getInstance().getLagerbestandDao();
        List<model.Lagerbestand> lbList = lagerbestandDao.queryForAll();
        
        if(lbList.size() > 0){
            for (int i = 0; i < lbList.size(); i++) {
                if(lbList.get(i).getLagerfach().getX() == lfm.getX()){
                  result.add(lbList.get(i));  
                }
                if (lbList.get(i).getLagerfach().getY() == lfm.getY()) {
                    result.add(lbList.get(i));
                }
                if (lbList.get(i).getLagerfach().getZ() == lfm.getZ()) {
                    result.add(lbList.get(i));
                }
                if (lbList.get(i).getLagerfach().getLager().getLagerort().equals(lfm.getLagerTyp())) {
                    result.add(lbList.get(i));
                }
                if (lbList.get(i).getMenge() <= lfm.getVonMenge() 
                        && lbList.get(i).getMenge() >= lfm.getBisMenge()
                        && lbList.get(i).getMenge() != 0) {
                    result.add(lbList.get(i));
                }
                if (lbList.get(i).getAnschaffungsgrund().contains(lfm.getGrund())) {
                    result.add(lbList.get(i));
                }
                
            }
        }
        for(model.Lagerbestand lb1 : result){
            add(lb1);
        }
        return this;
    }
    
    public void resetFilters()
    {
        
    }
}
