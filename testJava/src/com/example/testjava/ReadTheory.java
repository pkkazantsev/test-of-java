package com.example.testjava;

import java.io.IOException;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ReadTheory extends Activity {
	private DatabaseTestJava dbTestJava;
	private static final String DB_TABLE_NAME = "Theory";
	private TextView tvTheory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_theory_layout);
		setTitle("“≈Œ–»ﬂ");
		tvTheory = (TextView) findViewById(R.id.tvTheory_id);
		
		dbTestJava = new DatabaseTestJava(getBaseContext());
		try {
			dbTestJava.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dbTestJava.openDataBase();
		Cursor c = dbTestJava.query(DB_TABLE_NAME, null, null, null, null, null, null);
		c.moveToPosition(0);
		

		switch (getIntent().getIntExtra("position", 0)) {
		case 1:
			tvTheory.setText(String.valueOf(c.getString(c.getColumnIndex("generalQuestions"))));
			break;
		case 2:
			tvTheory.setText(String.valueOf(c.getString(c.getColumnIndex("syntax"))));
			break;
		case 3:
			tvTheory.setText(String.valueOf(c.getString(c.getColumnIndex("objectsAndClasses"))));
			break;
		default:
			break;
		}
		
		dbTestJava.close();
		c.close();
		
	}
	
}
