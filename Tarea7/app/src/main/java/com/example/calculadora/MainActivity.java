
package com.example.calculadora;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.view.Gravity;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText pantalla;
    int tamLetra = 28;

    Button[] botones = new Button[16];

    Double num1, num2;
    String operacion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout principal = new LinearLayout(this);
        principal.setOrientation(LinearLayout.VERTICAL);
        principal.setPadding(20, 30, 20, 30);
        principal.setBackgroundColor(Color.GRAY);

        // Nombre en la parte superior
        TextView nombre = new TextView(this);
        nombre.setText("Regina Lovera Jerónimo");
        nombre.setTextSize(24);
        nombre.setTextColor(Color.WHITE);
        nombre.setGravity(Gravity.CENTER);
        nombre.setPadding(5, 10, 5, 20);
        principal.addView(nombre);

        // Pantalla de la calculadora
        pantalla = new EditText(this);
        pantalla.setTextSize(20);
        pantalla.setBackgroundColor(Color.WHITE);
        pantalla.setTextSize(32);
        pantalla.setSingleLine(true);
        pantalla.setGravity(Gravity.RIGHT);
        pantalla.setInputType(
                android.text.InputType.TYPE_NULL
        );
        principal.addView(pantalla);

        LinearLayout l1 = new LinearLayout(this);
        l1.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout l2 = new LinearLayout(this);
        l2.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout l3 = new LinearLayout(this);
        l3.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout l4 = new LinearLayout(this);
        l4.setOrientation(LinearLayout.HORIZONTAL);

        // Botones numéricos del 0 al 9
        for (int i = 0; i <= 9; i++) {
            botones[i] = new Button(this);
            botones[i].setText(String.valueOf(i));
            botones[i].setTextSize(tamLetra);

            botones[i].setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String textoBoton =
                                    ((Button) v).getText().toString();

                            pantalla.setText(
                                    pantalla.getText().toString()
                                            + textoBoton
                            );
                        }
                    }
            );
        }

        // Botón decimal
        botones[10] = new Button(this);
        botones[10].setText(".");
        botones[10].setTextSize(tamLetra);

        botones[10].setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String texto = pantalla.getText().toString();

                        if (!texto.contains(".")) {
                            if (texto.isEmpty()) {
                                pantalla.setText("0.");
                            } else {
                                pantalla.setText(texto + ".");
                            }
                        }
                    }
                }
        );

        // Botón de suma
        botones[11] = new Button(this);
        botones[11].setText("+");
        botones[11].setTextSize(tamLetra);

        // Botón de resta
        botones[13] = new Button(this);
        botones[13].setText("-");
        botones[13].setTextSize(tamLetra);

        // Botón de multiplicación
        botones[14] = new Button(this);
        botones[14].setText("×");
        botones[14].setTextSize(tamLetra);

        // Botón de división
        botones[15] = new Button(this);
        botones[15].setText("÷");
        botones[15].setTextSize(tamLetra);

        // Acción común para seleccionar operación
        OnClickListener seleccionarOperacion =
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String texto = pantalla.getText().toString();

                        if (texto.isEmpty()) {
                            Toast.makeText(
                                    MainActivity.this,
                                    "Ingresa un número",
                                    Toast.LENGTH_SHORT
                            ).show();
                            return;
                        }

                        try {
                            num1 = Double.parseDouble(texto);
                            operacion = ((Button) v).getText().toString();
                            pantalla.setText("");
                        } catch (NumberFormatException e) {
                            pantalla.setText("");
                        }
                    }
                };

        botones[11].setOnClickListener(seleccionarOperacion);
        botones[13].setOnClickListener(seleccionarOperacion);
        botones[14].setOnClickListener(seleccionarOperacion);
        botones[15].setOnClickListener(seleccionarOperacion);

        // Botón igual
        botones[12] = new Button(this);
        botones[12].setText("=");
        botones[12].setTextSize(tamLetra);

        botones[12].setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String texto = pantalla.getText().toString();

                        if (num1 == null || operacion.isEmpty()
                                || texto.isEmpty()) {
                            Toast.makeText(
                                    MainActivity.this,
                                    "Completa la operación",
                                    Toast.LENGTH_SHORT
                            ).show();
                            return;
                        }

                        try {
                            num2 = Double.parseDouble(texto);
                            double res = 0;

                            switch (operacion) {
                                case "+":
                                    res = num1 + num2;
                                    break;

                                case "-":
                                    res = num1 - num2;
                                    break;

                                case "×":
                                    res = num1 * num2;
                                    break;

                                case "÷":
                                    if (num2 == 0) {
                                        pantalla.setText("");
                                        Toast.makeText(
                                                MainActivity.this,
                                                "No se puede dividir entre cero",
                                                Toast.LENGTH_LONG
                                        ).show();
                                        num1 = null;
                                        operacion = "";
                                        return;
                                    }
                                    res = num1 / num2;
                                    break;
                            }

                            if (res == (long) res) {
                                pantalla.setText(
                                        String.valueOf((long) res)
                                );
                            } else {
                                pantalla.setText(String.valueOf(res));
                            }

                            num1 = null;
                            num2 = null;
                            operacion = "";

                        } catch (NumberFormatException e) {
                            Toast.makeText(
                                    MainActivity.this,
                                    "Número no válido",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                }
        );

        // Botón para limpiar
        botones[16 - 1] = botones[15];

        Button borrar = new Button(this);
        borrar.setText("C");
        borrar.setTextSize(tamLetra);

        borrar.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pantalla.setText("");
                        num1 = null;
                        num2 = null;
                        operacion = "";
                    }
                }
        );

        // Distribución de los botones
        l1.addView(botones[7], new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        l1.addView(botones[8], new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        l1.addView(botones[9], new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        l1.addView(botones[11], new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        l2.addView(botones[4], new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        l2.addView(botones[5], new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        l2.addView(botones[6], new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        l2.addView(botones[13], new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        l3.addView(botones[1], new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        l3.addView(botones[2], new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        l3.addView(botones[3], new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        l3.addView(botones[14], new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        l4.addView(borrar, new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        l4.addView(botones[0], new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        l4.addView(botones[10], new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        l4.addView(botones[12], new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        LinearLayout l5 = new LinearLayout(this);
        l5.setOrientation(LinearLayout.HORIZONTAL);
        l5.addView(botones[15], new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        principal.addView(l1);
        principal.addView(l2);
        principal.addView(l3);
        principal.addView(l4);
        principal.addView(l5);

        setContentView(principal);
    }
}
