public class Proceso implements Comparable<Proceso> {
    
    private String id;
    private int llegada;
    private int rafaga;
    private double tiempoDeEspera = 0.0;

    public Proceso(String id, int llegada, int rafaga) {
        this.id = id;
        this.llegada = llegada;
        this.rafaga = rafaga;
    }

    @Override
    public int compareTo(Proceso proceso2) {
        if (rafaga == proceso2.rafaga) {
            return 0;
        } else {
            if (rafaga < proceso2.rafaga)
                return -1;
            return 1;
        }
    }

    public void incrementarTiempoDeEspera(double cantidadAIncrementar) {
        this.tiempoDeEspera += cantidadAIncrementar;
    }

    public void decrementarRafaga1ms() {
        this.rafaga--;
    }

    public String getId() {
        return id;
    }

    public int getLlegada() {
        return llegada;
    }

    public int getRafaga() {
        return rafaga;
    }

    public double getTiempoDeEspera() {
        return tiempoDeEspera;
    }

}