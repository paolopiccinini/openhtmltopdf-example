package com.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.w3c.dom.Document;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

public class SimpleUsage {
	
	public static void main(String[] args) throws Exception { 
        try (OutputStream os = new FileOutputStream("C:\\Users\\p.piccinini\\Documents\\openhtmltopdf\\out.pdf")) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withW3cDocument(html5ParseDocument("file:///C:/Users/p.piccinini/Documents/openhtmltopdf/in.html", 100), "file:///C:/Users/p.piccinini/Documents/openhtmltopdf/in.html");
            builder.toStream(os);
            builder.run();
        }
    }
	
	public static Document html5ParseDocument(String urlStr, int timeoutMs) throws IOException 
	{
		URL url = new URL(urlStr);
		org.jsoup.nodes.Document doc;
		
		if (url.getProtocol().equalsIgnoreCase("file")) {
			doc = Jsoup.parse(new File(url.getPath()), "UTF-8");
		}
		else {
			doc = Jsoup.parse(url, timeoutMs);	
		}
		// Should reuse W3CDom instance if converting multiple documents.
		return new W3CDom().fromJsoup(doc);
	}

}
