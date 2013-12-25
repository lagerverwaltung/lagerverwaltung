/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;


/**
 *
 * @author smodlich
 */
public class DeprecatedBSTFrame {
    
    Boolean einlagern = false;
    Boolean auslagern = false;
    Boolean splitten = false;
    Boolean bestehenderLagerbestand=false;
    int lagerbestandID;
    int fachid;
    int teilid;
   /**
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
    */
    
    
    
    /*
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
    */
    
    /*
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
    */
}
