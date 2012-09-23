package com.ime.jumble;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity {

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		String[] names = new String[] { "Start Jumble", "Exit" };
		this.setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_checked, names));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
		if (keyword.equals("Start Jumble")) {
			Intent i = new Intent(this, LevelSelector.class);
			startActivityForResult(i, 999);

		}
		if (keyword.equals("Add Words")) {
			Intent i = new Intent(this, AddWord.class);
			startActivity(i);

		}

		if (keyword.equals("Exit")) {
			System.exit(-1);

		}

	}

}
