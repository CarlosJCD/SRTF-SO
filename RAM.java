import java.util.PriorityQueue;

public class RAM {
    public PriorityQueue<Proceso> colaDeEspera;

    public RAM() {
        this.colaDeEspera = new PriorityQueue<Proceso>();
    }
}
