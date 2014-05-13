package si.renderspace.donatmgmoments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class HomeScreenActivity extends Activity {
	
	int j;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		

		 Button btnIndication1 = (Button) findViewById(R.id.btn_indication_1);
		 btnIndication1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_indication_1, 0, 0);
		 btnIndication1.setText(Settings.indications.get(1));
		 btnIndication1.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	showIndication(1);
			    }
			});
		 Button btnIndication2 = (Button) findViewById(R.id.btn_indication_2);
		 btnIndication2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_indication_2, 0, 0);
		 btnIndication2.setText(Settings.indications.get(2));
		 btnIndication2.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	showIndication(2);
			    }
			}); 
		 Button btnIndication3 = (Button) findViewById(R.id.btn_indication_3);
		 btnIndication3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_indication_3, 0, 0);
		 btnIndication3.setText(Settings.indications.get(3));
		 btnIndication3.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	showIndication(3);
			    }
			});
		 Button btnIndication4 = (Button) findViewById(R.id.btn_indication_4);
		 btnIndication4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_indication_4, 0, 0);
		 btnIndication4.setText(Settings.indications.get(4));
		 btnIndication4.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	showIndication(4);
			    }
			});
		 /*Button btnIndication5 = (Button) findViewById(R.id.btn_indication_5);
		 btnIndication5.setText(Settings.indications.get(5));
		 Button btnIndication6 = (Button) findViewById(R.id.btn_indication_6);
		 btnIndication6.setText(Settings.indications.get(6));
		 Button btnIndication7 = (Button) findViewById(R.id.btn_indication_7);
		 btnIndication7.setText(Settings.indications.get(7));
		 Button btnIndication8 = (Button) findViewById(R.id.btn_indication_8);
		 btnIndication8.setText(Settings.indications.get(8));
		 Button btnIndication9 = (Button) findViewById(R.id.btn_indication_9);
		 btnIndication9.setText(Settings.indications.get(9));
		 Button btnIndication10 = (Button) findViewById(R.id.btn_indication_10);
		 btnIndication10.setText(Settings.indications.get(10));*/
		 
		 
	} 

	@Override
	public void onResume() {
	    super.onResume();

	    
		//for (j=1; j<=Settings.indications.size(); j++) {
		for (j=1; j<=4; j++) {
			int id = getResources().getIdentifier("btn_indication_"+j, "id", getPackageName());
			Button btn = (Button) findViewById(id);
			btn.setBackgroundResource(R.drawable.dr_unselected);	
		}
	    if (Settings.indicationCurrentIndx != -1) {
			int id = getResources().getIdentifier("btn_indication_"+Settings.indicationCurrentIndx, "id", getPackageName());
			Button btn = (Button) findViewById(id);
			btn.setBackgroundResource(R.drawable.dr_selected);	
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.home_screen, menu);
		//return true;
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.action_bar, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	public void showIndication(int indx) {
		Intent intent = new Intent(this, IndicationActivity.class);
		intent.putExtra("INDX", indx);
		startActivity(intent);
	}
}
