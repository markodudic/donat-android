package si.renderspace.donatmgmoments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class RulesActivity extends Activity {

	private Spinner spLanguages, spZbujanje, spZajtrk, spKosilo, spVecerja, spSpanje, spObrokov;
	Menu mainMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rules);

		getActionBar().setHomeButtonEnabled(true);
		
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
		}
	    
	    return true;
	}
	

}
