import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI implements ActionListener {

    DrawingPanel panel;
    JFrame frame;
    ImageIcon imagenTablaProcesos;
    int frameWidth = 500;
    int frameHeight = 700;
    JLabel labelTitulo;
    JLabel imagen;
    JLabel labelDiagramaGannt; 
    JLabel labelPaso;
    JButton botonPaso;
    SistemaOperativo so;

    JLabel proceso1;
    JLabel proceso1_2;
    JLabel proceso1_3;
    JLabel proceso2;
    JLabel proceso3;
    JLabel proceso4;
    JLabel proceso5;

    public GUI (SistemaOperativo _so){

        so = _so;

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

        labelPaso = new JLabel("Paso 0");
        labelPaso.setBounds(50, 415, 500, 50);
        panel.add(labelPaso);

        //Añadimos el botón para ir mostrando cómo se va rellenando el diagrama de Gannt

        botonPaso = new JButton("Siguiente");
        botonPaso.setBounds(350, 430, 90, 25);
        botonPaso.addActionListener(this);
        panel.add(botonPaso);

        //Hacemos labels para cada proceso

        proceso1 = new JLabel();
        proceso1.setBounds(50,350 , 50, 50);
        panel.add(proceso1);

        proceso1_2= new JLabel();
        proceso1_2.setBounds(50+16*9, 350, 50, 50);
        panel.add(proceso1_2);

        proceso1_3 = new JLabel();
        proceso1_3.setBounds(50 + 16*17, 350, 50, 50);
        panel.add(proceso1_3);

        proceso2 = new JLabel();
        proceso2.setBounds(50 + 16*3 ,350 , 50, 50);
        panel.add(proceso2);

        proceso3 = new JLabel();
        proceso3.setBounds(50 + 16*7,350 , 50, 50);
        panel.add(proceso3);

        proceso4 = new JLabel();
        proceso4.setBounds(50 + 16* 10, 350, 50, 50);
        panel.add(proceso4);

        frame.setVisible(true);

    }

    public void revisarTiemposDeLLegada(){

        if(so.tiempo == 1){
            proceso1.setText("P1");
        }
        else if(so.tiempo == 3){
            proceso2.setText("P2");

        }
        else if(so.tiempo == 7){
            proceso3.setText("P3");
        }
        else if(so.tiempo == 9){
            proceso1_2.setText("P1");

        }
        else if(so.tiempo == 10){
            proceso4.setText("P4");
            
        }
        else if (so.tiempo == 17){
            proceso1_3.setText("P1");
        }

    }

    public void añadirSegundosGrafica(){

            panel.counter = so.tiempo; 

            for(int i = 0; i <= panel.counter;i++){
                JLabel tiempo = new JLabel();
                tiempo.setText(Integer.toString(i));
                tiempo.setBounds(50 + 16 * i, 390, 50, 50);
                panel.add(tiempo);
                labelPaso.setText("Paso: " + Integer.toString(i));
            }

    }

    public void mostrarAnaliticas(){

        so.calcularAnaliticas();

        JLabel analiticas = new JLabel("Analíticas :");
        analiticas.setBounds(50, 450, 100, 50);

        JLabel tep = new JLabel();
        tep.setText("TEP :" + Double.toString(so.getTEP()));
        tep.setBounds(50, 470, 100, 50);
        panel.add(tep);

        JLabel ttp = new JLabel();
        ttp.setText("TTP :" + Double.toString(so.getTTP()));
        ttp.setBounds(50 + 100, 470, 100, 50);
        panel.add(ttp);

        JLabel pp = new JLabel();
        pp.setText("PP :" + Double.toString(so.getPorcentajePromedio()));
        pp.setBounds(50 + 100 * 2, 470, 100, 50);
        panel.add(pp);
        

    }

    @Override
    public void actionPerformed(ActionEvent e) {

            so.srtfPasoN();
            añadirSegundosGrafica();
            revisarTiemposDeLLegada();

            int control = 0;
            control += so.tiempo;

            if(control == 23){

                mostrarAnaliticas();
                control += 1;

            }
        

            panel.repaint();
    }

    class DrawingPanel extends JPanel {

        Color rectColor = Color.BLACK;
        ArrayList<Integer> llegadas = new ArrayList<>();

        

        int counter = 0;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
        
            // Fill and draw outer rectangle
            g.setColor(Color.WHITE);
            g.fillRect(50, 350, 384, 50);
            g.setColor(Color.WHITE);
            g.drawRect(50, 350, 384, 50);
        
            // Fill and draw inner rectangles
            if (this.counter < 25){
                for (int i = 0; i < counter+1; i++) {
                    g.setColor(Color.BLUE);
                    g.fillRect(50 + i * 16, 350, 16, 50);
                    g.setColor(Color.WHITE);
                    g.drawRect(50 + i * 16, 350, 16, 50);
                }               
            }




        }
    }


    

}
