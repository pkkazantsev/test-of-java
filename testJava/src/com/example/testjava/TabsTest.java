package com.example.testjava;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager.OnActivityDestroyListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TabsTest extends FragmentActivity{
	AnswersPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    //HashMap<String, Boolean> correctAnswers;
    ArrayAdapter<String> adapter;
    boolean result = false;
	private DatabaseTestJava dbTestJava;
    private static final String DB_TABLE_NAME = "testJavaTable";
	private Cursor c;
    private int curNumber = 0;
    private ArrayList<Integer> randomNumbers = new ArrayList<Integer>();
    private ArrayList<Integer> randomQuestions = new ArrayList<Integer>();
    int points = 0;
    private CountDownTimer timer;
    private int correct = 0;
    private int numAnswer = 0;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("ÇÀÂÅÐØÈÒÜ");
        setContentView(R.layout.pager_answer_fragment);
        mDemoCollectionPagerAdapter = new AnswersPagerAdapter(getSupportFragmentManager());
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        
		dbTestJava = new DatabaseTestJava(getBaseContext());
		try {
			dbTestJava.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dbTestJava.openDataBase();
		c = dbTestJava.query(DB_TABLE_NAME, null, null, null, null, null, null);
		
        
        for(int i = 0; i < c.getCount(); i++)
        {
          randomNumbers.add(i);
        }

        Collections.shuffle(randomNumbers);
        while (randomQuestions.size() != 10) {
        	c.moveToPosition(randomNumbers.get(curNumber));
        	if (getIntent().getIntExtra("position", 0) == 1) {
	         	if (c.getString(10).equals("Îáùèå âîïðîñû")) {
	        		randomQuestions.add(c.getPosition());
	        	}
        	}
        	if (getIntent().getIntExtra("position", 0) == 2) {
	         	if (c.getString(10).equals("Ñèíòàêñèñ")) {
	        		randomQuestions.add(c.getPosition());
	        	}
        	}
        	if (getIntent().getIntExtra("position", 0) == 3) {
	         	if (c.getString(10).equals("Îáúåêòû è êëàññû")) {
	        		randomQuestions.add(c.getPosition());
	        	}
        	}
           	curNumber++;
        }     
        Log.d("TAG", String.valueOf(randomQuestions));

    }
    
    public class AnswersPagerAdapter extends FragmentStatePagerAdapter {

        public AnswersPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new AnswerFragment();
            Bundle args = new Bundle();
            args.putInt(AnswerFragment.ARG_OBJECT, i);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Âîïðîñ  " + (position + 1);
        }
    }
    
    
    
    public class AnswerFragment extends Fragment {
    	private ListView lvAnswers;
    	private TextView tvQuestion;
    	private TextView tvÑomplexity;
    	private Button btnAcceptAnswer;
    	private AnswerAdapter answerAdapter;
        ArrayList<String> answers;
        ViewPager pager;
        private static final String ARG_OBJECT = "answer";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.answer_fragment, container, false);
            
            Bundle args = getArguments();    
    		lvAnswers = (ListView) rootView.findViewById(R.id.lvAnswers_id);
    		tvQuestion = (TextView) rootView.findViewById(R.id.tvQuestion_id);	
    		tvÑomplexity = (TextView) rootView.findViewById(R.id.tvComplexity_id);
    		btnAcceptAnswer = (Button) rootView.findViewById(R.id.btnAcceptAnswer_id);

    	    c.moveToPosition(randomQuestions.get(args.getInt(ARG_OBJECT)));
    	    tvQuestion.setText(c.getString(c.getColumnIndex("question")));
    	    tvÑomplexity.setText("Ñëîæíîñòü " + c.getString(c.getColumnIndex("complexity")));
    	    
    	    answers = new ArrayList<String>();

    	    for (int i = 0; i < 5; i++) {
    	    	if (!(c.getString(c.getColumnIndex("answer" + String.valueOf(i + 1))).toString().equals("NULL"))) {
    	    		answers.add(c.getString(c.getColumnIndex("answer" + String.valueOf(i + 1))));
    	    	}
    	    }

    		if (c.getString(c.getColumnIndex("singleAnswer")).toString().equals("true")) {
    			lvAnswers.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    			answerAdapter = new AnswerAdapter(getBaseContext(), answers, numAnswer, true, c.getInt(c.getColumnIndex("correctAnswers")));
    		}
    		else {
    			lvAnswers.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    			answerAdapter = new AnswerAdapter(getBaseContext(), answers, numAnswer, false, c.getInt(c.getColumnIndex("correctAnswers")));
    		}
    		lvAnswers.setAdapter(answerAdapter);

    		OnClickListener acceptAnswerListener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (v.getId()) {
					case R.id.btnAcceptAnswer_id:
						numAnswer++;
						Bundle args = getArguments(); 
						dbTestJava = new DatabaseTestJava(getBaseContext());
						dbTestJava.openDataBase();
						Cursor c = dbTestJava.query(DB_TABLE_NAME, null, null, null, null, null, null);
			    	    c.moveToPosition(args.getInt(ARG_OBJECT));
			    	    Log.d("TAG", String.valueOf(args.getInt(ARG_OBJECT)));
			    	    
			    	    if (c.getString(c.getColumnIndex("singleAnswer")).toString().equals("true")) {
			    	    	//Log.d("TAG", String.valueOf(lvAnswers.getCheckedItemPosition() + 1));
			    	    	//Log.d("TAG", String.valueOf(c.getColumnIndex("correctAnswers")));
				    	    if (String.valueOf(lvAnswers.getCheckedItemPosition()) == c.getString(c.getColumnIndex("correctAnswers"))) {
				    	    	correct++;
				    	    	Log.d("TAG", "true");
				    	    }
			    	    }
			    	    else {
			    	    	SparseBooleanArray sbArray = lvAnswers.getCheckedItemPositions();
			    	        String strAnswer = "";
			    	        for (int i = 0; i < sbArray.size(); i++) {
			    	        	int key = sbArray.keyAt(i);
			    	        	if (sbArray.get(key)) {
			    	        		strAnswer = strAnswer + String.valueOf(key + 1);
			    	        	}
			    	        }
			    	        if (strAnswer.equals(c.getString(c.getColumnIndex("correctAnswers")))) {
			    	        	correct++;
			    	        }

			    	    }
			    	    
			    	    if (numAnswer == 10) {    		
    	    	
			    	    	Intent intent = new Intent(getBaseContext(), ResultTest.class);
			    	    	Log.d("TAG2", String.valueOf(correct));
			    	    	points = correct - 3;
			    	    	intent.putExtra("result", correct);
			    	    	startActivityForResult(intent, 1);
			    	    	timer.cancel();
			    	    } else {
			    	    	mViewPager.setCurrentItem(args.getInt(ARG_OBJECT) + 1);
			    	    }

			    	    btnAcceptAnswer.setEnabled(false);
			    		dbTestJava.close();
			    		c.close();
			    		
						break;
					default:
						break;
					}
				}
				
			};

			if (args.getInt(ARG_OBJECT) < numAnswer) {
				btnAcceptAnswer.setEnabled(false);
			}
    		btnAcceptAnswer.setOnClickListener(acceptAnswerListener);
            return rootView;
            
        }
        
    	@Override
		public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    		super.onActivityResult(requestCode, resultCode, intent);
    		result = true;
    		
/*    	    if (correctAnswers.size() == 10) {
    	    	Log.d("TAG2", String.valueOf(correctAnswers.size()));
    	    	lvAnswers.getChildAt(1).setBackgroundResource(R.color.correct_answer_beckground);
        		//lvAnswers.getChildAt(3).setBackgroundResource(R.color.correct_answer_beckground);
    	    }*/
    		//Log.d("TAG2", lvAnswers.getChildAt(0).toString());
    		//lvAnswers.getChildAt(0).setBackgroundResource(R.color.correct_answer_beckground);
    		//lvAnswers.getChildAt(3).setBackgroundResource(R.color.correct_answer_beckground);
    		//adapter.getItemViewType(0)
    		//tv.setTextColor(getResources().getColor(R.color.passing_test_beckground));
    		//lvAnswers.getItemAtPosition(1)  		
    		
    	}
        
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(getBaseContext(), MainActivity.class);
			intent.putExtra("points", points);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
    	return super.onOptionsItemSelected(item);
    }
    

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_test, menu);
		final MenuItem item = menu.getItem(0);
		
        timer = new CountDownTimer(600000, 1000) {

            public void onTick(long millisUntilFinished) {
            	item.setTitle("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
            	item.setTitle("Âðåìÿ âûøëî!");
            	finish();
            }
         }.start();
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		
		return true;
	}
	
	@Override
	protected void onDestroy() {
		dbTestJava.close();
		c.close();
		super.onDestroy();
	}

}

