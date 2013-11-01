/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.collections;

import java.util.ArrayList;
import java.util.Collection;
import model.Teilebestand;

/**
 *
 * @author simon
 */
public class WarenbewegungCollection<Warenbewegung> extends ArrayList {

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
