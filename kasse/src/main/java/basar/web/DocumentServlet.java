package basar.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thoughtworks.xstream.XStream;

import basar.domain.Document;
import basar.domain.DocumentPosition;
import basar.domain.Seller;
import basar.domain.logic.BasarKasseFacade;

public class DocumentServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		ServletContext context = this.getServletConfig().getServletContext();
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(context);
		
		BasarKasseFacade basarKasse = (BasarKasseFacade) springContext.getBean("basarKasse");
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/xml");
		String url = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>";
		out.println(url);
		out.println("<documents>");
		
		XStream xStream = new XStream();
		xStream.alias("document", Document.class);
		xStream.alias("position", DocumentPosition.class);
		
		List<Document> sellerList = basarKasse.getDocumentList();
		for (Document document : sellerList) {
			
			out.print(xStream.toXML(document));
		}
		out.println("</documents>");
	}
	
}
