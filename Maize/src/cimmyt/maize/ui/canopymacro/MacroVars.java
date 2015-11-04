package cimmyt.maize.ui.canopymacro;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 * 
 * @author George - george.dma@gmail.com
 * <br>
 * Created on: Jun 22, 2015
 *
 */
public abstract class MacroVars {
        
        protected String macroNameKey = null;
        protected String macroName = null;
        protected RSyntaxTextArea syntaxTextArea = null;
        
        public MacroVars() {
                
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
