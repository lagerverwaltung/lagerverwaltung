/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import helper.Misc;
import helper.TeilebestandHelper;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.JTable;
import model.Lagerbestand;
import model.Teilebestand;
import model.Teilebestand.Typ;

/**
 *
 * @author simon
 */
public class TeilFrame extends javax.swing.JFrame {
    Boolean editMode = false;
    JTable teileBestandTable;
    int teilID;
    /**
     * Creates new form TeilFrame
     */
    public TeilFrame() {
        initComponents();
        setLocationRelativeTo(null);
    }
    
    public TeilFrame(Boolean editMode) {
        this();
        this.editMode = editMode;
        lblNeuesTeilAnlegen.setText("Teil bearbeiten");    
        btnanlegen.setText("Änderungen speichern");  
    }
    public Typ[] getTyps()
    {
        return Teilebestand.Typ.values();
    }
    
    public void setTable(JTable t)
    {
        teileBestandTable = t;
    }
    
    public void initTeil(int id) throws SQLException
    {
        Teilebestand t = Teilebestand.loadTeil(id);
        if(t != null){
            teilID = id;
            txfBezeichnung.setText(t.getBezeichnung());
            txfBezeichnung.setText(t.getBezeichnung());
            if(t.getTyp() != null){
                cbxTyp.setSelectedItem(t.getTyp());
            }
            txfMaterial.setText(t.getMaterialgruppe());
            txfZeichnungsnummer.setText(t.getZeichnungsnummer());
            if(t.getPreis() > 0){
                txfPreisInEuro.setText(String.valueOf(t.getPreis()));
            }
            if(t.getVe() > 0){
                txfGroesseVE.setText(String.valueOf(t.getVe()));
            }
            
            if (Teilebestand.countLagerbestand(t) > 0){
                pnlVol.setVisible(false);
            }
        }
    }
   /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNeuesTeilAnlegen = new javax.swing.JLabel();
        lblBezeichnung = new javax.swing.JLabel();
        txfMaterial = new javax.swing.JTextField();
        lblTyp = new javax.swing.JLabel();
        cbxTyp = new javax.swing.JComboBox(getTyps());
        lblMaterialGruppe = new javax.swing.JLabel();
        txfBezeichnung = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        txfZeichnungsnummer = new javax.swing.JTextField();
        lblZeichnungsnummer = new javax.swing.JLabel();
        lblPreisInEuro = new javax.swing.JLabel();
        txfPreisInEuro = new javax.swing.JTextField();
        btnanlegen = new javax.swing.JButton();
        pnlVol = new javax.swing.JPanel();
        lblGrößeVE = new javax.swing.JLabel();
        txfGroesseVE = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblNeuesTeilAnlegen.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblNeuesTeilAnlegen.setText("Neues Teil anlegen:");

        lblBezeichnung.setText("Bezeichnung:");

        txfMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfMaterialActionPerformed(evt);
            }
        });

        lblTyp.setText("Typ:");

        cbxTyp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTypActionPerformed(evt);
            }
        });

        lblMaterialGruppe.setText("Materialgruppe:");

        txfBezeichnung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfBezeichnungActionPerformed(evt);
            }
        });

        txfZeichnungsnummer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfZeichnungsnummerActionPerformed(evt);
            }
        });

        lblZeichnungsnummer.setText("Zeichnungsnummer:");

        lblPreisInEuro.setText("Preis in Euro:");

        txfPreisInEuro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfPreisInEuroActionPerformed(evt);
            }
        });

        btnanlegen.setText("Anlegen");
        btnanlegen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnanlegenActionPerformed(evt);
            }
        });

        lblGrößeVE.setText("Größe in Volumeneinheiten:");

        txfGroesseVE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfGroesseVEActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlVolLayout = new javax.swing.GroupLayout(pnlVol);
        pnlVol.setLayout(pnlVolLayout);
        pnlVolLayout.setHorizontalGroup(
            pnlVolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVolLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblGrößeVE)
                .addGap(20, 20, 20)
                .addComponent(txfGroesseVE, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlVolLayout.setVerticalGroup(
            pnlVolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVolLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlVolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGrößeVE)
                    .addComponent(txfGroesseVE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblZeichnungsnummer)
                                    .addComponent(lblMaterialGruppe)
                                    .addComponent(lblTyp)
                                    .addComponent(lblBezeichnung)
                                    .addComponent(lblPreisInEuro))
                                .addGap(65, 65, 65)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txfBezeichnung, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxTyp, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txfMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txfZeichnungsnummer, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txfPreisInEuro, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblNeuesTeilAnlegen)
                            .addComponent(pnlVol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnanlegen, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblNeuesTeilAnlegen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfBezeichnung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBezeichnung))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxTyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTyp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaterialGruppe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfZeichnungsnummer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblZeichnungsnummer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfPreisInEuro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPreisInEuro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlVol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnanlegen)
                .addGap(37, 37, 37))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txfMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfMaterialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfMaterialActionPerformed

    private void cbxTypActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTypActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxTypActionPerformed

    private void txfBezeichnungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfBezeichnungActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfBezeichnungActionPerformed

    private void txfZeichnungsnummerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfZeichnungsnummerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfZeichnungsnummerActionPerformed

    private void txfPreisInEuroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfPreisInEuroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfPreisInEuroActionPerformed

    /**
     * Behandelte den Klick auf speichern und legt ein Teil an oder ändert die 
     * Teildaten, nachdem alles validiert wurde
     * 
     */
    private void btnanlegenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnanlegenActionPerformed
        Teilebestand t = new Teilebestand();
        if (teilID > 0) {
            t.setIdentnummer(teilID);
        }
        
        HashMap<Integer, String> errors = TeilebestandHelper.getInstance().validateTeilData(
               txfBezeichnung.getText(),
               txfMaterial.getText(),
               txfZeichnungsnummer.getText(),
               txfGroesseVE.getText(),
               txfPreisInEuro.getText()
        );
        if (Misc.createErrorDialog(this, errors) == true) {
         return;
        }
        
        float euro = 0;
        String euroStr = txfPreisInEuro.getText();
        if (euroStr.contains(",")) {
            euroStr = euroStr.replace(',', '.');
            euro = Float.parseFloat(euroStr);
        } else {
            euro = Float.parseFloat(txfPreisInEuro.getText());
        }
        String groesse = txfGroesseVE.getText();
        t.setVe(Integer.parseInt(groesse));
        String bezeichnung = txfBezeichnung.getText();

        Typ typ = (Teilebestand.Typ) cbxTyp.getSelectedItem();
        String mat = txfMaterial.getText();
        String zeichnnr = txfZeichnungsnummer.getText();

        t.setBezeichnung(bezeichnung);
        t.setTyp(typ);
        t.setMaterialgruppe(mat);
        t.setZeichnungsnummer(zeichnnr);
        t.setPreis(euro);

        try {
            t.save();
        } catch (SQLException ex) {
            Misc.printSQLException(this, ex);
        }
        TeilebestandHelper.refreshTeileTableModel(teileBestandTable);
        this.dispose();
    }//GEN-LAST:event_btnanlegenActionPerformed

    private void txfGroesseVEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfGroesseVEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfGroesseVEActionPerformed
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TeilFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TeilFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TeilFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TeilFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TeilFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnanlegen;
    private javax.swing.JComboBox cbxTyp;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblBezeichnung;
    private javax.swing.JLabel lblGrößeVE;
    private javax.swing.JLabel lblMaterialGruppe;
    private javax.swing.JLabel lblNeuesTeilAnlegen;
    private javax.swing.JLabel lblPreisInEuro;
    private javax.swing.JLabel lblTyp;
    private javax.swing.JLabel lblZeichnungsnummer;
    private javax.swing.JPanel pnlVol;
    private javax.swing.JTextField txfBezeichnung;
    private javax.swing.JTextField txfGroesseVE;
    private javax.swing.JTextField txfMaterial;
    private javax.swing.JTextField txfPreisInEuro;
    private javax.swing.JTextField txfZeichnungsnummer;
    // End of variables declaration//GEN-END:variables
}
