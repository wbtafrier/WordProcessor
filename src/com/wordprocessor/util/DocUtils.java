package com.wordprocessor.util;

import java.awt.event.ActionEvent;

import javax.swing.AbstractButton;

public class DocUtils {
	public static void buttonDoClick(ActionEvent e) {
		
		AbstractButton ab = (AbstractButton)e.getSource();
		
		if (e != null && ab != null && !e.getActionCommand().equals(ab.getActionCommand())) {
			ab.doClick();
		}
	}
}
