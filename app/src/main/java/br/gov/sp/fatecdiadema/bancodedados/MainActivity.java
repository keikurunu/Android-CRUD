package br.gov.sp.fatecdiadema.bancodedados;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    TextInputEditText textUsuario, textSenha;
    TextView textResultado;
    Button btnGravar, btnConsultar, btnAtualizar, btnDeletar, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        textUsuario = findViewById(R.id.textInEdUserT1);
        textSenha = findViewById(R.id.textInEdSenhaT1);
        textResultado = findViewById(R.id.textResultadoT1);
        btnGravar = findViewById(R.id.btnGravarT1);
        btnConsultar = findViewById(R.id.btnConsultarT1);
        btnAtualizar = findViewById(R.id.btnAtualizarT1);
        btnDeletar = findViewById(R.id.btnDeletarT1);
        btnVoltar = findViewById(R.id.btnVoltarT1);

        // Trata btnGravar como ouvinte:
        btnGravar.setOnClickListener(v -> {
            String usuario = textUsuario.getText().toString();
            String senha = textSenha.getText().toString();

            if(usuario.isEmpty() || senha.isEmpty()){
                Toast.makeText(MainActivity.this,
                        "Preencha todos os campos.",
                        Toast.LENGTH_LONG).show();
                return;
            }
            // Caso sucesso - Grava no Banco de Dados:
            try {
                int numSenha = Integer.parseInt(senha);

                if(dbHelper.inserirDados(usuario, numSenha)){
                    // Sucesso na gravação dos dados:
                    Toast.makeText(MainActivity.this,
                            "Dados inseridos com sucesso!",
                            Toast.LENGTH_LONG).show();
                }else{
                    // Insucesso na gravação dos dados:
                    Toast.makeText(MainActivity.this,
                            "Erro ao inserir dados.",
                            Toast.LENGTH_LONG).show();
                }
            }catch (NumberFormatException e){
                Toast.makeText(MainActivity.this,
                        "Senha Invalida para Gravação",
                        Toast.LENGTH_LONG).show();
            }
        });

        // Trata btnConsultar como ouvinte:
        btnConsultar.setOnClickListener(v -> {
            String usuario = textUsuario.getText().toString();

            if(usuario.isEmpty()){
                Toast.makeText(MainActivity.this,
                        "Usuario Invalido",
                        Toast.LENGTH_LONG).show();
                return;
            }

            Cursor cursor = dbHelper.obterSenhaPorUsuario(usuario);
            if (cursor != null && cursor.moveToFirst()){
                String senha = cursor.getString(0);
                textResultado.setText("Senha: " + senha);
                cursor.close();
            }else{
                Toast.makeText(MainActivity.this,
                        "Usuario não encontrado.",
                        Toast.LENGTH_LONG).show();
            }

        });

        // Trata btnAtualizar como ouvinte:
        btnAtualizar.setOnClickListener(v -> {
            String usuario = textUsuario.getText().toString();
            String senha = textSenha.getText().toString();

            if (usuario.isEmpty() || senha.isEmpty()) {
                Toast.makeText(MainActivity.this,
                        "Preencha todos os campos.",
                        Toast.LENGTH_LONG).show();
                return;
            }

            try {
                int novaSenha = Integer.parseInt(senha);

                if (dbHelper.atualizarDados(usuario, novaSenha)) {
                    Toast.makeText(MainActivity.this,
                            "Dados atualizados com sucesso!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Usuário não encontrado para atualização.",
                            Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this,
                        "Senha inválida para atualização.",
                        Toast.LENGTH_LONG).show();
            }
        });


        // Trata btnDeletar como ouvinte:
        btnDeletar.setOnClickListener(v -> {
            String usuario = textUsuario.getText().toString();

            if (usuario.isEmpty()) {
                Toast.makeText(MainActivity.this,
                        "Usuário inválido.",
                        Toast.LENGTH_LONG).show();
                return;
            }

            if (dbHelper.deletarUsuario(usuario)) {
                Toast.makeText(MainActivity.this,
                        "Usuário deletado com sucesso!",
                        Toast.LENGTH_LONG).show();
                textResultado.setText(""); // Limpa o texto do resultado
                textUsuario.setText(""); // Limpa o campo de usuário
                textSenha.setText(""); // Limpa o campo de senha
            } else {
                Toast.makeText(MainActivity.this,
                        "Erro ao deletar: Usuário não encontrado.",
                        Toast.LENGTH_LONG).show();
            }
        });

        // Volta para tela anterior
        btnVoltar.setOnClickListener(v -> {
            finish();
        });

    }
}