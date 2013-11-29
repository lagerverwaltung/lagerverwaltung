/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import helper.DatabaseManager;
import helper.Misc;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JTable;
import model.Lager;
import model.Lagerbestand;
import model.Lagerfach;
import model.Teilebestand;
import model.Warenbewegung;
import model.ZielPosition;
import model.collection.LagerbestandCollection;
import model.table.LagerbestandTableModel;

/**
 *
 * @author simon
 */
//ändern
public class BestandsaenderungFrame extends javax.swing.JFrame {
    Boolean einlagern = false;
    Boolean auslagern = false;
    Boolean splitten = false;
    JTable lagerBestandTable;
    int lagerID;
    
    /**
     * Creates new form BestandsaenderungFrame
     */
    public BestandsaenderungFrame(){
        initComponents();
        setLocationRelativeTo(null);
        try {
            loadHlCbx();
        } catch (SQLException ex) {
            Logger.getLogger(BestandsaenderungFrame.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }
    
    //Artjom
  /* BestandsaenderungFrame(boolean einlagern, int id,String anschGr) {
        this();
        this.einlagern = einlagern;
        lblEinlagern.setText("Teile einlagern");
        einlagernButton.setText("Teile einlagern");
        this.txfTeilID.setText(""+id);
        this.txaAnschaffungsgrund.setText(anschGr);
        //this.txfHaltbarkeitsdatum.setText(hbDate+"kommt noch");
        //this.txfHaltbarkeitsdatum.setText(haltbDate);
        this.txfTeilID.setEditable(false);
        this.txaAnschaffungsgrund.setEditable(false);
        
            //ComboBox füllen
            try {
                loadHlCbx();
            } catch (SQLException ex) {
                Logger.getLogger(BestandsaenderungFrame.class.getName()).log(Level.SEVERE, null, ex); 
            }
   }*/
        
   
    //Teil einlagern aus der Registerkarte Teilebestand/Lagerbestand
    BestandsaenderungFrame(boolean einlagern, int id) {
        this();
        this.einlagern = einlagern;
        lblEinlagern.setText("Teile einlagern");
        einlagernButton.setText("Teile einlagern");
        this.txfTeilID.setText(""+id);
      //  this.txaAnschaffungsgrund.setText(anschGr);
        this.txfTeilID.setEditable(false);
        this.txfTeilID.setEnabled(false);
        
            //ComboBox füllen
            try {
                loadHlCbx();
            } catch (SQLException ex) {
                Logger.getLogger(BestandsaenderungFrame.class.getName()).log(Level.SEVERE, null, ex); 
            }
    }
    
        //Teil auslagern aus der Registerkarte Lagerbestand
        BestandsaenderungFrame(boolean auslagern, int id,String anschGr) {
            this();
            this.auslagern = auslagern;
            lblEinlagern.setText("Teile auslagern");
            einlagernButton.setText("Teile auslagern");
            this.txfTeilID.setText("" + id);
            //this.txaAnschaffungsgrund.setText(anschGr);
            //this.txfHaltbarkeitsdatum.setText(hbDate+"kommt noch");
            //this.txfHaltbarkeitsdatum.setText(haltbDate);
            this.txfTeilID.setEditable(false);
            this.txfTeilID.setEnabled(false);
            this.txaAnschaffungsgrund.setEditable(true);
            this.txfHaltbarkeitsdatum.setVisible(false);
            this.lblHaltbarkeitsdatum.setVisible(false);
            this.cbxFachTyp.setEnabled(false);
            this.cbxFachX.setEnabled(false);
            this.cbxFachY.setEnabled(false);
            this.cbxFachZ.setEnabled(false);

            //ComboBox füllen
            Lagerfach lf = new Lagerfach();
            try {
                lf = Lagerfach.getLagerfach(id);
            } catch (SQLException ex) {
                Logger.getLogger(BestandsaenderungFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (lf.getLager().getLagerort().freilager.equals(Lager.Lagerort.hochregal)) {
                cbxFachTyp.removeItem("HL");
            } else {
                cbxFachTyp.removeItem("FL");
            }
            cbxFachZ.removeAllItems();
            cbxFachX.removeAllItems();
            cbxFachY.removeAllItems();

            cbxFachX.addItem(lf.getX());
            cbxFachY.addItem(lf.getY());
            cbxFachZ.addItem(lf.getZ());

        }
        
        BestandsaenderungFrame(boolean splitten) {
        this();
        this.splitten = splitten;
        lblEinlagern.setText("Teile splitten");
        einlagernButton.setText("Teile splitten");
       // this.txfTeilID.setText(""+id);
        //this.txaAnschaffungsgrund.setText(anschGr);
    //    this.txfHaltbarkeitsdatum.setText(hbDate+"kommt noch");
      //  this.txfHaltbarkeitsdatum.setText(haltbDate);
             this.txfTeilID.setEditable(false);
           //  this.txaAnschaffungsgrund.setEditable(false);
             this.txfHaltbarkeitsdatum.setVisible(false);
             this.lblHaltbarkeitsdatum.setVisible(false);
        }
        
    //Table setzen
    public void setTable(JTable t)
    {
        lagerBestandTable = t;
    }
     public void initLagerObjekt(int id)
    {
        Lagerbestand l = Lagerbestand.loadLagerObjekt(id);
        Warenbewegung w = Warenbewegung.loadWarenbewegung(id);
        if(l != null){
            lagerID = id;
            txaAnschaffungsgrund.setText(l.getAnschaffungsgrund());
            txfMenge.setText(Integer.toString(l.getMenge()));
            //if(t.getTyp() != null){
              //  cbxTyp.setSelectedItem(t.getTyp());
            //}
           //
            Format f = new SimpleDateFormat("DD.MM.YYYY");
            txfHaltbarkeitsdatum.setText(f.format(w.getHaltbarkeitsDatum()));
        }
    }
     
    /*
    * Läd Comboboxen für Hochlager
    */
    private void loadHlCbx() throws SQLException{
        cbxFachZ.removeAllItems();
        cbxFachX.removeAllItems();
        cbxFachY.removeAllItems();

        Lager hl = Lager.getLager(Lager.Lagerort.hochregal);
        int x = hl.getHoehe();
        int y = hl.getBreite();
        int z = hl.getTiefe();
        
        for(int i = 1; i <= x; i++){
            cbxFachZ.addItem(i);
        }
        
        for(int i = 1; i <= y; i++){
            cbxFachX.addItem(i);
        }
                
         for(int i = 1; i <= z; i++){
            cbxFachY.addItem(i);
        }
         
         
    }

    /*
    * läd ComboBoxen für Freilagern
    */
    public void loadFlCbx() throws SQLException{
        cbxFachZ.removeAllItems();
        cbxFachX.removeAllItems();
        cbxFachY.removeAllItems();
        
        Lager fl = Lager.getLager(Lager.Lagerort.freilager);
        int x = fl.getHoehe();
        int y = fl.getBreite();
        int z = fl.getTiefe();
        
        for(int i = 1; i <= x; i++){
            cbxFachX.addItem(i);
        }
        
        for(int i = 1; i <= y; i++){
            cbxFachY.addItem(i);
        }
                
         for(int i = 1; i <= z; i++){
            cbxFachZ.addItem(i);
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

        lblEinlagern = new javax.swing.JLabel();
        lblFachAdresse = new javax.swing.JLabel();
        lblHinweis = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblTeilID = new javax.swing.JLabel();
        lblAnschaffungsgrund = new javax.swing.JLabel();
        spnAnschaffungsgrund = new javax.swing.JScrollPane();
        txaAnschaffungsgrund = new javax.swing.JTextArea();
        lblMenge = new javax.swing.JLabel();
        txfMenge = new javax.swing.JTextField();
        einlagernButton = new javax.swing.JButton();
        cbxFachTyp = new javax.swing.JComboBox();
        cbxFachZ = new javax.swing.JComboBox();
        cbxFachX = new javax.swing.JComboBox();
        cbxFachY = new javax.swing.JComboBox();
        txfTeilID = new javax.swing.JTextField();
        lblHaltbarkeitsdatum = new javax.swing.JLabel();
        txfHaltbarkeitsdatum = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblEinlagern.setText("Teile auslagern");

        lblFachAdresse.setText("Lagerfachadresse:");

        lblHinweis.setText("Hinweis: [LagerID][x][y][z]");

        lblTeilID.setText("Teil ID:");

        lblAnschaffungsgrund.setText("Grund:");

        txaAnschaffungsgrund.setColumns(20);
        txaAnschaffungsgrund.setRows(5);
        spnAnschaffungsgrund.setViewportView(txaAnschaffungsgrund);

        lblMenge.setText("Menge:");

        einlagernButton.setText("Teile auslagern");
        einlagernButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                einlagernButtonActionPerformed(evt);
            }
        });

        cbxFachTyp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "HL", "FL"}));
        cbxFachTyp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFachTypActionPerformed(evt);
            }
        });

        cbxFachZ.setModel(new javax.swing.DefaultComboBoxModel());

        cbxFachX.setModel(new javax.swing.DefaultComboBoxModel());
        cbxFachX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFachXActionPerformed(evt);
            }
        });

        cbxFachY.setModel(new javax.swing.DefaultComboBoxModel());

        txfTeilID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfTeilIDActionPerformed(evt);
            }
        });

        lblHaltbarkeitsdatum.setText("Haltbarkeitsdatum");

        txfHaltbarkeitsdatum.setText("dd.mm.YYYY");
        txfHaltbarkeitsdatum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfHaltbarkeitsdatumActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAnschaffungsgrund)
                            .addComponent(lblMenge))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfMenge, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spnAnschaffungsgrund, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFachAdresse)
                            .addComponent(lblTeilID))
                        .addGap(65, 65, 65)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbxFachTyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxFachX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxFachY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxFachZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(lblHinweis))
                            .addComponent(txfTeilID, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEinlagern, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(einlagernButton)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblHaltbarkeitsdatum)
                        .addGap(62, 62, 62)
                        .addComponent(txfHaltbarkeitsdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblEinlagern)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFachAdresse)
                    .addComponent(lblHinweis)
                    .addComponent(cbxFachTyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxFachZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxFachX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxFachY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTeilID)
                    .addComponent(txfTeilID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAnschaffungsgrund)
                    .addComponent(spnAnschaffungsgrund, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMenge)
                    .addComponent(txfMenge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHaltbarkeitsdatum)
                    .addComponent(txfHaltbarkeitsdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(einlagernButton)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//ändern
    private void einlagernButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_einlagernButtonActionPerformed

        //Einlagern
        if (einlagern) {

            //Variablendeklaration
            Lagerbestand lb = new Lagerbestand();
            // lb.getLagerbestand();
            Warenbewegung wb = new Warenbewegung();
            Lagerfach lf = new Lagerfach();
            Teilebestand tb = new Teilebestand();

            int fachID = 0;
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            df.setLenient(false);
            Date hd = new Date();
            Date today = new Date();
            int mng = 0;
            String errors = "";

            //Übernahme der Variablen aus der GUI und Validierung
            String ag = txaAnschaffungsgrund.getText();
            if (ag.length() == 0) {
                errors += "Bitte Anschaffungsgrund eingeben. \n";
            }
            try {
                hd = df.parse(txfHaltbarkeitsdatum.getText());
            } catch (ParseException ex) {
                errors += "Das Haltbarkeitsdatum muss im Format tt.mm.jjjj eingegeben werden. \n";
            }
            if (hd.before(today)) {
                errors += "Achtung, Artikel ist schon abgelaufen. \n";
            }
            mng = Integer.parseInt(txfMenge.getText());
            if (mng == 0) {
                errors += "Bitte einzulagernde Menge eingeben. +\n";
            }
            int teiID = Integer.parseInt(txfTeilID.getText());
            String ort = (String) cbxFachTyp.getSelectedItem();
            int x = (int) (cbxFachX.getSelectedItem());
            int y = (int) (cbxFachY.getSelectedItem());
            int z = (int) (cbxFachZ.getSelectedItem());
            try {
                fachID = Lagerfach.getFach(ort, x, y, z).getFachnummer();
            } catch (SQLException ex) {
                Logger.getLogger(BestandsaenderungFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            int freeVe = 0;
            int usedVe = 0;
            int maxVe = 0;

            try {
                maxVe = Lagerfach.getLagerfach(fachID).getMaxVe();
            } catch (SQLException ex) {
                Logger.getLogger(BestandsaenderungFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                usedVe = Lagerfach.getLagerfach(fachID).getUsedVe();
            } catch (SQLException ex) {
                Logger.getLogger(BestandsaenderungFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            freeVe = maxVe - usedVe;

            if (mng > freeVe) {
                errors += "Es steht nicht genug Platz zum einlagern zur Verfügung. +\n";
                errors += usedVe + " von " + maxVe + " VE belegt. " + freeVe + " VE frei. +\n";
            }
            if (Misc.createErrorDialog(this, errors) == true) {
                return;
            }

            //Setzt das Ziellagerfach zusammen
            lf.setFachnummer(fachID);
            lf.setX(x);
            lf.setY(y);
            lf.setZ(z);

            //Setzt den Teilebestand zusammen
            tb.setIdentnummer(teiID);

            //Setzt den Lagerbestand zusammen
            lb.setTeil(tb);
            lb.setLagerfach(lf);
            lb.setAnschaffungsgrund(ag);
            lb.setMenge(mng);

            //Speichert die Warenbewegung
            wb.setVerantwortlicher("Lagerverwalter");
            wb.setLagerbestand(lb);
            wb.setDatum(today);
            wb.setHaltbarkeitsDatum(hd);

            //Speichern der Zielpositionen
            //Variablendeklaration
            DatabaseManager dbm = new DatabaseManager();

            //Ziellagerfach zusammensetzen
            ZielPosition zpZiel = new ZielPosition();
            zpZiel.setLagerfach(lf);
            zpZiel.setMenge(mng);
            zpZiel.setWarenbewegung(wb);
            try {
                dbm.getZielpositionDao().createOrUpdate(zpZiel);
            } catch (SQLException ex) {
                Logger.getLogger(BestandsaenderungFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Lagerbestand speichern
            try {
                lb.save();
                wb.save();
            } catch (SQLException ex) {
                Logger.getLogger(BestandsaenderungFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            refreshLagerbestandTableModel();
            this.dispose();

            //Auslagern
        } else {
        }


    }//GEN-LAST:event_einlagernButtonActionPerformed
    private void refreshLagerbestandTableModel(){
        LagerbestandCollection lc = LagerbestandCollection.getInstance(true);
        LagerbestandTableModel lm = new LagerbestandTableModel();
        lm.setData(lc);
        lagerBestandTable.setModel(lm);
    }
    
    private void cbxFachXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFachXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxFachXActionPerformed

    private void txfTeilIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfTeilIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfTeilIDActionPerformed

    private void cbxFachTypActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFachTypActionPerformed
        if(cbxFachTyp.getSelectedItem().equals("HL")){
            try {
                loadHlCbx();
            } catch (SQLException ex) {
                Logger.getLogger(BestandsaenderungFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
                loadFlCbx();
            } catch (SQLException ex) {
                Logger.getLogger(BestandsaenderungFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_cbxFachTypActionPerformed

    private void txfHaltbarkeitsdatumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfHaltbarkeitsdatumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfHaltbarkeitsdatumActionPerformed

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
            java.util.logging.Logger.getLogger(BestandsaenderungFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BestandsaenderungFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BestandsaenderungFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BestandsaenderungFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BestandsaenderungFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbxFachTyp;
    private javax.swing.JComboBox cbxFachX;
    private javax.swing.JComboBox cbxFachY;
    private javax.swing.JComboBox cbxFachZ;
    private javax.swing.JButton einlagernButton;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAnschaffungsgrund;
    private javax.swing.JLabel lblEinlagern;
    private javax.swing.JLabel lblFachAdresse;
    private javax.swing.JLabel lblHaltbarkeitsdatum;
    private javax.swing.JLabel lblHinweis;
    private javax.swing.JLabel lblMenge;
    private javax.swing.JLabel lblTeilID;
    private javax.swing.JScrollPane spnAnschaffungsgrund;
    private javax.swing.JTextArea txaAnschaffungsgrund;
    private javax.swing.JTextField txfHaltbarkeitsdatum;
    private javax.swing.JTextField txfMenge;
    private javax.swing.JTextField txfTeilID;
    // End of variables declaration//GEN-END:variables

}
