package net.marwa.applicationy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HeadActivity extends AppCompatActivity {
    ListView listView;
    final ArrayList<String> keyList = new ArrayList<>();

    List<Head> list;
    MyAdapterHead myAdapter;
    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    private EditText editTextPrice5,editTextFirst5,editTextLast5,editTextPhone5;
    private Button addNew5;
    private Button add5;
    private Button upload5;

    public static final String DATABASE_PATH = "Head";
    private Uri imageUri;

    private StorageReference storageReference;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head);
        firebaseAuth = FirebaseAuth.getInstance();

        listView = (ListView) findViewById(R.id.list6);
        editTextPrice5= (EditText) findViewById(R.id.editTextPrice5);editTextPrice5.setVisibility(View.GONE);
        editTextFirst5= (EditText) findViewById(R.id.editTextFirstName5);editTextFirst5.setVisibility(View.GONE);
        editTextLast5= (EditText) findViewById(R.id.editTextLastName5);editTextLast5.setVisibility(View.GONE);
        editTextPhone5= (EditText) findViewById(R.id.editTextPhone5);editTextPhone5.setVisibility(View.GONE);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);
        add5=(Button) findViewById(R.id.add5);
        addNew5=(Button) findViewById(R.id.addNew5);
        addNew5.setVisibility(View.GONE);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        add5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listView.setVisibility(View.GONE);
                add5.setVisibility(View.GONE);
                addNew5.setVisibility(View.VISIBLE);
                editTextPrice5.setVisibility(View.VISIBLE);
                editTextLast5.setVisibility(View.VISIBLE);
                editTextFirst5.setVisibility(View.VISIBLE);
                editTextPhone5.setVisibility(View.VISIBLE);

            }
        });



        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                list.clear();
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    keyList.add(snap.getKey());

                    Head head = snap.getValue(Head.class);
                    list.add(head);
                }

                myAdapter = new MyAdapterHead (HeadActivity.this,R.layout.data_items3,list);
                listView.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void uploadData(View view){

        if(!(TextUtils.isEmpty(editTextFirst5.getText().toString()))&&!(TextUtils.isEmpty(editTextLast5.getText().toString()))&&! (TextUtils.isEmpty(editTextPhone5.getText().toString()))&& !(TextUtils.isEmpty(editTextPrice5.getText().toString())))
        {
            // insert data

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Saving Singer Info..!!");
            progressDialog.show();

            String price = editTextPrice5.getText().toString();
            String first = editTextFirst5.getText().toString();
            String last = editTextLast5.getText().toString();
            String phone = editTextPhone5.getText().toString();

            Head head = new Head(first,last,phone,price);

            String id = databaseReference.push().getKey();

            databaseReference.child(id).setValue(head);

            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "New Singer is added", Toast.LENGTH_LONG).show();
        }

        else {
            // show message
            Toast.makeText(getApplicationContext(),"All fields are required!!",Toast.LENGTH_LONG).show();
        }

    }


}






