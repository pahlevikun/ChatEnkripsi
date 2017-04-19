package nugraha.arief.e_chat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import nugraha.arief.e_chat.database.DatabaseHandler;
import nugraha.arief.e_chat.pojo.Profil;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private DatabaseHandler dataSource;
    private ArrayList<Profil> valuesProfil;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        dataSource = new DatabaseHandler(this);

        valuesProfil = (ArrayList<Profil>) dataSource.getAllProfils();
        for(Profil profil : valuesProfil){
            username = profil.getUsername();
        }

        if(username==null){
            startLogin();
        }else{
            startMain();
        }
    }

    private void startLogin() {
        int SPLASH_TIME_OUT = 3500;

        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void startMain() {
        int SPLASH_TIME_OUT = 3500;

        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
