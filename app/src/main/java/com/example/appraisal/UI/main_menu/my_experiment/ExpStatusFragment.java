package com.example.appraisal.UI.main_menu.my_experiment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.specific_experiment_details.SpecificExpActivity;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.MainModel;
import com.google.firebase.firestore.CollectionReference;

/**
 * This is the class that controls the DialogFragment for the user to modify an experiment's status.
 */
public class ExpStatusFragment extends DialogFragment {
    private TextView description;
    private TextView published_status;
    private TextView ended_status;
    private Button publish_switch;
    private Button view_results;
    private Button end_switch;
    private LinearLayout end_row;
    private OnFragmentInteractionListener listener;
    private Boolean is_published;
    private Boolean is_ended;
    private Experiment experiment;
    private String exp_ID;

    public interface OnFragmentInteractionListener{
    }

    /**
     * This method is called when th DialogFragment is attached to the MyExperimentActivity
     * @param context
     */
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

    /**
     * This method creates the Dialog for the user to view results, end, and unpublish their experiments
     * @param savedInstanceState
     * @return the created dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        // inflate layout from xml and get id's of xml objects
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.exp_change_status_fragment, null);
        description = view.findViewById(R.id.description);
        published_status = view.findViewById(R.id.current_published_status);
        ended_status = view.findViewById(R.id.current_ended_status);
        view_results = view.findViewById(R.id.view_results_button);
        publish_switch = view.findViewById(R.id.publish_button);
        end_switch = view.findViewById(R.id.end_button);
        end_row = view.findViewById(R.id.end_row);

        // get experiment object
        Bundle args = getArguments();
        experiment = (Experiment) args.getParcelable("experiment");
        try {
            MainModel.setCurrentExperiment(experiment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        exp_ID = experiment.getExp_id();
        is_published = experiment.getIs_published();
        is_ended = experiment.getIs_ended();

        // set textView fields
        setFields();
        // create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog dialog = builder
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // if user clicks Done button, update experiment object
                        try {
                            updateStatus();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).create();

        view_results.setOnClickListener(view_results_listener);
        publish_switch.setOnClickListener(publish_listener);
        end_switch.setOnClickListener(end_listener);

        return dialog;
    }

    /**
     * This method creates a newInstance of the fragment
     * @param experiment
     * @return the created instance
     */
    public static ExpStatusFragment newInstance(Experiment experiment) {
        Bundle args = new Bundle();
        args.putParcelable("experiment", experiment);
        ExpStatusFragment fragment = new ExpStatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * This method sets the TextView Fields in the UI
     */
    private void setFields(){
        description.setText(experiment.getDescription());   // set description

        if (is_published && !is_ended) {                // if published and open
            published_status.setText("Published");
            ended_status.setText("Open");
            publish_switch.setText("Unpublish");
            end_switch.setText("End");
        }
        else if (is_published && is_ended) {            // if published and ended
            published_status.setText("Published");
            ended_status.setText("Ended");
            publish_switch.setText("Unpublish");
            end_switch.setText("Open");
        }
        else {                                          // if unpublished
            published_status.setText("Unpublished");
            ended_status.setText("");
            publish_switch.setText("Publish");
            end_row.setVisibility(View.INVISIBLE);
            end_switch.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * This is called when the user clicks on the view results button
     */
    View.OnClickListener view_results_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goViewResults();
        }
    };

    /**
     * This checks if user clicks the Publish Button
     */
    View.OnClickListener publish_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeIfPublished();
        }
    };

    /**
     * This hecks if user clicks the End Button
     */
    View.OnClickListener end_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeIfEnded();
        }
    };


    /**
     * This method goes to the specific activity to display the contents of the experiment selected
     */
    public void goViewResults(){
        // TODO: when VIEW RESULTS Button on dialog is clicked

        Intent intent = new Intent((MyExperimentActivity)getActivity(), SpecificExpActivity.class);
        try {
            MainModel.setCurrentExperiment(experiment);
        } catch (Exception e) {
            e.printStackTrace();
        }
//         Intent intent = new Intent(getActivity(), SpecificExpActivity.class);
//         Bundle bundle = new Bundle();
//         bundle.putParcelable("experiments", experiment);
//         intent.putExtras(bundle);

        startActivity(intent);
    }

    /**
     * This method changed the publish status
     */
    public void changeIfPublished(){
        if (is_published){                              // if it's published, switch to Unpublished
            published_status.setText("Unpublished");
            ended_status.setText("");
            publish_switch.setText("Publish");
            end_switch.setVisibility(View.INVISIBLE);
            end_row.setVisibility(View.INVISIBLE);
            is_published = false;
            is_ended = true;
        }
        else {                                          // else it's unpublished, switch to Published and Open
            published_status.setText("Published");
            ended_status.setText("Open");
            publish_switch.setText("Unpublish");
            end_switch.setVisibility(View.VISIBLE);
            end_row.setVisibility(View.VISIBLE);

            end_switch.setText("End");
            is_published = true;
            is_ended = false;
        }
    }

    /**
     * This method changed ended status
     */
    public void changeIfEnded(){
        if (is_published){
            if (is_ended){                              // if published and ended, switch to published and open
                ended_status.setText("Open");
                end_switch.setText("End");
                is_ended = false;
            }
            else {                                      // else switch to published and ended
                ended_status.setText("Ended");
                end_switch.setText("Open");
                is_ended = true;
            }
        }

    }

    /**
     * This method updates the experiment's status in the database.
     */
    private void updateStatus() throws Exception {
        // get reference to Experiment Collection, update with exp_ID
        CollectionReference reference = MainModel.getExperimentReference();
        reference.document(exp_ID).update("isPublished", is_published);
        reference.document(exp_ID).update("isEnded", is_ended);
    }

}
