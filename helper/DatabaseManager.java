/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Lager;
import view.MainFrame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Lagerbestand;
import model.Lagerfach;
import model.Teilebestand;
import model.Warenbewegung;
import model.ZielPosition;

/**
 *
 * @author simon
 */
public class DatabaseManager {
    
    private final static String DATABASE_URL = "jdbc:sqlite:lager.db";
    ConnectionSource connectionSource;
            
    private Dao<Lager, Integer> lagerDao;
    private Dao<Lagerfach, Integer> lagerfachDao;
    private Dao<Teilebestand, Integer> teilebestandDao;
    private Dao<Lagerbestand, Integer> lagerbestandDao;
    private Dao<ZielPosition, Integer> zielpositionDao;
    private Dao<Warenbewegung, Integer> warenbewegungDao;
    
    private static DatabaseManager singleton;

    public DatabaseManager()  {
        try {
            setupDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static DatabaseManager getInstance()
    {
        if (DatabaseManager.singleton == null){
            singleton = new DatabaseManager();
        }
        return singleton;
    }
    
    private void setupDatabase() throws SQLException {
            try {
                    connectionSource = new JdbcConnectionSource(DATABASE_URL);
            } finally {
                if (connectionSource != null) {
                        connectionSource.close();
                }
            }
            TableUtils.createTableIfNotExists(connectionSource, Lager.class);
            TableUtils.createTableIfNotExists(connectionSource, Lagerfach.class);
            TableUtils.createTableIfNotExists(connectionSource, Teilebestand.class);
            TableUtils.createTableIfNotExists(connectionSource, Lagerbestand.class);
            TableUtils.createTableIfNotExists(connectionSource, ZielPosition.class);
            TableUtils.createTableIfNotExists(connectionSource, Warenbewegung.class);
        
            lagerDao = DaoManager.createDao(connectionSource, Lager.class);
            lagerfachDao = DaoManager.createDao(connectionSource, Lagerfach.class);
            teilebestandDao = DaoManager.createDao(connectionSource, Teilebestand.class);
            lagerbestandDao = DaoManager.createDao(connectionSource, Lagerbestand.class);
            zielpositionDao = DaoManager.createDao(connectionSource, ZielPosition.class);
            warenbewegungDao = DaoManager.createDao(connectionSource, Warenbewegung.class);
            
            /* Test Komplettzusammenhang
            Lager t = new Lager();
            t.setLagerID(1);
            t.setBreite(1);
            t.setHoehe(1);
            t.setTiefe(1);
            t.setKleinVE(1);
            t.setMittelVE(10);
            t.setGrossVE(100);
            t.setLagerort(Lager.Lagerort.freilager);
            lagerDao.createOrUpdate(t);
            
            Lager t1 = new Lager();
            t1.setLagerID(2);
            t1.setBreite(1);
            t1.setHoehe(1);
            t1.setTiefe(1);
            t1.setKleinVE(1);
            t1.setMittelVE(10);
            t1.setGrossVE(100);
            t1.setLagerort(Lager.Lagerort.hochregal);
            lagerDao.createOrUpdate(t1);
            
            Lagerfach lf = new Lagerfach();
            lf.setFachnummer(1);
            lf.setBemerkung("fddfdfdf");
            lf.setLager(t);
            lagerfachDao.createOrUpdate(lf);
            
            Lagerfach lf1 = new Lagerfach();
            lf1.setFachnummer(2);
            lf1.setBemerkung("fddfdfdf");
            lf1.setLager(t1);
            lagerfachDao.createOrUpdate(lf1);
            
            Teilebestand tb = new Teilebestand();
            tb.setBezeichnung("Schraube M5");
            tb.setMaterialgruppe("Materialgruppe");
            tb.setZeichnungsnummer(121212);
            tb.setPreis((float)180.12);
            tb.setVe(1);
            teilebestandDao.createOrUpdate(tb);
            
            Lagerbestand lb = new Lagerbestand();
            lb.setTeil(tb);
            lb.setLagerfach(lf1);
            lb.setMenge(15);
            lb.setAnschaffungsgrund("Brauchen wir");
            lagerbestandDao.createOrUpdate(lb);
            
            Warenbewegung wb = new Warenbewegung();
            wb.setVerantwortlicher("Simon");
            wb.setLagerbestand(lb);
            warenbewegungDao.create(wb);
            int  w = warenbewegungDao.extractId(wb);
            
            ZielPosition z1 = new ZielPosition();
            z1.setLagerfach(lf1);
            z1.setMenge(10);
            z1.setWarenbewegung(wb);
            zielpositionDao.createOrUpdate(z1);
            
            ZielPosition z2 = new ZielPosition();
            z2.setLagerfach(lf);
            z2.setMenge(10);
            z2.setWarenbewegung(wb);
            zielpositionDao.createOrUpdate(z2);
            */
    }

    public Dao<Lager, Integer> getLagerDao() {
        return lagerDao;
    }

    public Dao<Lagerfach, Integer> getLagerfachDao() {
        return lagerfachDao;
    }

    public Dao<Teilebestand, Integer> getTeilebestandDao() {
        return teilebestandDao;
    }

    public Dao<Lagerbestand, Integer> getLagerbestandDao() {
        return lagerbestandDao;
    }

    public Dao<ZielPosition, Integer> getZielpositionDao() {
        return zielpositionDao;
    }

    public Dao<Warenbewegung, Integer> getWarenbewegungDao() {
        return warenbewegungDao;
    }
    
    
}
