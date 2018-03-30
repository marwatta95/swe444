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
import android.widget.ArrayAdapter;
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

public class HallActivity extends AppCompatActivity {
    ListView listView;
    final ArrayList<String> keyList = new ArrayList<>();

    List<Hall> list;
    MyAdapter myAdapter;
    ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    ImageView imageView;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    private EditText editTextName,editTextDes,editTextAddress,editTextPrice,editTextCapacity;
    private Button addNew;
    private Button add;
    private Spinner spinner;
    private Button upload;

    public static final String STORAGE_PATH = "HallsImages/";
    public static final String DATABASE_PATH = "Halls";
    private Uri imageUri;
private TextView textLocation;
    private StorageReference storageReference;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);
        firebaseAuth = FirebaseAuth.getInstance();
        spinner = (Spinner) findViewById(R.id.street);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.street, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setVisibility(View.GONE );
        listView = (ListView) findViewById(R.id.list1);
        imageView = (ImageView) findViewById(R.id.insertImages);
        imageView.setVisibility(View.GONE);
        textLocation=(TextView) findViewById( R.id.textLocation);textLocation.setVisibility( View.GONE );
        editTextName = (EditText) findViewById(R.id.editTextName);editTextName.setVisibility(View.GONE);
        editTextDes = (EditText) findViewById(R.id.editTextDes);editTextDes.setVisibility(View.GONE);
        editTextAddress= (EditText) findViewById(R.id.editTextAddress);editTextAddress.setVisibility(View.GONE);
        editTextPrice= (EditText) findViewById(R.id.editTextPrice);editTextPrice.setVisibility(View.GONE);
        editTextCapacity= (EditText) findViewById(R.id.editTextCapacity);editTextCapacity.setVisibility(View.GONE);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);
        add=(Button) findViewById(R.id.add);
        addNew=(Button) findViewById(R.id.addNew);
        addNew.setVisibility(View.GONE);
        upload=(Button) findViewById(R.id.uploadImage);upload.setVisibility(View.GONE);
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
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listView.setVisibility(View.GONE);
                add .setVisibility(View.GONE);
                addNew.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                editTextName.setVisibility(View.VISIBLE);
                editTextDes.setVisibility(View.VISIBLE);
                editTextAddress.setVisibility(View.VISIBLE);
                editTextPrice.setVisibility(View.VISIBLE);
                editTextCapacity.setVisibility(View.VISIBLE);
                upload.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE );
                textLocation.setVisibility(View.VISIBLE );
            }
        });



        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference(HallActivity.DATABASE_PATH);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                list.clear();

                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    keyList.add(snap.getKey());

                    Hall hall = snap.getValue(Hall.class);
                    list.add(hall);
                }
                myAdapter = new MyAdapter(HallActivity.this,R.layout.data_items,list);
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


        if(imageUri != null &&  !(TextUtils.isEmpty(editTextAddress.getText().toString())) &&  !(TextUtils.isEmpty(editTextCapacity.getText().toString())) &&  !(TextUtils.isEmpty(editTextDes.getText().toString())) &&  !(TextUtils.isEmpty(editTextName.getText().toString())) &&  !(TextUtils.isEmpty(editTextPrice.getText().toString())))
        {
            // insert data

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Saving Hall Info..!!");
            progressDialog.show();

            StorageReference reference = storageReference.child(STORAGE_PATH + System.currentTimeMillis() + "." + getActualImage(imageUri));

            final StorageTask<UploadTask.TaskSnapshot> taskSnapshotStorageTask = reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String NAME = editTextName.getText().toString();
                    String des = editTextDes.getText().toString();
                    String address = editTextAddress.getText().toString();
                    String price = editTextPrice.getText().toString();
                    double priceDouble=Double.parseDouble(price);
                    String capacity = editTextCapacity.getText().toString();
                    int capacityint=Integer.parseInt(capacity);
                    String location = spinner.getSelectedItem().toString();


                    Hall hall = new Hall(NAME,des,address,capacityint,priceDouble, taskSnapshot.getDownloadUrl().toString(),location);

                    String id = databaseReference.push().getKey();

                    databaseReference.child(id).setValue(hall);

                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "New hall is added", Toast.LENGTH_LONG).show();
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
            Toast.makeText(getApplicationContext(),"All fields are required!! ",Toast.LENGTH_LONG).show();
        }

    }


}






