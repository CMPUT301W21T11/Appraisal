package com.example.appraisal.UI.main_menu.specific_experiment_details.details.trial_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.specific_experiment.ViewTrial;
import com.example.appraisal.backend.trial.TrialType;
import com.example.appraisal.model.core.MainModel;

import java.util.ArrayList;


/**
 * Adapter to display the trials
 */
public class ViewTrialAdapter extends ArrayAdapter<ViewTrial> {
    private ArrayList<ViewTrial> trials;
    private Context context;
    private  Experiment experiment;

    public ViewTrialAdapter(Context context, ArrayList<ViewTrial> trials) {
        super(context, 0, trials);
        this.trials = trials;
        this.context = context;
    }

    /**
     * This method returns the view for viewing the trial details
     *
     * @param position -- the position of the view card
     * @param convertView -- the view to be modified
     * @param parent -- parent ViewGroup
     * @return View -- the view to be displayed
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.view_trial_layout, parent, false);
        }

        ViewTrial viewTrial = trials.get(position);

        TextView trial_ID = view.findViewById(R.id.trial_ID);
        // TODO: display success and failure
        TextView result = view.findViewById(R.id.trial_result);
        TextView date = view.findViewById(R.id.view_trial_date);

        trial_ID.setText(viewTrial.getID());

        try {
           experiment = MainModel.getCurrentExperiment();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (experiment.getType().equals(TrialType.BINOMIAL_TRIAL.getLabel())) {
            if (Double.parseDouble(viewTrial.getOutcome()) == 0.0) {
                result.setText("Failure");
            }
            else {
                result.setText("Success");
            }
        }
        else {
            result.setText(viewTrial.getOutcome());
        }


        date.setText(viewTrial.getDate());

        return view;
    }
}