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
    
    public TeilebestandCollection<Teilebestand> loadCollection()
    {
        return this;
    }
    
    public void addFilter(String attributeName, String attributeValue){
        
    }
    
    public void resetFilters()
    {
        
    }
}
