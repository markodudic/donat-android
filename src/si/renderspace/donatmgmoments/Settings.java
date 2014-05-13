package si.renderspace.donatmgmoments;

import java.util.Date;
import java.util.HashMap;

import android.content.Context;


public class Settings {
	public static HashMap<Integer,String> indications = new HashMap<Integer,String>();
	public static HashMap<Integer,String> indications_desc = new HashMap<Integer,String>();
	public static HashMap<Integer,String[][]> drinking = new HashMap<Integer,String[][]>();
	public static HashMap<Integer,String> interval = new HashMap<Integer,String>();
	
	public static int indicationCurrentIndx = -1; //-1 : ni trenutne indikacije
	public static Date indicationCurrentDate;
	
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
				{context.getResources().getString(R.string.drinking_na_tesce),context.getResources().getString(R.string.temperature_toplo)+" 3-8 "+context.getResources().getString(R.string.temperature_suffix),context.getResources().getString(R.string.spped_hitro)},
				{context.getResources().getString(R.string.drinking_pred_spanjem),context.getResources().getString(R.string.temperature_mlacno)+" 2 "+context.getResources().getString(R.string.temperature_suffix),context.getResources().getString(R.string.spped_razmeroma_hitro)}
		};
		drinking.put(1,drinking_1);

		String[][] drinking_2 = new String[][]{	
				{context.getResources().getString(R.string.drinking_veckrat_dnevno),context.getResources().getString(R.string.temperature_sobna)+" 1 "+context.getResources().getString(R.string.temperature_suffix),context.getResources().getString(R.string.spped_pocasi)},
				{context.getResources().getString(R.string.drinking_20_min_pred),context.getResources().getString(R.string.temperature_sobna)+" 1 "+context.getResources().getString(R.string.temperature_suffix),context.getResources().getString(R.string.spped_pocasi)},
				{context.getResources().getString(R.string.drinking_med_obroki),context.getResources().getString(R.string.temperature_sobna)+" 1 "+context.getResources().getString(R.string.temperature_suffix),context.getResources().getString(R.string.spped_pocasi)}
		};
		drinking.put(2,drinking_2);

		String[][] drinking_3 = new String[][]{	
				{context.getResources().getString(R.string.drinking_na_tesce),context.getResources().getString(R.string.temperature_hladno)+" 2 "+context.getResources().getString(R.string.temperature_suffix),context.getResources().getString(R.string.spped_pocasi)},
				{context.getResources().getString(R.string.drinking_opoldne),context.getResources().getString(R.string.temperature_hladno)+" 1 "+context.getResources().getString(R.string.temperature_suffix),context.getResources().getString(R.string.spped_pocasi)},
				{context.getResources().getString(R.string.drinking_zvecer),context.getResources().getString(R.string.temperature_hladno)+" 1 "+context.getResources().getString(R.string.temperature_suffix),context.getResources().getString(R.string.spped_pocasi)}
		};
		drinking.put(3,drinking_3);

		String[][] drinking_4 = new String[][]{	
				{context.getResources().getString(R.string.drinking_na_tesce),context.getResources().getString(R.string.temperature_toplo)+" 3 "+context.getResources().getString(R.string.temperature_suffix),context.getResources().getString(R.string.spped_razmeroma_hitro)},
				{context.getResources().getString(R.string.drinking_pred_kosilom),context.getResources().getString(R.string.temperature_hladno)+" 1 "+context.getResources().getString(R.string.temperature_suffix),context.getResources().getString(R.string.spped_pocasi)},
				{context.getResources().getString(R.string.drinking_pred_vecerjo),context.getResources().getString(R.string.temperature_hladno)+" 1 "+context.getResources().getString(R.string.temperature_suffix),context.getResources().getString(R.string.spped_pocasi)}
		};
		drinking.put(4,drinking_4);

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
		
	}
}
