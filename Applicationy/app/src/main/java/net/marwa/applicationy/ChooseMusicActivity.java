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

                final  Intent intent=new Intent(ChooseMusicActivity.this, ChooseDjActivity.class);
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
                intent.putExtra("photographer",photographer);
                startActivity(intent);




            }
        });

        band=(Button) findViewById(R.id.buttonBand);
        band.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final  Intent intent=new Intent(ChooseMusicActivity.this, ChooseBandActivity.class);
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
                intent.putExtra("photographer",photographer);
                startActivity(intent);




            }
        });
        singer=(Button) findViewById(R.id.buttonSinger);
        singer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final  Intent intent=new Intent(ChooseMusicActivity.this, ChooseSingerActivity.class);
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
                intent.putExtra("photographer",photographer);
                startActivity(intent);




            }
        });

    }




    }

