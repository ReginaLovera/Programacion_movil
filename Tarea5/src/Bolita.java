import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Bolita extends JPanel {

    int x, y;
    Image img;

    public Bolita() {
        setSize(300, 300);
        x = 50;
        y = 50;

        // Cambiamos la imagen por la externa generada con el nombre (.png)
        img = new ImageIcon("Regina.png").getImage();
        setBackground(Color.green);

        MouseAdapter mouse = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // Actualiza la posición de la imagen según la posición actual del cursor del mouse
                // Restamos la mitad del ancho/alto aproximado para centrar la imagen en el cursor (ej. asumiendo 50x50 píxeles)
                x = e.getX() - (img.getWidth(null) > 0 ? img.getWidth(null) / 2 : 25);
                y = e.getY() - (img.getHeight(null) > 0 ? img.getHeight(null) / 2 : 25);
                repaint();
            }
        };

        // Ya no necesitamos addMouseListener para presionar, solo el listener de movimiento del mouse
        addMouseMotionListener(mouse);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, x, y, this);
    }
}

class Ventana extends JFrame {

    public Ventana() {
        setSize(450, 430);
        setTitle("Bolita con Mouse Movido");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Bolita bolita = new Bolita();
        add(bolita);
    }

    public static void main(String[] args) {
        Ventana v = new Ventana();
        v.setVisible(true);
    }
}