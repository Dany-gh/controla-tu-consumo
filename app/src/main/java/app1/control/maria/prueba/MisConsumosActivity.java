package app1.control.maria.prueba;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MisConsumosActivity extends Activity {

    ListView lista1;
    ArrayList<String> datos;
    String[] arreglo;
    protected Object mActionMode;
    public int selectedItem=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_consumos);

      cargar();
      lista1= (ListView)findViewById(R.id.lista);
      lista1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

      lista1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
             if(mActionMode != null){
                 return false;
             }

             selectedItem=position;
             mActionMode = MisConsumosActivity.this.startActionMode(amc);
             view.setSelected(true);
             return true;
          }
      });

   }

   public void cargar() {

       BaseHelper basehelper = new BaseHelper(this, "CONSUMO", null, 6);

       SQLiteDatabase db = basehelper.getReadableDatabase();

       if (db != null) {
           String sql = "select * from consumos";
           Cursor c = db.rawQuery(sql,null);
           //cantidad de registros
           int cantidad= c.getCount();
           int i=0;

           //Pasar los datos a un array
           arreglo= new String[cantidad];

           if(c.moveToFirst()){

               do{
                   //Mostramos los datos de las columnas de la BD
                   String linea = c.getInt(0)+ "             " +c.getString(1)+ "             " +c.getString(2)+ "   " +c.getString(3);
                   arreglo[i]=linea;
                   i++;

               }while(c.moveToNext());
           }

           ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arreglo);
           final ListView lista1 = (ListView)findViewById(R.id.lista);
           lista1.setAdapter(adaptador);
       }

    }

    // Metodos para borrar registros
    private ActionMode.Callback amc = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.mis_consumos,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.borrar:
                    borrar();
                    mode.finish();
                        return true;
                    default:
                        return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode=null;
            selectedItem=-1;
        }
    };

    public void borrar(){
        BaseHelper basehelper = new BaseHelper(this, "CONSUMO", null, 6);
        SQLiteDatabase db = basehelper.getReadableDatabase();

        int id = Integer.parseInt(arreglo [selectedItem].split(" ")[0]);

        if (db != null) {
            long res = db.delete("consumos","Id="+ id, null);

            if(res > 0){
                Toast.makeText(this,"Registro Eliminado",Toast.LENGTH_SHORT).show();
                cargar();
            }
        }
    }
}