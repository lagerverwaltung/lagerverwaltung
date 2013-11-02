/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.collection;

import java.util.ArrayList;
import java.util.Collection;
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
        return this;
    }
    
    public void addFilter(String attributeName, String attributeValue){
        
    }
    
    public void resetFilters()
    {
        
    }
}
