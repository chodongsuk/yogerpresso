package kr.ds.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StringUtils {
	private StringUtils() {
		throw new AssertionError();
	}
	/**
	 * ���ڿ� üũ
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return (str == null || str.trim().length() == 0);
	}
	/**
	 * ���ڿ� üũ
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(CharSequence str){
		return (str == null || str.length() == 0);
	}
	/**
	 * null�ϰ�� ����ó��
	 * @param str
	 * @return
	 */
	public static String nullStrToEmpty(Object str) {
		return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
	}
	/**
	 * ���ڴ� utf-8
	 * @param str
	 * @return
	 */
	public static String utf8Encode(String str) {
		if (!isEmpty(str) && str.getBytes().length != str.length()) {
			try {
				return URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(
						"UnsupportedEncodingException occurred. ", e);
			}
		}
		return str;
	}
	public static String euckrEncode(String str) {
		if (!isEmpty(str) && str.getBytes().length != str.length()) {
			try {
				return URLEncoder.encode(str, "EUC-KR");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(
						"UnsupportedEncodingException occurred. ", e);
			}
		}
		return str;
	}
	/**
	 * ���ڴ� utf-8 , �����߻��� ����
	 * @param str
	 * @param defultReturn
	 * @return
	 */
	public static String utf8Encode(String str, String defultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }
	
	

}
