package com.wordprocessor.util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.TransferHandler;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;

public class ClipboardHandler extends TransferHandler {
	private static final long serialVersionUID = -4645012626515202311L;
	
	//Starting/ending point of selection in doc
	Position p0 = null, p1 = null;
	
	public ClipboardHandler() {
		super();
	}
	
	/*
	 * Import data from clipboard (Paste)
	 */
	public boolean importData(TransferHandler.TransferSupport support) {
		//Is this text? Can it be pasted?
		if (!canImport(support)) {
			return false;
		}
		
		String data;
		try {
			//Convert clipboard text into a Java String
			data = (String)support.getTransferable().getTransferData(DataFlavor.stringFlavor);
		}
		catch (UnsupportedFlavorException ufe) {
			//Guess this can't be converted to String
			ufe.printStackTrace();
			return false;
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
		
		//Get the doc
		JTextPane pane = (JTextPane)support.getComponent();
		//Whatever is selected, replace
		pane.replaceSelection(data);
		return true;
	}
	
	//Copying
	protected Transferable createTransferable(JComponent c) {
		//Get the pane
        JTextPane source = (JTextPane)c;
        //Selection index start
        int start = source.getSelectionStart();
        //Selection index end
        int end = source.getSelectionEnd();
        //Get the doc
        Document doc = source.getDocument();
        //Return nothing if there is no selection
        if (start == end) {
            return null;
        }
        
        try {
        	//Set the positions
            p0 = doc.createPosition(start);
            p1 = doc.createPosition(end);
        }
        catch (BadLocationException ble) {
        	//Invalid locations
            ble.printStackTrace();
        }
        //Copy selected text
        String data = source.getSelectedText();
        //Return it
        return new StringSelection(data);
    }

    //What can the pane do?
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }
	
	//Cutting
	protected void exportDone(JComponent c, Transferable data, int action) {
		//Only do this if cutting, not copying
        if (action != MOVE) {
            return;
        }
        
        //Make sure there is a valid selection
        if ((p0 != null) && (p1 != null) && (p0.getOffset() != p1.getOffset())) {
            try {
            	//Do the cutting
                JTextComponent tc = (JTextComponent)c;
                tc.getDocument().remove(p0.getOffset(), p1.getOffset() - p0.getOffset());
            }
            catch (BadLocationException ble) {
                ble.printStackTrace();
            }
        }
    }
	
	//Can this be transfered?
	public boolean canImport(TransferHandler.TransferSupport support) {
		//MUST be String text
        if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }
        return true;
	}
}
