package com.example.ruletadelafortuna;

import android.os.Parcel;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Bot extends Jugador {
    private static ArrayList<String> nombres = new ArrayList<String>() {{
        add("Ash");     add("HAL");     add("RoboCop");
        add("BMO");     add("TARS");    add("AlphaGo");
        add("C3P0");    add("BB-8");    add("Doraemon");
        add("Andy");    add("Alita");   add("Akinator");
        add("María");   add("Sonny");   add("Ex Machina");
        add("R2-D2");   add("TC-14");   add("Afrodita-a");
        add("BB-9E");   add("Skynet");  add("Mazinger-Z");
        add("Bender");  add("WALL-E");  add("Destructor");
        add("Marvin");  add("Roomba");  add("AlphaGo Zero");
        add("Rachael"); add("Chappie"); add("J.A.R.V.I.S.");
    }};

    public Bot() {
        Collections.shuffle(nombres);
        Random rand = new Random();
        this.nombre = nombres.get(rand.nextInt(nombres.size()));
        nombres.remove(this.nombre);

        this.avatarId = R.drawable.avatar_robot;
    }

    @Override
    public void tirarRuleta(Button bTirar) {
        if (bTirar.isEnabled()) bTirar.setEnabled(false);
        bTirar.performClick();
    }

    public int pedirLetra(Panel panel, TextView narrador) {
        String mensaje;
        char[] consonantes = "BCDFGHJKLMÑNPQRSTVWXYZ".toCharArray();
        char consonanteAlAzar = consonantes[(int) (Math.random() * consonantes.length)];
        int nCoincidencias = panel.revelaLetra(String.valueOf(consonanteAlAzar));

        if (nCoincidencias == -1)
            mensaje = "La " +consonanteAlAzar+" ya se ha dicho";
        else
            mensaje = "Hay "+nCoincidencias+" "+consonanteAlAzar;

        narrador.setText(mensaje);
        return nCoincidencias;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeInt(this.avatarId);
        dest.writeInt(this.dineroGanado);
    }

    protected Bot(Parcel in) {
        this.nombre = in.readString();
        this.avatarId = in.readInt();
        this.dineroGanado = in.readInt();
    }

    public static final Creator<Bot> CREATOR = new Creator<Bot>() {
        @Override
        public Bot createFromParcel(Parcel source) {
            return new Bot(source);
        }

        @Override
        public Bot[] newArray(int size) {
            return new Bot[size];
        }
    };
}
