package br.gov.sp.fatecdiadema.bancodedados;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivityErro extends AppCompatActivity {

    Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_erro);

        btnVoltar = findViewById(R.id.btnFecharT3);

        btnVoltar.setOnClickListener(v -> {
            finish();
        });

    }
}