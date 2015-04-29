package angel.zhuoxiu.color;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class ColorPickerPreference extends CheckBoxPreference {
	public interface OnColorPickedListener {
		public void onColorPicked(int color);
	}

	String tag = this.getClass().getSimpleName();
	AlertDialog dialog = null;
	View colorView;
	int color = Color.TRANSPARENT;
	OnColorPickedListener listener;

	int[] colors = { Color.RED, Color.BLACK, Color.CYAN, Color.BLUE, Color.DKGRAY, Color.GREEN, Color.MAGENTA, Color.YELLOW };

	public ColorPickerPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWidgetLayoutResource(R.layout.colorpicker);
	}

	public void setOnColorPickedListener(OnColorPickedListener listener) {
		this.listener = listener; 
	}

	@Override
	protected void onClick() {
		Log.i(tag, "onClickonClick");
		TextView tv = new TextView(getContext());
		tv.setText("12324566");
		// PopupWindow window = new PopupWindow(tv);
		// window.showAsDropDown(colorView);

		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setCancelable(true);
		ColorListView colorListView = new ColorListView(getContext());
		colorListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int color = (Integer) parent.getItemAtPosition(position);
				setColor(color);
				if (listener != null) {
					listener.onColorPicked(color);
				}
				dialog.dismiss();
			}
		});
		builder.setView(colorListView);
		dialog = builder.create();
		dialog.show();
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		colorView = view.findViewById(R.id.color_view);
		colorView.setBackgroundColor(color);
	}

	public void setColor(int color) {
		this.color = color;
		if (colorView != null) {
			colorView.setBackgroundColor(color);
		}
	}

	public int getColor() {
		return color;
	}

	public void setColorList(int... colorList) {
		colors = colorList;
	}

	class ColorListView extends ListView {

		public ColorListView(Context context) {
			super(context);
			setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT));
			setAdapter(new BaseAdapter() {
				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
					int color = (Integer) getItem(position);
					if (convertView == null) {
						TextView tv = new TextView(getContext());
						tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
						tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
						LinearLayout ll = new LinearLayout(getContext());
						ll.setPadding(20, 10, 20, 10);
						ll.addView(tv);
						convertView = ll;
						convertView.setTag(tv);
					}
					View v = (View) convertView.getTag();
					v.setBackgroundColor(color);
					return convertView;
				}

				@Override
				public long getItemId(int position) {
					return colors[position];
				}

				@Override
				public Object getItem(int position) {
					return colors[position];
				}

				@Override
				public int getCount() {
					return colors.length;
				}
			});
		}

	}
}
