package si.renderspace.donatmgmoments;


import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.Menu;

public class Utils {
    public static final String PREFS_NAME = "si.renderspace.donatmgmoments.PREFS_FILE";

    public static void savePrefernciesString(Context context, String paramString1, String paramString2)
    {
        SharedPreferences.Editor localEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
        localEditor.putString(paramString1, paramString2);
        localEditor.commit();
    }

    public static String getPrefernciesString(Context context, String paramString)
    {
        return context.getSharedPreferences(PREFS_NAME, 0).getString(paramString, null);
    }

    public static void savePrefernciesInt(Context context, String paramString1, int paramString2)
    {
        SharedPreferences.Editor localEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
        localEditor.putInt(paramString1, paramString2);
        localEditor.commit();
    }

    public static int getPrefernciesInt(Context context, String paramString)
    {
        return context.getSharedPreferences(PREFS_NAME, 0).getInt(paramString, -1);
    }


    public static void savePrefernciesBoolean(Context context, String paramString1, boolean paramString2)
    {
        SharedPreferences.Editor localEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
        localEditor.putBoolean(paramString1, paramString2);
        localEditor.commit();
    }

    public static boolean getPrefernciesBoolean(Context context, String paramString, boolean def)
    {
        return context.getSharedPreferences(PREFS_NAME, 0).getBoolean(paramString, def);
    }

    
    public static void resetMenu(Menu mainMenu) {
		for (int i = 0; i < mainMenu.size(); i++) {
	        if (mainMenu.getItem(i).getItemId() == R.id.calendar) {
	            mainMenu.getItem(i).setIcon(R.drawable.ic_calendar);
	        } else if (mainMenu.getItem(i).getItemId() == R.id.settings) {
	            mainMenu.getItem(i).setIcon(R.drawable.ic_settings);
	        }
	    }
	}
    
    public static void setMenu(Menu mainMenu, int id) {
		for (int i = 0; i < mainMenu.size(); i++) {
	        if ((mainMenu.getItem(i).getItemId() == R.id.calendar) && (id == R.id.calendar)){
	            mainMenu.getItem(i).setIcon(R.drawable.ic_calendar_pressed);
	        } else if ((mainMenu.getItem(i).getItemId() == R.id.settings) && (id == R.id.settings)){
	            mainMenu.getItem(i).setIcon(R.drawable.ic_settings_pressed);
	        }
	    }
	}
    
    public static void setLanguage(Context context, String lang) {
		Locale locale = new Locale(lang);
	    Locale.setDefault(locale);
	    Configuration config = new Configuration();
	    config.locale = locale;
	    context.getResources().updateConfiguration(config, null);
    }

}
