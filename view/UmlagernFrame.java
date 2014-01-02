/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import helper.LagerbestandHelper;
import helper.Misc;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import model.Lager;
import model.Lagerbestand;

/**
 *
 * @author simon,artjom
 */
public class UmlagernFrame extends javax.swing.JFrame {

    Boolean split = false;
    JTable lagerBestandtable;
    int lagerbestandId;
    int fachid;
    int teilId;

    /**
     * Creates new form UmlagernFrame
     */
    public UmlagernFrame() {
        initComponents();
        setLocationRelativeTo(null);
        cbxQuelleLagertyp.addItem("HL");
        cbxQuelleLagertyp.addItem("FL");
        try {
            loadHlCbx();
        } catch (SQLException ex) {
            Logger.getLogger(UmlagernFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            loadHlCbxZiel();
        } catch (SQLException ex) {
            Logger.getLogger(UmlagernFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pnlWeitereZF.setVisible(true);
        
        
        
    }
    /**
     * @author artjom, ssinger
     * @param split false
     * @param lb 
     *  Erzeugt das UmlagernFrame und setzt die
     *  jeweiligen Formularfelder wie im übergebenen Lagerbestand.
     */
    public UmlagernFrame(boolean split, Lagerbestand lb){
        this();
        this.split = split;
        teilId = lb.getTeil().getIdentnummer();
        String btnText = "Teile Umlagern";
        lblUmlagern.setText(btnText);
        btnUmlagern.setText(btnText);
        txfTeilID.setText(String.valueOf(lb.getTeil().getIdentnummer()));
        this.txfTeilID.setEditable(false);
        this.txfTeilID.setEnabled(false);
       
        cbxQuelleLagertyp.setSelectedItem(lb.getLagerfach().getLager().getLagerortCode());
        this.cbxQuelleX.setSelectedItem(lb.getLagerfach().getX());
        this.cbxQuelleY.setSelectedItem(lb.getLagerfach().getY());
        this.cbxQuelleZ.setSelectedItem(lb.getLagerfach().getZ());
       
        this.cbxQuelleLagertyp.setEnabled(false);
        this.cbxQuelleX.setEnabled(false);
        this.cbxQuelleY.setEnabled(false);
        this.cbxQuelleZ.setEnabled(false);
    }
    
    /*
     * debrecated
     */
    public UmlagernFrame(boolean split, int id, int x, int y, int z,Lager.Lagerort lo) {
        
        this();
        this.split = split;
      //  this.fachid=fachid;
        this.teilId=id;
        //  panSplitl.setVisible(split);
        //  panUmlagern.setVisible(!split);
        String text = "Teile Umlagern";
        lblUmlagern.setText(text);
        btnUmlagern.setText(text);
        this.txfTeilID.setText("" + id);
        this.txfTeilID.setEditable(false);
        this.txfTeilID.setEnabled(false);
        
        this.cbxQuelleX.setSelectedItem(x);
        this.cbxQuelleY.setSelectedItem(y);
        this.cbxQuelleZ.setSelectedItem(z);
        loadCBXQuelleLagertyp(lo);

        this.cbxQuelleLagertyp.setEnabled(false);
        this.cbxQuelleX.setEnabled(false);
        this.cbxQuelleY.setEnabled(false);
        this.cbxQuelleZ.setEnabled(false);
    
    }
   
   public void setTable(JTable t)
    {
        lagerBestandtable = t;
    }
   
   public void initUmlagernObj(int id) throws SQLException
    {
        Lagerbestand l = Lagerbestand.loadLagerObjekt(id); 
        if(l != null){
            lagerbestandId = id;
            txfUmzulagerndeMenge.setText(Integer.toString(l.getMenge()));
        }
    }
     //ComboBoxen laden für Hochlager Quelladresse
      private void loadHlCbx() throws SQLException{
        cbxQuelleZ.removeAllItems();
        cbxQuelleX.removeAllItems();
        cbxQuelleY.removeAllItems();

        Lager hl = Lager.getLager(Lager.Lagerort.hochregal);
        int x = hl.getHoehe();
        int y = hl.getBreite();
        int z = hl.getTiefe();
        
        for(int i = 1; i <= x; i++){
            cbxQuelleZ.addItem(i);
        }
        
        for(int i = 1; i <= y; i++){
            cbxQuelleX.addItem(i);
        }
                
         for(int i = 1; i <= z; i++){
            cbxQuelleY.addItem(i);
        }
    }

    /*
     * läd ComboBoxen für Freilagern Quelladresse
     */
    public void loadFlCbx() throws SQLException {
        cbxQuelleZ.removeAllItems();
        cbxQuelleX.removeAllItems();
        cbxQuelleY.removeAllItems();

        Lager fl = Lager.getLager(Lager.Lagerort.freilager);
        int x = fl.getHoehe();
        int y = fl.getBreite();
        int z = fl.getTiefe();

        for (int i = 1; i <= x; i++) {
            cbxQuelleZ.addItem(i);
        }

        for (int i = 1; i <= y; i++) {
            cbxQuelleX.addItem(i);
        }

        for (int i = 1; i <= z; i++) {
            cbxQuelleY.addItem(i);
        }
    }

    //ComboBoxen laden für Hochlager Zieladresse
      private void loadHlCbxZiel() throws SQLException{
          cbxZiel0Z.removeAllItems();
          cbxZiel0X.removeAllItems();
          cbxZiel0Y.removeAllItems();

        Lager hl = Lager.getLager(Lager.Lagerort.hochregal);
        int x = hl.getHoehe();
        int y = hl.getBreite();
        int z = hl.getTiefe();
        
        for(int i = 1; i <= x; i++){
            cbxZiel0Z.addItem(i);
        }
        
        for(int i = 1; i <= y; i++){
            cbxZiel0Y.addItem(i);
        }
                
         for(int i = 1; i <= z; i++){
            cbxZiel0X.addItem(i);
        }
    }
    //ComboBoxen laden für Freiregallager Zieladresse  
 private void loadFlCbxZiel() throws SQLException {
        cbxZiel0Z.removeAllItems();
        cbxZiel0X.removeAllItems();
        cbxZiel0Y.removeAllItems();

        Lager fl = Lager.getLager(Lager.Lagerort.freilager);
        int x = fl.getHoehe();
        int y = fl.getBreite();
        int z = fl.getTiefe();

        for (int i = 1; i <= x; i++) {
            cbxZiel0Z.addItem(i);
        }

        for (int i = 1; i <= y; i++) {
            cbxZiel0X.addItem(i);
        }

        for (int i = 1; i <= z; i++) {
            cbxZiel0Y.addItem(i);
        }
    }
 
  private void loadCBXQuelleLagertyp(Lager.Lagerort s){
       if (s.equals(Lager.Lagerort.freilager)){
          cbxQuelleLagertyp.setSelectedItem("FL");
       } 
       else{
           cbxQuelleLagertyp.setSelectedItem("HL");
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

        lblUmlagern = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblUmzulagerndeMenge = new javax.swing.JLabel();
        txfUmzulagerndeMenge = new javax.swing.JTextField();
        lblQuellfach = new javax.swing.JLabel();
        btnUmlagern = new javax.swing.JButton();
        lblTeilID = new javax.swing.JLabel();
        panUmlagern = new javax.swing.JPanel();
        lblZielLagerfach = new javax.swing.JLabel();
        cbxZiel0Lagertyp = new javax.swing.JComboBox();
        cbxZiel0Z = new javax.swing.JComboBox();
        cbxZiel0Y = new javax.swing.JComboBox();
        cbxZiel0X = new javax.swing.JComboBox();
        panSplitl = new javax.swing.JPanel();
        panQuellFach = new javax.swing.JPanel();
        cbxQuelleY = new javax.swing.JComboBox();
        cbxQuelleLagertyp = new javax.swing.JComboBox();
        lblZ = new javax.swing.JLabel();
        cbxQuelleZ = new javax.swing.JComboBox();
        cbxQuelleX = new javax.swing.JComboBox();
        lblX = new javax.swing.JLabel();
        lblY = new javax.swing.JLabel();
        lblLager = new javax.swing.JLabel();
        txfTeilID = new javax.swing.JTextField();
        pnlWeitereZF = new javax.swing.JPanel();
        lblWeitereZielFaecher = new javax.swing.JLabel();
        txfWeitereZF = new javax.swing.JTextField();
        btnAnzahlOK = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblWeitereZF = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblUmlagern.setText("Teile umlagern");

        lblUmzulagerndeMenge.setText("Umzulagernde Menge:");

        lblQuellfach.setText("Quell-Lagerfachadresse:");

        btnUmlagern.setText("Teile umlagern");
        btnUmlagern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUmlagernActionPerformed(evt);
            }
        });

        lblTeilID.setText("Teil ID:");

        lblZielLagerfach.setText("Ziel-Lagerfachadresse nach:");

        cbxZiel0Lagertyp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "FL", "HL" }));
        cbxZiel0Lagertyp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxZiel0LagertypActionPerformed(evt);
            }
        });

        cbxZiel0Z.setModel(new javax.swing.DefaultComboBoxModel());

        cbxZiel0Y.setModel(new javax.swing.DefaultComboBoxModel());

        cbxZiel0X.setModel(new javax.swing.DefaultComboBoxModel());
        cbxZiel0X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxZiel0XActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panSplitlLayout = new javax.swing.GroupLayout(panSplitl);
        panSplitl.setLayout(panSplitlLayout);
        panSplitlLayout.setHorizontalGroup(
            panSplitlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 375, Short.MAX_VALUE)
        );
        panSplitlLayout.setVerticalGroup(
            panSplitlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panUmlagernLayout = new javax.swing.GroupLayout(panUmlagern);
        panUmlagern.setLayout(panUmlagernLayout);
        panUmlagernLayout.setHorizontalGroup(
            panUmlagernLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panUmlagernLayout.createSequentialGroup()
                .addGroup(panUmlagernLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panSplitl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panUmlagernLayout.createSequentialGroup()
                        .addComponent(lblZielLagerfach)
                        .addGap(30, 30, 30)
                        .addComponent(cbxZiel0Lagertyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxZiel0X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxZiel0Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxZiel0Z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panUmlagernLayout.setVerticalGroup(
            panUmlagernLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panUmlagernLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panUmlagernLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panUmlagernLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbxZiel0Z)
                        .addComponent(cbxZiel0Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxZiel0X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxZiel0Lagertyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblZielLagerfach))
                .addGap(55, 55, 55)
                .addComponent(panSplitl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cbxQuelleY.setModel(new javax.swing.DefaultComboBoxModel());

        cbxQuelleLagertyp.setModel(new javax.swing.DefaultComboBoxModel());
        cbxQuelleLagertyp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxQuelleLagertypActionPerformed(evt);
            }
        });

        lblZ.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblZ.setText("z");

        cbxQuelleZ.setModel(new javax.swing.DefaultComboBoxModel());

        cbxQuelleX.setModel(new javax.swing.DefaultComboBoxModel());
        cbxQuelleX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxQuelleXActionPerformed(evt);
            }
        });

        lblX.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblX.setText("x");

        lblY.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblY.setText("y");

        lblLager.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblLager.setText("Lager");

        javax.swing.GroupLayout panQuellFachLayout = new javax.swing.GroupLayout(panQuellFach);
        panQuellFach.setLayout(panQuellFachLayout);
        panQuellFachLayout.setHorizontalGroup(
            panQuellFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panQuellFachLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panQuellFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLager, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxQuelleLagertyp, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panQuellFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblX, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxQuelleX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panQuellFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panQuellFachLayout.createSequentialGroup()
                        .addComponent(lblY, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblZ, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panQuellFachLayout.createSequentialGroup()
                        .addComponent(cbxQuelleY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxQuelleZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panQuellFachLayout.setVerticalGroup(
            panQuellFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panQuellFachLayout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addGroup(panQuellFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLager, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panQuellFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblZ)
                        .addComponent(lblY)
                        .addComponent(lblX)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panQuellFachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxQuelleLagertyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxQuelleZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxQuelleX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxQuelleY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        lblWeitereZielFaecher.setText("Anzahl:");

        btnAnzahlOK.setText("OK");
        btnAnzahlOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnzahlOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlWeitereZFLayout = new javax.swing.GroupLayout(pnlWeitereZF);
        pnlWeitereZF.setLayout(pnlWeitereZFLayout);
        pnlWeitereZFLayout.setHorizontalGroup(
            pnlWeitereZFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWeitereZFLayout.createSequentialGroup()
                .addComponent(lblWeitereZielFaecher)
                .addGap(33, 33, 33)
                .addComponent(txfWeitereZF, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(btnAnzahlOK)
                .addContainerGap())
        );
        pnlWeitereZFLayout.setVerticalGroup(
            pnlWeitereZFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWeitereZFLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlWeitereZFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWeitereZielFaecher)
                    .addComponent(txfWeitereZF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnzahlOK))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("weitere Ziel-Lagerfachadressen");

        lblWeitereZF.setText("einfügen");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnUmlagern, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUmzulagerndeMenge)
                                    .addComponent(lblQuellfach)
                                    .addComponent(lblTeilID))
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(panQuellFach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txfUmzulagerndeMenge, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txfTeilID))
                                .addGap(0, 153, Short.MAX_VALUE)))
                        .addGap(21, 21, 21))
                    .addComponent(panUmlagern, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblWeitereZF)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUmlagern)
                            .addComponent(pnlWeitereZF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblUmlagern)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panQuellFach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(lblQuellfach)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTeilID)
                            .addComponent(txfTeilID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblUmzulagerndeMenge)
                            .addComponent(txfUmzulagerndeMenge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27)
                .addComponent(panUmlagern, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblWeitereZF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlWeitereZF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                .addComponent(btnUmlagern)
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxQuelleXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxQuelleXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxQuelleXActionPerformed

    private void btnUmlagernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUmlagernActionPerformed
        
        HashMap<Integer, String> errors = LagerbestandHelper.getInstance().validateUmlagern(
                txfUmzulagerndeMenge.getText());
        
        if (Misc.createErrorDialog(this, errors) == true) {
            return;
        }
        
        //saveUmlagern();
        
        this.dispose();
        
        
    }//GEN-LAST:event_btnUmlagernActionPerformed

    private void cbxZiel0XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxZiel0XActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxZiel0XActionPerformed

    private void cbxQuelleLagertypActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxQuelleLagertypActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxQuelleLagertypActionPerformed

    private void cbxZiel0LagertypActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxZiel0LagertypActionPerformed
        if (cbxZiel0Lagertyp.getSelectedItem().equals("HL")) {
            try {
                loadHlCbxZiel();
            } catch (SQLException ex) {
                Misc.printSQLException(this, ex);
            }
        } else {
            try {
                loadFlCbxZiel();
            } catch (SQLException ex) {
                Misc.printSQLException(this, ex);
            }
        }
    }//GEN-LAST:event_cbxZiel0LagertypActionPerformed

    private void btnAnzahlOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnzahlOKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAnzahlOKActionPerformed

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
            java.util.logging.Logger.getLogger(UmlagernFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UmlagernFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UmlagernFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UmlagernFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UmlagernFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnzahlOK;
    private javax.swing.JButton btnUmlagern;
    private javax.swing.JComboBox cbxQuelleLagertyp;
    private javax.swing.JComboBox cbxQuelleX;
    private javax.swing.JComboBox cbxQuelleY;
    private javax.swing.JComboBox cbxQuelleZ;
    private javax.swing.JComboBox cbxZiel0Lagertyp;
    private javax.swing.JComboBox cbxZiel0X;
    private javax.swing.JComboBox cbxZiel0Y;
    private javax.swing.JComboBox cbxZiel0Z;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblLager;
    private javax.swing.JLabel lblQuellfach;
    private javax.swing.JLabel lblTeilID;
    private javax.swing.JLabel lblUmlagern;
    private javax.swing.JLabel lblUmzulagerndeMenge;
    private javax.swing.JToggleButton lblWeitereZF;
    private javax.swing.JLabel lblWeitereZielFaecher;
    private javax.swing.JLabel lblX;
    private javax.swing.JLabel lblY;
    private javax.swing.JLabel lblZ;
    private javax.swing.JLabel lblZielLagerfach;
    private javax.swing.JPanel panQuellFach;
    private javax.swing.JPanel panSplitl;
    private javax.swing.JPanel panUmlagern;
    private javax.swing.JPanel pnlWeitereZF;
    private javax.swing.JTextField txfTeilID;
    private javax.swing.JTextField txfUmzulagerndeMenge;
    private javax.swing.JTextField txfWeitereZF;
    // End of variables declaration//GEN-END:variables
}
