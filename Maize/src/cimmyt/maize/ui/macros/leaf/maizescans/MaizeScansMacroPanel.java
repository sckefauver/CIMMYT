package cimmyt.maize.ui.macros.leaf.maizescans;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import cimmyt.maize.MaizeScanner;
import cimmyt.maize.ui.MaizeFrame;
import cimmyt.maize.ui.macros.canopy.maize.MaizeMacroPanel;
import cimmyt.maize.ui.tools.FileOpen;
import cimmyt.maize.ui.tools.FileSave;
import ij.IJ;
import layout.TableLayout;

/**
 * 
 * @author George El Haddad (george.dma@gmail.com)
 * <p>
 * Created on: Feb 21, 2017
 */
public class MaizeScansMacroPanel extends JPanel {
        
        private static final long serialVersionUID = -2774662326855220700L;
        
        private JLabel batchInputLabel = null;
        private JTextField batchInputField = null;
        private JButton batchInputButton = null;
        
        private JLabel batchOutputLabel = null;
        private JTextField batchOutputField = null;
        private JButton batchOutputButton = null;
        
        private JLabel resultsFileLabel = null;
        private JTextField resultsFileField = null;
        private JButton resultsFileButton = null;
        
        private JCheckBox saveScannerImagesCheckBox = null;
        private JCheckBox saveGreenVegImagesCheckBox = null;
        private JCheckBox saveGreenerVegImagesCheckBox = null;
        private JCheckBox saveChlorosisImagesCheckBox = null;
        private JCheckBox saveNecrosisImagesCheckBox = null;
        private JCheckBox saveNgrdiImagesCheckBox = null;
        private JCheckBox saveTgiImagesCheckBox = null;
        private JPanel checkBoxPanel = null;
        private JLabel checkBoxLabel = null;
        
        private JPanel optionsPanel = null;
        
        private JPanel buttonPanel = null;
        private JButton runButton = null;
        private JProgressBar progressBar = null;
        
        private String recentDir = null;
        private File batchInputDir = null;
        private File batchOutputDir = null;
        private File saveResultsFile = null;
        
        private MaizeScansMacroVars macroVars = null;
        
        public MaizeScansMacroPanel() {
                macroVars = new MaizeScansMacroVars();
                
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
                
                batchOutputLabel = new JLabel("Batch Outputs:");
                batchOutputLabel.setHorizontalAlignment(JLabel.RIGHT);
                
                batchOutputField = new JTextField(20);
                batchOutputField.setEditable(false);
                batchOutputField.setBackground(Color.WHITE);
                
                batchOutputButton = new JButton("...");
                batchOutputButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                batchOutputButton_actionPerformed();
                        }
                });
                
                //----------------------------------------------------------------
                
                saveNgrdiImagesCheckBox = new JCheckBox("NGRDI", false);
                saveNgrdiImagesCheckBox.setFocusable(false);
                saveNgrdiImagesCheckBox.setSelected(false);
                saveNgrdiImagesCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveNgrdiImagesCheckBox_actionPerformed();
                        }
                });
                
                saveTgiImagesCheckBox = new JCheckBox("TGI", false);
                saveTgiImagesCheckBox.setFocusable(false);
                saveTgiImagesCheckBox.setSelected(false);
                saveTgiImagesCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveTgiImagesCheckBox_actionPerformed();
                        }
                });
                
                saveScannerImagesCheckBox = new JCheckBox("Scanner", false);
                saveScannerImagesCheckBox.setFocusable(false);
                saveScannerImagesCheckBox.setSelected(false);
                saveScannerImagesCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveScannerImagesCheckBox_actionPerformed();
                        }
                });
                
                saveGreenVegImagesCheckBox = new JCheckBox("GreenVeg", false);
                saveGreenVegImagesCheckBox.setFocusable(false);
                saveGreenVegImagesCheckBox.setSelected(false);
                saveGreenVegImagesCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveGreenVegImagesCheckBox_actionPerformed();
                        }
                });
                
                saveGreenerVegImagesCheckBox = new JCheckBox("GreenerVeg", false);
                saveGreenerVegImagesCheckBox.setFocusable(false);
                saveGreenerVegImagesCheckBox.setSelected(false);
                saveGreenerVegImagesCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveGreenerVegImagesCheckBox_actionPerformed();
                        }
                });
                
                saveChlorosisImagesCheckBox = new JCheckBox("Chlorosis", false);
                saveChlorosisImagesCheckBox.setFocusable(false);
                saveChlorosisImagesCheckBox.setSelected(false);
                saveChlorosisImagesCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveChlorosisImagesCheckBox_actionPerformed();
                        }
                });
                
                saveNecrosisImagesCheckBox = new JCheckBox("Necrosis", false);
                saveNecrosisImagesCheckBox.setFocusable(false);
                saveNecrosisImagesCheckBox.setSelected(false);
                saveNecrosisImagesCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveNecrosisImagesCheckBox_actionPerformed();
                        }
                });
                
                checkBoxLabel = new JLabel("Save Images:");
                checkBoxLabel.setHorizontalAlignment(JLabel.RIGHT);
                
                checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
                checkBoxPanel.add(saveScannerImagesCheckBox);
                checkBoxPanel.add(saveNgrdiImagesCheckBox);
                checkBoxPanel.add(saveTgiImagesCheckBox);
                checkBoxPanel.add(saveGreenVegImagesCheckBox);
                checkBoxPanel.add(saveGreenerVegImagesCheckBox);
                checkBoxPanel.add(saveChlorosisImagesCheckBox);
                checkBoxPanel.add(saveNecrosisImagesCheckBox);
                
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
                
                double spacer = 5;
                double[][] layoutSize = {
                                //                   0,                        2,                             4
                                {TableLayout.PREFERRED, spacer, TableLayout.FILL, spacer, TableLayout.PREFERRED},
                                {TableLayout.PREFERRED, //0
                                 spacer,
                                 TableLayout.PREFERRED, //2
                                 spacer,
                                 TableLayout.PREFERRED, //4
                                 spacer,
                                 TableLayout.PREFERRED  //6
                                }
                };
                
                optionsPanel = new JPanel();
                optionsPanel.setBorder(BorderFactory.createTitledBorder("Macro Options"));
                optionsPanel.setLayout(new TableLayout(layoutSize));
                optionsPanel.add(batchInputLabel,   "0, 0");
                optionsPanel.add(batchInputField,   "2, 0");
                optionsPanel.add(batchInputButton,  "4, 0");
                optionsPanel.add(batchOutputLabel,  "0, 2");
                optionsPanel.add(batchOutputField,  "2, 2");
                optionsPanel.add(batchOutputButton, "4, 2");
                optionsPanel.add(checkBoxLabel,     "0, 4");
                optionsPanel.add(checkBoxPanel,     "2, 4, 4");
                optionsPanel.add(resultsFileLabel,  "0, 6");
                optionsPanel.add(resultsFileField,  "2, 6");
                optionsPanel.add(resultsFileButton, "4, 6");
                
                //----------------------------------------------------------------
                
                runButton = new JButton("Run Macro");
                runButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveTgiImagesCheckBox_actionPerformed();
                                saveNgrdiImagesCheckBox_actionPerformed();
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
                
                RTextScrollPane textScrollPane = createMacroPanel("Maize MLN Scans", "AnalyzeMaizeScans_BatchComponents_HSBplusplusNGRDIandTGIv3Yoseph.ijm");
                
                setLayout(new BorderLayout(5, 5));
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                add(optionsPanel, BorderLayout.NORTH);
                add(textScrollPane, BorderLayout.CENTER);
                add(buttonPanel, BorderLayout.SOUTH);
        }
        
        private final void runButton_actionPerformed() {
                if(MaizeFrame.isMacroRunning()) {
                        JOptionPane.showMessageDialog(this, "A macro is already running, please wait until it finishes.", "Macro Already Running", JOptionPane.INFORMATION_MESSAGE);
                        return;
                }
                
                if(batchInputDir == null) {
                        JOptionPane.showMessageDialog(this, "Please select the batch inputs folder.", "Select Batch Inputs", JOptionPane.INFORMATION_MESSAGE);
                        return;
                }
                
                if(batchOutputDir == null && isSaveImages()) {
                        JOptionPane.showMessageDialog(this, "Please select the batch outputs folder.", "Select Batch Outputs", JOptionPane.INFORMATION_MESSAGE);
                        return;
                }
                
                if(saveResultsFile == null) {
                        JOptionPane.showMessageDialog(this, "Please select a results file location.", "Select Results File", JOptionPane.INFORMATION_MESSAGE);
                        return;
                }
                
                saveNecrosisImagesCheckBox_actionPerformed();
                saveChlorosisImagesCheckBox_actionPerformed();
                saveGreenerVegImagesCheckBox_actionPerformed();
                saveGreenVegImagesCheckBox_actionPerformed();
                saveScannerImagesCheckBox_actionPerformed();
                saveTgiImagesCheckBox_actionPerformed();
                saveNgrdiImagesCheckBox_actionPerformed();
                
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
                                
                                String macroCode = macroVars.getMacroCode();
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
        
        private final RTextScrollPane createMacroPanel(String panelName, String macroTemplateName) {
                InputStream macroInputStream = MaizeMacroPanel.class.getResourceAsStream("/cimmyt/maize/ui/macros/leaf/maizescans/AnalyzeMaizeScans_BatchComponents_HSBplusplusNGRDIandTGIv3Yoseph.ijm");
                RTextScrollPane syntaxScrollPane = null;
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
                                syntaxTextArea.setEditable(false);
                                syntaxScrollPane = new RTextScrollPane(syntaxTextArea);
                                
                                macroVars.setMacroNameKey(panelName);
                                macroVars.setMacroName(panelName);
                                macroVars.setSyntaxTextArea(syntaxTextArea);
                                
                                syntaxTextArea.setCaretPosition(0);
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
                        IJ.error("File Not Found", "Could not find macro template \""+macroTemplateName+"\"");
                }
                
                return syntaxScrollPane;
        }
        
        private final void batchInputButton_actionPerformed() {
                batchInputDir = FileOpen.getFile("Select batch input folder", (recentDir == null ? System.getProperty("user.dir") : recentDir), JFileChooser.DIRECTORIES_ONLY, "Batch Image Folder", (String[]) null);
                if (batchInputDir != null) {
                        recentDir = batchInputDir.getAbsolutePath();
                        batchInputField.setText(batchInputDir.getAbsolutePath());
                        macroVars.setBatchInputVar(batchInputDir.getAbsolutePath());
                }
        }
        
        private final void batchOutputButton_actionPerformed() {
                batchOutputDir = FileOpen.getFile("Select batch output folder", (recentDir == null ? System.getProperty("user.dir") : recentDir), JFileChooser.DIRECTORIES_ONLY, "Batch Image Folder", (String[]) null);
                if (batchOutputDir != null) {
                        recentDir = batchOutputDir.getAbsolutePath();
                        batchOutputField.setText(batchOutputDir.getAbsolutePath());
                        macroVars.setBatchOutputVar(batchOutputDir.getAbsolutePath());
                }
        }
        
        private final void saveNgrdiImagesCheckBox_actionPerformed() {
                String selection = null;
                if(saveNgrdiImagesCheckBox.isSelected()) {
                        selection = "true";
                }
                else {
                        selection = "false";
                }
                
                macroVars.setSaveNgrdiVar(selection);
        }
        
        private final void saveTgiImagesCheckBox_actionPerformed() {
                String selection = null;
                if(saveTgiImagesCheckBox.isSelected()) {
                        selection = "true";
                }
                else {
                        selection = "false";
                }
                
                macroVars.setSaveTgiVar(selection);
        }
        
        private final void saveScannerImagesCheckBox_actionPerformed() {
                String selection = null;
                if(saveScannerImagesCheckBox.isSelected()) {
                        selection = "true";
                }
                else {
                        selection = "false";
                }
                
                macroVars.setSaveScannerVar(selection);
        }
        
        private final void saveGreenVegImagesCheckBox_actionPerformed() {
                String selection = null;
                if(saveGreenVegImagesCheckBox.isSelected()) {
                        selection = "true";
                }
                else {
                        selection = "false";
                }
                
                macroVars.setSaveGreenVegVar(selection);
        }
        
        private final void saveGreenerVegImagesCheckBox_actionPerformed() {
                String selection = null;
                if(saveGreenerVegImagesCheckBox.isSelected()) {
                        selection = "true";
                }
                else {
                        selection = "false";
                }
                
                macroVars.setSaveGreenerVegVar(selection);
        }
        
        private final void saveChlorosisImagesCheckBox_actionPerformed() {
                String selection = null;
                if(saveChlorosisImagesCheckBox.isSelected()) {
                        selection = "true";
                }
                else {
                        selection = "false";
                }
                
                macroVars.setSaveChlorosisVar(selection);
        }
        
        private final void saveNecrosisImagesCheckBox_actionPerformed() {
                String selection = null;
                if(saveNecrosisImagesCheckBox.isSelected()) {
                        selection = "true";
                }
                else {
                        selection = "false";
                }
                
                macroVars.setSaveNecrosisVar(selection);
        }
        
        private final void resultsFileButton_actionPerformed() {
                saveResultsFile = FileSave.saveFile("Name Results File", (recentDir == null ? new File(System.getProperty("user.dir")) : new File(recentDir)), "Results File (*.csv)", "Maize_LeafScans_v"+MaizeScanner.VERSION+"_Results.csv");
                if(saveResultsFile != null) {
                        recentDir = saveResultsFile.getParentFile().getAbsolutePath();
                        
                        if(!saveResultsFile.getName().endsWith(".csv")) {
                                String tmp = saveResultsFile.getAbsolutePath();
                                saveResultsFile = new File(tmp+".csv");
                        }
                        
                        resultsFileField.setText(saveResultsFile.getAbsolutePath());
                        macroVars.setSaveResultsFile(saveResultsFile.getAbsolutePath());
                }
        }
        
        private final boolean isSaveImages() {
                return saveNecrosisImagesCheckBox.isSelected() ||
                        saveChlorosisImagesCheckBox.isSelected() ||
                        saveGreenerVegImagesCheckBox.isSelected() ||
                        saveGreenVegImagesCheckBox.isSelected() ||
                        saveScannerImagesCheckBox.isSelected() ||
                        saveTgiImagesCheckBox.isSelected() ||
                        saveNgrdiImagesCheckBox.isSelected();
        }
}

