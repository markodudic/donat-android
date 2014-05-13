package si.renderspace.donatmgmoments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

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
        return context.getSharedPreferences(PREFS_NAME, 0).getInt(paramString, 0);
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


}
