package app1.control.maria.prueba;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //Declaracion de variables---
    TextView textView, textView1, textView2, textView3,
            textView6, textView7;
    EditText editText, editText1, resultadoKWH,resultadoParcial;
    RadioButton radioButton, radioButton1;
    Button button, btguardar;
    final Context context = this;

    //DEFINO CTES PARA PODER CALCULAR LA FACTURA
    //final tipo nombreConstante=valor;
    //USO RESIDENCIAL-CUADRO
    final double cargoFijo_ss=1026.14; //res 064-22_A2
    final double cargoVariable_ss=19.2163;
    final double cargoFijo_cs=450.84; //res 064-22_A2
    final double cargoVariable_cs=6.8061;
    //USO GENERAL-CUADRO
    final double cargoFijo_cs_G=1598.93; //RES ENRE 064-22_A2
    final double cargoVariable_cs_G=8.9031; //RES ENRE 064-22_A2
    //-----------------------------------------------------------
    final double tasaOEA=0.06; //Tasa Ocupacion Esp. Aereo (6%)
    final double tasaMAP=0.08; //Tasa M.A.P. (8%)
    final double tasaFiscalizacion=0.015; //Tasa de Fiscalizacion 1,50%
    final double iva_R=0.21; //Residencial
    final double iva_G=0.27; //General

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enlazamos nuestras variables con la parte grafica
        textView = (TextView) findViewById(R.id.usuario); //Estos son instancias
        textView1 = (TextView) findViewById(R.id.actual);
        textView2 = (TextView) findViewById(R.id.anterior);
        textView3 = (TextView) findViewById(R.id.costoParcial);

        textView6 = (TextView)findViewById(R.id.referenciaEstado);
        textView7 = (TextView) findViewById(R.id.referenciaMedidor);

        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);

        editText = (EditText) findViewById(R.id.ingreso);
        editText1 = (EditText) findViewById(R.id.ingreso1);
        resultadoKWH= (EditText) findViewById(R.id.resultadokwh);
        resultadoParcial=(EditText) findViewById(R.id.resultado);

        button = (Button) findViewById(R.id.calcular);
        btguardar = (Button) findViewById(R.id.guardar);

        //Evento que muestra un alerta con la imagen de la factura en la actividad principal
        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater imagenAlerta = LayoutInflater.from(MainActivity.this);
            alerta.setView(imagenAlerta.inflate(R.layout.imagen_estado_actual, null));

            AlertDialog titulo = alerta.create();
            titulo.setTitle("Factura");
            titulo.show();
            }
        });

        //Evento que muestra un alerta con la imagen del medidor en la actividad principal
        textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater imagenAlerta = LayoutInflater.from(MainActivity.this);
            alerta.setView(imagenAlerta.inflate(R.layout.imagen_medidor, null));

            AlertDialog titulo = alerta.create();
            titulo.setTitle("Medidor");
            titulo.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void guardar(View v){

        String resul = resultadoParcial.getText().toString(); //importe en pesos
        String resulKWH = resultadoKWH.getText().toString(); //kwh

        if( resul != null ){
             BaseHelper basehelper = new BaseHelper(this, "CONSUMO",null,6);
             SQLiteDatabase db = basehelper.getWritableDatabase();

            try{
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            ContentValues registro = new ContentValues();
            registro.put("ConsumoKWH",resulKWH);
            registro.put("ConsumoParcial",resul);
            registro.put("Fecha",date);

            long i = db.insert("consumos",null, registro);

                if(i>0){
                Toast.makeText(this, "Consumo Registrado", Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e) {
                Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else
            Toast.makeText(this,"Ingrese los datos",Toast.LENGTH_SHORT).show();
    }

    //Metodo perteneciente al boton Calcular
    public void calcular(View v) {
       // DecimalFormat form = new DecimalFormat("0.00");
         //if(radioButton.isChecked() == true || radioButton1.isChecked() == true) {
        try {
             //Tomamos los valores obtenidos en los editText
             String valor_string = editText.getText().toString(); //kwh inicial
             String valor1_string = editText1.getText().toString(); //kwh final

             //Pasamos a enteros los valores ingresados
             //int valor_int = Integer.parseInt(valor_string); //kWh inicial
             //int valor_int1 = Integer.parseInt(valor1_string); //kWh final

            //Pasamos a double los valores ingresados
            double valor_int=Double.valueOf(valor_string);
            //double valor_int=Double.parseDouble(valor_string);
            double valor_int1=Double.parseDouble(valor1_string);

             //Si pertenece a un Cliente RESIDENCIAL
             if (radioButton.isChecked() == true) {
                 //radioButton=Residencial
                 //Realizamos la diferencia entre el valor actual(valor_int) y el ultimo facturado (valor_int1)
                 double resul_parcial = (valor_int - valor_int1); //[kWh] consumo
                 double consumoFacturado=(resul_parcial/2); //[kWh] consumo facturado
                 double cargoVariabledeEnergia_cs=(consumoFacturado*cargoVariable_cs);
                 double Basico_cs=(cargoFijo_cs+cargoVariabledeEnergia_cs);
                 double tasaOEA_Importe=(tasaOEA*Basico_cs); //$
                 double subTotal=(Basico_cs+tasaOEA_Importe); //$

                 //Calculo de Importe de la tasa MAP
                 double cargoVariabledeEnergia_ss=(consumoFacturado*cargoVariable_ss);
                 double cargoVariabledeEnergia_subsidio=(cargoVariabledeEnergia_ss-cargoVariabledeEnergia_cs);
                 //ss=cs+subsidio --> cs=(ss-subsidio)
                 double tasaMAP_Importe=(tasaMAP*(cargoVariabledeEnergia_ss-cargoVariabledeEnergia_subsidio)); //$
                 //double tasaMAP_Importe=(tasaMAP*(cargoVariabledeEnergia_cs)); //$

                 //Calculo IVA
                 double ivaBasico=(iva_R*subTotal);
                 ////Calculo Tasa de Fiscalizacion
                 //ss=cs+subsidio --> cs=(ss-subsidio)
                 double BasicoEnergetico_ss=(cargoFijo_ss+cargoVariabledeEnergia_ss);
                 double tasaFiscalizacion_Importe=(tasaFiscalizacion*(Basico_cs)); //$

                 //SubTotal ECSAPEM
                 double subTotalECSAPEM=(subTotal+tasaMAP_Importe+ivaBasico+tasaFiscalizacion_Importe);

                 //Realizamos la operación para calcular el costo total
                 //double resultado = (resul_parcial * 6.8); //[kWh] * [$/kWh]=[$]//esto estaba en la version anterior
                 double resultado=subTotalECSAPEM;
                 if (resultado > 0) {
                     //Pasamos los resultados obtenidos anteriormente a String
                     //String resul_finalKWH = Integer.toString(resul_parcial); //kWh
                     String resul_finalKWH = Double.toString(resul_parcial); //kWh
                     String resultado_final = String.format("%.2f", resultado);//$

                     //Mostramos el resultado obtenido de kWh y el Importe
                     resultadoKWH.setText(resul_finalKWH);
                     resultadoParcial.setText(resultado_final);
                     //Leyenda de aclaración
                     Toast.makeText(getApplicationContext(), "Si Incluye Impuestos y Cargos Fijos", Toast.LENGTH_SHORT).show();
                 } else {
                     resultadoKWH.setText(" ");
                     resultadoParcial.setText(" ");
                     //En caso de ingresar incorrectamente los datos, mostramos la leyenda de error
                     Toast.makeText(getApplicationContext(), "Error: Ingrese Nuevamente los datos", Toast.LENGTH_SHORT).show();
                 }

             //Si pertenece a un Cliente COMERCIAL
             } else if (radioButton1.isChecked() == true) {
                 double resul_parcial = (valor_int - valor_int1); //[kwh]
                 double consumoFacturado=(resul_parcial/2); //[kWh] consumo facturado

                 //Resolucion SE No 1866-05
                 double resolucionSE=(consumoFacturado*(3.6/1000)*1.115); //Res SE NO 1866/05

                 double cargoVariabledeEnergia_cs_G=(consumoFacturado*cargoVariable_cs_G);
                 double Basico_cs1=(cargoFijo_cs_G+cargoVariabledeEnergia_cs_G);
                 //Basico_CS_G
                 double Basico_cs_G=(Basico_cs1+resolucionSE);
                 //Importe Tasa OEA
                 double tasaOEA_Importe=(tasaOEA*Basico_cs_G); //$
                 //SubTotal
                 double subTotal=(Basico_cs_G+tasaOEA_Importe); //$

                 //Calculo de Importe de la tasa MAP
                 double tasaMAP_Importe=(tasaMAP*(cargoVariabledeEnergia_cs_G)); //$

                 //Calculo IVA
                 double ivaBasico=(iva_G*subTotal);

                 //Calculo Tasa de Fiscalizacion
                 //ss=cs+subsidio --> cs=(ss-subsidio)
                 double tasaFiscalizacion_Importe=(tasaFiscalizacion*(Basico_cs_G)); //$

                 //SubTotal ECSAPEM
                 double subTotalECSAPEM=(subTotal+tasaMAP_Importe+ivaBasico+tasaFiscalizacion_Importe);

                 //Realizamos la operación para calcular el costo total
                 //double resultado = (resul_parcial * 8.9); //[kWh] * [$/kWh]=[$] Estaba en la version anterior
                 double resultado=subTotalECSAPEM;
                 if (resultado > 0) {
                     String resul_finalKWH = Double.toString(resul_parcial);
                     String resultado_final = String.format("%.2f", resultado);
                     resultadoKWH.setText(resul_finalKWH);
                     resultadoParcial.setText(resultado_final);
                     Toast.makeText(getApplicationContext(), "Si Incluye Impuestos y Cargos Fijos", Toast.LENGTH_SHORT).show();
                  } else {
                     resultadoKWH.setText(" ");
                     resultadoParcial.setText(" ");
                     Toast.makeText(getApplicationContext(), "ERROR: Ingrese Correctamente los Datos", Toast.LENGTH_SHORT).show();
                  }
             }
        }catch(Exception e){
              Toast.makeText(getApplicationContext(), "ERROR: Ingrese los Datos Requeridos", Toast.LENGTH_SHORT).show();
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
                startActivity(intent);
                return true;

            case R.id.opcion2:
                intent= new Intent(MainActivity.this, MisConsumosActivity.class);
                startActivity(intent);
                return true;

            case R.id.opcion3:
                intent= new Intent(MainActivity.this, AyudaActivity.class);
                startActivity(intent);
                return true;

            case R.id.opcion4:
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater imagenAlerta = LayoutInflater.from(MainActivity.this);
                alerta.setView(imagenAlerta.inflate(R.layout.acerca, null));

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Acerca");
                titulo.show();
             default:
                return super.onOptionsItemSelected(item);
        }
    }
}
