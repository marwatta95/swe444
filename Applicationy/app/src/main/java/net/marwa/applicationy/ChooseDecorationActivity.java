package net.marwa.applicationy;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChooseDecorationActivity extends AppCompatActivity {
    ListView listView;

    List<Decor> list;
    ProgressDialog progressDialog;
    final ArrayList<String> keyList = new ArrayList<>();
    private DatabaseReference databaseReference;
    public static final String DATABASE_PATH = "Decoration";
    MyAdapterChooseDecor myAdapter;
    private Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_choose_decoration );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        listView=(ListView) findViewById( R.id.list1);


        final Intent intent = getIntent();
        final  Intent intent1=new Intent(ChooseDecorationActivity.this, ChooseFoodActivity.class);
      //  final Bundle bundle1=new Bundle();
  //      final Bundle bundle= getIntent().getExtras();

        final Decor[] decorChosen = new Decor[1];
        //get the info from last activity
        final String type = intent.getExtras().getString( "type" );
        final String date = intent.getExtras().getString( "date" );
        final String guests = intent.getExtras().getString( "guests" );
        final String location = intent.getExtras().getString( "location" );
        final String hallS=intent.getStringExtra("hallS");
        // there is an exception
 //       final Hall hallO=(Hall)(intent.getSerializableExtra("hallO"));
        // show the decorations available to the user
        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference(DecorActivity.DATABASE_PATH);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                list.clear();

                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    keyList.add(snap.getKey());

                    Decor decor = snap.getValue(Decor.class);
                    if(type.equals( decor.type )){
                        list.add(decor);
                    }
                }
                myAdapter = new MyAdapterChooseDecor(ChooseDecorationActivity.this,R.layout.data_items_choose_decor,list);
                listView.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view, "Everything has a place, and everything in its place !!", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        } );
        // get what the user choose and put it in intent
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {

                decorChosen[0] =list.get(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        ChooseDecorationActivity.this );
                alert.setTitle( "Confirm" );
                alert.setMessage( "Are you sure you want this Decoration? " );
                alert.setPositiveButton( "YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    intent1.putExtra( "decorS",(String) decorChosen[0].getImageUri() );
                //        intent1.putExtra("decorO", decorChosen);
                        Toast.makeText( getApplicationContext(), "Chosen Successfully!!!", Toast.LENGTH_LONG ).show();
                        dialog.dismiss();

                    }
                } );
                alert.setNegativeButton( "NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                } );

                alert.show();

            }
        });
        next=(Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                intent1.putExtra( "type", type );
                intent1.putExtra( "date", date );
                intent1.putExtra( "guests", guests );
                intent1.putExtra( "location", location );
               intent1.putExtra( "hallS",hallS );
            //    intent1.putExtra("bundle",bundle1);

                startActivity(intent1);




            }
        });
    }
}
