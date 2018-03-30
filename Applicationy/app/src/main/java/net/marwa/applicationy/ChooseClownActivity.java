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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChooseClownActivity extends AppCompatActivity {
    ListView listView;
    List<Clown> list;
    ProgressDialog progressDialog;
    final ArrayList<String> keyList = new ArrayList<>();
    private DatabaseReference databaseReference;
    MyAdapterChooseClown myAdapter;
    private Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_choose_clown);
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        listView=(ListView) findViewById( R.id.list1);


        Intent intent = getIntent();
        final String type = intent.getExtras().getString( "type" );
        final String date = intent.getExtras().getString( "date" );
        final String guests = intent.getExtras().getString( "guests" );
        final String location = intent.getExtras().getString( "location" );
        final String hall = intent.getExtras().getString( "hall" );
        final String decor = intent.getExtras().getString( "decor" );
        final String appetizer = intent.getExtras().getString( "appetizer" );
        final String main = intent.getExtras().getString( "main" );
        final String dessert = intent.getExtras().getString( "dessert" );
        final String cake = intent.getExtras().getString( "cake" );
        final String photographer = intent.getExtras().getString( "photographer" );
        final String singer = intent.getExtras().getString( "singer" );
        final String dj = intent.getExtras().getString( "dj" );
        final String band = intent.getExtras().getString( "band" );
        final String makeup = intent.getExtras().getString( "makeup" );
        final String hair = intent.getExtras().getString( "hair" );



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
                    if(!clown.dates.contains(date)){
                        list.add(clown);
                    }


                }
                myAdapter = new MyAdapterChooseClown(ChooseClownActivity.this,R.layout.data_items_choose_clown,list);
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
                Snackbar.make( view, "WELL !! you can always be a clown !!", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        } );

        next=(Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final  Intent intent=new Intent(ChooseClownActivity.this, PartyActivity.class);
                intent.putExtra( "type", type );
                intent.putExtra( "date", date );
                intent.putExtra( "guests", guests );
                intent.putExtra( "location", location );
                intent.putExtra( "hall", hall );
                intent.putExtra( "decor", decor );
                intent.putExtra( "appetizer", appetizer );
                intent.putExtra( "main", main );
                intent.putExtra( "dessert", dessert );
                intent.putExtra( "cake", cake );
                intent.putExtra( "photographer", photographer );
                intent.putExtra( "singer", singer );
                intent.putExtra( "dj", dj );
                intent.putExtra( "band", band );
                intent.putExtra( "makeup", makeup );
                intent.putExtra( "hair", hair );




                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(
                                ChooseClownActivity.this );
                        alert.setTitle( "Confirm" );
                        alert.setMessage( "Are you sure you want this? " );
                        alert.setPositiveButton( "YES", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                intent.putExtra( "clown", databaseReference.getRoot().child( ClownActivity.DATABASE_PATH ).child( keyList.get( position ) ).getClass().toString() );
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




                startActivity(intent);




            }
        });
    }

}
