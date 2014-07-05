package com.wordprocessor.action;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.wordprocessor.core.WordProcessor;
import com.wordprocessor.util.DocUtils;

public class FindNextAction extends AbstractAction {

	private static final long serialVersionUID = -3968914242663532111L;

	private WordProcessor wordProcessor = null;
	
	public FindNextAction(WordProcessor wp) {
		super();
		this.wordProcessor = wp;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DocUtils.buttonDoClick(e);
		
		String txt = wordProcessor.area1.getText().replaceAll("\r\n", "\n");
		String find = wordProcessor.findFld.getText();
		
		if (find == null || find.isEmpty()) {
			wordProcessor.findMsg.setText("Enter a term to find");
			return;
		}
		
		if (!wordProcessor.findAndMatchCase) {
			txt = txt.toLowerCase();
			find = find.toLowerCase();
		}
		
		if (txt.contains(find)) {
			int count = 0;
			int start = txt.indexOf(find);
			int end = start + find.length();
			wordProcessor.area1.select(start, end);
			String searchRange = txt;
			
			while (searchRange != null && !searchRange.isEmpty() && searchRange.contains(find)) {
				count++;
				start = searchRange.indexOf(find);
				end = start + find.length();
				searchRange = searchRange.substring(end);
			}
			
			if (count != 1) wordProcessor.findMsg.setText(Integer.toString(count) + " matches found");
			else wordProcessor.findMsg.setText(Integer.toString(count) + " match found");
		}
		else {
			wordProcessor.findMsg.setText("No matches found");
			Toolkit.getDefaultToolkit().beep();
		}
	}

}
