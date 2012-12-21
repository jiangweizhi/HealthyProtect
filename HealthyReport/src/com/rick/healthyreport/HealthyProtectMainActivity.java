package com.rick.healthyreport;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.TextView;

public class HealthyProtectMainActivity extends Activity {

	TextView mTxtviewTitle;
	WebView mWebviewReadContent;
	public static String SDCardRoot=Environment.getExternalStorageDirectory()+"/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_healthy_protect_main);
		
		initControl();
		
		//showDialog();
		
		loadWebview();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_healthy_protect_main, menu);
		return true;
	}
	
	private void initControl(){
		mTxtviewTitle = (TextView)findViewById(R.id.txtview_healthy_recource_title);
		mWebviewReadContent = (WebView)findViewById(R.id.webview_healthy_recource_view);
		
		mTxtviewTitle.setText("RickTest");
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void loadWebview(){
		mWebviewReadContent.getSettings().setJavaScriptEnabled(true);
		mWebviewReadContent.getSettings().setBuiltInZoomControls(true);
		mWebviewReadContent.getSettings().setSupportZoom(true);
		mWebviewReadContent.getSettings().setUseWideViewPort(true);
		mWebviewReadContent.getSettings().setLoadWithOverviewMode(true);
		mWebviewReadContent.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		mWebviewReadContent.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		
		mWebviewReadContent.loadUrl("file:///"+SDCardRoot+"ricktest/学看心电图.html");
	}

	private String getCellPhoneInfo(){
		TelephonyManager mTm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);  
		String mtype = android.os.Build.MODEL; 
		return mtype;
	}
	
	private void showDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder
			.setTitle("Hi Rick")
			.setMessage(getCellPhoneInfo())
			.setPositiveButton("I know it!Thanks", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
		
		builder.create().show();
	}
	
}
