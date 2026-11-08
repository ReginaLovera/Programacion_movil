import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Dibujo extends JFrame {

    JComboBox<String> comboColores;
    JPanel panelDibujo, panelSuperior;
    JButton btnLimpiar;

    BufferedImage imagen;
    Graphics2D g2;

    int xAnterior, yAnterior;

    // Imagen que se utilizará como fondo
    Image fondo;

    public Dibujo() {

        setTitle("Paint");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cargar imagen de fondo
        fondo = new ImageIcon("imagenes/fondo.jpg").getImage();

        comboColores = new JComboBox<>();

        comboColores.addItem("Rojo");
        comboColores.addItem("Verde");
        comboColores.addItem("Azul");
        comboColores.addItem("Rosa");
        comboColores.addItem("Amarillo");
        comboColores.addItem("Cyan");
        comboColores.addItem("Gris");
        comboColores.addItem("Magenta");
        comboColores.addItem("Naranja");
        comboColores.addItem("Negro");
        comboColores.addItem("Blanco");





        btnLimpiar = new JButton("Limpiar");

        panelSuperior = new JPanel();
        panelSuperior.add(comboColores);
        panelSuperior.add(btnLimpiar);

        add(panelSuperior, BorderLayout.NORTH);

        // Crear el área de dibujo
        panelDibujo = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Dibujar la imagen de fondo
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);

                // Dibujar lo que haya realizado el usuario
                g.drawImage(imagen, 40, 10, null);
            }
        };

        // Crear imagen donde se realizarán los dibujos
        imagen = new BufferedImage(
                300,
                300,
                BufferedImage.TYPE_INT_ARGB
        );

        g2 = imagen.createGraphics();

        // Hacer el fondo de la imagen transparente
        g2.setComposite(
                java.awt.AlphaComposite.Clear
        );
        g2.fillRect(
                0,
                0,
                imagen.getWidth(),
                imagen.getHeight()
        );

        g2.setComposite(
                java.awt.AlphaComposite.SrcOver
        );

        // Color inicial
        g2.setColor(Color.red);

        // Cambiar color
        comboColores.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int colorSeleccionado =
                        comboColores.getSelectedIndex();

                switch (colorSeleccionado) {

                    case 0:
                        g2.setColor(Color.red);
                        break;

                    case 1:
                        g2.setColor(Color.green);
                        break;

                    case 2:
                        g2.setColor(Color.blue);
                        break;

                    case 3:
                        g2.setColor(Color.pink);
                        break;

                    case 4:
                        g2.setColor(Color.yellow);
                        break;

                    case 5:
                        g2.setColor(Color.cyan);
                        break;

                    case 6:
                        g2.setColor(Color.gray);
                        break;

                    case 7:
                        g2.setColor(Color.magenta);
                        break;

                    case 8:
                        g2.setColor(Color.orange);
                        break;

                    case 9:
                        g2.setColor(Color.black);
                        break;

                    case 10:
                        g2.setColor(Color.white);
                        break;
                }
            }
        });

        // Botón Limpiar
        btnLimpiar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // Limpiar todo lo dibujado
                g2.setComposite(
                        java.awt.AlphaComposite.Clear
                );

                g2.fillRect(
                        0,
                        0,
                        imagen.getWidth(),
                        imagen.getHeight()
                );

                // Regresar al modo normal de dibujo
                g2.setComposite(
                        java.awt.AlphaComposite.SrcOver
                );

                // Actualizar el panel
                panelDibujo.repaint();
            }
        });

        // Mouse presionado
        panelDibujo.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

                xAnterior = e.getX() - 40;
                yAnterior = e.getY() - 10;
            }
        });

        // Movimiento del mouse
        panelDibujo.addMouseMotionListener(
                new MouseMotionAdapter() {

                    @Override
                    public void mouseDragged(MouseEvent e) {

                        int x = e.getX() - 40;
                        int y = e.getY() - 10;

                        g2.drawLine(
                                xAnterior,
                                yAnterior,
                                x,
                                y
                        );

                        xAnterior = x;
                        yAnterior = y;

                        panelDibujo.repaint();
                    }
                }
        );

        add(panelDibujo, BorderLayout.CENTER);
    }

    public static void main(String[] args) {

        Dibujo d = new Dibujo();
        d.setVisible(true);
    }
}