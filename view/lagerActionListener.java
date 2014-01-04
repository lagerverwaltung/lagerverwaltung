/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;

/**
 *
 * @author simon
 */
class lagerActionListener implements ItemListener {
    JComboBox cbX;
    JComboBox cbY;
    JComboBox cbZ;
    BestandsaenderungFrame bestandFrame;
    String code;
    
    public lagerActionListener(String id, JComboBox x, JComboBox y, JComboBox z, BestandsaenderungFrame bF) {
        code = id;
        cbX = x;
        cbY = y;
        cbZ = z;
        bestandFrame = bF;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String lagerCode = (String) e.getItem();
        bestandFrame.refreshXYZ(code, lagerCode);
    }
    
}
