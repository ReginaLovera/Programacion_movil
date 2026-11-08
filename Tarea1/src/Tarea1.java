import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Tarea1 extends JFrame {
    public Tarea1() {
        setSize(230, 200);
        setLocation(570, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tarea 1");

        JButton botonInicio = new JButton("Bienvenido");

        botonInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "¡Regina Lovera Jerónimo!");
            }
        });

        add(botonInicio);
        setVisible(true);
    }
    public static void main(String[] args) { new Tarea1();
    }
}