package com.ime.jumble;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ime.jumble.LevelSelector.Level;

public class Jumble extends Activity {
	TextView jumbledText, feedBackText;
	EditText answerText;
	String currentWord;
	ArrayList<String> words;
	boolean isNewWord;
	int[] letterFreq;
	Level level;
	Button nextButon, showAnsButtob;

	public static String easyFileName = "easy_words.txt";
	public static String hardFileName = "hard_words.txt";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		isNewWord = false;
		words = new ArrayList<String>();
		level = LevelSelector.getLevel();
		loadWordsfromApp();
		// loadWordsfromSdCard();
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		jumbledText = new TextView(this);
		feedBackText = new TextView(this);
		answerText = new EditText(this);
		LinearLayout buttonLayout = new LinearLayout(this);
		buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
		nextButon = new Button(this);
		showAnsButtob = new Button(this);
		nextButon.setWidth(0);
		showAnsButtob.setWidth(0);
		LayoutParams param = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
		nextButon.setLayoutParams(param);
		showAnsButtob.setLayoutParams(param);
		nextButon.setText("NEXT");
		nextButon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				isNewWord = true;
				setNewWord();

			}

		});
		showAnsButtob.setText("Show Answer");
		showAnsButtob.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				feedBackText.setText(currentWord);
				nextButon.setText("NEXT");

			}

		});
		answerText.setMaxHeight(30);
		jumbledText.setHeight(100);
		jumbledText.setTextColor(Color.RED);
		jumbledText.setTextSize(35);
		int height = LinearLayout.LayoutParams.FILL_PARENT;
		int width = LinearLayout.LayoutParams.WRAP_CONTENT;
		layout.addView(jumbledText,
				new LinearLayout.LayoutParams(height, width));
		layout.addView(answerText, new LinearLayout.LayoutParams(height, width));
		layout.addView(feedBackText, new LinearLayout.LayoutParams(height,
				width));
		buttonLayout.addView(nextButon);
		buttonLayout.addView(showAnsButtob);
		layout.addView(buttonLayout, new LinearLayout.LayoutParams(height,
				width));

		answerText.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable currentSolution) {
				String curText = null;
				int curLen = 0;
				String lastChar = null;
				feedBackText.setText("");
				;
				int[] curLetterFreq;
				int finPos;
				boolean inValidCharFound = false;
				if (currentSolution.toString().length() > 0) {
					curText = currentSolution.toString();
					curLen = curText.length();
					lastChar = curText.substring(curLen - 1);
					lastChar = curText.substring(curLen - 1);
					curLetterFreq = Jumble.getFrequency(curText);
					finPos = curLen - 1;
					if (finPos < 0
							|| finPos >= currentSolution.toString().length())
						finPos = 0;
					if (!currentWord.contains(lastChar.toUpperCase())) {

						answerText.setTextKeepState(currentSolution.toString()
								.substring(0, finPos));
						feedBackText.setText("You typed a wrong character.");

					} else if (currentSolution.toString().length() > currentWord
							.toString().length()) {
						answerText.setTextKeepState(currentSolution.toString()
								.substring(0, finPos));

						feedBackText.setText("You exceeded the word size");
					} else {
						for (int i = 0; i < 26; i++) {
							if (curLetterFreq[i] > letterFreq[i]) {
								answerText.setTextKeepState(currentSolution
										.toString().substring(0, finPos));
								feedBackText.setText("You already typed '"
										+ lastChar + "' " + letterFreq[i]
										+ " times");
								inValidCharFound = true;
								break;
							}

						}
						if (currentSolution.toString().length() == currentWord
								.toString().length() && !inValidCharFound) {
							if (!currentSolution.toString().equals(
									currentWord.toString()))
								feedBackText.setText("Wrong answer");
								inValidCharFound = false;
						}
					}

				} else {
				}

				if (currentSolution.toString().equalsIgnoreCase(currentWord)) {
					words.remove(currentWord);
					isNewWord = true;
					setNewWord();
					feedBackText.setText("You found it. Try the next one");
				} else {
					if (isNewWord)
						isNewWord = false;
				}

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

		});
		int index = Math.abs(new Random().nextInt() % words.size());
		currentWord = words.get(index);
		jumbledText.setText(Jumble.jumble(currentWord));
		letterFreq = Jumble.getFrequency(currentWord);
		setContentView(layout);
	}

	static String jumble(String str) {
		String ret = "";
		int len = str.length();
		ArrayList<Integer> a = new ArrayList<Integer>();
		ArrayList<Integer> b = new ArrayList<Integer>();
		for (int i = 0; i < len; i++) {
			a.add(Integer.valueOf(i));
		}
		for (int i = 0; i < len; i++) {
			int t = Math.abs(new Random().nextInt() % (len - i));
			b.add(a.get(t));
			a.remove(t);
		}
		for (int i = 0; i < len; i++) {
			ret = ret.concat(String.valueOf(str.charAt(b.get(i).intValue())));
		}
		return ret;
	}

	void setNewWord() {
		while (true) {
			String prevWord = currentWord;
			int index = Math.abs(new Random().nextInt() % words.size());
			currentWord = words.get(index);
			if (!currentWord.equals(prevWord))
				break;
		}
		jumbledText.setText(Jumble.jumble(currentWord));
		letterFreq = Jumble.getFrequency(currentWord);
		answerText.setText("");
	}

	static int[] getFrequency(String str) {
		int freq[] = new int[26];
		int index;
		for (int  i = 0; i< 26; i++)
			freq[i] = 0;
		char[] lowerCurWord = str.toLowerCase().toCharArray();
		for (int i = 0; i < str.length(); i++){
			index = lowerCurWord[i] - 'a';
			if (index >=0 && index <= 25){
				freq[lowerCurWord[i] - 'a']++;
			}
		}
		return freq;
	}

	void loadWordsfromApp() {
		AssetManager am = this.getApplicationContext().getAssets();
		try {
			InputStream is;
			if (level == Level.EASY)
				is = am.open(easyFileName);
			else if (level == Level.HARD)
				is = am.open(hardFileName);
			else {
				is = null;
			}
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String line = null;

			while ((line = reader.readLine()) != null) {
				words.add(line);
			}

			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void loadWordsfromSdCard() {
		// @ToDo

	}

}