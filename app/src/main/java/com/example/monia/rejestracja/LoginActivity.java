package com.example.monia.rejestracja;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    EditText L,P;
    String Login1;
    String Password1;
    Boolean Jest;
    Boolean Jest2;
    public int id_klienta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.klient_toolbar);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        Button button1 = (Button) findViewById(R.id.Rejestracja);
        Button  button2 = (Button) findViewById(R.id.Logowanie);
        Button button3 = (Button) findViewById(R.id.AnonimMode);
        L = (EditText) findViewById(R.id.LoginL);
        P = (EditText) findViewById(R.id.PasswordP);



        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Context context;
                context = getApplicationContext();
                Intent intent = new Intent(context,RegisterActivity.class);
                startActivity(intent);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Login1 = L.getText().toString();
                Password1 = P.getText().toString();

                CheckIfExist();
                int login=CheckIfExits2();

                id_klienta = login;



                if(Jest2 == false){
                   Toast.makeText(LoginActivity.this,"Zalogowales się!", Toast.LENGTH_LONG).show();

                    String query = "DELETE FROM " + " Koszyk " + "WHERE KlientID ='" + id_klienta + "'";
                    new db_connect().getResults(query);

                    Intent intent = new Intent(getApplicationContext(),KlientMode.class);

                    intent.putExtra("ID",id_klienta);

                    startActivity(intent);

                }

                else{
                    Toast.makeText(LoginActivity.this,"Zły login lub hasło!", Toast.LENGTH_LONG).show();
                }


            }
        });



        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context;
                context = getApplicationContext();
                Intent intent = new Intent(context,AnonimMode.class);
                startActivity(intent);

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_podstawowe,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void CheckIfExist(){
        try {
            JSONArray tab = new db_connect().getResults("SELECT Login,Haslo FROM Aptekarze ");
            //JSONArray tab = new db_connect().getResults("SELECT Login FROM Aptekarze ");
            //JSONArray tab2 = new db_connect().getResults("SELECT Login FROM Klienci");
            for (int i = 0; i < tab.length(); i++) {


                JSONObject json_data = tab.getJSONObject(i);




                String login = json_data.getString("Login");
                String password = json_data.getString("Haslo");

                String name = MD5(Password1);

                if(login.equals(Login1) && name.equals(password)){


                    Jest = false;
                    break;
                }
                else
                    Jest = true;
            }

        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

    }

    public int CheckIfExits2(){
        try {
            JSONArray tab = new db_connect().getResults("SELECT Login,Haslo,ID FROM Klienci ");
            for (int i = 0; i < tab.length(); i++) {
                JSONObject json_data = tab.getJSONObject(i);
                String login = json_data.getString("Login");
                String password = json_data.getString("Haslo");
                int ID = json_data.getInt("ID");
                String name = MD5(Password1);

                if(login.equals(Login1) && name.equals(password)){
                    Jest2 = false;
                    id_klienta = ID;

                    return id_klienta;
                }
                else {
                    Jest2 = true;
                }
            }
        }

        catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());

        }
        return 0;
}



    private String MD5(String s) {
        byte[] data;
        try {
            data = s.getBytes("UTF-8");
            String base64Sms = Base64.encodeToString(data, Base64.DEFAULT);
            return base64Sms;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed() {

        return;
    }
    /*
    private Dialog createAlertDialogWithButtons() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Wyjście");
        dialogBuilder.setMessage("Czy napewno?");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("Tak", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                showToast("Wychodzę");
                LoginActivity.this.finish();
            }
        });

        dialogBuilder.setNegativeButton("Nie", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                showToast("Anulowaleś wyjście");

            }

            private void showToast(String s) {
            }
        });
        return dialogBuilder.create();
    }
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }
    public void onCancel(DialogInterface dialog) {
        createAlertDialogWithButtons();

    }
    */


}

