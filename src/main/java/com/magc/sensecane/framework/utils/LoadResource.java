package com.magc.sensecane.framework.utils;

import java.io.File;

import com.magc.sensecane.framework.container.Container;

public class LoadResource implements Util<String, File> {
	
	@Override
	public File execute(String file) {
		String path = null;
		try {
			File f = new File("./src/main/resources/" + file);
			if (f.exists()) {
				path = f.getCanonicalPath();
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			try {
				path = new File("./resources/" + file).getCanonicalPath();
			} catch (Exception ex) {}
		}
		return new File(path);
	}

}
