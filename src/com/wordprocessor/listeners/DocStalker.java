package com.wordprocessor.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.wordprocessor.core.WordProcessor;

public class DocStalker implements DocumentListener {
	
	private WordProcessor wordProcessor = null;
	
	public DocStalker(WordProcessor wp) {
		this.wordProcessor = wp;
	}
	
	public void insertUpdate(DocumentEvent e) {
		wordProcessor.revisionSaved = false;
		wordProcessor.updateStats();
	}

	public void removeUpdate(DocumentEvent e) {
		wordProcessor.revisionSaved = false;
		wordProcessor.updateStats();
	}

	public void changedUpdate(DocumentEvent e) {
		wordProcessor.revisionSaved = false;
	}
	
}