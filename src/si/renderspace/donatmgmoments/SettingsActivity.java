package si.renderspace.donatmgmoments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsActivity extends Activity {

	private Spinner spLanguages, spZbujanje, spZajtrk, spKosilo, spVecerja, spSpanje, spObrokov;
	Menu mainMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
 
		getActionBar().setHomeButtonEnabled(true);
		Drawable bg = (Drawable)getResources().getDrawable(R.drawable.dr_action_bar_border); 
        getActionBar().setBackgroundDrawable(bg);
		
		spLanguages = (Spinner) findViewById(R.id.sp_languages);
		spZbujanje = (Spinner) findViewById(R.id.sp_zbujanje);
		spZajtrk = (Spinner) findViewById(R.id.sp_zajtrk);
		spKosilo = (Spinner) findViewById(R.id.sp_kosilo);
		spVecerja = (Spinner) findViewById(R.id.sp_vecerja);
		spSpanje = (Spinner) findViewById(R.id.sp_spanje);
		spObrokov = (Spinner) findViewById(R.id.sp_obrokov); 

		
		Button btnSave = (Button) findViewById(R.id.btn_save);
		btnSave.setOnClickListener(new OnClickListener() {
	 		  @Override
			  public void onClick(View v) {
	 			  Utils.savePrefernciesString(SettingsActivity.this, "TESCE", (String)spZbujanje.getSelectedItem());
	 			  Utils.savePrefernciesString(SettingsActivity.this, "ZAJTRK", (String)spZajtrk.getSelectedItem());
	 			  Utils.savePrefernciesString(SettingsActivity.this, "KOSILO", (String)spKosilo.getSelectedItem());
	 			  Utils.savePrefernciesString(SettingsActivity.this, "VECERJA", (String)spVecerja.getSelectedItem());
	 			  Utils.savePrefernciesString(SettingsActivity.this, "SPANJE", (String)spSpanje.getSelectedItem());
	 			  Utils.savePrefernciesInt(SettingsActivity.this, "OBROKOV", Integer.parseInt((String)spObrokov.getSelectedItem()));
		 	    
	 			  Settings.updateData(SettingsActivity.this);
	 			 			        
	 			  int langId = (int)spLanguages.getSelectedItemId();
	 			  if (langId != Utils.getPrefernciesInt(SettingsActivity.this, "LANG")) {
		 			Utils.savePrefernciesInt(SettingsActivity.this, "LANG", langId);
		 			Settings.setLanguage(SettingsActivity.this, Settings.languages.get(langId));
	 			  	restartApplication();
	 			  }
	 			 
	 			  Utils.resetMenu(mainMenu);
	 			 if (Utils.getPrefernciesBoolean(SettingsActivity.this, "firstStart", false)) {
	 				 finish();
	 			 } else {
	 				Intent intent = new Intent(SettingsActivity.this, HomeScreenActivity.class);
	 				startActivity(intent);
					Utils.savePrefernciesBoolean(SettingsActivity.this, "firstStart", true); 
	 			 }
			  }
	 	});	
		
		
		TextView tvPravila = (TextView) findViewById(R.id.pravila);
		tvPravila.setOnClickListener(new OnClickListener() {
	 		  @Override
			  public void onClick(View v) {
	 			    Intent i = new Intent(SettingsActivity.this, RulesActivity.class);
	 			    SettingsActivity.this.startActivity(i);
			  }
	 	});		
		

	}

	@Override
	public void onResume() {
	    super.onResume();
	    setSpinnerIdSelection(spLanguages, "LANG");
	    setSpinnerStringSelection(spZbujanje, "TESCE");
	    setSpinnerStringSelection(spZajtrk, "ZAJTRK");
	    setSpinnerStringSelection(spKosilo, "KOSILO");
	    setSpinnerStringSelection(spVecerja, "VECERJA");
	    setSpinnerStringSelection(spSpanje, "SPANJE");
	    setSpinnerIntSelection(spObrokov, "OBROKOV");
	}	

	public void setSpinnerStringSelection(Spinner sp, String pref) {
		ArrayAdapter myAdap = (ArrayAdapter) sp.getAdapter();
		int spinnerPosition = myAdap.getPosition(Utils.getPrefernciesString(SettingsActivity.this, pref));
		sp.setSelection(spinnerPosition);
	}

	public void setSpinnerIntSelection(Spinner sp, String pref) {
		ArrayAdapter myAdap = (ArrayAdapter) sp.getAdapter();
	    int spinnerPosition = myAdap.getPosition(Utils.getPrefernciesInt(SettingsActivity.this, pref));
	    sp.setSelection(spinnerPosition);
	}

	public void setSpinnerIdSelection(Spinner sp, String pref) {
		int spinnerPosition = Utils.getPrefernciesInt(SettingsActivity.this, pref);
	    sp.setSelection(spinnerPosition);
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
			 if (Utils.getPrefernciesBoolean(SettingsActivity.this, "firstStart", false)) {
 				 finish();
 			 } else {
 				Intent intent = new Intent(SettingsActivity.this, HomeScreenActivity.class);
 				startActivity(intent);
 			 }
	    } else if (item.getItemId() == R.id.calendar) {
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
	


}
