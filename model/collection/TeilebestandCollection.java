/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.collection;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import helper.DatabaseManager;
import helper.Misc;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.filter.TeilebestandFilterModel;

/** Enthält die Daten für den Teilbestand und hält Filterfunktionen bereit
 * Ist eine ArrayList welche Teilebestandsobjekte enthält
 * @param <Teilebestand> Teilebestand Objekt
 * @author simon
 */
public class TeilebestandCollection<Teilebestand> extends ArrayList {
    private static TeilebestandCollection singleton;
    
    /**
     * Liefert eine Collection Instanz (Singleton)
     * @return
     */
    public static TeilebestandCollection getInstance()
    {
        if (TeilebestandCollection.singleton == null){
            singleton = new TeilebestandCollection();
        }
        return singleton;
    }
    
    /**
     * Konstruktor
     */
    public TeilebestandCollection() {
        loadCollection();
    }
    
    /**
     * Liefert eine Instanz und erneuert die Daten
     * @param refresh
     * @return
     */
    public static TeilebestandCollection getInstance(boolean refresh)
    {
         singleton = TeilebestandCollection.getInstance();
         return singleton.loadCollection();
    }
    
   
    /**
     * Läd die Daten neu
     * @return TeilebestandCollection<Teilebestand> Eine Liste mit Teilen
     */
    public TeilebestandCollection<Teilebestand> loadCollection()
    { 
        try {
            Dao<model.Teilebestand, Integer> teilebestandDao = DatabaseManager.getInstance().getTeilebestandDao();
            
            QueryBuilder<model.Teilebestand, Integer> queryBuilder = teilebestandDao.queryBuilder();
            queryBuilder.where().eq("deleted", false);
                   
            PreparedQuery<model.Teilebestand> preparedQuery = queryBuilder.prepare();
            List<Teilebestand> list = (List<Teilebestand>) teilebestandDao.query(preparedQuery);
            clear();
            for (Teilebestand tb1 : list) {
                add(tb1);
            }
            return this;
        } catch (SQLException ex) {
            Misc.printSQLException(null, ex);
        }
        return singleton;
    }
    
    /**
     * Grenzt die Collection nach der im TeileFilterModel-Objekt
     * angegebenen Attributen ein
     * @author ssinger
     * @param tfm
     * @return TeilebestandCollection
     * @throws SQLException
     */
    public TeilebestandCollection<Teilebestand> applyFilter(TeilebestandFilterModel tfm) throws SQLException {

        Dao<model.Teilebestand, Integer> teileDao = DatabaseManager.getInstance().getTeilebestandDao();
        QueryBuilder<model.Teilebestand, Integer> queryBuilder = teileDao.queryBuilder();

        Where<model.Teilebestand, Integer> where = queryBuilder.where();

        where.like("materialgruppe", tfm.getMaterialgruppr())
                .and()
                .like("zeichnungsnummer", tfm.getZeichnungsnummer())
                .and()
                .between("preis", tfm.getVonPreis(), tfm.getBisPreis())
                .and()
                .between("ve", tfm.getVonVe(), tfm.getBisVe())
                .and()
                .eq("deleted", false)
                .and()
                .like("bezeichnung", tfm.getBezeichnung());

        if (tfm.getTyp() != null) {
            where.and().eq("typ", tfm.getTyp());
        }

        PreparedQuery<model.Teilebestand> preparedQuery = queryBuilder.prepare();

        List<model.Teilebestand> list = teileDao.query(preparedQuery);
        this.clear();
        for (model.Teilebestand tb1 : list) {
            add(tb1);
        }
        return this;
    }
}
