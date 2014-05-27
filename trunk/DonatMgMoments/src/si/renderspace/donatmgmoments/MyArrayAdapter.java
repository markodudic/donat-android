package si.renderspace.donatmgmoments;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class MyArrayAdapter extends ArrayAdapter {
	public MyArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}
	
	public TextView getView(int position, View convertView, ViewGroup parent) {
		TextView v = (TextView) super.getView(position, convertView, parent);
		Typeface myFont = Typeface.createFromAsset(MyArrayAdapter.this.getContext().getAssets(), "fonts/Roboto-Light.ttf");
		v.setTypeface(myFont);
		return v;
	}
	
	public TextView getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView v = (TextView) super.getView(position, convertView, parent);
		Typeface myFont = Typeface.createFromAsset(MyArrayAdapter.this.getContext().getAssets(), "fonts/Roboto-Light.ttf");
		v.setTypeface(myFont);
		return v;
	}

}
