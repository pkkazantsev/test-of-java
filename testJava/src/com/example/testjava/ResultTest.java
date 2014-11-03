package com.example.testjava;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ResultTest extends Activity implements OnClickListener {
	private TextView tvResultTest;
	private TextView tvPoints;
	private Button btnViewTest;
	private DatabaseTestJava dbTestJava;
	private static final String DB_TABLE_NAME = "pointsTable";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_test_layout);
		setTitle("РЕЗУЛЬТАТ");
		getActionBar().setDisplayShowHomeEnabled(false);
		tvResultTest = (TextView) findViewById(R.id.tvResultTest_id);
		tvPoints = (TextView) findViewById(R.id.tvPoints_id);
		btnViewTest = (Button) findViewById(R.id.btnViewTest_id);
		btnViewTest.setText("Посмотреть правильные ответы");
		btnViewTest.setOnClickListener(this);
		Intent intent = getIntent();
		tvResultTest.setText("Количество правильных ответов: " + String.valueOf(intent.getIntExtra("result", 0)) + " из 10");

		tvPoints.setText("Количество набранных очков: " + String.valueOf(intent.getIntExtra("result", 0) - 3));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnViewTest_id:
			Intent intent = getIntent();
			dbTestJava = new DatabaseTestJava(this);
			dbTestJava.openDataBase();
			ContentValues cv = new ContentValues();
			cv.put("points", intent.getIntExtra("result", 0) - 3);
			dbTestJava.update(DB_TABLE_NAME, cv, "_id = " + 1, null);
			dbTestJava.close();
			finish();
			break;

		default:
			break;
		}
		
	}

}
