package basar.dao

import java.util.List;

import basar.domain._;

trait PositionDao {
	
	def getPosition(positionKey : PositionKey) : Position
	
	def insertPosition(position : Position)

	def updatePosition(position : Position)
	
	def deletePosition(position : Position)
	
	def createPositionKey() : PositionKey
	
	def getPositionList() : List[Position]

}