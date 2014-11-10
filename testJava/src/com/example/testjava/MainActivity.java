package com.example.testjava;

import java.io.IOException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private Button btnGoTest;
	private Button btnTheory;
	private TextView tvPointsTest;
	private DatabaseTestJava dbTestJava;
	private static final String DB_TABLE_NAME = "pointsTable";
	private RadioButton rbOne;
	private RadioButton rbTwo;
	private RadioButton rbThree;
	private RadioButton rbFour;
	private RadioButton rbFive;
	private TextView tvText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnGoTest = (Button) findViewById(R.id.btnGoTest_id);
		btnTheory = (Button) findViewById(R.id.btnTheory_id);
		rbOne = (RadioButton) findViewById(R.id.radioButton1_id);
		rbTwo = (RadioButton) findViewById(R.id.radioButton2_id);
		rbThree = (RadioButton) findViewById(R.id.radioButton3_id);
		tvText= (TextView) findViewById(R.id.tvText_id);
		btnGoTest.setOnClickListener(this);
		btnTheory.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnGoTest_id:
			Intent intentTest = null;
			if (rbOne.isChecked()) {
				intentTest = new Intent(this, TabsTest.class);
				intentTest.putExtra("position", 1);
			}
			if (rbTwo.isChecked()) {
				intentTest = new Intent(this, TabsTest.class);
				intentTest.putExtra("position", 2);
			}
			if (rbThree.isChecked()) {
				intentTest = new Intent(this, TabsTest.class);
				intentTest.putExtra("position", 3);
			}
			startActivity(intentTest);
			finish();
			break;
		case R.id.btnTheory_id:
			Intent intentTheory = null;
			if (rbOne.isChecked()) {
				intentTheory = new Intent(this, ReadTheory.class);
				intentTheory.putExtra("position", 1);
			}
			
			if (rbTwo.isChecked()) {
				intentTheory = new Intent(this, ReadTheory.class);
				intentTheory.putExtra("position", 2);
			}
			
			if (rbThree.isChecked()) {
				intentTheory = new Intent(this, ReadTheory.class);
				intentTheory.putExtra("position", 3);
			}
			startActivity(intentTheory);
			break;
		default:
			break;
		}
		
	}
	
	public void onRadioButtonClicked (View view) {
		boolean checked = ((RadioButton) view).isChecked();
		switch (view.getId()) {
		case R.id.radioButton1_id:
			if (checked) {
				tvText.setText("ѕосмотреть теорию или начать тест по теме:\n" + rbOne.getText());
			}
			break;
		case R.id.radioButton2_id:
			if (checked) {
				tvText.setText("ѕосмотреть теорию или начать тест по теме:\n" + rbTwo.getText());
			}
			break;
		case R.id.radioButton3_id:
			if (checked) {
				tvText.setText("ѕосмотреть теорию или начать тест по теме:\n" + rbThree.getText());
			}
			break;
		default:
			break;
		}
	}

}
