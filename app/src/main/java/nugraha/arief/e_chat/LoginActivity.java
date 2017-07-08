package nugraha.arief.e_chat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nugraha.arief.e_chat.database.DatabaseHandler;
import nugraha.arief.e_chat.pojo.Profil;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private Button btLogin;
    private DatabaseHandler dataSource;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.editTextUsername);
        btLogin = (Button) findViewById(R.id.buttonMasuk);

        dataSource = new DatabaseHandler(this);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                if(username.length()<5){
                    Toast.makeText(LoginActivity.this, "Minimal 5 karakter!", Toast.LENGTH_SHORT).show();
                }else{
                    dataSource.tambahProfil(new Profil(username));
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
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
