package basar.domain

import java.io.Serializable
import java.util.List
import java.util.ArrayList

import javax.persistence._
import javax.xml.bind.annotation._

import scala.reflect._
import scalaj.collection.Imports._

@Entity
@XmlRootElement
class Seller extends Serializable {

	@XmlElement
	@Id
	@BeanProperty
	var basarNumber = 0L
	
	@XmlElement
	@Basic
	@BeanProperty
	var name = ""
	
	@XmlTransient
	@BeanProperty
	@OneToMany(cascade = Array(CascadeType.ALL), fetch = FetchType.EAGER, mappedBy="seller")
	var positions : List[Position] = null
	
	@Transient
	def getSumme() : Long = {
		var summe = 0L;
		positions.asScala.foreach(position => 
			if(position.positionType.equals(PositionType.SALE)){
				summe += position.getPrice();
			}
			else{
				summe -= position.getPrice();
			}
		);
		return summe;
	}
	
}