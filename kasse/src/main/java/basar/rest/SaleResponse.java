package basar.rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SaleResponse implements Serializable {
	
	private String umsatz;
	
	private String gewinn;

	public void setUmsatz(String umsatz) {
		this.umsatz = umsatz;
	}

	public String getUmsatz() {
		return umsatz;
	}

	public void setGewinn(String gewinn) {
		this.gewinn = gewinn;
	}

	public String getGewinn() {
		return gewinn;
	}

}
