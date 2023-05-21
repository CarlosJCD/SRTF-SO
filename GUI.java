import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {

    DrawingPanel panel;
    JFrame frame;
    ImageIcon imagenTablaProcesos;
    int frameWidth = 500;
    int frameHeight = 600;
    JLabel labelTitulo;
    JLabel imagen;
    JLabel labelDiagramaGannt; 
    JLabel labelPaso;
    JButton botonPaso;

    public GUI (){

        panel = new DrawingPanel();
        frame = new JFrame();

        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        //Añadimos el apartado de bienvenida

        labelTitulo = new JLabel("Administración Del Procesador Utilizando El Algoritmo SRTF");
        labelTitulo.setBounds(frameWidth/8, frameHeight/20, 500, 50);
        panel.add(labelTitulo);

        //Añadimos la imagen de los procesos

        imagenTablaProcesos = new ImageIcon(getClass().getResource("TablaProcesosSOProyectoFinal.png"));
        imagen = new JLabel(imagenTablaProcesos);
        imagen.setBounds(35, 50, 410, 300);
        panel.add(imagen);

        //Añadimos el apartado que indica que sigue el diagrama de Gannt

        labelDiagramaGannt = new JLabel("A continuación el Diagrama de Gannt: ");
        labelDiagramaGannt.setBounds(50, 300, 500, 50);
        panel.add(labelDiagramaGannt);

        //Añadimos el label que nos indicará el paso que estamos haciendo

        labelPaso = new JLabel("Paso ");
        labelPaso.setBounds(50, 400, 500, 50);
        panel.add(labelPaso);

        //Añadimos el botón para ir mostrando cómo se va rellenando el diagrama de Gannt

        botonPaso = new JButton("Siguiente");
        botonPaso.setBounds(200, 420, 90, 25);
        botonPaso.addActionListener(this);
        panel.add(botonPaso);

        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Hola");
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
