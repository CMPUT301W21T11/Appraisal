package com.example.appraisal.model;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


/**
 * This structure is the major backend that host the connection to Firebase and many other things. This is a Singleton
 * design pattern and should be called at least once right after the app starts via {@link #createInstance()}
 */
public class MainModel implements DataRequestable {
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
     * Use this method to create one single instance of the MainModel. If left untouched, its lifetime would persists
     * throughout the whole app. Should be called at least once right after the app starts.
     */
    public static void createInstance(){
        if (single_instance == null) {
            single_instance = new MainModel();
        }
    }

    /**
     * This method is used to check the if the single_instance is null or not
     * @return True if created, False if null
     */
    public static boolean existed() {
        return single_instance != null;
    }

    /**
     * This method remove the existence of the single_instance. MAY CAUSES @{@link NullPointerException}!!!
     */
    public static void kill() {
        single_instance = null;
    }

    /**
     * This method reset the single_instance to a fresh start
     */
    public static void reset() {
        single_instance = new MainModel();
    }



    public List<String> requestMyExpIndex() {
        CollectionReference ref = db.collection("Users").document("User0000").collection("Subscriptions");
        return new ArrayList<String>();
    }
}
