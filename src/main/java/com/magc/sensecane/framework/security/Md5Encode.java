package com.magc.sensecane.framework.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import com.magc.sensecane.framework.container.Container;
import com.magc.sensecane.framework.utils.AbstractUtil;

public class Md5Encode extends AbstractUtil<String, String> {
	
	public Md5Encode(Container container) {
		super(container);
	}

	@Override
	public String execute(String text) {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes(StandardCharsets.UTF_8));
			byte[] digest = md.digest();
			
			result = DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
