import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.SwingConstants;

public class Galeria extends JFrame {

    JButton btnAtras, btnAdelante, btnPlay;
    JPanel panelBotones, panelImagen;
    ImageIcon[] imagenes;
    String[] titulos;

    int imagenActual;

    JLabel etiquetaImagen;
    JLabel etiquetaTitulo;

    Timer timer;
    boolean reproduciendo;

    public Galeria() {

        setTitle("Galería de personajes de Tokyo Revengers");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelBotones = new JPanel();

        panelImagen = new JPanel(new BorderLayout());

        btnAtras = new JButton("Atrás");
        btnAdelante = new JButton("Adelante");
        btnPlay = new JButton("Reproducir");


        imagenes = new ImageIcon[10];

        imagenes[0] = new ImageIcon("Tomo3.jpg");
        imagenes[1] = new ImageIcon("Tomo7.jpg");
        imagenes[2] = new ImageIcon("Tomo8.jpg");
        imagenes[3] = new ImageIcon("Tomo14.jpg");
        imagenes[4] = new ImageIcon("Tomo16.jpg");
        imagenes[5] = new ImageIcon("Tomo17.jpg");
        imagenes[6] = new ImageIcon("Tomo19.jpg");
        imagenes[7] = new ImageIcon("Tomo24.jpg");
        imagenes[8] = new ImageIcon("Tomo29.jpg");
        imagenes[9] = new ImageIcon("Tomo30.jpg");

        titulos = new String[10];

        titulos[0] = "Mickey, tomo 1";
        titulos[1] = "Baji, tomo 7";
        titulos[2] = "Kazutora, tomo 8";
        titulos[3] = "Inui y Koko, tomo 14";
        titulos[4] = "Naoto, tomo 16";
        titulos[5] = "Izana, tomo 17";
        titulos[6] = "Ran y Rindou, tomo 19";
        titulos[7] = "Mickey, tomo 24";
        titulos[8] = "Sanzu, tomo 29";
        titulos[9] = "Shinichiro, tomo 30";

        etiquetaImagen = new JLabel();
        etiquetaImagen.setHorizontalAlignment(SwingConstants.CENTER);

        etiquetaTitulo = new JLabel();
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        etiquetaTitulo.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        panelImagen.add(etiquetaTitulo, BorderLayout.NORTH);
        panelImagen.add(etiquetaImagen, BorderLayout.CENTER);

        // Mostrar la primera imagen
        imagenActual = 0;
        mostrarImagen();

        btnAtras.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (imagenActual == 0) {
                    imagenActual = imagenes.length - 1;
                } else {
                    imagenActual--;
                }

                mostrarImagen();
            }
        });

        btnAdelante.addActionListener(e -> {

            if (imagenActual == imagenes.length - 1) {
                imagenActual = 0;
            } else {
                imagenActual++;
            }

            mostrarImagen();
        });

        btnPlay.addActionListener(e -> {

            if (!reproduciendo) {

                btnPlay.setText("Detener");
                reproduciendo = true;

                timer.start();

            } else {

                btnPlay.setText("Reproducir");
                reproduciendo = false;

                timer.stop();
            }
        });

        timer = new Timer(2000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (imagenActual == imagenes.length - 1) {
                    imagenActual = 0;
                } else {
                    imagenActual++;
                }

                mostrarImagen();
            }
        });

        panelBotones.add(btnAtras);
        panelBotones.add(btnAdelante);
        panelBotones.add(btnPlay);

        add(panelImagen, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void mostrarImagen() {

        // Obtener la imagen actual
        ImageIcon icono = imagenes[imagenActual];

        Image imagen = icono.getImage();

        // Escalar imagen
        Image imagenEscalada = imagen.getScaledInstance(
                400,
                400,
                Image.SCALE_SMOOTH
        );

        // Mostrar imagen
        etiquetaImagen.setIcon(
                new ImageIcon(imagenEscalada)
        );

        // Mostrar título correspondiente
        etiquetaTitulo.setText(
                titulos[imagenActual]
        );
    }

    public static void main(String[] args) {

        Galeria g = new Galeria();

        g.setVisible(true);
    }
}