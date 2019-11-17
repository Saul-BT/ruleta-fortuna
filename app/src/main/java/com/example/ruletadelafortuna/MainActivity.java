package com.example.ruletadelafortuna;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void iniciarJuego(View v) {
        Intent i = new Intent(this, FormularioActivity.class);
        startActivity(i);
    }
}
