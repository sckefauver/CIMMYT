package cimmyt.maize.ui.macros.canopy.maize;

import cimmyt.maize.ui.macros.MacroVars;

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: Nov 4, 2015
 *
 */
public class MaizeMacroVars extends MacroVars {

        private String batchInputPrev = null;
        private String saveImagesPrev = null;
        private String saveImagesDirPrev = null;
        private String saveResultsFilePrev = null;
        
        public MaizeMacroVars() {
                
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
        
        public final void setSaveImagesVar(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_images}") != -1) {
                        saveImagesPrev = "saveImages = $P{save_images}";
                }
                
                String newTxt = macroTxt.replace(saveImagesPrev, "saveImages = "+variableName);
                syntaxTextArea.setText(newTxt);
                
                saveImagesPrev = new String("saveImages = "+variableName);
                syntaxTextArea.setCaretPosition(0);
        }
        
        public final void setSaveImagesDirVar(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_images_dir}") != -1) {
                        saveImagesDirPrev = "$P{save_images_dir}";
                }
                
                variableName = variableName.replaceAll("\\\\", "\\\\\\\\");
                variableName = variableName + "\\\\";
                
                String newTxt = macroTxt.replace(saveImagesDirPrev, variableName);
                syntaxTextArea.setText(newTxt);
                
                saveImagesDirPrev = new String(variableName);
                syntaxTextArea.setCaretPosition(0);
        }
        
        public final void setSaveResultsFileVar(String variableName) {
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

        public final String getBatchInputVar() {
                return batchInputPrev;
        }

        public final String getSaveImagesDirVar() {
                return saveImagesDirPrev;
        }

        public final String getSaveResultsFileVar() {
                return saveResultsFilePrev;
        }
        
        public final boolean isSaveImages() {
                if(saveImagesPrev != null) {
                        return saveImagesPrev.toLowerCase().contains("true");  
                }
                else {
                        return false;
                }
        }
}
