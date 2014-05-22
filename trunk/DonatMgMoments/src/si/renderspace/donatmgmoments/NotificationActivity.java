package si.renderspace.donatmgmoments;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

public class NotificationActivity extends Activity {

	Menu mainMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);

		getActionBar().setHomeButtonEnabled(true);
		Drawable bg = (Drawable)getResources().getDrawable(R.drawable.dr_action_bar_border); 
        getActionBar().setBackgroundDrawable(bg);
        
        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView tvTitle = (TextView) findViewById(titleId);
        Typeface ft=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        tvTitle.setTypeface(ft);
        
        int indx = Utils.getPrefernciesInt(this,  Settings.SETTING_INDX);
		
		Intent intent = getIntent();
		final int period = intent.getIntExtra("PERIOD", 0);
        
		ImageView indicationImage = (ImageView) findViewById(R.id.indicationImage);
		int id = getResources().getIdentifier("ic_indication_"+indx, "drawable", getPackageName());
		indicationImage.setImageResource(id);
		
		TextView indicationTitle = (TextView) findViewById(R.id.indicationTitle);
		indicationTitle.setText(Settings.indications.get(indx));
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
		indicationTitle.setTypeface(tf);
		
		//datum
		final Calendar c = Calendar.getInstance();
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);
		String monthString = new DateFormatSymbols().getMonths()[mMonth];
	   	
	   	TextView notificationDate = (TextView) findViewById(R.id.notificationDate);
	   	notificationDate.setText(mDay+". "+monthString);
		
		//drinks
	   	if (Settings.drinking.size() == 0) {
	   		Settings.prepareData(NotificationActivity.this);
	   	}
		String[][] drinks = Settings.drinking.get(indx);
		String[] drink = drinks[period];

		//system notification
		showNotification(period, getResources().getString(R.string.app_name), drink[0]+", "+drink[2]+", "+drink[1]+", "+drink[3]);
		
		//texti
		TextView notificationPeriod = (TextView) findViewById(R.id.notificationPeriod);
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
		int resId;
		for (int i=0; i<drinks.length; i++) {
			TextView iv = new TextView(this);
			iv.setLayoutParams(new LayoutParams( LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,1f));
			if (i==period) {
				resId = getResources().getIdentifier( drinks[i][4]+"_active", "drawable", getPackageName());
			} else {
				resId = getResources().getIdentifier( drinks[i][4], "drawable", getPackageName());
			}
			iv.setCompoundDrawablesWithIntrinsicBounds(0, resId, 0, 0);
			iv.setText(drinks[i][0].toLowerCase());
			iv.setGravity(Gravity.CENTER_HORIZONTAL);
			iv.setTypeface(tfl);
			lNotificationIcons.addView(iv);

		}
		
		Button btnClose = (Button) findViewById(R.id.btn_close);
		btnClose.setOnClickListener(new OnClickListener() {
	 		  @Override
			  public void onClick(View v) {
	 			  ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
	 			  finish();
			  }
	 	});			
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
			Intent intent = new Intent(this, CalendarActivity.class);
			startActivity(intent);	    	
	    } else if (item.getItemId() == R.id.settings) { 
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);	    	
		}
	    
	    return true;
	}
	
	
	private void showNotification(int period, String title, String text){
		Intent resultIntent = new Intent(this, NotificationActivity.class);
		resultIntent.putExtra("PERIOD", period);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);
		
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.ic_notification)
		        .setContentTitle(title)
		        .setContentText(text)
		        .setAutoCancel(true)
		        .setContentIntent(pIntent);
		
		NotificationManager mNotificationManager =  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, mBuilder.build());
	}

}
