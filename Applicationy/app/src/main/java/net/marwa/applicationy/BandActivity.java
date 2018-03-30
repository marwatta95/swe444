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

public class BandActivity extends AppCompatActivity {
    ListView listView;
    final ArrayList<String> keyList = new ArrayList<>();

    List<Band> list;
    MyAdapter8 myAdapter;
    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    private EditText editTextPrice8,editTextFirst8,editTextLast8,editTextPhone8;
    private Button addNew8;
    private Button add8;

    public static final String DATABASE_PATH = "Band";

    private StorageReference storageReference;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band);
        firebaseAuth = FirebaseAuth.getInstance();

        listView = (ListView) findViewById(R.id.list9);
        editTextPrice8= (EditText) findViewById(R.id.editTextPrice8);editTextPrice8.setVisibility(View.GONE);
        editTextFirst8= (EditText) findViewById(R.id.editTextFirstName8);editTextFirst8.setVisibility(View.GONE);
        editTextLast8= (EditText) findViewById(R.id.editTextLastName8);editTextLast8.setVisibility(View.GONE);
        editTextPhone8= (EditText) findViewById(R.id.editTextPhone8);editTextPhone8.setVisibility(View.GONE);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);
        add8=(Button) findViewById(R.id.add8);
        addNew8=(Button) findViewById(R.id.addNew8);
        addNew8.setVisibility(View.GONE);
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
        add8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listView.setVisibility(View.GONE);
                add8.setVisibility(View.GONE);
                addNew8.setVisibility(View.VISIBLE);
                editTextPrice8.setVisibility(View.VISIBLE);
                editTextLast8.setVisibility(View.VISIBLE);
                editTextFirst8.setVisibility(View.VISIBLE);
                editTextPhone8.setVisibility(View.VISIBLE);

            }
        });



        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference(BandActivity.DATABASE_PATH);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                list.clear();

                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    keyList.add(snap.getKey());

                    Band band = snap.getValue(Band.class);
                    list.add(band);
                }

                myAdapter = new MyAdapter8 (BandActivity.this,R.layout.data_items3,list);
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

        if(!(TextUtils.isEmpty(editTextFirst8.getText().toString()))&&!(TextUtils.isEmpty(editTextLast8.getText().toString()))&&! (TextUtils.isEmpty(editTextPhone8.getText().toString()))&& !(TextUtils.isEmpty(editTextPrice8.getText().toString())))
        {
            // insert data

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Saving Singer Info..!!");
            progressDialog.show();

            String price = editTextPrice8.getText().toString();
            String first = editTextFirst8.getText().toString();
            String last = editTextLast8.getText().toString();
            int num=Integer.parseInt(last);
            String phone = editTextPhone8.getText().toString();
            double priceDouble=Double.parseDouble(price);

            Band mu = new Band(first,num,phone,priceDouble);

            String id = databaseReference.push().getKey();

            databaseReference.child(id).setValue(mu);

            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "New Band is added", Toast.LENGTH_LONG).show();
        }

        else {
            // show message
            Toast.makeText(getApplicationContext(),"All fields are required!!",Toast.LENGTH_LONG).show();
        }

    }


}






