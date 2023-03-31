package com.example.aguacorporal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public SQLiteDatabase bancoCadastros;
    public static EditText textPeso;
    public static EditText textIdade;
    private TextView textResult;
    private Button buttonCalc;
    private ImageButton buttonClr;
    private ImageButton buttonHist;
    public static Button buttonCadastro;
    public static EditText textNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textPeso = (EditText) findViewById(R.id.editPeso);
        textIdade = (EditText) findViewById(R.id.editIdade);
        textResult = (TextView) findViewById(R.id.showResult);
        buttonCalc = (Button) findViewById(R.id.buttonCalc);
        buttonClr = (ImageButton) findViewById(R.id.limparButton);
        buttonCadastro = (Button) findViewById(R.id.buttonCadastro);
        buttonHist = (ImageButton) findViewById(R.id.buttonHist);
        textNome = (EditText) findViewById(R.id.editName);
        criarBanco();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        buttonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(textIdade.getText().toString()) || TextUtils.isEmpty(textPeso.getText().toString())){
                    Toast.makeText(MainActivity.this, "Preencha a idade e o peso", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    calculaQTD();
                }
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        buttonClr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpaCampos();
            }
        });

        buttonHist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CadastroActivity.class));
            }
        });

        buttonCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(textIdade.getText().toString()) || TextUtils.isEmpty(textPeso.getText().toString()) || TextUtils.isEmpty(textNome.getText().toString())){
                    Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    cadastrarBanco();
                    Toast.makeText(MainActivity.this, "Usuário cadastrado com sucesso", Toast.LENGTH_LONG).show();
                }
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

    }
    public void calculaQTD(){
            float peso = Float.parseFloat(textPeso.getText().toString());
            int idade = Integer.parseInt(textIdade.getText().toString());
            
            if (idade <= 17){
                textResult.setText("Resultado: " + peso*40 + " ml");
            } else if (idade >= 18 && idade <= 55) {
                textResult.setText("Resultado: " + peso*40 + " ml");
            } else if (idade >= 56 && idade <= 65) {
                textResult.setText("Resultado: " + peso*30 + " ml");
            } else if (idade >= 66) {
                textResult.setText("Resultado: " + peso*25 + " ml");
            }
    }

    public void limpaCampos(){
        textResult.setText("");
        textPeso.setText("");
        textIdade.setText("");
        textNome.setText("");
    }

    public void criarBanco(){
        try {
            bancoCadastros = openOrCreateDatabase("cadastros", MODE_PRIVATE, null);
            bancoCadastros.execSQL("CREATE TABLE IF NOT EXISTS atletas("
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT"
                    + ", NOME VARCHAR"
                    + ", IDADE INTEGER"
                    + ", PESO REAL)");
            bancoCadastros.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cadastrarBanco(){
            bancoCadastros = openOrCreateDatabase("cadastros", MODE_PRIVATE,null);
            String nome = textNome.getText().toString();
            nome = '"' + nome + '"';
            int idade = Integer.parseInt(textIdade.getText().toString());
            double peso = Double.parseDouble(textPeso.getText().toString());
            String sql = "INSERT INTO atletas(nome, idade, peso) VALUES(" + nome + "," + idade + ","+ peso + ")";
            bancoCadastros.execSQL(sql);
            bancoCadastros.close();

    }
}