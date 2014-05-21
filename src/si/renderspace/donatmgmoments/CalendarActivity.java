package si.renderspace.donatmgmoments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
/*
		// Min date is last 7 days
		cal.add(Calendar.DATE, -18);
		Date blueDate = cal.getTime();

		// Max date is next 7 days
		cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 16);
		Date greenDate = cal.getTime();

		if (caldroidFragment != null) {
			caldroidFragment.setBackgroundResourceForDate(R.color.blue, blueDate);
			caldroidFragment.setBackgroundResourceForDate(R.color.green, greenDate);
			caldroidFragment.setTextColorForDate(R.color.white, blueDate);
			caldroidFragment.setTextColorForDate(R.color.white, greenDate);
		}
		*/
		Bundle args = new Bundle();
		args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY); // Tuesday
		args.putBoolean(CaldroidFragment.ENABLE_CLICK_ON_DISABLED_DATES, true);
		caldroidFragment.setArguments(args);		
		/*
		// Min date is last 7 days
		cal.add(Calendar.DATE, -7);
		Date minDate = cal.getTime(); 

		// Max date is next 7 days
		cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 14);
		Date maxDate = cal.getTime();
*/
		int indx = Utils.getPrefernciesInt(this, "INDX");
		long startDate = Utils.getPrefernciesLong(this, "DATE");
		Calendar startDateCal = Calendar.getInstance();
		Calendar endDateCal = Calendar.getInstance();
		startDateCal.setTimeInMillis(startDate);
		endDateCal.setTimeInMillis(startDate);
		endDateCal.add(Calendar.YEAR, 1);
		//long daysAfter = daysBetween(startDateCal, endDateCal);
		//long daysBefore = daysBetween(startDateCal, cal);
		ArrayList<Date> drinkingDates = new ArrayList<Date>();
		
		switch (indx) {
		case 2: case 3: case 6: case 10:
			/*daysAfter = daysBetween(startDateCal, endDateCal);
			daysBefore = daysBetween(startDateCal, cal);
			for (int i = -(int)daysBefore; i < (int)daysAfter; i++) {
				cal = Calendar.getInstance();
				cal.add(Calendar.DATE, i);
				drinkingDates.add(cal.getTime());
			}*/
			while (startDateCal.before(endDateCal)) {  
				drinkingDates.add(startDateCal.getTime());
		    	startDateCal.add(Calendar.DATE, 1);
			}  

			break;
		case 1: case 4:
			drinkingDates = calculateDrinkingDays(startDateCal, endDateCal, 5, 2, -1);
			break;
		case 5:
			drinkingDates = calculateDrinkingDays(startDateCal, endDateCal, 42, 21, 3);
			break;
		case 7:
			drinkingDates = calculateDrinkingDays(startDateCal, endDateCal, 90, 30, 3);
			break;
		case 8: case 9:
			drinkingDates = calculateDrinkingDays(startDateCal, endDateCal, 60, 30, 3);
			;
		}

		/*
		// Set selected dates
		// From Date
		cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		Date fromDate = cal.getTime();

		// To Date
		cal = Calendar.getInstance();
		cal.add(Calendar.DATE, (int)daysAfter);
		Date toDate = cal.getTime();
*/
		// Set disabled dates
 
		// Customize
		//caldroidFragment.setMinDate(minDate);
		//caldroidFragment.setMaxDate(maxDate);
		caldroidFragment.setDisableDates(drinkingDates);
		//caldroidFragment.setSelectedDates(fromDate, toDate);
		//caldroidFragment.setShowNavigationArrows(false);
		//caldroidFragment.setEnableSwipe(false);
		
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
				Intent intent = new Intent(CalendarActivity.this, IndicationActivity.class);
		        intent.putExtra("INDX", Utils.getPrefernciesInt(CalendarActivity.this, "INDX"));
				startActivity(intent);
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
	    Utils.setMenu(mainMenu, R.id.settings);		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Utils.resetMenu(mainMenu);		
		
		if (item.getItemId() == android.R.id.home) {
	    	finish();
	    } else if (item.getItemId() == R.id.calendar) {
	    } else if (item.getItemId() == R.id.settings) { 
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);	    	
		}
	    
	    return true;
	}
	
	public static ArrayList<Date> calculateDrinkingDays(Calendar start, Calendar end, int drinkDays, int pauseDays, int cycles)
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

}
