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

public class MakeupActivity extends AppCompatActivity {
    ListView listView;
    final ArrayList<String> keyList = new ArrayList<>();
    List<MakeUp> list;
    MyAdapter7 myAdapter;
    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    private EditText editTextPrice7,editTextFirst7,editTextLast7,editTextPhone7;
    private Button addNew7;
    private Button add7;

    public static final String DATABASE_PATH = "Make Up Artist";

    private StorageReference storageReference;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeup);
        firebaseAuth = FirebaseAuth.getInstance();

        listView = (ListView) findViewById(R.id.list8);
        editTextPrice7= (EditText) findViewById(R.id.editTextPrice7);editTextPrice7.setVisibility(View.GONE);
        editTextFirst7= (EditText) findViewById(R.id.editTextFirstName7);editTextFirst7.setVisibility(View.GONE);
        editTextLast7= (EditText) findViewById(R.id.editTextLastName7);editTextLast7.setVisibility(View.GONE);
        editTextPhone7= (EditText) findViewById(R.id.editTextPhone7);editTextPhone7.setVisibility(View.GONE);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);
        add7=(Button) findViewById(R.id.add7);
        addNew7=(Button) findViewById(R.id.addNew7);
        addNew7.setVisibility(View.GONE);
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
        add7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listView.setVisibility(View.GONE);
                add7.setVisibility(View.GONE);
                addNew7.setVisibility(View.VISIBLE);
                editTextPrice7.setVisibility(View.VISIBLE);
                editTextLast7.setVisibility(View.VISIBLE);
                editTextFirst7.setVisibility(View.VISIBLE);
                editTextPhone7.setVisibility(View.VISIBLE);

            }
        });



        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference(MakeupActivity.DATABASE_PATH);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                list.clear();
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    keyList.add(snap.getKey());
                    MakeUp makeUp = snap.getValue(MakeUp.class);
                    list.add(makeUp);
                }

                myAdapter = new MyAdapter7 (MakeupActivity.this,R.layout.data_items3,list);
                listView.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list.remove(position);
                myAdapter.notifyDataSetChanged();
                //new code below
                databaseReference.getRoot().child(DATABASE_PATH).child(keyList.get(position)).removeValue();
                keyList.remove(position);
                Toast.makeText(getApplicationContext(),"Deleted Successfully!!!",Toast.LENGTH_LONG).show();

            }
        });

    }

    public void uploadData(View view){

        if(!(TextUtils.isEmpty(editTextFirst7.getText().toString()))&&!(TextUtils.isEmpty(editTextLast7.getText().toString()))&&! (TextUtils.isEmpty(editTextPhone7.getText().toString()))&& !(TextUtils.isEmpty(editTextPrice7.getText().toString())))
        {
            // insert data

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Saving Singer Info..!!");
            progressDialog.show();

            String price = editTextPrice7.getText().toString();
            String first = editTextFirst7.getText().toString();
            String last = editTextLast7.getText().toString();
            String phone = editTextPhone7.getText().toString();
            double priceDouble=Double.parseDouble(price);

            MakeUp mu = new MakeUp(first,last,phone,priceDouble);

            String id = databaseReference.push().getKey();

            databaseReference.child(id).setValue(mu);

            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "New MakeUp Artist is added", Toast.LENGTH_LONG).show();
        }

        else {
            // show message
            Toast.makeText(getApplicationContext(),"All Fields are required!!",Toast.LENGTH_LONG).show();
        }

    }


}






