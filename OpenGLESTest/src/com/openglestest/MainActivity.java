package com.openglestest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.openglestest.test01.MainActivity01;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ListView listView = new ListView(getApplicationContext());
		setContentView(listView);

		List<Map<String, String>> mylist = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("ItemTitle", "First OpenGL ES!");
		mylist.add(map);

		ListAdapter listAdapter = new SimpleAdapter(this, mylist,
				R.layout.my_listitem, new String[] { "ItemTitle" },
				new int[] { R.id.ItemTitle });
		listView.setAdapter(listAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				if (position == 0) {
					intent.setClass(MainActivity.this, MainActivity01.class);
				}
				startActivity(intent);
			}
		});
	}
}
