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

public class ChooseHallActivity extends AppCompatActivity {
    ListView listView;

    List<Hall> list;
    ProgressDialog progressDialog;
    final ArrayList<String> keyList = new ArrayList<>();
    private DatabaseReference databaseReference;
    public static final String DATABASE_PATH = "Halls";
    MyAdapterChooseHall myAdapter;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_choose_hall2 );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        listView=(ListView) findViewById( R.id.list1);


        Intent intent = getIntent();
        final  Intent intent1=new Intent(ChooseHallActivity.this, ChooseDecorationActivity.class);
        final Bundle bundle=new Bundle();
        //**************************************
        final Hall[] hallChosen = new Hall[1];
        final String type = intent.getExtras().getString( "type" );
        final String date = intent.getExtras().getString( "date" );
        final String guests = intent.getExtras().getString( "guests" );
        final String location = intent.getExtras().getString( "location" );


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
              // if(location.equals( hall.location )) {
                    int guest = Integer.parseInt( guests );
                    if ((hall.capacity >= guest) && (hall.capacity <= guest + 50)) {
                        if (!hall.dates.contains( date ))
                            list.add( hall );

                }
                }
                myAdapter = new MyAdapterChooseHall(ChooseHallActivity.this,R.layout.data_items_choose_hall,list);
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
                Snackbar.make( view, "Some people look for a beautiful space.others make a place beautiful !!", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        } );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                //**********************************************************************
                hallChosen[0] =list.get(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        ChooseHallActivity.this );
                alert.setTitle( "Confirm" );
                alert.setMessage( "Are you sure you want this hall? " );
                alert.setPositiveButton( "YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //*********************************************************
                       intent1.putExtra( "hallS",(String) hallChosen[0].getName() );
           //            intent1.putExtra("bundle",bundle);
                     //   intent1.putExtra("hallO", hallChosen);
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

                startActivity(intent1);




            }
        });
    }
}
