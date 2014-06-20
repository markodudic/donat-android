package si.renderspace.donatmgmoments;

import hirondelle.date4j.DateTime;

import java.util.HashMap;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout.LayoutParams;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;
import com.roomorama.caldroid.SquareTextView;

public class CalendarCustomAdapter extends CaldroidGridAdapter {

	public CalendarCustomAdapter(Context context, int month, int year,
			HashMap<String, Object> caldroidData,
			HashMap<String, Object> extraData) {
		super(context, month, year, caldroidData, extraData);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		FrameLayout cellLayout = (FrameLayout) convertView;

		// For reuse
		if (convertView == null) {
			cellLayout = (FrameLayout) inflater.inflate(R.layout.calendar_cell, null);
		}

		SquareTextView cellView = (SquareTextView) cellLayout.getChildAt(0);
		ImageView cellImage = (ImageView) cellLayout.getChildAt(1);

		// Get dateTime of this cell
		DateTime dateTime = this.datetimeList.get(position);

		
		// Set color of the dates in previous / next month
		if (dateTime.getMonth() != month) {
			cellView.setTextColor(resources.getColor(R.color.text_green_40));
		}

		boolean shouldResetDiabledView = false;
		boolean shouldResetSelectedView = false;

		// Customize for disabled dates and date outside min/max dates
		if ((minDateTime != null && dateTime.lt(minDateTime))
				|| (maxDateTime != null && dateTime.gt(maxDateTime))
				|| (disableDates != null && disableDatesMap.containsKey(dateTime))) {


			cellView.setBackgroundColor(resources.getColor(R.color.background_green_10));
			cellLayout.setBackgroundColor(resources.getColor(R.color.background_green_10));
			if (dateTime.lt(getToday())) {
				cellView.setTextColor(CaldroidFragment.disabledTextColor);
				cellImage.setImageResource(R.drawable.ic_cal_indicator_past);
			} else {
				cellView.setTextColor(CaldroidFragment.selectedTextColor);
				cellImage.setImageResource(R.drawable.ic_cal_indicator_future);				
			}
				
			if (dateTime.equals(getToday())) {
				//cellView.setBackgroundResource(R.drawable.red_border_gray_bg);
				cellLayout.setBackgroundResource(R.drawable.red_border_gray_bg);
				//cellImage.setImageResource(R.drawable.ic_cal_indicator_curr);
			}
		} else {
			shouldResetDiabledView = true;
		}

		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		float dp = 16f;
		int p = (int) (metrics.density * dp);
		if (dateTime.equals(getToday())) {
			if ((minDateTime != null && dateTime.lt(minDateTime))
					|| (maxDateTime != null && dateTime.gt(maxDateTime))
					|| (disableDates != null && disableDatesMap.containsKey(dateTime))) {
				if (Utils.getPrefernciesInt(context, Settings.SETTING_INDX) != -1) {
					cellImage.setImageResource(R.drawable.ic_cal_indicator_curr);
					float dp_curr = 20f;
					p = (int) (metrics.density * dp_curr);
				} else {
					cellView.setTextColor(CaldroidFragment.disabledTextColor);
					cellImage.setImageResource(R.drawable.ic_cal_indicator_past);
				}
			}
		}
		
		cellView.setText("" + dateTime.getDay());

		// Set custom color if required
		//setCustomResources(dateTime, cellView, cellView);
		
		int height = parent.getMeasuredHeight();
		int rH = (int)(((height-3)/6) - 1f);
		
		//FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,rH/4);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,p);
	    lp.gravity= Gravity.RIGHT; 
	    cellImage.setLayoutParams(lp);
		cellView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,rH));
		cellLayout.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,rH));
		parent.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		
		return cellLayout;
		
	}

}
