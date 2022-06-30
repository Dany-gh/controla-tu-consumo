package app1.control.maria.prueba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseHelper extends SQLiteOpenHelper {

    String tabla = "CREATE TABLE consumos (Id INTEGER PRIMARY KEY AUTOINCREMENT, ConsumoKWH Text, ConsumoParcial Text, Fecha text)";

    public BaseHelper(Context context, String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "CONSUMO", null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table consumos");
        db.execSQL(tabla);
    }
}
