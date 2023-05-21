import javax.swing.*;
import java.awt.*;

public class GUI {

    DrawingPanel panel;
    JFrame frame;
    ImageIcon imagenTablaProcesos;
    int frameWidth = 500;
    int frameHeight = 600;

    public GUI(){

        panel = new DrawingPanel();
        frame = new JFrame();

        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        //Añadimos el apartado de bienvenida

        JLabel labelTitulo = new JLabel("Administración Del Procesador Utilizando El Algoritmo SRTF");
        labelTitulo.setBounds(frameWidth/8, frameHeight/20, 500, 50);
        panel.add(labelTitulo);

        //Añadimos la imagen de los procesos

        imagenTablaProcesos = new ImageIcon(getClass().getResource("TablaProcesosSOProyectoFinal.png"));
        JLabel imagen = new JLabel(imagenTablaProcesos);
        imagen.setBounds(35, 50, 410, 300);
        panel.add(imagen);

        //Añadimos el apartado que indica que sigue el diagrama de Gannt

        JLabel labelDiagramaGannt = new JLabel("A continuación el Diagrama de Gannt: ");
        labelDiagramaGannt.setBounds(50, 300, 500, 50);
        panel.add(labelDiagramaGannt);

        frame.setVisible(true);

    }

    class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw outer rectangle
            g.setColor(Color.BLUE);
            g.drawRect(50, 350, 400, 50);

            // Draw inner rectangles
            g.setColor(Color.RED);
            for (int i = 0; i < 8; i++) {
                g.drawRect(50 + i * 50, 350, 50, 50);
            }
        }
    }
}
