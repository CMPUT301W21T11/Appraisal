package com.example.appraisal.model;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.FirebaseAuthentication;
import com.example.appraisal.backend.user.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This structure is the major backend that host the connection to Firebase and many other things. This is a Singleton
 * design pattern and should be called at least once right after the app starts via {@link #createInstance()}
 */
public class MainModel implements DataRequestable {
    private static MainModel single_instance;

    private FirebaseFirestore db;

    private static User current_user;
    private Experiment chosen_experiment;
    private static ArrayList<Experiment> my_experiments;

    //    public static FirebaseAuthentication auth;
    public static String user_id;
    public static boolean is_new;

    public static FirebaseAuthentication mAuth;

    public static ArrayList<Experiment> sub_experiments;


    private MainModel() {
        db = FirebaseFirestore.getInstance();

        mAuth = new FirebaseAuthentication();

        mAuth.sign_in();

        if (mAuth.isLoggedIn()) {
            is_new = false;

        } else {
            is_new = true;
        }

    }

    /**
     * Use this method to create one single instance of the MainModel. If left untouched, its lifetime would persists
     * throughout the whole app. Should be called at least once right after the app starts.
     */
    public static void createInstance() {
        if (single_instance == null) {
            single_instance = new MainModel();
        }
    }

    /**
     * This method is used to check the if the single_instance is null or not
     *
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

    /**
     * Setting the chosen experiment and store in this global space
     *
     * @throws Exception thrown when either the MainModel is not instantiated
     */
    public static void setCurrentExperiment(Experiment experiment) throws Exception {
        if (single_instance == null) {
            throw new Exception("single_instance is not initiated");
        }
        single_instance.chosen_experiment = experiment;
    }

    /**
     * Remove the chosen experiment and store in this global space. THIS RENDERS CHOSEN_EXPERIMENT TO NULL!
     *
     * @throws Exception thrown when either the MainModel is not instantiated
     */
    public static void removeChosenExperiment() throws Exception {
        if (single_instance == null) {
            throw new Exception("single_instance is not initiated");
        }
        single_instance.chosen_experiment = null;
    }

    /**
     * Getting the chosen experiment stored in this global space
     *
     * @return chosen {@link Experiment}
     * @throws Exception {@link NullPointerException} thrown when either the MainModel is not instantiated or no
     *                   experiment was chosen
     */
    public static Experiment getCurrentExperiment() throws Exception {
        if (single_instance == null) {
            throw new Exception("single_instance is not initiated");
        }

        if (single_instance.chosen_experiment == null) {
            throw new Exception("No experiment was chosen (NullPointerException)");
        }

        return single_instance.chosen_experiment;
    }

    /**
     * Setting the user and store in this global space
     *
     * @throws Exception thrown when either the MainModel is not instantiated
     */
    public static void setCurrentUser(User user) throws Exception {
        if (single_instance == null) {
            throw new Exception("single_instance is not initiated");
        }
        single_instance.current_user = user;

        user.setID(user_id);

    }

    /**
     * Remove the chosen user and store in this global space. THIS RENDERS CURRENT_USER TO NULL!
     *
     * @throws Exception thrown when either the MainModel is not instantiated
     */
    public static void removeCurrentUser() throws Exception {
        if (single_instance == null) {
            throw new Exception("single_instance is not initiated");
        }
        single_instance.chosen_experiment = null;
    }

    /**
     * Getting the chosen experiment stored in this global space
     *
     * @return chosen {@link Experiment}
     * @throws Exception {@link NullPointerException} thrown when either the MainModel is not instantiated or no
     *                   experiment was chosen
     */
    public static User getCurrentUser() throws Exception {
        if (single_instance == null) {
            throw new Exception("single_instance is not initiated");
        }

        if (single_instance.current_user == null) {
            throw new Exception("No User was set (NullPointerException)");
        }

        return single_instance.current_user;
    }

    public static DocumentReference getUserReference() throws Exception {

        if (single_instance == null) {
            throw new Exception("single_instance is not initiated");
        }

        final DocumentReference user_reference = single_instance.db.collection("Users")
                .document(user_id);

        return user_reference;
    }


    public static void checkUserStatus() {
        user_id = signInUser();
        if (is_new){
            setUpNewUser();
        }
        else {
            loadCurrentUser();
        }

    }

    public static String signInUser() {
       return mAuth.get_userID();
    }

    public static void setUpNewUser(){
        CollectionReference new_user = single_instance.db.collection("Users");

        current_user = new User(user_id, "", "", "");

//        User user = new User(user_id, "", "", "");
//        current_user = user;

        // Create a new user with a first and last name
        Map<String, Object> user_info = new HashMap<>();
        user_info.put("user_name", "");
        user_info.put("user_email", "");
        user_info.put("phone_number", "");
        user_info.put("num_of_my_exp", 0);

        // Add a new document with a generated ID
        new_user.document(user_id).set(user_info)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("***", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("***", "Error writing document", e);
                    }
                });
    }

    public static void loadCurrentUser() {
        // get data from firebase
        // update local user object
        Log.d("user id", user_id);
        single_instance.db.collection("Users").document(user_id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String user_name = value.get("user_name").toString();
                String user_email = value.get("user_email").toString();
                String phone_number = value.get("phone_number").toString();
                Integer num_of_exp = Integer.valueOf(value.get("num_of_my_exp").toString());

                User current_user = new User(user_id, user_name, user_email, phone_number);
                current_user.setNum_of_exp(num_of_exp);
                try {
                    MainModel.setCurrentUser(current_user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Method to be called to retrieve a document reference of a specific user on the database
    public static DocumentReference retrieveSpecificUser (String other_user_id) throws Exception {

        if (single_instance == null) {
            throw new Exception("single_instance is not initiated");
        }

        final DocumentReference other_user_reference = single_instance.db.collection("Users")
                .document(other_user_id);

        return other_user_reference;
    }


    public static CollectionReference getExperimentReference() throws Exception {
        if (single_instance == null) {
            throw new Exception("single_instance is not initiated");
        }

        final CollectionReference experiment_reference = single_instance.db.collection("Experiments");
        return experiment_reference;
    }

    public static ArrayList<Experiment> getSubscribed_experiments() {
        return sub_experiments;
    }

    public static void setSubscribed_experiments(ArrayList<Experiment> subscribed_experiments) {
        sub_experiments = subscribed_experiments;
    }
}
