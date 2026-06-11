package Reproductor;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ReproductorMP3 {
    protected PlayList playlist;
    protected int indiceActual;
    protected MediaPlayer player;
    protected boolean reproduciendo;
    protected boolean pausado;
    protected boolean aleatorio;

    public ReproductorMP3() {
        this.playlist = null;
        this.indiceActual = -1;
        this.player = null;
        this.reproduciendo = false;
        this.pausado = false;
        this.aleatorio = false;
    }

    public void cargarPlayList(PlayList playlist) {
        this.playlist = playlist;

        if (this.playlist != null && this.playlist.tamanio() > 0) {
            indiceActual = 0;
            cargarCancionActual();            
        } 
        else {
            indiceActual = -1;
            this.player = null;
        }
    }

    public Cancion getCancionActual() {
        Cancion cancion = null;

        if(this.playlist != null && this.indiceActual >= 0 && this.indiceActual < this.playlist.tamanio()) {
            cancion = (Cancion)this.playlist.devolver(this.indiceActual);
        }

        return cancion;
    }

    private void cargarCancionActual() {
        Cancion actual = getCancionActual();

        if(actual != null ){
            if(this.player != null){
                this.player.stop();
                this.player.dispose();
            }
            try {
                File archivo = new File(actual.getRuta());
                Media media = new Media(archivo.toURI().toString());
                this.player = new MediaPlayer(media);

                player.setOnEndOfMedia(()-> {
                    siguiente();
                });
            }
            catch(Exception e) {
                System.out.println("Error al cargar la cancion: " + e.getMessage());
                this.player = null;
            }
        }
    }

    public void play() { 
        Cancion actual = getCancionActual();
        if(actual != null) {
            if(this.player == null) {
                cargarCancionActual();
            }
            
                this.player.play();
                reproduciendo = true;
                pausado = false;

                System.out.println("Reproduciendo: " + actual);
        }
        else{
            System.out.println("No hay cancion para reproducir.");
        }
    }

    public void pause() {
        if(this.player != null) {
            this.player.pause();
            this.reproduciendo = false;
            this.pausado = true;

            System.out.println("Pausado ");
        }
    }

    public void stop() {
        if(this.player != null){
            this.player.stop();
            this.reproduciendo = false;
            this.pausado = false;

            System.out.println("Detenido");
        }
    }

    private int obtenerIndiceAleatorio() {
        int nuevoIndice;
        do {
            nuevoIndice = (int)(Math.random() * this.playlist.tamanio());
        } while(this.playlist.tamanio() > 1 && nuevoIndice == this.indiceActual); // Evitar repetir la misma canción
        
        return nuevoIndice;
    }

    public void siguiente() {
        if(this.playlist != null && this.playlist.tamanio() > 0) {
            if(this.aleatorio) {
                this.indiceActual = obtenerIndiceAleatorio();
            }
            else {
                if(this.indiceActual < this.playlist.tamanio() - 1) {
                    this.indiceActual++;
                }
                else {
                    this.indiceActual = 0; // Volver al inicio si se llega al final
                }
            }
            cargarCancionActual();
            System.out.println("Actual: "+ getCancionActual());
            play();
        }
        else{
            System.out.println("No hay siguiente cancion disponible...");
        }
    }

    public void anterior() {
        if(this.playlist != null && this.playlist.tamanio() > 0) {
            if(this.aleatorio) {
                this.indiceActual = obtenerIndiceAleatorio();
            }
            else {
                if(this.indiceActual > 0) {
                    this.indiceActual--;
                }
                else{
                    this.indiceActual = this.playlist.tamanio() - 1; // Ir al final si se llega al inicio
                }
            }
            cargarCancionActual();
            System.out.println("Actual: "+ getCancionActual());
            play();
        }
        else{
            System.out.println("NO hay cancion anterior disponible...");
        }
    }

    //Metodos que utiliza la intefaz 
    public void toggleAleatorio() {
        this.aleatorio = !this.aleatorio;
        System.out.println("Modo aleatorio: " + (this.aleatorio ? "Activado" : "Desactivado"));
    }

    public boolean esAleatorio() {
        return this.aleatorio;
    }

    public void seleccionarCancion(int indice) {
        if(indice >= 0 && indice < this.playlist.tamanio()){
            this.indiceActual = indice;
            cargarCancionActual();
        }
        else {
            System.out.println("Índice de canción no válido: " + indice);
        }
    }

    public MediaPlayer getPlayer() {
        return this.player;
    }

}
