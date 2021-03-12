package com.example.appraisal.model;

import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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
    private User current_user;
    private Experiment chosen_experiment;


    private MainModel(){
        db = FirebaseFirestore.getInstance();



        /**
         * HAS TO BE REFACTORED AFTER FIREBASE DATA QUERY IS READY
         */
        // TODO implement after USER is done
        current_user = new User("User0000", "John", "john@mail.com", "780 999 9999");
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
     * experiment was chosen
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
     * experiment was chosen
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

    public static DocumentReference getUserReference(String id) throws Exception {

        if (single_instance == null) {
            throw new Exception("single_instance is not initiated");
        }

        final DocumentReference user_reference = single_instance.db.collection("Users")
                .document(id);

        return user_reference;
    }
}
