package com.example.ruletadelafortuna;

import android.content.Context;
import android.os.Parcelable;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

public abstract class Jugador extends ViewModel implements Parcelable {
    String nombre;
    int avatarId;

    public int dineroGanado = 0;

    abstract public void tirarRuleta(Button bTirar);

    public String getNombre() { return nombre; }
    public int getAvatar() { return avatarId; }
}
