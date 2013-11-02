/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.collection;

import helper.DatabaseManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Teilebestand;

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
    
    public LagerbestandCollection() {
        loadCollection();
    }
    
    public LagerbestandCollection<Lagerbestand> loadCollection()
    {
        return this;
    }
    
    public void addFilter(String attributeName, String attributeValue){
        
    }
    
    public void resetFilters()
    {
        
    }
}
