package com.example.ruletadelafortuna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class JuegoActivity extends AppCompatActivity implements Animation.AnimationListener {

    int grados = 0;
    Panel panel;
    boolean girando;
    ImageButton b_Start;
    ImageView ruletaImg;
    TextView etiNarrador;

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
        setContentView(R.layout.activity_juego);
        Bundle playersBundle = getIntent().getExtras();


            ((TextView) findViewById(R.id.nombrePlayer1))
                    .setText(playersBundle.getString("player1"));
            ((TextView) findViewById(R.id.nombrePlayer2))
                    .setText(playersBundle.getString("player2"));
            ((TextView) findViewById(R.id.nombrePlayer3))
                    .setText(playersBundle.getString("player3"));


        b_Start = findViewById(R.id.bTiraRuleta);
        ruletaImg = findViewById(R.id.RuletaImagen);
        etiNarrador = findViewById(R.id.tvMensajePresentador);
        panel = new Panel(this);
        ((TextView) findViewById(R.id.tvPista)).setText(panel.getPistaActual());

        try {
            panel.rellenaPanel((TableLayout) findViewById(R.id.tlPanel));

            Log.i("AAAA", "GUAY");
        } catch (Exception e) {
            Log.i("AAAA", e.toString());
        }
    }

    public void onClickButtonRotation(View v){
        if (girando) return;
        MediaPlayer mp = MediaPlayer.create(this, R.raw.girar);
        ImageButton play_button = this.findViewById(R.id.bTiraRuleta);

        int ran = new Random().nextInt(360) + 360 * 2;
        RotateAnimation rotateAnimation = new RotateAnimation(
                this.grados,
                this.grados + ran,
                1, 0.5f,
                1, 0.5f
        );

        rotateAnimation.setAnimationListener(this);
        this.grados = (this.grados + ran) % 360;
        rotateAnimation.setDuration(2100);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        ruletaImg.setAnimation(rotateAnimation);
        ruletaImg.startAnimation(rotateAnimation);
        mp.start();
   }


    @Override
    public void onAnimationStart(Animation animation) {
        girando = true;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Toast.makeText(this, "Has caido en "+((Gajo)gajos[grados / 15]).getValor(),
                Toast.LENGTH_LONG).show();

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Pide una letra");
        try {
            startActivityForResult(intent, 2);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Tu dispositivo es un plátano",
                    Toast.LENGTH_SHORT).show();
        }
        girando = false;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2: {
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String mensaje;
                    String letra = String.valueOf(result.get(0).charAt(0));
                    int nCoincidencias = panel.revelaLetra(letra);

                    if (nCoincidencias == -1)
                        mensaje = "La " +letra+" ya se ha dicho";
                    else
                        mensaje = "Hay "+nCoincidencias+" "+letra;

                    etiNarrador.setText(mensaje);
                }
                break;
            }
        }
    }
}
