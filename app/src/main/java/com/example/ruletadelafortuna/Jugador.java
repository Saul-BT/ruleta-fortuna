package com.example.ruletadelafortuna;

import android.graphics.drawable.Drawable;
import android.os.Parcelable;

public abstract class Jugador implements Parcelable {
    String nombre;
    int avatarId;

    public int dineroGanado = 0;
    public boolean esJugadorActual = false;

    abstract public void tirarRuleta(int fuerza);
    public String getNombre() { return nombre; }
    public int getAvatar() { return avatarId; }
}
