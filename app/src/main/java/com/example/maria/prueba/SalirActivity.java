package com.example.maria.prueba;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SalirActivity extends AppCompatActivity {

    Button salida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salir);

        salida = (Button)findViewById(R.id.opcion3);
        salida.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //finish();
                //System.exit(0);

                /*Intent intent=new Intent(Intent.ACTION_MAIN);
               intent.addCategory(Intent.CATEGORY_HOME);
               intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent);*/

                /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);*/

               // finishAffinity();
            }
        });
    }



}
