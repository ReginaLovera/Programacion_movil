package com.example.papa;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ClipData;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String NOMBRE_AUTOR = "Regina Lovera Jerónimo";
    private static final int TOTAL_PIEZAS = 5;
    private static final float ESCALA_MAX = 500f;
    private static final int PUNTOS_POR_PIEZA = 20;

    // Tamaños en dp por pieza: [0, Ojos, Nariz, Cabello, Boca, Pies]
    private static final int[] TAMANOS_DP = {0, 72, 72, 110, 72, 105};

    FrameLayout escenario;
    ImageView piezaDisponible;
    Spinner selector;
    Button btnPosiciones;
    TextView resultado, tituloid;

    int piezaActual = 0;
    int tamanoPieza;

    // Índices 1..5 correspondientes a Ojos, Nariz, Cabello, Boca, Pies
    ImageView[] piezasColocadas = new ImageView[TOTAL_PIEZAS + 1];
    float[] posX = new float[TOTAL_PIEZAS + 1];
    float[] posY = new float[TOTAL_PIEZAS + 1];

    // Coordenadas objetivo aproximadas (escala 0 a 500) - Ajustar según tu cuerpo.png
    float[] objetivoX = {0f, 250f, 250f, 250f, 250f, 250f};
    float[] objetivoY = {0f, 185f, 245f, 95f,  315f, 435f};
    float[] tolerancia = {0f, 55f,  45f,  60f,  50f,  60f};

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        escenario = findViewById(R.id.escenario);
        piezaDisponible = findViewById(R.id.piezadisponible);
        selector = findViewById(R.id.selector);
        btnPosiciones = findViewById(R.id.btnposiciones);
        resultado = findViewById(R.id.resultado);
        tituloid = findViewById(R.id.tituloid);

        Typeface fuente = getResources().getFont(R.font.ps);
        tituloid.setTypeface(fuente);
        resultado.setTypeface(fuente);

        tituloid.setText(getString(R.string.titulo_con_autor, NOMBRE_AUTOR));

        tamanoPieza = dp(72);

        for (int i = 1; i <= TOTAL_PIEZAS; i++) {
            posX[i] = -1f;
            posY[i] = -1f;
        }

        String[] opciones = {
                getString(R.string.selecciontexto),
                getString(R.string.opojos),
                getString(R.string.opnariz),
                getString(R.string.opcabello),
                getString(R.string.opboca),
                getString(R.string.oppies)
        };

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                opciones
        );
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selector.setAdapter(adaptador);

        selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                piezaActual = position;
                piezaDisponible.setVisibility(View.GONE);

                if (position >= 1 && position <= TOTAL_PIEZAS) {
                    if (piezasColocadas[position] != null) {
                        escenario.removeView(piezasColocadas[position]);
                        piezasColocadas[position] = null;
                        posX[position] = -1f;
                        posY[position] = -1f;
                    }

                    int tamPx = obtenerTamanoPiezaPx(position);
                    ViewGroup.LayoutParams params = piezaDisponible.getLayoutParams();
                    if (params != null) {
                        params.width = tamPx;
                        params.height = tamPx;
                        piezaDisponible.setLayoutParams(params);
                    }

                    piezaDisponible.setImageResource(obtenerDrawablePieza(position));
                    piezaDisponible.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        piezaDisponible.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (piezaActual < 1 || piezaActual > TOTAL_PIEZAS) {
                        return false;
                    }
                    ClipData datos = ClipData.newPlainText(
                            "pieza",
                            String.valueOf(piezaActual)
                    );
                    View.DragShadowBuilder sombra = new View.DragShadowBuilder(v);
                    v.startDrag(
                            datos,
                            sombra,
                            piezaActual,
                            0
                    );
                    return true;
                }
                return true;
            }
        });

        escenario.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        return event.getLocalState() instanceof Integer;
                    case DragEvent.ACTION_DRAG_ENTERED:
                    case DragEvent.ACTION_DRAG_LOCATION:
                    case DragEvent.ACTION_DRAG_EXITED:
                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;
                    case DragEvent.ACTION_DROP:
                        int tipo = (Integer) event.getLocalState();
                        colocarPieza(event.getX(), event.getY(), tipo);
                        return true;
                }
                return false;
            }
        });

        btnPosiciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                sb.append("Jugador/Autor: ").append(NOMBRE_AUTOR).append("\n\n");

                int puntajeTotal = 0;
                int puntajeMaximo = TOTAL_PIEZAS * PUNTOS_POR_PIEZA;

                for (int i = 1; i <= TOTAL_PIEZAS; i++) {
                    String nombre = obtenerNombrePieza(i);
                    sb.append(nombre).append(": ");

                    if (piezasColocadas[i] != null && posX[i] >= 0 && posY[i] >= 0) {
                        int xRedondeado = Math.round(posX[i]);
                        int yRedondeado = Math.round(posY[i]);
                        double distancia = Math.hypot(
                                posX[i] - objetivoX[i],
                                posY[i] - objetivoY[i]
                        );
                        int puntosPieza = calcularPuntosPieza(distancia, tolerancia[i]);
                        puntajeTotal += puntosPieza;

                        String estado = distancia <= tolerancia[i]
                                ? "Bien colocada"
                                : (puntosPieza > 0 ? "Aproximada" : "Fuera de lugar");

                        sb.append("X=").append(xRedondeado)
                                .append(", Y=").append(yRedondeado)
                                .append(" | ").append(estado)
                                .append(" (").append(puntosPieza).append("/").append(PUNTOS_POR_PIEZA).append(" pts)\n");
                    } else {
                        sb.append("No colocada (0/").append(PUNTOS_POR_PIEZA).append(" pts)\n");
                    }
                }

                sb.append("\nCalificación total: ").append(puntajeTotal).append(" / ").append(puntajeMaximo);
                resultado.setText(sb.toString());
            }
        });
    }

    private int obtenerTamanoPiezaPx(int tipo) {
        if (tipo >= 1 && tipo <= TOTAL_PIEZAS) {
            return dp(TAMANOS_DP[tipo]);
        }
        return tamanoPieza;
    }

    private int obtenerDrawablePieza(int tipo) {
        switch (tipo) {
            case 1:
                return R.drawable.ojos;
            case 2:
                return R.drawable.nariz;
            case 3:
                return R.drawable.cabello;
            case 4:
                return R.drawable.boca;
            case 5:
                return R.drawable.pies;
            default:
                return R.drawable.ojos;
        }
    }

    private String obtenerNombrePieza(int tipo) {
        switch (tipo) {
            case 1:
                return getString(R.string.opojos);
            case 2:
                return getString(R.string.opnariz);
            case 3:
                return getString(R.string.opcabello);
            case 4:
                return getString(R.string.opboca);
            case 5:
                return getString(R.string.oppies);
            default:
                return "";
        }
    }

    private int calcularPuntosPieza(double distancia, float tol) {
        if (distancia <= tol) {
            return PUNTOS_POR_PIEZA;
        }
        float rangoExtra = tol;
        if (distancia < tol + rangoExtra) {
            double proporcion = 1.0 - ((distancia - tol) / rangoExtra);
            int puntos = (int) Math.round(proporcion * PUNTOS_POR_PIEZA);
            return Math.max(1, Math.min(PUNTOS_POR_PIEZA - 1, puntos));
        }
        return 0;
    }

    private void colocarPieza(float x, float y, int tipo) {
        if (tipo < 1 || tipo > TOTAL_PIEZAS) {
            return;
        }

        int ancho = escenario.getWidth();
        int alto = escenario.getHeight();
        if (ancho <= 0 || alto <= 0) {
            return;
        }

        if (piezasColocadas[tipo] != null) {
            escenario.removeView(piezasColocadas[tipo]);
        }

        int tamActual = obtenerTamanoPiezaPx(tipo);

        ImageView nuevaPieza = new ImageView(this);
        nuevaPieza.setImageResource(obtenerDrawablePieza(tipo));
        nuevaPieza.setScaleType(ImageView.ScaleType.FIT_CENTER);

        int izquierda = Math.max(
                0,
                Math.min(
                        Math.round(x) - tamActual / 2,
                        ancho - tamActual
                )
        );

        int arriba = Math.max(
                0,
                Math.min(
                        Math.round(y) - tamActual / 2,
                        alto - tamActual
                )
        );

        FrameLayout.LayoutParams parametros = new FrameLayout.LayoutParams(
                tamActual,
                tamActual
        );
        parametros.leftMargin = izquierda;
        parametros.topMargin = arriba;

        escenario.addView(nuevaPieza, parametros);

        float factorX = ESCALA_MAX / ancho;
        float factorY = ESCALA_MAX / alto;
        float centroX = izquierda + tamActual / 2f;
        float centroY = arriba + tamActual / 2f;

        piezasColocadas[tipo] = nuevaPieza;
        posX[tipo] = centroX * factorX;
        posY[tipo] = centroY * factorY;

        piezaDisponible.setVisibility(View.GONE);
    }

    private int dp(float valor) {
        return Math.round(valor * getResources().getDisplayMetrics().density);
    }
}
