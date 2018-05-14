package com.example.monia.rejestracja;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class AnonimMode extends AppCompatActivity {
    private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonim_mode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.klient_toolbar);
        setSupportActionBar(toolbar);
        Button button = (Button) findViewById(R.id.button5);
        Button button2 = (Button) findViewById(R.id.button8);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TextView textView = (TextView) findViewById(R.id.textView5);
                Context context;
                context = getApplicationContext();
                Intent intent = new Intent(context, LekiAdoZ.class);
                startActivity(intent);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TextView textView = (TextView) findViewById(R.id.textView5);
                Context context;
                context = getApplicationContext();
                Intent intent = new Intent(context, LekiCenaSort.class);
                startActivity(intent);

            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_anonim,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override

        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

        if (id == R.id.zarejestruj){
            Intent intent1 = new Intent(this,RegisterActivity.class);
            this.startActivity(intent1);
            return true;
        }
            if (id == R.id.zaloguj){
                Intent intent1 = new Intent(this,LoginActivity.class);
                this.startActivity(intent1);
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
}


