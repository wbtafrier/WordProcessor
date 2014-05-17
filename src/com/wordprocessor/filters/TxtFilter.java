package com.wordprocessor.filters;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import com.wordprocessor.util.FileUtils;

public class TxtFilter extends FileFilter {
	
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		
		String ex = FileUtils.getExtension(f);
		if (ex != null) {
			if (ex.equals("txt")) {
				return true;
			}
		}
		return false;
	}

	public String getDescription() {
		return "Plain text file - .txt";
	}
	
	@Override
	public String toString() {
		return "txt";
	}
}