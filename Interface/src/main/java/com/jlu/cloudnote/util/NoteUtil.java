package com.jlu.cloudnote.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.security.MessageDigest;
import java.util.UUID;

public class NoteUtil {
	public static String createId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	//加密算法
	public static String md5(String src) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] output = md.digest(src.getBytes());
		String s = Base64.encode(output);
		return s;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(md5("123456"));
		System.out.println(md5("1231232"));
		System.out.println(createId());
	}
}