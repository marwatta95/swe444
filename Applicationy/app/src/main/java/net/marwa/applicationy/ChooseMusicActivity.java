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

public class ChooseMusicActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    private DatabaseReference databaseReference;

    private Button dj;
    private Button band;
    private Button singer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_choose_music);
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );


        Intent intent = getIntent();
        final  Intent intent1=new Intent(ChooseMusicActivity.this, ChooseBandActivity.class);
        final  Intent intent2=new Intent(ChooseMusicActivity.this, ChooseDjActivity.class);
        final  Intent intent3=new Intent(ChooseMusicActivity.this, ChooseSingerActivity.class);
        final String type = intent.getExtras().getString( "type" );
        final String date = intent.getExtras().getString( "date" );
        final String guests = intent.getExtras().getString( "guests" );
        final String location = intent.getExtras().getString( "location" );
        final String hallS=intent.getStringExtra("hallS");
        final String decorS=intent.getStringExtra("decorS");
        final String appetizerS = intent.getStringExtra( "appetizerS" );
        final String mainS = intent.getStringExtra( "mainS" );
        final String dessertS = intent.getStringExtra( "dessertS" );
        final String cakeS = intent.getStringExtra( "cakeS" );
        final String photographerS = intent.getStringExtra("photoS" );


     /*   final Hall hallO=(Hall)intent.getSerializableExtra("hallO");
        final Decor decorO=(Decor)intent.getSerializableExtra("decorO");
        final Food appetizerO=(Food)intent.getSerializableExtra("appetizerO");
        final Food mainO=(Food)intent.getSerializableExtra("mainO");
        final Food dessertO=(Food)intent.getSerializableExtra("dessertO");
        final Food cakeO=(Food)intent.getSerializableExtra("cakeO");
        final Photographer photographerO=(Photographer)intent.getSerializableExtra("photographerO");*/







        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view, "When words fail, music speaks !!", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        } );


        dj=(Button) findViewById(R.id.buttonDj);
        dj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                intent2.putExtra( "type", type );
                intent2.putExtra( "date", date );
                intent2.putExtra( "guests", guests );
                intent2.putExtra( "location", location );
                intent2.putExtra( "hallS", hallS );
                intent2.putExtra( "decorS", decorS );
                intent2.putExtra( "appetizerS", appetizerS );
                intent2.putExtra( "mainS", mainS );
                intent2.putExtra( "dessertS", dessertS );
                intent2.putExtra( "cakeS", cakeS );
                intent2.putExtra("photoS",photographerS);
             /*   intent2.putExtra("hallO",hallO);
                intent2.putExtra("decorO",decorO);
                intent2.putExtra( "appetizerO", appetizerO );
                intent2.putExtra( "mainO", mainO );
                intent2.putExtra( "dessertO", dessertO );
                intent2.putExtra( "cakeO", cakeO );
                intent2.putExtra("photographerO",photographerO);*/
                startActivity(intent2);




            }
        });

        band=(Button) findViewById(R.id.buttonBand);
        band.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                intent1.putExtra( "type", type );
                intent1.putExtra( "date", date );
                intent1.putExtra( "guests", guests );
                intent1.putExtra( "location", location );
                intent1.putExtra( "hallS", hallS );
                intent1.putExtra( "decorS", decorS );
                intent1.putExtra( "appetizerS", appetizerS );
                intent1.putExtra( "mainS", mainS );
                intent1.putExtra( "dessertS", dessertS );
                intent1.putExtra( "cakeS", cakeS );
                intent1.putExtra("photoS",photographerS);
        /*        intent1.putExtra("hallO",hallO);
                intent1.putExtra("decorO",decorO);
                intent1.putExtra( "appetizerO", appetizerO );
                intent1.putExtra( "mainO", mainO );
                intent1.putExtra( "dessertO", dessertO );
                intent1.putExtra( "cakeO", cakeO );
                intent1.putExtra("photographerO",photographerO);*/


                startActivity(intent1);




            }
        });
        singer=(Button) findViewById(R.id.buttonSinger);
        singer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                intent3.putExtra( "type", type );
                intent3.putExtra( "date", date );
                intent3.putExtra( "guests", guests );
                intent3.putExtra( "location", location );
                intent3.putExtra( "hallS", hallS );
                intent3.putExtra( "decorS", decorS );
                intent3.putExtra( "appetizerS", appetizerS );
                intent3.putExtra( "mainS", mainS );
                intent3.putExtra( "dessertS", dessertS );
                intent3.putExtra( "cakeS", cakeS );
                intent3.putExtra("photoS",photographerS);
           /*     intent3.putExtra("hallO",hallO);
                intent3.putExtra("decorO",decorO);
                intent3.putExtra( "appetizerO", appetizerO );
                intent3.putExtra( "mainO", mainO );
                intent3.putExtra( "dessertO", dessertO );
                intent3.putExtra( "cakeO", cakeO );
                intent3.putExtra("photographerO",photographerO);*/

                startActivity(intent3);




            }
        });

    }




    }

