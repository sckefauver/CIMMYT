package cimmyt.maize.ui.macros.ngrditgi;

import cimmyt.maize.ui.macros.MacroVars;

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: Nov 4, 2015
 *
 */
public class NgrdiTgiMacroVars extends MacroVars {

        private String batchInputPrev = null;
        private String ngrdiDirPrev = null;
        private String tgiDirPrev = null;
        private String saveNgrdimagesPrev = null;
        private String saveTgiImagesPrev = null;
        private String saveResultsFilePrev = null;
        
        public NgrdiTgiMacroVars() {
                
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
        
        public final void setSaveNgrdiImagesVar(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_ngrdi_images}") != -1) {
                        saveNgrdimagesPrev = "saveNgrdi = $P{save_ngrdi_images}";
                }
                
                String newTxt = macroTxt.replace(saveNgrdimagesPrev, "saveNgrdi = "+variableName);
                syntaxTextArea.setText(newTxt);
                
                saveNgrdimagesPrev = new String("saveNgrdi = "+variableName);
                syntaxTextArea.setCaretPosition(0);
        }
        
        public final void setSaveTgiImagesVar(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_tgi_images}") != -1) {
                        saveTgiImagesPrev = "saveTgi = $P{save_tgi_images}";
                }
                
                String newTxt = macroTxt.replace(saveTgiImagesPrev, "saveTgi = "+variableName);
                syntaxTextArea.setText(newTxt);
                
                saveTgiImagesPrev = new String("saveTgi = "+variableName);
                syntaxTextArea.setCaretPosition(0);
        }
        
        public final void setSaveNgrdiDirVar(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_ngrdi_dir}") != -1) {
                        ngrdiDirPrev = "$P{save_ngrdi_dir}";
                }
                
                variableName = variableName.replaceAll("\\\\", "\\\\\\\\");
                variableName = variableName + "\\\\";
                
                String newTxt = macroTxt.replace(ngrdiDirPrev, variableName);
                syntaxTextArea.setText(newTxt);
                
                ngrdiDirPrev = new String(variableName);
                syntaxTextArea.setCaretPosition(0);
        }
        
        public final void setSaveTgiDirVar(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_tgi_dir}") != -1) {
                        tgiDirPrev = "$P{save_tgi_dir}";
                }
                
                variableName = variableName.replaceAll("\\\\", "\\\\\\\\");
                variableName = variableName + "\\\\";
                
                String newTxt = macroTxt.replace(tgiDirPrev, variableName);
                syntaxTextArea.setText(newTxt);
                
                tgiDirPrev = new String(variableName);
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
}
