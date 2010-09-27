package basar.domain

import java.io.Serializable
import java.util.List
import java.util.ArrayList

import scala.reflect._

class Document extends Serializable {

  @BeanProperty
  var basarNumber : Long = -1L

  @BeanProperty
  var name = "-"

  @BeanProperty
  val positions: List[DocumentPosition] = new ArrayList[DocumentPosition]

  @BeanProperty
  var summe = ""

  @BeanProperty
  var rate = ""

  @BeanProperty
  var proceeds = ""

  @BeanProperty
  var prozent: String = null
  
  def addPosition(position : DocumentPosition) = {
	  positions.add(position)
  }
  
  def removePosition(position : DocumentPosition) = {
	  positions.remove(position)
  }

}