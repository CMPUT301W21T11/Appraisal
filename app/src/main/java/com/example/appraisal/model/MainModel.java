package com.example.appraisal.model;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainModel implements DataRequestable{
    private static MainModel single_instance;

    private FirebaseFirestore db;
    private List<String> subscription_list;
    final CollectionReference collection_reference;
    private String user_name;

    private MainModel(){
        db = FirebaseFirestore.getInstance();
        collection_reference = db.collection("Subscribed");
        /**
         * HAS TO BE REFACTORED WHEN USER CLASS IS READY
         */
        // TODO implement after USER is done
        user_name = "User0000";
    }

    /**
     * Use this method to create one single instance
     */
    public static void getInstance(){
        if (single_instance == null){
            single_instance = new MainModel();
        }
    }

    public List<String> getSubscriptionList(){
        return subscription_list;
    }

    @Override
    public List<String> requestMyExpIndex() {
        CollectionReference ref = db.collection("Users").document("User0000").collection("Subscriptions");
        return new ArrayList<String>();
    }
}
