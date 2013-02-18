package com.rick.async;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.rick.healthyreport.PdfActivity;

public class MyAsync extends AsyncTask<Void,Void,Void>{

	protected String resultHtml = "";
	protected Context context;
	
	public MyAsync(Context context) {
		this.context = context;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		resultHtml = go2();
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		//Log.e("Rick", resultHtml);
		try {
			saveAsNewHtml(resultHtml);
			try {
				html2Pdf();
			} catch (DocumentException e) {
				e.printStackTrace();
			} finally {
				Toast.makeText(context, "Finish making Pdf", Toast.LENGTH_SHORT).show();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	
	private String go2(){
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
			Element eles = doc.getElementById("content_area");
			/*Elements eles2 = eles.getElementsByTag("img");
			for(int i=0;i<eles2.size();i++){
				eles2.get(i).parent().attr("style", "text-align:center");
				eles2.get(i).parent().parent().nextElementSibling().attr("style", "text-align:center");
				eles2.get(i).attr("style", "width:50%");
			}*/
			
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

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private void saveAsNewHtml(String html) throws IOException{
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8' /><title>Hi Rick</title></head><body>");
		sb.append(html);
		sb.append("</body></html>");
		
		File file = new File(Environment.getExternalStorageDirectory()+File.separator+"rickpdf", "pdfTest.html");
		/*if(!file.exists()){
			file.mkdirs();
		}*/
		
		//PrintStream printStream = new PrintStream(new FileOutputStream("D:\\rick88997766.html"));
		/*PrintStream printStream = new PrintStream(new FileOutputStream(file));
		printStream.println(sb.toString());
		printStream.flush();
		printStream.close();*/
		
		FileOutputStream fos = null;
		fos = new FileOutputStream(file);
		fos.write(sb.toString().getBytes());
		fos.flush();
		fos.close();
	}
	
	private void html2Pdf() throws DocumentException, IOException{
		File htmlFile = new File(Environment.getExternalStorageDirectory()+File.separator+"rickpdf", "pdfTest.html");
		File pdfFile = new File(Environment.getExternalStorageDirectory()+File.separator+"rickpdf","rick.pdf");
		//String pdfFile = "d:/test88997766.pdf";
		//String htmlFile = "d:/rick88997766.html";

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
