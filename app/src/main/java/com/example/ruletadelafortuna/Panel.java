package com.example.ruletadelafortuna;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.ArrayList;

public class Panel {
    private final int MAX_LETRAS = 48;
    private MainActivity activity;
    private ArrayList<TextView> celdas;
    private String frase;

    public Panel(MainActivity activity, String frase) {
        this.activity = activity;
        this.frase = frase.toUpperCase();
    }

    public void generaCeldas() throws Exception {
        if (this.frase.isEmpty() || this.frase.length() >= MAX_LETRAS)
            throw new Exception("La frase es inválida");

        String[] caracteres = this.frase.split("");
        ArrayList<TextView> celdas = new ArrayList<>();

        for (String caracter : caracteres) {
            TextView celda = (TextView) this.activity.getLayoutInflater().inflate(R.layout.celda_panel, null);
            if (!caracter.equals(" ")) {
                celda.setTextColor(Color.parseColor("red"));
                celda.setBackgroundColor(activity.getResources().getColor(R.color.colorCeldaConLetra));
            }
            celda.setText(caracter);

            celdas.add(celda);
        }
        this.celdas = celdas;
    }

    public boolean revelaLetras(String letra) {
        if (!this.frase.contains(letra)) return false;

        letra = letra.toUpperCase();
        for (TextView celda : this.celdas)
            if (celda.getText().toString().equals(letra))
                celda.setTextColor(Color.parseColor("black"));

        return true;
    }

    public boolean rellenaPanel(TableLayout contenedor) {
        int nHijos = contenedor.getChildCount();

        for (int i = 0, n = 0; i < nHijos; i++) {
            for (int j = 0; j < 12; j++, n++) {
                ((TableRow) contenedor.getChildAt(i)).addView(celdas.get(n));
            }
        }
        return true;
    }
}
