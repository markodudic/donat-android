package si.renderspace.donatmgmoments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.roomorama.caldroid.WeekdayArrayAdapter;

@SuppressLint("SimpleDateFormat")
public class CalendarActivity extends FragmentActivity {

	Menu mainMenu;
	private boolean undo = false;
	private CaldroidFragment caldroidFragment;
	private CaldroidFragment dialogCaldroidFragment;

	private void setCustomResourceForDates() {
		Calendar cal = Calendar.getInstance();

		Bundle args = new Bundle();
		args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY); // Tuesday
		args.putBoolean(CaldroidFragment.ENABLE_CLICK_ON_DISABLED_DATES, true);
		caldroidFragment.setArguments(args);		

		//Trenutna indikacija
		int indx = Utils.getPrefernciesInt(this,  Settings.SETTING_INDX);
		long startDate = Utils.getPrefernciesLong(this,  Settings.SETTING_START_DATE);
		Calendar startDateCal = Calendar.getInstance();
		Calendar endDateCal = Calendar.getInstance();
		startDateCal.setTimeInMillis(startDate);
		endDateCal.setTimeInMillis(startDate);
		endDateCal.add(Calendar.YEAR, 1);
		
		ArrayList<Date> drinkingDates = new ArrayList<Date>();
		drinkingDates = addDrinkingDates(indx, startDateCal, endDateCal);
		
		//history
		for (int i=0; i<Settings.history.length(); i++) {
			try {
				JSONObject historyEl = (JSONObject) Settings.history.get(i);
				indx = historyEl.getInt(Settings.SETTING_INDX);
				startDate = historyEl.getLong(Settings.SETTING_START_DATE);
				long endDate = historyEl.getLong(Settings.SETTING_END_DATE);
				startDateCal = Calendar.getInstance();
				endDateCal = Calendar.getInstance();
				startDateCal.setTimeInMillis(startDate);
				endDateCal.setTimeInMillis(endDate);
				
				drinkingDates.addAll(addDrinkingDates(indx, startDateCal, endDateCal));
				
			} catch (Exception e) {
				System.out.println("CALENDAR ERROR="+e.getLocalizedMessage());
			}
			
		}
			
		
		//nastavitve
		caldroidFragment.setDisableDates(drinkingDates);

		WeekdayArrayAdapter.textColor = getResources().getColor(R.color.text_green);
		WeekdayArrayAdapter.textSize = 28;
		Typeface ft=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
		WeekdayArrayAdapter.typeFace = ft;
		
		CaldroidFragment.disabledTextColor = getResources().getColor(R.color.text_green_50);
		
		caldroidFragment.refreshView();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);

		getActionBar().setHomeButtonEnabled(true);
		Drawable bg = (Drawable)getResources().getDrawable(R.drawable.dr_action_bar_border); 
        getActionBar().setBackgroundDrawable(bg);
        getActionBar().setTitle(R.string.home);

        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView tvTitle = (TextView) findViewById(titleId);
        Typeface ft=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        tvTitle.setTypeface(ft);
        
		final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

		// Setup caldroid fragment
		// **** If you want normal CaldroidFragment, use below line ****
		//caldroidFragment = new CaldroidFragment();

		// //////////////////////////////////////////////////////////////////////
		// **** This is to show customized fragment. If you want customized
		// version, uncomment below line ****
		 caldroidFragment = new CalendarCustomFragment();

		// Setup arguments

		// If Activity is created after rotation
		if (savedInstanceState != null) {
			caldroidFragment.restoreStatesFromKey(savedInstanceState,
					"CALDROID_SAVED_STATE");
		}
		// If activity is created from fresh
		else {
			Bundle args = new Bundle();
			Calendar cal = Calendar.getInstance();
			args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
			args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
			args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
			args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

			caldroidFragment.setArguments(args);
		}

		setCustomResourceForDates();

		// Attach to the activity
		android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.calendar1, caldroidFragment);
		t.commit();

		// Setup listener
		final CaldroidListener listener = new CaldroidListener() {
			@Override
			public void onSelectDate(Date date, View view) {
				int indx = -1;
				long start = 0;
				if ((Utils.getPrefernciesInt(CalendarActivity.this, Settings.SETTING_INDX) != -1) && (date.getTime() > Utils.getPrefernciesLong(CalendarActivity.this,  Settings.SETTING_START_DATE))) {
					indx = Utils.getPrefernciesInt(CalendarActivity.this, Settings.SETTING_INDX);
					start = Utils.getPrefernciesLong(CalendarActivity.this, Settings.SETTING_START_DATE);
				} else {
					long[] data = getIndxFromDate(date);
		        	indx = (int)data[0]; 
					start = data[1];
		        }
				if (indx != -1) {
					Intent intent = new Intent(CalendarActivity.this, IndicationActivity.class);
		        	intent.putExtra("INDX", indx);
		        	intent.putExtra("START_DATE", start);
					startActivity(intent);
				}
			}
		};

		// Setup Caldroid
		caldroidFragment.setCaldroidListener(listener);

	}

	/**
	 * Save current states of the Caldroid here
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

		if (caldroidFragment != null) {
			caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
		}

		if (dialogCaldroidFragment != null) {
			dialogCaldroidFragment.saveStatesToKey(outState,
					"DIALOG_CALDROID_SAVED_STATE");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		mainMenu = menu;
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.action_bar, menu);
	    Utils.setMenu(mainMenu, R.id.calendar);		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			Utils.resetMenu(mainMenu);		
	    	finish();
	    } else if (item.getItemId() == R.id.calendar) {
	    } else if (item.getItemId() == R.id.settings) { 
			Utils.resetMenu(mainMenu);		
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);	    	
		}
	    
	    return true;
	}
	
	private static ArrayList<Date> addDrinkingDates(int i, Calendar start, Calendar end) { 
		ArrayList<Date> dates = new ArrayList<Date>();
		switch (i) {
		case 2: case 3: case 6: case 10:
			while (start.before(end)) {  
				dates.add(start.getTime());
				start.add(Calendar.DATE, 1);
			}  
			break;
		case 1: case 4:
			dates = calculateDrinkingDays(start, end, 5, 2, -1);
			break;
		case 5:
			dates = calculateDrinkingDays(start, end, 42, 21, 3);
			break;
		case 7:
			dates = calculateDrinkingDays(start, end, 90, 30, 3);
			break;
		case 8: case 9:
			dates = calculateDrinkingDays(start, end, 60, 30, 3);
			;
		}
		return dates;
	}
	
	private static ArrayList<Date> calculateDrinkingDays(Calendar start, Calendar end, int drinkDays, int pauseDays, int cycles)
	{
		ArrayList<Date> dates = new ArrayList<Date>();
		int drink = 0;
		int pause = 1;
		int cycle = 1;
		while (start.before(end)) {  
			if (drink < drinkDays) {
				dates.add(start.getTime());
	    		drink++;
	    	} else {
	    		if (pause < pauseDays) {
	    			pause++;
	    		} else {
	    			if (cycle == cycles) break;
	    			cycle++;
	    			pause = 1;		    			
	    			drink = 0;		    			
	    		}
	    	}
			start.add(Calendar.DATE, 1);
		}
    	return dates;  
	} 
	
	private long[] getIndxFromDate(Date date) {
		for (int i=0; i<Settings.history.length(); i++) {
			try {
				JSONObject historyEl = (JSONObject) Settings.history.get(i);
				int indx = historyEl.getInt(Settings.SETTING_INDX);
				long startDate = historyEl.getLong(Settings.SETTING_START_DATE);
				long endDate = historyEl.getLong(Settings.SETTING_END_DATE);
				if ((startDate < date.getTime()) && (endDate > date.getTime())) {
					return new long[] {(long)indx, startDate};
				}
			} catch (Exception e) {
				System.out.println("CALENDAR ERROR="+e.getLocalizedMessage());
			}
			
		}
		return new long[] {-1,-1};		
	}

}
