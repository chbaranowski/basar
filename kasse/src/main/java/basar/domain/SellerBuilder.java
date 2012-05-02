package basar.domain;

import java.util.ArrayList;
import java.util.List;

public class SellerBuilder {

	private Long basarNumber;
	
	private String name;
	
	private final List<Position> positions = new ArrayList<Position>();

	public SellerBuilder name(String name){
		this.name = name;
		return this;
	}
	
	public SellerBuilder basarNumber(Long basarNumber){
		this.basarNumber = basarNumber;
		return this;
	}
	
	public SellerBuilder addPosition(Position position){
		positions.add(position);
		return this;
	}
	
	public Seller build(){
		Seller seller = new Seller();
		seller.setBasarNumber(basarNumber);
		seller.setName(name);
		seller.setPositions(positions);
		return seller;
	}
	
	public static class PositionBuilder {

		private PositionKey key;
		private long price;
		private String desc;

		public PositionBuilder id(PositionKey key){
			this.key = key;
			return this;
		}
		
		public PositionBuilder price(long price){
			this.price = price;
			return this;
		}
		
		public PositionBuilder desc(String desc){
			this.desc = desc;
			return this;
		}
		
		public Position build(){
			Position position = new Position();
			position.setPositionKey(key);
			position.setPrice(price);
			position.setDescription(desc);
			return position;
		}
		
	}
	
	public static SellerBuilder seller(){
		return new SellerBuilder();
	}
	
	public static PositionBuilder position(){
		return new PositionBuilder();
	}
}
