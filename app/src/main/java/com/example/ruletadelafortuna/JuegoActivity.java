package com.example.ruletadelafortuna;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class JuegoActivity extends AppCompatActivity implements Animation.AnimationListener {

    private Handler mHandler;

    private int grados = 0;
    private boolean girando;
    private Gajo gajoActual;
    private Panel panel;
    private Button bTirar;
    private ImageView ruletaImg;
    private TextView etiNarrador;

    private int posJugadorActual;
    private Jugador[] jugadores;
    private TextView[] tvScores;

    private int numLetrasReveladas;

    Gajo[] gajos = {
            Gajo.SETENTA_Y_CINCO, Gajo.CIEN, Gajo.CINCUENTA,
            Gajo.QUIEBRA, Gajo.VEINTICINCO, Gajo.CERO,
            Gajo.CINCUENTA, Gajo.CIENTO_CINCUENTA, Gajo.SETENTA_Y_CINCO,
            Gajo.PIERDE_TURNO, Gajo.SETENTA_Y_CINCO, Gajo.CERO,
            Gajo.CIEN, Gajo.SETENTA_Y_CINCO, Gajo.SETENTA_Y_CINCO,
            Gajo.QUIEBRA, Gajo.CIENTO_CINCUENTA, Gajo.SETENTA_Y_CINCO,
            Gajo.CIENTO_CINCUENTA, Gajo.CINCUENTA, Gajo.SETENTA_Y_CINCO,
            Gajo.PIERDE_TURNO, Gajo.CIENTO_CINCUENTA, Gajo.CERO,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        mHandler = new Handler();
        tvScores = new TextView[]{
                findViewById(R.id.scorePlayer1),
                findViewById(R.id.scorePlayer2),
                findViewById(R.id.scorePlayer3),
        };

        bTirar = findViewById(R.id.bTiraRuleta);
        ruletaImg = findViewById(R.id.RuletaImagen);
        etiNarrador = findViewById(R.id.tvMensajePresentador);

        // Para que decideTurno tome el primer jugador al inicio
        // la variable posJugadorActual debe ser -1
        this.posJugadorActual = -1;

        // Creando el panel y estableciendo la pista
        panel = new Panel(this);
        ((TextView) findViewById(R.id.tvPista)).setText(panel.getPistaActual());

        try {
            panel.rellenaPanel((TableLayout) findViewById(R.id.tlPanel));
        }
        catch (Exception e) {
            new AlertDialog.Builder(this)
                    .setTitle("Error").setMessage(e.getMessage()).create().show();
        }

        Bundle playersBundle = getIntent().getExtras();

        setupPayers(playersBundle);
        decideTurno();
    }


    // Metodo que da estado inicial a los jugadores (avatares y tal)
    private void setupPayers(Bundle playersBundle) {
        // Estableciendo nombre y avatar a los concursantes
        TextView[] tvPlayers = {
                findViewById(R.id.nombrePlayer1),
                findViewById(R.id.nombrePlayer2),
                findViewById(R.id.nombrePlayer3),
        };
        ImageView[] playerAvatars = {
                findViewById(R.id.avatarPlayer1),
                findViewById(R.id.avatarPlayer2),
                findViewById(R.id.avatarPlayer3),
        };

        Parcelable[] jugadoresParcel = (Parcelable[]) playersBundle.get("Jugadores");
        Jugador primerJugador = (Jugador) jugadoresParcel[0];
        this.jugadores = new Jugador[jugadoresParcel.length];

        // Estableciendo nombres y avatares a los jugadores
        for (int i = 0; i < jugadoresParcel.length; i++) {
            Jugador jugador = (Jugador) jugadoresParcel[i];
            jugadores[i] = jugador;

            tvPlayers[i].setText(jugador.getNombre());
            playerAvatars[i].setImageDrawable(getResources().getDrawable(jugador.getAvatar()));
        }
    }


    // Metodo que establece el siguiente turno
    private void decideTurno() {
        posJugadorActual = (posJugadorActual + 1) % jugadores.length;

        int codMorado = this.getResources().getColor(R.color.colorMorado);
        int[][] codigosColorAvatar = {
                {codMorado, this.getResources().getColor(R.color.colorJugador1)},
                {codMorado, this.getResources().getColor(R.color.colorJugador2)},
                {codMorado, this.getResources().getColor(R.color.colorJugador3)},
        };
        GradientDrawable fondo = (GradientDrawable) bTirar.getBackground();
        Jugador jugadorActual = jugadores[posJugadorActual];

        etiNarrador.setText("Tu turno " + jugadorActual.nombre);
        fondo.setColors(codigosColorAvatar[posJugadorActual]);

        jugadorActual.tirarRuleta(bTirar);
    }


    // Metodo que administra el turno del jugador actual
    private void procesaTurno() {
        Jugador jugadorActual = jugadores[posJugadorActual];

        switch (gajoActual) {
            case QUIEBRA: jugadorActual.dineroGanado = 0;
            case PIERDE_TURNO: {
                mHandler.postDelayed(new Runnable() {
                    public void run() { decideTurno(); }
                }, 2000);
                tvScores[posJugadorActual].setText(String.valueOf(jugadorActual.dineroGanado));
                return;
            }
        }

        if (jugadorActual instanceof Humano) {
            ((Humano) jugadorActual).pedirLetraPorMicrofono(this);
        }
        else {
            numLetrasReveladas = ((Bot) jugadorActual).pedirLetra(panel, etiNarrador);
            procesaPuntos();
        }
    }


    // Metodo que establece la pasta del jugador a partir del valor (numérico) del gajo
    private void procesaPuntos() {
        Jugador jugadorActual = jugadores[posJugadorActual];

        if (numLetrasReveladas > 0) {
            if (gajoActual.getValor() instanceof Integer) {
                int valorGajo = (Integer) gajoActual.getValor();
                jugadorActual.dineroGanado += valorGajo * numLetrasReveladas;
            }
            jugadorActual.tirarRuleta(bTirar);
        }
        else {
            mHandler.postDelayed(new Runnable() {
                public void run() { decideTurno(); }
            }, 2000);
        }
        tvScores[posJugadorActual].setText(String.valueOf(jugadorActual.dineroGanado));
    }


    public void onClickButtonRotation(View v){
        if (girando) return;
        MediaPlayer mp = MediaPlayer.create(this, R.raw.girar);
        Button play_button = this.findViewById(R.id.bTiraRuleta);

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
        gajoActual = gajos[grados / 15];
        girando = false;
        procesaTurno();
    }

    @Override
    public void onAnimationRepeat(Animation animation) { }


    // Resultado del intent de grabación de voz
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
                    Jugador jugadorActual = jugadores[posJugadorActual];

                    if (letra.matches("(?i)[AEIOU]")) {
                        if(jugadorActual.dineroGanado > 50) {
                            jugadorActual.dineroGanado -= 50;
                            tvScores[posJugadorActual].setText(String.valueOf(jugadorActual.dineroGanado));
                            numLetrasReveladas = panel.revelaLetra(letra);
                        } else {
                            etiNarrador.setText("No tienes suficiente dinero para comprar vocal");
                        }
                        return;
                    }
                    numLetrasReveladas = panel.revelaLetra(letra);
                    if (numLetrasReveladas == -1)
                        mensaje = "La " + letra + " ya está en el panel";
                    else
                        mensaje = "Hay " + numLetrasReveladas + " " + letra;

                    etiNarrador.setText(mensaje);

                    procesaPuntos();
                }
                break;
            }
            case 3: {
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> aproximaciones = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String frasePanel = panel.getFraseActual()
                            .replaceAll("[^\\w\\sÁÉÍÓÚáéíóú]", "");
                    Collator espCollator = Collator.getInstance();
                    espCollator.setStrength(Collator.PRIMARY);
                    boolean panelResuelto = false;

                    for (String frase : aproximaciones) {
                        panelResuelto = panelResuelto ||
                                0 == espCollator.compare(
                                        frase.toLowerCase(), frasePanel.toLowerCase()
                                );
                    }

                    if (panelResuelto) {
                        panel.resolver();
                        String nombreGranador = jugadores[posJugadorActual].getNombre();

                        new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)
                                .setTitle(nombreGranador+" ganaste la partida \uD83E\uDD73")
                                .setMessage("¿Empezamos una nueva partida?")
                                .setPositiveButton("Sí :D", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) { finish(); }
                                })
                                .setNegativeButton("No D:", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finishAffinity();
                                    }
                                })
                                .create().show();
                    }
                    else {
                        etiNarrador.setText("Casi lo tienes, ánimo");
                    }
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)
            .setTitle("¿Estás seguro?")
            .setMessage("El progreso de la partida actual se perderá")
            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { finish(); }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { }
            })
        .create().show();
    }

    public void resolver(View v) {
        Jugador jugadorActual = jugadores[posJugadorActual];
        if (jugadorActual instanceof Humano) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Pide una letra");
            try {
                startActivityForResult(intent, 3);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(getApplicationContext(),
                        "Tu dispositivo es un plátano",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
