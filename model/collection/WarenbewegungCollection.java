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


/** Enthält die Daten für den Warenbewegung und hält Filterfunktionen bereit
 * Ist eine ArrayList welche Warenbewegungsobjekte enthält
 * @param <Warenbewegung> Warenbewegung Objekt
 * @author simon
 */
public class WarenbewegungCollection<Warenbewegung> extends ArrayList {
    
    private static WarenbewegungCollection singleton;
    
    /**
     * Liefert die Collection Instanz
     * @return
     */
    public static WarenbewegungCollection getInstance()
    {
        if (WarenbewegungCollection.singleton == null){
            singleton = new WarenbewegungCollection();
        }
        return singleton;
    }
    
    /**
     * Liefert die Collection Instanz und läd diese neu
     * @param refresh
     * @return
     */
    public static WarenbewegungCollection getInstance(boolean refresh) {
        singleton = WarenbewegungCollection.getInstance();
        return singleton.loadCollection();
    }
   
    /**
     * Konstruktor
     */
    public WarenbewegungCollection() {
        loadCollection();
    }
    
    /**
     * Läd die Warenbewegung Collection
     * @return Liste mit Warenbewegungen
     */
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
    
    /**
     * Wendet einen Filter an
     * @param wfm  WarenbewegungFilterModel
     * @return Liste mit Warenbewegungsobjekten
     * @throws SQLException
     */
    public WarenbewegungCollection<Warenbewegung> applyFilter(WarenbewegungFilterModel wfm) throws SQLException{
        
        Dao<model.Warenbewegung, Integer> warenbewegungDao = DatabaseManager.getInstance().getWarenbewegungDao();
        List<model.Warenbewegung> resultA = warenbewegungDao.queryForAll();
        List<model.Warenbewegung> resultB = new ArrayList();
        ForeignCollection<ZielPosition> fc;
        
        if(resultA.size() > 0){
            for (int i = 0; i < resultA.size(); i++) {
                if (wfm.getExpireID() > 0) {
                    if ((resultA.get(i).getHaltbarkeitsDatum() == null)
                            || (resultA.get(i).getHaltbarkeitsDatum().after(wfm.getHaltbarVon())
                            && resultA.get(i).getHaltbarkeitsDatum().before(wfm.getBisHaltbarkeit()))) {
                        resultB.add(resultA.get(i));


                    } else {
                        resultB.add(resultA.get(i));
                    }
                }
            }
            resultA.clear();
            
            for(int i = 0; i < resultB.size(); i++){
                if(resultB.get(i).getDatum().after(wfm.getDatumVon())
                        && resultB.get(i).getDatum().before(wfm.getDatumBis())){
                    resultA.add(resultB.get(i));
                }
            }
            resultB.clear();
            
            for (int i = 0; i < resultA.size(); i++) {
                if ((resultA.get(i).getLagerbestand().getLagerfach().getLager().getLagerort().equals(wfm.getqLagerort()))
                         || (wfm.getqLagerort() == null)) {
                    resultB.add(resultA.get(i));
                }
            }
            resultA.clear();

            for (int i = 0; i < resultB.size(); i++) {
                if ((wfm.getqX() == 0) 
                        || resultB.get(i).getLagerbestand().getLagerfach().getX() == wfm.getqX()) {
                    resultA.add(resultB.get(i));
                }
            }
            resultB.clear();
            
            for (int i = 0; i < resultA.size(); i++) {
                if ((wfm.getqY() == 0)
                        || resultA.get(i).getLagerbestand().getLagerfach().getY() == wfm.getqY()) {
                    resultB.add(resultA.get(i));
                }
            }
            resultA.clear();

            for (int i = 0; i < resultB.size(); i++) {
                if ((wfm.getqZ() == 0)
                        || resultB.get(i).getLagerbestand().getLagerfach().getZ() == wfm.getqZ()) {
                    resultA.add(resultB.get(i));
                }
            }
            resultB.clear();
            
            for (int i = 0; i < resultA.size(); i++) {
                fc = resultA.get(i).getArrZielPosition();
                for (ZielPosition zp : fc) {
                    if (zp.getLagerfach().getLager().getLagerort().equals(wfm.getzLagerort())
                            || (wfm.getzLagerort() == null)) {
                        resultB.add(resultA.get(i));
                    }
                }
            }
            resultA.clear();

            for (int i = 0; i < resultB.size(); i++) {
                fc = resultB.get(i).getArrZielPosition();
                for (ZielPosition zp : fc) {
                    if ((wfm.getzX() == 0)
                            || zp.getLagerfach().getX() == wfm.getzX()) {
                        resultA.add(resultB.get(i));
                    }
                }
            }
            resultB.clear();

            for (int i = 0; i < resultA.size(); i++) {
                fc = resultA.get(i).getArrZielPosition();
                for (ZielPosition zp : fc) {
                    if ((wfm.getzY() == 0)
                            || zp.getLagerfach().getY() == wfm.getzY()) {
                        resultB.add(resultA.get(i));
                    }
                }
            }
            resultA.clear();

            for (int i = 0; i < resultB.size(); i++) {
                fc = resultB.get(i).getArrZielPosition();
                for (ZielPosition zp : fc) {
                    if ((wfm.getzZ() == 0)
                        || zp.getLagerfach().getZ() == wfm.getzZ()) {
                        resultA.add(resultB.get(i));
                    }
                }
            }
            resultB.clear();
            
            for (int i = 0; i < resultA.size(); i++) {
                if ((wfm.getBezeichnung() == null)
                        ||resultA.get(i).getLagerbestand().getTeil().getBezeichnung().contains(wfm.getBezeichnung())) {
                    resultB.add(resultA.get(i));
                }
            }
            resultA.clear();

            for (int i = 0; i < resultB.size(); i++) {
                if ((wfm.getTyp() == null)
                        ||resultB.get(i).getLagerbestand().getTeil().getTyp().equals(wfm.getTyp())) {
                    resultA.add(resultB.get(i));
                }
            }
            resultB.clear();
            
            for(int i = 0; i < resultA.size(); i++){
                if(wfm.getBewegungsTyp() == 0
                        || resultA.get(i).getActionCode() == wfm.getBewegungsTyp()
                        || ( resultA.get(i).getActionCode() == 2
                        && wfm.getBewegungsTyp() == 1)){
                    resultB.add(resultA.get(i));
                }
            }
            resultA.clear();
        }
        
        this.clear();
        for(model.Warenbewegung wb1 : resultB){
            add(wb1);
        }
        return this;
    }
    
}
