package com.wordprocessor.listeners;

import javax.swing.JToggleButton;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;

import com.wordprocessor.core.WordProcessor;

public class CaretStalker implements CaretListener {
	
	private WordProcessor wordProcessor = null;
	
	public CaretStalker(WordProcessor wp) {
		this.wordProcessor = wp;
	}
	
	public void caretUpdate(CaretEvent e) {
		
		boolean boldFound = false;
		boolean italicFound = false;
		boolean underlineFound = false;
		
		int dot = e.getDot();
		int mark = e.getMark();
		
		if (dot == mark) {
			wordProcessor.copy.setEnabled(false);
			wordProcessor.cut.setEnabled(false);
			
			boldFound = checkStyleAtIndex(StyleConstants.Bold, dot - 1, wordProcessor.lockButtonBold);
			italicFound = checkStyleAtIndex(StyleConstants.Italic, dot - 1, wordProcessor.lockButtonItalic);
			underlineFound = checkStyleAtIndex(StyleConstants.Underline, dot - 1, wordProcessor.lockButtonUnderline);
		}
		else {
			wordProcessor.copy.setEnabled(true);
			wordProcessor.cut.setEnabled(true);
			
			for (int i = Math.min(dot, mark); i < Math.max(dot, mark); i++) {
				boldFound = checkStyleAtIndex(StyleConstants.Bold, i);
				italicFound = checkStyleAtIndex(StyleConstants.Italic, i);
				underlineFound = checkStyleAtIndex(StyleConstants.Underline, i);
				
				if (boldFound || italicFound || underlineFound) {
					break;
				}
			}
		}
		
		selectButton(wordProcessor.bold, boldFound);
		selectButton(wordProcessor.italic, italicFound);
		selectButton(wordProcessor.underline, underlineFound);
		
	}
	
	public void selectButton(JToggleButton button, boolean selected) {
		button.setSelected(selected);
	}
	
	public boolean checkStyleAtIndex(Object constant, int index) {
		Element el = wordProcessor.doc.getCharacterElement(index);
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
		Element el = wordProcessor.doc.getCharacterElement(index);
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