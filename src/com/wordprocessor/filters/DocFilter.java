package com.wordprocessor.filters;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DocFilter extends DocumentFilter {
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		fb.remove(offset, length);
	}
	
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
		fb.insertString(offset, string, attr);
	}
}