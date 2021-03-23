package com.example.appraisal.UI.main_menu.my_experiment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.search.SearchActivity;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.MainModel;

import java.util.ArrayList;

public class ExpAdapter extends ArrayAdapter<Experiment> implements Filterable {
    private ArrayList<Experiment> experiments;
    private ArrayList<Experiment> experimentsFiltered;
    private Context context;
    private String activity_from;

    public ExpAdapter(Context context, ArrayList<Experiment> experiments, String activity_from){
        super(context, 0, experiments);
        this.experiments = experiments;
        this.experimentsFiltered = experiments;
        this.context = context;
        this.activity_from = activity_from;
    }

    @Override
    public int getCount() {
        return experimentsFiltered == null ? 0 : experimentsFiltered.size();
    }

    @Override
    public Experiment getItem(int position) {
        return experimentsFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;

        if (view == null){
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
        }
        else {
            TextView owner_name = view.findViewById(R.id.owner_name);
            owner_name.setText(exp_current.getOwner().substring(0, 7));
        }

        // show status of experiment
        if (exp_current.getIsPublished() && !exp_current.getIsEnded()) {
            status.setText("Open");
        }
        else if (exp_current.getIsPublished() && exp_current.getIsEnded()) {
            status.setText("Ended");
        }
        else {
            status.setText("Unpublished");
        }


        return view;
    }


//    public void setExperiments(ArrayList<Experiment> experiments_list){
//        this.experiments = experiments_list;
//    }

    /**
     * This method filters the list with the text specified by the user
     * @return
     */
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
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
                Log.d("Size after filtering:", String.valueOf(experimentsFiltered.size()));
                notifyDataSetChanged();
//                clear();
//                int count = experimentsFiltered == null ? 0 : experimentsFiltered.size();
//
//                for(int i = 0; i<count; i++){
//                    add(experimentsFiltered.get(i));
//                    notifyDataSetInvalidated();
//                }
            }
        };
        return filter;
    }

    /**
     * This method checks the status of the experiment and returns corresponding string
     * @param exp
     * @return
     */
    private String checkStat(Experiment exp) {
        if (exp.getIsEnded()){
            return "Ended";
        }
        else {
            return "Open";
        }
    }

}
