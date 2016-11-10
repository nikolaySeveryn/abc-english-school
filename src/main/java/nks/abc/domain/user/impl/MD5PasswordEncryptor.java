package nks.abc.domain.user.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import nks.abc.domain.user.PasswordEncryptor;

public class MD5PasswordEncryptor implements PasswordEncryptor{
	
	@Override
	public String encrypt(String password){
		byte[] bytesOfPassword = password.getBytes();
		MessageDigest md=null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
		}
		byte[] md5 = md.digest(bytesOfPassword);
		byte[] base64 = Base64.encodeBase64(md5);
		
        return new String(base64);
	}
}
