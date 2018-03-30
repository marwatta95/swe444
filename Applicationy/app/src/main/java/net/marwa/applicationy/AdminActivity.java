package net.marwa.applicationy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    ImageButton hall;
    ImageButton decoration;
    ImageButton food;
    ImageButton photographer;
    ImageButton dj;
    ImageButton band;
    ImageButton singer;
    ImageButton hair;
    ImageButton makeup;
    ImageButton clown;
    ImageButton invitation;
    ImageButton custom;
    private Button buttonLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        hall =(ImageButton)findViewById(R.id.hall);
        hall.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    startActivity(new Intent(getApplicationContext(), HallActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        decoration =(ImageButton)findViewById(R.id.decoration);
        decoration.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    startActivity(new Intent(getApplicationContext(), DecorActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        food =(ImageButton)findViewById(R.id.food);
        food.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    startActivity(new Intent(getApplicationContext(), FoodActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        photographer =(ImageButton)findViewById(R.id.photographer);
        photographer.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    startActivity(new Intent(getApplicationContext(), PhotoActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dj =(ImageButton)findViewById(R.id.dj);
        dj.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    startActivity(new Intent(getApplicationContext(), DjActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        band =(ImageButton)findViewById(R.id.band);
        band.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    startActivity(new Intent(getApplicationContext(), BandActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        singer =(ImageButton)findViewById(R.id.singer);
        singer.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    startActivity(new Intent(getApplicationContext(), SingerActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        hair =(ImageButton)findViewById(R.id.hair);
        hair.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    startActivity(new Intent(getApplicationContext(), HairActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        makeup =(ImageButton)findViewById(R.id.makeup);
        makeup.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    startActivity(new Intent(getApplicationContext(), MakeupActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        clown =(ImageButton)findViewById(R.id.clown);
        clown.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    startActivity(new Intent(getApplicationContext(), ClownActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        invitation =(ImageButton)findViewById(R.id.invitation);
        invitation.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    startActivity(new Intent(getApplicationContext(), InvitationActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        custom =(ImageButton)findViewById(R.id.custom);
        custom.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    startActivity(new Intent(getApplicationContext(), CustomActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        buttonLogout =(Button)findViewById(R.id.buttonLogout);
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
    }
}

