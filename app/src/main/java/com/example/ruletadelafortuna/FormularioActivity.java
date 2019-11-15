package com.example.ruletadelafortuna;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
            String[] datosValidados = devolverDatosValidados();
            boolean j2esBot = ((RadioButton) findViewById(R.id.rbDosJuagaores)).isChecked();
            boolean j3esBot = ((RadioButton) findViewById(R.id.rbTresJugadores)).isChecked();

            i.putExtra("player1", datosValidados[0]);
            i.putExtra("player2", datosValidados[1]);
            i.putExtra("player3", datosValidados[2]);

            i.putExtra("player2esBot", j2esBot);
            i.putExtra("player3esBot", j3esBot);

            startActivity(i);
        } catch (Exception e) {
            new AlertDialog.Builder(this)
                    .setTitle("Error").setMessage(e.getMessage()).create().show();
        }
    }

    private String[] devolverDatosValidados() throws Exception {

        List<String> nombresBot = new ArrayList<String>() {{
                add("HAL");        add("C3P0");
                add("BB-8");       add("Alita");
                add("María");      add("Sonny");
                add("R2-D2");      add("TC-14");
                add("BB-9E");      add("Skynet");
                add("Bender");     add("WALL-E");
                add("Marvin");     add("Rachael");
                add("Chappie");    add("RoboCop");
                add("Destructor"); add("Ex Machina");
                add("Mazinger-Z"); add("Droide cangrejo");
        }};
        Collections.shuffle(nombresBot);
        Random rand = new Random();
        String nombreRobot1 = nombresBot.get(rand.nextInt(nombresBot.size()));
        nombresBot.remove(nombreRobot1);
        String nombreRobot2 = nombresBot.get(rand.nextInt(nombresBot.size()));
        nombresBot.remove(nombreRobot2);

        boolean j2esBot = !((RadioButton) findViewById(R.id.rbDosJuagaores)).isChecked();
        boolean j3esBot = !((RadioButton) findViewById(R.id.rbTresJugadores)).isChecked();

        EditText tvNombreJugador1 = findViewById(R.id.etNombreJ1);
        EditText tvNombreJugador2 = findViewById(R.id.etNombreJ2);
        EditText tvNombreJugador3 = findViewById(R.id.etNombreJ3);

        String[] datos = {
                tvNombreJugador1.getText().toString(),
                j2esBot ? nombreRobot1 : tvNombreJugador2.getText().toString(),
                j3esBot ? nombreRobot2 : tvNombreJugador3.getText().toString(),
        };

        for (String dato : datos)
            if (dato.isEmpty() || dato.equals("0"))
                throw new Exception("La información introducida no es válida");

        return datos;
    }

    public void procesarRB(View v) {
        RadioButton rb1 = findViewById(R.id.rbUnJugador);
        RadioButton rb2 = findViewById(R.id.rbDosJuagaores);
        RadioButton rb3 = findViewById(R.id.rbTresJugadores);

        EditText tvNombreJugador1 = findViewById(R.id.etNombreJ1);
        EditText tvNombreJugador2 = findViewById(R.id.etNombreJ2);
        EditText tvNombreJugador3 = findViewById(R.id.etNombreJ3);

        tvNombreJugador1.setVisibility(View.VISIBLE);
        tvNombreJugador2.setVisibility(View.VISIBLE);
        tvNombreJugador3.setVisibility(View.VISIBLE);

        if (rb1.isChecked()) {
            tvNombreJugador2.setVisibility(View.GONE);
            tvNombreJugador3.setVisibility(View.GONE);
        }
        else if (rb2.isChecked())
            tvNombreJugador3.setVisibility(View.GONE);
    }
}
