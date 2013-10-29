/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.CardLayout;
import java.awt.Container;

/**
 *
 * @author simon
 */
public class NavigationController {
    Container mainPanel;
    
    /**
     * Initialiert den Vater Panel der einzelnen Cards
     * 
     * @param mainPanel Panel welcher das Card Layout erhalten hat
     */
    public NavigationController(Container mainPanel)
    {
        this.mainPanel = mainPanel;
    }
    
    /**
     * Verwalter das Umschalten von einzelnen Panels und ruft die Methode
     * zum Aktualisieren der Daten auf, falls das Interface Refreshable
     * implementiert ist
     *
     * @param name Name der Card
     */
    public void showCard(String name)
    {
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, name);    // TODO add your handling code here:
    }
}
