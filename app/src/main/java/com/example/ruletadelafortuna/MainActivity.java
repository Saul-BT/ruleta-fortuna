package com.example.ruletadelafortuna;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                findViewById(R.id.ruletaImagen),
                PropertyValuesHolder.ofFloat("scaleX", 1.05f),
                PropertyValuesHolder.ofFloat("scaleY", 1.05f));
        scaleDown.setDuration(500);

        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);

        scaleDown.start();
    }

    public void iniciarJuego(View v) {
        Intent i = new Intent(this, FormularioActivity.class);
        startActivity(i);
    }

    public void rotar(View v) {
        RotateAnimation animRotate = new RotateAnimation(0f, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        animRotate.setDuration(2000);
        v.startAnimation(animRotate);
    }
}
