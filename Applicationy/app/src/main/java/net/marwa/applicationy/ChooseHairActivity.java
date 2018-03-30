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

public class ChooseHairActivity extends AppCompatActivity {
    ListView listView;
    List<Hair> list;
    ProgressDialog progressDialog;
    final ArrayList<String> keyList = new ArrayList<>();
    private DatabaseReference databaseReference;
    MyadapterChooseHair myAdapter;
    private Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_choose_hair);
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
                    if(!hair.dates.contains(date)){
                        list.add(hair);
                    }


                }
                myAdapter = new MyadapterChooseHair(ChooseHairActivity.this,R.layout.data_items_choose_hair,list);
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
                Snackbar.make( view, "Your hair is 90% of your selfie !! Be careful !!", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        } );

        next=(Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final  Intent intent;
                if(type.equals( "Birthday" ))

                    intent =new Intent(ChooseHairActivity.this, ChooseClownActivity.class);
                else
                    intent =new Intent(ChooseHairActivity.this, ChooseCustomActivity.class);

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




                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(
                                ChooseHairActivity.this );
                        alert.setTitle( "Confirm" );
                        alert.setMessage( "Are you sure you want this? " );
                        alert.setPositiveButton( "YES", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                intent.putExtra( "hair", databaseReference.getRoot().child( HairActivity.DATABASE_PATH ).child( keyList.get( position ) ).getClass().toString() );
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
