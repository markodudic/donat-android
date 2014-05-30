package si.renderspace.donatmgmoments;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.widget.Toast;


public class Settings {
	
	public static String SETTING_FIRST_START = "FIRST_START";
	public static String SETTING_INDX = "INDX";
	public static String SETTING_START_DATE = "START_DATE";
	public static String SETTING_END_DATE = "END_DATE";
	public static String SETTING_LANG = "LANG";
	public static String SETTING_TESCE = "TESCE";
	public static String SETTING_ZAJTRK = "ZAJTRK";
	public static String SETTING_KOSILO = "KOSILO";
	public static String SETTING_VECERJA = "VECERJA";
	public static String SETTING_SPANJE = "SPANJE";
	public static String SETTING_OBROKOV = "OBROKOV";
	public static String SETTING_HISTORY = "HISTORY";

	public static HashMap<Integer,String> languages = new HashMap<Integer,String>() {{
																					    put(0, "en");
																					    put(1, "ru");
																					    put(2, "hr");
																					    put(3, "it");
																					}};
	//nacini pitja
	public static HashMap<Integer,String> indications = new HashMap<Integer,String>();
	public static HashMap<Integer,String> indications_desc = new HashMap<Integer,String>();
	public static HashMap<Integer,String[][]> drinking = new HashMap<Integer,String[][]>();
	public static HashMap<Integer,String> interval = new HashMap<Integer,String>();
	
	//trenutna indikacija
	//public static int indicationCurrentIndx = -1; //-1 : ni trenutne indikacije
	//public static Date indicationCurrentDate;

	//nastavitve ur za intervale
	public static HashMap<String,Date> intervalHours = new HashMap<String,Date>();
	public static int intervalMeals = 3;
	
	//notifications glede na nacin pitja in nastvavitvah ur za intervale. Nastavi se po izbiri indikacije.
	public static int NOTIFICATION_ALARM_MINUTES = 10*60*1000; //10 minut v milisec
	public static Date[] notificationTimes;
	public static int[] notificationIndex;
 
	public final static int TIMER = 60*1000; //1 minut v milisec

	public static JSONArray history = new JSONArray();

    
	public static void prepareData (Context context) {
		indications.put(1,context.getResources().getString(R.string.indication_1));
		indications.put(2,context.getResources().getString(R.string.indication_2));
		indications.put(3,context.getResources().getString(R.string.indication_3));
		indications.put(4,context.getResources().getString(R.string.indication_4));
		indications.put(5,context.getResources().getString(R.string.indication_5));
		indications.put(6,context.getResources().getString(R.string.indication_6));
		indications.put(7,context.getResources().getString(R.string.indication_7));
		indications.put(8,context.getResources().getString(R.string.indication_8));
		indications.put(9,context.getResources().getString(R.string.indication_9));
		indications.put(10,context.getResources().getString(R.string.indication_10));

		indications_desc.put(1,context.getResources().getString(R.string.indication_1_desc));
		indications_desc.put(2,context.getResources().getString(R.string.indication_2_desc));
		indications_desc.put(3,context.getResources().getString(R.string.indication_3_desc));
		indications_desc.put(4,context.getResources().getString(R.string.indication_4_desc));
		indications_desc.put(5,context.getResources().getString(R.string.indication_5_desc));
		indications_desc.put(6,context.getResources().getString(R.string.indication_6_desc));
		indications_desc.put(7,context.getResources().getString(R.string.indication_7_desc));
		indications_desc.put(8,context.getResources().getString(R.string.indication_8_desc));
		indications_desc.put(9,context.getResources().getString(R.string.indication_9_desc));
		indications_desc.put(10,context.getResources().getString(R.string.indication_10_desc));
	
		String[][] drinking_1 = new String[][]{	
				{context.getResources().getString(R.string.drinking_na_tesce),context.getResources().getString(R.string.temperature_toplo)," 3-8 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_hitro),"ic_tesce"},
				{context.getResources().getString(R.string.drinking_pred_spanjem),context.getResources().getString(R.string.temperature_mlacno)," 2 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_razmeroma_hitro),"ic_pred_spanjem"}
		};
		drinking.put(1,drinking_1);

		String[][] drinking_2 = new String[][]{	
				{context.getResources().getString(R.string.drinking_veckrat_dnevno),context.getResources().getString(R.string.temperature_sobna)," 1 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_veckrat_dnevno"},
				{context.getResources().getString(R.string.drinking_20_min_pred),context.getResources().getString(R.string.temperature_sobna)," 1 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_pred_jedjo"},
				{context.getResources().getString(R.string.drinking_med_obroki),context.getResources().getString(R.string.temperature_sobna)," 1 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_med_obroki"}
		};
		drinking.put(2,drinking_2);

		String[][] drinking_3 = new String[][]{	
				{context.getResources().getString(R.string.drinking_na_tesce),context.getResources().getString(R.string.temperature_hladno)," 2 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_tesce"},
				{context.getResources().getString(R.string.drinking_opoldne),context.getResources().getString(R.string.temperature_hladno)," 1 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_opoldne"},
				{context.getResources().getString(R.string.drinking_zvecer),context.getResources().getString(R.string.temperature_hladno)," 1 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_zvecer"}
		};
		drinking.put(3,drinking_3);

		String[][] drinking_4 = new String[][]{	
				{context.getResources().getString(R.string.drinking_na_tesce),context.getResources().getString(R.string.temperature_toplo)," 3 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_razmeroma_hitro),"ic_tesce"},
				{context.getResources().getString(R.string.drinking_pred_kosilom),context.getResources().getString(R.string.temperature_hladno)," 1 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_pred_jedjo"},
				{context.getResources().getString(R.string.drinking_pred_vecerjo),context.getResources().getString(R.string.temperature_hladno)," 1 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_pred_jedjo"}
		};
		drinking.put(4,drinking_4);

		String[][] drinking_5 = new String[][]{	
				{context.getResources().getString(R.string.drinking_na_tesce),context.getResources().getString(R.string.temperature_mlacno)," 3-5 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_tesce"},
				{context.getResources().getString(R.string.drinking_pred_kosilom),context.getResources().getString(R.string.temperature_hladno)," 2 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_pred_jedjo"},
				{context.getResources().getString(R.string.drinking_pred_vecerjo),context.getResources().getString(R.string.temperature_mlacno)," 2 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_pred_jedjo"}
		};
		drinking.put(5,drinking_5);

		String[][] drinking_6 = new String[][]{	
				{context.getResources().getString(R.string.drinking_na_tesce),context.getResources().getString(R.string.temperature_mlacno)," 2 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_tesce"},
				{context.getResources().getString(R.string.drinking_pred_kosilom),context.getResources().getString(R.string.temperature_mlacno)," 2 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_pred_jedjo"},
				{context.getResources().getString(R.string.drinking_pred_vecerjo),context.getResources().getString(R.string.temperature_mlacno)," 2 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_pred_jedjo"},
				{context.getResources().getString(R.string.drinking_pred_spanjem),context.getResources().getString(R.string.temperature_mlacno)," 2 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_pred_spanjem"}
		};
		drinking.put(6,drinking_6);

		String[][] drinking_7 = new String[][]{	
				{context.getResources().getString(R.string.drinking_na_tesce),context.getResources().getString(R.string.temperature_toplo)," 3-5 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_hitro),"ic_tesce"},
				{context.getResources().getString(R.string.drinking_pred_jedjo),context.getResources().getString(R.string.temperature_hladno)," 1 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_pred_jedjo"}
		};
		drinking.put(7,drinking_7);

		
		String[][] drinking_8 = new String[][]{	
				{context.getResources().getString(R.string.drinking_3_4_dnevno),context.getResources().getString(R.string.temperature_sobna)," 1 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_veckrat_dnevno"}
		};
		drinking.put(8,drinking_8);

		
		String[][] drinking_9 = new String[][]{	
				{context.getResources().getString(R.string.drinking_na_tesce),context.getResources().getString(R.string.temperature_hladno)," 3 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_tesce"},
				{context.getResources().getString(R.string.drinking_pred_spanjem),context.getResources().getString(R.string.temperature_hladno)," 1-2 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_pred_spanjem"}
		};
		drinking.put(9,drinking_9);

		
		String[][] drinking_10 = new String[][]{	
				{context.getResources().getString(R.string.drinking_pred_jedjo),context.getResources().getString(R.string.temperature_hladno)," 1-2 "+context.getResources().getString(R.string.volume_suffix),context.getResources().getString(R.string.spped_pocasi),"ic_pred_jedjo"}
		};
		drinking.put(10,drinking_10);
		
		interval.put(1,context.getResources().getString(R.string.interval_5_dni));
		interval.put(2,context.getResources().getString(R.string.interval_stalno));
		interval.put(3,context.getResources().getString(R.string.interval_stalno));
		interval.put(4,context.getResources().getString(R.string.interval_5_dni));
		interval.put(5,context.getResources().getString(R.string.interval_6_tednov));
		interval.put(6,context.getResources().getString(R.string.interval_stalno));
		interval.put(7,context.getResources().getString(R.string.interval_3_mesece));
		interval.put(8,context.getResources().getString(R.string.interval_2_meseca));
		interval.put(9,context.getResources().getString(R.string.interval_2_meseca));
		interval.put(10,context.getResources().getString(R.string.interval_stalno));

		updateData(context);
		
		String h = Utils.getPrefernciesString(context, SETTING_HISTORY);
		if (h != null) {
			try {
				System.out.println("HISTORY GET="+h);
				history = new JSONArray(h);
			} catch (Exception e) {}
		}
		
		//za test
		history = new JSONArray();
		Calendar cc = Calendar.getInstance();
		cc.add(Calendar.MONTH, -4);
		Calendar cc1 = Calendar.getInstance();
		cc1.add(Calendar.DATE, -15);
		Calendar cc2 = Calendar.getInstance();
		cc2.add(Calendar.MONTH, -8);
		Calendar cc3 = Calendar.getInstance();
		cc3.add(Calendar.MONTH, -5);
		saveHistory (context, 4, cc2.getTimeInMillis(), cc3.getTimeInMillis());
		saveHistory (context, 8, cc.getTimeInMillis(), cc1.getTimeInMillis());
		//
	}
	
	public static void updateData (Context context) {
		setInterval(context, Settings.SETTING_TESCE);
		setInterval(context, Settings.SETTING_ZAJTRK);
		setInterval(context, Settings.SETTING_KOSILO);
		setInterval(context, Settings.SETTING_VECERJA);
		setInterval(context, Settings.SETTING_SPANJE);
		
		intervalMeals = Utils.getPrefernciesInt(context,  Settings.SETTING_OBROKOV); 
		if (Utils.getPrefernciesInt(context,  Settings.SETTING_INDX) != -1) {
			setNotificationTimes(context, Utils.getPrefernciesInt(context,  Settings.SETTING_INDX));
		}
		
	}
	
	public static void setInterval (Context context, String pref) {
		String prefStr = Utils.getPrefernciesString(context, pref);
		if (prefStr != null) {
			String[] spl = prefStr.split(":");
			long l = Integer.parseInt(spl[0])*60*60*1000+Integer.parseInt(spl[1])*60*1000;
			intervalHours.put(pref, new Date(l));
		}
	}

	public static void setNotificationTimes(Context context, int indx) {
		long danDel = (intervalHours.get(Settings.SETTING_SPANJE).getTime() - intervalHours.get(Settings.SETTING_TESCE).getTime()) / 3;
		
		switch (indx) {
	        case 1: case 9:
	    		notificationTimes = new Date[2];
	    		notificationTimes[0] = new Date(intervalHours.get(Settings.SETTING_ZAJTRK).getTime() - NOTIFICATION_ALARM_MINUTES);
		        notificationTimes[1] = new Date(intervalHours.get(Settings.SETTING_SPANJE).getTime() - NOTIFICATION_ALARM_MINUTES);
		        notificationIndex = new int[]{0,1};
	    		break;
	        case 2: 
	        	notificationTimes = new Date[10];
	        	notificationTimes[0] = new Date(intervalHours.get(Settings.SETTING_TESCE).getTime());
	        	notificationTimes[1] = new Date(intervalHours.get(Settings.SETTING_TESCE).getTime() + danDel);
	        	notificationTimes[2] = new Date(intervalHours.get(Settings.SETTING_TESCE).getTime() + (2 * danDel));
	        	notificationTimes[3] = new Date(intervalHours.get(Settings.SETTING_SPANJE).getTime());
	        	
	        	notificationTimes[4] = new Date(intervalHours.get(Settings.SETTING_ZAJTRK).getTime() - 20*60*1000 - NOTIFICATION_ALARM_MINUTES);
		        notificationTimes[5] = new Date(intervalHours.get(Settings.SETTING_KOSILO).getTime() - 20*60*1000 - NOTIFICATION_ALARM_MINUTES);
		        notificationTimes[6] = new Date(intervalHours.get(Settings.SETTING_VECERJA).getTime() - 20*60*1000 - NOTIFICATION_ALARM_MINUTES);
		        
	        	notificationTimes[7] = new Date(intervalHours.get(Settings.SETTING_ZAJTRK).getTime() + 90*60*1000);
		        notificationTimes[8] = new Date(intervalHours.get(Settings.SETTING_KOSILO).getTime() + 90*60*1000);
		        notificationTimes[9] = new Date(intervalHours.get(Settings.SETTING_VECERJA).getTime() + 90*60*1000);
		        notificationIndex = new int[]{0,0,0,0,1,1,1,2,2,2};
	    		break;
	        case 3: 
	    		notificationTimes = new Date[3];
	        	notificationTimes[0] = new Date(intervalHours.get(Settings.SETTING_TESCE).getTime() - NOTIFICATION_ALARM_MINUTES);
        		notificationTimes[1] = new Date(12*60*60*1000 - NOTIFICATION_ALARM_MINUTES);
        		notificationTimes[2] = new Date(intervalHours.get(Settings.SETTING_VECERJA).getTime() - NOTIFICATION_ALARM_MINUTES);
		        notificationIndex = new int[]{0,1,2};
	    		break;
	        case 4: case 5: 
	    		notificationTimes = new Date[3];
	        	notificationTimes[0] = new Date(intervalHours.get(Settings.SETTING_ZAJTRK).getTime() - NOTIFICATION_ALARM_MINUTES);
        		notificationTimes[1] = new Date(intervalHours.get(Settings.SETTING_KOSILO).getTime() - NOTIFICATION_ALARM_MINUTES);
        		notificationTimes[2] = new Date(intervalHours.get(Settings.SETTING_VECERJA).getTime() - NOTIFICATION_ALARM_MINUTES);
		        notificationIndex = new int[]{0,1,2};
	    		break;
	        case 6: 
	    		notificationTimes = new Date[4];
	        	notificationTimes[0] = new Date(intervalHours.get(Settings.SETTING_ZAJTRK).getTime() - NOTIFICATION_ALARM_MINUTES);
        		notificationTimes[1] = new Date(intervalHours.get(Settings.SETTING_KOSILO).getTime() - NOTIFICATION_ALARM_MINUTES);
        		notificationTimes[2] = new Date(intervalHours.get(Settings.SETTING_VECERJA).getTime() - NOTIFICATION_ALARM_MINUTES);
		        notificationTimes[3] = new Date(intervalHours.get(Settings.SETTING_SPANJE).getTime() - NOTIFICATION_ALARM_MINUTES);
		        notificationIndex = new int[]{0,1,2,3};
	    		break;
	        case 7: 
	    		notificationTimes = new Date[3];
	        	notificationTimes[0] = new Date(intervalHours.get(Settings.SETTING_ZAJTRK).getTime() - NOTIFICATION_ALARM_MINUTES);
        		notificationTimes[1] = new Date(intervalHours.get(Settings.SETTING_KOSILO).getTime() - NOTIFICATION_ALARM_MINUTES);
        		notificationTimes[2] = new Date(intervalHours.get(Settings.SETTING_VECERJA).getTime() - NOTIFICATION_ALARM_MINUTES);
		        notificationIndex = new int[]{0,1,1};
		        break;
	        case 8: 
	        	notificationTimes = new Date[4];
	        	notificationTimes[0] = new Date(intervalHours.get(Settings.SETTING_TESCE).getTime());
	        	notificationTimes[1] = new Date(intervalHours.get(Settings.SETTING_TESCE).getTime() + danDel);
	        	notificationTimes[2] = new Date(intervalHours.get(Settings.SETTING_TESCE).getTime() + (2 * danDel));
	        	notificationTimes[3] = new Date(intervalHours.get(Settings.SETTING_SPANJE).getTime());
		        notificationIndex = new int[]{0,0,0,0};
		        break;
	        case 10: 
	    		notificationTimes = new Date[3];
	        	notificationTimes[0] = new Date(intervalHours.get(Settings.SETTING_ZAJTRK).getTime() - NOTIFICATION_ALARM_MINUTES);
        		notificationTimes[1] = new Date(intervalHours.get(Settings.SETTING_KOSILO).getTime() - NOTIFICATION_ALARM_MINUTES);
        		notificationTimes[2] = new Date(intervalHours.get(Settings.SETTING_VECERJA).getTime() - NOTIFICATION_ALARM_MINUTES);
		        notificationIndex = new int[]{0,0,0};
	    		;
	    }
		HomeScreenActivity.setNextNotification(context);				
	}
	
    public static void setLanguage(Context context, String lang) {
    	System.out.println("LANG="+lang);
        Locale locale = new Locale(lang);
	    Locale.setDefault(locale);
	    Configuration config = new Configuration();
	    config.locale = locale;
	    context.getResources().updateConfiguration(config, null);
	    
	    for (Entry<Integer, String> entry : languages.entrySet()) {
	        if (entry.getValue().equals(lang)) {
	        	Utils.savePrefernciesInt(context, Settings.SETTING_LANG, entry.getKey());
	        }
    	}

    }	
	
	public static void saveHistory (Context context, int ind, long startDate, long endDate) {
		try {
			JSONObject historyEl = new JSONObject();
			historyEl.put(SETTING_INDX, ind);
			historyEl.put(SETTING_START_DATE, startDate);
			historyEl.put(SETTING_END_DATE, endDate);
			history.put(historyEl);
			Utils.savePrefernciesString(context, SETTING_HISTORY, history.toString());
			System.out.println("HISTORY SAVE="+history.toString());
		} catch (Exception e) {
			System.out.println("HISTORY ERROR="+e.getLocalizedMessage());
		}
	}  

}
