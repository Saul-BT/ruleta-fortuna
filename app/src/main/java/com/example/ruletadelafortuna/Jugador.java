package com.example.ruletadelafortuna;

import android.graphics.drawable.Drawable;

public abstract class Jugador {
    String nombre;
    Drawable avatar;

    public int dineroGanado = 0;
    public boolean esJugadorActual = false;

    abstract public void tirarRuleta(int fuerza);
    public String getNombre() { return nombre; }
    public Drawable getAvatar() { return avatar; }
}
