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

        this.frases = new LinkedList<>(Arrays.asList(
                this.activity.getResources().getStringArray(R.array.frases)));
        this.pistas = new LinkedList<>(Arrays.asList(
                this.activity.getResources().getStringArray(R.array.pistas)));

        // Llamada al metodo generaFraseYPista cuando se crea la instancia
        generaFraseYPista();
    }


    /**
     * Método que pinta las letras identificadas y el numero de ellas
     * @param letra que se desea revelar
     * @return N donde n es el número de letras pintadas o
     *         -1 si la letra ya ha sido revelada
     */
    public int revelaLetra(String letra) {
        int nLetras = 0;
        letra = letra.toUpperCase();

        if (!this.fraseActual.contains(letra)) return nLetras;

        Collator espCollator = Collator.getInstance();
        espCollator.setStrength(Collator.PRIMARY);

        for (TextView celda : this.celdas) {

            String letraCelda = celda.getText().toString();
            if (espCollator.compare(letraCelda, letra) == 0) {
                int colorTextoCelda = this.activity.getResources()
                        .getColor(R.color.colorTextoCeldaConLetra);

                if (celda.getCurrentTextColor() == colorTextoCelda)
                    return -1;

                celda.setTextColor(colorTextoCelda);
                nLetras++;
            }
        }
        return nLetras;
    }


    /**
     * Método que rellena el panel con las letras (transparentes) de la frase
     * @param contenedor TableView
     * @throws Exception si fraseActual está vacia o supera el límite de letras
     */
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


    /**
     * Método que genera un par frase-pista aleatorio
     * fraseActual y pistaActual tomarán esos valores
     */
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
