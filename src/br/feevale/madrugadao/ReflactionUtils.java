package br.feevale.madrugadao;

public class ReflactionUtils {

	public static String captalize(String txt) {
		if (txt == null || txt.trim().isEmpty()) {
			return txt;
		}
		String newTxt = txt.toLowerCase();
		return newTxt.substring(0, 1).toUpperCase() + newTxt.substring(1);
	}
	
}
