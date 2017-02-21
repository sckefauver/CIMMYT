package cimmyt.maize.ui.macros.leaf.maizescans;

import cimmyt.maize.ui.macros.MacroVars;

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: Feb 21, 2017
 */
public class MaizeScansMacroVars extends MacroVars {
        
        private String batchInputPrev = null;
        private String batchOutputPrev = null;
        private String saveResultsFilePrev = null;
        private String saveScannerPrev = null;
        private String saveGreenvegPrev = null;
        private String saveGreenerVegPrev = null;
        private String saveChlorosisPrev = null;
        private String saveNecrosisPrev = null;
        private String saveNgrdiPrev = null;
        private String saveTgiPrev = null;
        
        
        public MaizeScansMacroVars() {
                
        }
        
        public final void setBatchInputVar(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{batch_input}") != -1) {
                        batchInputPrev = "$P{batch_input}";
                }
                
                variableName = variableName.replaceAll("\\\\", "\\\\\\\\");
                variableName = variableName + "\\\\";
                
                String newTxt = macroTxt.replace(batchInputPrev, variableName);
                syntaxTextArea.setText(newTxt);
                
                batchInputPrev = new String(variableName);
                syntaxTextArea.setCaretPosition(0);
        }
        
        public final void setBatchOutputVar(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{batch_output}") != -1) {
                        batchOutputPrev = "$P{batch_output}";
                }
                
                variableName = variableName.replaceAll("\\\\", "\\\\\\\\");
                variableName = variableName + "\\\\";
                
                String newTxt = macroTxt.replace(batchOutputPrev, variableName);
                syntaxTextArea.setText(newTxt);
                
                batchOutputPrev = new String(variableName);
                syntaxTextArea.setCaretPosition(0);
        }
        
        public final void setSaveResultsFile(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_results_file}") != -1) {
                        saveResultsFilePrev = "$P{save_results_file}";
                }
                
                variableName = variableName.replaceAll("\\\\", "\\\\\\\\");
                
                String newTxt = macroTxt.replace(saveResultsFilePrev, variableName);
                syntaxTextArea.setText(newTxt);
                
                saveResultsFilePrev = new String(variableName);
                syntaxTextArea.setCaretPosition(0);
        }
        
        public final void setSaveScannerVar(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_scanner}") != -1) {
                        saveScannerPrev = "saveScanner = $P{save_scanner}";
                }
                
                String newTxt = macroTxt.replace(saveScannerPrev, "saveScanner = "+variableName);
                syntaxTextArea.setText(newTxt);
                
                saveScannerPrev = new String("saveScanner = "+variableName);
                syntaxTextArea.setCaretPosition(0);
        }
        
        public final void setSaveGreenVegVar(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_green_veg}") != -1) {
                        saveGreenvegPrev = "saveGreenVeg = $P{save_green_veg}";
                }
                
                String newTxt = macroTxt.replace(saveGreenvegPrev, "saveGreenVeg = "+variableName);
                syntaxTextArea.setText(newTxt);
                
                saveGreenvegPrev = new String("saveGreenVeg = "+variableName);
                syntaxTextArea.setCaretPosition(0);
        }
        
        public final void setSaveGreenerVegVar(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_greener_veg}") != -1) {
                        saveGreenerVegPrev = "saveGreenerVeg = $P{save_greener_veg}";
                }
                
                String newTxt = macroTxt.replace(saveGreenerVegPrev, "saveGreenerVeg = "+variableName);
                syntaxTextArea.setText(newTxt);
                
                saveGreenerVegPrev = new String("saveGreenerVeg = "+variableName);
                syntaxTextArea.setCaretPosition(0);
        }
        
        public final void setSaveChlorosisVar(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_chlorosis}") != -1) {
                        saveChlorosisPrev = "saveChlorosis = $P{save_chlorosis}";
                }
                
                String newTxt = macroTxt.replace(saveChlorosisPrev, "saveChlorosis = "+variableName);
                syntaxTextArea.setText(newTxt);
                
                saveChlorosisPrev = new String("saveChlorosis = "+variableName);
                syntaxTextArea.setCaretPosition(0);
        }
        
        public final void setSaveNecrosisVar(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_necrosis}") != -1) {
                        saveNecrosisPrev = "saveNecrosis = $P{save_necrosis}";
                }
                
                String newTxt = macroTxt.replace(saveNecrosisPrev, "saveNecrosis = "+variableName);
                syntaxTextArea.setText(newTxt);
                
                saveNecrosisPrev = new String("saveNecrosis = "+variableName);
                syntaxTextArea.setCaretPosition(0);
        }
        
        public final void setSaveNgrdiVar(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_ngrdi}") != -1) {
                        saveNgrdiPrev = "saveNgrdi = $P{save_ngrdi}";
                }
                
                String newTxt = macroTxt.replace(saveNgrdiPrev, "saveNgrdi = "+variableName);
                syntaxTextArea.setText(newTxt);
                
                saveNgrdiPrev = new String("saveNgrdi = "+variableName);
                syntaxTextArea.setCaretPosition(0);
        }
        
        public final void setSaveTgiVar(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_tgi}") != -1) {
                        saveTgiPrev = "saveTgi = $P{save_tgi}";
                }
                
                String newTxt = macroTxt.replace(saveTgiPrev, "saveTgi = "+variableName);
                syntaxTextArea.setText(newTxt);
                
                saveTgiPrev = new String("saveTgi = "+variableName);
                syntaxTextArea.setCaretPosition(0);
        }
}
