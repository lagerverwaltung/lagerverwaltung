/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.j256.ormlite.dao.Dao;
import helper.DatabaseManager;
import helper.Misc;
import helper.TableHelper;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
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


    JTable lagerBestandTable;
    JTable teileBestandTable;
    JTable warenBewegungTable;
    
    /**
     * Konstanten für die verschiendenen Frames
     */
    public final static int EINLAGERN_TEILEBESTAND = 1;
    public final static int EINLAGERN_LAGERBESTAND = 2;
    public final static int AUSLAGERN = 3;
    public final static int UMLAGERN = 4;
    public final static int SPLITTEN = 5;
    
    /**
     * action der anzeigt um welchen Frame es sich handelt
     * BestandsGUIHelper zum auslesen und validieren der GUI
     */
    int action;
    BestandsGUIHelper bestandsGuiHelper;
    Lagerbestand selectedLagerbestand;
    HashMap faecherCbs = new HashMap();
    
    /**
     * Array für die Zielfaeche rund Mengen
     */
    JComboBox cbX[];
    JComboBox cbY[];
    JComboBox cbZ[];
    JComboBox cbLager[];
    JTextField qty[];
    
    /**
     * Creates new form BestandsaenderungFrame
     */
    private BestandsaenderungFrame(){
        initComponents();
        registerFaecherCbs("quelle", cbxFachX, cbxFachY,cbxFachZ);
        setLocationRelativeTo(null);
        cbxFachTyp.addItem("HL");
        cbxFachTyp.addItem("FL");
        try {
           
            loadLagerCbx("quelle","HL");
        } catch (SQLException ex) {
            Misc.printSQLException(this, ex); 
        }
    }
    
    /**
     * @author smodlich
     * Vereinheitlichter Konstruktor für alle Bestandsaenderungsframes
     * @param action Code der angibt um welche Funktion aus welchem Panel es sich handelt. 
     * Codedeklaration im Kopf des Bestandsaenderungsframe als Konstanten
     * @param teilID die TeilID muss aus der Tabelle ausgelesen werden 
     * @param fachID die FachID muss für die Generierung des Lagerbestandes und die Anzeige des korrekten Faches übergeben werden
     * falls nötig muss sie konstruiert werden
     */
    public BestandsaenderungFrame(int action, int teilID, int fachID) throws SQLException
    {   
        this();
        this.txfTeilID.setText("" + teilID);
        this.txfTeilID.setEditable(false);
        this.txfTeilID.setEnabled(false);
        pnlDestination.setVisible(false);
        pnlSplit.setVisible(false);
        pnlSplit2.setVisible(false);
        Lagerbestand lb= Lagerbestand.getLagerbestand(Lagerbestand.getLagerbestandID(teilID, fachID));
        this.selectedLagerbestand = lb;
        setGUI(action,lb);
        loadTeilIdUndGrund(lb);
       
        this.action=action;
        
        bestandsGuiHelper= new BestandsGUIHelper();
        
        bestandsGuiHelper.setQuellFachID(fachID);
        bestandsGuiHelper.setTeilID(teilID);

    }
    
    /**
     * @author smodlich
     * Methode die entscheidet welche GUI erzeugt wird
     * @param action der Code entsprechend der Variablen im Kopf des Bestandsaenderungsframes auszuwählen
     */
    
    private void setGUI(int action,Lagerbestand lb) throws SQLException
    {
        switch (action)
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
    /**
     * @author smodlich
     * Erstellen der Einlagern Teilebstand GUI und setzen der entsprechenden Elemente
     * 
     */
    
    private void setEinlagernTeilebestandGUI()
    {
        lblEinlagern.setText("Teile einlagern");
        einlagernButton.setText("Teile einlagern");
    
    }
    
    
    /**
     * @author smodlich
     * Erstellen der Einlagern Lagerbestand GUI und setzen der entsprechenden Elemente
     * 
     */
    
    private void setEinlagernLagerbestandGUI(Lagerbestand lb)
    {
        lblEinlagern.setText("Teile einlagern");
        einlagernButton.setText("Teile einlagern");        
        this.txaAnschaffungsgrund.setText(lb.getAnschaffungsgrund());
        this.setVisible(true);
        loadQuellComboBoxen(lb);
    }
    /**
     * @author smodlich
     * Erstellen der Auslagern GUI und setzen der entsprechenden Elemente
     * 
     */
    private void setAuslagernGUI(Lagerbestand lb)
    {
        lblEinlagern.setText("Teile auslagern");
        einlagernButton.setText("Teile auslagern");
        loadQuellComboBoxen(lb);
        this.txaAnschaffungsgrund.setText(lb.getAnschaffungsgrund()); 
        lblHaltbarkeitsdatum.setVisible(false);
        txfHaltbarkeitsdatum.setVisible(false);
        this.lblHinweisDatum.setVisible(false);
        this.setVisible(true);
    }
    /**
     * Erstellen der Umlagern GUI und setzen der entsprechenden Elemente
     * 
     */
    private void setUmlagernGUI(Lagerbestand lb) throws SQLException
    {
        lblEinlagern.setText("Teile umlagern");
        einlagernButton.setText("Teile umlagern");
        loadQuellComboBoxen(lb);
        this.txaAnschaffungsgrund.setText(lb.getAnschaffungsgrund()); 
        lblHaltbarkeitsdatum.setVisible(false);
        txfHaltbarkeitsdatum.setVisible(false);
        this.lblHinweisDatum.setVisible(false);
        pnlDestination.setVisible(true);
        registerFaecherCbs("dest", cbxFachX1, cbxFachY1,cbxFachZ1);
        cbxFachTyp1.addItem("HL");
        cbxFachTyp1.addItem("FL");
        loadLagerCbx("dest", "HL");
        this.setVisible(true);
    
    }
    /**
     * Erstellen der Splitten GUI und setzen der entsprechenden Elemente
     * 
     */
    private void setSplittenGUI(Lagerbestand lb)
    {
        lblEinlagern.setText("Teile splitten");
        einlagernButton.setText("Teile splitten");
         loadQuellComboBoxen(lb);
        lblHaltbarkeitsdatum.setVisible(false);
        txfHaltbarkeitsdatum.setVisible(false);
        this.lblHinweisDatum.setVisible(false);
        pnlSplit.setVisible(true);
        pnlSplit2.setVisible(true);
        pnlQty.setVisible(false);
        setSize(new Dimension(this.getWidth(), this.getHeight()+110));
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
    /*
     * @author spickert
    * Regisrtiert zu aktualisierende Comboboxen
    * @param String id enthält die ID für die Fachklasse die registriert werden soll 
    * @param JComboBox cbX, cbY, cbZ enthält die Comboboxen die registriert werden
    */
    public void registerFaecherCbs(String id, JComboBox cbX, JComboBox cbY, JComboBox cbZ){
        HashMap hmFields = new HashMap();
        hmFields.put("x", cbX);
        hmFields.put("y", cbY);
        hmFields.put("z", cbZ);
        faecherCbs.put(id, hmFields);
    }
    /*
     * @author spickert
    * Aktualisiert Comboboxen für alle Lagertypen
    * @param String destination enthält die ID für die Fachklasse die aktualisiert werden soll 
    * @param String lagerCode enthält den Code für das ausgewählte Lager
    */
    private void loadLagerCbx(String destination, String lagerCode) throws SQLException{
        HashMap hmFields = (HashMap) faecherCbs.get(destination);
        Lager lager = Lager.getLager(Lager.getLagerort(lagerCode));
        int x = lager.getBreite();
        int y = lager.getTiefe();
        int z = lager.getHoehe();
       
        JComboBox cbX = (JComboBox) hmFields.get("x");
        JComboBox cbY = (JComboBox) hmFields.get("y");
        JComboBox cbZ = (JComboBox) hmFields.get("z");
        
        cbX.removeAllItems();
        cbY.removeAllItems();
        cbZ.removeAllItems();
        
        for(int i = 1; i <= x; i++){
            cbX.addItem(i);
        }
        
        for(int i = 1; i <= y; i++){
            cbY.addItem(i);
        }
                
         for(int i = 1; i <= z; i++){
            cbZ.addItem(i);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this action. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblEinlagern = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        lblFachAdresse = new javax.swing.JLabel();
        cbxFachTyp = new javax.swing.JComboBox();
        cbxFachX = new javax.swing.JComboBox();
        cbxFachY = new javax.swing.JComboBox();
        cbxFachZ = new javax.swing.JComboBox();
        lblHinweis = new javax.swing.JLabel();
        pnlSplit = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txfSplitAmount = new javax.swing.JTextField();
        btnAddSplitDest = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblTeilID = new javax.swing.JLabel();
        txfTeilID = new javax.swing.JTextField();
        pnlQty = new javax.swing.JPanel();
        lblMenge = new javax.swing.JLabel();
        txfMenge = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        lblHaltbarkeitsdatum = new javax.swing.JLabel();
        txfHaltbarkeitsdatum = new javax.swing.JTextField();
        lblHinweisDatum = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lblAnschaffungsgrund = new javax.swing.JLabel();
        spnAnschaffungsgrund = new javax.swing.JScrollPane();
        txaAnschaffungsgrund = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        pnlDestination = new javax.swing.JPanel();
        lblFachAdresse1 = new javax.swing.JLabel();
        cbxFachTyp1 = new javax.swing.JComboBox();
        cbxFachX1 = new javax.swing.JComboBox();
        cbxFachY1 = new javax.swing.JComboBox();
        cbxFachZ1 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        einlagernButton = new javax.swing.JButton();
        pnlSplit2 = new javax.swing.JScrollPane();
        pnlDestinations = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(602, 474));

        lblEinlagern.setText("Teile auslagern");

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblFachAdresse.setText("Lagerfachadresse:");
        lblFachAdresse.setPreferredSize(new java.awt.Dimension(150, 20));
        jPanel1.add(lblFachAdresse);

        cbxFachTyp.setModel(new javax.swing.DefaultComboBoxModel());
        cbxFachTyp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFachTypActionPerformed(evt);
            }
        });
        jPanel1.add(cbxFachTyp);

        cbxFachX.setModel(new javax.swing.DefaultComboBoxModel());
        cbxFachX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFachXActionPerformed(evt);
            }
        });
        jPanel1.add(cbxFachX);

        cbxFachY.setModel(new javax.swing.DefaultComboBoxModel());
        jPanel1.add(cbxFachY);

        cbxFachZ.setModel(new javax.swing.DefaultComboBoxModel());
        jPanel1.add(cbxFachZ);

        lblHinweis.setText("Hinweis: [LagerID][x][y][z]");
        jPanel1.add(lblHinweis);

        pnlSplit.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setText("Auf wieviele Felder wollen Sie splitten?");
        jLabel1.setPreferredSize(new java.awt.Dimension(277, 35));
        pnlSplit.add(jLabel1);

        jLabel2.setText("Anzahl der Ziele:");
        jLabel2.setPreferredSize(new java.awt.Dimension(150, 15));
        pnlSplit.add(jLabel2);

        txfSplitAmount.setPreferredSize(new java.awt.Dimension(40, 22));
        txfSplitAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txfSplitAmountKeyReleased(evt);
            }
        });
        pnlSplit.add(txfSplitAmount);

        btnAddSplitDest.setText("Hinzufügen");
        btnAddSplitDest.setEnabled(false);
        btnAddSplitDest.setPreferredSize(new java.awt.Dimension(115, 20));
        btnAddSplitDest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSplitDestActionPerformed(evt);
            }
        });
        pnlSplit.add(btnAddSplitDest);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblTeilID.setText("Teil ID:");
        lblTeilID.setPreferredSize(new java.awt.Dimension(150, 20));
        jPanel2.add(lblTeilID);

        txfTeilID.setMinimumSize(new java.awt.Dimension(20, 40));
        txfTeilID.setPreferredSize(new java.awt.Dimension(50, 22));
        txfTeilID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfTeilIDActionPerformed(evt);
            }
        });
        jPanel2.add(txfTeilID);

        pnlQty.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblMenge.setText("Menge:");
        lblMenge.setPreferredSize(new java.awt.Dimension(150, 20));
        pnlQty.add(lblMenge);

        txfMenge.setPreferredSize(new java.awt.Dimension(50, 22));
        pnlQty.add(txfMenge);

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblHaltbarkeitsdatum.setText("Haltbarkeitsdatum");
        lblHaltbarkeitsdatum.setPreferredSize(new java.awt.Dimension(150, 20));
        jPanel5.add(lblHaltbarkeitsdatum);

        txfHaltbarkeitsdatum.setMinimumSize(new java.awt.Dimension(50, 20));
        txfHaltbarkeitsdatum.setPreferredSize(new java.awt.Dimension(90, 22));
        txfHaltbarkeitsdatum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfHaltbarkeitsdatumActionPerformed(evt);
            }
        });
        jPanel5.add(txfHaltbarkeitsdatum);

        lblHinweisDatum.setText("Hinweis: dd.mm.yyyy");
        jPanel5.add(lblHinweisDatum);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblAnschaffungsgrund.setText("Grund:");
        lblAnschaffungsgrund.setPreferredSize(new java.awt.Dimension(150, 20));
        jPanel6.add(lblAnschaffungsgrund);

        txaAnschaffungsgrund.setColumns(30);
        txaAnschaffungsgrund.setRows(5);
        spnAnschaffungsgrund.setViewportView(txaAnschaffungsgrund);

        jPanel6.add(spnAnschaffungsgrund);

        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        pnlDestination.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblFachAdresse1.setText("Ziel-Lagerfach");
        lblFachAdresse1.setPreferredSize(new java.awt.Dimension(150, 20));
        pnlDestination.add(lblFachAdresse1);

        cbxFachTyp1.setModel(new javax.swing.DefaultComboBoxModel());
        cbxFachTyp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFachTyp1ActionPerformed(evt);
            }
        });
        pnlDestination.add(cbxFachTyp1);

        cbxFachX1.setModel(new javax.swing.DefaultComboBoxModel());
        cbxFachX1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFachX1ActionPerformed(evt);
            }
        });
        pnlDestination.add(cbxFachX1);

        cbxFachY1.setModel(new javax.swing.DefaultComboBoxModel());
        pnlDestination.add(cbxFachY1);

        cbxFachZ1.setModel(new javax.swing.DefaultComboBoxModel());
        pnlDestination.add(cbxFachZ1);

        einlagernButton.setText("Teile auslagern");
        einlagernButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                einlagernButtonActionPerformed(evt);
            }
        });
        jPanel3.add(einlagernButton);

        pnlSplit2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout pnlDestinationsLayout = new javax.swing.GroupLayout(pnlDestinations);
        pnlDestinations.setLayout(pnlDestinationsLayout);
        pnlDestinationsLayout.setHorizontalGroup(
            pnlDestinationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 539, Short.MAX_VALUE)
        );
        pnlDestinationsLayout.setVerticalGroup(
            pnlDestinationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 215, Short.MAX_VALUE)
        );

        pnlSplit2.setViewportView(pnlDestinations);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEinlagern, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlQty, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlSplit, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlSplit2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 9, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblEinlagern)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlSplit, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSplit2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private  ArrayList<HashMap> getDestinations(){
        ArrayList<HashMap> destList = new ArrayList();
        HashMap hm = new HashMap();
       
        if (action == UMLAGERN){
            hm.put("fachCode",cbxFachTyp1.getSelectedItem());
            hm.put("x",cbxFachX1.getSelectedItem());
            hm.put("y",cbxFachY1.getSelectedItem());
            hm.put("z",cbxFachZ1.getSelectedItem());
            hm.put("qty",txfMenge.getText());
            destList.add(hm);
        }
       else if (action == EINLAGERN_LAGERBESTAND || action == EINLAGERN_TEILEBESTAND){
            hm.put("fachCode",cbxFachTyp.getSelectedItem());
            hm.put("x",cbxFachX.getSelectedItem());
            hm.put("y",cbxFachY.getSelectedItem());
            hm.put("z",cbxFachZ.getSelectedItem());
            hm.put("qty",txfMenge.getText());
            destList.add(hm);
       }
       else if (action == SPLITTEN){
            for(int i = 0; i<cbLager.length; i++){
                HashMap hm1 = new HashMap();
                hm1.put("fachCode",cbLager[i].getSelectedItem());
                hm1.put("x",cbX[i].getSelectedItem());
                hm1.put("y",cbY[i].getSelectedItem());
                hm1.put("z",cbZ[i].getSelectedItem());
                hm1.put("qty",qty[i].getText());
                destList.add(hm1);
            }
        }
        return destList;
    }
    private void einlagernButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_einlagernButtonActionPerformed
        try {
            String menge = txfMenge.getText();

            HashMap<Integer, String> errors = bestandsGuiHelper.validateLagerbestandData(action, menge, txfHaltbarkeitsdatum.getText(),
                    txaAnschaffungsgrund.getText(), getDestinations()); 

            Lagerfach[] faecher = new Lagerfach[1];
            try {
                faecher[0] = bestandsGuiHelper.generateFach((int) cbxFachX.getSelectedItem(), (int) cbxFachY.getSelectedItem(), (int) cbxFachZ.getSelectedItem(), cbxFachTyp.getSelectedItem().toString());
            } catch (SQLException ex) {
                Misc.printSQLException(this, ex);
            }
            bestandsGuiHelper.setFaecher(faecher);
            if (action == EINLAGERN_TEILEBESTAND) {
                bestandsGuiHelper.setQuellFachID(bestandsGuiHelper.getEinLagerfach().getFachnummer());
            }

            if (errors.size() > 0) {
                Misc.createErrorDialog(this, errors);
                return;
            }
            bestandsAenderung(action, bestandsGuiHelper);
        } catch (SQLException ex) {
            Misc.printSQLException(this, ex);
        }

        refreshLagerbestandTableModel();
        TableHelper.refreshWarenbewegungTableModel(warenBewegungTable);
    }//GEN-LAST:event_einlagernButtonActionPerformed
   
    /**
     * Methode zur Bestandsänderung
     * @author smodlich
     * @param action Der entsprechende Code (nach den Konstanten in dieser Klasse)
     * @param help das BestandsGUIHelper Objekt das die ausgelesenen Variablen enthält
     * @throws SQLException 
     */
    public void bestandsAenderung(int action,BestandsGUIHelper help) throws SQLException
    {
        Lagerbestand quellLb= Lagerbestand.getLagerbestand(help.getTeilID(), help.getQuellFachID());
        ArrayList zielPositionen = new ArrayList();
        int mengeOld = 0;
        int mengeAdd = help.getMenge();

        if (quellLb != null) {
            mengeOld = quellLb.getMenge();
        }

        Teilebestand teil = Teilebestand.loadTeil(help.getTeilID());

        switch (action) {
            case EINLAGERN_TEILEBESTAND:
                if (quellLb == null) {
                    quellLb = neuerLagerbestand(teil, help.getEinLagerfach(), help.getGrund(), help.getEineMenge());

                } else {
                    quellLb.setMenge(mengeOld + mengeAdd);
                }
                ZielPosition zp = new ZielPosition();
                zp.setMenge(mengeAdd);
                zp.setLagerfach(help.getEinLagerfach());
                zielPositionen.add(zp);
                break;

            case EINLAGERN_LAGERBESTAND:
                if (quellLb != null) {
                    quellLb.setMenge(mengeOld + mengeAdd);
                }
                ZielPosition zp1 = new ZielPosition();
                zp1.setMenge(mengeAdd);
                zp1.setLagerfach(help.getEinLagerfach());
                zielPositionen.add(zp1);
                break;

            case AUSLAGERN:
                quellLb.setMenge(mengeOld - mengeAdd);
                ZielPosition zp2 = new ZielPosition();
                zp2.setMenge(mengeAdd);
                zp2.setLagerfach(help.getEinLagerfach());
                zielPositionen.add(zp2);
                
                if (quellLb.getMenge() == 0 && Lagerbestand.isLastTeil(quellLb)) {
                    int option = JOptionPane.showConfirmDialog(this, "Soll das zugehörige Teil aus dem Teilebestand gelöscht werden?", "Zugehöriges Teil löschen?", JOptionPane.YES_NO_OPTION);

                    if (option == JOptionPane.YES_OPTION) {

                        Dao<Teilebestand, Integer> teilebestandDao = DatabaseManager.getInstance().getTeilebestandDao();
                        teilebestandDao.deleteById(help.getTeilID());
                        refreshTeilebestandTableModel();
                    }
                }
                break;
            case UMLAGERN:
            case SPLITTEN:
                int mengeSum = 0;
                ArrayList<HashMap> destinations = help.getDestinations();
                for(int i = 0; i<destinations.size(); i++){
                    HashMap hm = destinations.get(i);
                    int qty = Integer.parseInt((String)hm.get("qty"));  
                    Lagerfach fach = Lagerfach.getFach(
                            Lager.getLager(Lager.getLagerort((String)hm.get("fachCode"))), 
                            (int)hm.get("x"), 
                            (int)hm.get("y"), 
                            (int)hm.get("z")
                    );
                    Lagerbestand zielLb;
                    zielLb= Lagerbestand.getLagerbestand(help.getTeilID(), fach.getFachnummer());
                    if(zielLb == null){
                        zielLb = neuerLagerbestand(teil, fach, help.getGrund(), help.getEineMenge());
                    }
                    else {
                        zielLb.setMenge(zielLb.getMenge()+qty);
                    }
                    mengeSum += qty;
                    zielLb.save();
                    
                    ZielPosition ziel = new ZielPosition();
                    ziel.setLagerfach(fach);
                    ziel.setMenge(qty);
                    zielPositionen.add(ziel);
                }
                quellLb.setMenge(mengeOld-mengeSum);
                
                break;
        }
        
        if (quellLb != null) {
            quellLb.save();
            Warenbewegung wb = new Warenbewegung();
            wb.logWarenbewegung(quellLb, action, quellLb.getAnschaffungsgrund(),new Date(), help.getHbDatum(), "Lagerverwalter", 
                                zielPositionen);
        }
        //refreshWarenbestandTableModel();
        this.dispose();
    }
   
    
    /**
     * @author smodlich 
     * Erstellt einen neuen lagerbestand anhand der Eingabedaten
     * @param tb Teilebestand
     * @param lf Lagerfach
     * @param grund Grund für den neuen bestand
     * @param menge die Menge die der neue Bestand enthalten soll
     * @throws SQLException 
     */
    public Lagerbestand neuerLagerbestand(Teilebestand tb,Lagerfach lf,String grund, int menge) throws SQLException
    {
        Lagerbestand lb = new Lagerbestand();
        lb.setTeil(tb);
        lb.setLagerfach(lf);
        lb.setAnschaffungsgrund(grund);
        lb.setMenge(menge);
        lb.save();
        return lb;
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
    
    private void cbxFachXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFachXActionPerformed
        // TODO add your handling action here:
    }//GEN-LAST:event_cbxFachXActionPerformed

    private void txfTeilIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfTeilIDActionPerformed
        // TODO add your handling action here:
    }//GEN-LAST:event_txfTeilIDActionPerformed

    private void cbxFachTypActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFachTypActionPerformed
        refreshXYZ("quelle",cbxFachTyp.getSelectedItem().toString());
    }//GEN-LAST:event_cbxFachTypActionPerformed

    private void txfHaltbarkeitsdatumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfHaltbarkeitsdatumActionPerformed
        // TODO add your handling action here:
    }//GEN-LAST:event_txfHaltbarkeitsdatumActionPerformed
    
    public  void refreshXYZ(String id, String selectedItem){
        if(selectedItem.equals("HL")){
            try {
                loadLagerCbx(id,"HL");
            } catch (SQLException ex) {
                Misc.printSQLException(this, ex);            }
        }else{
            try {
                loadLagerCbx(id,"FL");
            } catch (SQLException ex) {
                Misc.printSQLException(this, ex);
            }
        }
    }
    
    private void cbxFachTyp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFachTyp1ActionPerformed
        refreshXYZ("dest",cbxFachTyp1.getSelectedItem().toString());
    }//GEN-LAST:event_cbxFachTyp1ActionPerformed

    private void cbxFachX1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFachX1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxFachX1ActionPerformed

    private void txfSplitAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txfSplitAmountKeyReleased
        if(txfSplitAmount.getText().length() > 0){
            btnAddSplitDest.setEnabled(true);
        }
        else {
            btnAddSplitDest.setEnabled(false);
        }
    }//GEN-LAST:event_txfSplitAmountKeyReleased

    private void btnAddSplitDestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSplitDestActionPerformed
        int amount = Integer.parseInt(txfSplitAmount.getText());
        this.cbX        = new JComboBox[amount];
        this.cbY        = new JComboBox[amount];
        this.cbZ        = new JComboBox[amount];
        this.cbLager    = new JComboBox[amount];
        this.qty        = new JTextField[amount];

        JPanel jp[] = new JPanel[amount];
        pnlDestinations.removeAll();
        for (int i = 0; i<amount; i++) {
            jp[i] = new JPanel();
            jp[i].setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
            JLabel preText = new JLabel("Ziel "+(i+1)+":");
            preText.setPreferredSize(new Dimension(57,23));
            
            JLabel qtyText = new JLabel("Menge: ");
            preText.setPreferredSize(new Dimension(57,23));
            
            cbLager[i] = new JComboBox();
            cbLager[i].setPreferredSize(new Dimension(57,23));
           
            cbX[i] = new JComboBox();
            cbX[i].setPreferredSize(new Dimension(57,23));
            
            cbY[i] = new JComboBox();
            cbY[i].setPreferredSize(new Dimension(57,23));    
            
            cbZ[i] = new JComboBox();
            cbZ[i].setPreferredSize(new Dimension(57,23)); 
            
            cbLager[i].addItemListener(new lagerActionListener("ziel"+i,cbX[i],cbY[i],cbZ[i], this));
            registerFaecherCbs("ziel"+i, cbX[i], cbY[i],cbZ[i]);
            
            cbLager[i].addItem("HL");
            cbLager[i].addItem("FL");
            try {
                loadLagerCbx("ziel"+i,"HL");
            } catch (SQLException ex) {
                Misc.printSQLException(this, ex);
            }
            
            qty[i] = new JTextField();
            qty[i].setPreferredSize(new Dimension(57,23));
           
            jp[i].add(preText);
            jp[i].add(cbLager[i]);
            jp[i].add(cbX[i]);
            jp[i].add(cbY[i]);
            jp[i].add(cbZ[i]);
            jp[i].add(qtyText);
            jp[i].add(qty[i]);
            jp[i].setBounds(5, 5+i*30, 454, 30);

            pnlDestinations.add(jp[i]);
            if(i == amount-1){
                break;
            }
        }
        pnlDestinations.setPreferredSize(new Dimension(454, amount*34));
        this.repaint();
    }//GEN-LAST:event_btnAddSplitDestActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting action (optional) ">
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
    
    public Lagerbestand getSelectedLagerbestand(){
        return this.selectedLagerbestand;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddSplitDest;
    private javax.swing.JComboBox cbxFachTyp;
    private javax.swing.JComboBox cbxFachTyp1;
    private javax.swing.JComboBox cbxFachX;
    private javax.swing.JComboBox cbxFachX1;
    private javax.swing.JComboBox cbxFachY;
    private javax.swing.JComboBox cbxFachY1;
    private javax.swing.JComboBox cbxFachZ;
    private javax.swing.JComboBox cbxFachZ1;
    private javax.swing.JButton einlagernButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAnschaffungsgrund;
    private javax.swing.JLabel lblEinlagern;
    private javax.swing.JLabel lblFachAdresse;
    private javax.swing.JLabel lblFachAdresse1;
    private javax.swing.JLabel lblHaltbarkeitsdatum;
    private javax.swing.JLabel lblHinweis;
    private javax.swing.JLabel lblHinweisDatum;
    private javax.swing.JLabel lblMenge;
    private javax.swing.JLabel lblTeilID;
    private javax.swing.JPanel pnlDestination;
    private javax.swing.JPanel pnlDestinations;
    private javax.swing.JPanel pnlQty;
    private javax.swing.JPanel pnlSplit;
    private javax.swing.JScrollPane pnlSplit2;
    private javax.swing.JScrollPane spnAnschaffungsgrund;
    private javax.swing.JTextArea txaAnschaffungsgrund;
    private javax.swing.JTextField txfHaltbarkeitsdatum;
    private javax.swing.JTextField txfMenge;
    private javax.swing.JTextField txfSplitAmount;
    private javax.swing.JTextField txfTeilID;
    // End of variables declaration//GEN-END:variables

}
