package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.specific_experiment.ViewTrial;

import java.util.ArrayList;

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
        TextView outcome = view.findViewById(R.id.trial_result);

        trial_ID.setText(viewTrial.getID());
        outcome.setText(viewTrial.getOutcome());

        return view;
    }
}