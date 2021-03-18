package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.Experimenter;

import java.util.ArrayList;

public class SpecificExpContributorsAdapter extends ArrayAdapter<Experimenter> {
    private ArrayList<Experimenter> experimenters;
    private Context context;

    public SpecificExpContributorsAdapter(Context context, ArrayList<Experimenter> experimenters) {
        super(context, 0, experimenters);
        this.experimenters = experimenters;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_specific_exp_contributors_view_holder, parent, false);
        }

        Experimenter current_experimenter = experimenters.get(position);

//        TextView user_name = view.findViewById(R.id.user_name);
//        TextView num_of_trials = view.findViewById(R.id.num_of_trials);

//        user_name.setText(current_experimenter.getId());
//        num_of_trials.setText(Integer.toString(current_experimenter.getTrial_list().size()));

        return view;
    }
}
