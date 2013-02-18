package com.rick.healthyreport;

import com.rick.async.MyAsync;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PdfActivity extends Activity{

	protected Button btnPdf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.test_pdf_activity);
		
		init();
	}
	
	private void init(){
		btnPdf = (Button)findViewById(R.id.btn_pdf);
		
		btnPdf.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(PdfActivity.this, "Start to make Pdf", Toast.LENGTH_SHORT).show();
				
				MyAsync myAsync = new MyAsync(PdfActivity.this);
				myAsync.execute();
			}
		});
	}
	
}
