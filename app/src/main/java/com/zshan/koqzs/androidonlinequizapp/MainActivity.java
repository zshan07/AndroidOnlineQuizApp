package com.zshan.koqzs.androidonlinequizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zshan.koqzs.androidonlinequizapp.Common.Common;
import com.zshan.koqzs.androidonlinequizapp.Model.User;

public class MainActivity extends AppCompatActivity {

    MaterialEditText edtNewUser,edtNewPassword,edtNewEmail;//for sign up
    MaterialEditText edtUser,edtPassword;//for sign in

    Button btnSignUp,btnSignIn;

    FirebaseDatabase database;
    DatabaseReference users;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //firebase

        database=FirebaseDatabase.getInstance();
        users=database.getReference("Users");


        edtUser=(MaterialEditText)findViewById(R.id.edtUser);
        edtPassword=(MaterialEditText)findViewById(R.id.edtPassword);

        btnSignIn=(Button)findViewById(R.id.btn_sign_in);
        btnSignUp=(Button)findViewById(R.id.btn_sign_up);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpDialog();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(edtUser.getText().toString(),edtPassword.getText().toString());
            }
        });



    }

    private void signIn(final String user, final String pwd) {

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(user).exists())
                {
                    if(!user.isEmpty())
                    {
                        User login=dataSnapshot.child(user).getValue(User.class);
                        if(login.getPassword().equals(pwd))
                        {
                            // Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent homeActivity=new Intent(MainActivity.this,Home.class);
                            Common.currentUser=login;
                            startActivity(homeActivity);
                            finish();


                        }else
                        {
                            Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }



                    }else
                    {
                        Toast.makeText(MainActivity.this, "Enter user name", Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(MainActivity.this, "User Does Not exist", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showSignUpDialog() {

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
        alertDialog.setMessage("please fill all the information to register");
        alertDialog.setTitle("Sign Up");


        LayoutInflater layoutInflater=this.getLayoutInflater();
        View sign_up_layout=layoutInflater.inflate(R.layout.sign_up_layout,null);


        edtNewUser=(MaterialEditText)sign_up_layout.findViewById(R.id.edtNewUserName);
        edtNewPassword=(MaterialEditText)sign_up_layout.findViewById(R.id.edtNewPassword);
        edtNewEmail=(MaterialEditText)sign_up_layout.findViewById(R.id.edtNewEmail);


        alertDialog.setView(sign_up_layout);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)

            {
               dialog.dismiss();
            }
        });

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                final User user=new User(edtNewUser.getText().toString(),edtNewPassword.getText().toString(),edtNewEmail.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.getUsername()).exists())
                        {
                            Toast.makeText(MainActivity.this, "User already exists !", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            //setvalue(user) is used to set values inside the firebase using  User
                            users.child(user.getUsername()).setValue(user);
                            Toast.makeText(MainActivity.this, "Registration successful !", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        dialog.dismiss();
                    }
                });


            }
        });

        alertDialog.show();
    }
}
