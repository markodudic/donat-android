package si.renderspace.donatmgmoments;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
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
		Drawable bg = (Drawable)getResources().getDrawable(R.drawable.dr_action_bar_border); 
        getActionBar().setBackgroundDrawable(bg);

        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView tvTitle = (TextView) findViewById(titleId);
        Typeface ft=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        tvTitle.setTypeface(ft);

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

		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
		Typeface tfl = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

		TableLayout lTable = (TableLayout) findViewById(R.id.drinking_data_table);
		String[][] drinking = Settings.drinking.get(indx);
		for (int i=0; i<drinking.length; i++) { 
			String[] drink = drinking[i];
			TableRow tr = new TableRow(this);
			if (i%2==0) {
				tr.setBackgroundColor(this.getResources().getColor(R.color.background_green_10));
			} else {
				tr.setBackgroundColor(this.getResources().getColor(R.color.background_white_50));				
			}
			tr.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			TextView textview1 = new TextView(this);
			textview1.setText(drink[0]);
			textview1.setTextAppearance(this, R.style.TabelFirst);
			//textview1.setLayoutParams(new TableRow.LayoutParams( LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,3f));
			TableRow.LayoutParams tableRowParams =  new TableRow.LayoutParams(100,LayoutParams.WRAP_CONTENT,1f);
			tableRowParams.setMargins(this.getResources().getDimensionPixelSize(R.dimen.layout_margin), 0, 0, 0);
			textview1.setLayoutParams(tableRowParams);
			textview1.setTypeface(tf);
			tr.addView(textview1);
			
			LinearLayout cell = new LinearLayout(this);
			cell.setBackgroundColor(this.getResources().getColor(R.color.background_white));
			cell.setLayoutParams(new TableRow.LayoutParams( 2,LayoutParams.MATCH_PARENT));//2px border on the right for the cell
			tr.addView(cell);
			
			TextView textview2 = new TextView(this);
			textview2.setText(drink[1]+drink[2]);
			textview2.setTextAppearance(this, R.style.TabelSecond);
			//textview2.setLayoutParams(new TableRow.LayoutParams( LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			//tableRowParams.setMargins(this.getResources().getDimensionPixelSize(R.dimen.layout_margin), 0, 0, 0);
			textview2.setLayoutParams(tableRowParams);
			textview2.setTypeface(tfl);
			tr.addView(textview2);
			
			LinearLayout cell1 = new LinearLayout(this);
			cell1.setBackgroundColor(this.getResources().getColor(R.color.background_white));
			cell1.setLayoutParams(new TableRow.LayoutParams( 2,LayoutParams.MATCH_PARENT));//2px border on the right for the cell
			tr.addView(cell1);
			
			TextView textview3 = new TextView(this);
			textview3.setText(drink[3]);
			textview3.setTextAppearance(this, R.style.TabelSecond);
			//textview3.setLayoutParams(new TableRow.LayoutParams( LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			//tableRowParams.setMargins(this.getResources().getDimensionPixelSize(R.dimen.layout_margin), 0, 0, 0);
			textview3.setLayoutParams(tableRowParams);
			textview3.setTypeface(tfl);
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
		
		final int indxCurr = Utils.getPrefernciesInt(this,  Settings.SETTING_INDX);
		
		//date time picker
		Calendar cal = Calendar.getInstance();
		dtNow = cal.getTime();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date dtNowDay = cal.getTime();
		//if (Settings.indicationCurrentIndx != -1 && (Settings.indicationCurrentDate.after(dtNow) || Settings.indicationCurrentDate.equals(dtNow))) {
		if (indxCurr != -1) {
			long indicationCurrentFirstNotification = dtNowDay.getTime() + Settings.notificationTimes[0].getTime() + Settings.NOTIFICATION_ALARM_MINUTES;
			System.out.println(indxCurr+":"+new Date(indicationCurrentFirstNotification)+":"+dtNow);
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
		
		final TextView startDateDate = (TextView) findViewById(R.id.startDateDate);
		startDateDate.setText(new SimpleDateFormat("dd").format(dtNow));
		final TextView startDateMonth = (TextView) findViewById(R.id.startDateMonth);
		startDateMonth.setText(new SimpleDateFormat("MMM").format(dtNow));
		final TextView startDateYear = (TextView) findViewById(R.id.startDateYear);
		startDateYear.setText(new SimpleDateFormat("yyyy").format(dtNow));
		
		FrameLayout lStartDateDate = (FrameLayout) findViewById(R.id.start_date_date_layout);
		lStartDateDate.setOnClickListener(new View.OnClickListener() {
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
									startDateDate.setText(dayOfMonth+"");
								   	startDateMonth.setText(monthString.substring(0,3));
								   	startDateYear.setText(year+"");
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
    
		TextView confirmationTitle = (TextView) dialogConfirmation.findViewById(R.id.confirmation_title);
		confirmationTitle.setTypeface(tf);
		
	    
		LinearLayout close = (LinearLayout) dialogConfirmation.findViewById(R.id.close);
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
		final Button bIndicationStart = (Button) findViewById(R.id.btn_indication_start);
		if (indxCurr == indx) {
			bIndicationStart.setText(R.string.button_indication_stop);
		}
		bIndicationStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar cc = Calendar.getInstance();
				if (indxCurr == indx) {
					Utils.savePrefernciesInt(IndicationActivity.this, Settings.SETTING_INDX, -1);
					Settings.saveHistory(IndicationActivity.this, indx, Utils.getPrefernciesLong(IndicationActivity.this, Settings.SETTING_START_DATE), cc.getTimeInMillis());
					finish();
				} else {
					if (Utils.getPrefernciesInt(IndicationActivity.this, Settings.SETTING_INDX) != -1) {
						Settings.saveHistory(IndicationActivity.this, Utils.getPrefernciesInt(IndicationActivity.this, Settings.SETTING_INDX), Utils.getPrefernciesLong(IndicationActivity.this, Settings.SETTING_START_DATE), cc.getTimeInMillis());
					}
					Utils.savePrefernciesInt(IndicationActivity.this, Settings.SETTING_INDX, indx);
					String dtStart = startDateDate.getText().toString()+"."+startDateMonth.getText().toString()+"."+startDateYear.getText().toString();  
					SimpleDateFormat  format = new SimpleDateFormat("dd.MMM.yyyy");  
					try {  
					    Date date = format.parse(dtStart);  
					    Utils.savePrefernciesLong(IndicationActivity.this, Settings.SETTING_START_DATE, date.getTime()-20000*60*1000);
					} catch (ParseException e) {  
					    e.printStackTrace();  
					}
					bIndicationStart.setText(R.string.button_indication_stop);
					Settings.setNotificationTimes(indx);
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
			Intent intent = new Intent(this, CalendarActivity.class);
			startActivity(intent);	    	
		} else if (item.getItemId() == R.id.settings) {
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);	    	
	    }
	    
	    return true;
	}

	
	
}
