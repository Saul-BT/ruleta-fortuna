package com.example.ruletadelafortuna;

import android.graphics.Color;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Panel {
    private String fraseActual;
    private String pistaActual;

    private final int MAX_LETRAS = 48;
    private MainActivity activity;
    private List<TextView> celdas;

    private static List<String> frases;
    private static List<String> pistas;

    public Panel(MainActivity activity) {
        this.activity = activity;
        this.celdas =  new ArrayList<>();

        this.frases = new LinkedList<String>(Arrays.asList(
                this.activity.getResources().getStringArray(R.array.frases)));
        this.pistas = new LinkedList<String>(Arrays.asList(
                this.activity.getResources().getStringArray(R.array.pistas)));

        generaFraseYPista();
    }


    public int revelaLetra(String letra) {
        if (!this.fraseActual.contains(letra)) return 0;

        int nLetras = 0;
        letra = letra.toUpperCase();
        Collator espCollator = Collator.getInstance();
        espCollator.setStrength(Collator.PRIMARY);

        for (TextView celda : this.celdas) {
            String letraCelda = celda.getText().toString();
            if (espCollator.compare(letraCelda, letra) == 0) {
                celda.setTextColor(Color.parseColor("#555555"));
                nLetras++;
            }
        }
        return nLetras;
    }


    public void rellenaPanel(TableLayout contenedor) throws Exception {
        if (this.fraseActual.isEmpty() || this.fraseActual.length() >= MAX_LETRAS)
            throw new Exception("La frase es inválida");

        char[] caracteres = String.format("%-"+MAX_LETRAS+"s", this.fraseActual).toCharArray();
        int nFilas = contenedor.getChildCount();

        for (int i = 0, n = 0; i < nFilas; i++) {
            TableRow row = (TableRow) contenedor.getChildAt(i);
            int nCeldas = row.getChildCount();

            for (int j = 0; j < nCeldas; j++, n++) {
                TextView celda = (TextView) row.getChildAt(j);

                if (caracteres[n] != ' ')
                    celda.setBackground(this.activity.getResources().getDrawable(R.drawable.rounded_celda_con_letra));

                celda.setText(String.valueOf(caracteres[n]));
                this.celdas.add(celda);
            }
        }
        revelaLetra(",");
        revelaLetra(".");
    }


    public void generaFraseYPista() {
        int rand = (int) (Math.random() * Panel.frases.size());

        this.fraseActual = Panel.frases.remove(rand).toUpperCase();
        this.pistaActual = Panel.pistas.remove(rand).toUpperCase();
    }


    public String getFraseActual() {
        return fraseActual;
    }

    public String getPistaActual() {
        return pistaActual;
    }
}
