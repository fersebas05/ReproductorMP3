package ReproductorNuevo;

import java.io.File;

import Recursos.NodoDoble;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ReproductorMP3 {
    private NodoDoble nodoActual;
    private ListaCanciones playlist;
    private MediaPlayer mediaPlayer;
    private boolean reproduciendo;
    private boolean pausado;

    public ReproductorMP3() {
        this.nodoActual = null;
        this.playlist = new ListaCanciones();
        this.mediaPlayer = null;
        this.reproduciendo = false;
        this.pausado = false;
    }
    
    public void agregarCancion(Cancion cancion) {
        this.playlist.insertar(cancion, this.playlist.tamanio());
        // if(nodoActual == null) {
        //     nodoActual = this.playlist.getFrenteL();
        // }
    }

    public void reproducir() {
        Cancion cancion = (Cancion) nodoActual.getNodoInfo();
        if(cancion != null) {
            if(this.mediaPlayer == null) {
                cargarCancionActual(cancion);
            }
            this.mediaPlayer.play();
            this.reproduciendo = true;
            this.pausado = false;
        }
        else {
            System.out.println("No hay cancion para reproducir");
        }
    }

    // public void reproducir() {
    //     if(this.nodoActual == null) {
    //         this.nodoActual = this.playlist.getFrenteL();
    //     }

    //     if(this.nodoActual != null) {
    //         Cancion cancion = (Cancion) this.nodoActual.getNodoInfo();

    //     }
    // }

    //Carga el MediaPlayer de forma segura aislando fallos de lectura o metadatos.
    private void cargarCancionActual(Cancion cancionActual) {
        try {
            File archivo = new File(cancionActual.getRuta());
            Media media = new Media(archivo.toURI().toString()); 
            this.mediaPlayer = new MediaPlayer(media);

            // Al terminar de reproducirse, pasa de manera natural a la siguiente
            this.mediaPlayer.setOnEndOfMedia(() -> {
                this.siguiente();
            });

        } catch (Exception e) {
            System.err.println("Error al cargar los metadatos o el archivo de audio: " + e.getMessage());
            this.mediaPlayer = null;
            this.reproduciendo = true;
            this.pausado = false;
        }
    }

    public void mostrar() {
        Cancion c1 = (Cancion) nodoActual.getNodoInfo();
        System.out.println("Cancion: " +  c1.toString());
    }

    public void pausar() {
        if (this.mediaPlayer != null && this.reproduciendo) {
            this.mediaPlayer.pause();
            this.pausado = true;
            this.reproduciendo = false;
        }
    }

    public void detener() {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.stop();
            this.mediaPlayer.dispose();
            this.mediaPlayer = null;
            this.reproduciendo = false;
            this.pausado = false;
        }
    }

    public void siguiente() {
        if (this.nodoActual != null) {
            this.detener();
            this.nodoActual = this.nodoActual.getNextNodo();

            if (this.nodoActual != null) {
                this.reproducir(); //Si es null, simplemente no hacemos nada (se queda detenido)
            }
        }
    }

    public void anterior() {
        if (this.nodoActual != null) {
            this.detener();
            this.nodoActual = this.nodoActual.getPrevNodo();
            
            //Si es null, la reproducción se detiene aquí
            if (this.nodoActual != null) {
                this.reproducir();
            }
        }
    }

}