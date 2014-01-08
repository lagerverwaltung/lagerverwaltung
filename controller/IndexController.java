package controller;

import helper.Misc;
import java.util.logging.Level;
import view.MainFrame;


/**
 * Hauptklasse der Lagerverwaltung 
 * 
 * @author simon
 */
public class IndexController {
     /**
      * Main Methode als Programmeinstiegsspunkt
      * Erzeugt den Hauptframe und den NavigationController zur Steuerung des Layouts
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            MainFrame mainFrame = new MainFrame();
            NavigationController navigationController = new NavigationController(mainFrame.getStartPanel());
            mainFrame.addNaviController(navigationController);
            mainFrame.setVisible(true);
        } catch(Exception e1){
            e1.printStackTrace();
        }
   }
}
