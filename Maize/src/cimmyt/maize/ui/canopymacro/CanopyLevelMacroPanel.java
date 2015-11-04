package cimmyt.maize.ui.canopymacro;

import ij.IJ;
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
import javax.swing.plaf.basic.BasicButtonUI;
import layout.TableLayout;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import cimmyt.maize.ui.MaizeFrame;
import cimmyt.maize.ui.tools.FileOpen;
import cimmyt.maize.ui.tools.FileSave;

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: May 26, 2015
 *
 */
public class CanopyLevelMacroPanel extends JPanel {

        private static final long serialVersionUID = -9141490313177003568L;
        
        private JLabel batchInputLabel = null;
        private JTextField batchInputField = null;
        private JButton batchInputButton = null;
        
        private JCheckBox saveHsbImagesCheckBox = null;
        private JTextField saveHsbImagesField = null;
        private JButton saveHsbImagesButton = null;
        
        private JLabel resultsFileLabel = null;
        private JTextField resultsFileField = null;
        private JButton resultsFileButton = null;
        
        private JPanel optionsPanel = null;
        private JTabbedPane macroTab = null;
        
        private JPanel buttonPanel = null;
        private JButton runButton = null;
        private JProgressBar progressBar = null;
        
        private String recentDir = null;
        private File batchInputDir = null;
        private File saveHsbDir = null;
        private File saveResultsFile = null;
        
        private HashMap<String, MaizeMacroVars> macroMap = null;
        
        public CanopyLevelMacroPanel() {
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
                
                saveHsbImagesCheckBox = new JCheckBox("Save HSB Images?", false);
                saveHsbImagesCheckBox.setFocusable(false);
                saveHsbImagesCheckBox.setSelected(false);
                saveHsbImagesCheckBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                saveHsbImagesCheckBox_actionPerformed();
                        }
                });
                
                saveHsbImagesField = new JTextField(20);
                saveHsbImagesField.setEditable(false);
                saveHsbImagesField.setBackground(null);
                
                saveHsbImagesButton = new JButton("...");
                saveHsbImagesButton.setEnabled(false);
                saveHsbImagesButton.addActionListener(new ActionListener() {
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
                addMacroPanelTab("Default", "Maize_CIMMYT_QuanitfyImageComponentsHSB_UBv8.ijm");
                
                //----------------------------------------------------------------
                
                double spacer = 5;
                double[][] layoutSize = {
                                //                   0,                        2,                             4
                                {TableLayout.PREFERRED, spacer, TableLayout.FILL, spacer, TableLayout.PREFERRED},
                                {TableLayout.PREFERRED, //0
                                 spacer,
                                 TableLayout.PREFERRED, //2
                                 spacer,
                                 TableLayout.PREFERRED //4
                                }
                };
                
                optionsPanel = new JPanel();
                optionsPanel.setBorder(BorderFactory.createTitledBorder("Macro Options"));
                optionsPanel.setLayout(new TableLayout(layoutSize));
                optionsPanel.add(batchInputLabel,      "0, 0");
                optionsPanel.add(batchInputField,      "2, 0");
                optionsPanel.add(batchInputButton,     "4, 0");
                optionsPanel.add(saveHsbImagesCheckBox,"0, 2");
                optionsPanel.add(saveHsbImagesField,   "2, 2");
                optionsPanel.add(saveHsbImagesButton,  "4, 2");
                optionsPanel.add(resultsFileLabel,     "0, 4");
                optionsPanel.add(resultsFileField,     "2, 4");
                optionsPanel.add(resultsFileButton,    "4, 4");
                
                //----------------------------------------------------------------
                
                runButton = new JButton("Run Macro");
                runButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
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
        
        private final void saveHsbImagesCheckBox_actionPerformed() {
                saveHsbImagesButton.setEnabled(saveHsbImagesCheckBox.isSelected());
                String selection = null;
                if(saveHsbImagesCheckBox.isSelected()) {
                        saveHsbImagesField.setBackground(Color.WHITE);
                        selection = "true";
                }
                else {
                        saveHsbImagesField.setBackground(null);
                        selection = "false";
                }
                
                int index = macroTab.getSelectedIndex();
                MaizeMacroVars macroVar = macroMap.get(""+index);
                macroVar.setSaveHsbImagesVar(selection);
        }
        
        private final void saveHsbImagesButton_actionPerformed() {
                saveHsbDir = FileOpen.getFile("Select HSB images folder", (recentDir == null ? System.getProperty("user.dir") : recentDir), JFileChooser.DIRECTORIES_ONLY , "HSB Images Folder", (String[])null);
                if(saveHsbDir != null) {
                        recentDir = saveHsbDir.getAbsolutePath();
                        saveHsbImagesField.setText(saveHsbDir.getAbsolutePath());
                        
                        int index = macroTab.getSelectedIndex();
                        MaizeMacroVars macroVar = macroMap.get(""+index);
                        macroVar.setSaveHsbDirVar(saveHsbDir.getAbsolutePath());
                }
        }
        
        private final void resultsFileButton_actionPerformed() {
                saveResultsFile = FileSave.saveFile("Name Results File", (recentDir == null ? new File(System.getProperty("user.dir")) : new File(recentDir)), "Results File (*.xls)", "Results.xls");
                if(saveResultsFile != null) {
                        recentDir = saveResultsFile.getParentFile().getAbsolutePath();
                        
                        if(!saveResultsFile.getName().endsWith(".xls")) {
                                String tmp = saveResultsFile.getAbsolutePath();
                                saveResultsFile = new File(tmp+".xls");
                        }
                        
                        resultsFileField.setText(saveResultsFile.getAbsolutePath());
                        
                        int index = macroTab.getSelectedIndex();
                        MaizeMacroVars macroVar = macroMap.get(""+index);
                        macroVar.setSaveResultsFile(saveResultsFile.getAbsolutePath());
                }
        }
        
        private final void addMacroPanelTab(String tabName, String macroTemplateName) {
                InputStream macroInputStream = CanopyLevelMacroPanel.class.getResourceAsStream("/cimmyt/maize/ui/canopymacro/"+macroTemplateName);
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
                                        addMacroPanelTab("New", "Maize_CIMMYT_QuanitfyImageComponentsHSB_UBv8_template.ijm");
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
                                        if (i != -1) {
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
