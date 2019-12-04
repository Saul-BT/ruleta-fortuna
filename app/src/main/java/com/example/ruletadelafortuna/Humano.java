package com.example.ruletadelafortuna;

import android.graphics.drawable.Drawable;
import android.os.Parcel;

public class Humano extends Jugador {
    public Humano(String nombre, int avatarId) {
        this.nombre = nombre;
        this.avatarId = avatarId;
    }

    @Override
    public void tirarRuleta(int fuerza) {

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
        dest.writeByte(this.esJugadorActual ? (byte) 1 : (byte) 0);
    }

    protected Humano(Parcel in) {
        this.nombre = in.readString();
        this.avatarId = in.readInt();
        this.dineroGanado = in.readInt();
        this.esJugadorActual = in.readByte() != 0;
    }

    public static final Creator<Humano> CREATOR = new Creator<Humano>() {
        @Override
        public Humano createFromParcel(Parcel source) {
            return new Humano(source);
        }

        @Override
        public Humano[] newArray(int size) {
            return new Humano[size];
        }
    };
}
