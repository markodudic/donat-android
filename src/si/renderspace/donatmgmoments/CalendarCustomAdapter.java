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

			/*if (CaldroidFragment.disabledBackgroundDrawable == -1) {
				cellView.setBackgroundResource(R.drawable.disable_cell);
				cellLayout.setBackgroundResource(R.drawable.disable_cell);
			} else {
				cellView.setBackgroundResource(CaldroidFragment.disabledBackgroundDrawable);
				cellLayout.setBackgroundResource(CaldroidFragment.disabledBackgroundDrawable);
			}*/
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
				cellImage.setImageResource(R.drawable.ic_cal_indicator_curr);
			}
		} else {
			shouldResetDiabledView = true;
		}
/*
		// Customize for selected dates
		if (selectedDates != null && selectedDatesMap.containsKey(dateTime)) {
			if (CaldroidFragment.selectedBackgroundDrawable != -1) {
				cellView.setBackgroundResource(CaldroidFragment.selectedBackgroundDrawable);
				cellLayout.setBackgroundResource(CaldroidFragment.selectedBackgroundDrawable);
				cellImage.setImageResource(R.drawable.ic_cal_indicator_future);
			} else {
				cellView.setBackgroundColor(resources.getColor(R.color.caldroid_sky_blue));
				cellLayout.setBackgroundColor(resources.getColor(R.color.caldroid_sky_blue));
				cellImage.setImageResource(R.drawable.ic_cal_indicator_future);
			}

			cellView.setTextColor(CaldroidFragment.selectedTextColor);
		} else {
			shouldResetSelectedView = true;
		}
*/
		/*
		if (shouldResetDiabledView && shouldResetSelectedView) {
			// Customize for today
			if (dateTime.equals(getToday())) {
				//cellView.setBackgroundResource(R.drawable.red_border);
				cellLayout.setBackgroundResource(R.drawable.red_border);
				cellImage.setImageResource(R.drawable.ic_cal_indicator_curr);
			} else {
				cellView.setBackgroundResource(R.drawable.cell_bg);
				cellLayout.setBackgroundResource(R.drawable.cell_bg);
			}
		}*/

		if (dateTime.equals(getToday())) {
			if ((minDateTime != null && dateTime.lt(minDateTime))
					|| (maxDateTime != null && dateTime.gt(maxDateTime))
					|| (disableDates != null && disableDatesMap.containsKey(dateTime))) {
				cellImage.setImageResource(R.drawable.ic_cal_indicator_curr);
			}
		}
		
		cellView.setText("" + dateTime.getDay());

		// Set custom color if required
		//setCustomResources(dateTime, cellView, cellView);
		
		
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int height = parent.getMeasuredHeight();
		//System.out.println("HHHHHHHH="+parent.getHeight()+":"+parent.getMeasuredHeight());

		//System.out.println("calHeight="+height);
		int rH = (int)(((height-3)/6) - 0.5f);
		//System.out.println("rowHeight2="+rH);
		//float p = metrics.density * 7.5f;
		//int cH = (int)(rH - p);
		//System.out.println("cellHeight2="+cH);

		/*
		float dp = 57f;
		float fpixels = metrics.density * dp;
		int pixels = (int) (fpixels + 0.5f);
		*/
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,rH/4);
	    lp.gravity= Gravity.RIGHT; 
	    cellImage.setLayoutParams(lp);
		cellView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,rH));
		cellLayout.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,rH));
		parent.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		
		return cellLayout;
	}

}
