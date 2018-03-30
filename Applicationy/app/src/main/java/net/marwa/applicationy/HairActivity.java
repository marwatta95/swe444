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

public class HairActivity extends AppCompatActivity {
    ListView listView;
    final ArrayList<String> keyList = new ArrayList<>();

    List<Hair> list;
    MyAdapter6 myAdapter;
    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    private EditText editTextPrice6,editTextFirst6,editTextLast6,editTextPhone6;
    private Button addNew6;
    private Button add6;
    private Button upload5;

    public static final String DATABASE_PATH = "Hair Dresser";

    private StorageReference storageReference;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair);
        firebaseAuth = FirebaseAuth.getInstance();

        listView = (ListView) findViewById(R.id.list7);
        editTextPrice6= (EditText) findViewById(R.id.editTextPrice6);editTextPrice6.setVisibility(View.GONE);
        editTextFirst6= (EditText) findViewById(R.id.editTextFirstName6);editTextFirst6.setVisibility(View.GONE);
         editTextLast6= (EditText) findViewById(R.id.editTextLastName6);editTextLast6.setVisibility(View.GONE);
        editTextPhone6= (EditText) findViewById(R.id.editTextPhone6);editTextPhone6.setVisibility(View.GONE);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);
        add6=(Button) findViewById(R.id.add6);
        addNew6=(Button) findViewById(R.id.addNew6);
        addNew6.setVisibility(View.GONE);
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
        add6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listView.setVisibility(View.GONE);
                add6.setVisibility(View.GONE);
                addNew6.setVisibility(View.VISIBLE);
                editTextPrice6.setVisibility(View.VISIBLE);
                editTextLast6.setVisibility(View.VISIBLE);
                editTextFirst6.setVisibility(View.VISIBLE);
                editTextPhone6.setVisibility(View.VISIBLE);

            }
        });



        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference(HairActivity.DATABASE_PATH);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                list.clear();
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    keyList.add(snap.getKey());

                    Hair hair = snap.getValue(Hair.class);
                    list.add(hair);
                }

                myAdapter = new MyAdapter6 (HairActivity.this,R.layout.data_items3,list);
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

                databaseReference.getRoot().child(DATABASE_PATH).child(keyList.get(position)).removeValue();
                keyList.remove(position);

                Toast.makeText(getApplicationContext(),"Deleted Successfully!!!",Toast.LENGTH_LONG).show();

            }
        });

    }

    public void uploadData(View view){

        if(!(TextUtils.isEmpty(editTextFirst6.getText().toString()))&&!(TextUtils.isEmpty(editTextLast6.getText().toString()))&&! (TextUtils.isEmpty(editTextPhone6.getText().toString()))&& !(TextUtils.isEmpty(editTextPrice6.getText().toString())))
        {
            // insert data

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Saving Singer Info..!!");
            progressDialog.show();

            String price = editTextPrice6.getText().toString();
            String first = editTextFirst6.getText().toString();
            String last = editTextLast6.getText().toString();
            String phone = editTextPhone6.getText().toString();
            double priceDouble=Double.parseDouble(price);

            Hair hair = new Hair(first,last,phone,priceDouble);

            String id = databaseReference.push().getKey();

            databaseReference.child(id).setValue(hair);

            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "New Hair dresser is added", Toast.LENGTH_LONG).show();
        }

        else {
            // show message
            Toast.makeText(getApplicationContext(),"All fields are required!!",Toast.LENGTH_LONG).show();
        }

    }


}






