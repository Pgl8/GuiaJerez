package com.pgl8.sherryguia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		String wine = extras.getString("wine");

		Toast.makeText(this, wine, Toast.LENGTH_LONG).show();
	}
}
