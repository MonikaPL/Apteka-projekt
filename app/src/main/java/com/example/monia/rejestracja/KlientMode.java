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

public class KlientMode extends AppCompatActivity {

    private Object Menu;
    private Button button1;
    int klientid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klient_mode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.klient_toolbar);
        setSupportActionBar(toolbar);
        Button button = (Button) findViewById(R.id.button5);

        Intent intent = getIntent();
        klientid = intent.getIntExtra("ID", 0);




        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TextView textView = (TextView) findViewById(R.id.textView5);
                Context context;
                context = getApplicationContext();

                Intent intent = new Intent(context, LekiAdoZ.class);
                intent.putExtra("ID",klientid);


               /* Toast.makeText(getApplicationContext(),
                        "Witaj :)      " + klientid, Toast.LENGTH_LONG)
                        .show();;
                        */

                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_klient,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.wyloguj){
            Intent intent1 = new Intent(this,LoginActivity.class);

            String query = "DELETE FROM " + " Koszyk " + "WHERE KlientID ='" + klientid + "'";
            new db_connect().getResults(query);


            this.startActivity(intent1);
            Toast.makeText(this, "Zostałeś wylogowany!", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.koszyk){
            Intent intent1 = new Intent(this,Koszyk.class);
            intent1.putExtra("ID",klientid);
            this.startActivity(intent1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        return;
    }
}

