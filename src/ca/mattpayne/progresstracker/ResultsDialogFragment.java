package ca.mattpayne.progresstracker;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResultsDialogFragment extends DialogFragment {
	
	public static ResultsDialogFragment newInstance(String title, String message) {
		ResultsDialogFragment frag = new ResultsDialogFragment();
        Bundle args = new Bundle();
        args.putString("message", message);
        args.putString("title", title);
        frag.setArguments(args);
        frag.setShowsDialog(true);
        return frag;
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, 0);
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
	{
		String title = getArguments().getString("title");
		getDialog().setTitle(title);
		String message = getArguments().getString("message");
		View v = inflater.inflate(R.layout.dialog, container, false);
		TextView messageArea = (TextView)v.findViewById(R.id.txtDialogMessage);
		messageArea.setText(message);
		return v;
	}
}
