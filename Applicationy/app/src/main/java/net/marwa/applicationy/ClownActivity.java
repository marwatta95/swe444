package net.marwa.applicationy;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.MenuPopupWindow;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class ClownActivity extends AppCompatActivity {
    ListView listView;
    final ArrayList<String> keyList = new ArrayList<>();

    List<Clown> list;
    MyAdapter9 myAdapter;
    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    ImageView imageView;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    private EditText editTextName9,editTextPrice9;
    private Button addNew9;
    private Button add9;

    private Button upload9;

    public static final String STORAGE_PATH = "ClownsImages/";
    public static final String DATABASE_PATH = "Clowns";
    private Uri imageUri;

    private StorageReference storageReference;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clown);
        firebaseAuth = FirebaseAuth.getInstance();

        listView = (ListView) findViewById(R.id.list10);
        imageView = (ImageView) findViewById(R.id.insertImages9);
        imageView.setVisibility(View.GONE);
        editTextName9 = (EditText) findViewById(R.id.editTextName9);editTextName9.setVisibility(View.GONE);
        editTextPrice9= (EditText) findViewById(R.id.editTextPrice9);editTextPrice9.setVisibility(View.GONE);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);
        add9=(Button) findViewById(R.id.add9);
        addNew9=(Button) findViewById(R.id.addNew9);
        addNew9.setVisibility(View.GONE);
        upload9=(Button) findViewById(R.id.uploadImage9);upload9.setVisibility(View.GONE);
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
        add9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listView.setVisibility(View.GONE);
                add9 .setVisibility(View.GONE);
                addNew9.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                editTextName9.setVisibility(View.VISIBLE);
                editTextPrice9.setVisibility(View.VISIBLE);
                upload9.setVisibility(View.VISIBLE);
            }
        });



        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference(ClownActivity.DATABASE_PATH);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                list.clear();

                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    keyList.add(snap.getKey());

                    Clown clown = snap.getValue(Clown.class);
                    list.add(clown);
                }
                myAdapter = new MyAdapter9(ClownActivity.this,R.layout.data_items1,list);
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
    public void browseImages(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK){
            imageUri = data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getActualImage(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void uploadData(View view){

        if(imageUri != null &&  !(TextUtils.isEmpty(editTextName9.getText().toString())) &&  !(TextUtils.isEmpty(editTextPrice9.getText().toString()))){
            // insert data

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Saving Clown Info..!!");
            progressDialog.show();

            StorageReference reference = storageReference.child(STORAGE_PATH + System.currentTimeMillis() + "." + getActualImage(imageUri));

            final StorageTask<UploadTask.TaskSnapshot> taskSnapshotStorageTask = reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String NAME = editTextName9.getText().toString();
                    String price = editTextPrice9.getText().toString();
                    double priceDouble=Double.parseDouble(price);



                    Clown hall = new Clown(NAME,priceDouble, taskSnapshot.getDownloadUrl().toString());

                    String id = databaseReference.push().getKey();

                    databaseReference.child(id).setValue(hall);

                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "New Clown is added", Toast.LENGTH_LONG).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @SuppressWarnings("VisibleForTests")
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double totalProgress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploading % " + (int) totalProgress);
                        }
                    });


        } else {
            // show message
            Toast.makeText(getApplicationContext(),"All fields are required!!",Toast.LENGTH_LONG).show();
        }

    }


}






