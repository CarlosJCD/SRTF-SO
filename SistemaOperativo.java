import java.util.ArrayList;
import java.util.List;

public class SistemaOperativo {
    private RAM ram;
    private CPU cpu;
    private List<Proceso> procesosAEjecutar;
    private List<Proceso> procesosFinalizados;
    private int tiempo;
    private double cambioDeContexto;
    private int contadorCambiosDeContexto;
    private double TTP;
    private double TEP;
    private double procentajePromedio;

    public SistemaOperativo(double cambioDeContexto) {
        this.cambioDeContexto = cambioDeContexto;
        this.ram = new RAM();
        this.cpu = new CPU();
        this.tiempo = 0;
        this.contadorCambiosDeContexto = 0;
        this.procesosAEjecutar = new ArrayList<Proceso>();
        this.procesosFinalizados = new ArrayList<Proceso>();
        cargarProcesos();
    }

    private void cargarProcesos() {
        procesosAEjecutar.add(new Proceso("P1", 0, 8));
        procesosAEjecutar.add(new Proceso("P2", 3, 4));
        procesosAEjecutar.add(new Proceso("P3", 6, 2));
        procesosAEjecutar.add(new Proceso("P4", 10, 3));
        procesosAEjecutar.add(new Proceso("P5", 15, 6));
    }

    public boolean srtfPasoN() {
        if (iniciaAlgoritmo()) {
            cpu.proceso = procesosAEjecutar.remove(0);
            avanzaTiempoEnUno();
            return true;
        } else {
            if (llegaUnProcesoNuevo()) {
                Proceso procesoNuevo = procesosAEjecutar.remove(0);
                return procedimientoSRTF(procesoNuevo);
            } else {
                return procedimientoSRTF();
            }
        }
    }

    private boolean procedimientoSRTF() {
        if (procesoEnEjeucionFinaliza()) {
            procesosFinalizados.add(cpu.proceso);
            if (finalizaAlgoritmo())
                return false;
            if (colaDeEsperaNoVacia()) {
                cpu.proceso = ram.colaDeEspera.poll();

                cpu.proceso.incrementarTiempoDeEspera(cambioDeContexto);
                incrementarTiemposDeEsperaEnLaCola(cambioDeContexto);
                contadorCambiosDeContexto++;
            }
        } else {
            if (procesoEnEjecucionMayorAProcesoColaDeEspera()) {
                ram.colaDeEspera.add(cpu.proceso);
                cpu.proceso = ram.colaDeEspera.poll();

                cpu.proceso.incrementarTiempoDeEspera(cambioDeContexto);
                incrementarTiemposDeEsperaEnLaCola(cambioDeContexto);
                contadorCambiosDeContexto++;
            }
        }
        avanzaTiempoEnUno();
        return true;
    }

    private boolean procedimientoSRTF(Proceso procesoNuevo) {
        ram.colaDeEspera.add(procesoNuevo);
        if (procesoEnEjeucionFinaliza()) {
            procesosFinalizados.add(cpu.proceso);
            if (finalizaAlgoritmo())
                return false;

            cpu.proceso = ram.colaDeEspera.poll();
            cpu.proceso.incrementarTiempoDeEspera(cambioDeContexto);
            incrementarTiemposDeEsperaEnLaCola(cambioDeContexto);
            contadorCambiosDeContexto++;
        } else {
            if (procesoEnEjecucionMayorAProcesoColaDeEspera()) {
                ram.colaDeEspera.add(cpu.proceso);
                cpu.proceso = ram.colaDeEspera.poll();

                cpu.proceso.incrementarTiempoDeEspera(cambioDeContexto);
                incrementarTiemposDeEsperaEnLaCola(cambioDeContexto, procesoNuevo);
                contadorCambiosDeContexto++;
            }
        }
        avanzaTiempoEnUno();
        return true;
    }

    private boolean colaDeEsperaNoVacia() {
        return !ram.colaDeEspera.isEmpty();
    }

    private boolean iniciaAlgoritmo() {
        return tiempo == 0;
    }

    private boolean finalizaAlgoritmo() {
        return (ram.colaDeEspera.isEmpty()) && (procesosAEjecutar.isEmpty());
    }

    private boolean procesoEnEjeucionFinaliza() {
        return cpu.proceso.getRafaga() == 0;
    }

    private boolean procesoEnEjecucionMayorAProcesoColaDeEspera() {
        if (colaDeEsperaNoVacia())
            return cpu.proceso.compareTo(ram.colaDeEspera.peek()) > 0;
        return false;
    }

    private boolean llegaUnProcesoNuevo() {
        if (!procesosAEjecutar.isEmpty())
            return (tiempo == procesosAEjecutar.get(0).getLlegada());
        return false;
    }

    private void avanzaTiempoEnUno() {
        tiempo++;
        cpu.proceso.decrementarRafaga1ms();
        incrementarTiemposDeEsperaEnLaCola(1);

    }

    private void incrementarTiemposDeEsperaEnLaCola(double tiempo) {
        if (colaDeEsperaNoVacia()) {
            for (Proceso proceso : ram.colaDeEspera) {
                proceso.incrementarTiempoDeEspera(tiempo);
            }
        }
    }

    private void incrementarTiemposDeEsperaEnLaCola(double tiempo, Proceso procesoNuevo) {
        if (colaDeEsperaNoVacia()) {
            for (Proceso proceso : ram.colaDeEspera) {
                if (proceso != procesoNuevo)
                    proceso.incrementarTiempoDeEspera(tiempo);
            }
        }
    }

    public void calcularAnaliticas() {
        TTP = tiempo + (contadorCambiosDeContexto * cambioDeContexto);
        TEP = calcularTEP();
        procentajePromedio = (double) Math.round((TEP / TTP) * 10000) / 100;
    }

    private double calcularTEP() {
        double TEP = 0;
        for (Proceso proceso : procesosFinalizados) {
            TEP += proceso.getTiempoDeEspera();
        }
        return TEP / procesosFinalizados.size();
    }

    public double getTTP() {
        return TTP;
    }

    public double getTEP() {
        return TEP;
    }

    public double getProcentajePromedio() {
        return procentajePromedio;
    }

    public List<Proceso> getProcesosFinalizados() {
        return procesosFinalizados;
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        SistemaOperativo SO = new SistemaOperativo(0.2);
        while (SO.srtfPasoN()) {
        }
        SO.calcularAnaliticas();
        for (Proceso proceso : SO.procesosFinalizados) {
            System.out.println("Nombre: " + proceso.getId() + " Tiempo de espera:" + proceso.getTiempoDeEspera());
        }
        System.out.println("TTP: " + SO.TTP);
        System.out.println("TEP: " + SO.TEP);
        System.out.println("TEP/TTP %: " + SO.procentajePromedio);
    }
}
/*
 * 1.- tiempo de espera para todos los procesos listos en la cola de espera
 * 
 * 2.- No se cuenta el primer cambio de contexto del grafico
 * 
 * 3.- No se cuenta el ultimo cambio de contexto del grafico
 * 
 * 4.- Si un proceso es levantado, se cuenta el cambio de contexto que ocurre al
 * ser levantado (Siempre y cuando no sea el ultimo proceso de la grafica)
 * 
 * 5.- Si un proceso es colocado, se cuenta el cambio de contexto que ocurre al
 * ser colocado (Siempre y cuando no sea el primer proceso de la grafica)
 * 
 * 6.- Si un proceso llega justo durante un cambio de contexto, primero se añade
 * a la cola
 * 
 * 7.- A modo de continuation del 6, si un proceso llego justo durante un cambio
 * de contexto, no se le añade el tiempo de cambio de contexto a no ser que sea
 * colocado, en ese caso se le aplicaría el cambio de contexto.
 */