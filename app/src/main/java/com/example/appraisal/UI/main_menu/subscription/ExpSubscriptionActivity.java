package com.example.appraisal.UI.main_menu.subscription;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.MainMenuCommonActivity;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.MainModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ExpSubscriptionActivity extends MainMenuCommonActivity {

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

        subscribed_list = findViewById(R.id.subscribedList);
        subscribed_experiments = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.list_content, subscribed_experiments);


        getSubscribedExperiments();


        subscribed_experiments = MainModel.getSubscribed_experiments();
        Log.d("MainModel subs:", MainModel.getSubscribed_experiments().toString());
        subscribed_list.setAdapter(adapter);

    }


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

                        for(String subscription: user_subscriptions) {

                            exp_ref.document(subscription).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot doc, @Nullable FirebaseFirestoreException error) {
                                    if (doc != null && doc.exists()) {
                                        Log.d("Current data: ", doc.getData().toString());

                                        String owner_ID = (String)doc.getData().get("owner");
                                        String exp_ID = doc.getId();
                                        String description = (String)doc.getData().get("description");
                                        String type = (String)doc.getData().get("type");
                                        Boolean geo_required = (Boolean)doc.getData().get("isGeolocationRequired");
                                        Integer min_trials = Integer.valueOf(doc.getData().get("minTrials").toString());
                                        String rules = (String)doc.getData().get("rules");
                                        String region = (String)doc.getData().get("region");
                                        Boolean is_ended = (Boolean)doc.getData().get("isEnded");
                                        Boolean is_published = (Boolean)doc.getData().get("isPublished");

                                        Experiment experiment = new Experiment(exp_ID, owner_ID, description, type, geo_required, min_trials, rules, region);

                                        experiment.setIs_ended(is_ended);
                                        experiment.setIs_published(is_published);

                                        subscribed_experiments.add(experiment);

                                        Log.d("Subscribed Experiments:", subscribed_experiments.toString());

                                        adapter.notifyDataSetChanged();

                                    }
                                }
                            });
                        }

                        MainModel.setSubscribed_experiments(subscribed_experiments);

                    }
                }
            }

        });


    }




}
