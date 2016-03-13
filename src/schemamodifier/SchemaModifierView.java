/*
 * SchemaModifierView.java
 */
package schemamodifier;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.DefaultListModel;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.Task;
import org.jdesktop.application.TaskService;

/**
 * The application's main frame.
 */
public class SchemaModifierView extends FrameView {

    File[] files;
    boolean isSelect = true;
    boolean isCreate = true;
    boolean isInsert = true;
    boolean isDrop = true;
    boolean isDelete = true;
    boolean isTruncate = true;
    boolean isAlter = true;
    boolean isUpdate = true;
    boolean isGrant = true;
    boolean isOverWrite = false;
    File schemaFile;
    String schemaData;
    String[] schemaList = null;
    DefaultListModel model;
    StringTokenizer tokens;
    String modifiedData = "";
    String[] modifiedToken;
    int modifiedTokenCounter;

    public SchemaModifierView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
        BufferedReader br = null;
        String currentLine;
        try {
            schemaFile = new File("schema.txt");
            br = new BufferedReader(new FileReader(schemaFile.getAbsolutePath()));
            while ((currentLine = br.readLine()) != null) {
                schemaData += (currentLine);
            }
            schemaList = schemaData.split(",");


            model = new DefaultListModel();
            for (int i = 1; i < schemaList.length; i++) {
                model.addElement(schemaList[i]);
            }

            jList1.setModel(model);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "CDC - Schema Adder", JOptionPane.ERROR_MESSAGE);

        } finally {
            try {
                if (br != null) {
                    br.close();
                }

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(null, ex.toString(), "CDC - Schema Adder", JOptionPane.ERROR_MESSAGE);

            }
        }
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = SchemaModifierApp.getApplication().getMainFrame();
            aboutBox = new SchemaModifierAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        SchemaModifierApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        jCheckBox11 = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jFileChooser1 = new javax.swing.JFileChooser();
        jOptionPane1 = new javax.swing.JOptionPane();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(410, 300));
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(schemamodifier.SchemaModifierApp.class).getContext().getResourceMap(SchemaModifierView.class);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jTextField1.setText(resourceMap.getString("jTextField1.text")); // NOI18N
        jTextField1.setToolTipText(resourceMap.getString("jTextField1.toolTipText")); // NOI18N
        jTextField1.setName("jTextField1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextField2.setText(resourceMap.getString("jTextField2.text")); // NOI18N
        jTextField2.setToolTipText(resourceMap.getString("jTextField2.toolTipText")); // NOI18N
        jTextField2.setName("jTextField2"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setToolTipText(resourceMap.getString("jButton2.toolTipText")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jCheckBox1.setToolTipText(resourceMap.getString("jCheckBox1.toolTipText")); // NOI18N
        jCheckBox1.setActionCommand(resourceMap.getString("jCheckBox1.actionCommand")); // NOI18N
        jCheckBox1.setLabel(resourceMap.getString("jCheckBox1.label")); // NOI18N
        jCheckBox1.setName("jCheckBox1"); // NOI18N
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setToolTipText(resourceMap.getString("jButton1.toolTipText")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(23, 23, 23)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(13, 13, 13)
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox1)))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 5, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 440, 90));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), resourceMap.getString("jPanel2.border.title"))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jTextField3.setText(resourceMap.getString("schema.text")); // NOI18N
        jTextField3.setToolTipText(resourceMap.getString("schema.toolTipText")); // NOI18N
        jTextField3.setName("schema"); // NOI18N
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jList1.setToolTipText(resourceMap.getString("schemaList.toolTipText")); // NOI18N
        jList1.setName("schemaList"); // NOI18N
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setToolTipText(resourceMap.getString("jButton5.toolTipText")); // NOI18N
        jButton5.setEnabled(false);
        jButton5.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton5.setName("jButton5"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainPanel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 220, 130));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), resourceMap.getString("jPanel3.border.title"))); // NOI18N
        jPanel3.setToolTipText(resourceMap.getString("jPanel3.toolTipText")); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jCheckBox3.setSelected(true);
        jCheckBox3.setText(resourceMap.getString("jCheckBox3.text")); // NOI18N
        jCheckBox3.setName("jCheckBox3"); // NOI18N
        jCheckBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox3ItemStateChanged(evt);
            }
        });
        jPanel3.add(jCheckBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jCheckBox5.setSelected(true);
        jCheckBox5.setText(resourceMap.getString("jCheckBox5.text")); // NOI18N
        jCheckBox5.setName("jCheckBox5"); // NOI18N
        jCheckBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox5ItemStateChanged(evt);
            }
        });
        jPanel3.add(jCheckBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jCheckBox8.setSelected(true);
        jCheckBox8.setText(resourceMap.getString("jCheckBox8.text")); // NOI18N
        jCheckBox8.setName("jCheckBox8"); // NOI18N
        jCheckBox8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox8ItemStateChanged(evt);
            }
        });
        jPanel3.add(jCheckBox8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jCheckBox9.setSelected(true);
        jCheckBox9.setText(resourceMap.getString("jCheckBox9.text")); // NOI18N
        jCheckBox9.setName("jCheckBox9"); // NOI18N
        jCheckBox9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox9ItemStateChanged(evt);
            }
        });
        jPanel3.add(jCheckBox9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jCheckBox4.setSelected(true);
        jCheckBox4.setText(resourceMap.getString("jCheckBox4.text")); // NOI18N
        jCheckBox4.setName("jCheckBox4"); // NOI18N
        jCheckBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox4ItemStateChanged(evt);
            }
        });
        jPanel3.add(jCheckBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        jCheckBox2.setSelected(true);
        jCheckBox2.setText(resourceMap.getString("jCheckBox2.text")); // NOI18N
        jCheckBox2.setActionCommand(resourceMap.getString("jCheckBox2.actionCommand")); // NOI18N
        jCheckBox2.setName("jCheckBox2"); // NOI18N
        jCheckBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox2ItemStateChanged(evt);
            }
        });
        jPanel3.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, -1, -1));

        jCheckBox6.setSelected(true);
        jCheckBox6.setText(resourceMap.getString("jCheckBox6.text")); // NOI18N
        jCheckBox6.setName("jCheckBox6"); // NOI18N
        jCheckBox6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox6ItemStateChanged(evt);
            }
        });
        jPanel3.add(jCheckBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, -1, -1));

        jCheckBox7.setSelected(true);
        jCheckBox7.setText(resourceMap.getString("jCheckBox7.text")); // NOI18N
        jCheckBox7.setName("jCheckBox7"); // NOI18N
        jCheckBox7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox7ItemStateChanged(evt);
            }
        });
        jPanel3.add(jCheckBox7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, -1, -1));

        jCheckBox10.setSelected(true);
        jCheckBox10.setText(resourceMap.getString("jCheckBox10.text")); // NOI18N
        jCheckBox10.setName("jCheckBox10"); // NOI18N
        jCheckBox10.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox10ItemStateChanged(evt);
            }
        });
        jPanel3.add(jCheckBox10, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, -1, -1));

        jCheckBox11.setFont(resourceMap.getFont("jCheckBox11.font")); // NOI18N
        jCheckBox11.setSelected(true);
        jCheckBox11.setText(resourceMap.getString("jCheckBox11.text")); // NOI18N
        jCheckBox11.setName("jCheckBox11"); // NOI18N
        jCheckBox11.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox11ItemStateChanged(evt);
            }
        });
        jCheckBox11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox11ActionPerformed(evt);
            }
        });
        jPanel3.add(jCheckBox11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        mainPanel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 210, 130));
        jPanel3.getAccessibleContext().setAccessibleName(resourceMap.getString("jPanel3.AccessibleContext.accessibleName")); // NOI18N

        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        mainPanel.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, 80, -1));

        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        mainPanel.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 250, 80, -1));

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(schemamodifier.SchemaModifierApp.class).getContext().getActionMap(SchemaModifierView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 289, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jFileChooser1.setName("jFileChooser1"); // NOI18N

        jOptionPane1.setName("jOptionPane1"); // NOI18N

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        jTextField3.setText(jList1.getSelectedValue().toString());

    // TODO add your handling code here:
    }//GEN-LAST:event_jList1ValueChanged

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged

        if (jCheckBox1.isSelected()) {
            isOverWrite = true;
            jTextField2.setEditable(false);
            jButton1.setEnabled(false);
        } else {
            isOverWrite = false;
            jTextField2.setEditable(true);
            jButton1.setEnabled(true);
        }
    // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void jCheckBox11ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox11ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox11ItemStateChanged

    private void jCheckBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox4ItemStateChanged

        if (!jCheckBox4.isSelected()) {
            jCheckBox11.setSelected(false);
        }
        isSelect = jCheckBox4.isSelected();
    // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox4ItemStateChanged

    private void jCheckBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox2ItemStateChanged
        if (!jCheckBox2.isSelected()) {
            jCheckBox11.setSelected(false);
        }

        isCreate = jCheckBox2.isSelected();
    // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ItemStateChanged

    private void jCheckBox6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox6ItemStateChanged
        if (!jCheckBox6.isSelected()) {
            jCheckBox11.setSelected(false);
        }
        isAlter = jCheckBox6.isSelected();
    // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox6ItemStateChanged

    private void jCheckBox7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox7ItemStateChanged

        if (!jCheckBox7.isSelected()) {
            jCheckBox11.setSelected(false);
        }
        isDelete = jCheckBox7.isSelected();
    // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox7ItemStateChanged

    private void jCheckBox10ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox10ItemStateChanged
        if (!jCheckBox10.isSelected()) {
            jCheckBox11.setSelected(false);
        }
        isTruncate = jCheckBox10.isSelected();
    // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox10ItemStateChanged

    private void jCheckBox9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox9ItemStateChanged

        if (!jCheckBox9.isSelected()) {
            jCheckBox11.setSelected(false);
        }
        isGrant = jCheckBox9.isSelected();
    // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox9ItemStateChanged

    private void jCheckBox8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox8ItemStateChanged
        if (!jCheckBox8.isSelected()) {
            jCheckBox11.setSelected(false);
        }
        isDrop = jCheckBox8.isSelected();
    // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox8ItemStateChanged

    private void jCheckBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox5ItemStateChanged
        if (!jCheckBox5.isSelected()) {
            jCheckBox11.setSelected(false);
        }
        isUpdate = jCheckBox5.isSelected();
    // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox5ItemStateChanged

    private void jCheckBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox3ItemStateChanged

        if (!jCheckBox3.isSelected()) {
            jCheckBox11.setSelected(false);
        }
        isInsert = jCheckBox3.isSelected();
    // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox3ItemStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        FileFilter filter = new FileNameExtensionFilter("SQL Files", new String[]{"sql", "prc", "vw", "pks", "pkb", "fnc"});
        jFileChooser1.setFileFilter(filter);
        jFileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser1.setMultiSelectionEnabled(true);
        int returnVal = jFileChooser1.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            files = jFileChooser1.getSelectedFiles();

            if (files.length == 1) {

                jTextField1.setText(files[0].getAbsolutePath());
            } else {

                String filesName = "";
                for (int i = 0; i < files.length; i++) {

                    filesName += files[i].getName() + ";";

                }
                jTextField1.setText(filesName);
            }

        } else {
            System.out.println("File access cancelled by user.");
        }
    // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jFileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser1.setMultiSelectionEnabled(false);
        int returnVal = jFileChooser1.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = jFileChooser1.getSelectedFile();
            jTextField2.setText(file.getAbsolutePath());
        } else {
            System.out.println("Directory access cancelled by user.");
        }

    // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        boolean isValidated = true;

        String validationMessage = "Kindly Fill the Following Fields: \n\n";
        if (jTextField1.getText().equals("") || jTextField1.getText() == null) {
            isValidated = false;
            validationMessage += "*Source Files\n";
        }
        if (!jCheckBox1.isSelected() && (jTextField2.getText().equals("") || jTextField2.getText() == null)) {
            isValidated = false;
            validationMessage += "*Destination Folder \n";
        }
        if ((jTextField3.getText().equals("") || jTextField3.getText() == null)) {
            isValidated = false;
            validationMessage += "*Schema Text \n";
        }

        if (!isSelect && !isAlter && !isCreate && !isDelete && !isDrop && !isGrant && !isInsert && !isTruncate && !isUpdate) {
            isValidated = false;
            validationMessage += "*Select Operation";

        }
        if (isValidated) {
            myTaskButtonAction();
        } else {

            JOptionPane.showMessageDialog(null, validationMessage, "CDC - Schema Adder", JOptionPane.ERROR_MESSAGE);
        }
// TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jCheckBox11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox11ActionPerformed
        // TODO add your handling code here:
        if (jCheckBox11.isSelected()) {

            jCheckBox2.setSelected(true);
            jCheckBox3.setSelected(true);
            jCheckBox4.setSelected(true);
            jCheckBox5.setSelected(true);
            jCheckBox6.setSelected(true);
            jCheckBox7.setSelected(true);
            jCheckBox8.setSelected(true);
            jCheckBox9.setSelected(true);
            jCheckBox10.setSelected(true);
            isSelect =
                    true;
            isCreate =
                    true;
            isInsert =
                    true;
            isDrop =
                    true;
            isDelete =
                    true;
            isTruncate =
                    true;
            isAlter =
                    true;
            isUpdate =
                    true;
            isGrant =
                    true;
        } else {
            jCheckBox2.setSelected(false);
            jCheckBox3.setSelected(false);
            jCheckBox4.setSelected(false);
            jCheckBox5.setSelected(false);
            jCheckBox6.setSelected(false);
            jCheckBox7.setSelected(false);
            jCheckBox8.setSelected(false);
            jCheckBox9.setSelected(false);
            jCheckBox10.setSelected(false);
            isSelect =
                    false;
            isCreate =
                    false;
            isInsert =
                    false;
            isDrop =
                    false;
            isDelete =
                    false;
            isTruncate =
                    false;
            isAlter =
                    false;
            isUpdate =
                    false;
            isGrant =
                    false;
        }
    }//GEN-LAST:event_jCheckBox11ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (!jTextField3.getText().equals("") || jTextField3.getText() != null) {

            for (int i = 0; i < schemaList.length; i++) {
                if (jTextField3.getText().equals(schemaList[i])) {
                    JOptionPane.showMessageDialog(null, "Schema is already in list", "CDC - Schema Adder", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            FileWriter fileWriter = null;
            try {

                fileWriter = new FileWriter(schemaFile, true);
                // true to append
                fileWriter.write("," + jTextField3.getText().trim());
                model.addElement(jTextField3.getText().trim());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "CDC - Schema Adder", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (fileWriter != null) {
                        fileWriter.close();
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString(), "CDC - Schema Adder", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        // TODO add your handling code here:
        if (jTextField3.getText().trim().equals("") || jTextField3.getText() == null) {

            jButton5.setEnabled(false);
        } else {
            jButton5.setEnabled(true);
        }
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jCheckBox11.setSelected(true);
        jCheckBox2.setSelected(true);
        jCheckBox3.setSelected(true);
        jCheckBox4.setSelected(true);
        jCheckBox5.setSelected(true);
        jCheckBox6.setSelected(true);
        jCheckBox7.setSelected(true);
        jCheckBox8.setSelected(true);
        jCheckBox9.setSelected(true);
        jCheckBox10.setSelected(true);
        jButton5.setEnabled(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    @Action
    public void myTaskButtonAction() { // this action is called from a menu item or a button
        startMyTaskAction();
    }

    @Action
    public Task startMyTaskAction() { // this sets up the Task and TaskMonitor
        StartMyTask task = new StartMyTask(org.jdesktop.application.Application.getInstance());

        ApplicationContext C = getApplication().getContext();
        TaskMonitor M = C.getTaskMonitor();
        TaskService S = C.getTaskService();
        S.execute(task);
        M.setForegroundTask(task);

        return task;
    }

    private String getToken(int index, int position) {
        int _position = 0;
        if (position > 0) {
            for (int i = index; i < modifiedToken.length; i++) {
                if (!modifiedToken[i].equalsIgnoreCase("{#NL#}") && !modifiedToken[i].equalsIgnoreCase("{#SP#}")) {
                    if (_position == position) {
                        return modifiedToken[i];
                    } else {
                        _position++;
                    }
                }
            }
        } else {

            for (int i = index; i > 0; i--) {
                if (!modifiedToken[i].equalsIgnoreCase("{#NL#}") && !modifiedToken[i].equalsIgnoreCase("{#SP#}")) {
                    if (_position == position) {
                        return modifiedToken[i];
                    } else {
                        _position--;
                    }
                }
            }

        }
        return "";
    }

    private String getSetToken() {
        
        
        for (int i = modifiedTokenCounter; i < modifiedToken.length; i++, modifiedTokenCounter++) {
            if (modifiedToken[i] != null) {
                if (modifiedToken[i].equalsIgnoreCase("{#NL#}")) {
                    System.out.println("new line");
                    modifiedData += "\n";
                } else if (modifiedToken[i].equalsIgnoreCase("{#SP#}")) {
                    System.out.println("Space");
                    modifiedData += " ";
                } else {
                    return modifiedToken[i];
                }
            }
        }
        return "";
    }

    private String getNextToken() {
        String data = "";

        while (tokens.hasMoreTokens()) {
            data = tokens.nextToken();
            if (data.equalsIgnoreCase("{#NL#}")) {
                System.out.println("new line");
                modifiedData += "\n";
            } else if (data.equalsIgnoreCase("{#SP#}")) {
                System.out.println("Space");
                modifiedData += " ";
            } else {
                return data;
            }

        }
        return " ";
    }

    private class StartMyTask extends Task<Void, Void> { // this is the Task

        StartMyTask(org.jdesktop.application.Application app) {
            super(app);
        }

        @Override
        protected Void doInBackground() {
            try {
                // specific code for your task
                // this code shows progress bar with status message for a few seconds
                setMessage("Starting Up");// status message

                String ResultMessage = "";
                String modifier = jTextField3.getText().trim() + ".";
                FileWriter fileWriter = null;
                BufferedReader br = null;
                int calcProgress = 100 / files.length;
                int progress = calcProgress;
                int totalChangesCounter = 0;
                for (int i = 0; i < files.length; i++) {

                    modifiedData = "";
                    fileWriter = null;
                    br = null;
                    String data = "";

                    int changesCounter = 0;

                    //   System.out.println("File info: " + files[i].getName() + ", " + i + ", " + progress);
                    try {

                        String sCurrentLine;

                        br = new BufferedReader(new FileReader(files[i].getAbsolutePath()));


                        while ((sCurrentLine = br.readLine()) != null) {
                            data += (sCurrentLine) + " {#NL#} ";
                        }
                        data = data.replace(" ", " {#SP#} ");
                        data = data.replace("{#SP#} {#NL#} {#SP#}", "{#NL#}");
                        data = data.replace(";", " ; ");
                        data = data.replace(",", " , ");
                        data = data.replace("(", " ( ");
                        data = data.replace(")", " ) ");

                        System.out.println(data);

                        tokens = new StringTokenizer(data);
                        String token = "";
                        String preToken = "";


                        String extension = "";

                        int k = files[i].getName().lastIndexOf('.');
                        if (k > 0) {
                            extension = files[i].getName().substring(k + 1);
                        }

                        if (extension.equalsIgnoreCase("prc")) {
                            while (tokens.hasMoreTokens()) {

                                token = getNextToken();
                                if ((token.equalsIgnoreCase("procedure")) && ((preToken.equalsIgnoreCase("drop") && isDrop) || (preToken.equalsIgnoreCase("create") && isCreate) || (preToken.equalsIgnoreCase("replace") && isCreate))) {
                                    changesCounter++;
                                    modifiedData += token + " " + modifier + getNextToken();

                                } else {
                                    modifiedData += token;

                                }

                                preToken = token;

                            }

                        } else if (extension.equalsIgnoreCase("fnc")) {

                            while (tokens.hasMoreTokens()) {

                                token = getNextToken();
                                if ((token.equalsIgnoreCase("function")) && ((preToken.equalsIgnoreCase("drop") && isDrop) || (preToken.equalsIgnoreCase("create") && isCreate) || (preToken.equalsIgnoreCase("replace") && isCreate))) {
                                    changesCounter++;
                                    modifiedData += token + " " + modifier + getNextToken();

                                } else {
                                    modifiedData += token;

                                }

                                preToken = token;

                            }

                        } else if (extension.equalsIgnoreCase("vw")) {

                            while (tokens.hasMoreTokens()) {

                                token = getNextToken();
                                if ((token.equalsIgnoreCase("view")) && ((preToken.equalsIgnoreCase("drop") && isDrop) || (preToken.equalsIgnoreCase("create") && isCreate) || (preToken.equalsIgnoreCase("replace") && isCreate))) {
                                    changesCounter++;
                                    modifiedData += token + " " + modifier + getNextToken();

                                } else {
                                    modifiedData += token;

                                }

                                preToken = token;

                            }


                        } else if (extension.equalsIgnoreCase("pkb")) {


                            while (tokens.hasMoreTokens()) {

                                token = getNextToken();
                                if (((token.equalsIgnoreCase("package")) && ((preToken.equalsIgnoreCase("drop") && isDrop) || (preToken.equalsIgnoreCase("create") && isCreate) || (preToken.equalsIgnoreCase("replace") && isCreate))) || token.equalsIgnoreCase("procedure")) {
                                    changesCounter++;
                                    modifiedData += token + " " + modifier + getNextToken();

                                } else {
                                    modifiedData += token;

                                }
                                preToken = token;

                            }


                        } else if (extension.equalsIgnoreCase("sql")) {
                            modifiedToken = new String[tokens.countTokens() + 4];
                            int counter = 2;
                            while (tokens.hasMoreTokens()) {
                                modifiedToken[counter++] = tokens.nextToken();
                            }


                            for (modifiedTokenCounter = 1; modifiedTokenCounter < modifiedToken.length - 2; modifiedTokenCounter++) {
                                preToken = token;
                                token = getSetToken();

                                System.out.println("Token: " + token);
                                System.out.println("Pre Token: " + preToken);

                                if (((token.equalsIgnoreCase("view") || token.equalsIgnoreCase("procedure") || token.equalsIgnoreCase("function")) && preToken.equalsIgnoreCase("drop") && isDrop) || ((token.equalsIgnoreCase("table")) && ((preToken.equalsIgnoreCase("drop") && isDrop) || (preToken.equalsIgnoreCase("create") && isCreate) || (preToken.equalsIgnoreCase("alter") && isAlter) || (preToken.equalsIgnoreCase("truncate") && isTruncate))) || (token.equalsIgnoreCase("update") && isUpdate) || (isSelect && token.equalsIgnoreCase("from") && !preToken.equalsIgnoreCase("delete") && !getToken(modifiedTokenCounter, 1).contains("(")) || (token.equalsIgnoreCase("from") && preToken.equalsIgnoreCase("delete") && isDelete) || (isSelect && token.equals(",") && (getToken(modifiedTokenCounter, -1).contains(modifier) || getToken(modifiedTokenCounter, -2).contains(modifier))) || (token.equalsIgnoreCase("into") && preToken.equalsIgnoreCase("insert") && isInsert)) {
                                    changesCounter++;
                                    modifiedTokenCounter++;
                                    modifiedData += token + " " + modifier + getSetToken();
                                    modifiedToken[modifiedTokenCounter] = modifier + modifiedToken[modifiedTokenCounter];
                                    


                                } else {
                                    modifiedData += token;



                                }

                            }

                        }


                        // modifiedData = modifiedData.replace(" ; ", ";");
                        //  modifiedData = modifiedData.replace(" , ", ",");
                        //  modifiedData = modifiedData.replace(" ) ", ")");
                        //  modifiedData = modifiedData.replace(" ( ", "(");
                        modifiedData = modifiedData.trim();
                        System.out.println(modifiedData);
                        if (br != null) {
                            br.close();
                        }
                        if (isOverWrite) {
                            fileWriter = new FileWriter(files[i], false); // true to append
                        } else {
                            fileWriter = new FileWriter(new File(jTextField2.getText() + "\\" + files[i].getName()), false); // true to append
                        }
                        // false to overwrite.
                        fileWriter.write(modifiedData);
                        totalChangesCounter += changesCounter;
                        ResultMessage += i + 1 + ") " + files[i].getName() + " ------ " + changesCounter + " changes\n";
                    //  System.out.print("Result: " + modifiedData);

                    } catch (IOException e) {

                        ResultMessage += i + 1 + ") " + files[i].getName() + " ------ " + e.toString() + " \n";
                    } finally {
                        try {
                            if (br != null) {
                                br.close();
                            }
                            if (fileWriter != null) {
                                fileWriter.close();
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    setProgress(progress); // progress bar (0-100)
                    setMessage("Progress: " + progress); // status message
                    progress += calcProgress;
                }
                setProgress(100); // progress bar (0-100)
                setMessage("Progress: " + 100); // status message
                JOptionPane.showMessageDialog(null, "Modifier Successfully Added!\nTotal Files Modified: " + files.length + "\nTotal Modifications Made: " + totalChangesCounter, "CDC - Schema Adder", JOptionPane.INFORMATION_MESSAGE);
                setMessage("Done");// status message
            } catch (java.lang.Exception e) {
                JOptionPane.showMessageDialog(null, e.toString(), "CDC - Schema Adder", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

            return null;
        }

        protected void succeeded() {
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList jList1;
    private javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
}
