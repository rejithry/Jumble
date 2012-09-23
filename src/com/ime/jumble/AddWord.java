package com.ime.jumble;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AddWord extends Activity {
	Button addButton;
	Button backToGameButton;
	EditText wordText;
	String currentWord;
	ArrayList<String> words;
	boolean isNewWord;
	HashMap<String, Integer> hm, hm1;
	Activity activity = this;
	String dirName;
	String extFileName;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		wordText = new EditText(this);
		wordText.setMaxHeight(30);
		addButton = new Button(this);
		addButton.setText("Add");
		backToGameButton = new Button(this);
		backToGameButton.setText("Back to Game");
		dirName = "jumble";
		extFileName = "words.txt";
		int height = LinearLayout.LayoutParams.FILL_PARENT;
		int width = LinearLayout.LayoutParams.WRAP_CONTENT;
		layout.addView(wordText, new LinearLayout.LayoutParams(height, width));
		layout.addView(addButton, new LinearLayout.LayoutParams(height, width));
		layout.addView(backToGameButton, new LinearLayout.LayoutParams(height, width));
		setContentView(layout);
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				addWord(wordText.getText().toString());
				wordText.setText("");

			}
		});
		backToGameButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(activity, Jumble.class);
				startActivityForResult(i, 999);

			}
		});
	}

	public static boolean createDirIfNotExists(String path) {
		boolean ret = true;

		File file = new File(Environment.getExternalStorageDirectory(), path);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				ret = false;
			}
		}
		return ret;
	}

	public  void addWord(String str) {

		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/" + dirName + "/";
		createDirIfNotExists("/" + dirName);
		File file = new File(path + extFileName);
		try {
			if (file.exists()) {
				FileInputStream fr = new FileInputStream(file);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(fr));
				String temp;
				boolean isNew = true;
				while ((temp = br.readLine()) != null) {
					if (temp.equals(str)) {
						isNew = false;
						break;
					}
				}
				if (isNew) {
					FileOutputStream fOut = new FileOutputStream(file);
					OutputStreamWriter myOutWriter = new OutputStreamWriter(
							fOut);
					myOutWriter.write(str + "\n");
					myOutWriter.close();
					fOut.close();
				}

				br.close();
				fr.close();
			}

			else {
				file.createNewFile();
				FileOutputStream fOut = new FileOutputStream(file);
				OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
				myOutWriter.append(str);
				myOutWriter.close();
				fOut.close();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void readWords() {
		File file = new File(Environment.getExternalStorageDirectory(),
				extFileName);
		try {

			if (file.exists()) {
				String word;
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				while ((word = br.readLine()) != null) {
					//@To Do
				}
				br.close();
				fr.close();
			} else {
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}