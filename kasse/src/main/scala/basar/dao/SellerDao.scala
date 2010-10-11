package basar.dao

import java.util.List;

import basar.domain.Seller;

trait SellerDao {
	
	def getSeller(basarNumber : Long) : Seller
	
	def insertSeller(seller : Seller)
	
	def updateSeller(seller : Seller)
	
	def deleteSeller(seller : Seller)
	
	def getSellerList() : List[Seller]

}