package basar.domain

import java.io.Serializable

import scala.reflect._

class PositionKey(k: String, n: Int) extends Serializable {

  @BeanProperty
  var kasse = k

  @BeanProperty
  var number = n

  def this() = this("", 0) // auxiliary constructor

  override def toString() = this.kasse + "-" + number;

  override def equals(that: Any): Boolean = {
    if (that == null)
      return false
    if (!that.isInstanceOf[PositionKey])
      return false
    var other = that.asInstanceOf[PositionKey];
    if (kasse == null) {
      if (other.kasse != null)
        return false
    } else if (kasse != other.kasse)
      return false
    if (number != other.number)
      return false
    return true
  }
  
  override def hashCode() : Int = {
	    val prime = 31;
		var result = 1;
		if(kasse == null)
			result = prime * result +  0;
		else
			result = prime * result +  kasse.hashCode();
		result = prime * result + number;
		return result;
  }

}