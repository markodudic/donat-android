package si.renderspace.donatmgmoments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
 
public class SplashScreenActivity extends Activity {
 
    private static int SPLASH_TIME_OUT = 2000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
 
        new Handler().postDelayed(new Runnable() {
 
	            @Override
	            public void run() {
	            	if (Utils.getPrefernciesBoolean(SplashScreenActivity.this, "firstStart", false)) {
		            	Intent i = new Intent(SplashScreenActivity.this, HomeScreenActivity.class);
	                	startActivity(i);
		            } else {
		            	Utils.savePrefernciesBoolean(SplashScreenActivity.this, "firstStart", true); 
	            		Intent i = new Intent(SplashScreenActivity.this, SettingsActivity.class);
	                	startActivity(i);
		            }
                finish();
            }
        }, SPLASH_TIME_OUT);
        
        Settings.prepareData(SplashScreenActivity.this);
    }
 
}