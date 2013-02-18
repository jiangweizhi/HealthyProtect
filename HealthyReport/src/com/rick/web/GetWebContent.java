package com.rick.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class GetWebContent {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//go2();
		saveAsNewHtml(go2());
		try {
			html2Pdf();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private static String go2(){
		//Below link is for the multiple link page: total 5page
		//String concreateURL = "http://www.yixinhealth.com/%E5%8C%96%E9%AA%8C%E5%92%8C%E6%A3%80%E6%9F%A5/%E5%BF%83%E7%94%B5%E5%9B%BE/";  
		
		//Below link is for multiple title content page: total 3 titles 
		//String concreateURL = "http://www.yixinhealth.com/%E5%8C%96%E9%AA%8C%E5%92%8C%E6%A3%80%E6%9F%A5/%E5%BF%83%E7%94%B5%E5%9B%BE/"; 
		//String concreateURL = "http://www.yixinhealth.com/%E7%97%87%E7%8A%B6%E8%87%AA%E6%9F%A5/%E8%83%B8%E9%97%B7-%E8%83%B8%E7%97%9B/%E5%85%B6%E4%BB%96%E5%87%B6%E9%99%A9%E7%9A%84%E8%83%B8%E7%97%9B-%E4%B8%BB%E5%8A%A8%E8%84%89%E5%A4%B9%E5%B1%82%E5%92%8C%E8%82%BA%E6%A0%93%E5%A1%9E/";
		String concreateURL = "http://www.yixinhealth.com/%E7%97%87%E7%8A%B6%E8%87%AA%E6%9F%A5/%E6%99%95%E5%8E%A5-1/%E5%85%B6%E4%BB%96%E5%8E%9F%E5%9B%A0%E6%99%95%E5%8E%A5/";
		Connection c = Jsoup.connect(concreateURL); 
		c.timeout(100000);
		String result = "";
		try {
			Document doc = c.get();
			//System.out.println(doc.html());  
			//System.out.println(doc.getElementById("content_area").html());
			//Element eles = doc.getElementById("content_area");
			//System.out.println(eles.html());
			
			//Below code is for the multiple link page
			//Elements eles2 = eles.getElementsByAttributeValue("style", "text-align: center;");
			//System.out.println(eles2.html());
			/*for(Element ele: eles2){
				Elements link = ele.getElementsByTag("a");
				//System.out.println(link.html());
				for (int i=0;i<link.size();i++) {
					System.out.println(link.get(i).attr("href"));
				}
			}*/
			
			
			//System.out.println(eles2.html());
			Element eles = doc.getElementById("content_area");
			Elements eles2 = eles.getElementsByTag("img");
			for(int i=0;i<eles2.size();i++){
				eles2.get(i).parent().attr("style", "text-align:center");
				eles2.get(i).parent().parent().nextElementSibling().attr("style", "text-align:center");
				eles2.get(i).attr("style", "width:50%");
			}
			
			Elements eles3 = eles.getElementsByClass("j-header");
			for (Element ele : eles3) {
				Elements eleHeader = ele.getElementsByTag("h2");
				eleHeader.attr("style", "text-align:center");
			}
			System.out.println(eles.html());
			
			//Below code is help to clear out the share icon to like Sina weibo
			Elements eles4 = eles.getElementsByClass("bshare-custom");
			eles4.remove();
			result =  eles.html();
			
			//Elements eles3 = eles.getElementsByClass("n*j-imageSubtitle");
			//System.out.println(eles3.size());
			/*for(int i=0;i<eles3.size();i++){
				eles3.get(i).attr("style", "text-align:center;width:50%");
			}
			System.out.println(eles.html());*/
			//System.out.println(eles2.get(0).attr("src"));
			/*Element eles = doc.getElementById("content_area");
			Elements eles2 = eles.getElementsByClass("j-header");
			for (Element ele : eles2) {
				Elements eleHeader = ele.getElementsByTag("h2");
				System.out.println("Title---->"+eleHeader.html());  
			}
			Elements elesBody = eles.getElementsByClass("j-text");
			System.out.println("Body---->"+elesBody.html());  */
			//System.out.println(eles2.html());  
            /*List<String> nameList = new ArrayList<String>();  
            for (Element ele : eles) {
            	String text = ele.select("h*").text();
            	System.out.println(text);  
                String text = ele.select("span").first().text();  
                if (text.length() > 1 && text.startsWith("▲")) {  
  
                    if (Integer.parseInt(text.substring(1)) > 30) {  
                        // 在这里.html()和.text()方法获得的内容是一样的  
                        System.out.println(ele.select("a").first().html());  
                        nameList.add(ele.select("a").first().text());  
                    }  
                }  
            }  */
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static void saveAsNewHtml(String html) throws IOException{
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8' /><title>Hi Rick</title></head><body>");
		sb.append(html);
		sb.append("</body></html>");
		
		PrintStream printStream = new PrintStream(new FileOutputStream("D:\\rick88997766.html"));
		printStream.println(sb.toString());
		printStream.flush();
		printStream.close();
		/*FileOutputStream fos= new FileOutputStream(new File("D:\\rick8899.html"));
	    OutputStreamWriter osw=new OutputStreamWriter(fos);
	    osw.write(sb.toString());
	    osw.flush();
	    osw.close();*/
	}
	
	private static void html2Pdf() throws DocumentException, IOException{
		String pdfFile = "d:/test88997766.pdf";
		String htmlFile = "d:/rick88997766.html";

		com.itextpdf.text.Document document = new com.itextpdf.text.Document();
		PdfWriter pdfwriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
		pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
		document.open();

		// html文件
		InputStreamReader isr = new InputStreamReader(new FileInputStream(htmlFile), "UTF-8");
		XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, isr);
		document.close();
	}
}
