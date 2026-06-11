package Reproductor;

public class Cancion {
    protected String titulo;
    protected String artista;
    protected String album;
    protected String ruta; 
    protected double duracion;
    protected int anio;

    public Cancion (String titulo, String artista, String album, String ruta, double duracion, int anio) {
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.ruta = ruta;
        this.duracion = duracion;
        this.anio = anio;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getArtista() {
        return this.artista;
    }

    public String getAlbum() {
        return this.album;
    }

    public String getRuta() {
        return this.ruta;
    }

    public double getDuracion() {
        return this.duracion;
    }

    public int getAnio() {
        return this.anio;
    }

    public String toString() {
        return this.titulo + " | " + this.artista + " | " + this.anio;
    }

}
