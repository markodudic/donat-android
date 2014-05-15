package si.renderspace.donatmgmoments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home_screen);
		
		getActionBar().setHomeButtonEnabled(true);

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
	    
		for (j=1; j<=Settings.indications.size(); j++) {
			int id = getResources().getIdentifier("indication_"+j+"_selected", "id", getPackageName());
			ImageView iv = (ImageView) findViewById(id);
			iv.setVisibility(View.GONE);	
		}
	    if (Settings.indicationCurrentIndx != -1) {
			int id = getResources().getIdentifier("indication_"+Settings.indicationCurrentIndx+"_selected", "id", getPackageName());
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
	    } else if (item.getItemId() == R.id.settings) {
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);	    	
	    }
	    
	    return true;
	}
	
	public void showIndication(int indx) {
		Intent intent = new Intent(this, IndicationActivity.class);
		intent.putExtra("INDX", indx);
		startActivity(intent);
	}
}
