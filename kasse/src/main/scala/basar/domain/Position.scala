package basar.domain

import java.io.Serializable
import java.util.List
import java.util.ArrayList
import java.util.Date;

import javax.persistence._
import javax.xml.bind.annotation._

import scalaj.collection.Imports._
import scala.reflect._


@Entity
class Position extends Serializable {
	
	@EmbeddedId
	@BeanProperty 
	var positionKey : PositionKey = null
	
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=classOf[Seller], optional=false)
	@BeanProperty 
	var seller : Seller = null
	
	@Basic(optional=false)
	@BeanProperty 
	var createTime = new Date()
	
	@Basic(optional=false)
	@BeanProperty
	var price = 0L
	
	@Basic
	@BeanProperty
	var description = ""
		
	@Basic(optional=false)
	@BeanProperty
	@Column(name="type")
	var positionType = PositionType.SALE
	
}