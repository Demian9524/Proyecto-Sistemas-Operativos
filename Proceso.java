public class Proceso {
    String id;
    String nombre;
    int size;
    int tiempo_e;
    int tiempo_l;

    public Proceso(int id, String nombre, int size, int tiempo_e, int tiempo_l){
        this.id = "-";
        this.nombre = "-";
        this.size = 0;
        this.tiempo_e = 0;
        this.tiempo_l = 0;
    }

    public Proceso(String id, String nombre, int size, int tiempo_e, int tiempo_l) {
        this.id = id;
        this.nombre = nombre;
        this.size = size;
        this.tiempo_e = tiempo_e;
        this.tiempo_l = tiempo_l;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int getTiempo_e() {
        return tiempo_e;
    }
    public void setTiempo_e(int tiempo_e) {
        this.tiempo_e = tiempo_e;
    }
    public int getTiempo_l() {
        return tiempo_l;
    }
    public void setTiempo_l(int tiempo_l) {
        this.tiempo_l = tiempo_l;
    }
}


