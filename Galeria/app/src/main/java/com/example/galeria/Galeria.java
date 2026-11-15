package com.example.galeria;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Galeria extends Activity implements View.OnClickListener {
    Button btnAtras, btnAdelante;
    ImageView iv;
    TextView tvTitulo;

    int imagenes[];
    String titulos[];
    int imagenActual = 0;

    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.galeria);

        imagenes = new int[]{
                R.drawable.flash,
                R.drawable.superman,
                R.drawable.mario
        };

        titulos = new String[]{
                "Flash",
                "Superman",
                "Mario Bros"
        };

        btnAtras = findViewById(R.id.btnatras);
        btnAdelante = findViewById(R.id.btnadelante);
        iv = findViewById(R.id.imagenx);
        tvTitulo = findViewById(R.id.tvtitulo);

        tvTitulo.setText(titulos[imagenActual]);

        btnAdelante.setOnClickListener(this);
        btnAtras.setOnClickListener(this);
    }

    public void onClick(View v){
        if(v.equals(btnAtras)){
            if(imagenActual <= 0){
                imagenActual = 2;
            } else {
                imagenActual = imagenActual - 1;
            }
            iv.setImageDrawable(getDrawable(imagenes[imagenActual]));
            tvTitulo.setText(titulos[imagenActual]);
        }
        if(v.equals(btnAdelante)){
            if(imagenActual >= 2){
                imagenActual = 0;
            } else {
                imagenActual = imagenActual + 1;
            }
            iv.setImageDrawable(getDrawable(imagenes[imagenActual]));
            tvTitulo.setText(titulos[imagenActual]);
        }
    }
}