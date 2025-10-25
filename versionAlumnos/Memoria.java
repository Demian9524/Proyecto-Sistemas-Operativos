// Memoria.java
// Modelo simple de memoria: sin particiones, solo cupo total/libre.

public class Memoria {
    private final int totalKB;
    private int libreKB;

    public Memoria(int totalKB) {
        if (totalKB <= 0) throw new IllegalArgumentException("Memoria total debe ser > 0");
        this.totalKB = totalKB;
        this.libreKB = totalKB;
    }

    public int getTotalKB() { return totalKB; }
    public int getLibreKB() { return libreKB; }

    public boolean cabe(Proceso p) {
        return p.getSizeKB() <= libreKB;
    }

    // Asignar memoria a un proceso (validar antes con cabe())
    public void cargar(Proceso p, int relojMs) {
        if (!cabe(p)) throw new IllegalStateException("No cabe " + p.getId() + " en memoria");
        libreKB -= p.getSizeKB();
        Logs.memCarga(relojMs, p, libreKB); // color + salto extra
    }

    // Liberar memoria ocupada por un proceso
    public void liberar(Proceso p, int relojMs) {
        libreKB += p.getSizeKB();
        Logs.memLibera(relojMs, p, libreKB); // color + salto extra
    }
}