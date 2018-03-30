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

import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class PhotoActivity extends AppCompatActivity {
    ListView listView;
    final ArrayList<String> keyList = new ArrayList<>();

    List<Photographer> list;
    MyAdapter3 myAdapter;
    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    private EditText editTextPrice3,editTextFirst3,editTextLast3,editTextPhone3;
    private Button addNew3;
    private Button add3;
    private Button upload3;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;


    public static final String DATABASE_PATH = "Photographer";
    private Uri imageUri;

    private StorageReference storageReference;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        firebaseAuth = FirebaseAuth.getInstance();

        listView = (ListView) findViewById(R.id.list4);
        editTextPrice3= (EditText) findViewById(R.id.editTextPrice3);editTextPrice3.setVisibility(View.GONE);
        editTextFirst3= (EditText) findViewById(R.id.editTextFirstName3);editTextFirst3.setVisibility(View.GONE);
        editTextLast3= (EditText) findViewById(R.id.editTextLastName3);editTextLast3.setVisibility(View.GONE);
        editTextPhone3= (EditText) findViewById(R.id.editTextPhone3);editTextPhone3.setVisibility(View.GONE);
        radioSexGroup=(RadioGroup) findViewById(R.id.radioSex);radioSexGroup.setVisibility( View.GONE );

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);
        add3=(Button) findViewById(R.id.add3);
        addNew3=(Button) findViewById(R.id.addNew3);
        addNew3.setVisibility(View.GONE);
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
        add3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listView.setVisibility(View.GONE);
                add3 .setVisibility(View.GONE);
                addNew3.setVisibility(View.VISIBLE);
                editTextPrice3.setVisibility(View.VISIBLE);
                editTextLast3.setVisibility(View.VISIBLE);
                editTextFirst3.setVisibility(View.VISIBLE);
                editTextPhone3.setVisibility(View.VISIBLE);
                radioSexGroup.setVisibility( View.VISIBLE );

            }
        });



        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference(PhotoActivity.DATABASE_PATH);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                list.clear();

                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    keyList.add(snap.getKey());

                    Photographer photog = snap.getValue(Photographer.class);
                    list.add(photog);
                }
                myAdapter = new MyAdapter3(PhotoActivity.this,R.layout.data_items3,list);
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

        if(!(TextUtils.isEmpty(editTextFirst3.getText().toString()))&&!(TextUtils.isEmpty(editTextPhone3.getText().toString())) &&!(TextUtils.isEmpty(editTextLast3.getText().toString()))&&!(TextUtils.isEmpty(editTextPrice3.getText().toString())))
        {
            // insert data

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Saving photographer Info..!!");
            progressDialog.show();

            String price = editTextPrice3.getText().toString();
            String first = editTextFirst3.getText().toString();
            String last = editTextLast3.getText().toString();
            String phone = editTextPhone3.getText().toString();
            double priceDouble=Double.parseDouble(price);
            int selectedId = radioSexGroup.getCheckedRadioButtonId();

            radioSexButton = (RadioButton) findViewById(selectedId);

            String gender= radioSexButton.getText().toString();
            Photographer photog = new Photographer(first,last,phone,priceDouble,gender);

                    String id = databaseReference.push().getKey();

                    databaseReference.child(id).setValue(photog);

                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "New photographer is added", Toast.LENGTH_LONG).show();
                }

     else {
            // show message
            Toast.makeText(getApplicationContext(),"All fields are required!!",Toast.LENGTH_LONG).show();
        }

    }


}






