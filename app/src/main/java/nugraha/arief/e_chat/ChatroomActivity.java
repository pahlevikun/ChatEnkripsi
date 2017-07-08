package nugraha.arief.e_chat;

/**
 * Created by farhan on 4/9/17.
 */

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import nugraha.arief.e_chat.database.DatabaseHandler;
import nugraha.arief.e_chat.pojo.Profil;

public class ChatroomActivity extends AppCompatActivity {

    private Button btn_send_msg;
    private EditText input_msg;
    private TextView chat_conversation;
    private String user_name ,room_name, enkrip,username;
    private int enkripsi;
    private DatabaseReference root;
    private String temp_key;
    private DatabaseHandler dataSource;
    private ArrayList<Profil> valuesProfil;

    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz,-+=*/,.?!1234567890:@#$%^&*()<> ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        btn_send_msg = (Button)findViewById(R.id.button);
        input_msg = (EditText)findViewById(R.id.editText);
        chat_conversation = (TextView)findViewById(R.id.textView);
        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();
        enkrip = getIntent().getExtras().get("enkripsi").toString();
        setTitle("Room - "+room_name);

        if (enkrip.length()==0||enkrip==null){
            enkripsi = 0;
        }else{
            enkripsi = Integer.parseInt(enkrip);
        }
        Log.d("ENKRIPSI","geser"+enkripsi);
        dataSource = new DatabaseHandler(this);
        valuesProfil = (ArrayList<Profil>) dataSource.getAllProfils();
        for(Profil profil : valuesProfil){
            username = profil.getUsername();
        }

        root = FirebaseDatabase.getInstance().getReference().child(room_name);

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(input_msg.getText().toString().length()==0){
                    Toast.makeText(ChatroomActivity.this, "Silahkan isi dengan benar!", Toast.LENGTH_SHORT).show();
                }else {
                    Map<String, Object> map = new HashMap<String, Object>();
                    temp_key = root.push().getKey();
                    root.updateChildren(map);

                    Log.d("ENKRIPSI", "awal " + input_msg.getText().toString());
                    Log.d("ENKRIPSI", "hasil " + encrypt(input_msg.getText().toString(), enkripsi));
                    DatabaseReference message_root = root.child(temp_key);
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("name", user_name);
                    map2.put("msg", encrypt(input_msg.getText().toString(), enkripsi));
                    message_root.updateChildren(map2);
                    input_msg.setText("");
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                }

            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                append_chat_conversatin(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conversatin(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private String chat_msg, chat_user_name;

    private void append_chat_conversatin(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();
            if(username.equals(chat_user_name)){
                chat_user_name = "Saya";
                chat_conversation.append(chat_user_name + " : "+decrypt(chat_msg,enkripsi) +"\n\n");
            }else {
                chat_conversation.append(chat_user_name + " : "+decrypt(chat_msg,enkripsi) +"\n\n");
            }
        }
    }

    public static String encrypt(String plainText, int shiftKey) {

        plainText = plainText.toLowerCase();
        String cipherText = "";
        for (int i = 0; i < plainText.length(); i++) {
            //try {
                int charPosition = ALPHABET.indexOf(plainText.charAt(i));
                int keyVal = (shiftKey + charPosition) % 59;
                char replaceVal = ALPHABET.charAt(keyVal);
                cipherText += replaceVal;
            //}catch (Exception e){
            //    cipherText += plainText.charAt(i);
            //}

        }
        return cipherText;
    }

    public static String decrypt(String cipherText, int shiftKey) {
        cipherText = cipherText.toLowerCase();
        String plainText = "";
        for (int i = 0; i < cipherText.length(); i++) {
            //try {
                int charPosition = ALPHABET.indexOf(cipherText.charAt(i));
                int keyVal = (charPosition - shiftKey) % 59;
                if (keyVal < 0) {
                    keyVal = ALPHABET.length() + keyVal;
                }
                char replaceVal = ALPHABET.charAt(keyVal);
                plainText += replaceVal;
            //}catch (Exception e){
            //    cipherText += cipherText.charAt(i);
            //}
        }
        return plainText;
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