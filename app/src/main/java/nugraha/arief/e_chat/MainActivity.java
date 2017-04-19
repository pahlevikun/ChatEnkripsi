package nugraha.arief.e_chat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import nugraha.arief.e_chat.database.DatabaseHandler;
import nugraha.arief.e_chat.pojo.Profil;

public class MainActivity  extends AppCompatActivity {
    private Button add_room;
    private EditText room_name;
    private ListView listView;
    private String name;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private DatabaseHandler dataSource;
    private ArrayList<Profil> valuesProfil;

    private boolean doubleBackToExitPressedOnce = false;

    private ProgressDialog loading;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_room = (Button)findViewById(R.id.btnAdd_room);
        room_name = (EditText)findViewById(R.id.etNeme_room);
        listView = (ListView)findViewById(R.id.listView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        dataSource = new DatabaseHandler(this);
        valuesProfil = (ArrayList<Profil>) dataSource.getAllProfils();
        for(Profil profil : valuesProfil){
            name = profil.getUsername();
        }

        setTitle("E-Chat - Hai, "+name+"!");
        loading = ProgressDialog.show(MainActivity.this,"Mohon Tunggu","Sedang memuat...",false,false);

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_of_rooms);
        listView.setAdapter(arrayAdapter);

        //request_user_name();
        add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(room_name.getText().toString().length()==0){
                    Toast.makeText(MainActivity.this, "Silahkan isi dengan benar!", Toast.LENGTH_SHORT).show();
                }else {
                    loading = ProgressDialog.show(MainActivity.this, "Mohon Tunggu", "Sedang memuat...", false, false);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(room_name.getText().toString(), "");
                    root.updateChildren(map);
                    room_name.setText("");
                }
            }
        });

        root.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hideDialog();
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while ( i.hasNext()) {
                    set.add(((DataSnapshot)i.next()).getKey());
                }
                list_of_rooms.clear();
                list_of_rooms.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideDialog();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent I = new Intent(getApplicationContext(),EnkripActivity.class);
                I.putExtra("room_name",((TextView)view).getText().toString());
                I.putExtra("user_name",name);
                startActivity(I);
            }
        });

    }

    private void request_user_name() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Name");
        final EditText input_field = new EditText(this);
        builder.setView(input_field);
        builder.setPositiveButton("OK ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                name = input_field.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                request_user_name();
            }
        });
        builder.show();
    }


    private void hideDialog() {
        if (loading.isShowing())
            loading.dismiss();
    }

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan lagi untuk keluar!", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}