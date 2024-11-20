package br.gov.sp.fatecdiadema.bancodedados;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    DatabaseHelper dbHelper;
    TextInputEditText textUsuario, textSenha;
    Button btnEntrar, btnGerenciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);

        textUsuario = findViewById(R.id.textInEdUserT1);
        textSenha = findViewById(R.id.textInEdSenhaT1);
        btnGerenciar = findViewById(R.id.btnConsultarT1);
        btnEntrar = findViewById(R.id.btnEntrarT1);

        // Vai para tela de Gerenciamento
        btnGerenciar.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        btnEntrar.setOnClickListener(v -> {
            String usuario = textUsuario.getText().toString();
            String senha = textSenha.getText().toString();

            if(usuario.isEmpty() || senha.isEmpty()){
                Toast.makeText(Login.this,
                        "Preencha todos os campos.",
                        Toast.LENGTH_LONG).show();
                return;
            }

            Cursor cursor = dbHelper.obterSenhaPorUsuario(usuario);

            if(cursor !=null && cursor.moveToFirst()){

            String senhaArmazenada = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_3));

            if(senhaArmazenada.equals(senha)){
                Intent intent = new Intent(this, MainActivityOk.class);
                startActivity(intent);
            }else {
                Toast.makeText(Login.this,
                        "Senha incorreta ",
                        Toast.LENGTH_SHORT).show();
            }cursor.close();
            }else{
                Intent intent = new Intent(this, MainActivityErro.class);
                startActivity(intent);
            }
        });

    }
}