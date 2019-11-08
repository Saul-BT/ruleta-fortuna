package com.example.ruletadelafortuna;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Celda {
    public static TextView valueOf(Activity activity, String letra) {
        TextView celda = new TextView(activity);

        if (letra.equals(" ")) {
            celda.setTextColor(activity.getResources().getColor(R.color.colorCeldaVacia));
        }
        else {
            celda.setTextColor(activity.getResources().getColor(R.color.colorCeldaConLetra));
            celda.setBackgroundColor(activity.getResources().getColor(R.color.colorCeldaConLetra));
        }
        celda.setText(letra);
        celda.setGravity(Gravity.CENTER);

        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT, 1f
        );
        params.setMargins(1, 1, 1, 1);
        celda.setLayoutParams(params);

        return celda;
    }

    public static void mostrar(TextView celda) {
        celda.setTextColor(Color.parseColor("black"));
    }
}
