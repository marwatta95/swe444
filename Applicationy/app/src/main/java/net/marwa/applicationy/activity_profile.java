package net.marwa.applicationy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.DatePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class activity_profile extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    private EditText editTextFirst,editTextLast,editTextAddress;
    private Button buttonSave;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        editTextAddress=(EditText) findViewById(R.id.editTextAddress);
        editTextFirst=(EditText) findViewById(R.id.editTextFirstName);
        editTextLast=(EditText) findViewById(R.id.editTextLastName);
       buttonSave=(Button) findViewById(R.id.buttonSave);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        radioSexGroup=(RadioGroup) findViewById(R.id.radioSex);

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("Now tell us about yourself :)");

        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);

    }

    private void saveUserInformation()
    {
      String dateOfBirth=(datePicker.getDayOfMonth()+"_"+datePicker.getMonth()+"_"+datePicker.getYear());

        int selectedId = radioSexGroup.getCheckedRadioButtonId();

        radioSexButton = (RadioButton) findViewById(selectedId);

        String gender= radioSexButton.getText().toString();
        String first= editTextFirst.getText().toString();
        String last= editTextLast.getText().toString();
        String address= editTextAddress.getText().toString();
 User userInfo=new User(2,first,last,gender,address,dateOfBirth);
        FirebaseUser user = firebaseAuth.getCurrentUser();

         databaseReference.child(user.getUid()).setValue(userInfo);

        Toast.makeText(this,"Thank you for sharing your Information with us",Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {
if(view == buttonLogout){


    startActivity(new Intent(this, UserHomeActivity.class));
}
if (view == buttonSave){
    saveUserInformation();
    startActivity(new Intent(this, UserHomeActivity.class));

}
    }
}
