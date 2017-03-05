package cimmyt.maize.ui.tools;

import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * 
 * @author George El Haddad (george.dma@gmail.com)
 * <p>
 * Created on: Dec 16, 2015
 *
 */
public class RegexDocumentFilter extends DocumentFilter {
        
        private Pattern regex = null;
        private int maxLength = -1;
        private boolean isBlocked = false;
        
        public RegexDocumentFilter(String regex) {
                super();
                this.regex = Pattern.compile(regex);
        }
        
        public RegexDocumentFilter(int maxLength) {
                super();
                this.maxLength = maxLength;
                this.regex = Pattern.compile(".*");
        }
        
        public RegexDocumentFilter(String regex, int maxLength) {
                super();
                this.maxLength = maxLength;
                this.regex = Pattern.compile(regex);
        }
        
        public void setRegex(String regex) {
                this.regex = Pattern.compile(regex);
        }
        
        @Override
        public void insertString(FilterBypass fb, int offset, String str, AttributeSet attrs) throws BadLocationException {
                if(str == null) {
                        return;
                }
                
                if(maxLength < 0) {
                        if(regex.matcher(str).matches()) {
                                if (!isBlocked) {
                                        fb.insertString(offset, str, attrs);
                                }
                                else {
                                        //send event
                                        //Max limit not reached but invalid input
                                }
                        }
                }
                else {
                        if(offset < maxLength && offset >= 0 && regex.matcher(str).matches() && fb.getDocument().getLength() < maxLength) {
                                if (!isBlocked) {
                                        fb.insertString(offset, str, attrs);
                                }
                                else {
                                        //send event
                                        //Max limit reached
                                }
                        }
                }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet attrs) throws BadLocationException {
                if(str == null) {
                        return;
                }
                
                if(maxLength < 0) {
                        if(regex.matcher(str).matches()) {
                                if (!isBlocked) {
                                        fb.replace(offset, length, str, attrs);
                                }
                                else {
                                        //send event
                                        //Max limit not reached but invalid input
                                }
                        }
                }
                else {
                        if(offset < maxLength && offset >= 0 && regex.matcher(str).matches() && fb.getDocument().getLength() < maxLength) {
                                if (!isBlocked) {
                                        fb.replace(offset, length, str, attrs);
                                }
                                else {
                                        //send event
                                        //Max limit reached
                                }
                        }
                }
        }
        
        
}
