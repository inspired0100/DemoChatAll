package com.inspired0100.flashchatnewfirebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    // member variables here:
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        //instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();


    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {
        // attemptLogin() here
        attemptLogin();

    }

    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.inspired0100.flashchatnewfirebase.RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    //  the attemptLogin() method
    private void attemptLogin() {

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if ( email.equals("") || (password.equals(""))) {
            new AlertDialog.Builder(this)
                    .setTitle("Fields Empty")
                    .setMessage("Either email or the password field is empty.Try Again!!")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mEmailView.setText("");
                            mPasswordView.setText("");
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


        } else {
            Toast.makeText(this, R.string.ToastLoginProgress, Toast.LENGTH_SHORT).show();


            //  FirebaseAuth to sign in with email & password

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Log.d("FlashChat", "Login failded " + task.getException());
                        errorLoginInFireBase(task.getException().toString());


                    } else {
                        //Toast.makeText(this, R.string.ToastLoginSuccessful, Toast.LENGTH_SHORT).show();
                        //Cannot resolve method 'makeText(anonymous com.google.android.gms.tasks.OnCompleteListener<com.google.firebase.auth.AuthResult>, int, int)
                        //because Toast is a class of Appcompat.v7 which is not in mAuth a.k.a FirebaseAuth
                        Intent intent = new Intent(LoginActivity.this, MainChatActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }
            });
        }



    }

    // error on screen with an alert dialog
    private void errorLoginInFireBase (String messages) {
        new AlertDialog.Builder(this)
                .setTitle("Login Problem")
                .setMessage(messages)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }







}