package si.renderspace.donatmgmoments;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;


public class IndicationActivity extends Activity {

	private boolean isDrinkingData = false;
	private boolean isIntervalData = false;
	private Date dtNow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indication);
		
		getActionBar().setHomeButtonEnabled(true);

		Intent intent = getIntent();
		final int indx = intent.getIntExtra("INDX", 1);

		ImageView indicationImage = (ImageView) findViewById(R.id.indicationImage);
		int id = getResources().getIdentifier("ic_indication_"+indx, "drawable", getPackageName());
		indicationImage.setImageResource(id);
		
		TextView indicationTitle = (TextView) findViewById(R.id.indicationTitle);
		indicationTitle.setText(Settings.indications.get(indx));

		TextView indicationDesc = (TextView) findViewById(R.id.indicationDesc);
		indicationDesc.setText(Settings.indications_desc.get(indx));

		TextView intervalDesc = (TextView) findViewById(R.id.intervalDesc);
		intervalDesc.setText(Settings.interval.get(indx));

		TableLayout lTable = (TableLayout) findViewById(R.id.drinking_data_table);
		String[][] drinking = Settings.drinking.get(indx);
		for (int i=0; i<drinking.length; i++) {
			String[] drink = drinking[i];
			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			TextView textview1 = new TextView(this);
			textview1.setText(drink[0]);
			tr.addView(textview1);
			TextView textview2 = new TextView(this);
			textview2.setText(drink[1]);
			tr.addView(textview2);
			TextView textview3 = new TextView(this);
			textview3.setText(drink[2]);
			tr.addView(textview3);			
			lTable.addView(tr);
		}
		

		
		final LinearLayout lDrinkingData = (LinearLayout) findViewById(R.id.drinking_data_layout);
		LinearLayout lDrinkingTitle = (LinearLayout) findViewById(R.id.drinking_title_layout);
		lDrinkingTitle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isDrinkingData) {
					lDrinkingData.setVisibility(LinearLayout.GONE);
					isDrinkingData = false;
				} else {
					lDrinkingData.setVisibility(LinearLayout.VISIBLE);
					isDrinkingData = true;
				}
			}
		});	
		
		final LinearLayout lIntervalData = (LinearLayout) findViewById(R.id.interval_data_layout);
		LinearLayout lIntervalTitle = (LinearLayout) findViewById(R.id.interval_title_layout);
		lIntervalTitle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isIntervalData) {
					lIntervalData.setVisibility(LinearLayout.GONE);
					isIntervalData = false;
				} else {
					lIntervalData.setVisibility(LinearLayout.VISIBLE);
					isIntervalData = true;
				}
			}
		});		
		
		//date time picker
		Calendar cal = Calendar.getInstance();
		dtNow = cal.getTime();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date dtNowDay = cal.getTime();
		//if (Settings.indicationCurrentIndx != -1 && (Settings.indicationCurrentDate.after(dtNow) || Settings.indicationCurrentDate.equals(dtNow))) {
		if (Settings.indicationCurrentIndx != -1) {
			long indicationCurrentFirstNotification = dtNowDay.getTime() + Settings.notificationTimes[0].getTime() + Settings.NOTIFICATION_ALARM_MINUTES;
			System.out.println(Settings.indicationCurrentIndx+":"+new Date(indicationCurrentFirstNotification)+":"+dtNow);
			if (new Date(indicationCurrentFirstNotification).before(dtNow)) {
				//ce obstaja aktivna indikacija, in je na danasnji dan ze bil notification, nastavim naslednjo indikacijo na naslednji dan
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				calendar.add(Calendar.DATE, 1);
				dtNow = calendar.getTime();
				System.out.println(dtNow);
			}
		}
		
		String timeStamp = new SimpleDateFormat("dd.MMM.yyyy").format(dtNow);
		final TextView startDateDate = (TextView) findViewById(R.id.startDateDate);
		startDateDate.setText(timeStamp);
		startDateDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Calendar c = Calendar.getInstance();
				int mYear = c.get(Calendar.YEAR);
				int mMonth = c.get(Calendar.MONTH);
				int mDay = c.get(Calendar.DAY_OF_MONTH);
				 
				DatePickerDialog dpd = new DatePickerDialog(IndicationActivity.this, new DatePickerDialog.OnDateSetListener() {
				 
				            @Override
				            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				            	Calendar calendar = Calendar.getInstance();
								calendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
								if (dtNow.getTime() <= calendar.getTimeInMillis()) {
								   	String monthString = new DateFormatSymbols().getMonths()[monthOfYear];
								   	startDateDate.setText(dayOfMonth + "." + monthString.substring(0,3) + "." + year);
								}
				            }
				        }, mYear, mMonth, mDay);
				dpd.show();
			}
		}); 
		
		
		// dialogConfirmation
		final Dialog dialogConfirmation = new Dialog(this,R.style.Dialog);
		dialogConfirmation.setContentView(R.layout.dialog_confirmation); 
		dialogConfirmation.getWindow().setBackgroundDrawable(new ColorDrawable(0));
    
	    
		ImageView close = (ImageView) dialogConfirmation.findViewById(R.id.close);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogConfirmation.dismiss();
			}
		});
		 
		LinearLayout back = (LinearLayout) dialogConfirmation.findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogConfirmation.dismiss();
				finish();
			}
		});		

		//vklopi/izklopi
		Button bIndicationStart = (Button) findViewById(R.id.btn_indication_start);
		if (Settings.indicationCurrentIndx == indx) {
			bIndicationStart.setText(R.string.button_indication_stop);
		}
		bIndicationStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Settings.indicationCurrentIndx == indx) {
					Settings.indicationCurrentIndx = -1;
					finish();
				} else {
					Settings.indicationCurrentIndx = indx;
					String dtStart = startDateDate.getText().toString();  
					SimpleDateFormat  format = new SimpleDateFormat("dd.MMM.yyyy");  
					try {  
					    Date date = format.parse(dtStart);  
						Settings.indicationCurrentDate = date;
					} catch (ParseException e) {  
					    e.printStackTrace();  
					}
					setNotificationTimes(indx);
					dialogConfirmation.show();
				}

			}
		});	
		

	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.home_screen, menu);
		//return true;
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.action_bar, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
	    	finish();
	    } else if (item.getItemId() == R.id.calendar) {
		} else if (item.getItemId() == R.id.settings) {
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);	    	
	    }
	    
	    return true;
	}

	
	public void setNotificationTimes(int indx) {
		switch (indx) {
	        case 1: case 9: 
	    		Settings.notificationTimes = new Date[2];
	        	Settings.notificationTimes[0] = new Date(Settings.intervalHours.get("TESCE").getTime() - Settings.NOTIFICATION_ALARM_MINUTES);
		        Settings.notificationTimes[1] = new Date(Settings.intervalHours.get("SPANJE").getTime() - Settings.NOTIFICATION_ALARM_MINUTES);
	    		break;
	        case 2: case 10: 
	    		Settings.notificationTimes = new Date[3];
	        	Settings.notificationTimes[0] = new Date(Settings.intervalHours.get("ZAJTRK").getTime() - 20*60*1000 - Settings.NOTIFICATION_ALARM_MINUTES);
		        Settings.notificationTimes[1] = new Date(Settings.intervalHours.get("KOSILO").getTime() - 20*60*1000 - Settings.NOTIFICATION_ALARM_MINUTES);
		        Settings.notificationTimes[2] = new Date(Settings.intervalHours.get("VECERJA").getTime() - 20*60*1000 - Settings.NOTIFICATION_ALARM_MINUTES);
	    		break;
	        case 3: 
	    		Settings.notificationTimes = new Date[3];
	        	Settings.notificationTimes[0] = new Date(Settings.intervalHours.get("TESCE").getTime() - Settings.NOTIFICATION_ALARM_MINUTES);
        		Settings.notificationTimes[1] = new Date(12*60*60*1000);
        		Settings.notificationTimes[2] = new Date(Settings.intervalHours.get("VECERJA").getTime() - Settings.NOTIFICATION_ALARM_MINUTES);
	    		break;
	        case 4: case 5: 
	    		Settings.notificationTimes = new Date[3];
	        	Settings.notificationTimes[0] = new Date(Settings.intervalHours.get("TESCE").getTime() - Settings.NOTIFICATION_ALARM_MINUTES);
        		Settings.notificationTimes[1] = new Date(Settings.intervalHours.get("KOSILO").getTime() - Settings.NOTIFICATION_ALARM_MINUTES);
        		Settings.notificationTimes[2] = new Date(Settings.intervalHours.get("VECERJA").getTime() - Settings.NOTIFICATION_ALARM_MINUTES);
	    		break;
	        case 6: 
	    		Settings.notificationTimes = new Date[4];
	        	Settings.notificationTimes[0] = new Date(Settings.intervalHours.get("TESCE").getTime() - Settings.NOTIFICATION_ALARM_MINUTES);
        		Settings.notificationTimes[1] = new Date(Settings.intervalHours.get("KOSILO").getTime() - Settings.NOTIFICATION_ALARM_MINUTES);
        		Settings.notificationTimes[2] = new Date(Settings.intervalHours.get("VECERJA").getTime() - Settings.NOTIFICATION_ALARM_MINUTES);
		        Settings.notificationTimes[3] = new Date(Settings.intervalHours.get("SPANJE").getTime() - Settings.NOTIFICATION_ALARM_MINUTES);
	    		break;
	        case 7: 
	    		Settings.notificationTimes = new Date[4];
	        	Settings.notificationTimes[0] = new Date(Settings.intervalHours.get("TESCE").getTime() - Settings.NOTIFICATION_ALARM_MINUTES);
        		Settings.notificationTimes[1] = new Date(Settings.intervalHours.get("KOSILO").getTime() - Settings.NOTIFICATION_ALARM_MINUTES);
        		Settings.notificationTimes[2] = new Date(Settings.intervalHours.get("VECERJA").getTime() - Settings.NOTIFICATION_ALARM_MINUTES);
		        break;
	        case 8: 
	        	long danDel = (Settings.intervalHours.get("SPANJE").getTime() - Settings.intervalHours.get("TESCE").getTime()) / 3;
	    		Settings.notificationTimes = new Date[4];
	        	Settings.notificationTimes[0] = new Date(Settings.intervalHours.get("TESCE").getTime() - Settings.NOTIFICATION_ALARM_MINUTES);
	        	Settings.notificationTimes[1] = new Date(Settings.intervalHours.get("TESCE").getTime() + danDel - Settings.NOTIFICATION_ALARM_MINUTES);
	        	Settings.notificationTimes[2] = new Date(Settings.intervalHours.get("TESCE").getTime() + (2 * danDel) -  Settings.NOTIFICATION_ALARM_MINUTES);
	        	Settings.notificationTimes[3] = new Date(Settings.intervalHours.get("SPANJE").getTime() - Settings.NOTIFICATION_ALARM_MINUTES);
	        	;
	    }
		
	}
	
	
}
