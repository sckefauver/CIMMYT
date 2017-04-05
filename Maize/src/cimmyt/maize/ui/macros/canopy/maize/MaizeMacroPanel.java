package cimmyt.maize.ui.macros.canopy.maize;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.BadLocationException;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import cimmyt.maize.ui.MaizeFrame;
import cimmyt.maize.ui.macros.MacroVars;
import cimmyt.maize.ui.tools.FileOpen;
import cimmyt.maize.ui.tools.FileSave;
import ij.IJ;
import layout.TableLayout;

/**
 * 
 * @author George El Haddad (george.dma@gmail.com)
 * <p>
 * Created on: May 26, 2015
 *
 */
public class MaizeMacroPanel extends JPanel {

        private static final long serialVersionUID = -9141490313177003568L;
        
        private JLabel batchInputLabel = null;
        private JTextField batchInputField = null;
        private JButton batchInputButton = null;
        
        private JCheckBox saveImagesCheckBox = null;
        private JTextField saveImagesField = null;
        private JButton saveImagesButton = null;
        
        private JLabel resultsFileLabel = null;
        private JTextField resultsFileField = null;
        private JButton resultsFileButton = null;
        
        private JPanel optionsPanel = null;
        private JTabbedPane macroTab = null;
        private int vegTabIndex = 1;
        
        private JPanel buttonPanel = null;
        private JButton runButton = null;
        private JProgressBar progressBar = null;
        
        private String recentDir = null;
        private File batchInputDir = null;
        private File saveImagesDir = null;
        private File saveResultsFile = null;
        
        private HashMap<String, MaizeMacroVars> macroMap = null;
        
        public MaizeMacroPanel() {
                macroMap = new HashMap<String, MaizeMacroVars>(7);
                
                batchInputLabel = new JLabel("Batch Inputs:");
                batchInputLabel.setHorizontalAlignment(JLabel.RIGHT);
                
                batchInputField = new JTextField(20);
                batchInputField.setEditable(false);
                batchInputField.setBackground(Color.WHITE);
                
                batchInputButton = new JButton("...");
                batchInputButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                batchInputButton_actionPerformed();
                        }
                });
                
                //----------------------------------------------------------------
                
                saveImagesCheckBox = new JCheckBox("Save HSB Images?", false);
                saveImagesCheckBox.setFocusable(false);
                saveImagesCheckBox.setSelected(false);
                saveImagesCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveImagesCheckBox_actionPerformed();
                        }
                });
                
                saveImagesField = new JTextField(20);
                saveImagesField.setEditable(false);
                saveImagesField.setBackground(null);
                
                saveImagesButton = new JButton("...");
                saveImagesButton.setEnabled(false);
                saveImagesButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveHsbImagesButton_actionPerformed();
                        }
                });
                
                //----------------------------------------------------------------
                
                resultsFileLabel = new JLabel("Results File:");
                resultsFileLabel.setHorizontalAlignment(JLabel.RIGHT);
                
                resultsFileField = new JTextField(20);
                resultsFileField.setEditable(false);
                resultsFileField.setBackground(Color.WHITE);
                
                resultsFileButton = new JButton("...");
                resultsFileButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                resultsFileButton_actionPerformed();  
                        }
                });
                
                //----------------------------------------------------------------
                
                macroTab = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
                addMacroPanelTab("Default", "Maize_CIMMYT_QuanitfyImageComponentsHSB_UBv8.ijm", false, false);
                
                macroTab.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                                tabPanel_stateChanged(e);
                        }
                });
                
                //----------------------------------------------------------------
                
                double spacer = 5;
                double[][] layoutSize = {
                                //                   0,                        2,                            4
                                {TableLayout.PREFERRED, spacer, TableLayout.FILL, spacer, TableLayout.PREFERRED},
                                {
                                 TableLayout.PREFERRED, //0
                                 spacer,
                                 TableLayout.PREFERRED, //2
                                 spacer,
                                 TableLayout.PREFERRED  //4
                                }
                };
                
                optionsPanel = new JPanel();
                optionsPanel.setBorder(BorderFactory.createTitledBorder("Macro Options"));
                optionsPanel.setLayout(new TableLayout(layoutSize));
                optionsPanel.add(batchInputLabel,      "0, 0");
                optionsPanel.add(batchInputField,      "2, 0");
                optionsPanel.add(batchInputButton,     "4, 0");
                optionsPanel.add(saveImagesCheckBox,   "0, 2");
                optionsPanel.add(saveImagesField,      "2, 2");
                optionsPanel.add(saveImagesButton,     "4, 2");
                optionsPanel.add(resultsFileLabel,     "0, 4");
                optionsPanel.add(resultsFileField,     "2, 4");
                optionsPanel.add(resultsFileButton,    "4, 4");
                
                //----------------------------------------------------------------
                
                runButton = new JButton("Run Macro");
                runButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveImagesCheckBox_actionPerformed();
                                runButton_actionPerformed();
                        }
                });
                
                progressBar = new JProgressBar(JProgressBar.HORIZONTAL);
                progressBar.setStringPainted(true);
                progressBar.setString("Ready");
                progressBar.setIndeterminate(false);
                
                buttonPanel = new JPanel(new BorderLayout(5, 5));
                buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                buttonPanel.add(runButton, BorderLayout.EAST);
                buttonPanel.add(progressBar, BorderLayout.CENTER);
                
                // ---------------------------------------------------
                
                setLayout(new BorderLayout(5, 5));
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                add(optionsPanel, BorderLayout.NORTH);
                add(macroTab, BorderLayout.CENTER);
                add(buttonPanel, BorderLayout.SOUTH);
        }
        
        private final void runButton_actionPerformed() {
                if(MaizeFrame.isMacroRunning()) {
                        JOptionPane.showMessageDialog(this, "A macro is already running, please wait until it finishes.", "Macro Already Running", JOptionPane.INFORMATION_MESSAGE);
                        return;
                }
                
                int index = macroTab.getSelectedIndex();
                MaizeMacroVars macroVar = macroMap.get("" + index);
                
                if(macroVar.getBatchInputVar() == null) {
                        JOptionPane.showMessageDialog(this, "Please select the batch inputs folder.", "Select Batch Inputs", JOptionPane.INFORMATION_MESSAGE);
                        return;
                }
                
                if(saveImagesCheckBox.isSelected()) {
                        if(macroVar.getSaveImagesDirVar() == null) {
                                JOptionPane.showMessageDialog(this, "Please select a folder to save images.", "Select Images Folder", JOptionPane.INFORMATION_MESSAGE);
                                return;
                        }
                }
                
                if(macroVar.getSaveResultsFileVar() == null) {
                        JOptionPane.showMessageDialog(this, "Please select a results file location.", "Select Results File", JOptionPane.INFORMATION_MESSAGE);
                        return;
                }
                
                Thread macroThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                                EventQueue.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                                runButton.setEnabled(false);
                                                progressBar.setIndeterminate(true);
                                                progressBar.setString("Running Macro ...");
                                                MaizeFrame.setMacroRunning(true);
                                        }
                                });
                                
                                int index = macroTab.getSelectedIndex();
                                MacroVars macroVar = macroMap.get("" + index);
                                String macroCode = macroVar.getMacroCode();
                                IJ.runMacro(macroCode);
                                
                                EventQueue.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                                progressBar.setIndeterminate(false);
                                                progressBar.setString("Ready");
                                                runButton.setEnabled(true);
                                                MaizeFrame.setMacroRunning(false);
                                        }
                                });
                        }
                });
                macroThread.start();
        }
        
        private final void batchInputButton_actionPerformed() {
                batchInputDir = FileOpen.getFile("Select batch input folder", (recentDir == null ? System.getProperty("user.dir") : recentDir), JFileChooser.DIRECTORIES_ONLY, "Batch Image Folder", (String[]) null);
                if (batchInputDir != null) {
                        recentDir = batchInputDir.getAbsolutePath();
                        batchInputField.setText(batchInputDir.getAbsolutePath());

                        int index = macroTab.getSelectedIndex();
                        MaizeMacroVars macroVar = macroMap.get("" + index);
                        macroVar.setBatchInputVar(batchInputDir.getAbsolutePath());
                }
        }
        
        private final void saveImagesCheckBox_actionPerformed() {
                saveImagesButton.setEnabled(saveImagesCheckBox.isSelected());
                String selection = null;
                if(saveImagesCheckBox.isSelected()) {
                        saveImagesField.setBackground(Color.WHITE);
                        selection = "true";
                }
                else {
                        saveImagesField.setBackground(null);
                        selection = "false";
                }
                
                int index = macroTab.getSelectedIndex();
                MaizeMacroVars macroVar = macroMap.get(""+index);
                macroVar.setSaveImagesVar(selection);
        }
        
        private final void saveHsbImagesButton_actionPerformed() {
                saveImagesDir = FileOpen.getFile("Select HSB images folder", (recentDir == null ? System.getProperty("user.dir") : recentDir), JFileChooser.DIRECTORIES_ONLY , "HSB Images Folder", (String[])null);
                if(saveImagesDir != null) {
                        recentDir = saveImagesDir.getAbsolutePath();
                        saveImagesField.setText(saveImagesDir.getAbsolutePath());
                        
                        int index = macroTab.getSelectedIndex();
                        MaizeMacroVars macroVar = macroMap.get(""+index);
                        macroVar.setSaveImagesDirVar(saveImagesDir.getAbsolutePath());
                }
        }
        
        private final void resultsFileButton_actionPerformed() {
                saveResultsFile = FileSave.saveFile("Name Results File", (recentDir == null ? new File(System.getProperty("user.dir")) : new File(recentDir)), "Results File (*.csv)", "Maize_Results.csv");
                if(saveResultsFile != null) {
                        recentDir = saveResultsFile.getParentFile().getAbsolutePath();
                        
                        if(!saveResultsFile.getName().endsWith(".csv")) {
                                String tmp = saveResultsFile.getAbsolutePath();
                                saveResultsFile = new File(tmp+".csv");
                        }
                        
                        resultsFileField.setText(saveResultsFile.getAbsolutePath());
                        
                        int index = macroTab.getSelectedIndex();
                        MaizeMacroVars macroVar = macroMap.get(""+index);
                        macroVar.setSaveResultsFileVar(saveResultsFile.getAbsolutePath());
                }
        }
        
        private final void addMacroPanelTab(String tabName, String macroTemplateName, boolean editable, boolean isTemplate) {
                InputStream macroInputStream = MaizeMacroPanel.class.getResourceAsStream("/cimmyt/maize/ui/macros/canopy/maize/"+macroTemplateName);
                if(macroInputStream != null) {
                        RSyntaxTextArea syntaxTextArea = new RSyntaxTextArea(20, 60);
                        syntaxTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
                        syntaxTextArea.setCodeFoldingEnabled(true);
                        
                        String line = null;
                        BufferedReader br = null;
                        StringBuilder sb = null;
                        
                        try {
                                sb = new StringBuilder();
                                br = new BufferedReader(new InputStreamReader(macroInputStream));
                                while((line = br.readLine()) != null) {
                                        sb.append(line).append('\n');
                                }
                                
                                syntaxTextArea.setText(sb.toString());
                                syntaxTextArea.setEditable(editable);
                                RTextScrollPane syntaxScrollPane = new RTextScrollPane(syntaxTextArea);
                                
                                macroTab.addTab(tabName, syntaxScrollPane);
                                
                                int tabIndex = macroTab.getTabCount();
                                if(tabIndex > 0) {
                                        tabIndex--;
                                }
                                
                                macroTab.setTabComponentAt(tabIndex, new CustomTabPanel(tabName));
                                
                                MaizeMacroVars macroVar = new MaizeMacroVars();
                                macroVar.setMacroNameKey(""+tabIndex);
                                macroVar.setMacroName(tabName);
                                macroVar.setSyntaxTextArea(syntaxTextArea);
                                macroMap.put(""+tabIndex, macroVar);
                                
                                int carPos = 0;
                                if(isTemplate) {
                                        try {
                                                syntaxTextArea.addLineHighlight(35, Color.GREEN);
                                                syntaxTextArea.addLineHighlight(36, Color.GREEN);
                                                syntaxTextArea.addLineHighlight(37, Color.GREEN);
                                        }
                                        catch(BadLocationException e) {
                                                e.printStackTrace();
                                        }
                                        
                                        carPos = syntaxTextArea.getText().indexOf("Add your own vegetation index calculator here");
                                        carPos+=100;
                                }
                                
                                syntaxTextArea.setCaretPosition(carPos);
                        }
                        catch(IOException ioe) {
                                ioe.printStackTrace();
                                IJ.error("I/O Error", "There was an error while reading the macro template file.");
                        }
                        finally {
                                if(br != null) {
                                        try {
                                                br.close();
                                        }
                                        catch (IOException e) {}
                                        
                                        br = null;
                                }
                                
                                sb = null;
                                line = null;
                                macroInputStream = null;
                        }
                }
                else {
                        IJ.error("File Not Found", "Could not find the macro template \""+macroTemplateName+"\"");
                }
        }
        
        private final void tabPanel_stateChanged(ChangeEvent e) {
                JTabbedPane tabby = (JTabbedPane) e.getSource();
                int index = tabby.getSelectedIndex();
                
                if(index > 0) {
                        saveImagesCheckBox.setText("Save Index1 Images?");
                }
                else {
                        saveImagesCheckBox.setText("Save HSB Images?");
                }
                
                MaizeMacroVars macroVar = macroMap.get("" + index);
                batchInputField.setText(macroVar.getBatchInputVar());
                saveImagesField.setText(macroVar.getSaveImagesDirVar());
                resultsFileField.setText(macroVar.getSaveResultsFileVar());
                saveImagesCheckBox.setSelected(macroVar.isSaveImages());
        }
        
        private final class CustomTabPanel extends JPanel {
                
                private static final long serialVersionUID = -3765960804880374737L;
                
                private JButton addButton = null;
                private JButton closeButton = null;
                private JLabel nameLabel = null;
                private final int size = 17;
                
                public CustomTabPanel(String tabName) {
                        nameLabel = new JLabel(tabName);
                        nameLabel.setHorizontalAlignment(JLabel.LEFT);
                        nameLabel.setFocusable(false);
                        
                        addButton = new JButton("+");
                        addButton.setContentAreaFilled(false);
                        addButton.setBorderPainted(false);
                        addButton.setFocusable(false);
                        addButton.setUI(new BasicButtonUI());
                        addButton.setBorder(BorderFactory.createEtchedBorder());
                        addButton.setPreferredSize(new Dimension(size, size));
                        addButton.addMouseListener(buttonMouseListener);
                        addButton.setRolloverEnabled(true);
                        addButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                        addMacroPanelTab("Veg Index "+vegTabIndex, "Maize_CIMMYT_QuanitfyImageComponentsHSB_UBv8_template.ijm", true, true);
                                        vegTabIndex++;
                                }
                        });
                        
                        closeButton = new JButton("x");
                        closeButton.setContentAreaFilled(false);
                        closeButton.setBorderPainted(false);
                        closeButton.setFocusable(false);
                        closeButton.setUI(new BasicButtonUI());
                        closeButton.setBorder(BorderFactory.createEtchedBorder());
                        closeButton.setPreferredSize(new Dimension(size, size));
                        closeButton.addMouseListener(buttonMouseListener);
                        closeButton.setRolloverEnabled(true);
                        closeButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                        int i = macroTab.indexOfTabComponent(CustomTabPanel.this);
                                        //Do not close first tab
                                        if (i != -1 && i > 0) {
                                                macroTab.remove(i);
                                        }
                                }
                        });
                        
                        setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
                        add(nameLabel);
                        add(closeButton);
                        add(addButton);
                }
                
                @Override
                public void updateUI() {

                }
        }
        
        private final static MouseListener buttonMouseListener = new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    Component component = e.getComponent();
                    if (component instanceof AbstractButton) {
                        AbstractButton button = (AbstractButton) component;
                        button.setBorderPainted(true);
                    }
                }

                public void mouseExited(MouseEvent e) {
                    Component component = e.getComponent();
                    if (component instanceof AbstractButton) {
                        AbstractButton button = (AbstractButton) component;
                        button.setBorderPainted(false);
                    }
                }
            };
}
