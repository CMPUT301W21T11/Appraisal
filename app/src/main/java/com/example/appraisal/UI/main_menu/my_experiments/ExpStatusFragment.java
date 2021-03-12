package com.example.appraisal.UI.main_menu.my_experiments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;

public class ExpStatusFragment extends DialogFragment {
    private TextView description;
    private TextView status;
    private OnFragmentInteractionListener listener;
    private Boolean is_published;
    private Boolean is_ended;

    public interface OnFragmentInteractionListener{
        void onButtonPressed();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        }
        else{
            throw new RuntimeException(context.toString()+" must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.exp_change_status_fragment, null);
        description = view.findViewById(R.id.description);
        status = view.findViewById(R.id.current_status);

        Bundle args = getArguments();
        Experiment experiment = (Experiment) args.getSerializable("experiment");
        is_published = experiment.getIs_published();
        is_ended = experiment.getIs_ended();
        description.setText(experiment.getDescription());

        setStatusField();
        ArrayAdapter<Experiment> adapter = MyExperimentActivity.getAdapter();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setNeutralButton("End", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        experiment.setIs_ended(true);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setPositiveButton("Unpublish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        experiment.setIs_published(false);
                        adapter.notifyDataSetChanged();
                    }
                }).create();

    }

    static ExpStatusFragment newInstance(Experiment experiment) {
        Bundle args = new Bundle();
        args.putSerializable("experiment", experiment);
        ExpStatusFragment fragment = new ExpStatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void setStatusField(){
        if (is_published && !is_ended) {
            status.setText("Published & Open");
        }
        else if (is_published && is_ended) {
            status.setText("Published & Ended");
        }
        else {
            status.setText("Unpublished");
        }
    }
}
