package si.renderspace.donatmgmoments;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

public class NotificationActivity extends Activity {

	Menu mainMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);

		getActionBar().setHomeButtonEnabled(true);
		Drawable bg = (Drawable)getResources().getDrawable(R.drawable.dr_action_bar_border); 
        getActionBar().setBackgroundDrawable(bg);
		
		Intent intent = getIntent();
		final int indx = intent.getIntExtra("INDX", 1);
		final int period = intent.getIntExtra("PERIOD", 0);
        
		ImageView indicationImage = (ImageView) findViewById(R.id.indicationImage);
		int id = getResources().getIdentifier("ic_indication_"+indx, "drawable", getPackageName());
		indicationImage.setImageResource(id);
		
		TextView indicationTitle = (TextView) findViewById(R.id.indicationTitle);
		indicationTitle.setText(Settings.indications.get(indx));
		
		//datum
		final Calendar c = Calendar.getInstance();
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);
		String monthString = new DateFormatSymbols().getMonths()[mMonth];
	   	
	   	TextView notificationDate = (TextView) findViewById(R.id.notificationDate);
	   	notificationDate.setText(mDay+". "+monthString);
		
		//drinks
		String[][] drinks = Settings.drinking.get(indx);
		String[] drink = drinks[period];

		TextView notificationPeriod = (TextView) findViewById(R.id.notificationPeriod);
		notificationPeriod.setText(drink[0]);

		TextView notificationVolume = (TextView) findViewById(R.id.notificationVolume);
		notificationVolume.setText(drink[2]);

		TextView notificationTemp = (TextView) findViewById(R.id.notificationTemp);
		notificationTemp.setText(drink[1]);

		TextView notificationSpeed = (TextView) findViewById(R.id.notificationSpeed);
		notificationSpeed.setText(drink[3]);

		//ikone
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
			lNotificationIcons.addView(iv);

		}
		
		Button btnClose = (Button) findViewById(R.id.btn_close);
		btnClose.setOnClickListener(new OnClickListener() {
	 		  @Override
			  public void onClick(View v) {
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
	    } else if (item.getItemId() == R.id.settings) { 
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);	    	
		}
	    
	    return true;
	}
	

}
