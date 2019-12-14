package com.example.ruletadelafortuna;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Humano extends Jugador {
    public Humano(String nombre, int avatarId) {
        this.nombre = nombre;
        this.avatarId = avatarId;
    }

    @Override
    public void tirarRuleta(Button bTirar) {
        if (!bTirar.isEnabled()) bTirar.setEnabled(true);
    }

    public void pedirLetraPorMicrofono(AppCompatActivity ctx) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Pide una letra");
        try {
            ctx.startActivityForResult(intent, 2);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(ctx.getApplicationContext(),
                    "Tu dispositivo es un plátano",
                    Toast.LENGTH_SHORT).show();
        }
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

    protected Humano(Parcel in) {
        this.nombre = in.readString();
        this.avatarId = in.readInt();
        this.dineroGanado = in.readInt();
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
