package nugraha.arief.e_chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnkripActivity extends AppCompatActivity {

    private EditText etEnkrip, etEnkrip2, etEnkrip3;
    private Button btEnkrip;
    private String username, room;
    private int enkrip,enkrip3;
    private float hasil,enkrip2;

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
        etEnkrip2 = (EditText) findViewById(R.id.etKode2);
        etEnkrip3 = (EditText) findViewById(R.id.etKode3);
        btEnkrip = (Button) findViewById(R.id.btnAdd_enkrip);

        btEnkrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(etEnkrip.getText().toString())>4){
                    Toast.makeText(EnkripActivity.this, "Nilai r maksimal 4!", Toast.LENGTH_SHORT).show();
                }else{
                    if(etEnkrip.getText().toString().length()==0||etEnkrip.getText().toString()==null||etEnkrip2.getText().toString().length()==0||etEnkrip2.getText().toString()==null||etEnkrip3.getText().toString().length()==0||etEnkrip3.getText().toString()==null){
                        hasil=0;
                    }else{
                        enkrip=Integer.parseInt(etEnkrip.getText().toString());
                        enkrip2=Float.parseFloat("0."+etEnkrip2.getText().toString());
                        enkrip3=Integer.parseInt(etEnkrip3.getText().toString());

                        hasil = chaos(enkrip,enkrip2,enkrip3);
                        Float fObj = new Float(hasil);
                        boolean b2 = fObj.isInfinite();
                        boolean b3 = fObj.isNaN();

                        int enkrips = (int)hasil;

                        if(hasil==0||b2||b3){
                            Toast.makeText(EnkripActivity.this, "Kode enkripsi tidak valid!", Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent = new Intent(EnkripActivity.this, ChatroomActivity.class);
                            intent.putExtra("user_name", username);
                            intent.putExtra("room_name", room);
                            intent.putExtra("enkripsi", enkrips);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        });
    }

    private float chaos(float r, float x0, float k){
        float hasil=0;
        for (int i=0; i<k; i++){
            x0 = (r*x0)*(1-x0);
            Log.d("COBA PUTAR","Putaran ke-"+i+" = "+x0);
        }
        hasil = x0 * 1000000;
        hasil = hasil % 59;
        /*if(hasil==0){
            hasil = x0-1;
        }*/
        if(hasil<0) {
            hasil = hasil * -1;
        }

        return hasil;
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
