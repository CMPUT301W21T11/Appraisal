package com.example.appraisal.UI.geolocation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.my_experiment.ExpStatusFragment;
import com.example.appraisal.backend.experiment.Experiment;

/**
 * This class represents the Geolocation warning dialog
 */
public class GeolocationWarningDialog extends DialogFragment {
    private OnFragmentInteractionListener listener;

    /**
     * This method inflates the warning dialog with the
     * @param savedInstanceState -- Bundle from previous instance state
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_geolocation_warning, null);

        builder.setView(view)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Allow the user to add a trial
                    }
                })
                .setNeutralButton("Reject", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Don't allow the user to add a trial if they don't want to add their trial geolocation
                        getActivity().finish();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof GeolocationWarningDialog.OnFragmentInteractionListener){
            listener = (GeolocationWarningDialog.OnFragmentInteractionListener) context;
        }
        else{
            throw new RuntimeException(context.toString()+" must implement OnFragmentInteractionListener");
        }
    }

    /**
     * This interface ensures the context is a fragment interaction listener
     */
    public interface OnFragmentInteractionListener{
    }

    /**
     * This method creates a new instance of the warning dialog
     * @return GeolocationWarningDialog -- this
     */
    @NonNull
    public static GeolocationWarningDialog newInstance() {
        return new GeolocationWarningDialog();
    }

}
