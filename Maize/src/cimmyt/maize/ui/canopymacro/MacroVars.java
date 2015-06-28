package cimmyt.maize.ui.canopymacro;

import javax.swing.text.Highlighter.HighlightPainter;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Jun 22, 2015
 *
 */
public class MacroVars {
        
        private String macroNameKey = null;
        private String macroName = null;
        private RSyntaxTextArea syntaxTextArea = null;
        
        private String batchInputPrev = null;
        private String batchOutputPrev = null;
        private String saveHsbImagesPrev = null;
        private String saveHsbDirPrev = null;
        private String saveResultsFilePrev = null;
        
//        private int batchInputIndexStart = -1;
//        private int batchOutputIndexStart = -1;
//        private int saveHsbImagesIndexStart = -1;
//        private int saveHsbDirIndexStart = -1;
//        private int saveResultsFileIndexStart = -1;
        
//        private int batchInputLength = -1;
//        private int batchOutputLength = -1;
//        private int saveHsbImagesLength = -1;
//        private int saveHsbDirLength = -1;
//        private int saveResultsFileLength = -1;
        
//        private Object batchInputHpTag = null;
//        private Object batchOutputHpTag = null;
//        private Object saveHsbImagesHpTag = null;
//        private Object saveHsbDirHpTag = null;
//        private Object saveResultsFileHpTag = null;
        
        public MacroVars() {
                
        }
        
        public final void setBatchInputVar(String variableName, HighlightPainter highlightColor) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{batch_input}") != -1) {
                        batchInputPrev = "$P{batch_input}";
                }
                
                variableName = variableName.replaceAll("\\\\", "\\\\\\\\");
                variableName = variableName + "\\\\";
                
                String newTxt = macroTxt.replace(batchInputPrev, variableName);
                syntaxTextArea.setText(newTxt);
                
//                batchInputIndexStart = newTxt.indexOf(batchInputPath);
//                batchInputLength = batchInputPath.length();
                batchInputPrev = new String(variableName);
                syntaxTextArea.setCaretPosition(0);
                
//                try {
//                        Highlighter h = syntaxTextArea.getHighlighter();
//                        
//                        if(batchInputHpTag != null) {
//                                h.removeHighlight(batchInputHpTag);
//                        }
//                        
//                        batchInputHpTag = h.addHighlight(batchInputIndexStart, batchInputIndexStart + batchInputLength, highlightColor);
//                }
//                catch (BadLocationException e) {
//                        e.printStackTrace();
//                }
//                finally {
//                        syntaxTextArea.setCaretPosition(0);
//                }
        }
        
        public final void setBatchOutputVar(String variableName, HighlightPainter highlightColor) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{batch_output}") != -1) {
                        batchOutputPrev = "$P{batch_output}";
                }
                
                variableName = variableName.replaceAll("\\\\", "\\\\\\\\");
                variableName = variableName + "\\\\";
                
                String newTxt = macroTxt.replace(batchOutputPrev, variableName);
                syntaxTextArea.setText(newTxt);
                
//                batchOutputIndexStart = newTxt.indexOf(variableName);
//                batchOutputLength = variableName.length();
                batchOutputPrev = new String(variableName);
                syntaxTextArea.setCaretPosition(0);
                
//                try {
//                        Highlighter h = syntaxTextArea.getHighlighter();
//                        
//                        if(batchOutputHpTag != null) {
//                                h.removeHighlight(batchOutputHpTag);
//                        }
//                        
//                        batchOutputHpTag = h.addHighlight(batchOutputIndexStart, batchOutputIndexStart + batchOutputLength, highlightColor);
//                }
//                catch (BadLocationException e) {
//                        e.printStackTrace();
//                }
//                finally {
//                        syntaxTextArea.setCaretPosition(0);
//                }
        }
        
        public final void setSaveHsbImagesVar(String variableName, HighlightPainter highlightColor) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_hsb_images}") != -1) {
                        saveHsbImagesPrev = "$P{save_hsb_images}";
                }
                
                String newTxt = macroTxt.replace(saveHsbImagesPrev, variableName);
                syntaxTextArea.setText(newTxt);
                
//                saveHsbImagesIndexStart = newTxt.indexOf(variableName);
//                saveHsbImagesLength = variableName.length();
                saveHsbImagesPrev = new String(variableName);
                syntaxTextArea.setCaretPosition(0);
                
//                try {
//                        Highlighter h = syntaxTextArea.getHighlighter();
//                        
//                        if(saveHsbImagesHpTag != null) {
//                                h.removeHighlight(saveHsbImagesHpTag);
//                        }
//                        
//                        saveHsbImagesHpTag = h.addHighlight(saveHsbImagesIndexStart, saveHsbImagesIndexStart + saveHsbImagesLength, highlightColor);
//                }
//                catch (BadLocationException e) {
//                        e.printStackTrace();
//                }
//                finally {
//                        syntaxTextArea.setCaretPosition(0);
//                }
        }
        
        public final void setSaveHsbDirVar(String variableName, HighlightPainter highlightColor) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_hsb_dir}") != -1) {
                        saveHsbDirPrev = "$P{save_hsb_dir}";
                }
                
                variableName = variableName.replaceAll("\\\\", "\\\\\\\\");
                variableName = variableName + "\\\\";
                
                String newTxt = macroTxt.replace(saveHsbDirPrev, variableName);
                syntaxTextArea.setText(newTxt);
                
//                saveHsbDirIndexStart = newTxt.indexOf(variableName);
//                saveHsbDirLength = variableName.length();
                saveHsbDirPrev = new String(variableName);
                syntaxTextArea.setCaretPosition(0);
                
//                try {
//                        Highlighter h = syntaxTextArea.getHighlighter();
//                        
//                        if(saveHsbDirHpTag != null) {
//                                h.removeHighlight(saveHsbDirHpTag);
//                        }
//                        
//                        saveHsbDirHpTag = h.addHighlight(saveHsbDirIndexStart, saveHsbDirIndexStart + saveHsbDirLength, highlightColor);
//                }
//                catch (BadLocationException e) {
//                        e.printStackTrace();
//                }
//                finally {
//                        syntaxTextArea.setCaretPosition(0);
//                }
        }
        
        public final void setSaveResultsFile(String variableName, HighlightPainter highlightColor) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_results_file}") != -1) {
                        saveResultsFilePrev = "$P{save_results_file}";
                }
                
                variableName = variableName.replaceAll("\\\\", "\\\\\\\\");
                
                String newTxt = macroTxt.replace(saveResultsFilePrev, variableName);
                syntaxTextArea.setText(newTxt);
                
//                saveResultsFileIndexStart = newTxt.indexOf(variableName);
//                saveResultsFileDirLength = variableName.length();
                saveResultsFilePrev = new String(variableName);
                syntaxTextArea.setCaretPosition(0);
                
//                try {
//                        Highlighter h = syntaxTextArea.getHighlighter();
//
//                        if(saveResultsFileHpTag != null) {
//                                h.removeHighlight(saveResultsFileHpTag);
//                        }
//
//                        saveResultsFileHpTag = h.addHighlight(saveResultsFileIndexStart, saveResultsFileIndexStart + saveResultsFileLength, highlightColor);
//                }
//                catch (BadLocationException e) {
//                        e.printStackTrace();
//                }
//                finally {
//                        syntaxTextArea.setCaretPosition(0);
//                }
        }
        
        public final RSyntaxTextArea getSyntaxTextArea() {
                return syntaxTextArea;
        }

        public final void setSyntaxTextArea(RSyntaxTextArea syntaxTextArea) {
                this.syntaxTextArea = syntaxTextArea;
        }

        public final String getMacroNameKey() {
                return macroNameKey;
        }

        public final void setMacroNameKey(String macroNameKey) {
                this.macroNameKey = macroNameKey;
        }

        public final String getMacroName() {
                return macroName;
        }

        public final void setMacroName(String macroName) {
                this.macroName = macroName;
        }
        
        public final String getMacroCode() {
                return syntaxTextArea.getText();
        }
}
