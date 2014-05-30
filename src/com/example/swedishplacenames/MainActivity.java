package com.example.swedishplacenames;

import java.util.ArrayList;
import java.util.HashMap;

import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
//import android.os.Build;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.swedishplacenames.R;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	private static final String TAG = "MainActivity";
	private Button buttonEnglish;
	private Button buttonSwedish;
	private Button buttonSort;
	private Button buttonHelp;
	private boolean isAscending = true;
	private boolean isSwedish = false; //make this configurable? save across quits?

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		FillTable();
		buttonEnglish = (Button) findViewById(R.id.button_english);
		buttonSwedish = (Button) findViewById(R.id.button_swedish);
		buttonSort = (Button) findViewById(R.id.button_sort);
		buttonHelp = (Button) findViewById(R.id.button_help);
		
		buttonEnglish.setOnClickListener(this);
		buttonSwedish.setOnClickListener(this);
		buttonSort.setOnClickListener(this);
		buttonHelp.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	private void FillTable() {
		//setContentView(R.layout.activity_main);

		ListView list = (ListView) findViewById(R.id.mylist);
		ArrayList<HashMap<String, String>> mylistData = new ArrayList<HashMap<String, String>>();

		String[] columnTags = new String[] { "col1", "col2", "col3" };

		int[] columnIds = new int[] { R.id.column1, R.id.column2, R.id.column3 };
		for (int i = 0; i < 50; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			// initialize row data
			for (int j = 0; j < 3; j++) {
				if(this.isAscending){
					map.put(columnTags[j], "row " + i + ", col " + j);
				}else{
					map.put(columnTags[j], "row "+ (50 - i) + ", col " + j);
				}
			}
			mylistData.add(map);
		}
		SimpleAdapter arrayAdapter = new SimpleAdapter(this, mylistData,
				R.layout.mylistrow, columnTags, columnIds);
		list.setAdapter(arrayAdapter);
	}

	@Override
	public void onClick(View view) {
		Log.d(TAG, "View "+view.getId()+" was touched. Sort is "+R.id.button_sort+".");
		switch (view.getId()) {
		case R.id.button_sort:
			Sort();
			break;
		case R.id.button_english:
			ToEnglish();
			break;
		case R.id.button_swedish:
			ToSwedish();
			break;
		case R.id.button_help:
			Help();
			break;
		}
	}

	private void Sort() {
		this.isAscending = !this.isAscending;
		FillTable();
		Log.d(TAG, "Are we now sorting ascending? "+isAscending);
	}

	private void ToEnglish() {
	}

	private void ToSwedish() {
	}

	private void Help() {
	}

}
