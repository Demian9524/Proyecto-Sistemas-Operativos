// Proceso.java
// Representa un proceso para el simulador RR con gestión de memoria simple
// Incluye: datos de captura, estado de ejecución, y métricas (respuesta, turnaround, espera)

public class Proceso {

    // ====== Datos de captura (del usuario) ======
    private final String id;
    private String nombre;
    private final int sizeKB;        // memoria requerida (KB)
    private final int cpuTotalMs;    // ráfaga total de CPU (ms)
    private final int llegadaMs;     // tiempo de llegada (ms)
    private int prioridad;           // aunque RR no la use, se captura

    // ====== Estado de simulación ======
    private int cpuRestanteMs;               // disminuye con la ejecución
    private Integer tPrimeraRespuestaMs;     // se fija cuando entra por 1ª vez a CPU
    private Integer tFinalizacionMs;         // se fija cuando cpuRestanteMs llega a 0

    // ====== Constructores ======
    public Proceso(String id, String nombre, int sizeKB, int cpuTotalMs, int llegadaMs, int prioridad) {
        validar(id, nombre, sizeKB, cpuTotalMs, llegadaMs);
        this.id = id.trim();
        this.nombre = nombre.trim();
        this.sizeKB = sizeKB;
        this.cpuTotalMs = cpuTotalMs;
        this.llegadaMs = llegadaMs;
        this.prioridad = prioridad;
        this.cpuRestanteMs = cpuTotalMs;
        this.tPrimeraRespuestaMs = null;
        this.tFinalizacionMs = null;
    }

    public Proceso(String id, String nombre, int sizeKB, int cpuTotalMs, int llegadaMs) {
        this(id, nombre, sizeKB, cpuTotalMs, llegadaMs, 0);
    }

    private static void validar(String id, String nombre, int sizeKB, int cpuTotalMs, int llegadaMs) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("ID vacío");
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("Nombre vacío");
        if (sizeKB <= 0) throw new IllegalArgumentException("sizeKB debe ser > 0");
        if (cpuTotalMs <= 0) throw new IllegalArgumentException("cpuTotalMs debe ser > 0");
        if (llegadaMs < 0) throw new IllegalArgumentException("llegadaMs debe ser >= 0");
    }

    // ====== Operación principal de CPU ======
    /**
     * Ejecuta el proceso por min(quantumMs, cpuRestanteMs).
     * - Si es la primera vez que entra a CPU, fija tPrimeraRespuestaMs = relojMsActual.
     * - Si se agota la ráfaga, fija tFinalizacionMs = relojMsActual + ejecutado.
     * @param quantumMs      cantidad máxima a ejecutar (ms) (debe ser > 0)
     * @param relojMsActual  tiempo actual (ms) cuando inicia esta ejecución
     * @return               cuánto se ejecutó efectivamente (ms)
     */
    public int ejecutarPor(int quantumMs, int relojMsActual) {
        if (quantumMs <= 0) throw new IllegalArgumentException("quantumMs debe ser > 0");
        if (relojMsActual < 0) throw new IllegalArgumentException("relojMsActual debe ser >= 0");

        if (tPrimeraRespuestaMs == null) {
            tPrimeraRespuestaMs = relojMsActual;
        }
        int ejecutado = Math.min(quantumMs, cpuRestanteMs);
        cpuRestanteMs -= ejecutado;
        if (cpuRestanteMs == 0) {
            tFinalizacionMs = relojMsActual + ejecutado;
        }
        return ejecutado;
    }

    // ====== Estado y métricas ======
    public boolean terminado() {
        return cpuRestanteMs == 0;
    }

    /** Tiempo de respuesta = (primer despacho) - (llegada). Retorna -1 si aún no ha respondido. */
    public int tiempoRespuesta() {
        if (tPrimeraRespuestaMs == null) return -1;
        return tPrimeraRespuestaMs - llegadaMs;
    }

    /** Tiempo de ejecución / turnaround = (finalización) - (llegada). Retorna -1 si no ha terminado. */
    public int tiempoEjecucion() {
        if (tFinalizacionMs == null) return -1;
        return tFinalizacionMs - llegadaMs;
    }

    /** Tiempo de espera = turnaround - CPU total. Retorna -1 si no ha terminado. */
    public int tiempoEspera() {
        int te = tiempoEjecucion();
        if (te < 0) return -1;
        return te - cpuTotalMs;
    }

    // ====== Getters ======
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public int getSizeKB() { return sizeKB; }
    public int getCpuTotalMs() { return cpuTotalMs; }
    public int getCpuRestanteMs() { return cpuRestanteMs; }
    public int getLlegadaMs() { return llegadaMs; }
    public int getPrioridad() { return prioridad; }
    public Integer getTPrimeraRespuestaMs() { return tPrimeraRespuestaMs; }
    public Integer getTFinalizacionMs() { return tFinalizacionMs; }

    // ====== Setters seguros ======
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("Nombre vacío");
        this.nombre = nombre.trim();
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    // ====== Utilidad ======
    @Override
    public String toString() {
        return String.format(
            "Proceso{id='%s', nombre='%s', sizeKB=%d, cpuTotalMs=%d, llegadaMs=%d, prio=%d, restante=%d, t1=%s, tf=%s}",
            id, nombre, sizeKB, cpuTotalMs, llegadaMs, prioridad, cpuRestanteMs,
            String.valueOf(tPrimeraRespuestaMs), String.valueOf(tFinalizacionMs)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proceso)) return false;
        Proceso p = (Proceso) o;
        return id.equals(p.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}