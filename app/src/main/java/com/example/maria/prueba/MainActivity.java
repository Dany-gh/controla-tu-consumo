package com.example.maria.prueba;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Declaracion de variables
    TextView textView, textView1, textView2, textView3, textView4;
    EditText editText, editText1, Eid;
    RadioButton radioButton, radioButton1;
    Button button, btguardar;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enlazamos nuestras variables con la parte grafica
        textView = (TextView) findViewById(R.id.usuario);
        textView1 = (TextView) findViewById(R.id.actual);
        textView2 = (TextView) findViewById(R.id.anterior);
        textView3 = (TextView) findViewById(R.id.costoParcial);
        textView4 = (TextView) findViewById(R.id.resultado);

        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);

        editText = (EditText) findViewById(R.id.ingreso);
        editText1 = (EditText) findViewById(R.id.ingreso1);

        button = (Button) findViewById(R.id.calcular);
        btguardar = (Button) findViewById(R.id.guardar);


        btguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsumosOpenHelper db = new ConsumosOpenHelper(getApplicationContext());
                String consumo = textView4.getText().toString();
                String mensaje = db.guardar(consumo);
                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Metodo perteneciente al boton Calcular
    public void calcular(View v) {

       // DecimalFormat form = new DecimalFormat("0.00");

        try {
            //Tomamos los valores obtenidos en los editText
            String valor_string = editText.getText().toString();
            String valor1_string = editText1.getText().toString();

            //Pasamos a enteros los valores ingresados
            int valor_int = Integer.parseInt(valor_string);
            int valor_int1 = Integer.parseInt(valor1_string);

            //Si pertenece a un Cliente Residencial
            if (radioButton.isChecked() == true) {

                //Realizamos la diferencia entre el valor actual(valor_int) y el ultimo facturado(valor_int1)
                int resul_parcial = valor_int - valor_int1;
                //Realizamos la operación necesaria para calcular el costo total
                double resultado = resul_parcial * 3.50;

                if (resultado > 0) {
                    //Pasamos el resultado obtenido anteriormente a String
                    String resultado_final = String.valueOf(resultado);
                    //Mostramos el resultado obtenido
                    //textView4.setText("$" + form.format(resultado_final));
                     textView4.setText("$" + resultado_final);
                    //Leyenda de aclaración
                    Toast.makeText(getApplicationContext(), "Cálculo según cuadro tarifario vigente Res/", Toast.LENGTH_LONG).show();
                } else {
                    textView4.setText("$" + 0);
                    //En caso de ingresar incorrectamente los datos, mostramos la leyenda de error
                    Toast.makeText(getApplicationContext(), "Error: Ingrese Nuevamente los datos", Toast.LENGTH_LONG).show();
                }

                //Si pertenece a un Cliente Comercial
            } else if (radioButton1.isChecked() == true) {

                int resul_parcial = valor_int - valor_int1;
                double resultado = resul_parcial * 3.90;

                if (resultado > 0) {
                    String resultado_final = String.valueOf(resultado);

                    //textView4.setText("$" + form.format(resultado_final));
                    textView4.setText("$" + resultado_final);
                    Toast.makeText(getApplicationContext(), "Cálculo según cuadro tarifario vigente Res/", Toast.LENGTH_LONG).show();
                } else {
                    textView4.setText("$" + 0);
                    Toast.makeText(getApplicationContext(), "ERROR: Ingrese Nuevamente los Datos", Toast.LENGTH_LONG).show();
                }
            }
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "ERROR: Ingrese los Datos Requeridos", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        Intent intent = null;

        switch (item.getItemId()){
            case R.id.opcion1:
                intent= new Intent(MainActivity.this, InformacionActivity.class);
                //Intent intent = new Intent(MainActivity.this, InformacionActivity.class);
                startActivity(intent);
                return true;

            case R.id.opcion2:
                intent= new Intent(MainActivity.this, MisConsumosActivity.class);
                startActivity(intent);
                return true;

            case R.id.opcion3:
                intent= new Intent(MainActivity.this, AyudaActivity.class);
                //Intent intent1 = new Intent (MainActivity.this, AyudaActivity.class);
                startActivity(intent);
                return true;
             default:
                 return super.onOptionsItemSelected(item);
        }
    }

}
