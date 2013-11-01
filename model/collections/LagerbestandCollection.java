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
public class LagerbestandCollection<Lagerbestand> extends ArrayList {

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
