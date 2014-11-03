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
	private TextView tvPointsTest;
	private DatabaseTestJava dbTestJava;
	private static final String DB_TABLE_NAME = "pointsTable";
	private RadioButton rbOneLvl;
	private RadioButton rbTwoLvl;
	private RadioButton rbThreeLvl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnGoTest = (Button) findViewById(R.id.btnGoTest_id);
		tvPointsTest = (TextView) findViewById(R.id.tvPointTest_id);
		rbOneLvl = (RadioButton) findViewById(R.id.radioButton1_id);
		rbTwoLvl = (RadioButton) findViewById(R.id.radioButton2_id);
		rbThreeLvl = (RadioButton) findViewById(R.id.radioButton3_id);
		btnGoTest.setOnClickListener(this);
		Intent intent = getIntent();
		dbTestJava = new DatabaseTestJava(this);
		
		try {
			dbTestJava.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}

		dbTestJava.openDataBase();
		Cursor c = dbTestJava.query(DB_TABLE_NAME, null, null, null, null, null, null);
		c.moveToPosition(0);
		tvPointsTest.setText(String.valueOf(c.getInt(c.getColumnIndex("points"))));
		dbTestJava.close();
		c.close();
		
		if (intent.getIntExtra("points", 0) < 2) {
			rbTwoLvl.setEnabled(false);
			rbThreeLvl.setEnabled(false);
		}
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
			Intent intent = new Intent(this, TabsTest.class);
			if (rbOneLvl.isChecked()) {
				Log.d("TAG2", "1");
			}
			if (rbTwoLvl.isChecked()) {
				Log.d("TAG2", "2");
			}
			if (rbThreeLvl.isChecked()) {
				Log.d("TAG2", "3");
			}
			
			startActivity(intent);
			finish();
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
				/*rbTwoLvl.setChecked(false);
				rbThreeLvl.setChecked(false);*/
			}
			break;
		case R.id.radioButton2_id:
			if (checked) {
				//tvWhoOrWhom.setText(R.string.textTvWhom);
				//radioButton1.setChecked(false);
			}
			break;
		case R.id.radioButton3_id:
			if (checked) {
				//tvWhoOrWhom.setText(R.string.textTvWhom);
				//radioButton1.setChecked(false);
			}
			break;
		default:
			break;
		}
	}

}
