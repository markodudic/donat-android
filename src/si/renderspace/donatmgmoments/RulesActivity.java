package si.renderspace.donatmgmoments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class RulesActivity extends Activity {

	Menu mainMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rules);

		getActionBar().setHomeButtonEnabled(true);
		Drawable bg = (Drawable)getResources().getDrawable(R.drawable.dr_action_bar_border); 
        getActionBar().setBackgroundDrawable(bg);
        getActionBar().setTitle(R.string.home);
		
        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        TextView tvTitle = (TextView) findViewById(titleId);
        Typeface ft=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        tvTitle.setTypeface(ft);

        TextView tvRules = (TextView) findViewById(R.id.tv_rules);
        tvRules.setMovementMethod(LinkMovementMethod.getInstance());
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
