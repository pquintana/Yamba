package com.artemisa.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class StatusActivity extends Activity implements OnClickListener {
	private static final String TAG = "StatusActivity";
	private Button updateButton;
	private TextView editText;
	private Twitter twitter;

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

		editText = (TextView) findViewById(R.id.editText);
		
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

}
