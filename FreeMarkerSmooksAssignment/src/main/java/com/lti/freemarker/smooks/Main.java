package com.lti.freemarker.smooks;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.milyn.Smooks;
import org.milyn.SmooksException;
import org.milyn.container.ExecutionContext;
import org.milyn.event.report.HtmlReportGenerator;
import org.milyn.io.StreamUtils;
import org.milyn.payload.JavaResult;
import org.xml.sax.SAXException;

import com.lti.freemarker.smooks.model.Book;
import com.lti.freemarker.smooks.model.BookOrderInformation;
import com.lti.freemarker.smooks.model.ContextInformation;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

public class Main {
	static final Logger logger = Logger.getLogger(Main.class);
	private static Configuration cfg = null;

	static {
		cfg = new Configuration(new Version("2.3.0"));
	}
	
	
	public static void main(String[] args) throws SmooksException, IOException, SAXException {
		
		try {
			// Load template
			Template template = cfg.getTemplate("resources/BuildXMLTemplate.ftl");
			
			// Create data for template
			Map<String, Object> templateData = new HashMap<String, Object>();

			List<Book> BookOrderItems = Arrays
					.asList(new Book("Head First Design Pattern",800, "Freeman and Freeman", "Oreilly", LocalDate.of(2020, 05, 01)),
							new Book("Head First Java",950, "Freeman and Freeman", "Oreilly", LocalDate.of(2020, 05,01)),
							new Book("Head First JSP and Servlets",920, "Freeman and Freeman", "Oreilly", LocalDate.of(2020, 05,01))
							);
			templateData.put("bookOrderItems", BookOrderItems);
			
			List<ContextInformation>ContextInformation = Arrays.asList(
					new ContextInformation("qwerfgh3432","website","India","Maharashtra","Mumbai")
					);
			templateData.put("contextInformation", ContextInformation);
			
			// Write output on console example 
			logger.info("========= Binding Java object to xml from freemarker template===========");
			StringWriter out = new StringWriter();
			template.process(templateData, out);
			logger.info(out.getBuffer().toString());
			out.flush();
			logger.info("===============================");
			
			
			// Write data to the file
			Writer file = new FileWriter(new File("BuildXMLTemplate.xml"));
			template.process(templateData, file);
			file.flush();
			file.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		logger.info("\n\n");
		logger.info("==============Passing above xml to java binding==============");
		logger.info(new String(messageIn));
		logger.info("======================================\n");
		
		BookOrderInformation bookorderinformation = Main.runSmooks();
		
		logger.info("============Order Javabeans===========");
		logger.info("ContextInformation  - SessionId: " + bookorderinformation.getContextInformation().getSessionId());
		logger.info(" 		    - BookingMode: " + bookorderinformation.getContextInformation().getBookingMode());
		logger.info("       		    - Country:    " + bookorderinformation.getContextInformation().getCountry());
		logger.info("       		    - State:    " + bookorderinformation.getContextInformation().getState());
		logger.info("       		    - City:  " + bookorderinformation.getContextInformation().getCity());	
		logger.info("\n"); 
		logger.info("BookOrderList:");
		logger.info(" ");
		
  for (int i = 0; i < bookorderinformation.getBookOrderItems().size(); i++) {
			Book book = bookorderinformation.getBookOrderItems().get(i);
			book.setBookOrderId("A4RTPDF");
			book.setDeliveryDate(LocalDate.of(2020, 05, 10));
			book.setDispatchDate(LocalDate.of(2020, 05, 05));
			logger.info("       (" + (i + 1) + ") BookOrderId:  " + book.getBookOrderId());
			logger.info("       (" + (i + 1) + ") BookName:  " + book.getBookName());
			logger.info("       (" + (i + 1) + ") BookPrice:  " + book.getBookPrice());
			logger.info("       (" + (i + 1) + ") AuthorName:  " + book.getAuthorName());
			logger.info("       (" + (i + 1) + ") PublisherName:  " + book.getPublisherName());
			logger.info("       (" + (i + 1) + ") OrderBookingDate:  " + book.getOrderBookingDate());
			logger.info("       (" + (i + 1) + ") DispatchDate:  " + book.getDispatchDate());
			logger.info("       (" + (i + 1) + ") DeliveryDate:  " + book.getDeliveryDate());
			
			
			logger.info("======================================");
			
		}
	}
	private static byte[] messageIn = readInputMessage();

	protected static BookOrderInformation runSmooks() throws IOException, SAXException, SmooksException {

		// Instantiate Smooks with the config
		Smooks smooks = new Smooks("smooks-config.xml");

		try {
			// Create an exec context - no profiles
			ExecutionContext executionContext = smooks.createExecutionContext();
			
			// The result of this transform is a set of Java objects
			JavaResult result = new JavaResult();
			
			// Configure the execution context to generate a report
			executionContext.setEventListener(new HtmlReportGenerator("target/report/report.html"));
		
			// Filter the input message to extract, using the execution context
			smooks.filterSource(executionContext, new StreamSource(new ByteArrayInputStream(messageIn)), result);
			return (BookOrderInformation) result.getBean("BookOrderInformation");
		} finally {
			smooks.close();
		}
}
	private static byte[] readInputMessage() {
		
		try {
			return StreamUtils.readStream(new FileInputStream("BuildXMLTemplate.xml"));
		} catch (IOException e) {
			e.printStackTrace();
			return "<no-message/>".getBytes();
		}
	}}
