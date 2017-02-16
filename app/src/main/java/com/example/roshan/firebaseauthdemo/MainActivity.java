package com.example.roshan.firebaseauthdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_register;
    private TextView txt_alreadyuser,txt_forgetpsw;
    private EditText editTextemail,editTextpassword;
    private ProgressDialog progressdialog;
    private FirebaseAuth firebaseauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressdialog = new ProgressDialog(this);
        firebaseauth= FirebaseAuth.getInstance();
        if(firebaseauth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), Profile.class));
        }
        btn_register=(Button)findViewById(R.id.btn_register);
        txt_alreadyuser=(TextView)findViewById(R.id.txt_alreadyuser);
        editTextemail=(EditText)findViewById(R.id.email);
        editTextpassword=(EditText)findViewById(R.id.password);
        txt_forgetpsw=(TextView)findViewById(R.id.txt_forgetpassword);
        txt_forgetpsw.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        txt_alreadyuser.setOnClickListener(this);
        MainActivity.this.setTitle("Register");
    }

    @Override
    public void onClick(View view) {
        if(view==btn_register){
            registerUser();
        }
        if (view==txt_alreadyuser){

            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

        }
        if (view==txt_forgetpsw){
            startActivity(new Intent(getApplicationContext(),ResetPasswordActivity.class));
        }

    }
    private void registerUser(){
        String email=editTextemail.getText().toString().trim();
        String password=editTextpassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {

            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
    return;
        }
        if (TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
     return;
        }
        progressdialog.setMessage("Registering User...");
        progressdialog.show();
//
        firebaseauth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressdialog.dismiss();

                        if (task.isSuccessful()){
//                            finish();
//                            startActivity(new Intent(getApplicationContext(), Profile.class));
                            Toast.makeText(MainActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Couldn't register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
