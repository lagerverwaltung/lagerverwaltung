/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.sql.SQLException;
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
        panSplitl.setVisible(false);
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
        
        panSplitl.setVisible(false);
        
        
        
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
        panSplitl = new javax.swing.JPanel();
        btnWeiteresZielhinzufügen = new javax.swing.JButton();
        lblZiel1 = new javax.swing.JLabel();
        cbxZ1Lagertyp = new javax.swing.JComboBox();
        cbxZ1X = new javax.swing.JComboBox();
        cbxZ1Y = new javax.swing.JComboBox();
        cbxZ1Z = new javax.swing.JComboBox();
        cbxZ2Z = new javax.swing.JComboBox();
        cbxz2Y = new javax.swing.JComboBox();
        cbxZ2X = new javax.swing.JComboBox();
        cbxz2Lagertyp = new javax.swing.JComboBox();
        lblZiel2 = new javax.swing.JLabel();
        lblZiel1Zielmenge = new javax.swing.JLabel();
        txfZiel1Zielmenge = new javax.swing.JTextField();
        lblZiel2Zielmenge = new javax.swing.JLabel();
        txfZiel2Zielmenge = new javax.swing.JTextField();
        panUmlagern = new javax.swing.JPanel();
        lblZielLagerfach = new javax.swing.JLabel();
        cbxZiel0Lagertyp = new javax.swing.JComboBox();
        cbxZiel0Z = new javax.swing.JComboBox();
        cbxZiel0Y = new javax.swing.JComboBox();
        cbxZiel0X = new javax.swing.JComboBox();
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

        btnWeiteresZielhinzufügen.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        btnWeiteresZielhinzufügen.setText("Weiteres Ziel hinzufügen");

        lblZiel1.setText("Ziel 1:");

        cbxZ1Lagertyp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxZ1LagertypActionPerformed(evt);
            }
        });

        cbxZ1X.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        cbxZ1X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxZ1XActionPerformed(evt);
            }
        });

        cbxZ1Y.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        cbxZ1Z.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        cbxZ2Z.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        cbxz2Y.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        cbxZ2X.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        cbxZ2X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxZ2XActionPerformed(evt);
            }
        });

        cbxz2Lagertyp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "FL", "RL" }));

        lblZiel2.setText("Ziel 2:");

        lblZiel1Zielmenge.setText("Zielmenge:");

        lblZiel2Zielmenge.setText("Zielmenge:");

        javax.swing.GroupLayout panSplitlLayout = new javax.swing.GroupLayout(panSplitl);
        panSplitl.setLayout(panSplitlLayout);
        panSplitlLayout.setHorizontalGroup(
            panSplitlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panSplitlLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panSplitlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnWeiteresZielhinzufügen)
                    .addGroup(panSplitlLayout.createSequentialGroup()
                        .addGroup(panSplitlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblZiel2)
                            .addComponent(lblZiel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSplitlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panSplitlLayout.createSequentialGroup()
                                .addComponent(cbxZ1Lagertyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxZ1X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxZ1Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxZ1Z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panSplitlLayout.createSequentialGroup()
                                .addComponent(cbxz2Lagertyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxZ2X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxz2Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxZ2Z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29)
                        .addGroup(panSplitlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblZiel2Zielmenge, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblZiel1Zielmenge, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panSplitlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panSplitlLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txfZiel1Zielmenge, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panSplitlLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(txfZiel2Zielmenge, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(31, 31, Short.MAX_VALUE))
        );
        panSplitlLayout.setVerticalGroup(
            panSplitlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panSplitlLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(panSplitlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxZ1Lagertyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxZ1X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxZ1Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxZ1Z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblZiel1)
                    .addComponent(lblZiel1Zielmenge)
                    .addComponent(txfZiel1Zielmenge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panSplitlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxZ2X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxz2Lagertyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxz2Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxZ2Z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblZiel2)
                    .addComponent(lblZiel2Zielmenge)
                    .addComponent(txfZiel2Zielmenge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnWeiteresZielhinzufügen)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout panUmlagernLayout = new javax.swing.GroupLayout(panUmlagern);
        panUmlagern.setLayout(panUmlagernLayout);
        panUmlagernLayout.setHorizontalGroup(
            panUmlagernLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panUmlagernLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblZielLagerfach)
                .addGap(18, 18, 18)
                .addComponent(cbxZiel0Lagertyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbxZiel0X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxZiel0Y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxZiel0Z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(cbxZiel0X, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panUmlagernLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblZielLagerfach)
                        .addComponent(cbxZiel0Lagertyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUmlagern)
                            .addComponent(panSplitl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(84, 84, 84))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnUmlagern, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblUmzulagerndeMenge)
                                            .addComponent(lblQuellfach)
                                            .addComponent(lblTeilID))
                                        .addGap(47, 47, 47)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(panQuellFach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txfUmzulagerndeMenge, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txfTeilID)))
                                    .addComponent(panUmlagern, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(21, 21, 21))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblUmlagern)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addComponent(txfUmzulagerndeMenge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panUmlagern, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(panSplitl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panQuellFach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUmlagern)
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxQuelleXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxQuelleXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxQuelleXActionPerformed

    private void btnUmlagernActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUmlagernActionPerformed
        this.dispose();
        
        
    }//GEN-LAST:event_btnUmlagernActionPerformed

    private void cbxZiel0XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxZiel0XActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxZiel0XActionPerformed

    private void cbxZ1XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxZ1XActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxZ1XActionPerformed

    private void cbxZ2XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxZ2XActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxZ2XActionPerformed

    private void cbxZ1LagertypActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxZ1LagertypActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxZ1LagertypActionPerformed

    private void cbxQuelleLagertypActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxQuelleLagertypActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxQuelleLagertypActionPerformed

    private void cbxZiel0LagertypActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxZiel0LagertypActionPerformed
        if (cbxZiel0Lagertyp.getSelectedItem().equals("HL")) {
            try {
                loadHlCbxZiel();
            } catch (SQLException ex) {
                Logger.getLogger(BestandsaenderungFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                loadFlCbxZiel();
            } catch (SQLException ex) {
                Logger.getLogger(BestandsaenderungFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_cbxZiel0LagertypActionPerformed

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
    private javax.swing.JButton btnUmlagern;
    private javax.swing.JButton btnWeiteresZielhinzufügen;
    private javax.swing.JComboBox cbxQuelleLagertyp;
    private javax.swing.JComboBox cbxQuelleX;
    private javax.swing.JComboBox cbxQuelleY;
    private javax.swing.JComboBox cbxQuelleZ;
    private javax.swing.JComboBox cbxZ1Lagertyp;
    private javax.swing.JComboBox cbxZ1X;
    private javax.swing.JComboBox cbxZ1Y;
    private javax.swing.JComboBox cbxZ1Z;
    private javax.swing.JComboBox cbxZ2X;
    private javax.swing.JComboBox cbxZ2Z;
    private javax.swing.JComboBox cbxZiel0Lagertyp;
    private javax.swing.JComboBox cbxZiel0X;
    private javax.swing.JComboBox cbxZiel0Y;
    private javax.swing.JComboBox cbxZiel0Z;
    private javax.swing.JComboBox cbxz2Lagertyp;
    private javax.swing.JComboBox cbxz2Y;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblLager;
    private javax.swing.JLabel lblQuellfach;
    private javax.swing.JLabel lblTeilID;
    private javax.swing.JLabel lblUmlagern;
    private javax.swing.JLabel lblUmzulagerndeMenge;
    private javax.swing.JLabel lblX;
    private javax.swing.JLabel lblY;
    private javax.swing.JLabel lblZ;
    private javax.swing.JLabel lblZiel1;
    private javax.swing.JLabel lblZiel1Zielmenge;
    private javax.swing.JLabel lblZiel2;
    private javax.swing.JLabel lblZiel2Zielmenge;
    private javax.swing.JLabel lblZielLagerfach;
    private javax.swing.JPanel panQuellFach;
    private javax.swing.JPanel panSplitl;
    private javax.swing.JPanel panUmlagern;
    private javax.swing.JTextField txfTeilID;
    private javax.swing.JTextField txfUmzulagerndeMenge;
    private javax.swing.JTextField txfZiel1Zielmenge;
    private javax.swing.JTextField txfZiel2Zielmenge;
    // End of variables declaration//GEN-END:variables
}
