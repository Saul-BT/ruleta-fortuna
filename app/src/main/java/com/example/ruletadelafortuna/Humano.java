package com.example.ruletadelafortuna;

import android.graphics.drawable.Drawable;

public class Humano extends Jugador {
    public Humano(String nombre, Drawable avatar) {
        this.nombre = nombre;
        this.avatar = avatar;
    }

    @Override
    public void tirarRuleta(int fuerza) {

    }
}
