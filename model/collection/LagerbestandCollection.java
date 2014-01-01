/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.collection;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import helper.DatabaseManager;
import helper.Misc;
import java.sql.SQLException;
import java.util.ArrayList; 
import java.util.List;
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
    
    public static LagerbestandCollection getInstance(boolean refresh) {
        singleton = LagerbestandCollection.getInstance();
        return singleton.loadCollection();
    }
    
    public LagerbestandCollection() {
        loadCollection();
    }
    
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
            Misc.printSQLException(null, ex);
        }
        return singleton;
    }
    
    /**
     * @author ssinger
     * @param lfm
     * @return gefilterte Liste<Lagerbestand>
     * @throws SQLException 
     * Nutzt als Ausgangslage eine Liste aller Lagerbestands-Objekte, führt für
     * die Filterung für jedes LagerbestandFilterModel-Attribut eine Schleife
     * aus. Die Zwischenspeicherung führt über ein Listen - Ping - Pong
     * statt. Gibt die gefilterte Collection zurück.
     */
    public LagerbestandCollection<Lagerbestand> applyFilter(LagerbestandFilterModel lfm) throws SQLException{
       
        
        Dao<model.Lagerbestand, Integer> lagerbestandDao = DatabaseManager.getInstance().getLagerbestandDao();
        List<model.Lagerbestand> resultA = new ArrayList();
        List<model.Lagerbestand> resultB = new ArrayList();
        List<model.Lagerbestand> lbList = lagerbestandDao.queryForAll();
        
        if (lbList.size() > 0) {
            for (int i = 0; i < lbList.size(); i++) {
                if ((lbList.get(i).getLagerfach().getX() == lfm.getX())
                        || (lfm.getX() == 0 )) {
                    resultA.add(lbList.get(i));
                }
            }
            
            for (int i = 0; i < resultA.size(); i++) {
                if ((resultA.get(i).getLagerfach().getY() == lfm.getY())
                        || (lfm.getY() == 0 )) {
                    resultB.add(resultA.get(i));
                }
            }
            resultA.clear();
            
            for (int i = 0; i < resultB.size(); i++) {
                if ((resultB.get(i).getLagerfach().getZ() == lfm.getZ())
                        || (lfm.getZ() == 0 )) {
                    resultA.add(resultB.get(i));
                }
            }
            resultB.clear();
            
            for (int i = 0; i < resultA.size(); i++) {
                if ((resultA.get(i).getLagerfach().getLager().getLagerort().equals(lfm.getLagerTyp()))
                        || (lfm.getLagerTyp() == null)) {
                    resultB.add(resultA.get(i));
                }
            }
            resultA.clear();
            
            for (int i = 0; i < resultB.size(); i++) {
                if (((resultB.get(i).getMenge() >= lfm.getVonMenge())
                        && (resultB.get(i).getMenge() <= lfm.getBisMenge()))) {
                    resultA.add(resultB.get(i));
                }
            }
            resultB.clear();
            
            for (int i = 0; i < resultA.size(); i++) {
                if ((lfm.getGrund() == null)
                        || (resultA.get(i).getAnschaffungsgrund().contains(lfm.getGrund()))) {
                    resultB.add(resultA.get(i));
                }
            }
            resultA.clear();
            
            for(int i = 0; i < resultB.size(); i++){
                if(resultB.get(i).getMenge() > 0){
                    resultA.add(resultB.get(i));
                }
            }
            resultB.clear();
            
            for(int i = 0; i < resultA.size(); i++){
                if((lfm.getBezeichnung() == null)
                        || (resultA.get(i).getTeil().getBezeichnung().contains(lfm.getBezeichnung()))){
                    resultB.add(resultA.get(i));
                }
            }
        }

        this.clear();
        for(model.Lagerbestand lb1 : resultB){
            add(lb1);
        }
        return this;
    }
    
}
