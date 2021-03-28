package com.example.appraisal.UI.main_menu.my_experiment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;

import java.util.ArrayList;

public class ExpAdapter extends ArrayAdapter<Experiment> implements Filterable {
    private ArrayList<Experiment> experiments;
    private ArrayList<Experiment> experimentsFiltered;
    private Context context;
    private String activity_from;

    public ExpAdapter(Context context, ArrayList<Experiment> experiments, String activity_from) {
        super(context, 0, experiments);
        this.experiments = experiments;
        this.experimentsFiltered = experiments;
        this.context = context;
        this.activity_from = activity_from;
    }

    /**
     * Get the number of experiments are in the data set represented by this adapter
     */
    @Override
    public int getCount() {
        return experimentsFiltered == null ? 0 : experimentsFiltered.size();
    }

    /**
     * Get the experiment associated with the specified position in the data set
     */
    @Override
    public Experiment getItem(int position) {
        return experimentsFiltered.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.exp_layout, parent, false);
        }

        Experiment exp_current = experimentsFiltered.get(position);

        TextView description = view.findViewById(R.id.exp_desc);
        TextView type = view.findViewById(R.id.exp_type);
        TextView status = view.findViewById(R.id.exp_status);

        description.setText(exp_current.getDescription());
        type.setText(exp_current.getType());

        // show owner if not from MyExperiment Activity
        if (activity_from.equals("MyExperiment")) {
            LinearLayout owner_row = view.findViewById(R.id.owner_row);
            owner_row.setVisibility(View.GONE);
        } else {
            TextView owner_name = view.findViewById(R.id.owner_name);
            owner_name.setText(exp_current.getOwner().substring(0, 7));
        }

        // show status of experiment
        if (exp_current.getIsPublished() && !exp_current.getIsEnded()) {
            status.setText("Open");
        } else if (exp_current.getIsPublished() && exp_current.getIsEnded()) {
            status.setText("Ended");
        } else {
            status.setText("Unpublished");
        }


        return view;
    }


    /**
     * This method filters the list with the text specified by the user
     *
     * @return
     */
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();

                // show the normal list if no search input is present
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = experiments.size();
                    filterResults.values = experiments;

                } else {
                    ArrayList<Experiment> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (Experiment exp : experiments) {
                        // check all searchable fields
                        boolean checkDesc = exp.getDescription().toLowerCase().contains(searchStr);
                        boolean checkOwner = exp.getOwner().toLowerCase().contains(searchStr);
                        boolean checkType = exp.getType().toLowerCase().contains(searchStr);
                        boolean checkStatus = checkStat(exp).toLowerCase().contains(searchStr);

                        // if searched text is any of the fields, show them
                        if (checkDesc || checkOwner || checkType || checkStatus) {
                            resultsModel.add(exp);
                            filterResults.count = resultsModel.size();
                            filterResults.values = resultsModel;
                        }
                    }
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {

                experimentsFiltered = (ArrayList<Experiment>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    /**
     * This method checks the status of the experiment and returns corresponding string
     *
     * @param exp
     * @return
     */
    private String checkStat(Experiment exp) {
        if (exp.getIsEnded()) {
            return "Ended";
        } else {
            return "Open";
        }
    }

}
