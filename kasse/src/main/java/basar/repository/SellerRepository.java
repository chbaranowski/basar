package basar.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import basar.domain.Seller;

@Transactional
public interface SellerRepository extends Repository<Seller, Long>, CrudRepository<Seller, Long> {
	
	
	// Seller save(Seller seller);
	
	// Seller findOne(Long basarnumber);

	Iterable<Seller> findAll(Sort sort);
	
	List<Seller> findByName(String name, Sort sort);
	
	Seller findOneByBasarNumberAndName(Long basarnumber, String name);
	
}
