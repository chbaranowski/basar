package basar.rest;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SaleDTO implements Serializable {

	private List<PositionDTO> positions; 

	public void setPositions(List<PositionDTO> positions) {
		this.positions = positions;
	}

	public List<PositionDTO> getPositions() {
		return positions;
	}
	
}
