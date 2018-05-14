package com.example.monia.rejestracja;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LekiAdoZ extends AppCompatActivity {
    TextView lekid;
    ListView listView;
    String nazwa_leku_textview;
    boolean czy_rozny;
    boolean dupa;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leki_a_do_z);
        Toolbar toolbar = (Toolbar) findViewById(R.id.klient_toolbar);
        setSupportActionBar(toolbar);

        Intent intent2 = getIntent();
        final int klientid = intent2.getIntExtra("ID", 0);






        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ListView listView = (ListView) findViewById(R.id.lista);
        List<PojedynczyLek> contacts = new ArrayList<PojedynczyLek>();
        LekiAdapter adapter = new LekiAdapter(this,
                R.layout.plusiki, contacts);


        try {
            JSONArray tab = new db_connect().getResults("SELECT * FROM Leki ORDER BY CENA DESC");


            for (int i = 0; i < tab.length(); i++) {

                JSONObject json_data = tab.getJSONObject(i);

                String nazwa = json_data.getString("Nazwa");
                Integer cena = json_data.getInt("Cena");
                String cena2 = Integer.toString(cena);




                contacts.add(new PojedynczyLek(nazwa, cena2));
                listView.setAdapter(adapter);

            }

        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Integer koszyk_id;


                String str =(String) ((TextView) view.findViewById(R.id.NazwaLeku)).getText();




                boolean czy_jest =czy_klient_id_jest_w_koszyku(klientid);
                int id_leku =pobierz_id_leku(str);
                int Najwieksze_koszyk_id_historia = get_najwiekszy_id_historiazamowien();

               /* Toast.makeText(getApplicationContext(),
                        "pnkihistoria " + Najwieksze_koszyk_id_historia, Toast.LENGTH_LONG)
                        .show();
                        */

                int Najwieksze_koszyk_id_z_koszyk = get_najwiekszy_id_koszyk();

               /* Toast.makeText(getApplicationContext(),
                        "z koszyk " + Najwieksze_koszyk_id_z_koszyk, Toast.LENGTH_LONG)
                        .show();
                        */




               /* Toast.makeText(getApplicationContext(),
                       "poka id lek " + Id_lek, Toast.LENGTH_LONG)
                       .show();
                       */

                int pobierz_id_z_leku = pobierz_id_leku(str);
                int ilosc_z_leku = zwroc_ilosc_z_leku(str, pobierz_id_z_leku);


                        if (czy_jest == false && ilosc_z_leku > 0) {

                            if (Najwieksze_koszyk_id_historia + 1 > Najwieksze_koszyk_id_z_koszyk + 1) {

                                koszyk_id = Najwieksze_koszyk_id_historia + 1;



                            String query="INSERT INTO" + " Koszyk " + "(KoszykID, KlientID, LekID, Ilosc) "
                                    + "Values ('" + koszyk_id + "', '" + klientid + "', '" + id_leku + "', '" + 1 + "')";
                            new db_connect().getResults(query);

                                Toast.makeText(getApplicationContext(),
                                        "Dodano lek do koszyka", Toast.LENGTH_LONG)
                                        .show();



                            } else {
                                koszyk_id = Najwieksze_koszyk_id_z_koszyk + 1;

                              /*  Toast.makeText(getApplicationContext(),
                                        "dup2a " + koszyk_id, Toast.LENGTH_LONG)
                                        .show();
                                        */

                                String query="INSERT INTO" + " Koszyk " + "(KoszykID, KlientID, LekID, Ilosc) "
                                        + "Values ('" + koszyk_id + "', '" + klientid + "', '" + id_leku + "', '" + 1 + "')";
                                new db_connect().getResults(query);

                                Toast.makeText(getApplicationContext(),
                                        "Dodano lek do koszyka", Toast.LENGTH_LONG)
                                        .show();


                        }
                        }
                        if (czy_jest == true) {


                           // int pobierz_id_z_leku = pobierz_id_leku(str);
                           // int ilosc_z_leku = zwroc_ilosc_z_leku(str, pobierz_id_z_leku);
                            int pobierz_ilosc_z_koszyka = pobierz_ilosc_w_koszyku(str, klientid);
                            boolean czy_jest_id_w_bazie = czy_id_leku_jest_w_bazie(pobierz_id_z_leku);
                            boolean czy_klient_jest_w_bazie_ = czy_klient_jest_w_bazie(klientid);


                            int b;
                            JSONArray tab3 = new db_connect().getResults("SELECT * FROM Koszyk");
                            try {
                                for (int j = 0; j < tab3.length(); j++) {

                                    JSONObject json_data2 = tab3.getJSONObject(j);
                                    Integer klient_id2 = json_data2.getInt("KlientID");
                                    Integer lek_id2 = json_data2.getInt("LekID");
                                    Integer ilosc2 = json_data2.getInt("Ilosc");
                                    Integer koszyk_id_ = json_data2.getInt("KoszykID");

                                    if(klient_id2 == klientid && 0 == ilosc_z_leku){

                                        Toast.makeText(getApplicationContext(),
                                                "Podany lek nie jest dostępny", Toast.LENGTH_LONG)
                                                .show();
                                        break;
                                    }



                                    if(lek_id2 != pobierz_id_z_leku && klient_id2 == klientid && ilosc2 < ilosc_z_leku){

                                            String query = "INSERT INTO" + " Koszyk " + "(KoszykID, KlientID, LekID, Ilosc) "
                                                    + "Values ('" + koszyk_id_ + "', '" + klientid + "', '" + id_leku + "', '" + 1 + "')";
                                            new db_connect().getResults(query);

                                        Toast.makeText(getApplicationContext(),
                                                "Dodano lek do koszyka", Toast.LENGTH_LONG)
                                                .show();


                                            break;


                                    }


                                }
                            } catch (JSONException e) {
                                Log.e("log_tag", "Error parsing data " + e.toString());
                            }

                        }




            }


        });
    }



    int get_najwiekszy_id_historiazamowien() {
        try {
            JSONArray tab3 = new db_connect().getResults("SELECT * FROM HistoriaZamowien");
            JSONObject json_data2 = tab3.getJSONObject(0);
            Integer najwiekszy = json_data2.getInt("KoszykID");

            for (int i = 1; i < tab3.length(); i++) {

                JSONObject json_data = tab3.getJSONObject(i);
                Integer Najwieksze_koszyk_id_historia = json_data.getInt("KoszykID");

                if (Najwieksze_koszyk_id_historia > najwiekszy)
                    najwiekszy = Najwieksze_koszyk_id_historia;


            }
            return najwiekszy;

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return 0;

    }

    int get_najwiekszy_id_koszyk() {
        try {
            JSONArray tab3 = new db_connect().getResults("SELECT * FROM Koszyk");
            JSONObject json_data2 = tab3.getJSONObject(0);
            Integer najwiekszy = json_data2.getInt("KoszykID");

            for (int i = 1; i < tab3.length(); i++) {

                JSONObject json_data = tab3.getJSONObject(i);
                Integer Najwieksze_koszyk_id_historia = json_data.getInt("KoszykID");

                if (Najwieksze_koszyk_id_historia > najwiekszy)
                    najwiekszy = Najwieksze_koszyk_id_historia;


            }
            return najwiekszy;

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return 0;

    }
    boolean czy_klient_id_jest_w_koszyku(int klientid){
        JSONArray tab2 = new db_connect().getResults("SELECT * FROM Koszyk");
        try {
            for (int i = 0; i < tab2.length(); i++) {

                JSONObject json_data = tab2.getJSONObject(i);
                Integer klient_id = json_data.getInt("KlientID");


                if (klient_id == klientid) {
                   return true;
                }
            }
            }catch (JSONException e) {
                    Log.e("log_tag", "Error parsing data " + e.toString());
                }
        return false;
            }

    int pobierz_id_leku(String Naazwa){
        JSONArray tab2 = new db_connect().getResults("SELECT * FROM Leki");
        try {
            for (int i = 0; i < tab2.length(); i++) {

                JSONObject json_data = tab2.getJSONObject(i);

                String nazwa = json_data.getString("Nazwa");
                if (Naazwa.equals(nazwa)) {
                    Integer lek_id = json_data.getInt("ID");
                    return lek_id;

                }
            }
        }catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return 0;
    }

    int zwroc_ilosc_z_leku(String nazwa,int d){

        JSONArray tab2 = new db_connect().getResults("SELECT * FROM Leki");
        try {
            for (int i = 0; i < tab2.length(); i++) {

                JSONObject json_data = tab2.getJSONObject(i);

                int id_ = json_data.getInt("ID");

                if (d == id_) {
                    int Ilosc= json_data.getInt("Ilosc");

                    return Ilosc;

                }
            }
        }catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return 0;
    }
    int pobierz_ilosc_w_koszyku(String nazwa,int d){

        JSONArray tab2 = new db_connect().getResults("SELECT * FROM Koszyk");
        try {
            for (int i = 0; i < tab2.length(); i++) {

                JSONObject json_data = tab2.getJSONObject(i);

                int id_ = json_data.getInt("ID");

                if (d == id_) {
                    int Ilosc= json_data.getInt("Ilosc");

                    return Ilosc;

                }
            }
        }catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return 0;
    }

    boolean czy_id_leku_jest_w_bazie(int id){
        JSONArray tab2 = new db_connect().getResults("SELECT * FROM Koszyk");
        try {
            for (int i = 0; i < tab2.length(); i++) {

                JSONObject json_data = tab2.getJSONObject(i);

                int k3 = json_data.getInt("LekID");


                if (k3 == id) {
                    return true;
                }
            }
        }catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return false;
    }
    boolean czy_klient_jest_w_bazie(int id){
        JSONArray tab2 = new db_connect().getResults("SELECT * FROM Koszyk");
        try {
            for (int i = 0; i < tab2.length(); i++) {

                JSONObject json_data = tab2.getJSONObject(i);

                int k3 = json_data.getInt("KlientID");


                if (k3 == id) {
                    return true;
                }
            }
        }catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return false;
    }





}




