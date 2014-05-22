package si.renderspace.donatmgmoments;

import java.util.Locale;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {
 
    private static int SPLASH_TIME_OUT = 1000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
 
        int lang = Utils.getPrefernciesInt(SplashScreenActivity.this, Settings.SETTING_LANG);
        if (lang == -1) {
        	String langDefault = Locale.getDefault().getLanguage();
        	if (langDefault.equals("ru") || (langDefault.equals("hr")) || (langDefault.equals("it"))){
        		Settings.setLanguage(SplashScreenActivity.this, langDefault);
            } else {
            	Settings.setLanguage(SplashScreenActivity.this, "en"); 
            };
        } else {
        	Settings.setLanguage(SplashScreenActivity.this, Settings.languages.get(lang));
        }
        
        new Handler().postDelayed(new Runnable() {
 
	            @Override
	            public void run() {
	            	if (Utils.getPrefernciesBoolean(SplashScreenActivity.this, Settings.SETTING_FIRST_START, false)) {
	            		Intent i = new Intent(SplashScreenActivity.this, HomeScreenActivity.class);
	            		startActivity(i);
		            } else {
		            	Utils.savePrefernciesString(SplashScreenActivity.this, Settings.SETTING_TESCE, "07:00");
						Utils.savePrefernciesString(SplashScreenActivity.this, Settings.SETTING_ZAJTRK, "07:30");
						Utils.savePrefernciesString(SplashScreenActivity.this, Settings.SETTING_KOSILO, "12:30");
						Utils.savePrefernciesString(SplashScreenActivity.this, Settings.SETTING_VECERJA, "19:30");
						Utils.savePrefernciesString(SplashScreenActivity.this, Settings.SETTING_SPANJE, "22:00");
						Utils.savePrefernciesInt(SplashScreenActivity.this, Settings.SETTING_OBROKOV, 3);
						
						Intent i = new Intent(SplashScreenActivity.this, SettingsActivity.class);
						startActivity(i);						
		            }
                finish();
            }
        }, SPLASH_TIME_OUT);
        
        Settings.prepareData(SplashScreenActivity.this);
    }
 
}