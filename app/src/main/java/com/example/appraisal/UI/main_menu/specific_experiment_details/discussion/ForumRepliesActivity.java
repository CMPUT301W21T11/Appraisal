package com.example.appraisal.UI.main_menu.specific_experiment_details.discussion;

import android.content.DialogInterface;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appraisal.R;
import com.example.appraisal.backend.experiment.Experiment;
import com.example.appraisal.model.core.MainModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ForumRepliesActivity extends  AppCompatActivity {

    private ListView reply_display;
    private ArrayList<String> replies_list;
    private ArrayAdapter<String> reply_adapter;
    private EditText reply_input;
    private Experiment current_experiment;
    private CollectionReference exp_ref;
    private int firebase_num_replies = 0;
    private String reply_text;
    private String question_name_receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_replies);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        String question = getIntent().getStringExtra("Question");
        question_name_receive = getIntent().getStringExtra("Q_id_name");

        TextView textView = findViewById(R.id.question);
        textView.setText(question);


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

        reply_display = findViewById(R.id.forum_replies);
        replies_list = new ArrayList<>();
        reply_adapter = new ArrayAdapter<>(this, R.layout.list_content, replies_list);

        getDbReplies();

        reply_display.setAdapter(reply_adapter);

        //dialogue box to publish a new reply
        final Button publishNewReply = findViewById(R.id.AddReplyButton);
        publishNewReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder  = new AlertDialog.Builder(ForumRepliesActivity.this, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Background);
                builder.setTitle("Post a reply!");

                reply_input = new EditText(ForumRepliesActivity.this);
                builder.setView(reply_input);

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

                listenToNumOfReplies();

                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reply_text = reply_input.getText().toString();
                        storeReplyInFireBase();
                        reply_adapter.notifyDataSetChanged();
                    }
                });



                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog reply_box = builder.create();
                reply_box.show();
            }
        });
    }

    /**
     * Queries the database to get the list of replies questions
     */
    private void getDbReplies() {
        exp_ref.document(current_experiment.getExpId()).collection("Questions").document(question_name_receive).collection("Replies").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // clear old list
                replies_list.clear();

                // check each replies document
                for (QueryDocumentSnapshot doc : value) {
                    String reply_text = doc.getData().get("reply").toString();

                    // add replies to the list to display
                    replies_list.add(reply_text);

                }
                // notify adapter that data has change and to update the UI
                reply_adapter.notifyDataSetChanged();
            }
        });
    }


    /**
     * Queries the database to get the total number of replies for the question
     */
    private void listenToNumOfReplies() {

        exp_ref.document(current_experiment.getExpId()).collection("Questions").document(question_name_receive).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if ((document != null) && document.exists()) {
                        try {
                            firebase_num_replies = Integer.parseInt(document.get("numOfReplies").toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /**
     * Publish the replies to firebase
     */
    public void storeReplyInFireBase() {

        String experiment_ID = current_experiment.getExpId();

        Integer num_of_replies = firebase_num_replies + 1;
        String reply_name = "Reply" + num_of_replies;
        Map<String, Object> replies_info = new HashMap<>();
        replies_info.put("reply", reply_text);

        // create new document for replies with values from hash map
        exp_ref.document(experiment_ID).collection("Questions").document(question_name_receive).collection("Replies").document(reply_name).set(replies_info)
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

        exp_ref.document(experiment_ID).collection("Questions").document(question_name_receive).update("numOfReplies", num_of_replies);
    }

    /**
     * When the up button gets clicked, the activity is killed.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
