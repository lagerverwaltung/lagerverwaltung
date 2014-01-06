/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import helper.Misc;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Lager;
import model.Lager.Lagerort;
import model.Lagerfach;

/**
 *
 * @author simon
 */
public class InitFaecherPanel extends javax.swing.JPanel {
    MainFrame mainFrame;
    Boolean freilager = false;
    class FachGroesse{
        Lagerfach.Groesse groesse;
        int lagerfach;
        FachGroesse (Lagerfach.Groesse groesse, int lagerfach){
            this.groesse = groesse;
            this.lagerfach = lagerfach;
        }
        public String toString(){
            return groesse.toString();
        }
    }
    
    /**
     * Creates new form InitFaecherPanel
     */
    public InitFaecherPanel() {
        initComponents(); 
    }
    
    public void setMainFrame(MainFrame mainFrame)
    {
        this.mainFrame = mainFrame;
    }
    
    public void createFaecherPanel() {
        Lagerort lo;
        spFaecherPanel.removeAll();
        if (freilager){
            lo = Lager.Lagerort.freilager;
        }
        else {
            lo = Lager.Lagerort.hochregal;
        }
        Lager t;
        try {
            t = Lager.getLager(lo);
            
            int amount = t.getBreite()*t.getTiefe()*t.getHoehe();
            JLabel labelX[] = new JLabel[amount];
            JLabel labelY[] = new JLabel[amount];
            JLabel labelZ[] = new JLabel[amount];
            
            JComboBox cb[] = new JComboBox[amount];
            JPanel jp[] = new JPanel[amount];
            HashMap m[] = new HashMap[amount];
           // FachGroesse fg[] = new FachGroesse[amount];
            
            if(t != null){
                List<Lagerfach> list;
                list = t.getFaecher();
                int i = 0;
                for (Lagerfach f : list) {
                    //System.out.println("x"+f.getX()+" y:"+f.getY()+" z:"+f.getZ());
                    jp[i] = new JPanel();
                    jp[i].setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
                    labelX[i] = new JLabel();
                    labelX[i].setPreferredSize(new Dimension(57,20));
                    labelX[i].setText(String.valueOf(f.getX()));
                    jp[i].add(labelX[i]);
                    labelY[i] = new JLabel();
                    labelY[i].setText(String.valueOf(f.getY()));
                    labelY[i].setPreferredSize(new Dimension(57,20));
                    jp[i].add(labelY[i]);
                    labelZ[i] = new JLabel();
                    labelZ[i].setText(String.valueOf(f.getZ()));
                    labelZ[i].setPreferredSize(new Dimension(57,20));
                    jp[i].add(labelZ[i]);
                    jp[i].setBounds(10, 10+i*30, 304, 30);
                    FachGroesse fg[] = new FachGroesse[3];
                    fg[0] = new FachGroesse(Lagerfach.Groesse.klein,f.getFachnummer());
                    fg[1] = new FachGroesse(Lagerfach.Groesse.mittel,f.getFachnummer());
                    fg[2] = new FachGroesse(Lagerfach.Groesse.gross,f.getFachnummer());
                    cb[i] = new JComboBox(fg);
                    cb[i].addItemListener(new ItemListener(){
                          public void itemStateChanged(ItemEvent e){
                            FachGroesse c = (FachGroesse)e.getItem();
                            try {
                                Lagerfach lf = Lagerfach.getLagerfach(c.lagerfach);
                                lf.setGroesse(c.groesse);
                                lf.save();
                            } catch (SQLException ex) {
                                Misc.printSQLException(mainFrame, ex);
                            }
                        }
                    });
                    jp[i].add(cb[i]);
                    
                    spFaecherPanel.add(jp[i]);
                    if(i == amount-1){
                        break;
                    }
                    i++;
                    //System.out.println("i"+i+" amount"+amount);
                }
            }
            spFaecherPanel.setPreferredSize(new Dimension(307, amount*40+20));
        } 
        catch (SQLException ex) {
            Misc.printSQLException(mainFrame, ex);
        }
    }
    
    public void prepareFreilager()
    {
        freilager = true;
        lblTyp.setText("Freilager");
        btnWeiter.setText("Installation fertigstellen");
        lbl1Schritt.setText("2. Schritt");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblAuswahl = new javax.swing.JLabel();
        lblX = new javax.swing.JLabel();
        lblY = new javax.swing.JLabel();
        lblZ = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblTyp = new javax.swing.JLabel();
        lbl1Schritt = new javax.swing.JLabel();
        lblEinrichtungsassi = new javax.swing.JLabel();
        spFaecher = new javax.swing.JScrollPane();
        spFaecherPanel = new javax.swing.JPanel();
        btnWeiter = new javax.swing.JButton();
        lblCube2 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(664, 32767));
        setPreferredSize(new java.awt.Dimension(664, 733));

        lblAuswahl.setText("Wählen Sie nun die Fachgrößen für die einzelnen Fächer:");

        lblX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblX.setText("x");

        lblY.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblY.setText("y");

        lblZ.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblZ.setText("z");

        lblTyp.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblTyp.setText("Hochregallager");

        lbl1Schritt.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl1Schritt.setText("1. Schritt");

        lblEinrichtungsassi.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblEinrichtungsassi.setText("Ersteinrichtungsassistent");

        spFaecher.setBorder(null);
        spFaecher.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spFaecher.setMaximumSize(new java.awt.Dimension(1000767, 32767));

        javax.swing.GroupLayout spFaecherPanelLayout = new javax.swing.GroupLayout(spFaecherPanel);
        spFaecherPanel.setLayout(spFaecherPanelLayout);
        spFaecherPanelLayout.setHorizontalGroup(
            spFaecherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );
        spFaecherPanelLayout.setVerticalGroup(
            spFaecherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 574, Short.MAX_VALUE)
        );

        spFaecher.setViewportView(spFaecherPanel);

        btnWeiter.setText("weiter zum Freilager");
        btnWeiter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWeiterActionPerformed(evt);
            }
        });

        lblCube2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/lagerDimensions2.gif"))); // NOI18N
        lblCube2.setText("jLabel7");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEinrichtungsassi)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbl1Schritt)
                                .addGap(18, 18, 18)
                                .addComponent(lblTyp, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblAuswahl)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spFaecher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblCube2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnWeiter, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(lblX, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(lblY, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(lblZ, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblEinrichtungsassi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl1Schritt)
                    .addComponent(lblTyp))
                .addGap(18, 18, 18)
                .addComponent(lblAuswahl)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(lblCube2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 207, Short.MAX_VALUE)
                        .addComponent(btnWeiter)
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblX)
                            .addComponent(lblY)
                            .addComponent(lblZ))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(spFaecher, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnWeiterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWeiterActionPerformed
       if(freilager){
            mainFrame.navigationController.showCard("login");
        }   
        else {
            InitPanel initPanel = (InitPanel) mainFrame.getInitPanel();
            initPanel.prepareFreilager();
            mainFrame.navigationController.showCard("init");
        }
    }//GEN-LAST:event_btnWeiterActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnWeiter;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbl1Schritt;
    private javax.swing.JLabel lblAuswahl;
    private javax.swing.JLabel lblCube2;
    private javax.swing.JLabel lblEinrichtungsassi;
    private javax.swing.JLabel lblTyp;
    private javax.swing.JLabel lblX;
    private javax.swing.JLabel lblY;
    private javax.swing.JLabel lblZ;
    private javax.swing.JScrollPane spFaecher;
    private javax.swing.JPanel spFaecherPanel;
    // End of variables declaration//GEN-END:variables
}
