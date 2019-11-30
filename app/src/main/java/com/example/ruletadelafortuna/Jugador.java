package com.example.ruletadelafortuna;

import android.graphics.drawable.Drawable;

public abstract class Jugador {
    String nombre;
    Drawable avatar;
    int dineroGanado = 0;

    abstract public void tirarRuleta(int fuerza);
    public String getNombre() { return nombre; }
    public Drawable getAvatar() { return avatar; }
    public int getDineroGanado() { return dineroGanado; }
}
