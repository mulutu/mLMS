package com.mpango.Util;
public class ConvertHelper {
	private static String symbols = " !\"#$%&'()*+,-./0123456789:;<=>?@";
	private static String loAZ = "abcdefghijklmnopqrstuvwxyz";

	static {
		symbols += loAZ.toUpperCase();
		symbols += "[\\]^_`";
		symbols += loAZ;
		symbols += "{|}~";
	}

	public static String asciiToHex(String valueStr) {
		String hexChars = "0123456789abcdef";
		String text = "";
		int i = 0;
		for (i = 0; i < valueStr.length(); i++) {
			char oneChar = valueStr.charAt(i);
			int asciiValue = symbols.indexOf(oneChar) + 32;
			int index1 = asciiValue % 16;
			int index2 = (asciiValue - index1) / 16;
			text += hexChars.charAt(index2);
			text += hexChars.charAt(index1);
		}
		return text;
	}

	public static String decToHex(int decValue) {
		String hexChars = "0123456789abcdef";
		StringBuilder result = new StringBuilder();
		int du;
		if(decValue < 16){
			result.append(hexChars.charAt(decValue));
			result.append('0');
			return result.reverse().toString();
		}
		do {
			du = decValue % 16;
			decValue = decValue / 16;
			result.append(hexChars.charAt(du));
		} while (decValue > 0);
		
		return result.reverse().toString();

	}
}
