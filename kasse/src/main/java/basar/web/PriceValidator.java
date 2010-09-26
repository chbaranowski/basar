package basar.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceValidator {

	
	public static void validatePrice(String price) throws NotValidPriceException{	
		Pattern p = Pattern.compile("[+]?[0-9]+");
		Matcher m = p.matcher(price.replace(",", ""));
		if(!m.matches()){
			throw new NotValidPriceException("Der Preis :"+price +" ist kein gültiger Geldbetrag");
		}
		
		if(price.contains(",")){
		 String komma = price.substring(price.indexOf(',')+1);
		 if(komma.length() > 2){
		   throw new NotValidPriceException("Der Preis :"+price +" ist kein gültiger Geldbetrag zu viele Kommastellen");
		 }
		 if(!komma.equals("00") && !komma.equals("50") && !komma.equals("0") && !komma.equals("5")){
			 throw new NotValidPriceException("Der Preis :"+price +" ist kein gültiger Geldbetrag");
		 }
		}
	}
	
	
}
