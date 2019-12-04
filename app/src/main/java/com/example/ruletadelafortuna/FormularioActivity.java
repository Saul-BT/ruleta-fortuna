package com.example.ruletadelafortuna;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FormularioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
    }

    public void procesarInfo(View v) {
        // MEH MEH MEH
        Intent i = new Intent(this, JuegoActivity.class);

        try {
            /*String[] datosValidados = devolverDatosValidados();
            boolean j2esBot = ((RadioButton) findViewById(R.id.rbDosJuagaores)).isChecked();
            boolean j3esBot = ((RadioButton) findViewById(R.id.rbTresJugadores)).isChecked();

            i.putExtra("player1", datosValidados[0]);
            i.putExtra("player2", datosValidados[1]);
            i.putExtra("player3", datosValidados[2]);

            i.putExtra("player2esBot", j2esBot);
            i.putExtra("player3esBot", j3esBot);*/

            Jugador[] jugadores = devolverDatosValidados();
            i.putExtra("Jugadores", jugadores);

            startActivity(i);
        } catch (Exception e) {
            new AlertDialog.Builder(this)
                    .setTitle("Error").setMessage(e.getMessage()).create().show();
        }
    }

    private Jugador[] devolverDatosValidados() throws Exception {
        boolean j2esBot = false;
        boolean j3esBot = false;

        RadioButton rb1Jugadores = findViewById(R.id.rbUnJugador);
        RadioButton rb2Jugadores = findViewById(R.id.rbDosJuagaores);

        if (rb2Jugadores.isChecked()) j3esBot = true;
        else if (rb1Jugadores.isChecked()) {
            j2esBot = true;
            j3esBot = true;
        }

        boolean esFemenino1 = ((RadioButton) findViewById(R.id.rbFemenino1)).isChecked();
        boolean esFemenino2 = ((RadioButton) findViewById(R.id.rbFemenino2)).isChecked();
        boolean esFemenino3 = ((RadioButton) findViewById(R.id.rbFemenino3)).isChecked();

        int avatarId1 = esFemenino1
                            ? R.drawable.avatar_femenino
                            : R.drawable.avatar_masculino;
        int avatarId2 = esFemenino2
                            ? R.drawable.avatar_femenino
                            : R.drawable.avatar_masculino;
        int avatarId3 = esFemenino3
                            ? R.drawable.avatar_femenino
                            : R.drawable.avatar_masculino;

        String nombreJugador1 = ((EditText) findViewById(R.id.etNombreJ1)).getText().toString();
        String nombreJugador2 = ((EditText) findViewById(R.id.etNombreJ2)).getText().toString();
        String nombreJugador3 = ((EditText) findViewById(R.id.etNombreJ3)).getText().toString();

        Jugador[] jugadores = {
                new Humano(nombreJugador1, avatarId1),

                j2esBot
                        ? new Bot()
                        : new Humano(nombreJugador2, avatarId2),
                j3esBot
                        ? new Bot()
                        : new Humano(nombreJugador3, avatarId3),
        };

        for (Jugador jugador : jugadores)
            if (jugador.getNombre().isEmpty())
                throw new Exception("La información introducida no es válida");

        return jugadores;
    }

    public void procesarRB(View v) {
        RadioButton rb1 = findViewById(R.id.rbUnJugador);
        RadioButton rb2 = findViewById(R.id.rbDosJuagaores);
        RadioButton rb3 = findViewById(R.id.rbTresJugadores);

        LinearLayout contenedorJugador1 = findViewById(R.id.contenedorInfoJ1);
        LinearLayout contenedorJugador2 = findViewById(R.id.contenedorInfoJ2);
        LinearLayout contenedorJugador3 = findViewById(R.id.contenedorInfoJ3);

        contenedorJugador1.setVisibility(View.VISIBLE);
        contenedorJugador2.setVisibility(View.VISIBLE);
        contenedorJugador3.setVisibility(View.VISIBLE);

        if (rb1.isChecked()) {
            contenedorJugador2.setVisibility(View.GONE);
            contenedorJugador3.setVisibility(View.GONE);
        }
        else if (rb2.isChecked())
            contenedorJugador3.setVisibility(View.GONE);
    }
}
