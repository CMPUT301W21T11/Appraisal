package com.example.appraisal.backend.experiment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appraisal.R;
import java.util.ArrayList;

public class ExpAdapter extends ArrayAdapter<Experiment> {
    private ArrayList<Experiment> experiments;
    private Context context;

    public ExpAdapter(Context context, ArrayList<Experiment> experiments){
        super(context, 0, experiments);
        this.experiments = experiments;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.exp_layout, parent, false);
        }

        Experiment exp_current = experiments.get(position);

        TextView description = view.findViewById(R.id.exp_desc);
        TextView type = view.findViewById(R.id.exp_type);
        TextView owner = view.findViewById(R.id.owner_name);
        TextView status = view.findViewById(R.id.exp_status);

        description.setText(exp_current.getDescription());
        type.setText(exp_current.getType());
        owner.setText(exp_current.getOwner());

        if (exp_current.getIs_published()) {
            status.setText("Published");
        }
        else {
            status.setText("Unpublished");
        }

        return view;
    }
}
