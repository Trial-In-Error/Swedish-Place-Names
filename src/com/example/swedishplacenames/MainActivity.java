package com.example.swedishplacenames;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.swedishplacenames.R;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	private static final String TAG = "MainActivity";
	//private Button buttonEnglish;
	private Button buttonLanguage;
	private Button buttonSort;
	private Button buttonHelp;
	private AutoCompleteTextView searchBar;
	private boolean isAscending = true;
	private boolean isSwedish = false; // make this configurable? save across
										// quits?
	private int colCount = 2;
	private TreeMap<String, String> data = new TreeMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		/*
		// populate the data fields with dummy data
		data.clear();
		for (int i = 0; i < 50; i++) {
			data.put("English word " + String.valueOf(i + 1), "Swedish word "
					+ String.valueOf(i + 1));
			//Log.d(TAG, "English word " + String.valueOf(i + 1)
			//		+ ", Swedish word " + String.valueOf(i + 1));
		}
		*/
		reWriteData();
		FillTable();
		buttonLanguage = (Button) findViewById(R.id.button_language);
		buttonSort = (Button) findViewById(R.id.button_sort);
		buttonHelp = (Button) findViewById(R.id.button_help);
		searchBar = (AutoCompleteTextView) findViewById(R.id.text_search_bar);
		
		buttonLanguage.setOnClickListener(this);
		buttonSort.setOnClickListener(this);
		buttonHelp.setOnClickListener(this);
		searchBar.addTextChangedListener(new TextWatcher(){
			@Override
		    public void afterTextChanged(Editable s) {
		    	search(String.valueOf(searchBar.getText()));
		    }

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
		}); 
		
		if(isSwedish){
			buttonLanguage.setText("English");
		} else {
			buttonLanguage.setText("Swedish");
		}
	}
	
	private void reWriteData(){
		// populate the data fields with dummy data
		data.clear();
		for (int i = 0; i < 50; i++) {
			data.put("English word " + String.valueOf(i + 1), "Swedish word "
					+ String.valueOf(i + 1));
		}
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
		ListView list = (ListView) findViewById(R.id.mylist);
		ArrayList<HashMap<String, String>> mylistData = new ArrayList<HashMap<String, String>>();

		String[] columnTags = new String[colCount];
		for (int i = 0; i < colCount; i++) {
			columnTags[i] = "col" + String.valueOf(i);
		}

		// build the sorted list
		int[] columnIds = new int[] { R.id.column1, R.id.column2 };
		ArrayList<String> temp = new ArrayList<String>();
		for (String key : data.keySet()) {
			temp.add(key);
			//Log.d(TAG, "Added " + key + " to temp.");
			if (!this.isAscending) {
				Collections.reverse(temp);
			}
		}

		// build the table from the list
		for (String key : temp) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(columnTags[0], key);
			map.put(columnTags[1], data.get(key));
			mylistData.add(map);
		}

		// load the table
		SimpleAdapter arrayAdapter = new SimpleAdapter(this, mylistData,
				R.layout.mylistrow, columnTags, columnIds);
		list.setAdapter(arrayAdapter);
	}

	@Override
	public void onClick(View view) {
		Log.d(TAG, "View " + view.getId() + " was touched.");
		switch (view.getId()) {
		case R.id.button_sort:
			Sort();
			break;
		case R.id.button_language:
			changeLanguages();
			break;
		case R.id.button_help:
			Help();
			break;
		}
	}

	private void changeLanguages() {
		Log.d(TAG, "Change languages button touched.");
		if(isSwedish)
		{
			buttonLanguage.setText("Swedish");
		}else {
			buttonLanguage.setText("English");
		}
		isSwedish = !isSwedish;
		TreeMap<String, String> revised = new TreeMap<String, String>();
		for (Entry<String, String> entry : data.entrySet())
			revised.put(entry.getValue(), entry.getKey());
		data = (TreeMap<String, String>) revised.clone();
		FillTable();
	}

	private void Sort() {
		this.isAscending = !this.isAscending;
		FillTable();
		Log.d(TAG, "Are we now sorting ascending? " + isAscending);
	}
	
	private void search(String query){
		reWriteData();
		for (Iterator<String> iterator = data.keySet().iterator(); iterator.hasNext();){
			String key = iterator.next();
			if(!key.toLowerCase().contains(query)){
				iterator.remove();
			}
		}
		FillTable();
	}

	private void Help() {
		Log.d(TAG, "Help button touched.");
	}

}
