package com.ime.jumble;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LevelSelector extends ListActivity {

	private static Level level;
	
	public static Level getLevel() {
		return level;
	}

	public static void setLevel(Level l) {
		level = l;
	}

	enum Level {
		EASY, HARD
	};

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		String[] names = new String[] { "Easy", "Hard"};
		this.setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_checked, names));

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
		if (keyword.equals("Easy"))
			level = Level.EASY;
		else if (keyword.equals("Hard")){
			level = Level.HARD;
		}
		else {
		}
		Intent i = new Intent(this, Jumble.class);
		startActivityForResult(i,999);

	}
}
