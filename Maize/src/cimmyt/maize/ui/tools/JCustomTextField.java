package cimmyt.maize.ui.tools;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * 
 * @author George - george.dma@gmail.com
 * Created on: Mar 12, 2015
 *
 */
public class JCustomTextField extends JTextField {

        private static final long serialVersionUID = 7627525877295451992L;
        
        protected int maxLength = -1;
	protected String regexCheck = ".*";	//default regex, accept everything
	protected boolean isBlocked = false;
	
	/**
	 * Creates a new CustomTextField with
	 * super class defaulted settings.
	 */
	public JCustomTextField() {
		super();
	}
	
	/**
	 * Creates a new custom text field with a specified number of columns.
	 * 
	 * @param cols - number of columns
	 */
	public JCustomTextField(int cols) {
		super(cols);
	}
	
	/**
	 * Creates a new custom text field with a specified number of columns and a maximum length.
	 * 
	 * @param cols - number of columns
	 * @param maxLength - maximum length of the text
	 */
	public JCustomTextField(int cols, int maxLength) {
		super(cols);
		setMaximumLength(maxLength);
	}
	
	/**
	 * Creates a new regular expression document.
	 * 
	 * @return the regular expression {@link Document}
	 */
	@Override
	protected Document createDefaultModel() {
		return new RegexDocument();
	}
	
	/**
	 * Returns the maximum length of the text field.
	 * 
	 * @return the maximum length of the the text field
	 */
	public int getMaximumLength() {
		return maxLength;
	}
	
	/**
	 * Sets the maximum length of the text field.
	 * 
	 * @param max - the maximum length for the text
	 */
	public void setMaximumLength(int max) {
		maxLength = max;
	}
	
	/**
	 * Returns a copy of the regular expression used.
	 * 
	 * @return a copy of the regular expression {@link String} used
	 */
	public String getRegexFilter() {
		return String.valueOf(regexCheck);
	}
	
	
	/**
	 * Sets the regular expression string.
	 * 
	 * @param regex - the regular expression string to use
	 */
	public void setRegexFilter(String regex) {
		regexCheck = regex;
	}
	
	/**
	 * Returns true if input is being blocked in the text field.
	 * 
	 * @return true if the input is being blocked
	 */
	public boolean isBlocked()  {
		return isBlocked;
	}
	
	/**
	 * When set to true, blocks all input to text field. 
	 * 
	 * @param boolean - true to block all input to the text field
	 */
	public void setBlocked(boolean bool) {
		isBlocked = bool;
	}
	
	/**
	 * 
	 * @author George - george.dma@gmail.com
	 * Created on: Mar 12, 2015
	 *
	 */
	private final class RegexDocument extends PlainDocument {
		
                private static final long serialVersionUID = 3115497565720910614L;

                @Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if(str == null) {
				return;
			}
			
			if(maxLength < 0) {
				if(str.matches(regexCheck)) {
					if (!isBlocked) {
						super.insertString(offs, str, a);
					}
					else {
						//send event
						//Max limit not reached but invalid input
					}
				}
			}
			else {
				if(offs < maxLength && offs >= 0 && str.matches(regexCheck) && getLength() < maxLength) {
					if (!isBlocked) {
						super.insertString(offs, str, a);
					}
					else {
						//send event
						//Max limit reached
					}
				}
			}
		}
	}
}
