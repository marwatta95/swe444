package net.marwa.applicationy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

private Button buttonSignIn;
private EditText editTextEmail;
private EditText editTextPassword;
private TextView textViewSignup;
    private DatabaseReference databaseReference;

private FirebaseAuth firebaseAuth;

private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth =  FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(), activity_profile.class));
        }
        databaseReference= FirebaseDatabase.getInstance().getReference();

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignin);
        textViewSignup = (TextView) findViewById(R.id.textViewSignup);

        progressDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);

    }


    private void userLogin(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Log in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         progressDialog.dismiss();
                         if(task.isSuccessful()) {

                             if ((editTextEmail.getText().toString()).equals("marwatta95@gmail.com")) {
                                 finish();
                                 startActivity(new Intent(getApplicationContext(), HeadActivity.class));


                             }
                             else{
                             finish();
                             startActivity(new Intent(getApplicationContext(), UserHomeActivity.class));

                         }
                         }
                         else{
                             Toast.makeText(LoginActivity.this, "Could not login "+task.getException().getMessage(), Toast.LENGTH_LONG).show();

                         }

                    }
                });

    }



    @Override
    public void onClick(View view) {
        if(view== buttonSignIn){
            userLogin();
        }
        if(view== textViewSignup){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
