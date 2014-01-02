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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
        Lagerbestand lb= Lagerbestand.getLagerbestand(Lagerbestand.getLagerbestandID(teilID, fachID));
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
    
    private void setGUI(int action,Lagerbestand lb)
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
            this.cbxFachTyp.setEnabled(false);
            this.cbxFachX.setEnabled(false);
            this.cbxFachY.setEnabled(false);
            this.cbxFachZ.setEnabled(false);
            
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
            
            
            this.txaAnschaffungsgrund.setEditable(true);
            this.txfHaltbarkeitsdatum.setVisible(false);
            this.lblHaltbarkeitsdatum.setVisible(false);
            
            loadQuellComboBoxen(lb);
            
            this.txaAnschaffungsgrund.setText(lb.getAnschaffungsgrund()); 
            
            this.lblHinweisDatum.setVisible(false);
            this.setVisible(true);
    
    }
    /**
     * Erstellen der Umlagern GUI und setzen der entsprechenden Elemente
     * 
     */
    private void setUmlagernGUI(Lagerbestand lb)
    {
    
    
    }
    /**
     * Erstellen der Splitten GUI und setzen der entsprechenden Elemente
     * 
     */
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this action. The content of this method is always
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

    private void einlagernButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_einlagernButtonActionPerformed
        try {
            if (action != SPLITTEN) {
                String[] menge = {txfMenge.getText()};
                HashMap<Integer, String> errors = bestandsGuiHelper.validateLagerbestandData(action, menge, txfHaltbarkeitsdatum.getText(),
                        txaAnschaffungsgrund.getText());

                try {
                    Lagerfach[] faecher = new Lagerfach[1];
                    faecher[0] = bestandsGuiHelper.generateFach((int) cbxFachX.getSelectedItem(), (int) cbxFachY.getSelectedItem(), (int) cbxFachZ.getSelectedItem(), cbxFachTyp.getSelectedItem().toString());
                    bestandsGuiHelper.setFaecher(faecher);

                    if (action == EINLAGERN_TEILEBESTAND) {
                        bestandsGuiHelper.setQuellFachID(bestandsGuiHelper.getEinLagerfach().getFachnummer());
                    }

                } catch (Exception ex) {
                    Misc.createErrorDialog(this, "Fehler bei der Fach Generierung");
                    return;
                }
                if (errors.size() > 0) {
                    Misc.createErrorDialog(this, errors);
                    return;
                }
            } // Splitten ergänzen
            else {
            }

            bestandsAenderung(action, bestandsGuiHelper);
        } catch (SQLException ex) {
            System.out.println("SQL Exception");
        }

        refreshLagerbestandTableModel();
        /*refreshTeilebestandTableModel();
         refreshWarenbestandTableModel();*/
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
        Lagerbestand quellLb= Lagerbestand.getLagerbestand(help.getTeilID(), help.getquellFachID());
        ArrayList zielPositionen = new ArrayList();
        int mengeOld = 0;

        if (quellLb != null) {
            mengeOld = quellLb.getMenge();
        }

        Teilebestand teil = Teilebestand.loadTeil(help.getTeilID());
        if (action == AUSLAGERN || action == UMLAGERN || action == SPLITTEN) {
            help.getMengen()[0] = -help.getMengen()[0];
        }

        HashMap<Integer, String> errors = kapazitaetsTest(help.getFaecher(), teil, help.getMengen(), mengeOld);

        if (errors.size() > 0) {
            Misc.createErrorDialog(this, errors);
            return;
        }

        switch (action) {
            case EINLAGERN_TEILEBESTAND:
                if (quellLb == null) {
                    quellLb = neuerLagerbestand(teil, help.getEinLagerfach(), help.getGrund(), help.getEineMenge());

                } else {
                    quellLb.setMenge(mengeOld + help.getMengen()[0]);
                }
                ZielPosition zp = new ZielPosition();
                zp.setMenge(help.getMengen()[0]);
                zp.setLagerfach(help.getEinLagerfach());
                zielPositionen.add(zp);
                break;

            case EINLAGERN_LAGERBESTAND:
                if (quellLb != null) {
                    quellLb.setMenge(mengeOld + help.getEineMenge());
                }
                ZielPosition zp1 = new ZielPosition();
                zp1.setMenge(help.getMengen()[0]);
                zp1.setLagerfach(help.getEinLagerfach());
                zielPositionen.add(zp1);
                break;

            case AUSLAGERN:
                quellLb.setMenge(mengeOld + help.getEineMenge());
                ZielPosition zp2 = new ZielPosition();
                zp2.setMenge(help.getMengen()[0]);
                zp2.setLagerfach(help.getEinLagerfach());
                zielPositionen.add(zp2);
                
                if (quellLb.getMenge() == 0 && Lagerbestand.isLastTeil(quellLb, quellLb.getMenge())) {
                    int option = JOptionPane.showConfirmDialog(this, "Soll das zugehörige Teil aus dem Teilebestand gelöscht werden?", "Zugehöriges Teil löschen?", JOptionPane.YES_NO_OPTION);

                    if (option == JOptionPane.YES_OPTION) {

                        Dao<Teilebestand, Integer> teilebestandDao = DatabaseManager.getInstance().getTeilebestandDao();
                        teilebestandDao.deleteById(help.getTeilID());
                        refreshTeilebestandTableModel();
                    }
                }
                break;
            case UMLAGERN:
                break;
            case SPLITTEN:
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
     * generischer Kapazitätstest für alle Bestandsaenderungen
     * @param faecher Faecher Array ausgelesen aus BestandsGUIHelper Objekt
     * @param teil Teilebestand
     * @param mengen
     * @param mengeOld
     * @return
     * @throws SQLException 
     */
    public HashMap<Integer,String> kapazitaetsTest(Lagerfach[] faecher,Teilebestand teil,int[] mengen,int mengeOld) throws SQLException
    {
        HashMap<Integer,String> errors = new HashMap<Integer,String>();
        int groesse= teil.getVe();
        
        int errorIndex=0;
        
        for(int i=0;i<faecher.length;i++)
        {
           int freeVE=faecher[i].getFreeVe();
           
           if((mengen[i]*groesse)>freeVE)
           {
               errorIndex++;
               errors.put(errorIndex,"Die Kapazitaet im Fach X:"+ faecher[i].getX()+" Y:"+faecher[i].getY() +" Z:"+ 
                       faecher[i].getZ()+ " ist nicht ausreichend. Es sind noch " + freeVE + " VE frei.Aber es werden " + 
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
        // TODO add your handling action here:
    }//GEN-LAST:event_cbxFachXActionPerformed

    private void txfTeilIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfTeilIDActionPerformed
        // TODO add your handling action here:
    }//GEN-LAST:event_txfTeilIDActionPerformed

    private void cbxFachTypActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFachTypActionPerformed
        if(cbxFachTyp.getSelectedItem().equals("HL")){
            try {
                loadHlCbx();
            } catch (SQLException ex) {
                Misc.printSQLException(this, ex);            }
        }else{
            try {
                loadFlCbx();
            } catch (SQLException ex) {
                Misc.printSQLException(this, ex);
            }
        }
    }//GEN-LAST:event_cbxFachTypActionPerformed

    private void txfHaltbarkeitsdatumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfHaltbarkeitsdatumActionPerformed
        // TODO add your handling action here:
    }//GEN-LAST:event_txfHaltbarkeitsdatumActionPerformed

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
