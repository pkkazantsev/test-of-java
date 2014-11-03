package com.example.testjava;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class AnswerAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> answers;
	private LayoutInflater lInflater;
	int answersSize;
	boolean choiceMode;
	int correctAnswers;
	
	public AnswerAdapter(Context context, ArrayList<String> answers, int answersSize, boolean choiceMode, int correctAnswers ) {
		this.context = context;
		this.answers = answers;
		this.answersSize = answersSize;
		this.choiceMode = choiceMode;
		this.correctAnswers = correctAnswers;
		lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return answers.size();
	}

	@Override
	public String getItem(int position) {
		return answers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			if (choiceMode) {
				convertView = lInflater.inflate(R.layout.item_single_choice, parent, false);
			}
			else {
				convertView = lInflater.inflate(R.layout.item_multiple_choice, parent, false);
			}

		}
		
		String answer = getItem(position);



		ArrayList<Integer> listCorrectAnswer = new ArrayList<Integer>();
		listCorrectAnswer.add(correctAnswers);
		
		if (choiceMode && answersSize == 10 && position == correctAnswers - 1) {
			convertView.setBackgroundResource(R.color.correct_answer_beckground);
		}
		
		String str = Integer.toString(correctAnswers);
		
		for (int i = 0; i < str.length(); i++) {
			if (!choiceMode && answersSize == 10 && position == (Integer.parseInt(String.valueOf(str.charAt(i))) ) - 1 ) {
				convertView.setBackgroundResource(R.color.correct_answer_beckground);
			}
		}
		
		CheckedTextView ctvAnswer;
		if (choiceMode) {
			ctvAnswer = (CheckedTextView) convertView.findViewById(R.id.ctvSingleAnswer_id);
		}
		else {
			ctvAnswer = (CheckedTextView) convertView.findViewById(R.id.ctvMultipleAnswer_id);
		}
		ctvAnswer.setText(answer);

		return convertView;
	}

}
