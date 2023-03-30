package com.example.aguacorporal;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {

    public static SQLiteDatabase bancoCadastros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        criarBanco();

    }

    public void criarBanco(){
        try {
            bancoCadastros = openOrCreateDatabase("cadastros", MODE_PRIVATE, null);
            bancoCadastros.execSQL("CREATE TABLE IF NOT EXISTS atletas("
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT"
                    + ", NOME VARCHAR"
                    + ", IDADE INTEGER"
                    + ", PESO DOUBLE)");
            bancoCadastros.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
