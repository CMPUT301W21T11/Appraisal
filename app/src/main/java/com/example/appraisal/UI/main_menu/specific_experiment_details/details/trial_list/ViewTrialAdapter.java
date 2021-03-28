package com.example.appraisal.UI.main_menu.specific_experiment_details.details.trial_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appraisal.R;
import com.example.appraisal.backend.specific_experiment.ViewTrial;

import java.util.ArrayList;


/**
 * Adapter to display the trials
 */
public class ViewTrialAdapter extends ArrayAdapter<ViewTrial> {
    private ArrayList<ViewTrial> trials;
    private Context context;

    public ViewTrialAdapter(Context context, ArrayList<ViewTrial> trials) {
        super(context, 0, trials);
        this.trials = trials;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.view_trial_layout, parent, false);
        }

        ViewTrial viewTrial = trials.get(position);

        TextView trial_ID = view.findViewById(R.id.trial_ID);
        TextView result = view.findViewById(R.id.trial_result);
        TextView date = view.findViewById(R.id.view_trial_date);

        trial_ID.setText(viewTrial.getID());
        result.setText(viewTrial.getOutcome());
        date.setText(viewTrial.getDate());

        return view;
    }
}