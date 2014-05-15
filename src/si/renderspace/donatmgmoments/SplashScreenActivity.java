package si.renderspace.donatmgmoments;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {
 
    private static int SPLASH_TIME_OUT = 1000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
 
        int lang = Utils.getPrefernciesInt(SplashScreenActivity.this, "LANG");
        if (lang == -1) {
        	String langDefault = Locale.getDefault().getLanguage();
        	if (langDefault.equals("ru") || (langDefault.equals("sr")) || (langDefault.equals("it"))){
        		Utils.setLanguage(SplashScreenActivity.this, langDefault);
            } else {
        		Utils.setLanguage(SplashScreenActivity.this, "en"); 
            };
        } else {
        	Utils.setLanguage(SplashScreenActivity.this, Settings.languages.get(lang));
        }
        
        new Handler().postDelayed(new Runnable() {
 
	            @Override
	            public void run() {
	            	if (Utils.getPrefernciesBoolean(SplashScreenActivity.this, "firstStart", false)) {
	            		Intent i = new Intent(SplashScreenActivity.this, HomeScreenActivity.class);
	            		startActivity(i);
		            } else {
		            	Utils.savePrefernciesString(SplashScreenActivity.this, "TESCE", "7:00");
						Utils.savePrefernciesString(SplashScreenActivity.this, "ZAJTRK", "7:30");
						Utils.savePrefernciesString(SplashScreenActivity.this, "KOSILO", "12:30");
						Utils.savePrefernciesString(SplashScreenActivity.this, "VECERJA", "19:30");
						Utils.savePrefernciesString(SplashScreenActivity.this, "SPANJE", "22:00");
						Utils.savePrefernciesInt(SplashScreenActivity.this, "OBROKOV", 3);
						
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