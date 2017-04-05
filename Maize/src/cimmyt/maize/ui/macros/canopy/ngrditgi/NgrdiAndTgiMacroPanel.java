package cimmyt.maize.ui.macros.canopy.ngrditgi;

import ij.IJ;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import layout.TableLayout;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import cimmyt.maize.ui.MaizeFrame;
import cimmyt.maize.ui.macros.canopy.maize.MaizeMacroPanel;
import cimmyt.maize.ui.tools.FileOpen;
import cimmyt.maize.ui.tools.FileSave;

/**
 * 
 * @author George El Haddad (george.dma@gmail.com)
 * <p>
 * Created on: Oct 31, 2015
 *
 */
public class NgrdiAndTgiMacroPanel extends JPanel {
        
        private static final long serialVersionUID = 6909704476372524216L;
        
        private JLabel batchInputLabel = null;
        private JTextField batchInputField = null;
        private JButton batchInputButton = null;
        
        private JCheckBox saveNgrdiImagesCheckBox = null;
        private JTextField saveNgrdiImagesField = null;
        private JButton saveNgrdiImagesButton = null;
        
        private JCheckBox saveTgiImagesCheckBox = null;
        private JTextField saveTgiImagesField = null;
        private JButton saveTgiImagesButton = null;
        
        private JLabel resultsFileLabel = null;
        private JTextField resultsFileField = null;
        private JButton resultsFileButton = null;
        
        private JLabel csvDelimiterLabel = null;
        private JComboBox<String> csvDelimiterList = null;
        
        private JPanel optionsPanel = null;
        
        private JPanel buttonPanel = null;
        private JButton runButton = null;
        private JProgressBar progressBar = null;
        
        private String recentDir = null;
        private File batchInputDir = null;
        private File saveNgrdiDir = null;
        private File saveTgiDir = null;
        private File saveResultsFile = null;
        
        private NgrdiTgiMacroVars macroVars = null;
        
        public NgrdiAndTgiMacroPanel() {
                macroVars = new NgrdiTgiMacroVars();
                
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
                
                saveNgrdiImagesCheckBox = new JCheckBox("Save NGRDI Images?", false);
                saveNgrdiImagesCheckBox.setFocusable(false);
                saveNgrdiImagesCheckBox.setSelected(false);
                saveNgrdiImagesCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveNgrdiImagesCheckBox_actionPerformed();
                        }
                });
                
                saveNgrdiImagesField = new JTextField(20);
                saveNgrdiImagesField.setEditable(false);
                saveNgrdiImagesField.setBackground(null);
                
                saveNgrdiImagesButton = new JButton("...");
                saveNgrdiImagesButton.setEnabled(false);
                saveNgrdiImagesButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveNgrdiImagesButton_actionPerformed();
                        }
                });
                
                //----------------------------------------------------------------
                
                saveTgiImagesCheckBox = new JCheckBox("Save TGI Images?", false);
                saveTgiImagesCheckBox.setFocusable(false);
                saveTgiImagesCheckBox.setSelected(false);
                saveTgiImagesCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveTgiImagesCheckBox_actionPerformed();
                        }
                });
                
                saveTgiImagesField = new JTextField(20);
                saveTgiImagesField.setEditable(false);
                saveTgiImagesField.setBackground(null);
                
                saveTgiImagesButton = new JButton("...");
                saveTgiImagesButton.setEnabled(false);
                saveTgiImagesButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveTgiImagesButton_actionPerformed();
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
                
                csvDelimiterLabel = new JLabel("CSV Delimiter");
                csvDelimiterLabel.setHorizontalAlignment(JLabel.RIGHT);
                
                csvDelimiterList = new JComboBox<>(new String[] {"Comma", "Space", "Tab", "Pipe", "Semi-Colon"});
                
                //----------------------------------------------------------------
                
                double spacer = 5;
                double[][] layoutSize = {
                                //                   0,                             2,                        4,                             6
                                {TableLayout.PREFERRED, spacer, TableLayout.PREFERRED, spacer ,TableLayout.FILL, spacer, TableLayout.PREFERRED},
                                {TableLayout.PREFERRED, //0
                                 spacer,
                                 TableLayout.PREFERRED, //2
                                 spacer,
                                 TableLayout.PREFERRED, //4
                                 spacer,
                                 TableLayout.PREFERRED , //6
                                 spacer,
                                 TableLayout.PREFERRED   //8
                                }
                };
                
                optionsPanel = new JPanel();
                optionsPanel.setBorder(BorderFactory.createTitledBorder("Macro Options"));
                optionsPanel.setLayout(new TableLayout(layoutSize));
                optionsPanel.add(batchInputLabel,        "0, 0");
                optionsPanel.add(batchInputField,        "2, 0, 4");
                optionsPanel.add(batchInputButton,       "6, 0");
                optionsPanel.add(saveNgrdiImagesCheckBox,"0, 2");
                optionsPanel.add(saveNgrdiImagesField,   "2, 2, 4");
                optionsPanel.add(saveNgrdiImagesButton,  "6, 2");
                optionsPanel.add(saveTgiImagesCheckBox,  "0, 4");
                optionsPanel.add(saveTgiImagesField,     "2, 4, 4");
                optionsPanel.add(saveTgiImagesButton,    "6, 4");
                optionsPanel.add(resultsFileLabel,       "0, 6");
                optionsPanel.add(resultsFileField,       "2, 6, 4");
                optionsPanel.add(resultsFileButton,      "6, 6");
                optionsPanel.add(csvDelimiterLabel,      "0, 8");
                optionsPanel.add(csvDelimiterList,       "2, 8");
                
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
                
                RTextScrollPane textScrollPane = createMacroPanel("NgrdiAndTgi", "Wheat_RGB_VegetationIndexes_v1.ijm");
                
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
                
                if(saveNgrdiImagesCheckBox.isSelected()) {
                        if(saveNgrdiDir == null) {
                                JOptionPane.showMessageDialog(this, "Please select a folder to save NGRDI images.", "Select NGRDI Folder", JOptionPane.INFORMATION_MESSAGE);
                                return;
                        }
                }
                
                if(saveTgiImagesCheckBox.isSelected()) {
                        if(saveTgiDir == null) {
                                JOptionPane.showMessageDialog(this, "Please select a folder to save TGI images.", "Select TGI Folder", JOptionPane.INFORMATION_MESSAGE);
                                return;
                        }
                }
                
                if(saveResultsFile == null) {
                        JOptionPane.showMessageDialog(this, "Please select a results file location.", "Select Results File", JOptionPane.INFORMATION_MESSAGE);
                        return;
                }
                
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
                                reformatResultsFile();
                                
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
                InputStream macroInputStream = MaizeMacroPanel.class.getResourceAsStream("/cimmyt/maize/ui/macros/canopy/ngrditgi/Wheat_RGB_VegetationIndexes_v1.ijm");
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
                        IJ.error("File Not Found", "Could not find the macro template \""+macroTemplateName+"\"");
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
        
        private final void saveNgrdiImagesCheckBox_actionPerformed() {
                saveNgrdiImagesButton.setEnabled(saveNgrdiImagesCheckBox.isSelected());
                String selection = null;
                if(saveNgrdiImagesCheckBox.isSelected()) {
                        saveNgrdiImagesField.setBackground(Color.WHITE);
                        selection = "true";
                }
                else {
                        saveNgrdiImagesField.setBackground(null);
                        selection = "false";
                }
                
                macroVars.setSaveNgrdiImagesVar(selection);
        }
        
        private final void saveNgrdiImagesButton_actionPerformed() {
                saveNgrdiDir = FileOpen.getFile("Select NGRDI images folder", (recentDir == null ? System.getProperty("user.dir") : recentDir), JFileChooser.DIRECTORIES_ONLY , "NGRDI Images Folder", (String[])null);
                if(saveNgrdiDir != null) {
                        recentDir = saveNgrdiDir.getAbsolutePath();
                        saveNgrdiImagesField.setText(saveNgrdiDir.getAbsolutePath());
                        macroVars.setSaveNgrdiDirVar(saveNgrdiDir.getAbsolutePath());
                }
        }
        
        private final void saveTgiImagesCheckBox_actionPerformed() {
                saveTgiImagesButton.setEnabled(saveTgiImagesCheckBox.isSelected());
                String selection = null;
                if(saveTgiImagesCheckBox.isSelected()) {
                        saveTgiImagesField.setBackground(Color.WHITE);
                        selection = "true";
                }
                else {
                        saveTgiImagesField.setBackground(null);
                        selection = "false";
                }
                
                macroVars.setSaveTgiImagesVar(selection);
        }
        
        private final void saveTgiImagesButton_actionPerformed() {
                saveTgiDir = FileOpen.getFile("Select TGI images folder", (recentDir == null ? System.getProperty("user.dir") : recentDir), JFileChooser.DIRECTORIES_ONLY , "TGI Images Folder", (String[])null);
                if(saveTgiDir != null) {
                        recentDir = saveTgiDir.getAbsolutePath();
                        saveTgiImagesField.setText(saveTgiDir.getAbsolutePath());
                        macroVars.setSaveTgiDirVar(saveTgiDir.getAbsolutePath());
                }
        }
        
        private final void resultsFileButton_actionPerformed() {
                saveResultsFile = FileSave.saveFile("Name Results File", (recentDir == null ? new File(System.getProperty("user.dir")) : new File(recentDir)), "Results File (*.csv)", "NGRDI_TGI_Results.csv");
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
        
        private final void reformatResultsFile() {
                try {
                        List<NgrdiTgiPojo> resultList = new ArrayList<NgrdiTgiPojo>();
                        BufferedReader br = new BufferedReader(new FileReader(saveResultsFile));
                        String line1 = null;
                        String line2 = null;
                        br.readLine(); //Skip the header
                        while(true) {
                                line1 = br.readLine();
                                
                                if(line1 == null) {
                                        break;
                                }
                                
                                line2 = br.readLine();
                                String[] arrLine1 = line1.split(",");
                                String[] arrLine2 = line2.split(",");
                                resultList.add(new NgrdiTgiPojo(arrLine1[3], arrLine1[1], arrLine1[2], arrLine2[1], arrLine2[2]));
                        }
                        
                        br.close();
                        br = null;
                        
                        String delimiter = (String)csvDelimiterList.getSelectedItem();
                        char delim = '\0';
                        switch(delimiter) {
                                case "Comma": {
                                        delim = ',';
                                        break;
                                }
                                
                                case "Space": {
                                        delim = ' ';
                                        break;
                                }
                                
                                case "Tab": {
                                        delim = '\t';
                                        break;
                                }
                                
                                case "Pipe": {
                                        delim = '|';
                                        break;
                                }
                                
                                case "Semi-Colon": {
                                        delim = ';';
                                        break;
                                }
                                
                                default: {
                                        delim = ',';
                                        break;
                                }
                        }
                        
                        StringBuilder csvBuilder = new StringBuilder();
                        csvBuilder.append("Image Name");
                        csvBuilder.append(delim);
                        csvBuilder.append("NGRDI-Mean");
                        csvBuilder.append(delim);
                        csvBuilder.append("NGRDI-StDev");
                        csvBuilder.append(delim);
                        csvBuilder.append("TGI-Mean");
                        csvBuilder.append(delim);
                        csvBuilder.append("TGI-StDev");
                        csvBuilder.append(System.getProperty("line.separator"));
                        
                        BufferedWriter bw = new BufferedWriter(new FileWriter(saveResultsFile, false));
                        bw.write(csvBuilder.toString());
                        bw.flush();
                        
                        for(int i=0; i < resultList.size(); i++) {
                                NgrdiTgiPojo pojo = resultList.get(i);
                                csvBuilder = new StringBuilder();
                                csvBuilder.append(pojo.getImageName());
                                csvBuilder.append(delim);
                                csvBuilder.append(pojo.getnMean());
                                csvBuilder.append(delim);
                                csvBuilder.append(pojo.getnStd());
                                csvBuilder.append(delim);
                                csvBuilder.append(pojo.gettMean());
                                csvBuilder.append(delim);
                                csvBuilder.append(pojo.gettStd());
                                csvBuilder.append(System.getProperty("line.separator"));
                                bw.write(csvBuilder.toString());
                                bw.flush();
                        }
                        
                        
                        bw.close();
                        bw = null;
                }
                catch (IOException e) {
                        IJ.error("Error Formating Results File", "There was an error while reformatting the results file \""+saveResultsFile.getName()+"\"");
                }
        }
        
        private class NgrdiTgiPojo {
                private String imageName = null;
                private String nMean = null;
                private String nStd = null;
                private String tMean = null;
                private String tStd = null;
                
                NgrdiTgiPojo(String imageName, String nMean, String nStd, String tMean, String tStd) {
                        this.imageName = imageName;
                        this.nMean = nMean;
                        this.nStd = nStd;
                        this.tMean = tMean;
                        this.tStd = tStd;
                }

                public final String getImageName() {
                        return imageName;
                }

                public final String getnMean() {
                        return nMean;
                }

                public final String getnStd() {
                        return nStd;
                }

                public final String gettMean() {
                        return tMean;
                }

                public final String gettStd() {
                        return tStd;
                }
        }
}

