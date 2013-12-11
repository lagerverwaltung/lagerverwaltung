/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.util.HashMap;

/**
 *
 * @author simon
 */
public class LagerbestandHelper {
   
    private static LagerbestandHelper singleton;
    public static LagerbestandHelper getInstance()
    {
        if (LagerbestandHelper.singleton == null){
            singleton = new LagerbestandHelper();
        }
        return singleton;
    }
}
