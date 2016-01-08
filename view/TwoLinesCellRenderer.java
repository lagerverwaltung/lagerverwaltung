/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author simon
 */
    public class TwoLinesCellRenderer extends JTextArea implements TableCellRenderer
    {
        @Override public Component getTableCellRendererComponent(
        JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column )
        {
            if(column == 5){
                this.setWrapStyleWord(true);
                this.setLineWrap(true);
                int length = value.toString().length();
                int innerRow = (int)Math.ceil(length/28);
                if(innerRow>1){
                    table.setRowHeight(row, 20*innerRow);
                }
          }
          setText( value.toString() );
          return this;
        }
        
    }
