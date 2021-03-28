package com.example.appraisal.UI.main_menu.specific_experiment_details;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.appraisal.R;
import com.example.appraisal.UI.main_menu.forum.ForumRepliesActivity;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.backend.user.User;
import com.example.appraisal.model.MainModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpecificExpDiscussionFragment extends Fragment {

    private ListView question_display;
    private ArrayList<String> questions_list;
    private ArrayAdapter<String> question_adapter;
    private EditText question_input;
    private Experiment current_experiment;
    private CollectionReference exp_ref;
    private int firebase_num_questions = 0;
    private String ques_text;
    private String question_name_pass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_specific_exp_discussion, container, false);

        try {
            current_experiment = MainModel.getCurrentExperiment();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            exp_ref = MainModel.getExperimentReference();
        } catch (Exception e) {
            e.printStackTrace();
        }

        question_display = v.findViewById(R.id.forum_questions);
        questions_list = new ArrayList<>();
        question_adapter = new ArrayAdapter<>(this.getActivity(), R.layout.list_content, questions_list);

        getDbQuestions();

        question_display.setAdapter(question_adapter);

        question_display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                int selected_question = position;
                String question_string = questions_list.get(selected_question);
                question_name_pass = "Question" + (position+1);


                Intent intent = new Intent(getContext(), ForumRepliesActivity.class);
                intent.putExtra("Question", question_string);
                intent.putExtra("Q_id_name",question_name_pass);

                startActivity(intent);
            }
        });

        //dialogue box to publish a new question
        final FloatingActionButton publishNewQuestion = v.findViewById(R.id.AddQuestionButton);
        publishNewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Post a question!");

                question_input = new EditText(getContext());
                builder.setView(question_input);

                try {
                    exp_ref = MainModel.getExperimentReference();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    current_experiment = MainModel.getCurrentExperiment();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                listenToNumOfQuestions();

                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ques_text = question_input.getText().toString();
                        storeQuestionInFireBase();
                        question_adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog question_box = builder.create();
                question_box.show();
            }
        });

        return v;
    }

    /**
     * Queries the database to get the list of questions
     */
    private void getDbQuestions() {
        exp_ref.document(current_experiment.getExpId()).collection("Questions").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // clear old list
                questions_list.clear();

                // check each question document
                for (QueryDocumentSnapshot doc : value) {
                    String question_text = doc.getData().get("question").toString();

                    // add question to the list to display
                    questions_list.add(question_text);

                }
                // notify adapter that data has change and to update the UI
                question_adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Queries the database to get the total number of questions for the experiment
     */
    private void listenToNumOfQuestions() {

        exp_ref.document(current_experiment.getExpId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if ((document != null) && document.exists()) {
                        try {
                            firebase_num_questions = Integer.parseInt(document.get("numOfQuestions").toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /**
     * Publish the questions to firebase
     */
    public void storeQuestionInFireBase() {

        String experiment_ID = current_experiment.getExpId();

        Integer num_of_questions = firebase_num_questions + 1;
        String question_name = "Question" + num_of_questions;
        Map<String, Object> question_info = new HashMap<>();
        question_info.put("question", ques_text);

        // create new document for question with values from hash map
        exp_ref.document(experiment_ID).collection("Questions").document(question_name).set(question_info)
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

        exp_ref.document(experiment_ID).update("numOfQuestions", num_of_questions);
    }
}