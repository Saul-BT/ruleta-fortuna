package com.example.ruletadelafortuna;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int grados = 0;
    Button b_Start;
    ImageView RuletaImg;
    // Pasar a enum
    Object[] gajos = {
            Gajo.GRAN_PREMIO, Gajo.CIEN, Gajo.AYUDA_FINAL,
            Gajo.QUIEBRA, Gajo.VEINTICINCO, Gajo.CERO,
            Gajo.CINCUENTA, Gajo.COMODIN, Gajo.INTERROGACION,
            Gajo.PIERDE_TURNO, Gajo.SETENTA_Y_CINCO, Gajo.ME_LO_QUEDO,
            Gajo.CIEN, Gajo.SETENTA_Y_CINCO, Gajo.PREMIO,
            Gajo.QUIEBRA, Gajo.DOBLE_LETRA, Gajo.SETENTA_Y_CINCO,
            Gajo.CIENTO_CINCUENTA, Gajo.CINCUENTA, Gajo.SETENTA_Y_CINCO,
            Gajo.PIERDE_TURNO, Gajo.CIENTO_CINCUENTA, Gajo.GRAN_PREMIO,
    };

    /**
     private String premios[] =
     {       "Kit de cocina valorado en 200€",
     "Playstation 4",
     "XBOX 360",
     "IPHONE X",
     "Ordenador GAMING valorado en 1000€",
     "Viaje valorado en 500€",
     "Coche valorado en 8000€",
     "Tablet valorada en 200€",
     "Colchón LOMONACO valorada en 500€",
     "Televisión valorada en 600€"};
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_Start = findViewById(R.id.bTiraRuleta);
        RuletaImg = findViewById(R.id.RuletaImagen);

        String fraseDePrueba = "Lorem ipsum dolor sit amet, lorem y tal";
        Panel p = new Panel(this, fraseDePrueba);
        try {
            p.rellenaPanel((TableLayout) findViewById(R.id.tlPanel));
            char[] chars = fraseDePrueba.toUpperCase().toCharArray();

            for (int i = 0; i < chars.length; i++)
                p.revelaLetras(String.valueOf(chars[i]));

            Log.i("AAAA", "GUAY");
        } catch (Exception e) {
            Log.i("AAAA", e.toString());
        }
    }

    public void onClickButtonRotation(View v){
        int ran = new Random().nextInt(360) + 360;
        RotateAnimation rotateAnimation = new RotateAnimation(
                this.grados,
                this.grados + ran,
                1, 0.5f,
                1, 0.5f
        );

        this.grados = (this.grados + ran) % 360;
        rotateAnimation.setDuration(1500);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        RuletaImg.setAnimation(rotateAnimation);
        RuletaImg.startAnimation(rotateAnimation);

        Toast.makeText(this, "Has caido en "+((Gajo)gajos[grados / 15]).getValor(),
                Toast.LENGTH_LONG).show();


   }
}
