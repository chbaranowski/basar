package basar.web;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRXmlUtils;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;


public class DocumentServlet extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		ServletContext context = this.getServletConfig().getServletContext();

		try
		{
			String reportName = request.getParameter("report");
			
			String reportFileName = context.getRealPath("WEB-INF/reports/"+reportName+".jasper");
			String xmlFileName = context.getRealPath("WEB-INF/reports/rechung.xml");
			
			URL url = new URL("http://localhost:8080/kasse/rechung.xml");
			
			
			File reportFile = new File(reportFileName);
			if (!reportFile.exists())
				throw new JRRuntimeException("File WebappReport.jasper not found. The report design must be compiled first.");

			Map params = new HashMap();
			Document document = JRXmlUtils.parse(url.openStream());
			params.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, document);
			params.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, "yyyy-MM-dd");
			params.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, "#,##0.##");
			params.put(JRXPathQueryExecuterFactory.XML_LOCALE, Locale.GERMAN);
			params.put(JRParameter.REPORT_LOCALE, Locale.GERMAN);
							
			JasperPrint jasperPrint = 
				JasperFillManager.fillReport(reportFileName, params);
						
			request.getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
			if (jasperPrint != null)
			{
				response.setContentType("application/octet-stream");
				ServletOutputStream ouputStream = response.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint, ouputStream);

				ouputStream.flush();
				ouputStream.close();
			}
			else
			{

				PrintWriter out = response.getWriter();

				response.setContentType("text/html");
				out.println("<html>");
				out.println("<head>");
				out.println("<title>JasperReports - Web Application Sample</title>");
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../stylesheet.css\" title=\"Style\">");
				out.println("</head>");
				
				out.println("<body bgcolor=\"white\">");
		
				out.println("<span class=\"bold\">Empty response.</span>");
		
				out.println("</body>");
				out.println("</html>");
			}
		}
		catch (JRException e)
		{

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			out.println("<html>");
			out.println("<head>");
			out.println("<title>JasperReports - Web Application Sample</title>");
			out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../stylesheet.css\" title=\"Style\">");
			out.println("</head>");
			
			out.println("<body bgcolor=\"white\">");

			out.println("<span class=\"bnew\">JasperReports encountered this error :</span>");
			out.println("<pre>");

			e.printStackTrace(out);

			out.println("</pre>");

			out.println("</body>");
			out.println("</html>");
		}
	}

}
