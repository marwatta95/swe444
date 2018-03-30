package net.marwa.applicationy;
import android.app.ProgressDialog;
import android.support.v4.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PartyActivity extends AppCompatActivity {

    NotificationCompat.Builder notification;
    private static final int uniqueID=3454;

    private  Button confirm;
    private Button cancel;
    private TextView hall;
    private TextView decor;
    private TextView photographer;
    private TextView music;
    private TextView clown;
    private TextView custom;
    private TextView food;
    private TextView makeup;
    private TextView hair;
    private DatabaseReference dr;
    private  Party party;
    private Head yourHead;
    List<Head> list;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

        notification=new NotificationCompat.Builder( this );
        notification.setAutoCancel( true );
// showing the party information

        Intent intent = getIntent();
        final String type1 = intent.getExtras().getString( "type" );
        final String date1 = intent.getExtras().getString( "date" );
        final String guests1 = intent.getExtras().getString( "guests" );
        final String location1 = intent.getExtras().getString( "location" );
        final String hall1 = intent.getExtras().getString( "hall" );
        final String decor1 = intent.getExtras().getString( "decor" );
        final String appetizer1 = intent.getExtras().getString( "appetizer" );
        final String main1 = intent.getExtras().getString( "main" );
        final String dessert1 = intent.getExtras().getString( "dessert" );
        final String cake1 = intent.getExtras().getString( "cake" );
        final String photographer1 = intent.getExtras().getString( "photographer" );
        final String singer1 = intent.getExtras().getString( "singer" );
        final String dj1 = intent.getExtras().getString( "dj" );
        final String band1 = intent.getExtras().getString( "band" );
        final String makeup1 = intent.getExtras().getString( "makeup" );
        final String hair1 = intent.getExtras().getString( "hair" );
        final String clown1=intent.getExtras().getString("clown");

        list = new ArrayList<>();
        //
        final String custom1="";
        clown = (TextView) findViewById(R.id.textViewClown);


        hall = (TextView)findViewById(R.id.textViewHall);
        hall.setText(hall1);

        decor = (TextView)findViewById(R.id.textViewDecor);
        decor.setText(decor1);

        photographer = (TextView)findViewById(R.id.textViewPhoto);
        photographer.setText(photographer1);

        music = (TextView)findViewById(R.id.textViewMusic);
        if(singer1!=null)
            music.setText(singer1);
        else   if(dj1!=null)
            music.setText(dj1);
        else if(band1!=null)
            music.setText(band1);


        food = (TextView)findViewById(R.id.textViewFood);
        if(appetizer1!=null)
            food.setText(appetizer1+"\n"+dessert1+"\n"+main1+"\n"+cake1);


        makeup=(TextView)findViewById(R.id.textViewMakeup);
        makeup.setText(makeup1);

        hair=(TextView)findViewById(R.id.textViewHairDresser);
        hair.setText(hair1);

        if(type1.equals("Birthday")) {
            clown.setText(clown1);
            clown.setVisibility(View.VISIBLE);
        }

            /*    custom = (TextView)findViewById(R.id.textViewCustom);
                custom.setText(custom1, TextView.BufferType.EDITABLE);*/

        // if the user want to cancel the party
        cancel=(Button) findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {

                startActivity(new Intent(getApplicationContext(), UserHomeActivity.class));

            }
        });

       //***************************************************************************************

        dr = FirebaseDatabase.getInstance().getReference(HeadActivity.DATABASE_PATH);
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list.clear();

                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                    Head head = snap.getValue( Head.class );

                    list.add( head );

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // if the user want to confirm the party
        confirm=(Button) findViewById(R.id.confirm);
        confirm.setOnClickListener( new View.OnClickListener()   {
            public void onClick(View v){
                  //
                final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
                progressDialog.setTitle("Saving Party Info..!!");
                progressDialog.show();


                        if((singer1!=null)&&(appetizer1!=null))
                    party= new Party((Hall)getIntent().getSerializableExtra("hall"),(Decor) getIntent().getSerializableExtra("decor"),(Band) getIntent().getSerializableExtra("Band"),(Clown) getIntent().getSerializableExtra("clown"),(Custom) getIntent().getSerializableExtra("custom"),(Dj)getIntent().getSerializableExtra("singer"),(Food) getIntent().getSerializableExtra("appetizer"),(Hair) getIntent().getSerializableExtra("hair"),(Photographer)getIntent().getSerializableExtra("photographer"),(MakeUp)getIntent().getSerializableExtra("makeup"),(Singer) getIntent().getSerializableExtra("singer"));
                        if((singer1!=null)&&(dessert1!=null))
                party= new Party((Hall)getIntent().getSerializableExtra("hall"),(Decor) getIntent().getSerializableExtra("decor"),(Band) getIntent().getSerializableExtra("Band"),(Clown) getIntent().getSerializableExtra("clown"),(Custom) getIntent().getSerializableExtra("custom"),(Dj)getIntent().getSerializableExtra("singer"),(Food) getIntent().getSerializableExtra("dessert"),(Hair) getIntent().getSerializableExtra("hair"),(Photographer)getIntent().getSerializableExtra("photographer"),(MakeUp)getIntent().getSerializableExtra("makeup"),(Singer) getIntent().getSerializableExtra("singer"));
                if((singer1!=null)&&(cake1!=null))
                    party= new Party((Hall)getIntent().getSerializableExtra("hall"),(Decor) getIntent().getSerializableExtra("decor"),(Band) getIntent().getSerializableExtra("Band"),(Clown) getIntent().getSerializableExtra("clown"),(Custom) getIntent().getSerializableExtra("custom"),(Dj)getIntent().getSerializableExtra("singer"),(Food) getIntent().getSerializableExtra("cake"),(Hair) getIntent().getSerializableExtra("hair"),(Photographer)getIntent().getSerializableExtra("photographer"),(MakeUp)getIntent().getSerializableExtra("makeup"),(Singer) getIntent().getSerializableExtra("singer"));
                if((singer1!=null)&&(main1!=null))
                    party= new Party((Hall)getIntent().getSerializableExtra("hall"),(Decor) getIntent().getSerializableExtra("decor"),(Band) getIntent().getSerializableExtra("Band"),(Clown) getIntent().getSerializableExtra("clown"),(Custom) getIntent().getSerializableExtra("custom"),(Dj)getIntent().getSerializableExtra("singer"),(Food) getIntent().getSerializableExtra("main"),(Hair) getIntent().getSerializableExtra("hair"),(Photographer)getIntent().getSerializableExtra("photographer"),(MakeUp)getIntent().getSerializableExtra("makeup"),(Singer) getIntent().getSerializableExtra("singer"));


                if((band1!=null)&&(appetizer1!=null))
                    party= new Party((Hall)getIntent().getSerializableExtra("hall"),(Decor) getIntent().getSerializableExtra("decor"),(Band) getIntent().getSerializableExtra("Band"),(Clown) getIntent().getSerializableExtra("clown"),(Custom) getIntent().getSerializableExtra("custom"),(Dj)getIntent().getSerializableExtra("singer"),(Food) getIntent().getSerializableExtra("appetizer"),(Hair) getIntent().getSerializableExtra("hair"),(Photographer)getIntent().getSerializableExtra("photographer"),(MakeUp)getIntent().getSerializableExtra("makeup"),(Singer) getIntent().getSerializableExtra("singer"));
                if((band1!=null)&&(dessert1!=null))
                    party= new Party((Hall)getIntent().getSerializableExtra("hall"),(Decor) getIntent().getSerializableExtra("decor"),(Band) getIntent().getSerializableExtra("Band"),(Clown) getIntent().getSerializableExtra("clown"),(Custom) getIntent().getSerializableExtra("custom"),(Dj)getIntent().getSerializableExtra("singer"),(Food) getIntent().getSerializableExtra("dessert"),(Hair) getIntent().getSerializableExtra("hair"),(Photographer)getIntent().getSerializableExtra("photographer"),(MakeUp)getIntent().getSerializableExtra("makeup"),(Singer) getIntent().getSerializableExtra("singer"));
                if((band1!=null)&&(cake1!=null))
                    party= new Party((Hall)getIntent().getSerializableExtra("hall"),(Decor) getIntent().getSerializableExtra("decor"),(Band) getIntent().getSerializableExtra("Band"),(Clown) getIntent().getSerializableExtra("clown"),(Custom) getIntent().getSerializableExtra("custom"),(Dj)getIntent().getSerializableExtra("singer"),(Food) getIntent().getSerializableExtra("cake"),(Hair) getIntent().getSerializableExtra("hair"),(Photographer)getIntent().getSerializableExtra("photographer"),(MakeUp)getIntent().getSerializableExtra("makeup"),(Singer) getIntent().getSerializableExtra("singer"));
                if((band1!=null)&&(main1!=null))
                    party= new Party((Hall)getIntent().getSerializableExtra("hall"),(Decor) getIntent().getSerializableExtra("decor"),(Band) getIntent().getSerializableExtra("Band"),(Clown) getIntent().getSerializableExtra("clown"),(Custom) getIntent().getSerializableExtra("custom"),(Dj)getIntent().getSerializableExtra("singer"),(Food) getIntent().getSerializableExtra("main"),(Hair) getIntent().getSerializableExtra("hair"),(Photographer)getIntent().getSerializableExtra("photographer"),(MakeUp)getIntent().getSerializableExtra("makeup"),(Singer) getIntent().getSerializableExtra("singer"));


                if((dj1!=null)&&(appetizer1!=null))
                    party= new Party((Hall)getIntent().getSerializableExtra("hall"),(Decor) getIntent().getSerializableExtra("decor"),(Band) getIntent().getSerializableExtra("Band"),(Clown) getIntent().getSerializableExtra("clown"),(Custom) getIntent().getSerializableExtra("custom"),(Dj)getIntent().getSerializableExtra("singer"),(Food) getIntent().getSerializableExtra("appetizer"),(Hair) getIntent().getSerializableExtra("hair"),(Photographer)getIntent().getSerializableExtra("photographer"),(MakeUp)getIntent().getSerializableExtra("makeup"),(Singer) getIntent().getSerializableExtra("singer"));
                if((dj1!=null)&&(dessert1!=null))
                    party= new Party((Hall)getIntent().getSerializableExtra("hall"),(Decor) getIntent().getSerializableExtra("decor"),(Band) getIntent().getSerializableExtra("Band"),(Clown) getIntent().getSerializableExtra("clown"),(Custom) getIntent().getSerializableExtra("custom"),(Dj)getIntent().getSerializableExtra("singer"),(Food) getIntent().getSerializableExtra("dessert"),(Hair) getIntent().getSerializableExtra("hair"),(Photographer)getIntent().getSerializableExtra("photographer"),(MakeUp)getIntent().getSerializableExtra("makeup"),(Singer) getIntent().getSerializableExtra("singer"));
                if((dj1!=null)&&(cake1!=null))
                    party= new Party((Hall)getIntent().getSerializableExtra("hall"),(Decor) getIntent().getSerializableExtra("decor"),(Band) getIntent().getSerializableExtra("Band"),(Clown) getIntent().getSerializableExtra("clown"),(Custom) getIntent().getSerializableExtra("custom"),(Dj)getIntent().getSerializableExtra("singer"),(Food) getIntent().getSerializableExtra("cake"),(Hair) getIntent().getSerializableExtra("hair"),(Photographer)getIntent().getSerializableExtra("photographer"),(MakeUp)getIntent().getSerializableExtra("makeup"),(Singer) getIntent().getSerializableExtra("singer"));
                if((dj1!=null)&&(main1!=null))
                    party= new Party((Hall)getIntent().getSerializableExtra("hall"),(Decor) getIntent().getSerializableExtra("decor"),(Band) getIntent().getSerializableExtra("Band"),(Clown) getIntent().getSerializableExtra("clown"),(Custom) getIntent().getSerializableExtra("custom"),(Dj)getIntent().getSerializableExtra("singer"),(Food) getIntent().getSerializableExtra("main"),(Hair) getIntent().getSerializableExtra("hair"),(Photographer)getIntent().getSerializableExtra("photographer"),(MakeUp)getIntent().getSerializableExtra("makeup"),(Singer) getIntent().getSerializableExtra("singer"));


                // to the database
                dr= FirebaseDatabase.getInstance().getReference();
                String id = dr.push().getKey();
                dr.child("Party").child(id).setValue(party);
                progressDialog.dismiss();
                Toast.makeText( getApplicationContext(), "your party has been reserved", Toast.LENGTH_LONG ).show();



                int index = new Random().nextInt(list.size());
                yourHead = list.get(index);


                notification.setSmallIcon( R.mipmap.ic_launcher );
                notification.setTicker( "YOUR HEAD " );
                notification.setWhen( System.currentTimeMillis() );

                notification.setContentTitle( "Now one Last step!!" );

                notification.setContentText( "This head is been assigned to help you in your party and makes sure you are fully satisfied \n "+yourHead );

                Intent intent=new Intent(getApplicationContext(),NotificationActivity.class);
                 intent.putExtra( "notification","This head is been assigned to help you in your party and makes sure you are fully satisfied \n "+yourHead.toString() );
                PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent( pendingIntent );
                NotificationManager nm = (NotificationManager) getSystemService( NOTIFICATION_SERVICE );
                nm.notify(uniqueID, notification.build());






            }});




    }
}

