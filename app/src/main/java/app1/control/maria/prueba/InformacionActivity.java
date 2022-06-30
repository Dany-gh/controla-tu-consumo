package app1.control.maria.prueba;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class InformacionActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tabla;
    TextView energia;
    TextView agua;
    TextView leer;
    String direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);


        tabla=(TextView)findViewById(R.id.tablaConsumos);
        energia=(TextView)findViewById(R.id.tablaEnergia);
        agua=(TextView)findViewById(R.id.tablaAgua);
        leer=(TextView)findViewById(R.id.leer);
        direccion="";

        tabla.setOnClickListener(this);
        energia.setOnClickListener(this);
        agua.setOnClickListener(this);
        leer.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tablaConsumos:
                direccion = "http://www.enre.catamarca.gob.ar/wp-content/uploads/Descargas/2019/CONSUMOS%20DE%20ARTEFACTOS%20ELECTRICOS-MARZO-19.pdf";
                web(direccion);
                break;

            case R.id.tablaEnergia:
                direccion = "http://www.enre.catamarca.gob.ar/wp-content/uploads/CT/Electricidad/2019/RES.%20ENRE%20N%C2%B0%20008-19_ANEXO%20I_FEB19-ABR19.pdf";
                web(direccion);
                break;

            case R.id.tablaAgua:
                direccion = "http://www.enre.catamarca.gob.ar/wp-content/uploads/CT/Agua/2018/RES.%20EN.RE.%20N%C2%B0%20175-18_ANEXO%20II,III,IV%20yV.pdf";
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
