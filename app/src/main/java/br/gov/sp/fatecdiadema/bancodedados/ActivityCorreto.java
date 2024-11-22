package br.gov.sp.fatecdiadema.bancodedados;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityCorreto extends AppCompatActivity {

    Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_correto);

        btnVoltar = findViewById(R.id.btnFecharT2);

        btnVoltar.setOnClickListener(v -> {
            finish();
        });

    }
}