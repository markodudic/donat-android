package si.renderspace.donatmgmoments;

import hirondelle.date4j.DateTime;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

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

		TextView cellView = (TextView) cellLayout.getChildAt(0);
		ImageView cellImage = (ImageView) cellLayout.getChildAt(1);

		cellView.setTextColor(Color.BLACK);

		// Get dateTime of this cell
		DateTime dateTime = this.datetimeList.get(position);

		// Set color of the dates in previous / next month
		if (dateTime.getMonth() != month) {
			cellView.setTextColor(resources
					.getColor(R.color.caldroid_darker_gray));
		}

		boolean shouldResetDiabledView = false;
		boolean shouldResetSelectedView = false;

		// Customize for disabled dates and date outside min/max dates
		if ((minDateTime != null && dateTime.lt(minDateTime))
				|| (maxDateTime != null && dateTime.gt(maxDateTime))
				|| (disableDates != null && disableDatesMap.containsKey(dateTime))) {

			cellView.setTextColor(CaldroidFragment.disabledTextColor);
			if (CaldroidFragment.disabledBackgroundDrawable == -1) {
				cellView.setBackgroundResource(R.drawable.disable_cell);
				cellLayout.setBackgroundResource(R.drawable.disable_cell);
				cellImage.setImageResource(R.drawable.ic_cal_indicator_past);
			} else {
				cellView.setBackgroundResource(CaldroidFragment.disabledBackgroundDrawable);
				cellLayout.setBackgroundResource(CaldroidFragment.disabledBackgroundDrawable);
				cellImage.setImageResource(R.drawable.ic_cal_indicator_past);
			}

			if (dateTime.equals(getToday())) {
				//cellView.setBackgroundResource(R.drawable.red_border_gray_bg);
				cellLayout.setBackgroundResource(R.drawable.red_border_gray_bg);
				cellImage.setImageResource(R.drawable.ic_cal_indicator_curr);
			}
		} else {
			shouldResetDiabledView = true;
		}

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
		}

		cellView.setText("" + dateTime.getDay());
		if (dateTime.equals(getToday())) {
			cellImage.setImageResource(R.drawable.ic_cal_indicator_curr);
		}

		// Set custom color if required
		setCustomResources(dateTime, cellView, cellView);

		
		return cellLayout;
	}

}
