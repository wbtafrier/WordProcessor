package com.wordprocessor.listeners;

import javax.swing.JToggleButton;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;

public class CaretStalker implements CaretListener {
	
	public void caretUpdate(CaretEvent e) {
		boolean boldFound = false;
		boolean italicFound = false;
		boolean underlineFound = false;
		
		int dot = e.getDot();
		int mark = e.getMark();
		
		if (dot == mark) {
			WordProcessing.copy.setEnabled(false);
			WordProcessing.cut.setEnabled(false);
			
			boldFound = checkStyleAtIndex(StyleConstants.Bold, dot - 1, WordProcessing.lockButtonBold);
			italicFound = checkStyleAtIndex(StyleConstants.Italic, dot - 1, WordProcessing.lockButtonItalic);
			underlineFound = checkStyleAtIndex(StyleConstants.Underline, dot - 1, WordProcessing.lockButtonUnderline);
		}
		else {
			WordProcessing.copy.setEnabled(true);
			WordProcessing.cut.setEnabled(true);
			
			for (int i = Math.min(dot, mark); i < Math.max(dot, mark); i++) {
				boldFound = checkStyleAtIndex(StyleConstants.Bold, i);
				italicFound = checkStyleAtIndex(StyleConstants.Italic, i);
				underlineFound = checkStyleAtIndex(StyleConstants.Underline, i);
				
				if (boldFound || italicFound || underlineFound) {
					break;
				}
			}
		}
		
		selectButton(WordProcessing.bold, boldFound);
		selectButton(WordProcessing.italic, italicFound);
		selectButton(WordProcessing.underline, underlineFound);
		
	}
	
	public void selectButton(JToggleButton button, boolean selected) {
		button.setSelected(selected);
	}
	
	public boolean checkStyleAtIndex(Object constant, int index) {
		Element el = doc.getCharacterElement(index);
		AttributeSet attr = null;
		if (el != null) {
			attr = el.getAttributes();
		}
		Object check = attr == null ? null : attr.getAttribute(constant);
		if (check != null && check != Boolean.FALSE) {
			return true;
		}
		return false;
	}
	
	public boolean checkStyleAtIndex(Object constant, int index, boolean locked) {
		Element el = doc.getCharacterElement(index);
		AttributeSet attr = null;
		if (el != null) {
			attr = el.getAttributes();
		}
		Object check = attr == null ? null : attr.getAttribute(constant);
		if (check != null && check != Boolean.FALSE) {
			return true;
		}
		else if (locked) {
			return true;
		}
		return false;
	}
	
}