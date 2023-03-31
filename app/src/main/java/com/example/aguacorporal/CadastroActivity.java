package com.example.aguacorporal;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CadastroActivity extends AppCompatActivity {

    public static SQLiteDatabase bancoCadastros;
    public ListView listCadastros;

    public ArrayList<String> idCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        listCadastros = (ListView) findViewById(R.id.listCadastros);



        listCadastros.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                selectDados()
        ));

        Toast.makeText(this, "Clique e segure para apagar", Toast.LENGTH_SHORT).show();

        listCadastros.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                deleteDado(i);
                return true;
            }
        });

    }

    public ArrayList<Atleta> selectDados(){
        bancoCadastros = openOrCreateDatabase("cadastros", MODE_PRIVATE, null);
        idCadastro = new ArrayList<>();
        ArrayList<Atleta> atletas = new ArrayList<Atleta>();
        Cursor cursor = bancoCadastros.query("atletas",null,null,null,null,null,null);
        if (cursor != null){
            while(cursor.moveToNext()){
                String nome = cursor.getString(cursor.getColumnIndex("NOME"));
                int idade = cursor.getInt(cursor.getColumnIndex("IDADE"));
                double peso = cursor.getDouble(cursor.getColumnIndex("PESO"));

                Atleta cadastrado = new Atleta(peso, idade, nome);
                atletas.add(cadastrado);
                idCadastro.add(cursor.getString(cursor.getColumnIndex("ID")));
            }
            cursor.close();
        }
        bancoCadastros.close();
        return atletas;
    }

    public void deleteDado(Integer i){
        bancoCadastros = openOrCreateDatabase("cadastros", MODE_PRIVATE, null);
        String sql = "DELETE FROM atletas WHERE ID = ?";
        SQLiteStatement stmt = bancoCadastros.compileStatement(sql);
        stmt.bindString(1, idCadastro.get(i));
        stmt.executeUpdateDelete();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
        bancoCadastros.close();
        Toast.makeText(this, "Item excluído", Toast.LENGTH_LONG).show();
    }

}
