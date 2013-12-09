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
import model.Teilebestand;
import model.filter.TeilebestandFilterModel;
import model.table.TeileTableModel;
import view.TeileFilterFrame;

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
    
    public static TeilebestandCollection getInstance(TeilebestandFilterModel tfm) throws SQLException{
        
        if(TeilebestandCollection.singleton == null){
            singleton = new TeilebestandCollection().addFilter(tfm);
        }
        
        return singleton.addFilter(tfm);
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
    
    /*
     * @param TeilebestandFilterModel welches angewand werden sollen
     */
    public TeilebestandCollection<Teilebestand> addFilter(TeilebestandFilterModel tfm) throws SQLException {

        Dao<model.Teilebestand, Integer> teileDao = DatabaseManager.getInstance().getTeilebestandDao();
        QueryBuilder<model.Teilebestand, Integer> queryBuilder = teileDao.queryBuilder();
        queryBuilder.where()
                .like("materialgruppe", tfm.getMaterialgruppr())
                .and()
                .like("zeichnungsnummer", tfm.getZeichnungsnummer())
                .and()
                .between("preis", tfm.getVonPreis(), tfm.getBisPreis())
                .and()
                .between("ve", tfm.getVonVe(), tfm.getBisVe());
        PreparedQuery<model.Teilebestand> preparedQuery = queryBuilder.prepare();
        List<model.Teilebestand> list = teileDao.query(preparedQuery);
        for (model.Teilebestand tb1 : list) {
            add(tb1);
        }
        return this;

    }
    
    public void resetFilters()
    {
        
    }

    
}
