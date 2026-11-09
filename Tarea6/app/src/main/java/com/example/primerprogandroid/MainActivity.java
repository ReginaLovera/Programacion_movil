package com.example.primerprogandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    LinearLayout mill;
    TextView res;
    Button btnNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mill = new LinearLayout(this);
        mill.setOrientation(LinearLayout.VERTICAL);
        mill.setBackgroundColor(Color.GRAY);
        mill.setPadding(20, 40, 20, 20);

        btnNombre = new Button(this);
        btnNombre.setText("Mostrar nombre");

        res = new TextView(this);
        res.setTextSize(20);
        res.setTextColor(Color.WHITE);

        btnNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                res.setText("¡Regina Lovera Jerónimo!");
            }
        });

        mill.addView(btnNombre);
        mill.addView(res);

        setContentView(mill);
    }
}