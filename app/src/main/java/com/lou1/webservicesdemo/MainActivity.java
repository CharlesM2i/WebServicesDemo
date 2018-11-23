package com.lou1.webservicesdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etCodePays;
    TextView tvPays;
    ListView lvPays;
    TextView tvaffpays;
    HttpClient client;

 private final String urlcli1="http://demo@services.groupkt.com/country/get/iso2code/";
 private final String urlcliall="http://alainpre.free.fr/cours/android/ressources/json/pays.json";
 //private final String urlcliall = "https://demo@services.groupkt.com/country/get/all";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etCodePays=findViewById(R.id.etCodePays);
        tvPays=findViewById(R.id.tvPays);
        lvPays=findViewById(R.id.lvPays);
        tvaffpays=findViewById(R.id.tvaffpays);
        lvPays=findViewById(R.id.lvPays);

    }


public void searchOne(View view) throws JSONException {


    String codePays = etCodePays.getText().toString();

    client=new HttpClient();

    client.setAdr(urlcli1+codePays);

    client.start();

    try {

    client.join();


        Toast.makeText(this, client.getResponse(),Toast.LENGTH_LONG).show();

    JSONObject j = new JSONObject(client.getResponse());

    JSONObject j2 = j.getJSONObject("RestResponse");

    JSONObject j3=j2.getJSONObject("result");

    tvaffpays.setText(j3.getString("name"));

    } catch (InterruptedException e) {

        e.printStackTrace();

    } catch (JSONException e) {
        e.printStackTrace();
    }



}
public void searchAll(View view) throws JSONException{

       client= new HttpClient();

        client.setAdr(urlcliall);

        ArrayList<String> NomPays = new ArrayList<>();

        client.start();

    try {
        client.join();
        Toast.makeText(this, client.getResponse(),Toast.LENGTH_LONG).show();

        JSONObject j = new JSONObject(client.getResponse());

        JSONObject j2 = j.getJSONObject("RestResponse");

        JSONArray j3 = j2.getJSONArray("result");


        for (int i = 0; i<j3.length();i++){

          NomPays.add(j3.getJSONObject(i).getString("name"));

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, NomPays);

        lvPays.setAdapter(adapter);




    } catch (InterruptedException e) {
        e.printStackTrace();
    }

}






}
