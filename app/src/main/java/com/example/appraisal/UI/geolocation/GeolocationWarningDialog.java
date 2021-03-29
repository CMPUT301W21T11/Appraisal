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

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.my_experiment.ExpStatusFragment;
import com.example.appraisal.backend.experiment.Experiment;

public class GeolocationWarningDialog extends DialogFragment {
    private OnFragmentInteractionListener listener;

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

    public interface OnFragmentInteractionListener{
    }

    public static GeolocationWarningDialog newInstance() {
        GeolocationWarningDialog geolocation_warning = new GeolocationWarningDialog();
        return geolocation_warning;
    }

}
