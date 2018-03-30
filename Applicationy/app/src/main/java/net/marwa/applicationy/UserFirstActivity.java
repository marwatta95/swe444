package net.marwa.applicationy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UserFirstActivity extends AppCompatActivity {

    private EditText editTextguest;
    private TextView textParty,textType,textDate;
    private Button letsParty;
    private RadioGroup radioType;
    private RadioButton radioTypeButton;
    private DatePicker datePicker;
private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_first);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         spinner = (Spinner) findViewById(R.id.street);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.street, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        editTextguest=(EditText) findViewById( R.id.editTextguest );
        textDate=(TextView) findViewById(R.id.textDate);
        textParty=(TextView) findViewById(R.id.textParty);
        textType=(TextView) findViewById(R.id.textType);
        editTextguest= (EditText) findViewById(R.id.editTextguest);
        radioType=(RadioGroup) findViewById(R.id.radioType);
        letsParty=(Button) findViewById(R.id.letsParty);
        letsParty.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            if( !(TextUtils.isEmpty(editTextguest.getText().toString()))) {
                int selectedId = radioType.getCheckedRadioButtonId();

                radioTypeButton = (RadioButton) findViewById( selectedId );

                String type = radioTypeButton.getText().toString();

                String date = (datePicker.getDayOfMonth() + "_" + datePicker.getMonth() + "_" + datePicker.getYear());

                String guests = editTextguest.getText().toString();
                String location = spinner.getSelectedItem().toString();

                Intent intent = new Intent( UserFirstActivity.this, ChooseHallActivity.class );
                intent.putExtra( "type", type );
                intent.putExtra( "date", date );
                intent.putExtra( "guests", guests );
                intent.putExtra( "location", location );
                startActivity( intent );
            }
            else
                Toast.makeText(getApplicationContext(),"All fields are required!! ",Toast.LENGTH_LONG).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
