package basar.domain

import java.io.Serializable

import scala.reflect._

class DocumentPosition extends Serializable {

  @BeanProperty
  var position = -1

  @BeanProperty
  var price = ""

  @BeanProperty
  var description = ""
	  
}