package si.renderspace.donatmgmoments;

import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
	public static AlarmManager alarmMgr;
	public static PendingIntent notificationIntent;

	
    @Override
    public void onReceive(Context context, Intent intent) {
    	if (intent.getAction().equals("si.renderspace.donatmgmoments")) {
        	
		   	if (Settings.drinking.size() == 0) {
		   		Settings.prepareData(context);
		   	}
		   	int indx = Utils.getPrefernciesInt(context,  Settings.SETTING_INDX);
	        String[][] drinks = Settings.drinking.get(indx);
			String[] drink = drinks[Settings.notificationIndex[Utils.getPrefernciesInt(context,  Settings.SETTING_PERIOD_CURR)]];
			
			//system notification
			showNotification(context, Utils.getPrefernciesInt(context,  Settings.SETTING_PERIOD_CURR), context.getResources().getString(R.string.app_name), drink[0]+", "+drink[2]+", "+drink[1]+", "+drink[3]);
        }
    } 
    
	private void showNotification(Context context, int period, String title, String text){
		Intent resultIntent = new Intent(context, NotiActivity.class);
		resultIntent.putExtra("PERIOD", period);
		
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.ic_notification)
		        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification_large))
		        .setContentTitle(title)
		        .setContentText(text)
		        .setAutoCancel(true)
		        .setContentIntent(pIntent);
		
		NotificationManager mNotificationManager =  (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(001, mBuilder.build());
	
		setNextNotification(context);
	}
	
	public static void setNextNotification(Context context){
		if (Utils.getPrefernciesInt(context,  Settings.SETTING_INDX)==-1) return;
		
        if (alarmMgr == null) {
        	alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        }
		Intent bi=new Intent(new Intent("si.renderspace.donatmgmoments"));
		notificationIntent = PendingIntent.getBroadcast( context, 0, bi, 0 );
		
		Calendar calendar = Calendar.getInstance();
		long minNotificationInDay = -1;
		long minNotificationTime = -1;
		boolean alarmSet = false;

		//preverim vse notifikatione za izbrano kuro
		Date[] notificationTimes = Settings.notificationTimes;
		if (notificationTimes != null) {
			for(int i=0; i<notificationTimes.length; i++) {
				Calendar calendarNotification = Calendar.getInstance();
				calendarNotification.setTime(notificationTimes[i]); 
				if ((minNotificationTime == -1) || (minNotificationTime > calendarNotification.getTimeInMillis())) {
					minNotificationTime = calendarNotification.getTimeInMillis();
				}
				
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				int minute = calendar.get(Calendar.MINUTE); 
				long calendar_curr = hour*60*60*1000+minute*60*1000;
			
				//ce je ura notifikationa za trenutno uro je lahko ura alarma
				if (calendarNotification.getTimeInMillis() > calendar_curr) {
					Calendar calendarNewNotification = Calendar.getInstance();
					calendarNewNotification.set(Calendar.HOUR_OF_DAY, 0);
					calendarNewNotification.set(Calendar.MINUTE, 0);
					calendarNewNotification.set(Calendar.SECOND, 0);
					long newNotification = calendarNewNotification.getTimeInMillis() + calendarNotification.getTimeInMillis();
					
					//ce je alarm prej kot nastavljen alarm nastavim tistega prej
					if ((minNotificationInDay == -1) || (minNotificationInDay > newNotification)) {
						Utils.savePrefernciesInt(context,  Settings.SETTING_PERIOD_CURR, i);
						alarmSet = true;
						minNotificationInDay = newNotification;
						Calendar c = Calendar.getInstance();
						//za elapsed time zracunam razliko med notijem in trenutnim casom
						long newAlarm = newNotification - c.getTimeInMillis();
						c.setTimeInMillis(newAlarm);
						//alarmMgr.set(AlarmManager.RTC_WAKEUP,  newNotification, notificationIntent);
						alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + newAlarm, notificationIntent);
					}
				}
			}
		}
		
		//ce ni nsatvljen alarm nastavim prvi alarm v naslednjem dnevu
		if (!alarmSet) {
			Utils.savePrefernciesInt(context,  Settings.SETTING_PERIOD_CURR, 0);
			Calendar calendarNewNotification = Calendar.getInstance();
			calendarNewNotification.add(Calendar.DATE, 1);
			calendarNewNotification.set(Calendar.HOUR_OF_DAY, 0);
			calendarNewNotification.set(Calendar.MINUTE, 0);
			calendarNewNotification.set(Calendar.SECOND, 0);
			long newNotification = calendarNewNotification.getTimeInMillis() + minNotificationTime;
			Calendar c = Calendar.getInstance();
			long newAlarm = newNotification - c.getTimeInMillis();
			c.setTimeInMillis(newAlarm);
			alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,  SystemClock.elapsedRealtime() + newAlarm, notificationIntent); 			
		}
	}
	
}