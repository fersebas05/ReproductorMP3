package Reproductor;

public class PlayList extends PlayListAbs{

    public PlayList(String nombre) {
        super(nombre);
    }

    public PlayList(String nombre, OrdenCriterio criterio) {
        super(nombre, criterio);
    }

    public void ordenar(OrdenCriterio criterio) {
        PlayList aux = new PlayList(this.nombre, criterio);
        for (int i = 0; i < tamanio(); i++) {
            aux.insertar((Cancion) devolver(i));
        }
        
        for (int i = 0; i < aux.tamanio(); i++) {
            this.reemplazar(aux.devolver(i), i);
        }
        this.criterio = criterio;
    }
}