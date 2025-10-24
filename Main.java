import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("======SIMULADOR PLANIFICADOR DE PROCESOS======");
        int cantidad = 0;
        int memoriaTotal = 0;
        int quantum = 0;
        while(cantidad <= 0) {
            System.out.println("Ingresa la cantidad de procesos: ");
            cantidad = sc.nextInt();
        }
        while(memoriaTotal <= 0) {
            System.out.println("Ingresa la memoria total del planificador: ");
            memoriaTotal = sc.nextInt();
        }
        while(quantum <= 0) {
            System.out.println("Ingresa el quantum del planificador: ");
            quantum = sc.nextInt();
        }
        Queue<Proceso> procesosIngresados = new LinkedList<>();
        for(int i = 0; i < cantidad; i++) {
            System.out.println("Proceso " + (i + 1));
            System.out.println("Ingresa el ID del proceso");
            String id = sc.next();
            System.out.println("Ingresa el nombre del proceso");
            String nombre = sc.next();
            int size = 0;
            while(size <= 0){
                System.out.println("Ingresa el tamaño en memoria del proceso");
                size =  sc.nextInt();
            }
            int tiempo_e = 0;
            while(tiempo_e <= 0){
                System.out.println("Ingresa el tiempo de ejecución del proceso");
                tiempo_e = sc.nextInt();
            }
            int tiempo_l = -1;
            while(tiempo_l < 0){
                System.out.println("Ingresa el tiempo de llegada del proceso");
                tiempo_l = sc.nextInt();
            }
            Proceso nuevoProceso = new Proceso(id, nombre, size, tiempo_e, tiempo_l);
            procesosIngresados.add(nuevoProceso);
        }
        System.out.println("======PLANIFICADOR======");
        System.out.println("Memoria total: " +  memoriaTotal);
        System.out.println("Quantum: " + quantum);
        System.out.println("Procesos a ingresar: " +  procesosIngresados.size());
        System.out.println("======PROCESOS INGRESADOS======");
        for(Proceso proceso : procesosIngresados) {
            System.out.println("ID:                         " + proceso.getId());
            System.out.println("Nombre:                     " + proceso.getNombre());
            System.out.println("Tamaño en memoria:          " + proceso.getSize());
            System.out.println("Tiempo de ejecución:        " + proceso.getTiempo_e());
            System.out.println("Tiempo llegada del proceso: " + proceso.getTiempo_l());
            System.out.println("============================");
        }
        roundRobin(procesosIngresados, memoriaTotal, quantum);
    }

    public static void roundRobin(Queue<Proceso> procesosIngresados, int memoriaTotal, int quantum) {
        LinkedList<Proceso> ordenaProcesos = new LinkedList<>(procesosIngresados);
        ordenaProcesos.sort(Comparator.comparing(Proceso::getTiempo_l));
        Queue<Proceso> procesosEsperando = new LinkedList<>(ordenaProcesos);
        Queue<Proceso> procesosSuben = new LinkedList<>();

        int memoriaDisponible = memoriaTotal;
        int tiempoActual = 0;
        //int procesosTerminados = 0;
        while(!procesosEsperando.isEmpty() && !procesosSuben.isEmpty()) {
            System.out.println("Tiempo actual: " + tiempoActual);
            System.out.println("Memoria disponible: " + memoriaDisponible);
            Proceso proceso = procesosEsperando.peek();
            if(proceso == null) break;
            if(proceso.getTiempo_l() > tiempoActual) break;
            if(proceso.getTiempo_l() <= memoriaDisponible){
                procesosSuben.add(proceso);

            }
        }
    }
}