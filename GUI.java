import javax.swing.*;
//import javax.swing.border.Border;
//import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUI implements ActionListener {

    DrawingPanel panel;
    JFrame frame;
    ImageIcon imagenTablaProcesos;
    int frameWidth = 820;
    int frameHeight = 700;
    int rectangleWidth = 30;
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

    JTable tabla;
    JScrollPane scrollPane;
    List<Proceso> datosProceso;
    Object[][] datos;

    public GUI(SistemaOperativo _so) {

        so = _so;

        panel = new DrawingPanel();
        frame = new JFrame();

        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);
        panel.setLayout(null);

        // Añadimos el apartado de bienvenida

        labelTitulo = new JLabel("Administración del Procesador utilizando el Algoritmo SRTF");
        Font currentFont = labelTitulo.getFont();
        labelTitulo.setFont(new Font(currentFont.getName(), Font.BOLD, 20));
        labelTitulo.setBounds(96, frameHeight / 20, 700, 50);
        panel.add(labelTitulo);

        // Añadimos la tabla de los procesos

        datosProceso = so.getProcesosAEjecutar();
        
        datos = new Object[datosProceso.size()][3];
        for (int i = 0; i < datosProceso.size(); i++) {
            Proceso proceso = datosProceso.get(i);
            datos[i][0] = proceso.getId();
            datos[i][1] = proceso.getLlegada();
            datos[i][2] = proceso.getRafaga();
        }

        Object[] columnas = {"Proceso", "Tiempo de Llegada", "Tiempo de Ráfaga"};

        tabla = new JTable(datos, columnas);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i=0; i<tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        ((DefaultTableCellRenderer)tabla.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(220, 90, 378, 200);
        panel.add(scrollPane);

        // Añadimos el apartado que indica que sigue el diagrama de Gannt

        labelDiagramaGannt = new JLabel("Diagrama de Gannt: ");
        labelDiagramaGannt.setBounds(50, 300, 500, 50);
        panel.add(labelDiagramaGannt);

        // Añadimos el botón para ir mostrando cómo se va rellenando el diagrama de Gannt

        botonPaso = new JButton("Paso n");
        botonPaso.setBounds(365, 450, 90, 25);
        botonPaso.addActionListener(this);
        panel.add(botonPaso);

        // Hacemos labels para cada proceso

        proceso1 = new JLabel();
        proceso1.setBounds(50, 350, 50, 50);
        proceso1.setForeground(Color.WHITE);
        panel.add(proceso1);

        proceso1_2 = new JLabel();
        proceso1_2.setBounds(50 + rectangleWidth * 9, 350, 50, 50);
        proceso1_2.setForeground(Color.WHITE);
        panel.add(proceso1_2);

        proceso1_3 = new JLabel();
        proceso1_3.setBounds(50 + rectangleWidth * 13, 350, 50, 50);
        proceso1_3.setForeground(Color.WHITE);
        panel.add(proceso1_3);

        proceso2 = new JLabel();
        proceso2.setBounds(50 + rectangleWidth * 3, 350, 50, 50);
        proceso2.setForeground(Color.WHITE);
        panel.add(proceso2);

        proceso3 = new JLabel();
        proceso3.setBounds(50 + rectangleWidth * 7, 350, 50, 50);
        proceso3.setForeground(Color.WHITE);
        panel.add(proceso3);

        proceso4 = new JLabel();
        proceso4.setBounds(50 + rectangleWidth * 10, 350, 50, 50);
        proceso4.setForeground(Color.WHITE);
        panel.add(proceso4);

        proceso5 = new JLabel();
        proceso5.setBounds(50 + rectangleWidth * 17, 350, 50, 50);
        proceso5.setForeground(Color.WHITE);
        panel.add(proceso5);

        frame.setVisible(true);
    }

    public void revisarTiemposDeLLegada() {
        switch (so.getTiempo()) {
            case 1:
                proceso1.setText("P1");
                break;
            case 3:
                proceso2.setText("P2");
                break;
            case 7:
                proceso3.setText("P3");
                break;
            case 9:
                proceso1_2.setText("P1");
                break;
            case 10:
                proceso4.setText("P4");
                break;
            case 13:
                proceso1_3.setText("P1");
                break;
            case 17:
                proceso5.setText("P5");
                break;
            default:
                break;
        }

    }

    public void añadirSegundosGrafica() {

        panel.counter = so.getTiempo();

        for (int i = 0; i <= panel.counter; i++) {
            JLabel tiempo = new JLabel();
            tiempo.setText(Integer.toString(i));
            tiempo.setBounds(50 + rectangleWidth * i, 390, 100, 50);
            panel.add(tiempo);
            botonPaso.setText("Paso " + Integer.toString(i));
        }

        if (so.getTiempo() == 23) {
            JLabel tiempo = new JLabel();
            tiempo.setText("24");
            tiempo.setBounds(50 + rectangleWidth * 24, 390, 100, 50);
            panel.add(tiempo);
        }
    }

    public void mostrarAnaliticas() {
        so.srtfPasoN();

        so.calcularAnaliticas();

        JLabel tiempoDeEsperaTitulo = new JLabel("Tiempos de espera por proceso:");
        tiempoDeEsperaTitulo.setBounds(310, 475, 250, 50);
        panel.add(tiempoDeEsperaTitulo);

        for (Proceso proceso : so.getProcesosFinalizados()) {
            switch (proceso.getId()) {
                case "P1":
                    JLabel p1Label = new JLabel();
                    p1Label.setBounds(185, 505, 100, 50);
                    panel.add(p1Label);
                    p1Label.setText("P1 = " + proceso.getTiempoDeEspera());
                    break;
                case "P2":
                    JLabel p2Label = new JLabel();
                    p2Label.setBounds(185 + 100, 505, 100, 50);
                    panel.add(p2Label);
                    p2Label.setText("P2 = " + proceso.getTiempoDeEspera());
                    break;
                case "P3":
                    JLabel p3Label = new JLabel();
                    p3Label.setBounds(185 + 200, 505, 100, 50);
                    panel.add(p3Label);
                    p3Label.setText("P3 = " + proceso.getTiempoDeEspera());
                    break;
                case "P4":
                    JLabel p4Label = new JLabel();
                    p4Label.setBounds(185 + 300, 505, 100, 50);
                    panel.add(p4Label);
                    p4Label.setText("P4 = " + proceso.getTiempoDeEspera());
                    break;
                case "P5":
                    JLabel p5Label = new JLabel();
                    p5Label.setBounds(185 + 400, 505, 100, 50);
                    panel.add(p5Label);
                    p5Label.setText("P5 = " + proceso.getTiempoDeEspera());
                    break;
                default:
                    break;
            }
        }

        JLabel analiticas = new JLabel("Analíticas:");
        analiticas.setBounds(378, 540, 100, 50);
        panel.add(analiticas);

        JLabel tep = new JLabel();
        tep.setText("TEP :" + so.getTEP());
        tep.setBounds(255, 570, 100, 50);
        panel.add(tep);

        JLabel ttp = new JLabel();
        ttp.setText("TTP :" + Double.toString(so.getTTP()));
        ttp.setBounds(255 + 100, 570, 100, 50);
        panel.add(ttp);

        JLabel pp = new JLabel();
        pp.setText("TEP/TTP % :" + Double.toString(so.getPorcentajePromedio()) + "%");
        pp.setBounds(255 + 100 * 2, 570, 200, 50);
        panel.add(pp);

    }

    public void cambiarBotonAlFinalizar() {
        botonPaso.setText("Finalizar");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!botonPaso.getText().equals("Finalizar")) {
            so.srtfPasoN();
            añadirSegundosGrafica();
            revisarTiemposDeLLegada();

            int control = 0;
            control += so.getTiempo();

            if (control == 23) {
                mostrarAnaliticas();
                control += 1;
                cambiarBotonAlFinalizar();
            }
            panel.repaint();

        } else {
            frame.dispose();
        }
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
            g.fillRect(50, 350, rectangleWidth * 24, 50);
            g.setColor(Color.WHITE);
            g.drawRect(50, 350, rectangleWidth * 24, 50);

            // Fill and draw inner rectangles
            if (this.counter < 25) {
                for (int i = 0; i < counter + 1; i++) {
                    g.setColor(Color.BLUE);
                    g.fillRect(50 + i * rectangleWidth, 350, 34, 50);
                    g.setColor(Color.WHITE);
                    g.drawRect(50 + i * rectangleWidth, 350, 34, 50);
                }
            }

        }
    }

}
