package Reproductor;
import Contenedores.*;

public abstract class PlayListAbs extends Lista2DLinkedList {
    public enum OrdenCriterio {
        NINGUNO, TITULO, ARTISTA, ANIO
    }
    protected String nombre;
    protected OrdenCriterio criterio;

    public PlayListAbs(String nombre) {
        super();
        this.nombre = nombre;
        this.criterio = OrdenCriterio.NINGUNO;
    }

    public PlayListAbs(String nombre, OrdenCriterio criterio) {
        super();
        this.nombre = nombre;
        this.criterio = criterio;
    }

    public void agregarCancion(Cancion cancion) {
        insertar(cancion, tamanio());
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public OrdenCriterio getCriterio() {
        return this.criterio;
    }

    public void setCriterio(OrdenCriterio criterio) {
        this.criterio = criterio;
    }

    public void mostrarPlaylist() {
        for(int i = 0; i < tamanio(); i++) {
            Cancion c1 = (Cancion) devolver(i);
            System.err.println();
            System.out.println(c1.toString());
        }
    }

    public boolean iguales(Object elementoL, Object elemento) {
        Cancion c1 = (Cancion) elementoL;
        Cancion c2 = (Cancion) elemento;

        String r1 = "";
        String r2 = "";

        switch (criterio) {
            case TITULO:
                r1 = c1.getTitulo();
                r2 = c2.getTitulo();
                break;
            case ARTISTA:
                r1 = c1.getArtista();
                r2 = c2.getArtista();
                break;
            case ANIO:
                r1 = String.valueOf(c1.getAnio());
                r2 = String.valueOf(c2.getAnio());
                break;
        default:
                r1 = c1.getTitulo();
                r2 = c2.getTitulo();
                break;
        }
        return r1.compareToIgnoreCase(r2) == 0;
    }

    public boolean esMayor(Object elememto1, Object elemento2) {
        Cancion c1 = (Cancion) elememto1;
        Cancion c2 = (Cancion) elemento2;

        String r1 = "";
        String r2 = "";

        switch (this.criterio) {
            case TITULO:
                r1 = c1.getTitulo();
                r2 = c2.getTitulo();
                break;
            case ARTISTA:
                r1 = c1.getArtista();
                r2 = c2.getArtista();
                break;
            case ANIO:
                r1 = String.valueOf(c1.getAnio());
                r2 = String.valueOf(c2.getAnio());
                break;
                default:
                    r1 = c1.getTitulo();
                    r2 = c2.getTitulo();
                break;
        }

        return r1.compareToIgnoreCase(r2) > 0;
    }

    public boolean esMenor(Object elemento1, Object elemento2) {
        Cancion c1 = (Cancion) elemento1;
        Cancion c2 = (Cancion) elemento2;

        String r1 = "";
        String r2 = "";

        switch (this.criterio) {
            case TITULO:
                r1 = c1.getTitulo();
                r2 = c2.getTitulo();
                break;
            case ARTISTA:
                r1 = c1.getArtista();
                r2 = c2.getArtista();
                break;
            case ANIO:
                r1 = String.valueOf(c1.getAnio());
                r2 = String.valueOf(c2.getAnio());
                break;
            default:
                r1 = c1.getTitulo();
                r2 = c2.getTitulo();
                break;
        }

        return r1.compareToIgnoreCase(r2) < 0;
    }

}
