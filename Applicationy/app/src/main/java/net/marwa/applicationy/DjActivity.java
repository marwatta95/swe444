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

public class DjActivity extends AppCompatActivity {
    ListView listView;
    final ArrayList<String> keyList = new ArrayList<>();

    List<Dj> list;
    MyAdapter4 myAdapter;
    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    private EditText editTextPrice4,editTextFirst4,editTextLast4,editTextPhone4;
    private Button addNew4;
    private Button add4;
    private Button upload4;

    public static final String DATABASE_PATH = "Dj";
    private Uri imageUri;

    private StorageReference storageReference;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dj);
        firebaseAuth = FirebaseAuth.getInstance();

        listView = (ListView) findViewById(R.id.list5);
        editTextPrice4= (EditText) findViewById(R.id.editTextPrice4);editTextPrice4.setVisibility(View.GONE);
        editTextFirst4= (EditText) findViewById(R.id.editTextFirstName4);editTextFirst4.setVisibility(View.GONE);
        editTextLast4= (EditText) findViewById(R.id.editTextLastName4);editTextLast4.setVisibility(View.GONE);
        editTextPhone4= (EditText) findViewById(R.id.editTextPhone4);editTextPhone4.setVisibility(View.GONE);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);
        add4=(Button) findViewById(R.id.add4);
        addNew4=(Button) findViewById(R.id.addNew4);
        addNew4.setVisibility(View.GONE);
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
        add4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listView.setVisibility(View.GONE);
                add4.setVisibility(View.GONE);
                addNew4.setVisibility(View.VISIBLE);
                editTextPrice4.setVisibility(View.VISIBLE);
                editTextLast4.setVisibility(View.VISIBLE);
                editTextFirst4.setVisibility(View.VISIBLE);
                editTextPhone4.setVisibility(View.VISIBLE);

            }
        });



        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference(DjActivity.DATABASE_PATH);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                list.clear();
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    keyList.add(snap.getKey());

                    Dj dj = snap.getValue(Dj.class);
                    list.add(dj);
                }

                myAdapter = new MyAdapter4(DjActivity.this,R.layout.data_items3,list);
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

        if(!(TextUtils.isEmpty(editTextFirst4.getText().toString()))&&!(TextUtils.isEmpty(editTextLast4.getText().toString()))&&! (TextUtils.isEmpty(editTextPhone4.getText().toString()))&& !(TextUtils.isEmpty(editTextPrice4.getText().toString())))
        {
            // insert data

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Saving Dj Info..!!");
            progressDialog.show();

            String price = editTextPrice4.getText().toString();
            String first = editTextFirst4.getText().toString();
            String last = editTextLast4.getText().toString();
            String phone = editTextPhone4.getText().toString();
            double priceDouble=Double.parseDouble(price);

            Dj dj = new Dj(first,last,phone,priceDouble);

            String id = databaseReference.push().getKey();

            databaseReference.child(id).setValue(dj);

            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "New DJ is added", Toast.LENGTH_LONG).show();
        }

        else {
            // show message
            Toast.makeText(getApplicationContext(),"All fields are required!!",Toast.LENGTH_LONG).show();
        }

    }


}






