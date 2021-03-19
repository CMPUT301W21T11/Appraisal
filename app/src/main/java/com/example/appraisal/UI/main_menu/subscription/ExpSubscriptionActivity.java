package com.example.appraisal.UI.main_menu.subscription;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.MainMenuCommonActivity;
import com.example.appraisal.UI.main_menu.my_experiment.ExpStatusFragment;
import com.example.appraisal.UI.main_menu.my_experiment.ExpAdapter;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.MainModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class ExpSubscriptionActivity extends MainMenuCommonActivity implements ExpStatusFragment.OnFragmentInteractionListener {

    private ListView subscribed_list;
    private ArrayList<String> user_subscriptions;
    private ArrayList<Experiment> subscribed_experiments;
    private ArrayAdapter<Experiment> adapter;
    private DocumentReference user_ref;
    private CollectionReference exp_ref;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        MainModel.checkUserStatus();

        subscribed_list = findViewById(R.id.subscribedList);
        subscribed_experiments = new ArrayList<>();
        adapter = new ExpAdapter(this, subscribed_experiments);

        getSubscribedExperiments();

        subscribed_list.setOnItemClickListener(selectExListener);
        subscribed_list.setAdapter(adapter);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_subscription);

        subscribed_list = findViewById(R.id.subscribedList);
        subscribed_experiments = new ArrayList<>();
        adapter = new ExpAdapter(this, subscribed_experiments);

        getSubscribedExperiments();

        subscribed_list.setOnItemClickListener(selectExListener);
        subscribed_list.setAdapter(adapter);
    }


    /**
     * This method gets the item in list the user clicks on, and opens up a dialog with the corresponding info.
     */
    private final AdapterView.OnItemClickListener selectExListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Experiment experiment = subscribed_experiments.get(position);
            try {
                MainModel.setCurrentExperiment(experiment);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ExpStatusFragment fragment = ExpStatusFragment.newInstance(experiment);
            fragment.show(getSupportFragmentManager(), "Edit Experiment Status");

        }
    };


    public void getSubscribedExperiments() {

        try {
            user_ref = MainModel.getUserReference();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            exp_ref = MainModel.getExperimentReference();
        } catch (Exception e) {
            e.printStackTrace();
        }

        user_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        user_subscriptions = (ArrayList<String>) document.get("my_subscriptions");

                        if (user_subscriptions != null) {
                            for (String subscription : user_subscriptions) {

                                exp_ref.document(subscription).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot doc, @Nullable FirebaseFirestoreException error) {
                                        if (doc != null && doc.exists()) {
                                            Log.d("Current data: ", doc.getData().toString());

                                            String owner_ID = (String) doc.getData().get("owner");
                                            String exp_ID = doc.getId();
                                            String description = (String) doc.getData().get("description");
                                            String type = (String) doc.getData().get("type");
                                            Boolean geo_required = (Boolean) doc.getData().get("isGeolocationRequired");
                                            Integer min_trials = Integer.valueOf(doc.getData().get("minTrials").toString());
                                            String rules = (String) doc.getData().get("rules");
                                            String region = (String) doc.getData().get("region");
                                            Boolean is_ended = (Boolean) doc.getData().get("isEnded");
                                            Boolean is_published = (Boolean) doc.getData().get("isPublished");

                                            Experiment experiment = new Experiment(exp_ID, owner_ID, description, type, geo_required, min_trials, rules, region);

                                            experiment.setIsEnded(is_ended);
                                            experiment.setIsPublished(is_published);

                                            subscribed_experiments.add(experiment);

                                            Log.d("Subscribed Experiments:", subscribed_experiments.toString());

                                            adapter.notifyDataSetChanged();

                                            subscribed_list.setAdapter(adapter);
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });
    }

}
