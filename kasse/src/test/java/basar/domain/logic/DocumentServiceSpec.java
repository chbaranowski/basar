package basar.domain.logic;

import static org.easymock.EasyMock.*;

import java.util.ArrayList;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

import basar.dao.SellerDao;
import basar.domain.Document;
import basar.domain.Seller;
import basar.domain.logic.impl.DocumentServiceImpl;

@RunWith(JDaveRunner.class)
public class DocumentServiceSpec extends Specification<DocumentService>{

	public class EmptyDocumentList {
		
		//private DocumentService documentService;
		
		private SellerDao mockSellerDao;
		
		public DocumentService create(){
			DocumentServiceImpl service = new DocumentServiceImpl();
			mockSellerDao = createMock(SellerDao.class);
			service.setSellerDao(mockSellerDao);
			return service;
		}
		
		public void callGetDocumentListWithEmptySeller(){
			expect(mockSellerDao.getSellerList()).andReturn(new ArrayList<Seller>());
			replay(mockSellerDao);
			//specify(documentService.getDocumentList(), must.be.equals(new ArrayList<Document>()));
		}
		
		
		
	}
	
}
