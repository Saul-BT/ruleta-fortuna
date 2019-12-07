package com.example.ruletadelafortuna;

import android.os.Parcelable;
import android.widget.Button;

public abstract class Jugador implements Parcelable {
    String nombre;
    int avatarId;

    public int dineroGanado = 0;
    public boolean esJugadorActual = false;

    abstract public void tirarRuleta(Button bTirar);
    public String getNombre() { return nombre; }
    public int getAvatar() { return avatarId; }
}
