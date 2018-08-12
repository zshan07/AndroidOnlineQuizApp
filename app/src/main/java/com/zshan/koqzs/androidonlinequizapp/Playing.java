//category id is coming as null so see everywhere you are importing or using categor id
// once again.child of question_score has value zeeshan_null

//probably the error is in gameplay phase
//playing.java activity_playing.xml
//done.java activity_done.xml
//start.java activity_start.xml
// /QuestionScore.java


package com.zshan.koqzs.androidonlinequizapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.zshan.koqzs.androidonlinequizapp.Common.Common;

import static com.zshan.koqzs.androidonlinequizapp.Common.Common.questionList;

public class Playing extends AppCompatActivity implements TextView.OnClickListener{

    final static long INTERVAL=1000;//1sec
    final static long TIMEOUT=7000;//1sec
    int progressValue=0;

    CountDownTimer mCountDown;



    int index=0,score=0,thisQuestion=0,totalQuestion,correctAnswer;




    ProgressBar progressBar;
    ImageView question_image;
    TextView question_text,txtScore,txtQuestionNum;
    Button btnA,btnB,btnC,btnD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);




        //views
        txtScore=(TextView)findViewById(R.id.txtScore);
        txtQuestionNum=(TextView)findViewById(R.id.txtTotalQuestion);
        question_text=(TextView)findViewById(R.id.question_text);
        question_image=(ImageView)findViewById(R.id.question_image);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);


        btnA=(Button)findViewById(R.id.btnAnswerA);
        btnB=(Button)findViewById(R.id.btnAnswerB);
        btnC=(Button)findViewById(R.id.btnAnswerC);
        btnD=(Button)findViewById(R.id.btnAnswerD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);

        totalQuestion=Common.questionList.size();

    }

    @Override
    public void onClick(View v) {
        mCountDown.cancel();
        if(index<totalQuestion) //still have quesions in the list
        {
            Button clickedButton=(Button)v;
            if(clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer()))
            {
                //chose the correct answer
                score+=10;
                correctAnswer++;
                showQuestion(++index);

            }else
            {
                //chose the wrong answer
                Intent intent=new Intent(this,Done.class);
                Bundle dataSend=new Bundle();
                dataSend.putInt("SCORE",score);
                dataSend.putInt("TOTAL",totalQuestion);
                dataSend.putInt("CORRECT",correctAnswer);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();


            }
            txtScore.setText(String.format("%d",score));

        }

    }

    private void showQuestion(int index)
    {
        if(index<totalQuestion)
        {
            thisQuestion++;
            txtQuestionNum.setText(String.format("%d / %d ",thisQuestion,totalQuestion));
            progressBar.setProgress(0);
            progressValue=0;


            if(Common.questionList.get(index).getIsImageQuestion().equals("true"))
            {
                //if its an image

                Picasso.with(getBaseContext())
                        .load(Common.questionList.get(index).getQuestion())
                        .into(question_image);

                question_image.setVisibility(View.VISIBLE);
                question_text.setVisibility(View.INVISIBLE);

            }else
            {
                question_text.setText(Common.questionList.get(index).getQuestion());

                question_image.setVisibility(View.INVISIBLE);
                question_text.setVisibility(View.VISIBLE);


            }
            //if question is text we will set the image to invisible
            btnA.setText(Common.questionList.get(index).getAnswerA());
            btnB.setText(Common.questionList.get(index).getAnswerB());
            btnC.setText(Common.questionList.get(index).getAnswerC());
            btnD.setText(Common.questionList.get(index).getAnswerD());


            mCountDown.start();
        }else
        {

            //if the question is a final question

            Intent intent=new Intent(this,Done.class);
            Bundle dataSend=new Bundle();
            dataSend.putInt("SCORE",score);
            dataSend.putInt("TOTAL",totalQuestion);
            dataSend.putInt("ANSWER",correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();

        }


    }


    @Override
    protected void onResume() {
        super.onResume();

        totalQuestion=Common.questionList.size();

        mCountDown=new CountDownTimer(TIMEOUT,INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(progressValue);
                progressValue++;


            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                showQuestion(++index);

            }
        };
        showQuestion(index);
    }
}




