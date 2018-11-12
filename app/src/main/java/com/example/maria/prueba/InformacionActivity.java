package com.example.maria.prueba;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.net.URL;


public class InformacionActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tabla;
    TextView leer;
    String direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);


        tabla=(TextView)findViewById(R.id.tablaConsumos);
        leer=(TextView)findViewById(R.id.leer);
        direccion="";

        tabla.setOnClickListener(this);
        leer.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tablaConsumos:
                direccion = "http://www.enre.catamarca.gob.ar/wp-content/uploads/2018/08/CONSUMOS%20DE%20ARTEFACTOS%20ELECTRICOS-AGOSTO.pdf";
                web(direccion);
                break;

            case R.id.leer:
                direccion= "http://www.enre.catamarca.gob.ar/index.php/como-leer-su-factura/";
                web(direccion);

            default:
                break;
        }
    }


    public void web(String d){
        Uri uri = Uri.parse(d);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
