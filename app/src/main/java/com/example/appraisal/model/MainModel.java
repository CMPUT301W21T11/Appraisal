package com.example.appraisal.model;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainModel {
    private static MainModel single_instance;

    private FirebaseFirestore db;
    private List<String> subscription_list;
    final CollectionReference collectionReference;

    private MainModel(){
        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("Subscribed");
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
}
