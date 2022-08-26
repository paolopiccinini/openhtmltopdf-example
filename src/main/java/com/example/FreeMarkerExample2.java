package com.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerExample2 {
	
private Configuration freemarkerConfiguration;
	
	public FreeMarkerExample2() {
		freemarkerConfiguration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
	}
	
	public static void main(String[] args) throws IOException, TemplateException {
		FreeMarkerExample2 example = new FreeMarkerExample2();
		example.generatePdf();
		
	}
	
	public void generatePdf() throws IOException, TemplateException {
		Reader targetReader = new InputStreamReader(getClass()
				.getClassLoader().getResourceAsStream("in2.ftl"));
		JavaBean example = new JavaBean();
		example.title = "title";
		Template template = new Template("in2", targetReader, freemarkerConfiguration);
		//String title = "title"; NOT WORKING
		//String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(title);
		String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, example);
		org.w3c.dom.Document doc = createWellFormedHtml(htmlContent);
		try (OutputStream os = new FileOutputStream("C:\\Users\\p.piccinini\\Documents\\openhtmltopdf\\out.pdf")) {
			PdfRendererBuilder builder = new PdfRendererBuilder();
			builder.useFastMode();
			builder.withW3cDocument(doc, "");
			builder.toStream(os);
			builder.run();
		}
//		you can use also a ByteArrayOutputStream if u need the btye array to expese in a REST API
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		PdfRendererBuilder builder = new PdfRendererBuilder();
//		builder.useFastMode();
//		builder.withW3cDocument(doc, "");
//		builder.toStream(baos);
//		builder.run();
//		baos.close()
//		return baos.toByteArray();
        
	}
	
	private org.w3c.dom.Document createWellFormedHtml(String inputHTML) {
	    Document document = Jsoup.parse(inputHTML, "UTF-8");
	    document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
	    return new W3CDom().fromJsoup(document);
	}

}
