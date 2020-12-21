package com.swinginwind.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	private static final char[] hex = "0123456789abcdef".toCharArray();

	public static String encrypt(String str) {
		return encrypt(str, null);
	}

	public static String encrypt(String str, String charsetName) {
		if (isEmpty(str))
			return str;
		MessageDigest messageDigest;
		try {
			charsetName = !isEmpty(charsetName) ? charsetName : "utf-8";
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(str.getBytes(charsetName));
			return toHexString(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static boolean isEmpty(String str) {
		return str == null || str.equals("");
	}

	private static String toHexString(byte[] bytes) {
		//System.out.println(Arrays.toString(bytes));
		if (null == bytes)
			return null;
		StringBuilder sb = new StringBuilder(bytes.length << 1);
		for (int i = 0; i < bytes.length; ++i) {
			sb.append(hex[bytes[i] >> 4 & 0xF]).append(hex[bytes[i] & 0xF]);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(encrypt("111111"));
	}
}
