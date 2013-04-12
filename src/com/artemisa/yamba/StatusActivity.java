package com.artemisa.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class StatusActivity extends Activity implements OnClickListener, TextWatcher {
	private static final String TAG = "StatusActivity";
	private Button updateButton;
	private EditText editText;
	private Twitter twitter;
	private TextView textCount;

	//asynchronously posts to twitter
	
	class PostToTwitter extends AsyncTask<String, Integer, String>{
	
	@Override
	
	protected String doInBackground(String...statuses) {
		try {
		  Twitter.Status status = twitter.updateStatus(statuses[0]);
		  return status.text;
		}catch (TwitterException e) {
		  Log.e(TAG, e.toString());
		  e.printStackTrace();
		  return "Failed to post";
		}
	}
	
	@Override
	protected void onProgressUpdate(Integer... values){
		super.onProgressUpdate(values);
	}
	
	@Override
	
	protected void onPostExecute(String result) {
		Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
	}
	
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);

		// Find views
		updateButton = (Button) findViewById(R.id.buttonUpdate);
		updateButton.setOnClickListener(this);

		editText = (EditText) findViewById(R.id.editText);
		
		editText.addTextChangedListener(this);
		
		textCount = (TextView) findViewById(R.id.textCount);
		textCount.setText(Integer.toString(140));
		textCount.setTextColor(Color.GREEN);
		
		twitter = new Twitter("student", "password");
		twitter.setAPIRootUrl("http://yamba.marakana.com/api");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status, menu);
		return true;
	}

	// Called when button is clicked
	public void onClick(View v) {
	// try{
	//	twitter.setStatus(editText.getText().toString());
	//	Log.d(TAG, "OnCLick: " + editText.getText().toString());
	//  } catch (TwitterException e) {
	//	  Log.e(TAG, e.toString());
	//	  e.printStackTrace();
	//  }
		
		String status = editText.getText().toString();
		new PostToTwitter().execute(status);
	}

@Override

public void afterTextChanged(Editable s){
	Log.d(TAG, "afterTextChanged()");
	
	int count = 140-s.length();
	String text = Integer.toString(count);
	
	textCount.setText(text);
	
	if (count>10) {
		textCount.setTextColor(Color.GREEN);
	} else {
		if (count>=1 && count <=10){
			textCount.setTextColor(Color.YELLOW);
		} else {
			textCount.setTextColor(Color.RED);
		}
	}
}

@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	// TODO Auto-generated method stub
	
}

@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
	// TODO Auto-generated method stub
	
}


}
