package si.renderspace.donatmgmoments;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class SettingsActivity extends Activity {
	
	final static String APP_PNAME = "Donat MG Moments";
	
	private Spinner spLanguages, spObrokov;
	private TextView tvZbujanje, tvZajtrk, tvKosilo, tvVecerja, tvSpanje, tvCurr;
	static final int TIME_DIALOG_ID = 999;
	Menu mainMenu;
	private int hour;
	private int minute;
	private TimePickerDialog tpd;
 
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
 
		getActionBar().setHomeButtonEnabled(true);
		Drawable bg = (Drawable)getResources().getDrawable(R.drawable.dr_action_bar_border); 
        getActionBar().setBackgroundDrawable(bg);
        getActionBar().setTitle(R.string.home);

        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView tvTitle = (TextView) findViewById(titleId);
        Typeface ft=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        tvTitle.setTypeface(ft);
		
		spLanguages = (Spinner) findViewById(R.id.sp_languages);
		
		/*MyArrayAdapter mySpinnerArrayAdapter = new MyArrayAdapter(this, R.layout.spinner); 
		mySpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spLanguages.setAdapter(mySpinnerArrayAdapter);
		*/
		tvZbujanje = (TextView) findViewById(R.id.tv_zbujanje);
        tvZbujanje.setOnClickListener(new OnClickListener() {
	 		  @Override
			  public void onClick(View v) {
	 			 showTimeDialog(tvZbujanje);
	 		  }
        });
		tvZajtrk = (TextView) findViewById(R.id.tv_zajtrk);
		tvZajtrk.setOnClickListener(new OnClickListener() {
	 		  @Override
			  public void onClick(View v) {
	 			 showTimeDialog(tvZajtrk);
	 		  }
        });
		tvKosilo = (TextView) findViewById(R.id.tv_kosilo);
		tvKosilo.setOnClickListener(new OnClickListener() {
	 		  @Override
			  public void onClick(View v) {
	 			 showTimeDialog(tvKosilo);
	 		  }
        });
		tvVecerja = (TextView) findViewById(R.id.tv_vecerja);
		tvVecerja.setOnClickListener(new OnClickListener() {
	 		  @Override
			  public void onClick(View v) {
	 			 showTimeDialog(tvVecerja);
	 		  }
        });
		tvSpanje = (TextView) findViewById(R.id.tv_spanje);
		tvSpanje.setOnClickListener(new OnClickListener() {
	 		  @Override
			  public void onClick(View v) {
	 			 showTimeDialog(tvSpanje);
	 		  }
        });
		spObrokov = (Spinner) findViewById(R.id.sp_obrokov); 
        
		Button btnSave = (Button) findViewById(R.id.btn_save);
		btnSave.setOnClickListener(new OnClickListener() {
	 		  @Override
			  public void onClick(View v) {
	 			  Utils.savePrefernciesString(SettingsActivity.this, Settings.SETTING_TESCE, (String)tvZbujanje.getText());
	 			  Utils.savePrefernciesString(SettingsActivity.this, Settings.SETTING_ZAJTRK, (String)tvZajtrk.getText());
	 			  Utils.savePrefernciesString(SettingsActivity.this, Settings.SETTING_KOSILO, (String)tvKosilo.getText());
	 			  Utils.savePrefernciesString(SettingsActivity.this, Settings.SETTING_VECERJA, (String)tvVecerja.getText());
	 			  Utils.savePrefernciesString(SettingsActivity.this, Settings.SETTING_SPANJE, (String)tvSpanje.getText());
	 			  Utils.savePrefernciesInt(SettingsActivity.this, Settings.SETTING_OBROKOV, Integer.parseInt((String)spObrokov.getSelectedItem()));
		 	    
	 			  Settings.updateData(SettingsActivity.this);
	 			  AlarmReceiver.setNextNotification(SettingsActivity.this);				

	 			 			        
	 			  int langId = (int)spLanguages.getSelectedItemId();
	 			  if (langId != Utils.getPrefernciesInt(SettingsActivity.this, Settings.SETTING_LANG)) {
		 			Utils.savePrefernciesInt(SettingsActivity.this, Settings.SETTING_LANG, langId);
		 			Settings.setLanguage(SettingsActivity.this, Settings.languages.get(langId));
	 			  	restartApplication();
	 			  }
	 			 
	 			  Utils.resetMenu(mainMenu);
	 			  if (Utils.getPrefernciesBoolean(SettingsActivity.this,  Settings.SETTING_FIRST_START, false)) {
	 				 finish();
	 			  } else {
	 				Intent intent = new Intent(SettingsActivity.this, HomeScreenActivity.class);
	 				startActivity(intent);
					Utils.savePrefernciesBoolean(SettingsActivity.this,  Settings.SETTING_FIRST_START, true); 
	 			  }
			  }
	 	});	
		
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

		TextView tvPravila = (TextView) findViewById(R.id.pravila);
		tvPravila.setTypeface(tf);
		tvPravila.setOnClickListener(new OnClickListener() {
	 		  @Override
			  public void onClick(View v) {
	 			    Intent i = new Intent(SettingsActivity.this, RulesActivity.class);
	 			    SettingsActivity.this.startActivity(i);
			  }
	 	});		

		TextView tvPravice = (TextView) findViewById(R.id.pravice);
		tvPravice.setOnClickListener(new OnClickListener() {
	 		  @Override
			  public void onClick(View v) {
	 			    Intent i = new Intent(SettingsActivity.this, RulesActivity.class);
	 			    SettingsActivity.this.startActivity(i);
			  }
	 	});		

		TextView tvRate = (TextView) findViewById(R.id.rate);
		tvRate.setTypeface(tf);
		tvRate.setOnClickListener(new OnClickListener() {
	 		  @Override
			  public void onClick(View v) {
	 			  	SettingsActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Settings.SETTING_APP_PNAME)));
			  }
	 	});		
		
	}

	
	@Override
	public void onResume() {
	    super.onResume();
	    tvZbujanje.setText(Utils.getPrefernciesString(SettingsActivity.this, Settings.SETTING_TESCE));
	    tvZajtrk.setText(Utils.getPrefernciesString(SettingsActivity.this, Settings.SETTING_ZAJTRK));
	    tvKosilo.setText(Utils.getPrefernciesString(SettingsActivity.this, Settings.SETTING_KOSILO));
	    tvVecerja.setText(Utils.getPrefernciesString(SettingsActivity.this, Settings.SETTING_VECERJA));
	    tvSpanje.setText(Utils.getPrefernciesString(SettingsActivity.this, Settings.SETTING_SPANJE));

		spLanguages.setSelection(Utils.getPrefernciesInt(SettingsActivity.this, Settings.SETTING_LANG));
	    spObrokov.setSelection(getIndex(spObrokov,  Utils.getPrefernciesInt(SettingsActivity.this, Settings.SETTING_OBROKOV)+""));

	}	
	
	private int getIndex(Spinner spinner, String myString)
	{
		for (int i=0;i<spinner.getCount();i++){
			if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
				return i;
	    	}
	    }
	    return -1;
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
		if (item.getItemId() == android.R.id.home) {
			Utils.resetMenu(mainMenu);		
			if (Utils.getPrefernciesBoolean(SettingsActivity.this, Settings.SETTING_FIRST_START, false)) {
				 finish();
 			} else {
 				Intent intent = new Intent(SettingsActivity.this, HomeScreenActivity.class);
 				startActivity(intent);
 			}
	    } else if (item.getItemId() == R.id.calendar) {
			Utils.resetMenu(mainMenu);		
			Intent intent = new Intent(this, CalendarActivity.class);
			startActivity(intent);	    	
	    } else if (item.getItemId() == R.id.settings) { 
		}
	    
	    return true;
	}
	
	void restartApplication()
	{
	    Intent i = new Intent(SettingsActivity.this, SplashScreenActivity.class);
	    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    SettingsActivity.this.startActivity(i);
	}	
	

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			tpd = new TimePickerDialog(this, timePickerListener, hour, minute, true);
			return tpd;
		}
		return null;
	}
 
	private void showTimeDialog(TextView tv) {
		tvCurr = tv;
		String[] time = tv.getText().toString().split(":");
		hour = Integer.parseInt(time[0]);
		minute = Integer.parseInt(time[1]);
		showDialog(TIME_DIALOG_ID);
		tpd.updateTime(hour,  minute);
	}
	 
	private TimePickerDialog.OnTimeSetListener timePickerListener = 
            new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
			tvCurr.setText(new StringBuilder().append(pad(selectedHour))
					.append(":").append(pad(selectedMinute)));
		}
	};
	
	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}	
}
