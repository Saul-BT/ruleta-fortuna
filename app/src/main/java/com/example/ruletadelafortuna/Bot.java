package com.example.ruletadelafortuna;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Bot extends Jugador {
    private Context context;
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

    public Bot(Context ctx) {
        this.context = ctx;

        Collections.shuffle(nombres);
        Random rand = new Random();
        this.nombre = nombres.get(rand.nextInt(nombres.size()));
        nombres.remove(this.nombre);

        this.avatar = context.getResources().getDrawable(R.drawable.avatar_robot);
    }

    @Override
    public void tirarRuleta(int fuerza) {

    }
}
