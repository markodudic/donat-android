package si.renderspace.donatmgmoments;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeScreenActivity extends Activity {
	
	int j;
	Menu mainMenu;
	private Handler handler = new Handler();
	//private Runnable runnable;
    
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home_screen);
		
		/*runnable = new Runnable(){
		    public void run() {
				checkNotifications();
		    }
		};*/ 
		
		handler.postDelayed(runnable, Settings.TIMER);
		
		getActionBar().setHomeButtonEnabled(true);
		Drawable bg = (Drawable)getResources().getDrawable(R.drawable.dr_action_bar_border); 
        getActionBar().setBackgroundDrawable(bg);
        
		TextView tvIndication1 = (TextView) findViewById(R.id.indication_1);
		tvIndication1.setText(Settings.indications.get(1));
		LinearLayout lIndication1 = (LinearLayout) findViewById(R.id.l_indication_1);
		lIndication1.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
					showIndication(1);
			    }
			});

		TextView tvIndication2 = (TextView) findViewById(R.id.indication_2);
		tvIndication2.setText(Settings.indications.get(2));
		LinearLayout lIndication2 = (LinearLayout) findViewById(R.id.l_indication_2);
		lIndication2.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	showIndication(2);
			    }
			});

		TextView tvIndication3 = (TextView) findViewById(R.id.indication_3);
		tvIndication3.setText(Settings.indications.get(3));
		LinearLayout lIndication3 = (LinearLayout) findViewById(R.id.l_indication_3);
		lIndication3.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	showIndication(3);
			    }
			});

		TextView tvIndication4 = (TextView) findViewById(R.id.indication_4);
		tvIndication4.setText(Settings.indications.get(4));
		LinearLayout lIndication4 = (LinearLayout) findViewById(R.id.l_indication_4);
		lIndication4.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	showIndication(4);
			    }
			});

		TextView tvIndication5 = (TextView) findViewById(R.id.indication_5);
		tvIndication5.setText(Settings.indications.get(5));
		LinearLayout lIndication5 = (LinearLayout) findViewById(R.id.l_indication_5);
		lIndication5.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	showIndication(5);
			    }
			});
		
		TextView tvIndication6 = (TextView) findViewById(R.id.indication_6);
		tvIndication6.setText(Settings.indications.get(6));
		LinearLayout lIndication6 = (LinearLayout) findViewById(R.id.l_indication_6);
		lIndication6.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	showIndication(6);
			    }
			}); 
		
		TextView tvIndication7 = (TextView) findViewById(R.id.indication_7);
		tvIndication7.setText(Settings.indications.get(7));
		LinearLayout lIndication7 = (LinearLayout) findViewById(R.id.l_indication_7);
		lIndication7.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	showIndication(7);
			    }
			});
		
		TextView tvIndication8 = (TextView) findViewById(R.id.indication_8);
		tvIndication8.setText(Settings.indications.get(8));
		LinearLayout lIndication8 = (LinearLayout) findViewById(R.id.l_indication_8);
		lIndication8.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	showIndication(8);
			    }
			});
		
		TextView tvIndication9 = (TextView) findViewById(R.id.indication_9);
		tvIndication9.setText(Settings.indications.get(9));
		LinearLayout lIndication9 = (LinearLayout) findViewById(R.id.l_indication_9);
		lIndication9.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	showIndication(9);
			    }
			});
		
		TextView tvIndication10 = (TextView) findViewById(R.id.indication_10);
		tvIndication10.setText(Settings.indications.get(10));
		LinearLayout lIndication10 = (LinearLayout) findViewById(R.id.l_indication_10);
		lIndication10.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	showIndication(10);
			    }
			});		
		
		 
	}

	@Override
	public void onResume() {
	    super.onResume();
	    
    	Drawable bg = (Drawable)getResources().getDrawable(R.drawable.dr_item_unpressed); 
		for (j=1; j<=Settings.indications.size(); j++) {
			int idd = getResources().getIdentifier("iv_indication_"+j, "id", getPackageName());
			ImageView ivIndication1 = (ImageView) findViewById(idd);
			ivIndication1.setBackgroundDrawable(bg);
			
			int id = getResources().getIdentifier("indication_"+j+"_selected", "id", getPackageName());
			ImageView iv = (ImageView) findViewById(id);
			iv.setVisibility(View.GONE);	
		}
		if (Utils.getPrefernciesInt(this,  Settings.SETTING_INDX) != -1) {
			int id = getResources().getIdentifier("indication_"+Utils.getPrefernciesInt(this,  Settings.SETTING_INDX)+"_selected", "id", getPackageName());
			ImageView iv = (ImageView) findViewById(id);
			iv.setVisibility(View.VISIBLE);	
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.home_screen, menu);
		//return true;
		mainMenu = menu;
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.action_bar, menu);
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
	 
	public void showIndication(int indx) {
		int id = getResources().getIdentifier("iv_indication_"+indx, "id", getPackageName());
		ImageView ivIndication = (ImageView) findViewById(id);
    	Drawable bg = (Drawable)getResources().getDrawable(R.drawable.dr_item_pressed); 
        ivIndication.setBackgroundDrawable(bg);

        Intent intent = new Intent(this, IndicationActivity.class);
		intent.putExtra("INDX", indx);
		startActivity(intent);
	}
	
	private void checkNotifications() {
		Calendar calendar = Calendar.getInstance();
		int hourCurr = calendar.get(Calendar.HOUR_OF_DAY);
		int minuteCurr = calendar.get(Calendar.MINUTE);

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		long dateCurr = calendar.getTimeInMillis();
		
		Date[] notificationTimes = Settings.notificationTimes;
		if ((notificationTimes != null) && 
			(Utils.getPrefernciesInt(this,  Settings.SETTING_INDX)!=-1) && 
			(dateCurr >= Utils.getPrefernciesLong(this,  Settings.SETTING_START_DATE))) {
			for(int i=0; i<notificationTimes.length; i++) {
				//System.out.println(notificationTimes[i]);
				calendar.setTime(notificationTimes[i]); 
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				int minute = calendar.get(Calendar.MINUTE);
				System.out.println(hourCurr+":"+minuteCurr+":"+hour+":"+minute);
				if ((hourCurr==hour) && (minuteCurr==minute)) {
					//odprem okno
					Intent intent = new Intent(this, NotificationActivity.class);
					intent.putExtra("PERIOD", i);
					startActivity(intent);	
					break;
				}
			}
		}
        //handler.postDelayed(runnable, Settings.TIMER);
	}
	
	private Runnable runnable = new Runnable() {
	   @Override
	   public void run() {
	      checkNotifications();
	      handler.postDelayed(this, Settings.TIMER);
	   }
	};	

}



