package com.example.ruletadelafortuna;

import android.graphics.Color;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.Collator;
import java.util.ArrayList;

public class Panel {
    private final int MAX_LETRAS = 48;
    private MainActivity activity;
    private ArrayList<TextView> celdas;
    private String frase;

    public Panel(MainActivity activity, String frase) {
        this.activity = activity;
        this.celdas =  new ArrayList<>();
        this.frase = frase.toUpperCase();
    }


    public boolean revelaLetra(String letra) {
        if (!this.frase.contains(letra)) return false;

        letra = letra.toUpperCase();
        Collator espCollator = Collator.getInstance();
        espCollator.setStrength(Collator.PRIMARY);

        for (TextView celda : this.celdas) {
            String letraCelda = celda.getText().toString();
            if (espCollator.compare(letraCelda, letra) == 0)
                celda.setTextColor(Color.parseColor("#555555"));
        }

        return true;
    }


    public void rellenaPanel(TableLayout contenedor) throws Exception {
        if (this.frase.isEmpty() || this.frase.length() >= MAX_LETRAS)
            throw new Exception("La frase es inválida");

        char[] caracteres = String.format("%-"+MAX_LETRAS+"s", this.frase).toCharArray();
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
}
