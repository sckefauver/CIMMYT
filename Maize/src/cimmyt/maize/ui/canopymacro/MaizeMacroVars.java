package cimmyt.maize.ui.canopymacro;

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: Nov 4, 2015
 *
 */
public class MaizeMacroVars extends MacroVars {

        private String batchInputPrev = null;
        private String saveHsbImagesPrev = null;
        private String saveHsbDirPrev = null;
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
        
        public final void setSaveHsbImagesVar(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_hsb_images}") != -1) {
                        saveHsbImagesPrev = "saveHsb = $P{save_hsb_images}";
                }
                
                String newTxt = macroTxt.replace(saveHsbImagesPrev, "saveHsb = "+variableName);
                syntaxTextArea.setText(newTxt);
                
                saveHsbImagesPrev = new String("saveHsb = "+variableName);
                syntaxTextArea.setCaretPosition(0);
        }
        
        public final void setSaveHsbDirVar(String variableName) {
                String macroTxt = syntaxTextArea.getText();
                
                if(macroTxt.indexOf("$P{save_hsb_dir}") != -1) {
                        saveHsbDirPrev = "$P{save_hsb_dir}";
                }
                
                variableName = variableName.replaceAll("\\\\", "\\\\\\\\");
                variableName = variableName + "\\\\";
                
                String newTxt = macroTxt.replace(saveHsbDirPrev, variableName);
                syntaxTextArea.setText(newTxt);
                
                saveHsbDirPrev = new String(variableName);
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
