package com.estsoft.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class EncryptTest {
	public static void main(String[] args) {
	    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
	    encryptor.setPassword("encKey"); //암호화키
	    String url = encryptor.encrypt("비밀!");
	    String username = encryptor.encrypt("비밀!");
	    String password = encryptor.encrypt("비밀!");
	    System.out.println("jdbc.url="+url);
	    System.out.println("jdbc.username="+username);
	    System.out.println("jdbc.password="+password);
	}
}
