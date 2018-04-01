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
   Hall h;
Hall hallO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

        notification=new NotificationCompat.Builder( this,"default" );
        notification.setAutoCancel( true );
// showing the party information

        Intent intent = getIntent();
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
        final String singerS = intent.getStringExtra( "singerS" );
        final String djS = intent.getStringExtra( "djS" );
        final String bandS =  intent.getStringExtra( "bandS" );
        final String makeupS = intent.getStringExtra( "makeupS" );
        final String hairS = intent.getStringExtra( "hairS" );
        final String clownS=intent.getStringExtra("clownS");
        final String customS=intent.getStringExtra("customS");

        list = new ArrayList<>();
        hall = (TextView)findViewById(R.id.textViewHall);
        decor = (TextView)findViewById(R.id.textViewDecor);
        food = (TextView)findViewById(R.id.textViewFood);
        photographer = (TextView)findViewById(R.id.textViewPhoto);
        clown = (TextView) findViewById(R.id.textViewClown);
        music = (TextView)findViewById(R.id.textViewMusic);
        makeup=(TextView)findViewById(R.id.textViewMakeup);
        hair=(TextView)findViewById(R.id.textViewHairDresser);
        custom = (TextView)findViewById(R.id.textViewCustom);
        clown.setVisibility(View.INVISIBLE);
if(hallS!=null)
   hall.setText("Your hall is: "+hallS);
else hall.setVisibility(View.INVISIBLE);
if(decorS!=null)
   decor.setText("Your decoration is: "+decorS);
else decor.setVisibility(View.INVISIBLE);

String food1="";
     if(appetizerS!=null)
         food1="You choose : "+appetizerS;
     if(dessertS!=null)
         food1+=" , "+dessertS;
     if(mainS!=null)
         food1+= " , "+mainS;
     if(cakeS!=null)
         food1+=" , "+cakeS;
     if(food1!="")
      food.setText(food1);
     else food.setVisibility(View.INVISIBLE);

        if(photographerS!=null)
     photographer.setText("Your photographer is: "+photographerS);
        else photographer.setVisibility(View.INVISIBLE);

        if(makeupS!=null)
        makeup.setText("Your makeup artist is: "+makeupS);
        else makeup.setVisibility(View.INVISIBLE);

        if(hairS!=null)
        hair.setText("Your hair dresser is: "+hairS);
        else hair.setVisibility(View.INVISIBLE);

        if(customS!=null)
        custom.setText("Your custom is: "+customS);
        else custom.setVisibility(View.INVISIBLE);



        // set text for music
        String mucis1="";
        if(singerS!=null)
            mucis1="singer: "+singerS;
         if(djS!=null)
           mucis1+="  Dj: "+djS;
         if(bandS!=null)
             mucis1+="  Band: "+bandS;
         if(mucis1!="")
            music.setText(mucis1);
         else music.setVisibility(View.INVISIBLE);

        // set text for clown
        if(type.equals("Birthday")) {
            if(clownS!=null)
            clown.setText("Your Clown is: "+clownS);
            clown.setVisibility(View.VISIBLE);
        }

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
                final ProgressDialog progressDialog = new ProgressDialog(PartyActivity.this);
                progressDialog.setTitle("Saving Party Info..!!");
                progressDialog.show();
                if(appetizerS!=null)
             party=new Party(hallS,decorS,bandS,clownS,customS,djS,appetizerS ,hairS,photographerS,makeupS,singerS);
            else    if(mainS!=null)
                    party=new Party(hallS,decorS,bandS,clownS,customS,djS,mainS ,hairS,photographerS,makeupS,singerS);
            else    if(cakeS!=null)
                    party=new Party(hallS,decorS,bandS,clownS,customS,djS,cakeS ,hairS,photographerS,makeupS,singerS);
           else     if(dessertS!=null)
                    party=new Party(hallS,decorS,bandS,clownS,customS,djS,dessertS ,hairS,photographerS,makeupS,singerS);
           else
                    party=new Party(hallS,decorS,bandS,clownS,customS,djS,"No food" ,hairS,photographerS,makeupS,singerS);

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




        /*    @Override
            public void onDataChange(DataSnapshot dataSnapshot) {








               for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String name = (String) messageSnapshot.child("name").getValue();
                    String message = (String) messageSnapshot.child("message").getValue();
                }
            }

        */



           /*
        hall.setText(hallS);
   //  final  Hall[] h;
     //   h = (Hall[])intent.getSerializableExtra("hallO");
     /*   final String type1 = intent.getExtras().getString( "type" );
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
        final String custom1=intent.getExtras().getString("custom");
        final Hall hallO=(Hall)intent.getSerializableExtra("hallO");
        final Decor decorO=(Decor)intent.getSerializableExtra("decorO");
        final Food appetizerO=(Food)intent.getSerializableExtra("appetizerO");
        final Food mainO=(Food)intent.getSerializableExtra("mainO");
        final Food dessertO=(Food)intent.getSerializableExtra("dessertO");
        final Food cakeO=(Food)intent.getSerializableExtra("cakeO");
        final Photographer photographerO=(Photographer)intent.getSerializableExtra("photographerO");
        final Dj  djO=(Dj)intent.getSerializableExtra("djO");
        final Band  bandO=(Band) intent.getSerializableExtra("bandO");
        final Singer  singerO=(Singer) intent.getSerializableExtra("singerO");
        final MakeUp makeupO=(MakeUp)intent.getSerializableExtra("makeupO");
        final Hair hairO=(Hair)intent.getSerializableExtra("hairO");
        final Clown clownO=(Clown)intent.getSerializableExtra("clownO");
        final Custom customO=(Custom)intent.getSerializableExtra("customO");
*/







/*
pp p;
                    //    if((singer1!=null)&&(appetizer1!=null))
                     p= new pp(hallS,decorS);
    /*                    if((singer1!=null)&&(dessert1!=null))
                party= new Party((Hall)getIntent().getSerializableExtra("hall"),(Decor) getIntent().getSerializableExtra("decorO"),(Band) getIntent().getSerializableExtra("BandO"),(Clown) getIntent().getSerializableExtra("clownO"),(Custom) getIntent().getSerializableExtra("customO"),(Dj)getIntent().getSerializableExtra("singerO"),(Food) getIntent().getSerializableExtra("dessertO"),(Hair) getIntent().getSerializableExtra("hairO"),(Photographer)getIntent().getSerializableExtra("photographer"),(MakeUp)getIntent().getSerializableExtra("makeup"),(Singer) getIntent().getSerializableExtra("singer"));
                if((singer1!=null)&&(cake1!=null))
                    party= new Party((Hall)getIntent().getSerializableExtra("hall"),(Decor) getIntent().getSerializableExtra("decorO"),(Band) getIntent().getSerializableExtra("BandO"),(Clown) getIntent().getSerializableExtra("clownO"),(Custom) getIntent().getSerializableExtra("customO"),(Dj)getIntent().getSerializableExtra("singerO"),(Food) getIntent().getSerializableExtra("cakeO"),(Hair) getIntent().getSerializableExtra("hairO"),(Photographer)getIntent().getSerializableExtra("photographer"),(MakeUp)getIntent().getSerializableExtra("makeup"),(Singer) getIntent().getSerializableExtra("singer"));
                if((singer1!=null)&&(main1!=null))
                    party= new Party((Hall)getIntent().getSerializableExtra("hall"),(Decor) getIntent().getSerializableExtra("decorO"),(Band) getIntent().getSerializableExtra("BandO"),(Clown) getIntent().getSerializableExtra("clownO"),(Custom) getIntent().getSerializableExtra("customO"),(Dj)getIntent().getSerializableExtra("singerO"),(Food) getIntent().getSerializableExtra("mainO"),(Hair) getIntent().getSerializableExtra("hairO"),(Photographer)getIntent().getSerializableExtra("photographer"),(MakeUp)getIntent().getSerializableExtra("makeup"),(Singer) getIntent().getSerializableExtra("singer"));
*/
/*
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

*/





    }
}

