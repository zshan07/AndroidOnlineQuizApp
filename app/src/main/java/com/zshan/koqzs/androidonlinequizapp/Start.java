package com.zshan.koqzs.androidonlinequizapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zshan.koqzs.androidonlinequizapp.Common.Common;
import com.zshan.koqzs.androidonlinequizapp.Model.Question;

import java.util.Collections;

import static com.zshan.koqzs.androidonlinequizapp.Common.Common.categoryId;
import static com.zshan.koqzs.androidonlinequizapp.Common.Common.questionList;

public class Start extends AppCompatActivity {

    Button btnPlay;

    FirebaseDatabase database;
    DatabaseReference questions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        database=FirebaseDatabase.getInstance();
        questions=database.getReference("Questions");





        btnPlay=(Button)findViewById(R.id.btnPlay);


        loadQuestion(Common.categoryId);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Start.this,Playing.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadQuestion(String categoryId)



    {
        //firstly check if theres any questions from before quizzes and clear them

        if(Common.questionList.size()>0)
            Common.questionList.clear();

        questions.orderByChild("CategoryId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            Question ques=postSnapshot.getValue(Question.class);
                            Common.questionList.add(ques);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        //random questions

        Collections.shuffle(Common.questionList);
    }
}
