package com.example.maria.prueba;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MisConsumosActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> lista;
    ArrayAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_consumos);

        listView = (ListView)findViewById(R.id.lista);

        ConsumosOpenHelper db = new ConsumosOpenHelper(getApplicationContext());
        lista = db.llenar_lv();
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista);
        listView.setAdapter(adaptador);
   }

}