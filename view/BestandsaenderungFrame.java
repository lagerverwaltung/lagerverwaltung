/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.j256.ormlite.dao.Dao;
import helper.DatabaseManager;
import helper.LagerbestandHelper;
import helper.Misc;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import model.Lager;
import model.Lager.Lagerort;
import model.Lagerbestand;
import model.Lagerfach;
import model.Teilebestand;
import model.Warenbewegung;
import model.ZielPosition;
import model.collection.LagerbestandCollection;
import model.collection.TeilebestandCollection;
import model.collection.WarenbewegungCollection;
import model.table.LagerbestandTableModel;
import model.table.TeileTableModel;
import model.table.WarenbewegungTableModel;

/**
 *
 * @author simon
 */
//ändern
public class BestandsaenderungFrame extends javax.swing.JFrame {
    Boolean einlagern = false;
    Boolean auslagern = false;
    Boolean splitten = false;
    Boolean bestehenderLagerbestand=false;
    JTable lagerBestandTable;
    JTable teileBestandTable;
    JTable warenBewegungTable;
    int lagerbestandID;
    int fachid;
    int teilid;
    
    /**
     * Konstanten für die verschiendenen Frames
     */
    public final static int EINLAGERN_TEILEBESTAND = 1;
    public final static int EINLAGERN_LAGERBESTAND = 2;
    public final static int AUSLAGERN = 3;
    public final static int UMLAGERN = 4;
    public final static int SPLITTEN = 5;
    
    int code;
    BestandsGUIHelper help;
    
    /**
     * Creates new form BestandsaenderungFrame
     */
    private BestandsaenderungFrame(){
        initComponents();
        setLocationRelativeTo(null);
        cbxFachTyp.addItem("HL");
        cbxFachTyp.addItem("FL");
        try {
            loadHlCbx();
        } catch (SQLException ex) {
            Logger.getLogger(BestandsaenderungFrame.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }
    
    
      
    //Teil einlagern aus der Registerkarte Teilebestand
    BestandsaenderungFrame(boolean einlagern, int id) {
        this();
        this.einlagern = einlagern;
        lblEinlagern.setText("Teile einlagern");
        einlagernButton.setText("Teile einlagern");
        this.txfTeilID.setText(""+id);
        this.txfTeilID.setEditable(false);
        this.txfTeilID.setEnabled(false);
        

            try {
                loadHlCbx();
            } catch (SQLException ex) {
                Logger.getLogger(BestandsaenderungFrame.class.getName()).log(Level.SEVERE, null, ex); 
            }
    }
    
    /**
     * @author smodlich, ssinger
     * @param bestehenderLagerbestand ?
     * @param einlagern Vorgang Einlagern
     * @param lagerbestand betroffener Lagerbestand
     *  Erzeugt das BestandsänderungFrame als "Einlagern" und setzt die
     *  jeweiligen Formularfelder wie im übergebenen Lagerbestand.
     */
    BestandsaenderungFrame(boolean bestehenderLagerbestand, boolean einlagern, Lagerbestand lagerbestand) {
        this();
        this.einlagern = einlagern;
        this.bestehenderLagerbestand = bestehenderLagerbestand;
        this.fachid = lagerbestand.getLagerfach().getFachnummer();
        this.teilid = lagerbestand.getTeil().getIdentnummer();
        lblEinlagern.setText("Teile einlagern");
        einlagernButton.setText("Teile einlagern");
        
        loadTeilIdUndGrund(lagerbestand);
        loadQuellComboBoxen(lagerbestand);
    }
    
    /**
     * @author smodlich, ssinger
     * @param auslagern Vorgang Auslagern
     * @param lagerbestand betroffener Lagerbestand Erzeugt das
     * BestandsänderungFrame als "Auslagern" und setzt die jeweiligen
     * Formularfelder wie im übergebenen Lagerbestand.
     */
    public BestandsaenderungFrame(boolean auslagern, Lagerbestand lagerbestand) {
        this();
        this.auslagern = auslagern;
        lblEinlagern.setText("Teile auslagern");
        einlagernButton.setText("Teile auslagern");
        this.txfHaltbarkeitsdatum.setVisible(false);
        this.lblHaltbarkeitsdatum.setVisible(false);
        loadTeilIdUndGrund(lagerbestand);
        this.txaAnschaffungsgrund.setEditable(true);        
        loadQuellComboBoxen(lagerbestand);
        this.fachid = lagerbestand.getLagerfach().getFachnummer();
        this.teilid = lagerbestand.getTeil().getIdentnummer();
        this.lblHinweisDatum.setVisible(false);
    }
    
        
    BestandsaenderungFrame(boolean splitten) {
        this();
        this.splitten = splitten;
        lblEinlagern.setText("Teile splitten");
        einlagernButton.setText("Teile splitten");

        this.txfTeilID.setEditable(false);

        this.txfHaltbarkeitsdatum.setVisible(false);
        this.lblHaltbarkeitsdatum.setVisible(false);
    }

    /**
     * Vereinheitlichter Konstruktor für alle Bestandsaenderungsframes
     * @param code Code der angibt um welche Funktion aus welchem Panel es sich handelt. 
     * Codedeklaration im Kopf des Bestandsaenderungsframe als Konstanten
     * @param teilID die TeilID muss aus der Tabelle ausgelesen werden 
     * @param fachID die FachID muss für die Generierung des Lagerbestandes und die Anzeige des korrekten Faches übergeben werden
     * falls nötig muss sie konstruiert werden
     */
    public BestandsaenderungFrame(int code, int teilID, int fachID) throws SQLException
    {   
        this();
        this.txfTeilID.setText("" + teilID);
        this.txfTeilID.setEditable(false);
        this.txfTeilID.setEnabled(false);
        Lagerbestand lb= Lagerbestand.getLagerbestand(Lagerbestand.getLagerbestandID(teilID, fachID));
        setGUI(code,lb);
        loadTeilIdUndGrund(lb);
        
        this.code=code;
        
        help= new BestandsGUIHelper();
        
        help.setquellFachID(fachID);
        help.setTeilID(teilID);
        
        
        
        
    
    
    
    
    }
    
    /**
     * Methode die entscheidet welche GUI erzeugt wird
     * @param code der Code entsprechend der Variablen im Kopf des Bestandsaenderungsframes auszuwählen
     */
    
    private void setGUI(int code,Lagerbestand lb)
    {
        switch (code)
        {
            case EINLAGERN_TEILEBESTAND:
                    setEinlagernTeilebestandGUI();
                    break;
            case EINLAGERN_LAGERBESTAND:
                    setEinlagernLagerbestandGUI(lb);
                    break;
            case AUSLAGERN:
                    setAuslagernGUI(lb);
                    break;
            case UMLAGERN:
                    setUmlagernGUI(lb);
                    break;
            case SPLITTEN:
                    setSplittenGUI(lb);
                    break;
        }
    
    }
    
    private void setEinlagernTeilebestandGUI()
    {
        lblEinlagern.setText("Teile einlagern");
        einlagernButton.setText("Teile einlagern");
    
    }
    private void setEinlagernLagerbestandGUI(Lagerbestand lb)
    {
            lblEinlagern.setText("Teile einlagern");
            einlagernButton.setText("Teile einlagern");
            this.cbxFachTyp.setEnabled(false);
            this.cbxFachX.setEnabled(false);
            this.cbxFachY.setEnabled(false);
            this.cbxFachZ.setEnabled(false);
            
            this.txaAnschaffungsgrund.setText(lb.getAnschaffungsgrund());
            this.setVisible(true);
            loadQuellComboBoxen(lb);
    
    }
    
    private void setAuslagernGUI(Lagerbestand lb)
    {
            lblEinlagern.setText("Teile auslagern");
            einlagernButton.setText("Teile auslagern");
            
            
            this.txaAnschaffungsgrund.setEditable(true);
            this.txfHaltbarkeitsdatum.setVisible(false);
            this.lblHaltbarkeitsdatum.setVisible(false);
            
            loadQuellComboBoxen(lb);
            
            this.txaAnschaffungsgrund.setText(lb.getAnschaffungsgrund()); 
            
            this.lblHinweisDatum.setVisible(false);
            this.setVisible(true);
    
    }
    
    private void setUmlagernGUI(Lagerbestand lb)
    {
    
    
    }
    
    private void setSplittenGUI(Lagerbestand lb)
    {
        lblEinlagern.setText("Teile splitten");
        einlagernButton.setText("Teile splitten");
        this.txfTeilID.setEditable(false);
        this.txfHaltbarkeitsdatum.setVisible(false);
        this.lblHaltbarkeitsdatum.setVisible(false);
    
    }
    /**
     * @author ssinger
     * @param lagerbestand 
     * setzt Anschaffungsgrund und TeilID, setzt Textfeld TeilID
     * Editable(false) und Diabled es
     */
    private void loadTeilIdUndGrund(Lagerbestand lagerbestand){
        if(lagerbestand!=null)
        {
        this.txaAnschaffungsgrund.setText(lagerbestand.getAnschaffungsgrund());
        this.txfTeilID.setText("" + lagerbestand.getTeil().getIdentnummer());
        this.txfTeilID.setEditable(false);
        this.txfTeilID.setEnabled(false);
        }
    }
    
    
    /**
     * @author ssinger
     * @param lagerbestand 
     * läd die oberste Reihe der ComboBOxen mit den Daten des
     * übergebenen Lagerbstandes und setzt Enabled(false)
     */
    private void loadQuellComboBoxen(Lagerbestand lagerbestand){
        
        this.cbxFachTyp.setSelectedItem(lagerbestand.getLagerfach().getLager().getLagerortCode());
        this.cbxFachX.setSelectedItem(lagerbestand.getLagerfach().getX());
        this.cbxFachY.setSelectedItem(lagerbestand.getLagerfach().getY());
        this.cbxFachZ.setSelectedItem(lagerbestand.getLagerfach().getZ());
       
        this.cbxFachX.setEnabled(false);
        this.cbxFachY.setEnabled(false);
        this.cbxFachZ.setEnabled(false);
        this.cbxFachTyp.setEnabled(false);
    }
    
    //Table setzen
    public void setTable(JTable t)
    {
        lagerBestandTable = t;
    }
    
    public void setTeileTable(JTable t)
    {
        teileBestandTable = t;
    }
    
    public void setWarenBewegungTable(JTable t){
        warenBewegungTable = t;
    }
    
     public void initLagerbestand(int id) throws SQLException
    {
        Lagerbestand l = Lagerbestand.loadLagerObjekt(id); 
        Warenbewegung w = Warenbewegung.loadWarenbewegung(id);
        if(l != null){
            lagerbestandID = id;
            txaAnschaffungsgrund.setText(l.getAnschaffungsgrund());
            txfMenge.setText(Integer.toString(l.getMenge()));
            Format f = new SimpleDateFormat("DD.MM.YYYY");
            if(w.getHaltbarkeitsDatum() == null){
                txfHaltbarkeitsdatum.setText("");
            }else{
            txfHaltbarkeitsdatum.setText(f.format(w.getHaltbarkeitsDatum()));
            }
        }
    }
     
    /*
     * @author ssinger
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

    /**
     * @author ssinger
     * läd ComboBoxen für Freilagern
     */
    public void loadFlCbx() throws SQLException {
        cbxFachZ.removeAllItems();
        cbxFachX.removeAllItems();
        cbxFachY.removeAllItems();

        Lager fl = Lager.getLager(Lager.Lagerort.freilager);
        int x = fl.getHoehe();
        int y = fl.getBreite();
        int z = fl.getTiefe();

        for (int i = 1; i <= x; i++) {
            cbxFachZ.addItem(i);
        }

        for (int i = 1; i <= y; i++) {
            cbxFachX.addItem(i);
        }

        for (int i = 1; i <= z; i++) {
            cbxFachY.addItem(i);
        }
    }

    /*
     * debrecated
     */
    private void loadLagerOrtCbx(Lager.Lagerort s){
       if (s.equals(Lager.Lagerort.freilager)){
           cbxFachTyp.setSelectedItem("FL");
       } 
       else{
           cbxFachTyp.setSelectedItem("HL");
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
        lblHinweisDatum = new javax.swing.JLabel();

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


        cbxFachTyp.setModel(new javax.swing.DefaultComboBoxModel());
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

        txfHaltbarkeitsdatum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfHaltbarkeitsdatumActionPerformed(evt);
            }
        });

        lblHinweisDatum.setText("Hinweis: dd.mm.yyyy");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEinlagern, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(einlagernButton)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFachAdresse)
                            .addComponent(lblTeilID)
                            .addComponent(lblAnschaffungsgrund)
                            .addComponent(lblMenge)
                            .addComponent(lblHaltbarkeitsdatum))
                        .addGap(65, 65, 65)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txfHaltbarkeitsdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblHinweisDatum))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbxFachTyp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxFachX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxFachY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxFachZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblHinweis))
                            .addComponent(spnAnschaffungsgrund, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txfTeilID, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txfMenge, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 35, Short.MAX_VALUE))
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
                    .addComponent(txfHaltbarkeitsdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHinweisDatum))
                .addGap(29, 29, 29)
                .addComponent(einlagernButton)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//ändern 
    private void einlagernButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_einlagernButtonActionPerformed

        
        
        if (code!=SPLITTEN)
        {
            String[] menge={txfMenge.getText()};
            HashMap<Integer,String> errors =help.validateLagerbestandData
        (code,menge, txfHaltbarkeitsdatum.getText(), 
                txaAnschaffungsgrund.getText());
        
            try {
            Lagerfach[] faecher= new Lagerfach[1];
            faecher[0] = help.generateFach((int)cbxFachX.getSelectedItem(), (int)cbxFachY.getSelectedItem(), (int)cbxFachZ.getSelectedItem(), cbxFachTyp.getSelectedItem().toString());
            help.setFaecher(faecher);
        } catch (Exception ex) {
            Misc.createErrorDialog(this, "Fehler bei der Fach Generierung");
            return;
        }
        
        
       
        
        if (errors.size()>0)
        {
             Misc.createErrorDialog(this, errors);
             return;
        }
        
        }
        
        
        // Splitten ergänzen
        else
        {
        
        
        
        }
        try {
            bestandsAenderung(code,help);
        } catch (SQLException ex) {
           System.out.println("SQL Exception");
        }
        
        refreshLagerbestandTableModel();
        /*refreshTeilebestandTableModel();
        refreshWarenbestandTableModel();*/
    }//GEN-LAST:event_einlagernButtonActionPerformed
   
    
    public void bestandsAenderung(int code,BestandsGUIHelper help) throws SQLException
    {
        int quellLbID= Lagerbestand.getLagerbestandID(help.getTeilID(), help.getquellFachID());
        
        Lagerbestand quellLb= Lagerbestand.getLagerbestand(quellLbID);
     /*   ZielPosition[] zielPos= new ZielPosition[help.getMengen().length];
        Warenbewegung[] wBewegungen = new Warenbewegung[help.getMengen().length];
        */
        int mengeOld=quellLb.getMenge();
        Teilebestand teil=Teilebestand.loadTeil(help.getTeilID());
        
        if(code==AUSLAGERN || code==UMLAGERN || code==SPLITTEN)
        {
            help.getMengen()[0]= - help.getMengen()[0];
        }
        
        HashMap<Integer,String> errors=kapazitaetsTest(help.getFaecher(),teil,help.getMengen(),mengeOld);
        
        if(errors.size()>0)
        { 
            Misc.createErrorDialog(this, errors);
            System.out.println(errors.toString());
            return;
        }
        
        switch(code)
        {
            case EINLAGERN_TEILEBESTAND:
                    if(quellLb==null)
                    {
                    
                    
                    
                    }            
                    else
                    {
                        quellLb.setMenge(mengeOld+help.getMengen()[0]);
                    }
                        
                        
                    break;
            case EINLAGERN_LAGERBESTAND:
                if(quellLb!=null)
                    quellLb.setMenge(mengeOld+help.getMengen()[0]);;
                    break;
            case AUSLAGERN:
                    ;
                    break;
            case UMLAGERN:
                    ;
                    break;
            case SPLITTEN:
                    ;
                    break;
        }
        
        quellLb.save();
        this.dispose();
    
    
    }
    
    public HashMap<Integer,String> kapazitaetsTest(Lagerfach[] faecher,Teilebestand teil,int[] mengen,int mengeOld) throws SQLException
    {
        HashMap<Integer,String> errors = new HashMap<Integer,String>();
        int groesse= teil.getVe();
        
        int errorIndex=0;
        
        for(int i=0;i<faecher.length;i++)
        {
           int freeVE=faecher[i].getFreeVe();
           int usedVE=faecher[i].getUsedVe();
           int maxVE=faecher[i].getMaxVe();
           
           
           
           if((mengen[i]*groesse)>freeVE)
           {
               errorIndex++;
               errors.put(errorIndex,"Die Kapazitaet im Fach X:"+ faecher[i].getX()+" Y:"+faecher[i].getY() +" Z:"+ 
                       faecher[i].getZ()+ " ist nicht ausreichend. Es sind noch " + freeVE + " VE frei.Aber es werden" + 
                       mengen[i]*groesse+ " VE benötigt.");
               
               
           }
           
        
        }
        if((mengen[0]+mengeOld)<0)
        {
            errorIndex++;
            errors.put(errorIndex, "Keine ausreichende Menge im Quellfach vorhanden!");
        }
    
        return errors;
    }
    
    private void einlagernButtonActionPerformedOLD(java.awt.event.ActionEvent evt)
    {
    
       
        int x = (int) (cbxFachX.getSelectedItem());
        int y = (int) (cbxFachY.getSelectedItem());
        int z = (int) (cbxFachZ.getSelectedItem());
        Lager l = new Lager();
        if (cbxFachTyp.getSelectedItem().equals("FL")) {

            l.setLagerort(Lager.Lagerort.freilager);
        } else {
            l.setLagerort(Lager.Lagerort.hochregal);
        }

        int fachID = 0;
        int teiID = Integer.parseInt(txfTeilID.getText());
        Lagerbestand lb = null;
        int lagerbestandsid = 0;
        try {
            fachID = Lagerfach.getFach(l, x, y, z).getFachnummer();
            if ((Lagerfach.getFach(l, x, y, z)) == null) {
                lagerbestandsid = 0;
            }
            lagerbestandsid = Lagerbestand.getLagerbestandID(teiID, fachID);
            lb = Lagerbestand.getLagerbestand(lagerbestandsid);

            if (lb != null) {
                bestehenderLagerbestand = true;
            }
        } catch (SQLException e) {
            Misc.createErrorDialog(this, "SQL Error");
        }

        try {
            //Einlagern
            if (einlagern && !bestehenderLagerbestand) {

                //Variablendeklaration
                lb = new Lagerbestand();
                Warenbewegung wb = new Warenbewegung();
                Lagerfach lf = new Lagerfach();
                Teilebestand tb = new Teilebestand();

                fachID = 0;
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                df.setLenient(false);
                Date hd = null;
                Date today = new Date();
                int mng = 0;
                String errors = "";

                //Übernahme der Variablen aus der GUI und Validierung
                String ag = txaAnschaffungsgrund.getText();
                if (ag.length() == 0) {
                    errors += "Bitte Anschaffungsgrund eingeben. \n";
                }
                //Grund darf kein Leerezeichen enthalten
                for (int i = 0; i < ag.length(); i++) {
                    if (ag.charAt(i) == ' ') {
                        errors += "Grund darf kein Leerezeichen enthalten. \n";
                    }
                }

                if (txfHaltbarkeitsdatum.getText().length() > 0) {
                    try {
                        hd = df.parse(txfHaltbarkeitsdatum.getText());
                    } catch (ParseException ex) {
                        errors += "Das Haltbarkeitsdatum muss im Format tt.mm.jjjj eingegeben werden. \n";
                    }
                    if (hd != null && hd.before(today)) {
                        errors += "Achtung, Artikel ist schon abgelaufen. \n";
                    }
                }

                try {
                    if ((txfMenge.getText().length() > 0) || (txfMenge.getText().matches("[0-9]+"))) {
                        mng = Integer.parseInt(txfMenge.getText());
                    } else {
                        errors += "Bitte einzulagernde Menge eingeben. +\n";
                    }
                } catch (NumberFormatException e) {
                    errors += "Bitte einzulagernde Menge eingeben. +\n";
                }
                teiID = Integer.parseInt(txfTeilID.getText());
                l = new Lager();
                if (cbxFachTyp.getSelectedItem().equals("FL")) {

                    l.setLagerort(Lager.Lagerort.freilager);
                } else {
                    l.setLagerort(Lager.Lagerort.hochregal);
                }
                x = (int) (cbxFachX.getSelectedItem());
                y = (int) (cbxFachY.getSelectedItem());
                z = (int) (cbxFachZ.getSelectedItem());

                fachID = Lagerfach.getFach(l, x, y, z).getFachnummer();

                int freeVe = Lagerfach.getLagerfach(fachID).getFreeVe();
                int maxVe = Lagerfach.getLagerfach(fachID).getMaxVe();
                int usedVe = Lagerfach.getLagerfach(fachID).getUsedVe();

                if (mng > freeVe) {
                    errors += "Es steht nicht genug Platz zum Einlagern zur Verfügung. \n";
                    errors += usedVe + " von " + maxVe + " VE belegt. " + freeVe + " VE frei. \n";
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

                Lagerfach qlf = new Lagerfach();
                qlf.setFachnummer(0);

                //Speichert die Warenbewegung
                wb.setVerantwortlicher("Lagerverwalter");
                wb.setQuellFach(qlf);
                wb.setDatum(today);
                wb.setHaltbarkeitsDatum(hd);
                wb.setAnschaffungsgrund(ag);

                //Ziellagerfach zusammensetzen
                ZielPosition zpZiel = new ZielPosition();
                zpZiel.setLagerfach(lf);
                zpZiel.setMenge(mng);
                zpZiel.setWarenbewegung(wb);

                //Lagerbestand speichern
                lb.save();
                wb.setLagerbestand(lb);
                wb.save();
                zpZiel.save();

                refreshLagerbestandTableModel();
                refreshWarenbestandTableModel();
                this.dispose();

                //Einlagern mit bestehendem Teil
            } else if (bestehenderLagerbestand && !auslagern) {
                if (fachID == 0) {
                    fachID = Lagerfach.getFach(l, x, y, z).getFachnummer();
                }

                lagerbestandsid = Lagerbestand.getLagerbestandID(teiID, fachID);
                lb = null;
                lb = Lagerbestand.getLagerbestand(lagerbestandsid);
                Lagerfach lf = lb.getLagerfach();
                String errors = "";

                int menge = 0;
                try {
                    if ((txfMenge.getText().length() > 0) || (txfMenge.getText().matches("[0-9]+"))) {
                        menge = Integer.parseInt(txfMenge.getText());
                    } else {
                        errors += "Bitte einzulagernde Menge eingeben. +\n";
                    }
                } catch (NumberFormatException e) {
                    errors += "Bitte einzulagernde Menge eingeben. +\n";
                }

                int freeVe = lf.getFreeVe();
                try {
                    menge = Integer.parseInt(this.txfMenge.getText());
                } catch (Exception e) {
                    Logger.getLogger(BestandsaenderungFrame.class.getName()).log(Level.SEVERE, null, e);
                }
                if (menge > 0 && menge * lb.getTeil().getVe() <= freeVe) {
                    lb.setMenge(lb.getMenge() + menge);
                    lb.save();
                } else if (menge <= 0) {
                    errors += "Die eingegebene Menge muss größer 0 sein! \n";
                } else {
                    errors += "Es ist nicht genug Platz im Lagerfach, nur " + freeVe + " VE frei, benötigt:" + lb.getTeil().getVe() * menge + "\n";
                }
                if (Misc.createErrorDialog(this, errors) == true) {
                    return;
                }
                refreshLagerbestandTableModel();
                refreshWarenbestandTableModel();
                this.dispose();
            } // auslagern
            else {
                lagerbestandsid = Lagerbestand.getLagerbestandID(this.teilid, this.fachid);
                lb = Lagerbestand.getLagerbestand(lagerbestandsid);
                Teilebestand tb = lb.getTeil();
                boolean isLastTeil = true;
                String errors = "";
                int menge = 0;
                try {
                    if ((txfMenge.getText().length() > 0) || (txfMenge.getText().matches("[0-9]+"))) {
                        menge = Integer.parseInt(txfMenge.getText());
                    } else {
                        errors += "Bitte einzulagernde Menge eingeben. +\n";
                    }
                } catch (NumberFormatException e) {
                    errors += "Bitte einzulagernde Menge eingeben. +\n";
                }

                isLastTeil = Lagerbestand.isLastTeil(lb, menge);
                if (menge <= lb.getMenge()) {
                    int lbMenge = lb.getMenge();
                    if (menge < lbMenge) {
                        lb.setMenge(lb.getMenge() - menge);
                        lb.save();
                    }

                    if (menge == lbMenge && isLastTeil) {
                        int option = JOptionPane.showConfirmDialog(this, "Soll das zugehörige Teil aus dem Teilebestand gelöscht werden ?");

                        if (option == JOptionPane.YES_OPTION) {
                            Dao<Lagerbestand, Integer> lagerbestandDao = DatabaseManager.getInstance().getLagerbestandDao();
                            // lagerbestandDao.delete(lb);
                            lb.setMenge(0);
                            Dao<Teilebestand, Integer> teilebestandDao = DatabaseManager.getInstance().getTeilebestandDao();
                            teilebestandDao.deleteById(teilid);
                            lb.save();
                            refreshLagerbestandTableModel();
                            refreshTeilebestandTableModel();
                            this.dispose();
                        }
                        if (option == JOptionPane.CANCEL_OPTION) {
                        }
                        if (option == JOptionPane.NO_OPTION) {

                            lb.setMenge(lbMenge - menge);
                            lb.save();
                            refreshLagerbestandTableModel();
                            this.dispose();

                        }
                    } else if (menge == lbMenge) {
                        lb.setMenge(lbMenge - menge);
                        lb.save();
                        refreshLagerbestandTableModel();
                        this.dispose();
                    }
                } else {
                    Misc.createErrorDialog(this, "Eingegebene Menge ist größer als die eingelagerte Menge!");
                    return;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BestandsaenderungFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
    
    }
    private void refreshLagerbestandTableModel(){
        LagerbestandCollection lc = LagerbestandCollection.getInstance(true);
        LagerbestandTableModel lm = new LagerbestandTableModel();
        lm.setData(lc);
        lagerBestandTable.setModel(lm);
    }
    
    private void refreshTeilebestandTableModel(){
        TeilebestandCollection tc = TeilebestandCollection.getInstance(true);
        TeileTableModel tm = new TeileTableModel();
        tm.setData(tc);
        teileBestandTable.setModel(tm);
    }
    
    private void refreshWarenbestandTableModel(){
        WarenbewegungCollection wc = WarenbewegungCollection.getInstance(true);
        WarenbewegungTableModel wm = new WarenbewegungTableModel();
        wm.setData(wc);
        warenBewegungTable.setModel(wm);

        int[] arrWidths = {15, 120, 70, 40, 170, 90, 65, 60,90};
        TableColumn tc;
        warenBewegungTable.setRowHeight(23);
        int i = 0;
        for (int width : arrWidths){
            tc = warenBewegungTable.getColumnModel().getColumn(i++);
            tc.setPreferredWidth(width);
        }
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
    private javax.swing.JLabel lblHinweisDatum;
    private javax.swing.JLabel lblMenge;
    private javax.swing.JLabel lblTeilID;
    private javax.swing.JScrollPane spnAnschaffungsgrund;
    private javax.swing.JTextArea txaAnschaffungsgrund;
    private javax.swing.JTextField txfHaltbarkeitsdatum;
    private javax.swing.JTextField txfMenge;
    private javax.swing.JTextField txfTeilID;
    // End of variables declaration//GEN-END:variables

}
