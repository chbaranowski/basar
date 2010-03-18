package basar.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import basar.domain.Position;
import basar.domain.Sale;
import basar.domain.Seller;
import basar.domain.logic.BasarKasseFacade;

@Path("/v1")
public class RestResource extends SpringBeanAutowiringSupport {

	@Autowired
	private BasarKasseFacade basarKasse;

	@GET
	@Path("/seller/{basarNumber}")
	public Seller getSeller(@PathParam("basarNumber") long basarNumber) {
		Seller seller = basarKasse.getSeller(basarNumber);
		return seller;
	}

	@POST
	@Path("/purchase")
	@Produces(MediaType.APPLICATION_JSON)
	public SaleResponse purchase(SaleDTO saleDTO) {
		SaleResponse saleResponse = new SaleResponse();
		Sale sale = new Sale();
		List<PositionDTO> positions = saleDTO.getPositions();
		for (PositionDTO positionDTO : positions) {
			Position position = new Position();
			Seller seller = basarKasse.getSeller(positionDTO.getBasarNumber());
			position.setSeller(seller);
			long priceLong = Math.round(positionDTO.getAmount() * 100);
			position.setPrice(priceLong);
			position.setDescription(positionDTO.getDescription());
			position.setCreateTime(new Date());
			sale.addPosition(position);
		}
		basarKasse.purchase(sale);
		saleResponse.setGewinn(basarKasse.getGewinn());
		saleResponse.setUmsatz(basarKasse.getUmsatz());
		return saleResponse;
	}
	
	@POST
	@Path("/info")
	@Produces(MediaType.APPLICATION_JSON)
	public SaleResponse info() {
		SaleResponse saleResponse = new SaleResponse();
		saleResponse.setGewinn(basarKasse.getGewinn());
		saleResponse.setUmsatz(basarKasse.getUmsatz());
		return saleResponse;
	}
	
	

}
