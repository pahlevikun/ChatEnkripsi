package nugraha.arief.e_chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnkripActivity extends AppCompatActivity {

    private EditText etEnkrip;
    private Button btEnkrip;
    private String username, room, enkrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enkrip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("E-Chat - Masukan Enkripsi");

        Intent intent = getIntent();
        username = intent.getStringExtra("user_name");
        room = intent.getStringExtra("room_name");

        etEnkrip = (EditText) findViewById(R.id.etEnkrip);
        btEnkrip = (Button) findViewById(R.id.btnAdd_enkrip);

        btEnkrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEnkrip.getText().toString().length()==0||etEnkrip.getText().toString()==null){
                    enkrip="";
                }else{
                    enkrip=etEnkrip.getText().toString();
                }
                Intent intent = new Intent(EnkripActivity.this, ChatroomActivity.class);
                intent.putExtra("user_name",username);
                intent.putExtra("room_name",room);
                intent.putExtra("enkripsi",enkrip);
                startActivity(intent);
                finish();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onBackPressed() {
        finish();
    }
}
