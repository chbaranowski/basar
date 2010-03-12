package basar.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
public class Seller implements Serializable {

	private Long basarNumber;
	
	private String name;
	
	private List<Position> positions;
	
	@XmlElement
	@Id
	public Long getBasarNumber() {
		return this.basarNumber;
	}
	
	@XmlElement
	@Basic
	public String getName() {
		return this.name;
	}
	
	@XmlTransient
	@OneToMany(mappedBy="seller", fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	public List<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBasarNumber(Long basarNumber) {
		this.basarNumber = basarNumber;
	}
	
	@Transient
	public long getSumme(){
		long summe = 0;
		for (Position position : getPositions()) {
			if(position.getType().equals(PositionType.SALE))
				summe += position.getPrice();
			else
				summe -= position.getPrice();
		}
		return summe;
	}
}
