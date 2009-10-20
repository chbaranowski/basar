package basar.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Document implements Serializable {

	private long basarNumber = -1;

	private String name = "-";
	
	private final List<DocumentPosition> positions 
		= new ArrayList<DocumentPosition>();
	
	private String summe = "";
	
	private String rate = "";
	
	private String proceeds = "";
	
	private String prozent;

	public String getProzent() {
		return prozent;
	}

	public void setProzent(String prozent) {
		this.prozent = prozent;
	}

	public boolean addPosition(DocumentPosition o) {
		return positions.add(o);
	}

	public boolean removePosition(DocumentPosition o) {
		return positions.remove(o);
	}

	public long getBasarNumber() {
		return basarNumber;
	}

	public void setBasarNumber(long basarNumber) {
		this.basarNumber = basarNumber;
	}

	public String getName() {
		if(name == null)
			return "keine Angabe";
		return name;
	}

	public void setName(String name) {
		if(name != null && !name.equals(""))
			this.name = name;
	}

	public String getSumme() {
		return summe;
	}

	public void setSumme(String summe) {
		this.summe = summe;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getProceeds() {
		return proceeds;
	}

	public void setProceeds(String proceeds) {
		this.proceeds = proceeds;
	}

	public List<DocumentPosition> getPositions() {
		return positions;
	}

	
	
}
