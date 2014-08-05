package si.renderspace.donatmgmoments;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotiActivity extends Activity {

	Menu mainMenu;
	int period = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);

		getActionBar().setHomeButtonEnabled(true);
		Drawable bg = (Drawable)getResources().getDrawable(R.drawable.dr_action_bar_border); 
        getActionBar().setBackgroundDrawable(bg);
        getActionBar().setTitle(R.string.home);
        
        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView tvTitle = (TextView) findViewById(titleId);
        Typeface ft=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        tvTitle.setTypeface(ft);
        
        Intent intent = getIntent();
		period = intent.getIntExtra("PERIOD", 0);
	    
		Button btnClose = (Button) findViewById(R.id.btn_close);
		btnClose.setOnClickListener(new OnClickListener() {
	 		  @Override
			  public void onClick(View v) {
	 			  ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
	 			  if (checkRateIt())
	 				  finish();
			  }
	 	});        
    }

	@Override
	public void onNewIntent(Intent intent) {
	    super.onNewIntent(intent);
	    period = intent.getIntExtra("PERIOD", 0);
	}
	
	@Override
	public void onResume() {
	    super.onResume();
		int indx = Utils.getPrefernciesInt(this,  Settings.SETTING_INDX);
        	
        ImageView indicationImage = (ImageView) findViewById(R.id.indicationImage);
		int id = getResources().getIdentifier("ic_indication_"+indx, "drawable", getPackageName());
		indicationImage.setImageResource(id);
		
		TextView indicationTitle = (TextView) findViewById(R.id.indicationTitle);
		indicationTitle.setText(Settings.indications.get(indx));
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
		indicationTitle.setTypeface(tf);
		
		//drinks
	   	if (Settings.drinking.size() == 0) {
	   		Settings.prepareData(this);
	   	}
		String[][] drinks = Settings.drinking.get(indx);
		String[] drink = drinks[Settings.notificationIndex[period]];
		
		//texti
		TextView notificationPeriod = (TextView) findViewById(R.id.notificationPeriod);
		notificationPeriod.setMaxLines(1);
		notificationPeriod.setEllipsize(TruncateAt.END);
		notificationPeriod.setText(drink[0]);

		TextView notificationVolume = (TextView) findViewById(R.id.notificationVolume);
		notificationVolume.setText(drink[2]);

		TextView notificationTemp = (TextView) findViewById(R.id.notificationTemp);
		notificationTemp.setText(drink[1]);

		TextView notificationSpeed = (TextView) findViewById(R.id.notificationSpeed);
		notificationSpeed.setText(drink[3]);

		//ikone
		Typeface tfl = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

		LinearLayout lNotificationIcons = (LinearLayout) findViewById(R.id.notificationIcons);
		lNotificationIcons.removeAllViews();
		int resId;
		for (int i=0; i<drinks.length; i++) {
			TextView iv = new TextView(this);
			iv.setLayoutParams(new LayoutParams( LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,1f));
			if (i < Settings.notificationIndex[period]) {
				resId = getResources().getIdentifier( drinks[i][4]+"_inactive", "drawable", getPackageName());
			} else {
				resId = getResources().getIdentifier( drinks[i][4], "drawable", getPackageName());
			}
			iv.setCompoundDrawablesWithIntrinsicBounds(0, resId, 0, 0);
			iv.setText(drinks[i][0].toLowerCase());
			iv.setMaxLines(1);
			iv.setEllipsize(TruncateAt.END);
			iv.setGravity(Gravity.CENTER_HORIZONTAL);
			iv.setTypeface(tfl);
			lNotificationIcons.addView(iv);

		}

		//datum
		final Calendar c = Calendar.getInstance();
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);
		String monthString = new DateFormatSymbols().getMonths()[mMonth];
	   	
	   	TextView notificationDate = (TextView) findViewById(R.id.notificationDate);
	   	notificationDate.setText(mDay+". "+monthString);
	   	
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		mainMenu = menu;
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.action_bar, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Utils.resetMenu(mainMenu);		
		if (checkRateIt()) {
			if (item.getItemId() == android.R.id.home) {
		    	finish();
		    } else if (item.getItemId() == R.id.calendar) {
				Intent intent = new Intent(this, CalendarActivity.class);
				startActivity(intent);	    	
		    } else if (item.getItemId() == R.id.settings) { 
				Intent intent = new Intent(this, SettingsActivity.class);
				startActivity(intent);	    	
			}
		}	    
	    return true;
	}
	
	private boolean checkRateIt() {
	   	if (!Utils.getPrefernciesBoolean(NotiActivity.this, Settings.SETTING_RATE_IT_FINISHED, false)) {
	        long rateStart = Utils.getPrefernciesLong(NotiActivity.this, Settings.SETTING_RATE_IT_START);
		    int rateCount = Utils.getPrefernciesInt(NotiActivity.this, Settings.SETTING_RATE_IT_COUNT);
		    rateCount++;
		    Calendar c = Calendar.getInstance();
		    //if ((rateCount >= Settings.RATE_COUNT) && (rateStart + Settings.RATE_PERIOD*24*60*60*1000 > c.getTime().getTime())) {
		    //za test dam 3 min
		    if ((rateCount >= Settings.RATE_COUNT) && (rateStart + 3*60*1000 > c.getTime().getTime())) {
		    //if (true) {
			  	//odpri okno za rate
				final Dialog dialogConfirmation = new Dialog(this,R.style.Dialog);
				dialogConfirmation.setContentView(R.layout.dialog_rate_it); 
				dialogConfirmation.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		    
				Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
				TextView confirmationTitle = (TextView) dialogConfirmation.findViewById(R.id.rate_it_title);
				confirmationTitle.setTypeface(tf);
				dialogConfirmation.show();				
			    
				LinearLayout close = (LinearLayout) dialogConfirmation.findViewById(R.id.close);
				close.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialogConfirmation.dismiss();
						finish();
					}
				}); 
				
				Button btnYes = (Button) dialogConfirmation.findViewById(R.id.btn_rate_it_yes);
				btnYes.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
				    	Utils.savePrefernciesBoolean(NotiActivity.this, Settings.SETTING_RATE_IT_FINISHED, true);
				    	dialogConfirmation.dismiss();
				    	NotiActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Settings.SETTING_APP_PNAME)));
					}
				}); 
				 
				Button btnNo = (Button) dialogConfirmation.findViewById(R.id.btn_rate_it_no);
				btnNo.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) { 
				    	Utils.savePrefernciesBoolean(NotiActivity.this, Settings.SETTING_RATE_IT_FINISHED, true);
				    	dialogConfirmation.dismiss();	
				    	finish();
				    }
				});	
				 
				Button btnLater = (Button) dialogConfirmation.findViewById(R.id.btn_rate_it_later);
				btnLater.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) { 
						Utils.savePrefernciesInt(NotiActivity.this, Settings.SETTING_RATE_IT_COUNT, -1);
						dialogConfirmation.dismiss();
						finish();
					}
				});					
		    }
		    else {
		    	Utils.savePrefernciesInt(NotiActivity.this, Settings.SETTING_RATE_IT_COUNT, rateCount);
		    	return true;
		    }
	
	    }
	    else {
	    	return true;
	    }
	    
	    return false;
	}

	

}
